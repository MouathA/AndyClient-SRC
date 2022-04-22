package org.apache.http.impl.auth;

import java.security.*;
import java.io.*;

class HttpEntityDigester extends OutputStream
{
    private final MessageDigest digester;
    private boolean closed;
    private byte[] digest;
    
    HttpEntityDigester(final MessageDigest digester) {
        (this.digester = digester).reset();
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.closed) {
            throw new IOException("Stream has been already closed");
        }
        this.digester.update((byte)n);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Stream has been already closed");
        }
        this.digester.update(array, n, n2);
    }
    
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.digest = this.digester.digest();
        super.close();
    }
    
    public byte[] getDigest() {
        return this.digest;
    }
}
