package org.apache.commons.io.output;

import java.io.*;

public class NullOutputStream extends OutputStream
{
    public static final NullOutputStream NULL_OUTPUT_STREAM;
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
    }
    
    @Override
    public void write(final int n) {
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
    }
    
    static {
        NULL_OUTPUT_STREAM = new NullOutputStream();
    }
}
