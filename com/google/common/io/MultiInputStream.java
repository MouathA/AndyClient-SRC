package com.google.common.io;

import java.util.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;

final class MultiInputStream extends InputStream
{
    private Iterator it;
    private InputStream in;
    
    public MultiInputStream(final Iterator iterator) throws IOException {
        this.it = (Iterator)Preconditions.checkNotNull(iterator);
        this.advance();
    }
    
    @Override
    public void close() throws IOException {
        if (this.in != null) {
            this.in.close();
            this.in = null;
        }
    }
    
    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.in = this.it.next().openStream();
        }
    }
    
    @Override
    public int available() throws IOException {
        if (this.in == null) {
            return 0;
        }
        return this.in.available();
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public int read() throws IOException {
        if (this.in == null) {
            return -1;
        }
        final int read = this.in.read();
        if (read == -1) {
            this.advance();
            return this.read();
        }
        return read;
    }
    
    @Override
    public int read(@Nullable final byte[] array, final int n, final int n2) throws IOException {
        if (this.in == null) {
            return -1;
        }
        final int read = this.in.read(array, n, n2);
        if (read == -1) {
            this.advance();
            return this.read(array, n, n2);
        }
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (this.in == null || n <= 0L) {
            return 0L;
        }
        final long skip = this.in.skip(n);
        if (skip != 0L) {
            return skip;
        }
        if (this.read() == -1) {
            return 0L;
        }
        return 1L + this.in.skip(n - 1L);
    }
}
