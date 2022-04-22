package org.apache.commons.compress.archivers;

import java.io.*;

public abstract class ArchiveInputStream extends InputStream
{
    private final byte[] SINGLE;
    private static final int BYTE_MASK = 255;
    private long bytesRead;
    
    public ArchiveInputStream() {
        this.SINGLE = new byte[1];
        this.bytesRead = 0L;
    }
    
    public abstract ArchiveEntry getNextEntry() throws IOException;
    
    @Override
    public int read() throws IOException {
        return (this.read(this.SINGLE, 0, 1) == -1) ? -1 : (this.SINGLE[0] & 0xFF);
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
    
    public boolean canReadEntryData(final ArchiveEntry archiveEntry) {
        return true;
    }
}
