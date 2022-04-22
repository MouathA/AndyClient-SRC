package io.netty.util;

import io.netty.util.internal.*;
import java.util.*;
import java.nio.charset.*;

public final class CharsetUtil
{
    public static final Charset UTF_16;
    public static final Charset UTF_16BE;
    public static final Charset UTF_16LE;
    public static final Charset UTF_8;
    public static final Charset ISO_8859_1;
    public static final Charset US_ASCII;
    
    public static CharsetEncoder getEncoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        final Map charsetEncoderCache = InternalThreadLocalMap.get().charsetEncoderCache();
        final CharsetEncoder charsetEncoder = charsetEncoderCache.get(charset);
        if (charsetEncoder != null) {
            charsetEncoder.reset();
            charsetEncoder.onMalformedInput(CodingErrorAction.REPLACE);
            charsetEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            return charsetEncoder;
        }
        final CharsetEncoder encoder = charset.newEncoder();
        encoder.onMalformedInput(CodingErrorAction.REPLACE);
        encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        charsetEncoderCache.put(charset, encoder);
        return encoder;
    }
    
    public static CharsetDecoder getDecoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        final Map charsetDecoderCache = InternalThreadLocalMap.get().charsetDecoderCache();
        final CharsetDecoder charsetDecoder = charsetDecoderCache.get(charset);
        if (charsetDecoder != null) {
            charsetDecoder.reset();
            charsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
            charsetDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            return charsetDecoder;
        }
        final CharsetDecoder decoder = charset.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        charsetDecoderCache.put(charset, decoder);
        return decoder;
    }
    
    private CharsetUtil() {
    }
    
    static {
        UTF_16 = Charset.forName("UTF-16");
        UTF_16BE = Charset.forName("UTF-16BE");
        UTF_16LE = Charset.forName("UTF-16LE");
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        US_ASCII = Charset.forName("US-ASCII");
    }
}
