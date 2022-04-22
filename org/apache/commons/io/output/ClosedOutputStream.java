package org.apache.commons.io.output;

import java.io.*;

public class ClosedOutputStream extends OutputStream
{
    public static final ClosedOutputStream CLOSED_OUTPUT_STREAM;
    
    @Override
    public void write(final int n) throws IOException {
        throw new IOException("write(" + n + ") failed: stream is closed");
    }
    
    static {
        CLOSED_OUTPUT_STREAM = new ClosedOutputStream();
    }
}
