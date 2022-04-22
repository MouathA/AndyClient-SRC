package com.google.common.hash;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;

@Beta
public final class HashingInputStream extends FilterInputStream
{
    private final Hasher hasher;
    
    public HashingInputStream(final HashFunction hashFunction, final InputStream inputStream) {
        super((InputStream)Preconditions.checkNotNull(inputStream));
        this.hasher = (Hasher)Preconditions.checkNotNull(hashFunction.newHasher());
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (read != -1) {
            this.hasher.putByte((byte)read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (read != -1) {
            this.hasher.putBytes(array, n, read);
        }
        return read;
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public void mark(final int n) {
    }
    
    @Override
    public void reset() throws IOException {
        throw new IOException("reset not supported");
    }
    
    public HashCode hash() {
        return this.hasher.hash();
    }
}
