package org.apache.commons.compress.archivers.tar;

import java.math.*;
import java.io.*;
import java.nio.*;
import org.apache.commons.compress.archivers.zip.*;

public class TarUtils
{
    private static final int BYTE_MASK = 255;
    static final ZipEncoding DEFAULT_ENCODING;
    static final ZipEncoding FALLBACK_ENCODING;
    
    private TarUtils() {
    }
    
    public static long parseOctal(final byte[] array, final int n, final int n2) {
        long n3 = 0L;
        int n4 = n + n2;
        int i = n;
        if (n2 < 2) {
            throw new IllegalArgumentException("Length " + n2 + " must be at least 2");
        }
        if (array[i] == 0) {
            return 0L;
        }
        while (i < n4 && array[i] == 32) {
            ++i;
        }
        for (byte b = array[n4 - 1]; i < n4 && (b == 0 || b == 32); --n4, b = array[n4 - 1]) {}
        while (i < n4) {
            final byte b2 = array[i];
            if (b2 < 48 || b2 > 55) {
                throw new IllegalArgumentException(exceptionMessage(array, n, n2, i, b2));
            }
            n3 = (n3 << 3) + (b2 - 48);
            ++i;
        }
        return n3;
    }
    
    public static long parseOctalOrBinary(final byte[] array, final int n, final int n2) {
        if ((array[n] & 0x80) == 0x0) {
            return parseOctal(array, n, n2);
        }
        final boolean b = array[n] == -1;
        if (n2 < 9) {
            return parseBinaryLong(array, n, n2, b);
        }
        return parseBinaryBigInteger(array, n, n2, b);
    }
    
    private static long parseBinaryLong(final byte[] array, final int n, final int n2, final boolean b) {
        if (n2 >= 9) {
            throw new IllegalArgumentException("At offset " + n + ", " + n2 + " byte binary number" + " exceeds maximum signed long" + " value");
        }
        long n3 = 0L;
        while (1 < n2) {
            n3 = (n3 << 8) + (array[n + 1] & 0xFF);
            int n4 = 0;
            ++n4;
        }
        if (b) {
            n3 = (n3 - 1L ^ (long)Math.pow(2.0, (n2 - 1) * 8) - 1L);
        }
        return b ? (-n3) : n3;
    }
    
    private static long parseBinaryBigInteger(final byte[] array, final int n, final int n2, final boolean b) {
        final byte[] array2 = new byte[n2 - 1];
        System.arraycopy(array, n + 1, array2, 0, n2 - 1);
        BigInteger not = new BigInteger(array2);
        if (b) {
            not = not.add(BigInteger.valueOf(-1L)).not();
        }
        if (not.bitLength() > 63) {
            throw new IllegalArgumentException("At offset " + n + ", " + n2 + " byte binary number" + " exceeds maximum signed long" + " value");
        }
        return b ? (-not.longValue()) : not.longValue();
    }
    
    public static boolean parseBoolean(final byte[] array, final int n) {
        return array[n] == 1;
    }
    
    private static String exceptionMessage(final byte[] array, final int n, final int n2, final int n3, final byte b) {
        return "Invalid byte " + b + " at offset " + (n3 - n) + " in '" + new String(array, n, n2).replaceAll("\u0000", "{NUL}") + "' len=" + n2;
    }
    
    public static String parseName(final byte[] array, final int n, final int n2) {
        return parseName(array, n, n2, TarUtils.DEFAULT_ENCODING);
    }
    
    public static String parseName(final byte[] array, final int n, final int n2, final ZipEncoding zipEncoding) throws IOException {
        int n3;
        for (n3 = n2; n3 > 0 && array[n + n3 - 1] == 0; --n3) {}
        if (n3 > 0) {
            final byte[] array2 = new byte[n3];
            System.arraycopy(array, n, array2, 0, n3);
            return zipEncoding.decode(array2);
        }
        return "";
    }
    
    public static int formatNameBytes(final String s, final byte[] array, final int n, final int n2) {
        return formatNameBytes(s, array, n, n2, TarUtils.DEFAULT_ENCODING);
    }
    
    public static int formatNameBytes(final String s, final byte[] array, final int n, final int n2, final ZipEncoding zipEncoding) throws IOException {
        int length;
        ByteBuffer byteBuffer;
        for (length = s.length(), byteBuffer = zipEncoding.encode(s); byteBuffer.limit() > n2 && length > 0; byteBuffer = zipEncoding.encode(s.substring(0, --length))) {}
        final int n3 = byteBuffer.limit() - byteBuffer.position();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), array, n, n3);
        for (int i = n3; i < n2; ++i) {
            array[n + i] = 0;
        }
        return n + n2;
    }
    
    public static void formatUnsignedOctalString(final long n, final byte[] array, final int n2, final int n3) {
        int i = n3;
        --i;
        if (n == 0L) {
            array[n2 + i--] = 48;
        }
        else {
            long n4;
            for (n4 = n; i >= 0 && n4 != 0L; n4 >>>= 3, --i) {
                array[n2 + i] = (byte)(48 + (byte)(n4 & 0x7L));
            }
            if (n4 != 0L) {
                throw new IllegalArgumentException(n + "=" + Long.toOctalString(n) + " will not fit in octal number buffer of length " + n3);
            }
        }
        while (i >= 0) {
            array[n2 + i] = 48;
            --i;
        }
    }
    
    public static int formatOctalBytes(final long n, final byte[] array, final int n2, final int n3) {
        int n4 = n3 - 2;
        formatUnsignedOctalString(n, array, n2, n4);
        array[n2 + n4++] = 32;
        array[n2 + n4] = 0;
        return n2 + n3;
    }
    
    public static int formatLongOctalBytes(final long n, final byte[] array, final int n2, final int n3) {
        final int n4 = n3 - 1;
        formatUnsignedOctalString(n, array, n2, n4);
        array[n2 + n4] = 32;
        return n2 + n3;
    }
    
    public static int formatLongOctalOrBinaryBytes(final long n, final byte[] array, final int n2, final int n3) {
        final long n4 = (n3 == 8) ? 2097151L : 8589934591L;
        final boolean b = n < 0L;
        if (!b && n <= n4) {
            return formatLongOctalBytes(n, array, n2, n3);
        }
        if (n3 < 9) {
            formatLongBinary(n, array, n2, n3, b);
        }
        formatBigIntegerBinary(n, array, n2, n3, b);
        array[n2] = (byte)(b ? 255 : 128);
        return n2 + n3;
    }
    
    private static void formatLongBinary(final long n, final byte[] array, final int n2, final int n3, final boolean b) {
        final int n4 = (n3 - 1) * 8;
        final long n5 = 1L << n4;
        long abs = Math.abs(n);
        if (abs >= n5) {
            throw new IllegalArgumentException("Value " + n + " is too large for " + n3 + " byte field.");
        }
        if (b) {
            abs = ((abs ^ n5 - 1L) | (long)(255 << n4)) + 1L;
        }
        for (int i = n2 + n3 - 1; i >= n2; --i) {
            array[i] = (byte)abs;
            abs >>= 8;
        }
    }
    
    private static void formatBigIntegerBinary(final long n, final byte[] array, final int n2, final int n3, final boolean b) {
        final byte[] byteArray = BigInteger.valueOf(n).toByteArray();
        final int length = byteArray.length;
        final int n4 = n2 + n3 - length;
        System.arraycopy(byteArray, 0, array, n4, length);
        final byte b2 = (byte)(b ? 255 : 0);
        for (int i = n2 + 1; i < n4; ++i) {
            array[i] = b2;
        }
    }
    
    public static int formatCheckSumOctalBytes(final long n, final byte[] array, final int n2, final int n3) {
        int n4 = n3 - 2;
        formatUnsignedOctalString(n, array, n2, n4);
        array[n2 + n4++] = 0;
        array[n2 + n4] = 32;
        return n2 + n3;
    }
    
    public static long computeCheckSum(final byte[] array) {
        long n = 0L;
        while (0 < array.length) {
            n += (0xFF & array[0]);
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static boolean verifyCheckSum(final byte[] array) {
        long n = 0L;
        long n2 = 0L;
        long n3 = 0L;
        while (0 < array.length) {
            final byte b = array[0];
            Label_0078: {
                if (148 <= 0 && 0 < 156) {
                    if (48 <= 32 && 32 <= 55) {
                        final int n4 = 6;
                        int n5 = 0;
                        ++n5;
                        if (n4 < 6) {
                            n = n * 8L + 32 - 48L;
                            break Label_0078;
                        }
                    }
                    if (6 > 0) {}
                }
            }
            n2 += 32;
            n3 += 32;
            int n6 = 0;
            ++n6;
        }
        return n == n2 || n == n3 || n > n2;
    }
    
    static {
        DEFAULT_ENCODING = ZipEncodingHelper.getZipEncoding(null);
        FALLBACK_ENCODING = new ZipEncoding() {
            public boolean canEncode(final String s) {
                return true;
            }
            
            public ByteBuffer encode(final String s) {
                final int length = s.length();
                final byte[] array = new byte[length];
                while (0 < length) {
                    array[0] = (byte)s.charAt(0);
                    int n = 0;
                    ++n;
                }
                return ByteBuffer.wrap(array);
            }
            
            public String decode(final byte[] array) {
                final int length = array.length;
                final StringBuilder sb = new StringBuilder(length);
                while (0 < length) {
                    final byte b = array[0];
                    if (b == 0) {
                        break;
                    }
                    sb.append((char)(b & 0xFF));
                    int n = 0;
                    ++n;
                }
                return sb.toString();
            }
        };
    }
}
