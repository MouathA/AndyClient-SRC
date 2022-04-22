package org.apache.commons.io.input;

import java.io.*;

public class BoundedInputStream extends InputStream
{
    private final InputStream in;
    private final long max;
    private long pos;
    private long mark;
    private boolean propagateClose;
    
    public BoundedInputStream(final InputStream in, final long max) {
        this.pos = 0L;
        this.mark = -1L;
        this.propagateClose = true;
        this.max = max;
        this.in = in;
    }
    
    public BoundedInputStream(final InputStream inputStream) {
        this(inputStream, -1L);
    }
    
    @Override
    public int read() throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return -1;
        }
        final int read = this.in.read();
        ++this.pos;
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return -1;
        }
        final int read = this.in.read(array, n, (int)((this.max >= 0L) ? Math.min(n2, this.max - this.pos) : n2));
        if (read == -1) {
            return -1;
        }
        this.pos += read;
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        final long skip = this.in.skip((this.max >= 0L) ? Math.min(n, this.max - this.pos) : n);
        this.pos += skip;
        return skip;
    }
    
    @Override
    public int available() throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return 0;
        }
        return this.in.available();
    }
    
    @Override
    public String toString() {
        return this.in.toString();
    }
    
    @Override
    public void close() throws IOException {
        if (this.propagateClose) {
            this.in.close();
        }
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.in.reset();
        this.pos = this.mark;
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.in.mark(n);
        this.mark = this.pos;
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    public boolean isPropagateClose() {
        return this.propagateClose;
    }
    
    public void setPropagateClose(final boolean propagateClose) {
        this.propagateClose = propagateClose;
    }
}
