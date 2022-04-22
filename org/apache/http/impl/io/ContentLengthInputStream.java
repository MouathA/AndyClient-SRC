package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.io.*;
import org.apache.http.*;

@NotThreadSafe
public class ContentLengthInputStream extends InputStream
{
    private static final int BUFFER_SIZE = 2048;
    private final long contentLength;
    private long pos;
    private boolean closed;
    private SessionInputBuffer in;
    
    public ContentLengthInputStream(final SessionInputBuffer sessionInputBuffer, final long n) {
        this.pos = 0L;
        this.closed = false;
        this.in = null;
        this.in = (SessionInputBuffer)Args.notNull(sessionInputBuffer, "Session input buffer");
        this.contentLength = Args.notNegative(n, "Content length");
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            if (this.pos < this.contentLength) {
                while (this.read(new byte[2048]) >= 0) {}
            }
            this.closed = true;
        }
    }
    
    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return Math.min(((BufferInfo)this.in).length(), (int)(this.contentLength - this.pos));
        }
        return 0;
    }
    
    @Override
    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.pos >= this.contentLength) {
            return -1;
        }
        final int read = this.in.read();
        if (read == -1) {
            if (this.pos < this.contentLength) {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
            }
        }
        else {
            ++this.pos;
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.pos >= this.contentLength) {
            return -1;
        }
        int n3 = n2;
        if (this.pos + n2 > this.contentLength) {
            n3 = (int)(this.contentLength - this.pos);
        }
        final int read = this.in.read(array, n, n3);
        if (read == -1 && this.pos < this.contentLength) {
            throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
        }
        if (read > 0) {
            this.pos += read;
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n <= 0L) {
            return 0L;
        }
        final byte[] array = new byte[2048];
        long min = Math.min(n, this.contentLength - this.pos);
        long n2 = 0L;
        while (min > 0L) {
            final int read = this.read(array, 0, (int)Math.min(2048L, min));
            if (read == -1) {
                break;
            }
            n2 += read;
            min -= read;
        }
        return n2;
    }
}
