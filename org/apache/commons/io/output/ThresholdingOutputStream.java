package org.apache.commons.io.output;

import java.io.*;

public abstract class ThresholdingOutputStream extends OutputStream
{
    private final int threshold;
    private long written;
    private boolean thresholdExceeded;
    
    public ThresholdingOutputStream(final int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.checkThreshold(1);
        this.getStream().write(n);
        ++this.written;
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.checkThreshold(array.length);
        this.getStream().write(array);
        this.written += array.length;
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.checkThreshold(n2);
        this.getStream().write(array, n, n2);
        this.written += n2;
    }
    
    @Override
    public void flush() throws IOException {
        this.getStream().flush();
    }
    
    @Override
    public void close() throws IOException {
        this.flush();
        this.getStream().close();
    }
    
    public int getThreshold() {
        return this.threshold;
    }
    
    public long getByteCount() {
        return this.written;
    }
    
    public boolean isThresholdExceeded() {
        return this.written > this.threshold;
    }
    
    protected void checkThreshold(final int n) throws IOException {
        if (!this.thresholdExceeded && this.written + n > this.threshold) {
            this.thresholdExceeded = true;
            this.thresholdReached();
        }
    }
    
    protected void resetByteCount() {
        this.thresholdExceeded = false;
        this.written = 0L;
    }
    
    protected abstract OutputStream getStream() throws IOException;
    
    protected abstract void thresholdReached() throws IOException;
}
