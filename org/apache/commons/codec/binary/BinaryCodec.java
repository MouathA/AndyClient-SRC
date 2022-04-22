package org.apache.commons.codec.binary;

import org.apache.commons.codec.*;

public class BinaryCodec implements BinaryDecoder, BinaryEncoder
{
    private static final char[] EMPTY_CHAR_ARRAY;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final int BIT_0 = 1;
    private static final int BIT_1 = 2;
    private static final int BIT_2 = 4;
    private static final int BIT_3 = 8;
    private static final int BIT_4 = 16;
    private static final int BIT_5 = 32;
    private static final int BIT_6 = 64;
    private static final int BIT_7 = 128;
    private static final int[] BITS;
    
    @Override
    public byte[] encode(final byte[] array) {
        return toAsciiBytes(array);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof byte[])) {
            throw new EncoderException("argument not a byte array");
        }
        return toAsciiChars((byte[])o);
    }
    
    @Override
    public Object decode(final Object o) throws DecoderException {
        if (o == null) {
            return BinaryCodec.EMPTY_BYTE_ARRAY;
        }
        if (o instanceof byte[]) {
            return fromAscii((byte[])o);
        }
        if (o instanceof char[]) {
            return fromAscii((char[])o);
        }
        if (o instanceof String) {
            return fromAscii(((String)o).toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }
    
    @Override
    public byte[] decode(final byte[] array) {
        return fromAscii(array);
    }
    
    public byte[] toByteArray(final String s) {
        if (s == null) {
            return BinaryCodec.EMPTY_BYTE_ARRAY;
        }
        return fromAscii(s.toCharArray());
    }
    
    public static byte[] fromAscii(final char[] array) {
        if (array == null || array.length == 0) {
            return BinaryCodec.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[array.length >> 3];
        for (int i = 0, n = array.length - 1; i < array2.length; ++i, n -= 8) {
            for (int j = 0; j < BinaryCodec.BITS.length; ++j) {
                if (array[n - j] == '1') {
                    final byte[] array3 = array2;
                    final int n2 = i;
                    array3[n2] |= (byte)BinaryCodec.BITS[j];
                }
            }
        }
        return array2;
    }
    
    public static byte[] fromAscii(final byte[] array) {
        if (isEmpty(array)) {
            return BinaryCodec.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[array.length >> 3];
        for (int i = 0, n = array.length - 1; i < array2.length; ++i, n -= 8) {
            for (int j = 0; j < BinaryCodec.BITS.length; ++j) {
                if (array[n - j] == 49) {
                    final byte[] array3 = array2;
                    final int n2 = i;
                    array3[n2] |= (byte)BinaryCodec.BITS[j];
                }
            }
        }
        return array2;
    }
    
    private static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }
    
    public static byte[] toAsciiBytes(final byte[] array) {
        if (isEmpty(array)) {
            return BinaryCodec.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[array.length << 3];
        for (int i = 0, n = array2.length - 1; i < array.length; ++i, n -= 8) {
            for (int j = 0; j < BinaryCodec.BITS.length; ++j) {
                if ((array[i] & BinaryCodec.BITS[j]) == 0x0) {
                    array2[n - j] = 48;
                }
                else {
                    array2[n - j] = 49;
                }
            }
        }
        return array2;
    }
    
    public static char[] toAsciiChars(final byte[] array) {
        if (isEmpty(array)) {
            return BinaryCodec.EMPTY_CHAR_ARRAY;
        }
        final char[] array2 = new char[array.length << 3];
        for (int i = 0, n = array2.length - 1; i < array.length; ++i, n -= 8) {
            for (int j = 0; j < BinaryCodec.BITS.length; ++j) {
                if ((array[i] & BinaryCodec.BITS[j]) == 0x0) {
                    array2[n - j] = '0';
                }
                else {
                    array2[n - j] = '1';
                }
            }
        }
        return array2;
    }
    
    public static String toAsciiString(final byte[] array) {
        return new String(toAsciiChars(array));
    }
    
    static {
        EMPTY_CHAR_ARRAY = new char[0];
        EMPTY_BYTE_ARRAY = new byte[0];
        BITS = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };
    }
}
