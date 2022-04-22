package org.apache.commons.compress.archivers.zip;

import java.io.*;

class BitStream
{
    private final InputStream in;
    private long bitCache;
    private int bitCacheSize;
    private static final int[] MASKS;
    
    BitStream(final InputStream in) {
        this.in = in;
    }
    
    private boolean fillCache() throws IOException {
        boolean b = false;
        while (this.bitCacheSize <= 56) {
            final long n = this.in.read();
            if (n == -1L) {
                break;
            }
            b = true;
            this.bitCache |= n << this.bitCacheSize;
            this.bitCacheSize += 8;
        }
        return b;
    }
    
    int nextBit() throws IOException {
        if (this.bitCacheSize == 0 && !this.fillCache()) {
            return -1;
        }
        final int n = (int)(this.bitCache & 0x1L);
        this.bitCache >>>= 1;
        --this.bitCacheSize;
        return n;
    }
    
    int nextBits(final int n) throws IOException {
        if (this.bitCacheSize < n && !this.fillCache()) {
            return -1;
        }
        final int n2 = (int)(this.bitCache & (long)BitStream.MASKS[n]);
        this.bitCache >>>= n;
        this.bitCacheSize -= n;
        return n2;
    }
    
    int nextByte() throws IOException {
        return this.nextBits(8);
    }
    
    static {
        MASKS = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
    }
}
