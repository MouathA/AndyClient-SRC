package com.google.common.io;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;

@Beta
public final class CountingOutputStream extends FilterOutputStream
{
    private long count;
    
    public CountingOutputStream(@Nullable final OutputStream outputStream) {
        super(outputStream);
    }
    
    public long getCount() {
        return this.count;
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.count += n2;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
        ++this.count;
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
