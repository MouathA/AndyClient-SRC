package com.google.common.hash;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;

@Beta
public final class HashingOutputStream extends FilterOutputStream
{
    private final Hasher hasher;
    
    public HashingOutputStream(final HashFunction hashFunction, final OutputStream outputStream) {
        super((OutputStream)Preconditions.checkNotNull(outputStream));
        this.hasher = (Hasher)Preconditions.checkNotNull(hashFunction.newHasher());
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.hasher.putByte((byte)n);
        this.out.write(n);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.hasher.putBytes(array, n, n2);
        this.out.write(array, n, n2);
    }
    
    public HashCode hash() {
        return this.hasher.hash();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
