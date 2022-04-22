package org.apache.http.client.entity;

import java.io.*;
import java.util.zip.*;

public class DeflateInputStream extends InputStream
{
    private InputStream sourceStream;
    
    public DeflateInputStream(final InputStream inputStream) throws IOException {
        final byte[] input = new byte[6];
        final PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, input.length);
        final int read = pushbackInputStream.read(input);
        if (read == -1) {
            throw new IOException("Unable to read the response");
        }
        final byte[] array = { 0 };
        final Inflater inflater = new Inflater();
        int inflate;
        while ((inflate = inflater.inflate(array)) == 0) {
            if (inflater.finished()) {
                throw new IOException("Unable to read the response");
            }
            if (inflater.needsDictionary()) {
                break;
            }
            if (!inflater.needsInput()) {
                continue;
            }
            inflater.setInput(input);
        }
        if (inflate == -1) {
            throw new IOException("Unable to read the response");
        }
        pushbackInputStream.unread(input, 0, read);
        this.sourceStream = new DeflateStream(pushbackInputStream, new Inflater());
        inflater.end();
    }
    
    @Override
    public int read() throws IOException {
        return this.sourceStream.read();
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.sourceStream.read(array);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.sourceStream.read(array, n, n2);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.sourceStream.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return this.sourceStream.available();
    }
    
    @Override
    public void mark(final int n) {
        this.sourceStream.mark(n);
    }
    
    @Override
    public void reset() throws IOException {
        this.sourceStream.reset();
    }
    
    @Override
    public boolean markSupported() {
        return this.sourceStream.markSupported();
    }
    
    @Override
    public void close() throws IOException {
        this.sourceStream.close();
    }
    
    static class DeflateStream extends InflaterInputStream
    {
        private boolean closed;
        
        public DeflateStream(final InputStream inputStream, final Inflater inflater) {
            super(inputStream, inflater);
            this.closed = false;
        }
        
        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.inf.end();
            super.close();
        }
    }
}
