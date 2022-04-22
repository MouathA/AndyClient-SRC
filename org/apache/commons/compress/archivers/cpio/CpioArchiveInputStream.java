package org.apache.commons.compress.archivers.cpio;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.utils.*;
import java.io.*;
import org.apache.commons.compress.archivers.*;

public class CpioArchiveInputStream extends ArchiveInputStream implements CpioConstants
{
    private boolean closed;
    private CpioArchiveEntry entry;
    private long entryBytesRead;
    private boolean entryEOF;
    private final byte[] tmpbuf;
    private long crc;
    private final InputStream in;
    private final byte[] TWO_BYTES_BUF;
    private final byte[] FOUR_BYTES_BUF;
    private final byte[] SIX_BYTES_BUF;
    private final int blockSize;
    private final ZipEncoding encoding;
    
    public CpioArchiveInputStream(final InputStream inputStream) {
        this(inputStream, 512, "US-ASCII");
    }
    
    public CpioArchiveInputStream(final InputStream inputStream, final String s) {
        this(inputStream, 512, s);
    }
    
    public CpioArchiveInputStream(final InputStream inputStream, final int n) {
        this(inputStream, n, "US-ASCII");
    }
    
    public CpioArchiveInputStream(final InputStream in, final int blockSize, final String s) {
        this.closed = false;
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.tmpbuf = new byte[4096];
        this.crc = 0L;
        this.TWO_BYTES_BUF = new byte[2];
        this.FOUR_BYTES_BUF = new byte[4];
        this.SIX_BYTES_BUF = new byte[6];
        this.in = in;
        this.blockSize = blockSize;
        this.encoding = ZipEncodingHelper.getZipEncoding(s);
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        if (this.entryEOF) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.in.close();
            this.closed = true;
        }
    }
    
    private void closeEntry() throws IOException {
        while (this.skip(2147483647L) == 2147483647L) {}
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public CpioArchiveEntry getNextCPIOEntry() throws IOException {
        this.ensureOpen();
        if (this.entry != null) {
            this.closeEntry();
        }
        this.readFully(this.TWO_BYTES_BUF, 0, this.TWO_BYTES_BUF.length);
        if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, false) == 29127L) {
            this.entry = this.readOldBinaryEntry(false);
        }
        else if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, true) == 29127L) {
            this.entry = this.readOldBinaryEntry(true);
        }
        else {
            System.arraycopy(this.TWO_BYTES_BUF, 0, this.SIX_BYTES_BUF, 0, this.TWO_BYTES_BUF.length);
            this.readFully(this.SIX_BYTES_BUF, this.TWO_BYTES_BUF.length, this.FOUR_BYTES_BUF.length);
            final String asciiString = ArchiveUtils.toAsciiString(this.SIX_BYTES_BUF);
            if (asciiString.equals("070701")) {
                this.entry = this.readNewEntry(false);
            }
            else if (asciiString.equals("070702")) {
                this.entry = this.readNewEntry(true);
            }
            else {
                if (!asciiString.equals("070707")) {
                    throw new IOException("Unknown magic [" + asciiString + "]. Occured at byte: " + this.getBytesRead());
                }
                this.entry = this.readOldAsciiEntry();
            }
        }
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.crc = 0L;
        if (this.entry.getName().equals("TRAILER!!!")) {
            this.entryEOF = true;
            this.skipRemainderOfLastBlock();
            return null;
        }
        return this.entry;
    }
    
    private void skip(final int n) throws IOException {
        if (n > 0) {
            this.readFully(this.FOUR_BYTES_BUF, 0, n);
        }
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        if (this.entry == null || this.entryEOF) {
            return -1;
        }
        if (this.entryBytesRead == this.entry.getSize()) {
            this.skip(this.entry.getDataPadCount());
            this.entryEOF = true;
            if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
                throw new IOException("CRC Error. Occured at byte: " + this.getBytesRead());
            }
            return -1;
        }
        else {
            final int n3 = (int)Math.min(n2, this.entry.getSize() - this.entryBytesRead);
            if (n3 < 0) {
                return -1;
            }
            final int fully = this.readFully(array, n, n3);
            if (this.entry.getFormat() == 2) {
                while (0 < fully) {
                    this.crc += (array[0] & 0xFF);
                    int n4 = 0;
                    ++n4;
                }
            }
            this.entryBytesRead += fully;
            return fully;
        }
    }
    
    private final int readFully(final byte[] array, final int n, final int n2) throws IOException {
        final int fully = IOUtils.readFully(this.in, array, n, n2);
        this.count(fully);
        if (fully < n2) {
            throw new EOFException();
        }
        return fully;
    }
    
    private long readBinaryLong(final int n, final boolean b) throws IOException {
        final byte[] array = new byte[n];
        this.readFully(array, 0, array.length);
        return CpioUtil.byteArray2long(array, b);
    }
    
    private long readAsciiLong(final int n, final int n2) throws IOException {
        final byte[] array = new byte[n];
        this.readFully(array, 0, array.length);
        return Long.parseLong(ArchiveUtils.toAsciiString(array), n2);
    }
    
    private CpioArchiveEntry readNewEntry(final boolean b) throws IOException {
        CpioArchiveEntry cpioArchiveEntry;
        if (b) {
            cpioArchiveEntry = new CpioArchiveEntry((short)2);
        }
        else {
            cpioArchiveEntry = new CpioArchiveEntry((short)1);
        }
        cpioArchiveEntry.setInode(this.readAsciiLong(8, 16));
        final long asciiLong = this.readAsciiLong(8, 16);
        if (CpioUtil.fileType(asciiLong) != 0L) {
            cpioArchiveEntry.setMode(asciiLong);
        }
        cpioArchiveEntry.setUID(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setGID(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setNumberOfLinks(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setTime(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setSize(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMaj(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMin(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMaj(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMin(this.readAsciiLong(8, 16));
        final long asciiLong2 = this.readAsciiLong(8, 16);
        cpioArchiveEntry.setChksum(this.readAsciiLong(8, 16));
        final String cString = this.readCString((int)asciiLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(asciiLong) == 0L && !cString.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry name: " + cString + " Occured at byte: " + this.getBytesRead());
        }
        this.skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }
    
    private CpioArchiveEntry readOldAsciiEntry() throws IOException {
        final CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short)4);
        cpioArchiveEntry.setDevice(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setInode(this.readAsciiLong(6, 8));
        final long asciiLong = this.readAsciiLong(6, 8);
        if (CpioUtil.fileType(asciiLong) != 0L) {
            cpioArchiveEntry.setMode(asciiLong);
        }
        cpioArchiveEntry.setUID(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setGID(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setNumberOfLinks(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setRemoteDevice(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setTime(this.readAsciiLong(11, 8));
        final long asciiLong2 = this.readAsciiLong(6, 8);
        cpioArchiveEntry.setSize(this.readAsciiLong(11, 8));
        final String cString = this.readCString((int)asciiLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(asciiLong) == 0L && !cString.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + cString + " Occured at byte: " + this.getBytesRead());
        }
        return cpioArchiveEntry;
    }
    
    private CpioArchiveEntry readOldBinaryEntry(final boolean b) throws IOException {
        final CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short)8);
        cpioArchiveEntry.setDevice(this.readBinaryLong(2, b));
        cpioArchiveEntry.setInode(this.readBinaryLong(2, b));
        final long binaryLong = this.readBinaryLong(2, b);
        if (CpioUtil.fileType(binaryLong) != 0L) {
            cpioArchiveEntry.setMode(binaryLong);
        }
        cpioArchiveEntry.setUID(this.readBinaryLong(2, b));
        cpioArchiveEntry.setGID(this.readBinaryLong(2, b));
        cpioArchiveEntry.setNumberOfLinks(this.readBinaryLong(2, b));
        cpioArchiveEntry.setRemoteDevice(this.readBinaryLong(2, b));
        cpioArchiveEntry.setTime(this.readBinaryLong(4, b));
        final long binaryLong2 = this.readBinaryLong(2, b);
        cpioArchiveEntry.setSize(this.readBinaryLong(4, b));
        final String cString = this.readCString((int)binaryLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(binaryLong) == 0L && !cString.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + cString + "Occured at byte: " + this.getBytesRead());
        }
        this.skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }
    
    private String readCString(final int n) throws IOException {
        final byte[] array = new byte[n - 1];
        this.readFully(array, 0, array.length);
        this.in.read();
        return this.encoding.decode(array);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("negative skip length");
        }
        this.ensureOpen();
        final int n2 = (int)Math.min(n, 2147483647L);
        while (0 < n2) {
            int length = n2 - 0;
            if (length > this.tmpbuf.length) {
                length = this.tmpbuf.length;
            }
            if (this.read(this.tmpbuf, 0, length) == -1) {
                this.entryEOF = true;
                break;
            }
        }
        return 0;
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextCPIOEntry();
    }
    
    private void skipRemainderOfLastBlock() throws IOException {
        final long n = this.getBytesRead() % this.blockSize;
        long skip;
        for (long n2 = (n == 0L) ? 0L : (this.blockSize - n); n2 > 0L; n2 -= skip) {
            skip = this.skip(this.blockSize - n);
            if (skip <= 0L) {
                break;
            }
        }
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 6 && ((array[0] == 113 && (array[1] & 0xFF) == 0xC7) || (array[1] == 113 && (array[0] & 0xFF) == 0xC7) || (array[0] == 48 && array[1] == 55 && array[2] == 48 && array[3] == 55 && array[4] == 48 && (array[5] == 49 || array[5] == 50 || array[5] == 55)));
    }
}
