package org.apache.commons.codec.binary;

import java.nio.charset.*;
import org.apache.commons.codec.*;

public class Hex implements BinaryEncoder, BinaryDecoder
{
    public static final Charset DEFAULT_CHARSET;
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;
    private final Charset charset;
    
    public static byte[] decodeHex(final char[] array) throws DecoderException {
        final int length = array.length;
        if ((length & 0x1) != 0x0) {
            throw new DecoderException("Odd number of characters.");
        }
        final byte[] array2 = new byte[length >> 1];
        while (0 < length) {
            final int n = toDigit(array[0], 0) << 4;
            int n2 = 0;
            ++n2;
            final int n3 = n | toDigit(array[0], 0);
            ++n2;
            array2[0] = (byte)(n3 & 0xFF);
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public static char[] encodeHex(final byte[] array) {
        return encodeHex(array, true);
    }
    
    public static char[] encodeHex(final byte[] array, final boolean b) {
        return encodeHex(array, b ? Hex.DIGITS_LOWER : Hex.DIGITS_UPPER);
    }
    
    protected static char[] encodeHex(final byte[] array, final char[] array2) {
        final int length = array.length;
        final char[] array3 = new char[length << 1];
        while (0 < length) {
            final char[] array4 = array3;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array4[n] = array2[(0xF0 & array[0]) >>> 4];
            final char[] array5 = array3;
            final int n3 = 0;
            ++n2;
            array5[n3] = array2[0xF & array[0]];
            int n4 = 0;
            ++n4;
        }
        return array3;
    }
    
    public static String encodeHexString(final byte[] array) {
        return new String(encodeHex(array));
    }
    
    protected static int toDigit(final char c, final int n) throws DecoderException {
        final int digit = Character.digit(c, 16);
        if (digit == -1) {
            throw new DecoderException("Illegal hexadecimal character " + c + " at index " + n);
        }
        return digit;
    }
    
    public Hex() {
        this.charset = Hex.DEFAULT_CHARSET;
    }
    
    public Hex(final Charset charset) {
        this.charset = charset;
    }
    
    public Hex(final String s) {
        this(Charset.forName(s));
    }
    
    @Override
    public byte[] decode(final byte[] array) throws DecoderException {
        return decodeHex(new String(array, this.getCharset()).toCharArray());
    }
    
    @Override
    public Object decode(final Object o) throws DecoderException {
        return decodeHex((o instanceof String) ? ((String)o).toCharArray() : ((char[])o));
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        return encodeHexString(array).getBytes(this.getCharset());
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        return encodeHex((o instanceof String) ? ((String)o).getBytes(this.getCharset()) : ((byte[])o));
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getCharsetName() {
        return this.charset.name();
    }
    
    @Override
    public String toString() {
        return super.toString() + "[charsetName=" + this.charset + "]";
    }
    
    static {
        DEFAULT_CHARSET = Charsets.UTF_8;
        DIGITS_LOWER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        DIGITS_UPPER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
}
