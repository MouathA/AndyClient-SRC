package io.netty.handler.codec.compression;

import java.util.zip.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class JdkZlibDecoder extends ZlibDecoder
{
    private static final int FHCRC = 2;
    private static final int FEXTRA = 4;
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private static final int FRESERVED = 224;
    private Inflater inflater;
    private final byte[] dictionary;
    private final CRC32 crc;
    private GzipState gzipState;
    private int flags;
    private int xlen;
    private boolean finished;
    private boolean decideZlibOrNone;
    
    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, null);
    }
    
    public JdkZlibDecoder(final byte[] array) {
        this(ZlibWrapper.ZLIB, array);
    }
    
    public JdkZlibDecoder(final ZlibWrapper zlibWrapper) {
        this(zlibWrapper, null);
    }
    
    private JdkZlibDecoder(final ZlibWrapper zlibWrapper, final byte[] dictionary) {
        this.gzipState = GzipState.HEADER_START;
        this.flags = -1;
        this.xlen = -1;
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        switch (zlibWrapper) {
            case GZIP: {
                this.inflater = new Inflater(true);
                this.crc = new CRC32();
                break;
            }
            case NONE: {
                this.inflater = new Inflater(true);
                this.crc = null;
                break;
            }
            case ZLIB: {
                this.inflater = new Inflater();
                this.crc = null;
                break;
            }
            case ZLIB_OR_NONE: {
                this.decideZlibOrNone = true;
                this.crc = null;
                break;
            }
            default: {
                throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + zlibWrapper);
            }
        }
        this.dictionary = dictionary;
    }
    
    @Override
    public boolean isClosed() {
        return this.finished;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        if (!byteBuf.isReadable()) {
            return;
        }
        if (this.decideZlibOrNone) {
            if (byteBuf.readableBytes() < 2) {
                return;
            }
            this.inflater = new Inflater(!looksLikeZlib(byteBuf.getShort(0)));
            this.decideZlibOrNone = false;
        }
        if (this.crc != null) {
            switch (this.gzipState) {
                case FOOTER_START: {
                    if (this.readGZIPFooter(byteBuf)) {
                        this.finished = true;
                    }
                    return;
                }
                default: {
                    if (this.gzipState != GzipState.HEADER_END && !this.readGZIPHeader(byteBuf)) {
                        return;
                    }
                    break;
                }
            }
        }
        final int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.inflater.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), byteBuf.readableBytes());
        }
        else {
            final byte[] input = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), input);
            this.inflater.setInput(input);
        }
        final int n = this.inflater.getRemaining() << 1;
        ByteBuf byteBuf2 = channelHandlerContext.alloc().heapBuffer(n);
        byte[] array = byteBuf2.array();
        while (!this.inflater.needsInput()) {
            final int writerIndex = byteBuf2.writerIndex();
            final int n2 = byteBuf2.arrayOffset() + writerIndex;
            final int writableBytes = byteBuf2.writableBytes();
            if (writableBytes == 0) {
                list.add(byteBuf2);
                byteBuf2 = channelHandlerContext.alloc().heapBuffer(n);
                array = byteBuf2.array();
            }
            else {
                final int inflate = this.inflater.inflate(array, n2, writableBytes);
                if (inflate > 0) {
                    byteBuf2.writerIndex(writerIndex + inflate);
                    if (this.crc != null) {
                        this.crc.update(array, n2, inflate);
                    }
                }
                else if (this.inflater.needsDictionary()) {
                    if (this.dictionary == null) {
                        throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                    }
                    this.inflater.setDictionary(this.dictionary);
                }
                if (!this.inflater.finished()) {
                    continue;
                }
                if (this.crc == null) {
                    this.finished = true;
                    break;
                }
                break;
            }
        }
        byteBuf.skipBytes(readableBytes - this.inflater.getRemaining());
        if (true) {
            this.gzipState = GzipState.FOOTER_START;
            if (this.readGZIPFooter(byteBuf)) {
                this.finished = true;
            }
        }
        if (byteBuf2.isReadable()) {
            list.add(byteBuf2);
        }
        else {
            byteBuf2.release();
        }
    }
    
    @Override
    protected void handlerRemoved0(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved0(channelHandlerContext);
        if (this.inflater != null) {
            this.inflater.end();
        }
    }
    
    private boolean readGZIPHeader(final ByteBuf byteBuf) {
        switch (this.gzipState) {
            case HEADER_START: {
                if (byteBuf.readableBytes() < 10) {
                    return false;
                }
                final byte byte1 = byteBuf.readByte();
                final byte byte2 = byteBuf.readByte();
                if (byte1 != 31) {
                    throw new DecompressionException("Input is not in the GZIP format");
                }
                this.crc.update(byte1);
                this.crc.update(byte2);
                final short unsignedByte = byteBuf.readUnsignedByte();
                if (unsignedByte != 8) {
                    throw new DecompressionException("Unsupported compression method " + unsignedByte + " in the GZIP header");
                }
                this.crc.update(unsignedByte);
                this.flags = byteBuf.readUnsignedByte();
                this.crc.update(this.flags);
                if ((this.flags & 0xE0) != 0x0) {
                    throw new DecompressionException("Reserved flags are set in the GZIP header");
                }
                this.crc.update(byteBuf.readByte());
                this.crc.update(byteBuf.readByte());
                this.crc.update(byteBuf.readByte());
                this.crc.update(byteBuf.readByte());
                this.crc.update(byteBuf.readUnsignedByte());
                this.crc.update(byteBuf.readUnsignedByte());
                this.gzipState = GzipState.FLG_READ;
            }
            case FLG_READ: {
                if ((this.flags & 0x4) != 0x0) {
                    if (byteBuf.readableBytes() < 2) {
                        return false;
                    }
                    final short unsignedByte2 = byteBuf.readUnsignedByte();
                    final short unsignedByte3 = byteBuf.readUnsignedByte();
                    this.crc.update(unsignedByte2);
                    this.crc.update(unsignedByte3);
                    this.xlen |= (unsignedByte2 << 8 | unsignedByte3);
                }
                this.gzipState = GzipState.XLEN_READ;
            }
            case XLEN_READ: {
                if (this.xlen != -1) {
                    if (byteBuf.readableBytes() < this.xlen) {
                        return false;
                    }
                    final byte[] array = new byte[this.xlen];
                    byteBuf.readBytes(array);
                    this.crc.update(array);
                }
                this.gzipState = GzipState.SKIP_FNAME;
            }
            case SKIP_FNAME: {
                if ((this.flags & 0x8) != 0x0) {
                    if (!byteBuf.isReadable()) {
                        return false;
                    }
                    do {
                        final short unsignedByte4 = byteBuf.readUnsignedByte();
                        this.crc.update(unsignedByte4);
                        if (unsignedByte4 == 0) {
                            break;
                        }
                    } while (byteBuf.isReadable());
                }
                this.gzipState = GzipState.SKIP_COMMENT;
            }
            case SKIP_COMMENT: {
                if ((this.flags & 0x10) != 0x0) {
                    if (!byteBuf.isReadable()) {
                        return false;
                    }
                    do {
                        final short unsignedByte5 = byteBuf.readUnsignedByte();
                        this.crc.update(unsignedByte5);
                        if (unsignedByte5 == 0) {
                            break;
                        }
                    } while (byteBuf.isReadable());
                }
                this.gzipState = GzipState.PROCESS_FHCRC;
            }
            case PROCESS_FHCRC: {
                if ((this.flags & 0x2) != 0x0) {
                    if (byteBuf.readableBytes() < 4) {
                        return false;
                    }
                    this.verifyCrc(byteBuf);
                }
                this.crc.reset();
                this.gzipState = GzipState.HEADER_END;
            }
            case HEADER_END: {
                return true;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    private boolean readGZIPFooter(final ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 8) {
            return false;
        }
        this.verifyCrc(byteBuf);
        while (0 < 4) {
            final int n = 0x0 | byteBuf.readUnsignedByte() << 0;
            int totalOut = 0;
            ++totalOut;
        }
        int totalOut = this.inflater.getTotalOut();
        if (false) {
            throw new DecompressionException("Number of bytes mismatch. Expected: " + 0 + ", Got: " + 0);
        }
        return true;
    }
    
    private void verifyCrc(final ByteBuf byteBuf) {
        long n = 0L;
        while (0 < 4) {
            n |= (long)byteBuf.readUnsignedByte() << 0;
            int n2 = 0;
            ++n2;
        }
        final long value = this.crc.getValue();
        if (n != value) {
            throw new DecompressionException("CRC value missmatch. Expected: " + n + ", Got: " + value);
        }
    }
    
    private static boolean looksLikeZlib(final short n) {
        return (n & 0x7800) == 0x7800 && n % 31 == 0;
    }
    
    private enum GzipState
    {
        HEADER_START("HEADER_START", 0), 
        HEADER_END("HEADER_END", 1), 
        FLG_READ("FLG_READ", 2), 
        XLEN_READ("XLEN_READ", 3), 
        SKIP_FNAME("SKIP_FNAME", 4), 
        SKIP_COMMENT("SKIP_COMMENT", 5), 
        PROCESS_FHCRC("PROCESS_FHCRC", 6), 
        FOOTER_START("FOOTER_START", 7);
        
        private static final GzipState[] $VALUES;
        
        private GzipState(final String s, final int n) {
        }
        
        static {
            $VALUES = new GzipState[] { GzipState.HEADER_START, GzipState.HEADER_END, GzipState.FLG_READ, GzipState.XLEN_READ, GzipState.SKIP_FNAME, GzipState.SKIP_COMMENT, GzipState.PROCESS_FHCRC, GzipState.FOOTER_START };
        }
    }
}
