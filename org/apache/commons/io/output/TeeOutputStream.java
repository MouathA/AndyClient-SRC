package org.apache.commons.io.output;

import java.io.*;

public class TeeOutputStream extends ProxyOutputStream
{
    protected OutputStream branch;
    
    public TeeOutputStream(final OutputStream outputStream, final OutputStream branch) {
        super(outputStream);
        this.branch = branch;
    }
    
    @Override
    public synchronized void write(final byte[] array) throws IOException {
        super.write(array);
        this.branch.write(array);
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        super.write(array, n, n2);
        this.branch.write(array, n, n2);
    }
    
    @Override
    public synchronized void write(final int n) throws IOException {
        super.write(n);
        this.branch.write(n);
    }
    
    @Override
    public void flush() throws IOException {
        super.flush();
        this.branch.flush();
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.branch.close();
    }
}
