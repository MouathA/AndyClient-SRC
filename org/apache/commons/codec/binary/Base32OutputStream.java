package org.apache.commons.codec.binary;

import java.io.*;

public class Base32OutputStream extends BaseNCodecOutputStream
{
    public Base32OutputStream(final OutputStream outputStream) {
        this(outputStream, true);
    }
    
    public Base32OutputStream(final OutputStream outputStream, final boolean b) {
        super(outputStream, new Base32(false), b);
    }
    
    public Base32OutputStream(final OutputStream outputStream, final boolean b, final int n, final byte[] array) {
        super(outputStream, new Base32(n, array), b);
    }
}
