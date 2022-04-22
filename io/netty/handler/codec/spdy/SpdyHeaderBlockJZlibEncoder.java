package io.netty.handler.codec.spdy;

import com.jcraft.jzlib.*;
import io.netty.handler.codec.compression.*;
import io.netty.buffer.*;

class SpdyHeaderBlockJZlibEncoder extends SpdyHeaderBlockRawEncoder
{
    private final Deflater z;
    private boolean finished;
    
    SpdyHeaderBlockJZlibEncoder(final SpdyVersion spdyVersion, final int n, final int n2, final int n3) {
        super(spdyVersion);
        this.z = new Deflater();
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        final int deflateInit = this.z.deflateInit(n, n2, n3, JZlib.W_ZLIB);
        if (deflateInit != 0) {
            throw new CompressionException("failed to initialize an SPDY header block deflater: " + deflateInit);
        }
        final int deflateSetDictionary = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
        if (deflateSetDictionary != 0) {
            throw new CompressionException("failed to set the SPDY dictionary: " + deflateSetDictionary);
        }
    }
    
    private void setInput(final ByteBuf byteBuf) {
        final byte[] next_in = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(next_in);
        this.z.next_in = next_in;
        this.z.next_in_index = 0;
        this.z.avail_in = next_in.length;
    }
    
    private void encode(final ByteBuf byteBuf) {
        final byte[] next_out = new byte[(int)Math.ceil(this.z.next_in.length * 1.001) + 12];
        this.z.next_out = next_out;
        this.z.next_out_index = 0;
        this.z.avail_out = next_out.length;
        final int deflate = this.z.deflate(2);
        if (deflate != 0) {
            throw new CompressionException("compression failure: " + deflate);
        }
        if (this.z.next_out_index != 0) {
            byteBuf.writeBytes(next_out, 0, this.z.next_out_index);
        }
        this.z.next_in = null;
        this.z.next_out = null;
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
        final ByteBuf buffer = encode.alloc().buffer();
        this.setInput(encode);
        this.encode(buffer);
        return buffer;
    }
    
    public void end() {
        if (this.finished) {
            return;
        }
        this.finished = true;
        this.z.deflateEnd();
        this.z.next_in = null;
        this.z.next_out = null;
    }
}
