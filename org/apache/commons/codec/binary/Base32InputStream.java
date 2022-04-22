package org.apache.commons.codec.binary;

import java.io.*;

public class Base32InputStream extends BaseNCodecInputStream
{
    public Base32InputStream(final InputStream inputStream) {
        this(inputStream, false);
    }
    
    public Base32InputStream(final InputStream inputStream, final boolean b) {
        super(inputStream, new Base32(false), b);
    }
    
    public Base32InputStream(final InputStream inputStream, final boolean b, final int n, final byte[] array) {
        super(inputStream, new Base32(n, array), b);
    }
}
