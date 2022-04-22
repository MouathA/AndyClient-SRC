package io.netty.handler.codec.spdy;

import java.util.zip.*;
import io.netty.buffer.*;

final class SpdyHeaderBlockZlibDecoder extends SpdyHeaderBlockRawDecoder
{
    private static final int DEFAULT_BUFFER_CAPACITY = 4096;
    private static final SpdyProtocolException INVALID_HEADER_BLOCK;
    private final Inflater decompressor;
    private ByteBuf decompressed;
    
    SpdyHeaderBlockZlibDecoder(final SpdyVersion spdyVersion, final int n) {
        super(spdyVersion, n);
        this.decompressor = new Inflater();
    }
    
    @Override
    void decode(final ByteBuf input, final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        final int setInput = this.setInput(input);
        while (this.decompress(input.alloc(), spdyHeadersFrame) > 0) {}
        if (this.decompressor.getRemaining() != 0) {
            throw SpdyHeaderBlockZlibDecoder.INVALID_HEADER_BLOCK;
        }
        input.skipBytes(setInput);
    }
    
    private int setInput(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.decompressor.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
        }
        else {
            final byte[] array = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), array);
            this.decompressor.setInput(array, 0, array.length);
        }
        return readableBytes;
    }
    
    private int decompress(final ByteBufAllocator byteBufAllocator, final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        this.ensureBuffer(byteBufAllocator);
        final byte[] array = this.decompressed.array();
        final int n = this.decompressed.arrayOffset() + this.decompressed.writerIndex();
        int n2 = this.decompressor.inflate(array, n, this.decompressed.writableBytes());
        if (n2 == 0 && this.decompressor.needsDictionary()) {
            this.decompressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
            n2 = this.decompressor.inflate(array, n, this.decompressed.writableBytes());
        }
        if (spdyHeadersFrame != null) {
            this.decompressed.writerIndex(this.decompressed.writerIndex() + n2);
            this.decodeHeaderBlock(this.decompressed, spdyHeadersFrame);
            this.decompressed.discardReadBytes();
        }
        return n2;
    }
    
    private void ensureBuffer(final ByteBufAllocator byteBufAllocator) {
        if (this.decompressed == null) {
            this.decompressed = byteBufAllocator.heapBuffer(4096);
        }
        this.decompressed.ensureWritable(1);
    }
    
    @Override
    void endHeaderBlock(final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        super.endHeaderBlock(spdyHeadersFrame);
        this.releaseBuffer();
    }
    
    public void end() {
        super.end();
        this.releaseBuffer();
        this.decompressor.end();
    }
    
    private void releaseBuffer() {
        if (this.decompressed != null) {
            this.decompressed.release();
            this.decompressed = null;
        }
    }
    
    static {
        INVALID_HEADER_BLOCK = new SpdyProtocolException("Invalid Header Block");
    }
}
