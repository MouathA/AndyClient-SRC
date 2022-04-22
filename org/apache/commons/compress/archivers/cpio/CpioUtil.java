package org.apache.commons.compress.archivers.cpio;

class CpioUtil
{
    static long fileType(final long n) {
        return n & 0xF000L;
    }
    
    static long byteArray2long(final byte[] array, final boolean b) {
        if (array.length % 2 != 0) {
            throw new UnsupportedOperationException();
        }
        final byte[] array2 = new byte[array.length];
        System.arraycopy(array, 0, array2, 0, array.length);
        int n2 = 0;
        if (!b) {
            while (1 < array2.length) {
                final byte b2 = array2[1];
                final byte[] array3 = array2;
                final int n = 1;
                ++n2;
                array3[n] = array2[1];
                array2[1] = 0;
                ++n2;
            }
        }
        long n3 = array2[0] & 0xFF;
        while (1 < array2.length) {
            n3 = (n3 << 8 | (long)(array2[1] & 0xFF));
            ++n2;
        }
        return n3;
    }
    
    static byte[] long2byteArray(final long n, final int n2, final boolean b) {
        final byte[] array = new byte[n2];
        if (n2 % 2 != 0 || n2 < 2) {
            throw new UnsupportedOperationException();
        }
        long n3 = n;
        int n4 = n2 - 1;
        while (true) {
            array[0] = (byte)(n3 & 0xFFL);
            n3 >>= 8;
            --n4;
        }
    }
}
