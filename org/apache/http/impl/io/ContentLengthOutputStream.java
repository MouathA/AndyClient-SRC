package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class ContentLengthOutputStream extends OutputStream
{
    private final SessionOutputBuffer out;
    private final long contentLength;
    private long total;
    private boolean closed;
    
    public ContentLengthOutputStream(final SessionOutputBuffer sessionOutputBuffer, final long n) {
        this.total = 0L;
        this.closed = false;
        this.out = (SessionOutputBuffer)Args.notNull(sessionOutputBuffer, "Session output buffer");
        this.contentLength = Args.notNegative(n, "Content length");
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.out.flush();
        }
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        if (this.total < this.contentLength) {
            final long n3 = this.contentLength - this.total;
            int n4 = n2;
            if (n4 > n3) {
                n4 = (int)n3;
            }
            this.out.write(array, n, n4);
            this.total += n4;
        }
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        if (this.total < this.contentLength) {
            this.out.write(n);
            ++this.total;
        }
    }
}
