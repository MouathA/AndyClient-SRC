package org.apache.commons.compress.compressors.xz;

import org.apache.commons.compress.compressors.*;
import java.io.*;
import org.tukaani.xz.*;

public class XZCompressorInputStream extends CompressorInputStream
{
    private final InputStream in;
    
    public static boolean matches(final byte[] array, final int n) {
        if (n < XZ.HEADER_MAGIC.length) {
            return false;
        }
        while (0 < XZ.HEADER_MAGIC.length) {
            if (array[0] != XZ.HEADER_MAGIC[0]) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public XZCompressorInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, false);
    }
    
    public XZCompressorInputStream(final InputStream inputStream, final boolean b) throws IOException {
        if (b) {
            this.in = (InputStream)new XZInputStream(inputStream);
        }
        else {
            this.in = (InputStream)new SingleXZInputStream(inputStream);
        }
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        this.count((read == -1) ? -1 : 1);
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
