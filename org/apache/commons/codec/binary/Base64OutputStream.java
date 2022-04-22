package org.apache.commons.codec.binary;

import java.io.*;

public class Base64OutputStream extends BaseNCodecOutputStream
{
    public Base64OutputStream(final OutputStream outputStream) {
        this(outputStream, true);
    }
    
    public Base64OutputStream(final OutputStream outputStream, final boolean b) {
        super(outputStream, new Base64(false), b);
    }
    
    public Base64OutputStream(final OutputStream outputStream, final boolean b, final int n, final byte[] array) {
        super(outputStream, new Base64(n, array), b);
    }
}
