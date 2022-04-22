package org.apache.commons.compress.utils;

import java.io.*;

public class CountingInputStream extends FilterInputStream
{
    private long bytesRead;
    
    public CountingInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (read >= 0) {
            this.count(1L);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (read >= 0) {
            this.count(read);
        }
        return read;
    }
    
    protected final void count(final long n) {
        if (n != -1L) {
            this.bytesRead += n;
        }
    }
    
    public long getBytesRead() {
        return this.bytesRead;
    }
}
