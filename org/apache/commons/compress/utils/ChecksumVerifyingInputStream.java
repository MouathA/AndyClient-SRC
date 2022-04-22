package org.apache.commons.compress.utils;

import java.util.zip.*;
import java.io.*;

public class ChecksumVerifyingInputStream extends InputStream
{
    private final InputStream in;
    private long bytesRemaining;
    private final long expectedChecksum;
    private final Checksum checksum;
    
    public ChecksumVerifyingInputStream(final Checksum checksum, final InputStream in, final long bytesRemaining, final long expectedChecksum) {
        this.checksum = checksum;
        this.in = in;
        this.expectedChecksum = expectedChecksum;
        this.bytesRemaining = bytesRemaining;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (read >= 0) {
            this.checksum.update(array, n, read);
            this.bytesRemaining -= read;
        }
        if (this.bytesRemaining <= 0L && this.expectedChecksum != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        return read;
    }
    
    @Override
    public long skip(final long p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: lcmp           
        //     2: iflt            7
        //     5: lconst_1       
        //     6: lreturn        
        //     7: lconst_0       
        //     8: lreturn        
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
