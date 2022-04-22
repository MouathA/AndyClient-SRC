package org.apache.commons.compress.compressors.snappy;

import org.apache.commons.compress.compressors.*;
import java.io.*;
import org.apache.commons.compress.utils.*;
import java.util.*;

public class FramedSnappyCompressorInputStream extends CompressorInputStream
{
    static final long MASK_OFFSET = 2726488792L;
    private static final int STREAM_IDENTIFIER_TYPE = 255;
    private static final int COMPRESSED_CHUNK_TYPE = 0;
    private static final int UNCOMPRESSED_CHUNK_TYPE = 1;
    private static final int PADDING_CHUNK_TYPE = 254;
    private static final int MIN_UNSKIPPABLE_TYPE = 2;
    private static final int MAX_UNSKIPPABLE_TYPE = 127;
    private static final int MAX_SKIPPABLE_TYPE = 253;
    private static final byte[] SZ_SIGNATURE;
    private final PushbackInputStream in;
    private SnappyCompressorInputStream currentCompressedChunk;
    private final byte[] oneByte;
    private boolean endReached;
    private boolean inUncompressedChunk;
    private int uncompressedBytesRemaining;
    private long expectedChecksum;
    private final PureJavaCrc32C checksum;
    
    public FramedSnappyCompressorInputStream(final InputStream inputStream) throws IOException {
        this.oneByte = new byte[1];
        this.expectedChecksum = -1L;
        this.checksum = new PureJavaCrc32C();
        this.in = new PushbackInputStream(inputStream, 1);
        this.readStreamIdentifier();
    }
    
    @Override
    public int read() throws IOException {
        return (this.read(this.oneByte, 0, 1) == -1) ? -1 : (this.oneByte[0] & 0xFF);
    }
    
    @Override
    public void close() throws IOException {
        if (this.currentCompressedChunk != null) {
            this.currentCompressedChunk.close();
            this.currentCompressedChunk = null;
        }
        this.in.close();
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        int n3 = this.readOnce(array, n, n2);
        if (n3 == -1) {
            this.readNextBlock();
            if (this.endReached) {
                return -1;
            }
            n3 = this.readOnce(array, n, n2);
        }
        return n3;
    }
    
    @Override
    public int available() throws IOException {
        if (this.inUncompressedChunk) {
            return Math.min(this.uncompressedBytesRemaining, this.in.available());
        }
        if (this.currentCompressedChunk != null) {
            return this.currentCompressedChunk.available();
        }
        return 0;
    }
    
    private int readOnce(final byte[] array, final int n, final int n2) throws IOException {
        if (this.inUncompressedChunk) {
            final int min = Math.min(this.uncompressedBytesRemaining, n2);
            if (min == 0) {
                return -1;
            }
            this.in.read(array, n, min);
            if (-1 != -1) {
                ++this.uncompressedBytesRemaining;
                this.count(-1);
            }
        }
        else if (this.currentCompressedChunk != null) {
            final long bytesRead = this.currentCompressedChunk.getBytesRead();
            this.currentCompressedChunk.read(array, n, n2);
            if (-1 == -1) {
                this.currentCompressedChunk.close();
                this.currentCompressedChunk = null;
            }
            else {
                this.count(this.currentCompressedChunk.getBytesRead() - bytesRead);
            }
        }
        if (-1 > 0) {
            this.checksum.update(array, n, -1);
        }
        return -1;
    }
    
    private void readNextBlock() throws IOException {
        this.verifyLastChecksumAndReset();
        this.inUncompressedChunk = false;
        final int oneByte = this.readOneByte();
        if (oneByte == -1) {
            this.endReached = true;
        }
        else if (oneByte == 255) {
            this.in.unread(oneByte);
            this.pushedBackBytes(1L);
            this.readStreamIdentifier();
            this.readNextBlock();
        }
        else if (oneByte == 254 || (oneByte > 127 && oneByte <= 253)) {
            this.skipBlock();
            this.readNextBlock();
        }
        else {
            if (oneByte >= 2 && oneByte <= 127) {
                throw new IOException("unskippable chunk with type " + oneByte + " (hex " + Integer.toHexString(oneByte) + ")" + " detected.");
            }
            if (oneByte == 1) {
                this.inUncompressedChunk = true;
                this.uncompressedBytesRemaining = this.readSize() - 4;
                this.expectedChecksum = unmask(this.readCrc());
            }
            else {
                if (oneByte != 0) {
                    throw new IOException("unknown chunk type " + oneByte + " detected.");
                }
                final long n = this.readSize() - 4;
                this.expectedChecksum = unmask(this.readCrc());
                this.currentCompressedChunk = new SnappyCompressorInputStream(new BoundedInputStream(this.in, n));
                this.count(this.currentCompressedChunk.getBytesRead());
            }
        }
    }
    
    private long readCrc() throws IOException {
        final byte[] array = new byte[4];
        final int fully = IOUtils.readFully(this.in, array);
        this.count(fully);
        if (fully != 4) {
            throw new IOException("premature end of stream");
        }
        long n = 0L;
        while (0 < 4) {
            n |= ((long)array[0] & 0xFFL) << 0;
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    static long unmask(long n) {
        n -= 2726488792L;
        n &= 0xFFFFFFFFL;
        return (n >> 17 | n << 15) & 0xFFFFFFFFL;
    }
    
    private int readSize() throws IOException {
        while (0 < 3) {
            this.readOneByte();
            if (0 == -1) {
                throw new IOException("premature end of stream");
            }
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    private void skipBlock() throws IOException {
        final int size = this.readSize();
        final long skip = IOUtils.skip(this.in, size);
        this.count(skip);
        if (skip != size) {
            throw new IOException("premature end of stream");
        }
    }
    
    private void readStreamIdentifier() throws IOException {
        final byte[] array = new byte[10];
        final int fully = IOUtils.readFully(this.in, array);
        this.count(fully);
        if (10 != fully || !matches(array, 10)) {
            throw new IOException("Not a framed Snappy stream");
        }
    }
    
    private int readOneByte() throws IOException {
        final int read = this.in.read();
        if (read != -1) {
            this.count(1);
            return read & 0xFF;
        }
        return -1;
    }
    
    private void verifyLastChecksumAndReset() throws IOException {
        if (this.expectedChecksum >= 0L && this.expectedChecksum != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        this.expectedChecksum = -1L;
        this.checksum.reset();
    }
    
    public static boolean matches(final byte[] array, final int n) {
        if (n < FramedSnappyCompressorInputStream.SZ_SIGNATURE.length) {
            return false;
        }
        byte[] array2 = array;
        if (array.length > FramedSnappyCompressorInputStream.SZ_SIGNATURE.length) {
            array2 = new byte[FramedSnappyCompressorInputStream.SZ_SIGNATURE.length];
            System.arraycopy(array, 0, array2, 0, FramedSnappyCompressorInputStream.SZ_SIGNATURE.length);
        }
        return Arrays.equals(array2, FramedSnappyCompressorInputStream.SZ_SIGNATURE);
    }
    
    static {
        SZ_SIGNATURE = new byte[] { -1, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
    }
}
