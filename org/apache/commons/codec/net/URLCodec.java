package org.apache.commons.codec.net;

import java.util.*;
import org.apache.commons.codec.binary.*;
import java.io.*;
import org.apache.commons.codec.*;

public class URLCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
{
    static final int RADIX = 16;
    @Deprecated
    protected String charset;
    protected static final byte ESCAPE_CHAR = 37;
    protected static final BitSet WWW_FORM_URL;
    
    public URLCodec() {
        this("UTF-8");
    }
    
    public URLCodec(final String charset) {
        this.charset = charset;
    }
    
    public static final byte[] encodeUrl(BitSet www_FORM_URL, final byte[] array) {
        if (array == null) {
            return null;
        }
        if (www_FORM_URL == null) {
            www_FORM_URL = URLCodec.WWW_FORM_URL;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (0 < array.length) {
            final byte b = array[0];
            if (43 < 0) {}
            if (www_FORM_URL.get(43)) {
                if (43 == 32) {}
                byteArrayOutputStream.write(43);
            }
            else {
                byteArrayOutputStream.write(37);
                final char upperCase = Character.toUpperCase(Character.forDigit(2, 16));
                final char upperCase2 = Character.toUpperCase(Character.forDigit(11, 16));
                byteArrayOutputStream.write(upperCase);
                byteArrayOutputStream.write(upperCase2);
            }
            int n = 0;
            ++n;
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static final byte[] decodeUrl(final byte[] array) throws DecoderException {
        if (array == null) {
            return null;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (0 < array.length) {
            final byte b = array[0];
            int n = 0;
            if (b == 43) {
                byteArrayOutputStream.write(32);
            }
            else if (b == 37) {
                ++n;
                final int digit16 = Utils.digit16(array[0]);
                ++n;
                byteArrayOutputStream.write((char)((digit16 << 4) + Utils.digit16(array[0])));
            }
            else {
                byteArrayOutputStream.write(b);
            }
            ++n;
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        return encodeUrl(URLCodec.WWW_FORM_URL, array);
    }
    
    @Override
    public byte[] decode(final byte[] array) throws DecoderException {
        return decodeUrl(array);
    }
    
    public String encode(final String s, final String s2) throws UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(s2)));
    }
    
    @Override
    public String encode(final String s) throws EncoderException {
        if (s == null) {
            return null;
        }
        return this.encode(s, this.getDefaultCharset());
    }
    
    public String decode(final String s, final String s2) throws DecoderException, UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), s2);
    }
    
    @Override
    public String decode(final String s) throws DecoderException {
        if (s == null) {
            return null;
        }
        return this.decode(s, this.getDefaultCharset());
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.encode((byte[])o);
        }
        if (o instanceof String) {
            return this.encode((String)o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be URL encoded");
    }
    
    @Override
    public Object decode(final Object o) throws DecoderException {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.decode((byte[])o);
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be URL decoded");
    }
    
    public String getDefaultCharset() {
        return this.charset;
    }
    
    @Deprecated
    public String getEncoding() {
        return this.charset;
    }
    
    static {
        WWW_FORM_URL = new BitSet(256);
        int n = 0;
        while (48 <= 122) {
            URLCodec.WWW_FORM_URL.set(48);
            ++n;
        }
        while (48 <= 90) {
            URLCodec.WWW_FORM_URL.set(48);
            ++n;
        }
        while (48 <= 57) {
            URLCodec.WWW_FORM_URL.set(48);
            ++n;
        }
        URLCodec.WWW_FORM_URL.set(45);
        URLCodec.WWW_FORM_URL.set(95);
        URLCodec.WWW_FORM_URL.set(46);
        URLCodec.WWW_FORM_URL.set(42);
        URLCodec.WWW_FORM_URL.set(32);
    }
}
