package org.apache.commons.codec.binary;

import java.io.*;

public class BaseNCodecInputStream extends FilterInputStream
{
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte;
    private final BaseNCodec.Context context;
    
    protected BaseNCodecInputStream(final InputStream inputStream, final BaseNCodec baseNCodec, final boolean doEncode) {
        super(inputStream);
        this.singleByte = new byte[1];
        this.context = new BaseNCodec.Context();
        this.doEncode = doEncode;
        this.baseNCodec = baseNCodec;
    }
    
    @Override
    public int available() throws IOException {
        return this.context.eof ? 0 : 1;
    }
    
    @Override
    public synchronized void mark(final int n) {
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public int read() throws IOException {
        int i;
        for (i = this.read(this.singleByte, 0, 1); i == 0; i = this.read(this.singleByte, 0, 1)) {}
        if (i > 0) {
            final byte b = this.singleByte[0];
            return (b < 0) ? (256 + b) : b;
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n > array.length || n + n2 > array.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        while (true) {
            if (!this.baseNCodec.hasData(this.context)) {
                final byte[] array2 = new byte[this.doEncode ? 4096 : 8192];
                final int read = this.in.read(array2);
                if (this.doEncode) {
                    this.baseNCodec.encode(array2, 0, read, this.context);
                }
                else {
                    this.baseNCodec.decode(array2, 0, read, this.context);
                }
            }
            this.baseNCodec.readResults(array, n, n2, this.context);
        }
    }
    
    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative skip length: " + n);
        }
        final byte[] array = new byte[512];
        long n2;
        int read;
        for (n2 = n; n2 > 0L; n2 -= read) {
            read = this.read(array, 0, (int)Math.min(array.length, n2));
            if (read == -1) {
                break;
            }
        }
        return n - n2;
    }
}
