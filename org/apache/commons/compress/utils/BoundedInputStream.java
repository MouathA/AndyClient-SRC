package org.apache.commons.compress.utils;

import java.io.*;

public class BoundedInputStream extends InputStream
{
    private final InputStream in;
    private long bytesRemaining;
    
    public BoundedInputStream(final InputStream in, final long bytesRemaining) {
        this.in = in;
        this.bytesRemaining = bytesRemaining;
    }
    
    @Override
    public int read() throws IOException {
        if (this.bytesRemaining > 0L) {
            --this.bytesRemaining;
            return this.in.read();
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.bytesRemaining == 0L) {
            return -1;
        }
        int n3 = n2;
        if (n3 > this.bytesRemaining) {
            n3 = (int)this.bytesRemaining;
        }
        final int read = this.in.read(array, n, n3);
        if (read >= 0) {
            this.bytesRemaining -= read;
        }
        return read;
    }
    
    @Override
    public void close() {
    }
}
