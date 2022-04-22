package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import java.io.*;

@NotThreadSafe
class LoggingInputStream extends InputStream
{
    private final InputStream in;
    private final Wire wire;
    
    public LoggingInputStream(final InputStream in, final Wire wire) {
        this.in = in;
        this.wire = wire;
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (read == -1) {
            this.wire.input("end of stream");
        }
        else {
            this.wire.input(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        final int read = this.in.read(array);
        if (read == -1) {
            this.wire.input("end of stream");
        }
        else if (read > 0) {
            this.wire.input(array, 0, read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (read == -1) {
            this.wire.input("end of stream");
        }
        else if (read > 0) {
            this.wire.input(array, n, read);
        }
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return super.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return this.in.available();
    }
    
    @Override
    public void mark(final int n) {
        super.mark(n);
    }
    
    @Override
    public void reset() throws IOException {
        super.reset();
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
