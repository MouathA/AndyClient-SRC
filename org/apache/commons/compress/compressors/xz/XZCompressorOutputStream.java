package org.apache.commons.compress.compressors.xz;

import org.apache.commons.compress.compressors.*;
import org.tukaani.xz.*;
import java.io.*;

public class XZCompressorOutputStream extends CompressorOutputStream
{
    private final XZOutputStream out;
    
    public XZCompressorOutputStream(final OutputStream outputStream) throws IOException {
        this.out = new XZOutputStream(outputStream, (FilterOptions)new LZMA2Options());
    }
    
    public XZCompressorOutputStream(final OutputStream outputStream, final int n) throws IOException {
        this.out = new XZOutputStream(outputStream, (FilterOptions)new LZMA2Options(n));
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    public void finish() throws IOException {
        this.out.finish();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
