package org.apache.commons.compress.utils;

import java.io.*;
import java.util.zip.*;

public class CRC32VerifyingInputStream extends ChecksumVerifyingInputStream
{
    public CRC32VerifyingInputStream(final InputStream inputStream, final long n, final int n2) {
        this(inputStream, n, (long)n2 & 0xFFFFFFFFL);
    }
    
    public CRC32VerifyingInputStream(final InputStream inputStream, final long n, final long n2) {
        super(new CRC32(), inputStream, n, n2);
    }
}
