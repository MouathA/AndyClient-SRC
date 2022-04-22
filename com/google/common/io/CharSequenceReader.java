package com.google.common.io;

import com.google.common.base.*;
import java.io.*;
import java.nio.*;

final class CharSequenceReader extends Reader
{
    private CharSequence seq;
    private int pos;
    private int mark;
    
    public CharSequenceReader(final CharSequence charSequence) {
        this.seq = (CharSequence)Preconditions.checkNotNull(charSequence);
    }
    
    private void checkOpen() throws IOException {
        if (this.seq == null) {
            throw new IOException("reader closed");
        }
    }
    
    private boolean hasRemaining() {
        return this.remaining() > 0;
    }
    
    private int remaining() {
        return this.seq.length() - this.pos;
    }
    
    @Override
    public synchronized int read(final CharBuffer charBuffer) throws IOException {
        Preconditions.checkNotNull(charBuffer);
        this.checkOpen();
        if (!this.hasRemaining()) {
            return -1;
        }
        final int min = Math.min(charBuffer.remaining(), this.remaining());
        while (0 < min) {
            charBuffer.put(this.seq.charAt(this.pos++));
            int n = 0;
            ++n;
        }
        return min;
    }
    
    @Override
    public synchronized int read() throws IOException {
        this.checkOpen();
        return this.hasRemaining() ? this.seq.charAt(this.pos++) : -1;
    }
    
    @Override
    public synchronized int read(final char[] array, final int n, final int n2) throws IOException {
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        this.checkOpen();
        if (!this.hasRemaining()) {
            return -1;
        }
        final int min = Math.min(n2, this.remaining());
        while (0 < min) {
            array[n + 0] = this.seq.charAt(this.pos++);
            int n3 = 0;
            ++n3;
        }
        return min;
    }
    
    @Override
    public synchronized long skip(final long n) throws IOException {
        Preconditions.checkArgument(n >= 0L, "n (%s) may not be negative", n);
        this.checkOpen();
        final int n2 = (int)Math.min(this.remaining(), n);
        this.pos += n2;
        return n2;
    }
    
    @Override
    public synchronized boolean ready() throws IOException {
        this.checkOpen();
        return true;
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public synchronized void mark(final int n) throws IOException {
        Preconditions.checkArgument(n >= 0, "readAheadLimit (%s) may not be negative", n);
        this.checkOpen();
        this.mark = this.pos;
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.checkOpen();
        this.pos = this.mark;
    }
    
    @Override
    public synchronized void close() throws IOException {
        this.seq = null;
    }
}
