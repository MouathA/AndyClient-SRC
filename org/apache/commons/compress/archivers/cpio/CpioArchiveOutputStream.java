package org.apache.commons.compress.archivers.cpio;

import java.util.*;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.utils.*;
import java.nio.*;
import java.io.*;

public class CpioArchiveOutputStream extends ArchiveOutputStream implements CpioConstants
{
    private CpioArchiveEntry entry;
    private boolean closed;
    private boolean finished;
    private final short entryFormat;
    private final HashMap names;
    private long crc;
    private long written;
    private final OutputStream out;
    private final int blockSize;
    private long nextArtificalDeviceAndInode;
    private final ZipEncoding encoding;
    
    public CpioArchiveOutputStream(final OutputStream outputStream, final short n) {
        this(outputStream, n, 512, "US-ASCII");
    }
    
    public CpioArchiveOutputStream(final OutputStream outputStream, final short n, final int n2) {
        this(outputStream, n, n2, "US-ASCII");
    }
    
    public CpioArchiveOutputStream(final OutputStream out, final short entryFormat, final int blockSize, final String s) {
        this.closed = false;
        this.names = new HashMap();
        this.crc = 0L;
        this.nextArtificalDeviceAndInode = 1L;
        this.out = out;
        switch (entryFormat) {
            case 1:
            case 2:
            case 4:
            case 8: {
                this.entryFormat = entryFormat;
                this.blockSize = blockSize;
                this.encoding = ZipEncodingHelper.getZipEncoding(s);
            }
            default: {
                throw new IllegalArgumentException("Unknown format: " + entryFormat);
            }
        }
    }
    
    public CpioArchiveOutputStream(final OutputStream outputStream) {
        this(outputStream, (short)1);
    }
    
    public CpioArchiveOutputStream(final OutputStream outputStream, final String s) {
        this(outputStream, (short)1, 512, s);
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    @Override
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        final CpioArchiveEntry entry = (CpioArchiveEntry)archiveEntry;
        this.ensureOpen();
        if (this.entry != null) {
            this.closeArchiveEntry();
        }
        if (entry.getTime() == -1L) {
            entry.setTime(System.currentTimeMillis() / 1000L);
        }
        final short format = entry.getFormat();
        if (format != this.entryFormat) {
            throw new IOException("Header format: " + format + " does not match existing format: " + this.entryFormat);
        }
        if (this.names.put(entry.getName(), entry) != null) {
            throw new IOException("duplicate entry: " + entry.getName());
        }
        this.writeHeader(entry);
        this.entry = entry;
        this.written = 0L;
    }
    
    private void writeHeader(final CpioArchiveEntry cpioArchiveEntry) throws IOException {
        switch (cpioArchiveEntry.getFormat()) {
            case 1: {
                this.out.write(ArchiveUtils.toAsciiBytes("070701"));
                this.count(6);
                this.writeNewEntry(cpioArchiveEntry);
                break;
            }
            case 2: {
                this.out.write(ArchiveUtils.toAsciiBytes("070702"));
                this.count(6);
                this.writeNewEntry(cpioArchiveEntry);
                break;
            }
            case 4: {
                this.out.write(ArchiveUtils.toAsciiBytes("070707"));
                this.count(6);
                this.writeOldAsciiEntry(cpioArchiveEntry);
                break;
            }
            case 8: {
                this.writeBinaryLong(29127L, 2, true);
                this.writeOldBinaryEntry(cpioArchiveEntry, true);
                break;
            }
            default: {
                throw new IOException("unknown format " + cpioArchiveEntry.getFormat());
            }
        }
    }
    
    private void writeNewEntry(final CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long deviceMin = cpioArchiveEntry.getDeviceMin();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            final long n = 0L;
            deviceMin = 0L;
            inode = n;
        }
        else if (inode == 0L && deviceMin == 0L) {
            inode = (this.nextArtificalDeviceAndInode & -1L);
            deviceMin = (this.nextArtificalDeviceAndInode++ >> 32 & -1L);
        }
        else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, inode + 4294967296L * deviceMin) + 1L;
        }
        this.writeAsciiLong(inode, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getMode(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getUID(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getGID(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getTime(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getSize(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getDeviceMaj(), 8, 16);
        this.writeAsciiLong(deviceMin, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMaj(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMin(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getChksum(), 8, 16);
        this.writeCString(cpioArchiveEntry.getName());
        this.pad(cpioArchiveEntry.getHeaderPadCount());
    }
    
    private void writeOldAsciiEntry(final CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            final long n = 0L;
            device = 0L;
            inode = n;
        }
        else if (inode == 0L && device == 0L) {
            inode = (this.nextArtificalDeviceAndInode & 0x3FFFFL);
            device = (this.nextArtificalDeviceAndInode++ >> 18 & 0x3FFFFL);
        }
        else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, inode + 262144L * device) + 1L;
        }
        this.writeAsciiLong(device, 6, 8);
        this.writeAsciiLong(inode, 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getMode(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getUID(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getGID(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDevice(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getTime(), 11, 8);
        this.writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getSize(), 11, 8);
        this.writeCString(cpioArchiveEntry.getName());
    }
    
    private void writeOldBinaryEntry(final CpioArchiveEntry cpioArchiveEntry, final boolean b) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            final long n = 0L;
            device = 0L;
            inode = n;
        }
        else if (inode == 0L && device == 0L) {
            inode = (this.nextArtificalDeviceAndInode & 0xFFFFL);
            device = (this.nextArtificalDeviceAndInode++ >> 16 & 0xFFFFL);
        }
        else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, inode + 65536L * device) + 1L;
        }
        this.writeBinaryLong(device, 2, b);
        this.writeBinaryLong(inode, 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getMode(), 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getUID(), 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getGID(), 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getNumberOfLinks(), 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getRemoteDevice(), 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getTime(), 4, b);
        this.writeBinaryLong(cpioArchiveEntry.getName().length() + 1, 2, b);
        this.writeBinaryLong(cpioArchiveEntry.getSize(), 4, b);
        this.writeCString(cpioArchiveEntry.getName());
        this.pad(cpioArchiveEntry.getHeaderPadCount());
    }
    
    @Override
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        this.ensureOpen();
        if (this.entry == null) {
            throw new IOException("Trying to close non-existent entry");
        }
        if (this.entry.getSize() != this.written) {
            throw new IOException("invalid entry size (expected " + this.entry.getSize() + " but got " + this.written + " bytes)");
        }
        this.pad(this.entry.getDataPadCount());
        if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
            throw new IOException("CRC Error");
        }
        this.entry = null;
        this.crc = 0L;
        this.written = 0L;
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        if (this.entry == null) {
            throw new IOException("no current CPIO entry");
        }
        if (this.written + n2 > this.entry.getSize()) {
            throw new IOException("attempt to write past end of STORED entry");
        }
        this.out.write(array, n, n2);
        this.written += n2;
        if (this.entry.getFormat() == 2) {
            while (0 < n2) {
                this.crc += (array[0] & 0xFF);
                int n3 = 0;
                ++n3;
            }
        }
        this.count(n2);
    }
    
    @Override
    public void finish() throws IOException {
        this.ensureOpen();
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        (this.entry = new CpioArchiveEntry(this.entryFormat)).setName("TRAILER!!!");
        this.entry.setNumberOfLinks(1L);
        this.writeHeader(this.entry);
        this.closeArchiveEntry();
        final int n = (int)(this.getBytesWritten() % this.blockSize);
        if (n != 0) {
            this.pad(this.blockSize - n);
        }
        this.finished = true;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        if (!this.closed) {
            this.out.close();
            this.closed = true;
        }
    }
    
    private void pad(final int n) throws IOException {
        if (n > 0) {
            this.out.write(new byte[n]);
            this.count(n);
        }
    }
    
    private void writeBinaryLong(final long n, final int n2, final boolean b) throws IOException {
        final byte[] long2byteArray = CpioUtil.long2byteArray(n, n2, b);
        this.out.write(long2byteArray);
        this.count(long2byteArray.length);
    }
    
    private void writeAsciiLong(final long n, final int n2, final int n3) throws IOException {
        final StringBuilder sb = new StringBuilder();
        if (n3 == 16) {
            sb.append(Long.toHexString(n));
        }
        else if (n3 == 8) {
            sb.append(Long.toOctalString(n));
        }
        else {
            sb.append(Long.toString(n));
        }
        String s;
        if (sb.length() <= n2) {
            while (0 < (long)(n2 - sb.length())) {
                sb.insert(0, "0");
                int n4 = 0;
                ++n4;
            }
            s = sb.toString();
        }
        else {
            s = sb.substring(sb.length() - n2);
        }
        final byte[] asciiBytes = ArchiveUtils.toAsciiBytes(s);
        this.out.write(asciiBytes);
        this.count(asciiBytes.length);
    }
    
    private void writeCString(final String s) throws IOException {
        final ByteBuffer encode = this.encoding.encode(s);
        final int n = encode.limit() - encode.position();
        this.out.write(encode.array(), encode.arrayOffset(), n);
        this.out.write(0);
        this.count(n + 1);
    }
    
    @Override
    public ArchiveEntry createArchiveEntry(final File file, final String s) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new CpioArchiveEntry(file, s);
    }
}
