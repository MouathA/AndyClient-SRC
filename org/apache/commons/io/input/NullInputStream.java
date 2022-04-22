package org.apache.commons.io.input;

import java.io.*;

public class NullInputStream extends InputStream
{
    private final long size;
    private long position;
    private long mark;
    private long readlimit;
    private boolean eof;
    private final boolean throwEofException;
    private final boolean markSupported;
    
    public NullInputStream(final long n) {
        this(n, true, false);
    }
    
    public NullInputStream(final long size, final boolean markSupported, final boolean throwEofException) {
        this.mark = -1L;
        this.size = size;
        this.markSupported = markSupported;
        this.throwEofException = throwEofException;
    }
    
    public long getPosition() {
        return this.position;
    }
    
    public long getSize() {
        return this.size;
    }
    
    @Override
    public int available() {
        final long n = this.size - this.position;
        if (n <= 0L) {
            return 0;
        }
        if (n > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int)n;
    }
    
    @Override
    public void close() throws IOException {
        this.eof = false;
        this.position = 0L;
        this.mark = -1L;
    }
    
    @Override
    public synchronized void mark(final int n) {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        this.mark = this.position;
        this.readlimit = n;
    }
    
    @Override
    public boolean markSupported() {
        return this.markSupported;
    }
    
    @Override
    public int read() throws IOException {
        if (this.eof) {
            throw new IOException("Read after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        ++this.position;
        return this.processByte();
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.eof) {
            throw new IOException("Read after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        this.position += n2;
        int n3 = n2;
        if (this.position > this.size) {
            n3 = n2 - (int)(this.position - this.size);
            this.position = this.size;
        }
        this.processBytes(array, n, n3);
        return n3;
    }
    
    @Override
    public synchronized void reset() throws IOException {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        if (this.mark < 0L) {
            throw new IOException("No position has been marked");
        }
        if (this.position > this.mark + this.readlimit) {
            throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
        }
        this.position = this.mark;
        this.eof = false;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (this.eof) {
            throw new IOException("Skip after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        this.position += n;
        long n2 = n;
        if (this.position > this.size) {
            n2 = n - (this.position - this.size);
            this.position = this.size;
        }
        return n2;
    }
    
    protected int processByte() {
        return 0;
    }
    
    protected void processBytes(final byte[] array, final int n, final int n2) {
    }
    
    private int doEndOfFile() throws EOFException {
        this.eof = true;
        if (this.throwEofException) {
            throw new EOFException();
        }
        return -1;
    }
}
