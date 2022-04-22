package io.netty.handler.codec.spdy;

import java.util.zip.*;
import io.netty.buffer.*;

class SpdyHeaderBlockZlibEncoder extends SpdyHeaderBlockRawEncoder
{
    private final Deflater compressor;
    private boolean finished;
    
    SpdyHeaderBlockZlibEncoder(final SpdyVersion spdyVersion, final int n) {
        super(spdyVersion);
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        (this.compressor = new Deflater(n)).setDictionary(SpdyCodecUtil.SPDY_DICT);
    }
    
    private int setInput(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.compressor.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
        }
        else {
            final byte[] array = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), array);
            this.compressor.setInput(array, 0, array.length);
        }
        return readableBytes;
    }
    
    private void encode(final ByteBuf byteBuf) {
        while (this.compressInto(byteBuf)) {
            byteBuf.ensureWritable(byteBuf.capacity() << 1);
        }
    }
    
    private boolean compressInto(final ByteBuf byteBuf) {
        final byte[] array = byteBuf.array();
        final int n = byteBuf.arrayOffset() + byteBuf.writerIndex();
        final int writableBytes = byteBuf.writableBytes();
        final int deflate = this.compressor.deflate(array, n, writableBytes, 2);
        byteBuf.writerIndex(byteBuf.writerIndex() + deflate);
        return deflate == writableBytes;
    }
    
    @Override
    public ByteBuf encode(final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (spdyHeadersFrame == null) {
            throw new IllegalArgumentException("frame");
        }
        if (this.finished) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf encode = super.encode(spdyHeadersFrame);
        if (encode.readableBytes() == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf heapBuffer = encode.alloc().heapBuffer(encode.readableBytes());
        final int setInput = this.setInput(encode);
        this.encode(heapBuffer);
        encode.skipBytes(setInput);
        return heapBuffer;
    }
    
    public void end() {
        if (this.finished) {
            return;
        }
        this.finished = true;
        this.compressor.end();
        super.end();
    }
}
