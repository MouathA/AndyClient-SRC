package io.netty.handler.codec.compression;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class SnappyFramedEncoder extends MessageToByteEncoder
{
    private static final int MIN_COMPRESSIBLE_LENGTH = 18;
    private static final byte[] STREAM_START;
    private final Snappy snappy;
    private boolean started;
    
    public SnappyFramedEncoder() {
        this.snappy = new Snappy();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        if (!this.started) {
            this.started = true;
            byteBuf2.writeBytes(SnappyFramedEncoder.STREAM_START);
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes > 18) {
            while (true) {
                final int n = byteBuf2.writerIndex() + 1;
                if (readableBytes < 18) {
                    writeUnencodedChunk(byteBuf.readSlice(readableBytes), byteBuf2, readableBytes);
                    break;
                }
                byteBuf2.writeInt(0);
                if (readableBytes <= 32767) {
                    final ByteBuf slice = byteBuf.readSlice(readableBytes);
                    calculateAndWriteChecksum(slice, byteBuf2);
                    this.snappy.encode(slice, byteBuf2, readableBytes);
                    setChunkLength(byteBuf2, n);
                    break;
                }
                final ByteBuf slice2 = byteBuf.readSlice(32767);
                calculateAndWriteChecksum(slice2, byteBuf2);
                this.snappy.encode(slice2, byteBuf2, 32767);
                setChunkLength(byteBuf2, n);
                readableBytes -= 32767;
            }
        }
        else {
            writeUnencodedChunk(byteBuf, byteBuf2, readableBytes);
        }
    }
    
    private static void writeUnencodedChunk(final ByteBuf byteBuf, final ByteBuf byteBuf2, final int n) {
        byteBuf2.writeByte(1);
        writeChunkLength(byteBuf2, n + 4);
        calculateAndWriteChecksum(byteBuf, byteBuf2);
        byteBuf2.writeBytes(byteBuf, n);
    }
    
    private static void setChunkLength(final ByteBuf byteBuf, final int n) {
        final int n2 = byteBuf.writerIndex() - n - 3;
        if (n2 >>> 24 != 0) {
            throw new CompressionException("compressed data too large: " + n2);
        }
        byteBuf.setMedium(n, ByteBufUtil.swapMedium(n2));
    }
    
    private static void writeChunkLength(final ByteBuf byteBuf, final int n) {
        byteBuf.writeMedium(ByteBufUtil.swapMedium(n));
    }
    
    private static void calculateAndWriteChecksum(final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        byteBuf2.writeInt(ByteBufUtil.swapInt(Snappy.calculateChecksum(byteBuf)));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    static {
        STREAM_START = new byte[] { -1, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
    }
}
