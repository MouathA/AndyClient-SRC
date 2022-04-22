package org.yaml.snakeyaml.external.biz.base64Coder;

public class Base64Coder
{
    private static final String systemLineSeparator;
    private static char[] map1;
    private static byte[] map2;
    
    public static String encodeString(final String s) {
        return new String(encode(s.getBytes()));
    }
    
    public static String encodeLines(final byte[] array) {
        return encodeLines(array, 0, array.length, 76, Base64Coder.systemLineSeparator);
    }
    
    public static String encodeLines(final byte[] array, final int n, final int n2, final int n3, final String s) {
        final int n4 = n3 * 3 / 4;
        if (n4 <= 0) {
            throw new IllegalArgumentException();
        }
        final StringBuilder sb = new StringBuilder((n2 + 2) / 3 * 4 + (n2 + n4 - 1) / n4 * s.length());
        while (0 < n2) {
            sb.append(encode(array, n + 0, Math.min(n2 - 0, n4)));
            sb.append(s);
        }
        return sb.toString();
    }
    
    public static char[] encode(final byte[] array) {
        return encode(array, 0, array.length);
    }
    
    public static char[] encode(final byte[] array, final int n) {
        return encode(array, 0, n);
    }
    
    public static char[] encode(final byte[] array, final int n, final int n2) {
        final int n3 = (n2 * 4 + 2) / 3;
        final char[] array2 = new char[(n2 + 2) / 3 * 4];
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        char[] array3;
        int n12;
        int n13 = 0;
        char[] array4;
        int n14;
        for (int i = n, n4 = n + n2; i < n4; n5 = (array[i++] & 0xFF), n6 = ((i < n4) ? (array[i++] & 0xFF) : 0), n7 = ((i < n4) ? (array[i++] & 0xFF) : 0), n8 = n5 >>> 2, n9 = ((n5 & 0x3) << 4 | n6 >>> 4), n10 = ((n6 & 0xF) << 2 | n7 >>> 6), n11 = (n7 & 0x3F), array3 = array2, n12 = 0, ++n13, array3[n12] = Base64Coder.map1[n8], array4 = array2, n14 = 0, ++n13, array4[n14] = Base64Coder.map1[n9], array2[0] = ((0 < n3) ? Base64Coder.map1[n10] : '='), ++n13, array2[0] = ((0 < n3) ? Base64Coder.map1[n11] : '='), ++n13) {}
        return array2;
    }
    
    public static String decodeString(final String s) {
        return new String(decode(s));
    }
    
    public static byte[] decodeLines(final String s) {
        final char[] array = new char[s.length()];
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 != ' ' && char1 != '\r' && char1 != '\n' && char1 != '\t') {
                final char[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = char1;
            }
            int n3 = 0;
            ++n3;
        }
        return decode(array, 0, 0);
    }
    
    public static byte[] decode(final String s) {
        return decode(s.toCharArray());
    }
    
    public static byte[] decode(final char[] array) {
        return decode(array, 0, array.length);
    }
    
    public static byte[] decode(final char[] array, final int n, int n2) {
        if (n2 % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (n2 > 0 && array[n + n2 - 1] == '=') {
            --n2;
        }
        final int n3 = n2 * 3 / 4;
        final byte[] array2 = new byte[n3];
        int i = n;
        final int n4 = n + n2;
        while (i < n4) {
            final char c = array[i++];
            final char c2 = array[i++];
            final char c3 = (i < n4) ? array[i++] : 'A';
            final char c4 = (i < n4) ? array[i++] : 'A';
            if (c > '\u007f' || c2 > '\u007f' || c3 > '\u007f' || c4 > '\u007f') {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            final byte b = Base64Coder.map2[c];
            final byte b2 = Base64Coder.map2[c2];
            final byte b3 = Base64Coder.map2[c3];
            final byte b4 = Base64Coder.map2[c4];
            if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            final int n5 = b << 2 | b2 >>> 4;
            final int n6 = (b2 & 0xF) << 4 | b3 >>> 2;
            final int n7 = (b3 & 0x3) << 6 | b4;
            final byte[] array3 = array2;
            final int n8 = 0;
            int n9 = 0;
            ++n9;
            array3[n8] = (byte)n5;
            if (0 < n3) {
                final byte[] array4 = array2;
                final int n10 = 0;
                ++n9;
                array4[n10] = (byte)n6;
            }
            if (0 >= n3) {
                continue;
            }
            final byte[] array5 = array2;
            final int n11 = 0;
            ++n9;
            array5[n11] = (byte)n7;
        }
        return array2;
    }
    
    private Base64Coder() {
    }
    
    static {
        systemLineSeparator = System.getProperty("line.separator");
        Base64Coder.map1 = new char[64];
        while (true) {
            final char[] map1 = Base64Coder.map1;
            final int n = 0;
            int n2 = 0;
            ++n2;
            map1[n] = '0';
            final char c = 49;
        }
    }
}
