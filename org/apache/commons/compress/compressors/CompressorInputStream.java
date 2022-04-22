package org.apache.commons.compress.compressors;

import java.io.*;

public abstract class CompressorInputStream extends InputStream
{
    private long bytesRead;
    
    public CompressorInputStream() {
        this.bytesRead = 0L;
    }
    
    protected void count(final int n) {
        this.count((long)n);
    }
    
    protected void count(final long n) {
        if (n != -1L) {
            this.bytesRead += n;
        }
    }
    
    protected void pushedBackBytes(final long n) {
        this.bytesRead -= n;
    }
    
    @Deprecated
    public int getCount() {
        return (int)this.bytesRead;
    }
    
    public long getBytesRead() {
        return this.bytesRead;
    }
}
