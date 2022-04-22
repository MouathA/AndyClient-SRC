package org.apache.commons.compress.compressors.lzma;

import org.apache.commons.compress.compressors.*;
import org.tukaani.xz.*;
import java.io.*;

public class LZMACompressorInputStream extends CompressorInputStream
{
    private final InputStream in;
    
    public LZMACompressorInputStream(final InputStream inputStream) throws IOException {
        this.in = (InputStream)new LZMAInputStream(inputStream);
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        this.count((read != -1) ? 1 : 0);
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        this.count(read);
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.in.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return this.in.available();
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
