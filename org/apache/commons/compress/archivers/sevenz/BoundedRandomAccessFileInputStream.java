package org.apache.commons.compress.archivers.sevenz;

import java.io.*;

class BoundedRandomAccessFileInputStream extends InputStream
{
    private final RandomAccessFile file;
    private long bytesRemaining;
    
    public BoundedRandomAccessFileInputStream(final RandomAccessFile file, final long bytesRemaining) {
        this.file = file;
        this.bytesRemaining = bytesRemaining;
    }
    
    @Override
    public int read() throws IOException {
        if (this.bytesRemaining > 0L) {
            --this.bytesRemaining;
            return this.file.read();
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
        final int read = this.file.read(array, n, n3);
        if (read >= 0) {
            this.bytesRemaining -= read;
        }
        return read;
    }
    
    @Override
    public void close() {
    }
}
