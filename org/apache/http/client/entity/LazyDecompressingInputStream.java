package org.apache.http.client.entity;

import org.apache.http.annotation.*;
import java.io.*;

@NotThreadSafe
class LazyDecompressingInputStream extends InputStream
{
    private final InputStream wrappedStream;
    private final DecompressingEntity decompressingEntity;
    private InputStream wrapperStream;
    
    public LazyDecompressingInputStream(final InputStream wrappedStream, final DecompressingEntity decompressingEntity) {
        this.wrappedStream = wrappedStream;
        this.decompressingEntity = decompressingEntity;
    }
    
    private void initWrapper() throws IOException {
        if (this.wrapperStream == null) {
            this.wrapperStream = this.decompressingEntity.decorate(this.wrappedStream);
        }
    }
    
    @Override
    public int read() throws IOException {
        this.initWrapper();
        return this.wrapperStream.read();
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        this.initWrapper();
        return this.wrapperStream.read(array);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        this.initWrapper();
        return this.wrapperStream.read(array, n, n2);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        this.initWrapper();
        return this.wrapperStream.skip(n);
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public int available() throws IOException {
        this.initWrapper();
        return this.wrapperStream.available();
    }
    
    @Override
    public void close() throws IOException {
        if (this.wrapperStream != null) {
            this.wrapperStream.close();
        }
        this.wrappedStream.close();
    }
}
