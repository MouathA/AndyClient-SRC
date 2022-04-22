package org.apache.commons.io.input;

import java.io.*;

public class CountingInputStream extends ProxyInputStream
{
    private long count;
    
    public CountingInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public synchronized long skip(final long n) throws IOException {
        final long skip = super.skip(n);
        this.count += skip;
        return skip;
    }
    
    @Override
    protected synchronized void afterRead(final int n) {
        if (n != -1) {
            this.count += n;
        }
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
