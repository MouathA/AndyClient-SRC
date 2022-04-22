package com.google.common.io;

import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;

class AppendableWriter extends Writer
{
    private final Appendable target;
    private boolean closed;
    
    AppendableWriter(final Appendable appendable) {
        this.target = (Appendable)Preconditions.checkNotNull(appendable);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(new String(array, n, n2));
    }
    
    @Override
    public void flush() throws IOException {
        this.checkNotClosed();
        if (this.target instanceof Flushable) {
            ((Flushable)this.target).flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
        if (this.target instanceof Closeable) {
            ((Closeable)this.target).close();
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.checkNotClosed();
        this.target.append((char)n);
    }
    
    @Override
    public void write(@Nullable final String s) throws IOException {
        this.checkNotClosed();
        this.target.append(s);
    }
    
    @Override
    public void write(@Nullable final String s, final int n, final int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(s, n, n + n2);
    }
    
    @Override
    public Writer append(final char c) throws IOException {
        this.checkNotClosed();
        this.target.append(c);
        return this;
    }
    
    @Override
    public Writer append(@Nullable final CharSequence charSequence) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence);
        return this;
    }
    
    @Override
    public Writer append(@Nullable final CharSequence charSequence, final int n, final int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence, n, n2);
        return this;
    }
    
    private void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Cannot write to a closed writer.");
        }
    }
    
    @Override
    public Appendable append(final char c) throws IOException {
        return this.append(c);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}
