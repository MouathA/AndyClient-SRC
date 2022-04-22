package org.apache.commons.io.input;

import java.io.*;

public class AutoCloseInputStream extends ProxyInputStream
{
    public AutoCloseInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
        this.in = new ClosedInputStream();
    }
    
    @Override
    protected void afterRead(final int n) throws IOException {
        if (n == -1) {
            this.close();
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
