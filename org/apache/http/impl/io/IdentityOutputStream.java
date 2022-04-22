package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class IdentityOutputStream extends OutputStream
{
    private final SessionOutputBuffer out;
    private boolean closed;
    
    public IdentityOutputStream(final SessionOutputBuffer sessionOutputBuffer) {
        this.closed = false;
        this.out = (SessionOutputBuffer)Args.notNull(sessionOutputBuffer, "Session output buffer");
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
        this.out.write(array, n, n2);
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
        this.out.write(n);
    }
}
