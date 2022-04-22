package com.ibm.icu.impl;

import java.io.*;

class ICUBinaryStream extends DataInputStream
{
    public ICUBinaryStream(final InputStream inputStream, final int n) {
        super(inputStream);
        this.mark(n);
    }
    
    public ICUBinaryStream(final byte[] array) {
        this(new ByteArrayInputStream(array), array.length);
    }
    
    public void seek(final int n) throws IOException {
        this.reset();
        final int skipBytes = this.skipBytes(n);
        if (skipBytes != n) {
            throw new IllegalStateException("Skip(" + n + ") only skipped " + skipBytes + " bytes");
        }
    }
}
