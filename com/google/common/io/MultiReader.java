package com.google.common.io;

import java.util.*;
import java.io.*;
import javax.annotation.*;
import com.google.common.base.*;

class MultiReader extends Reader
{
    private final Iterator it;
    private Reader current;
    
    MultiReader(final Iterator it) throws IOException {
        this.it = it;
        this.advance();
    }
    
    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.current = this.it.next().openStream();
        }
    }
    
    @Override
    public int read(@Nullable final char[] array, final int n, final int n2) throws IOException {
        if (this.current == null) {
            return -1;
        }
        final int read = this.current.read(array, n, n2);
        if (read == -1) {
            this.advance();
            return this.read(array, n, n2);
        }
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        Preconditions.checkArgument(n >= 0L, (Object)"n is negative");
        if (n > 0L) {
            while (this.current != null) {
                final long skip = this.current.skip(n);
                if (skip > 0L) {
                    return skip;
                }
                this.advance();
            }
        }
        return 0L;
    }
    
    @Override
    public boolean ready() throws IOException {
        return this.current != null && this.current.ready();
    }
    
    @Override
    public void close() throws IOException {
        if (this.current != null) {
            this.current.close();
            this.current = null;
        }
    }
}
