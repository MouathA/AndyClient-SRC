package org.apache.commons.io.output;

import java.io.*;

public class ProxyOutputStream extends FilterOutputStream
{
    public ProxyOutputStream(final OutputStream outputStream) {
        super(outputStream);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.beforeWrite(1);
        this.out.write(n);
        this.afterWrite(1);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        final int n = (array != null) ? array.length : 0;
        this.beforeWrite(n);
        this.out.write(array);
        this.afterWrite(n);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.beforeWrite(n2);
        this.out.write(array, n, n2);
        this.afterWrite(n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
    
    protected void beforeWrite(final int n) throws IOException {
    }
    
    protected void afterWrite(final int n) throws IOException {
    }
    
    protected void handleIOException(final IOException ex) throws IOException {
        throw ex;
    }
}
