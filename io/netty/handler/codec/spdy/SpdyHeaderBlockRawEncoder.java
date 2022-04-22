package io.netty.handler.codec.spdy;

import io.netty.buffer.*;
import java.util.*;

public class SpdyHeaderBlockRawEncoder extends SpdyHeaderBlockEncoder
{
    private final int version;
    
    public SpdyHeaderBlockRawEncoder(final SpdyVersion spdyVersion) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        this.version = spdyVersion.getVersion();
    }
    
    private static void setLengthField(final ByteBuf byteBuf, final int n, final int n2) {
        byteBuf.setInt(n, n2);
    }
    
    private static void writeLengthField(final ByteBuf byteBuf, final int n) {
        byteBuf.writeInt(n);
    }
    
    public ByteBuf encode(final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        final Set names = spdyHeadersFrame.headers().names();
        final int size = names.size();
        if (size == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (size > 65535) {
            throw new IllegalArgumentException("header block contains too many headers");
        }
        final ByteBuf buffer = Unpooled.buffer();
        writeLengthField(buffer, size);
        for (final String s : names) {
            final byte[] bytes = s.getBytes("UTF-8");
            writeLengthField(buffer, bytes.length);
            buffer.writeBytes(bytes);
            buffer.writerIndex();
            writeLengthField(buffer, 0);
            final Iterator iterator2 = spdyHeadersFrame.headers().getAll(s).iterator();
            while (iterator2.hasNext()) {
                final byte[] bytes2 = iterator2.next().getBytes("UTF-8");
                if (bytes2.length > 0) {
                    buffer.writeBytes(bytes2);
                    buffer.writeByte(0);
                    final int n = 0 + (bytes2.length + 1);
                }
            }
        }
        return buffer;
    }
    
    @Override
    void end() {
    }
}
