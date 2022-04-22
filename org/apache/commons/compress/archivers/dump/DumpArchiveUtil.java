package org.apache.commons.compress.archivers.dump;

import org.apache.commons.compress.archivers.zip.*;
import java.io.*;

class DumpArchiveUtil
{
    private DumpArchiveUtil() {
    }
    
    public static int calculateChecksum(final byte[] array) {
        while (true) {
            final int n = 0 + convert32(array, 0);
            int n2 = 0;
            ++n2;
        }
    }
    
    public static final boolean verify(final byte[] array) {
        return convert32(array, 24) == 60012 && convert32(array, 28) == calculateChecksum(array);
    }
    
    public static final int getIno(final byte[] array) {
        return convert32(array, 20);
    }
    
    public static final long convert64(final byte[] array, final int n) {
        return 0L + ((long)array[n + 7] << 56) + ((long)array[n + 6] << 48 & 0xFF000000000000L) + ((long)array[n + 5] << 40 & 0xFF0000000000L) + ((long)array[n + 4] << 32 & 0xFF00000000L) + ((long)array[n + 3] << 24 & 0xFF000000L) + ((long)array[n + 2] << 16 & 0xFF0000L) + ((long)array[n + 1] << 8 & 0xFF00L) + ((long)array[n] & 0xFFL);
    }
    
    public static final int convert32(final byte[] array, final int n) {
        final int n2 = array[n + 3] << 24;
        final int n3 = 0 + (array[n + 2] << 16 & 0xFF0000);
        final int n4 = 0 + (array[n + 1] << 8 & 0xFF00);
        final int n5 = 0 + (array[n] & 0xFF);
        return 0;
    }
    
    public static final int convert16(final byte[] array, final int n) {
        final int n2 = 0 + (array[n + 1] << 8 & 0xFF00);
        final int n3 = 0 + (array[n] & 0xFF);
        return 0;
    }
    
    static String decode(final ZipEncoding zipEncoding, final byte[] array, final int n, final int n2) throws IOException {
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return zipEncoding.decode(array2);
    }
}
