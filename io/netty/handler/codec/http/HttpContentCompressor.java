package io.netty.handler.codec.http;

import io.netty.channel.*;
import io.netty.channel.embedded.*;
import io.netty.handler.codec.compression.*;
import io.netty.util.internal.*;

public class HttpContentCompressor extends HttpContentEncoder
{
    private final int compressionLevel;
    private final int windowBits;
    private final int memLevel;
    
    public HttpContentCompressor() {
        this(6);
    }
    
    public HttpContentCompressor(final int n) {
        this(n, 15, 8);
    }
    
    public HttpContentCompressor(final int compressionLevel, final int windowBits, final int memLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        }
        this.compressionLevel = compressionLevel;
        this.windowBits = windowBits;
        this.memLevel = memLevel;
    }
    
    @Override
    protected Result beginEncode(final HttpResponse httpResponse, final String s) throws Exception {
        final String value = httpResponse.headers().get("Content-Encoding");
        if (value != null && !"identity".equalsIgnoreCase(value)) {
            return null;
        }
        final ZlibWrapper determineWrapper = this.determineWrapper(s);
        if (determineWrapper == null) {
            return null;
        }
        String s2 = null;
        switch (determineWrapper) {
            case GZIP: {
                s2 = "gzip";
                break;
            }
            case ZLIB: {
                s2 = "deflate";
                break;
            }
            default: {
                throw new Error();
            }
        }
        return new Result(s2, new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibEncoder(determineWrapper, this.compressionLevel, this.windowBits, this.memLevel) }));
    }
    
    protected ZlibWrapper determineWrapper(final String s) {
        float n = -1.0f;
        float n2 = -1.0f;
        float n3 = -1.0f;
        final String[] split = StringUtil.split(s, ',');
        while (0 < split.length) {
            final String s2 = split[0];
            float floatValue = 1.0f;
            final int index = s2.indexOf(61);
            if (index != -1) {
                floatValue = Float.valueOf(s2.substring(index + 1));
            }
            if (s2.contains("*")) {
                n = floatValue;
            }
            else if (s2.contains("gzip") && floatValue > n2) {
                n2 = floatValue;
            }
            else if (s2.contains("deflate") && floatValue > n3) {
                n3 = floatValue;
            }
            int n4 = 0;
            ++n4;
        }
        if (n2 <= 0.0f && n3 <= 0.0f) {
            if (n > 0.0f) {
                if (n2 == -1.0f) {
                    return ZlibWrapper.GZIP;
                }
                if (n3 == -1.0f) {
                    return ZlibWrapper.ZLIB;
                }
            }
            return null;
        }
        if (n2 >= n3) {
            return ZlibWrapper.GZIP;
        }
        return ZlibWrapper.ZLIB;
    }
}
