package org.apache.commons.io.output;

import java.io.*;

public class CountingOutputStream extends ProxyOutputStream
{
    private long count;
    
    public CountingOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.count = 0L;
    }
    
    @Override
    protected synchronized void beforeWrite(final int n) {
        this.count += n;
    }
    
    public int getCount() {
        final long byteCount = this.getByteCount();
        if (byteCount > 2147483647L) {
            throw new ArithmeticException("The byte count " + byteCount + " is too large to be converted to an int");
        }
        return (int)byteCount;
    }
    
    public int resetCount() {
        final long resetByteCount = this.resetByteCount();
        if (resetByteCount > 2147483647L) {
            throw new ArithmeticException("The byte count " + resetByteCount + " is too large to be converted to an int");
        }
        return (int)resetByteCount;
    }
    
    public synchronized long getByteCount() {
        return this.count;
    }
    
    public synchronized long resetByteCount() {
        final long count = this.count;
        this.count = 0L;
        return count;
    }
}
