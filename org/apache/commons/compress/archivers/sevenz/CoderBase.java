package org.apache.commons.compress.archivers.sevenz;

import java.io.*;

abstract class CoderBase
{
    private final Class[] acceptableOptions;
    private static final byte[] NONE;
    
    protected CoderBase(final Class... acceptableOptions) {
        this.acceptableOptions = acceptableOptions;
    }
    
    boolean canAcceptOptions(final Object o) {
        final Class[] acceptableOptions = this.acceptableOptions;
        while (0 < acceptableOptions.length) {
            if (acceptableOptions[0].isInstance(o)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    byte[] getOptionsAsProperties(final Object o) {
        return CoderBase.NONE;
    }
    
    Object getOptionsFromCoder(final Coder coder, final InputStream inputStream) {
        return null;
    }
    
    abstract InputStream decode(final InputStream p0, final Coder p1, final byte[] p2) throws IOException;
    
    OutputStream encode(final OutputStream outputStream, final Object o) throws IOException {
        throw new UnsupportedOperationException("method doesn't support writing");
    }
    
    protected static int numberOptionOrDefault(final Object o, final int n) {
        return (o instanceof Number) ? ((Number)o).intValue() : n;
    }
    
    static {
        NONE = new byte[0];
    }
}
