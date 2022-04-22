package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.io.*;
import java.io.*;

@NotThreadSafe
public class IdentityInputStream extends InputStream
{
    private final SessionInputBuffer in;
    private boolean closed;
    
    public IdentityInputStream(final SessionInputBuffer sessionInputBuffer) {
        this.closed = false;
        this.in = (SessionInputBuffer)Args.notNull(sessionInputBuffer, "Session input buffer");
    }
    
    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return ((BufferInfo)this.in).length();
        }
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
    }
    
    @Override
    public int read() throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.in.read();
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.in.read(array, n, n2);
    }
}
