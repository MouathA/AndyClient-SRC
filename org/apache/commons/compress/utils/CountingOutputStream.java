package org.apache.commons.compress.utils;

import java.io.*;

public class CountingOutputStream extends FilterOutputStream
{
    private long bytesWritten;
    
    public CountingOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.bytesWritten = 0L;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
        this.count(1L);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.count(n2);
    }
    
    protected void count(final long n) {
        if (n != -1L) {
            this.bytesWritten += n;
        }
    }
    
    public long getBytesWritten() {
        return this.bytesWritten;
    }
}
