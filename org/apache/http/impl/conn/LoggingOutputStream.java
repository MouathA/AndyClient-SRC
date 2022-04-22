package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import java.io.*;

@NotThreadSafe
class LoggingOutputStream extends OutputStream
{
    private final OutputStream out;
    private final Wire wire;
    
    public LoggingOutputStream(final OutputStream out, final Wire wire) {
        this.out = out;
        this.wire = wire;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.wire.output(n);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.wire.output(array);
        this.out.write(array);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.wire.output(array, n, n2);
        this.out.write(array, n, n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
