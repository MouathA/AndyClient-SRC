package io.netty.handler.codec.compression;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class SnappyFramedDecoder extends ByteToMessageDecoder
{
    private static final byte[] SNAPPY;
    private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
    private final Snappy snappy;
    private final boolean validateChecksums;
    private boolean started;
    private boolean corrupted;
    
    public SnappyFramedDecoder() {
        this(false);
    }
    
    public SnappyFramedDecoder(final boolean validateChecksums) {
        this.snappy = new Snappy();
        this.validateChecksums = validateChecksums;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.corrupted) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        final int readerIndex = byteBuf.readerIndex();
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes < 4) {
            return;
        }
        final short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
        final ChunkType mapChunkType = mapChunkType((byte)unsignedByte);
        final int swapMedium = ByteBufUtil.swapMedium(byteBuf.getUnsignedMedium(readerIndex + 1));
        switch (mapChunkType) {
            case STREAM_IDENTIFIER: {
                if (swapMedium != SnappyFramedDecoder.SNAPPY.length) {
                    throw new DecompressionException("Unexpected length of stream identifier: " + swapMedium);
                }
                if (readableBytes < 4 + SnappyFramedDecoder.SNAPPY.length) {
                    break;
                }
                final byte[] array = new byte[swapMedium];
                byteBuf.skipBytes(4).readBytes(array);
                if (!Arrays.equals(array, SnappyFramedDecoder.SNAPPY)) {
                    throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
                }
                this.started = true;
                break;
            }
            case RESERVED_SKIPPABLE: {
                if (!this.started) {
                    throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                }
                if (readableBytes < 4 + swapMedium) {
                    return;
                }
                byteBuf.skipBytes(4 + swapMedium);
                break;
            }
            case RESERVED_UNSKIPPABLE: {
                throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(unsignedByte));
            }
            case UNCOMPRESSED_DATA: {
                if (!this.started) {
                    throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                }
                if (swapMedium > 65540) {
                    throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                }
                if (readableBytes < 4 + swapMedium) {
                    return;
                }
                byteBuf.skipBytes(4);
                if (this.validateChecksums) {
                    Snappy.validateChecksum(ByteBufUtil.swapInt(byteBuf.readInt()), byteBuf, byteBuf.readerIndex(), swapMedium - 4);
                }
                else {
                    byteBuf.skipBytes(4);
                }
                list.add(byteBuf.readSlice(swapMedium - 4).retain());
                break;
            }
            case COMPRESSED_DATA: {
                if (!this.started) {
                    throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                }
                if (readableBytes < 4 + swapMedium) {
                    return;
                }
                byteBuf.skipBytes(4);
                final int swapInt = ByteBufUtil.swapInt(byteBuf.readInt());
                final ByteBuf buffer = channelHandlerContext.alloc().buffer(0);
                if (this.validateChecksums) {
                    final int writerIndex = byteBuf.writerIndex();
                    byteBuf.writerIndex(byteBuf.readerIndex() + swapMedium - 4);
                    this.snappy.decode(byteBuf, buffer);
                    byteBuf.writerIndex(writerIndex);
                    Snappy.validateChecksum(swapInt, buffer, 0, buffer.writerIndex());
                }
                else {
                    this.snappy.decode(byteBuf.readSlice(swapMedium - 4), buffer);
                }
                list.add(buffer);
                this.snappy.reset();
                break;
            }
        }
    }
    
    private static ChunkType mapChunkType(final byte b) {
        if (b == 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (b == 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (b == -1) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((b & 0x80) == 0x80) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }
    
    static {
        SNAPPY = new byte[] { 115, 78, 97, 80, 112, 89 };
    }
    
    private enum ChunkType
    {
        STREAM_IDENTIFIER("STREAM_IDENTIFIER", 0), 
        COMPRESSED_DATA("COMPRESSED_DATA", 1), 
        UNCOMPRESSED_DATA("UNCOMPRESSED_DATA", 2), 
        RESERVED_UNSKIPPABLE("RESERVED_UNSKIPPABLE", 3), 
        RESERVED_SKIPPABLE("RESERVED_SKIPPABLE", 4);
        
        private static final ChunkType[] $VALUES;
        
        private ChunkType(final String s, final int n) {
        }
        
        static {
            $VALUES = new ChunkType[] { ChunkType.STREAM_IDENTIFIER, ChunkType.COMPRESSED_DATA, ChunkType.UNCOMPRESSED_DATA, ChunkType.RESERVED_UNSKIPPABLE, ChunkType.RESERVED_SKIPPABLE };
        }
    }
}
