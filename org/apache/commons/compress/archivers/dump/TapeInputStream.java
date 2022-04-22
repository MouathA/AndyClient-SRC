package org.apache.commons.compress.archivers.dump;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.apache.commons.compress.utils.*;

class TapeInputStream extends FilterInputStream
{
    private byte[] blockBuffer;
    private int currBlkIdx;
    private int blockSize;
    private static final int recordSize = 1024;
    private int readOffset;
    private boolean isCompressed;
    private long bytesRead;
    
    public TapeInputStream(final InputStream inputStream) {
        super(inputStream);
        this.blockBuffer = new byte[1024];
        this.currBlkIdx = -1;
        this.blockSize = 1024;
        this.readOffset = 1024;
        this.isCompressed = false;
        this.bytesRead = 0L;
    }
    
    public void resetBlockSize(final int n, final boolean isCompressed) throws IOException {
        this.isCompressed = isCompressed;
        this.blockSize = 1024 * n;
        System.arraycopy(this.blockBuffer, 0, this.blockBuffer = new byte[this.blockSize], 0, 1024);
        this.readFully(this.blockBuffer, 1024, this.blockSize - 1024);
        this.currBlkIdx = 0;
        this.readOffset = 1024;
    }
    
    @Override
    public int available() throws IOException {
        if (this.readOffset < this.blockSize) {
            return this.blockSize - this.readOffset;
        }
        return this.in.available();
    }
    
    @Override
    public int read() throws IOException {
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }
    
    @Override
    public int read(final byte[] array, int n, final int n2) throws IOException {
        if (n2 % 1024 != 0) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        while (0 < n2) {
            if (this.readOffset == this.blockSize && !this.readBlock(true)) {
                return -1;
            }
            if (this.readOffset + (n2 - 0) > this.blockSize) {
                final int n3 = this.blockSize - this.readOffset;
            }
            System.arraycopy(this.blockBuffer, this.readOffset, array, n, 0);
            this.readOffset += 0;
            n += 0;
        }
        return 0;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n % 1024L != 0L) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        long n2;
        long n3;
        for (n2 = 0L; n2 < n; n2 += n3) {
            if (this.readOffset == this.blockSize && !this.readBlock(n - n2 < this.blockSize)) {
                return -1L;
            }
            if (this.readOffset + (n - n2) <= this.blockSize) {
                n3 = n - n2;
            }
            else {
                n3 = this.blockSize - this.readOffset;
            }
            this.readOffset += (int)n3;
        }
        return n2;
    }
    
    @Override
    public void close() throws IOException {
        if (this.in != null && this.in != System.in) {
            this.in.close();
        }
    }
    
    public byte[] peek() throws IOException {
        if (this.readOffset == this.blockSize && !this.readBlock(true)) {
            return null;
        }
        final byte[] array = new byte[1024];
        System.arraycopy(this.blockBuffer, this.readOffset, array, 0, array.length);
        return array;
    }
    
    public byte[] readRecord() throws IOException {
        final byte[] array = new byte[1024];
        if (-1 == this.read(array, 0, array.length)) {
            throw new ShortFileException();
        }
        return array;
    }
    
    private boolean readBlock(final boolean b) throws IOException {
        if (this.in == null) {
            throw new IOException("input buffer is closed");
        }
        if (!this.isCompressed || this.currBlkIdx == -1) {
            this.readFully(this.blockBuffer, 0, this.blockSize);
            this.bytesRead += this.blockSize;
        }
        else {
            if (!this.readFully(this.blockBuffer, 0, 4)) {
                return false;
            }
            this.bytesRead += 4L;
            final int convert32 = DumpArchiveUtil.convert32(this.blockBuffer, 0);
            if ((convert32 & 0x1) != 0x1) {
                this.readFully(this.blockBuffer, 0, this.blockSize);
                this.bytesRead += this.blockSize;
            }
            else {
                final int n = convert32 >> 1 & 0x7;
                final int n2 = convert32 >> 4 & 0xFFFFFFF;
                final byte[] array = new byte[n2];
                this.readFully(array, 0, n2);
                this.bytesRead += n2;
                if (!b) {
                    Arrays.fill(this.blockBuffer, (byte)0);
                }
                else {
                    switch (DumpArchiveConstants.COMPRESSION_TYPE.find(n & 0x3)) {
                        case ZLIB: {
                            final Inflater inflater = new Inflater();
                            inflater.setInput(array, 0, array.length);
                            if (inflater.inflate(this.blockBuffer) != this.blockSize) {
                                throw new ShortFileException();
                            }
                            inflater.end();
                            break;
                        }
                        case BZLIB: {
                            throw new UnsupportedCompressionAlgorithmException("BZLIB2");
                        }
                        case LZO: {
                            throw new UnsupportedCompressionAlgorithmException("LZO");
                        }
                        default: {
                            throw new UnsupportedCompressionAlgorithmException();
                        }
                    }
                }
            }
        }
        ++this.currBlkIdx;
        this.readOffset = 0;
        return true;
    }
    
    private boolean readFully(final byte[] array, final int n, final int n2) throws IOException {
        if (IOUtils.readFully(this.in, array, n, n2) < n2) {
            throw new ShortFileException();
        }
        return true;
    }
    
    public long getBytesRead() {
        return this.bytesRead;
    }
}
