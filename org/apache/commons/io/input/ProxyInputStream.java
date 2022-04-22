package org.apache.commons.io.input;

import java.io.*;

public abstract class ProxyInputStream extends FilterInputStream
{
    public ProxyInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public int read() throws IOException {
        this.beforeRead(1);
        final int read = this.in.read();
        this.afterRead((read != -1) ? 1 : -1);
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        this.beforeRead((array != null) ? array.length : 0);
        final int read = this.in.read(array);
        this.afterRead(read);
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        this.beforeRead(n2);
        final int read = this.in.read(array, n, n2);
        this.afterRead(read);
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.in.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return super.available();
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.in.mark(n);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.in.reset();
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    protected void beforeRead(final int n) throws IOException {
    }
    
    protected void afterRead(final int n) throws IOException {
    }
    
    protected void handleIOException(final IOException ex) throws IOException {
        throw ex;
    }
}
