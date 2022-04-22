package org.apache.commons.codec.net;

import java.util.*;
import java.nio.charset.*;
import org.apache.commons.codec.*;
import org.apache.commons.codec.binary.*;
import java.io.*;

public class QuotedPrintableCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
{
    private final Charset charset;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte ESCAPE_CHAR = 61;
    private static final byte TAB = 9;
    private static final byte SPACE = 32;
    
    public QuotedPrintableCodec() {
        this(Charsets.UTF_8);
    }
    
    public QuotedPrintableCodec(final Charset charset) {
        this.charset = charset;
    }
    
    public QuotedPrintableCodec(final String s) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(s));
    }
    
    private static final void encodeQuotedPrintable(final int n, final ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        final char upperCase = Character.toUpperCase(Character.forDigit(n >> 4 & 0xF, 16));
        final char upperCase2 = Character.toUpperCase(Character.forDigit(n & 0xF, 16));
        byteArrayOutputStream.write(upperCase);
        byteArrayOutputStream.write(upperCase2);
    }
    
    public static final byte[] encodeQuotedPrintable(BitSet printable_CHARS, final byte[] array) {
        if (array == null) {
            return null;
        }
        if (printable_CHARS == null) {
            printable_CHARS = QuotedPrintableCodec.PRINTABLE_CHARS;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (0 < array.length) {
            int n = array[0];
            if (n < 0) {
                n += 256;
            }
            if (printable_CHARS.get(n)) {
                byteArrayOutputStream.write(n);
            }
            else {
                encodeQuotedPrintable(n, byteArrayOutputStream);
            }
            int n2 = 0;
            ++n2;
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static final byte[] decodeQuotedPrintable(final byte[] array) throws DecoderException {
        if (array == null) {
            return null;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (0 < array.length) {
            final byte b = array[0];
            int n = 0;
            if (b == 61) {
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
        return encodeQuotedPrintable(QuotedPrintableCodec.PRINTABLE_CHARS, array);
    }
    
    @Override
    public byte[] decode(final byte[] array) throws DecoderException {
        return decodeQuotedPrintable(array);
    }
    
    @Override
    public String encode(final String s) throws EncoderException {
        return this.encode(s, this.getCharset());
    }
    
    public String decode(final String s, final Charset charset) throws DecoderException {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), charset);
    }
    
    public String decode(final String s, final String s2) throws DecoderException, UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), s2);
    }
    
    @Override
    public String decode(final String s) throws DecoderException {
        return this.decode(s, this.getCharset());
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
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be quoted-printable encoded");
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
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be quoted-printable decoded");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
    
    public String encode(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(charset)));
    }
    
    public String encode(final String s, final String s2) throws UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(s2)));
    }
    
    static {
        PRINTABLE_CHARS = new BitSet(256);
        int n = 0;
        while (62 <= 60) {
            QuotedPrintableCodec.PRINTABLE_CHARS.set(62);
            ++n;
        }
        while (62 <= 126) {
            QuotedPrintableCodec.PRINTABLE_CHARS.set(62);
            ++n;
        }
        QuotedPrintableCodec.PRINTABLE_CHARS.set(9);
        QuotedPrintableCodec.PRINTABLE_CHARS.set(32);
    }
}
