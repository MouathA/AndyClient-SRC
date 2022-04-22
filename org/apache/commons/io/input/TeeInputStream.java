package org.apache.commons.io.input;

import java.io.*;

public class TeeInputStream extends ProxyInputStream
{
    private final OutputStream branch;
    private final boolean closeBranch;
    
    public TeeInputStream(final InputStream inputStream, final OutputStream outputStream) {
        this(inputStream, outputStream, false);
    }
    
    public TeeInputStream(final InputStream inputStream, final OutputStream branch, final boolean closeBranch) {
        super(inputStream);
        this.branch = branch;
        this.closeBranch = closeBranch;
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        if (this.closeBranch) {
            this.branch.close();
        }
    }
    
    @Override
    public int read() throws IOException {
        final int read = super.read();
        if (read != -1) {
            this.branch.write(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = super.read(array, n, n2);
        if (read != -1) {
            this.branch.write(array, n, read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        final int read = super.read(array);
        if (read != -1) {
            this.branch.write(array, 0, read);
        }
        return read;
    }
}
