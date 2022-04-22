package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import java.io.*;

@NotThreadSafe
public class ChunkedOutputStream extends OutputStream
{
    private final SessionOutputBuffer out;
    private final byte[] cache;
    private int cachePosition;
    private boolean wroteLastChunk;
    private boolean closed;
    
    @Deprecated
    public ChunkedOutputStream(final SessionOutputBuffer sessionOutputBuffer, final int n) throws IOException {
        this(n, sessionOutputBuffer);
    }
    
    @Deprecated
    public ChunkedOutputStream(final SessionOutputBuffer sessionOutputBuffer) throws IOException {
        this(2048, sessionOutputBuffer);
    }
    
    public ChunkedOutputStream(final int n, final SessionOutputBuffer out) {
        this.cachePosition = 0;
        this.wroteLastChunk = false;
        this.closed = false;
        this.cache = new byte[n];
        this.out = out;
    }
    
    protected void flushCache() throws IOException {
        if (this.cachePosition > 0) {
            this.out.writeLine(Integer.toHexString(this.cachePosition));
            this.out.write(this.cache, 0, this.cachePosition);
            this.out.writeLine("");
            this.cachePosition = 0;
        }
    }
    
    protected void flushCacheWithAppend(final byte[] array, final int n, final int n2) throws IOException {
        this.out.writeLine(Integer.toHexString(this.cachePosition + n2));
        this.out.write(this.cache, 0, this.cachePosition);
        this.out.write(array, n, n2);
        this.out.writeLine("");
        this.cachePosition = 0;
    }
    
    protected void writeClosingChunk() throws IOException {
        this.out.writeLine("0");
        this.out.writeLine("");
    }
    
    public void finish() throws IOException {
        if (!this.wroteLastChunk) {
            this.flushCache();
            this.writeClosingChunk();
            this.wroteLastChunk = true;
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        this.cache[this.cachePosition] = (byte)n;
        ++this.cachePosition;
        if (this.cachePosition == this.cache.length) {
            this.flushCache();
        }
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        if (n2 >= this.cache.length - this.cachePosition) {
            this.flushCacheWithAppend(array, n, n2);
        }
        else {
            System.arraycopy(array, n, this.cache, this.cachePosition, n2);
            this.cachePosition += n2;
        }
    }
    
    @Override
    public void flush() throws IOException {
        this.flushCache();
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.finish();
            this.out.flush();
        }
    }
}
