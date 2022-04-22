package com.google.common.io;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;

@Beta
public final class CountingInputStream extends FilterInputStream
{
    private long count;
    private long mark;
    
    public CountingInputStream(@Nullable final InputStream inputStream) {
        super(inputStream);
        this.mark = -1L;
    }
    
    public long getCount() {
        return this.count;
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (read != -1) {
            ++this.count;
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (read != -1) {
            this.count += read;
        }
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        final long skip = this.in.skip(n);
        this.count += skip;
        return skip;
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.in.mark(n);
        this.mark = this.count;
    }
    
    @Override
    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        if (this.mark == -1L) {
            throw new IOException("Mark not set");
        }
        this.in.reset();
        this.count = this.mark;
    }
}
