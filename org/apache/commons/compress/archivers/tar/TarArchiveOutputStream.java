package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public class TarArchiveOutputStream extends ArchiveOutputStream
{
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_TRUNCATE = 1;
    public static final int LONGFILE_GNU = 2;
    public static final int LONGFILE_POSIX = 3;
    public static final int BIGNUMBER_ERROR = 0;
    public static final int BIGNUMBER_STAR = 1;
    public static final int BIGNUMBER_POSIX = 2;
    private long currSize;
    private String currName;
    private long currBytes;
    private final byte[] recordBuf;
    private int assemLen;
    private final byte[] assemBuf;
    private int longFileMode;
    private int bigNumberMode;
    private int recordsWritten;
    private final int recordsPerBlock;
    private final int recordSize;
    private boolean closed;
    private boolean haveUnclosedEntry;
    private boolean finished;
    private final OutputStream out;
    private final ZipEncoding encoding;
    private boolean addPaxHeadersForNonAsciiNames;
    private static final ZipEncoding ASCII;
    
    public TarArchiveOutputStream(final OutputStream outputStream) {
        this(outputStream, 10240, 512);
    }
    
    public TarArchiveOutputStream(final OutputStream outputStream, final String s) {
        this(outputStream, 10240, 512, s);
    }
    
    public TarArchiveOutputStream(final OutputStream outputStream, final int n) {
        this(outputStream, n, 512);
    }
    
    public TarArchiveOutputStream(final OutputStream outputStream, final int n, final String s) {
        this(outputStream, n, 512, s);
    }
    
    public TarArchiveOutputStream(final OutputStream outputStream, final int n, final int n2) {
        this(outputStream, n, n2, null);
    }
    
    public TarArchiveOutputStream(final OutputStream outputStream, final int n, final int recordSize, final String s) {
        this.longFileMode = 0;
        this.bigNumberMode = 0;
        this.closed = false;
        this.haveUnclosedEntry = false;
        this.finished = false;
        this.addPaxHeadersForNonAsciiNames = false;
        this.out = new CountingOutputStream(outputStream);
        this.encoding = ZipEncodingHelper.getZipEncoding(s);
        this.assemLen = 0;
        this.assemBuf = new byte[recordSize];
        this.recordBuf = new byte[recordSize];
        this.recordSize = recordSize;
        this.recordsPerBlock = n / recordSize;
    }
    
    public void setLongFileMode(final int longFileMode) {
        this.longFileMode = longFileMode;
    }
    
    public void setBigNumberMode(final int bigNumberMode) {
        this.bigNumberMode = bigNumberMode;
    }
    
    public void setAddPaxHeadersForNonAsciiNames(final boolean addPaxHeadersForNonAsciiNames) {
        this.addPaxHeadersForNonAsciiNames = addPaxHeadersForNonAsciiNames;
    }
    
    @Deprecated
    @Override
    public int getCount() {
        return (int)this.getBytesWritten();
    }
    
    @Override
    public long getBytesWritten() {
        return ((CountingOutputStream)this.out).getBytesWritten();
    }
    
    @Override
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.haveUnclosedEntry) {
            throw new IOException("This archives contains unclosed entries.");
        }
        this.writeEOFRecord();
        this.writeEOFRecord();
        this.padAsNeeded();
        this.out.flush();
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
    
    public int getRecordSize() {
        return this.recordSize;
    }
    
    @Override
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        final TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final String name = tarArchiveEntry.getName();
        final boolean handleLongName = this.handleLongName(name, hashMap, "path", (byte)76, "file name");
        final String linkName = tarArchiveEntry.getLinkName();
        final boolean b = linkName != null && linkName.length() > 0 && this.handleLongName(linkName, hashMap, "linkpath", (byte)75, "link name");
        if (this.bigNumberMode == 2) {
            this.addPaxHeadersForBigNumbers(hashMap, tarArchiveEntry);
        }
        else if (this.bigNumberMode != 1) {
            this.failForBigNumbers(tarArchiveEntry);
        }
        if (this.addPaxHeadersForNonAsciiNames && !handleLongName && !TarArchiveOutputStream.ASCII.canEncode(name)) {
            hashMap.put("path", name);
        }
        if (this.addPaxHeadersForNonAsciiNames && !b && (tarArchiveEntry.isLink() || tarArchiveEntry.isSymbolicLink()) && !TarArchiveOutputStream.ASCII.canEncode(linkName)) {
            hashMap.put("linkpath", linkName);
        }
        if (hashMap.size() > 0) {
            this.writePaxHeaders(name, hashMap);
        }
        tarArchiveEntry.writeEntryHeader(this.recordBuf, this.encoding, this.bigNumberMode == 1);
        this.writeRecord(this.recordBuf);
        this.currBytes = 0L;
        if (tarArchiveEntry.isDirectory()) {
            this.currSize = 0L;
        }
        else {
            this.currSize = tarArchiveEntry.getSize();
        }
        this.currName = name;
        this.haveUnclosedEntry = true;
    }
    
    @Override
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (!this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.assemLen > 0) {
            for (int i = this.assemLen; i < this.assemBuf.length; ++i) {
                this.assemBuf[i] = 0;
            }
            this.writeRecord(this.assemBuf);
            this.currBytes += this.assemLen;
            this.assemLen = 0;
        }
        if (this.currBytes < this.currSize) {
            throw new IOException("entry '" + this.currName + "' closed at '" + this.currBytes + "' before the '" + this.currSize + "' bytes specified in the header were written");
        }
        this.haveUnclosedEntry = false;
    }
    
    @Override
    public void write(final byte[] array, int n, int n2) throws IOException {
        if (!this.haveUnclosedEntry) {
            throw new IllegalStateException("No current tar entry");
        }
        if (this.currBytes + 0 > this.currSize) {
            throw new IOException("request to write '" + 0 + "' bytes exceeds size in header of '" + this.currSize + "' bytes for entry '" + this.currName + "'");
        }
        if (this.assemLen > 0) {
            if (this.assemLen + 0 >= this.recordBuf.length) {
                final int n3 = this.recordBuf.length - this.assemLen;
                System.arraycopy(this.assemBuf, 0, this.recordBuf, 0, this.assemLen);
                System.arraycopy(array, n, this.recordBuf, this.assemLen, n3);
                this.writeRecord(this.recordBuf);
                this.currBytes += this.recordBuf.length;
                n += n3;
                n2 = 0 - n3;
                this.assemLen = 0;
            }
            else {
                System.arraycopy(array, n, this.assemBuf, this.assemLen, 0);
                n += 0;
                this.assemLen += 0;
            }
        }
        while (0 > 0) {
            if (0 < this.recordBuf.length) {
                System.arraycopy(array, n, this.assemBuf, this.assemLen, 0);
                this.assemLen += 0;
                break;
            }
            this.writeRecord(array, n);
            final int length = this.recordBuf.length;
            this.currBytes += length;
            n2 = 0 - length;
            n += length;
        }
    }
    
    void writePaxHeaders(final String s, final Map map) throws IOException {
        String s2 = "./PaxHeaders.X/" + this.stripTo7Bits(s);
        if (s2.length() >= 100) {
            s2 = s2.substring(0, 99);
        }
        final TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(s2, (byte)120);
        final StringWriter stringWriter = new StringWriter();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            final String s3 = entry.getKey();
            final String s4 = (String)entry.getValue();
            int i = s3.length() + s4.length() + 3 + 2;
            String s5 = i + " " + s3 + "=" + s4 + "\n";
            for (int n = s5.getBytes("UTF-8").length; i != n; i = n, s5 = i + " " + s3 + "=" + s4 + "\n", n = s5.getBytes("UTF-8").length) {}
            stringWriter.write(s5);
        }
        final byte[] bytes = stringWriter.toString().getBytes("UTF-8");
        tarArchiveEntry.setSize(bytes.length);
        this.putArchiveEntry(tarArchiveEntry);
        this.write(bytes);
        this.closeArchiveEntry();
    }
    
    private String stripTo7Bits(final String s) {
        final int length = s.length();
        final StringBuilder sb = new StringBuilder(length);
        while (0 < length) {
            final char c = (char)(s.charAt(0) & '\u007f');
            if (this.shouldBeReplaced(c)) {
                sb.append("_");
            }
            else {
                sb.append(c);
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private boolean shouldBeReplaced(final char c) {
        return c == '\0' || c == '/' || c == '\\';
    }
    
    private void writeEOFRecord() throws IOException {
        Arrays.fill(this.recordBuf, (byte)0);
        this.writeRecord(this.recordBuf);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public ArchiveEntry createArchiveEntry(final File file, final String s) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new TarArchiveEntry(file, s);
    }
    
    private void writeRecord(final byte[] array) throws IOException {
        if (array.length != this.recordSize) {
            throw new IOException("record to write has length '" + array.length + "' which is not the record size of '" + this.recordSize + "'");
        }
        this.out.write(array);
        ++this.recordsWritten;
    }
    
    private void writeRecord(final byte[] array, final int n) throws IOException {
        if (n + this.recordSize > array.length) {
            throw new IOException("record has length '" + array.length + "' with offset '" + n + "' which is less than the record size of '" + this.recordSize + "'");
        }
        this.out.write(array, n, this.recordSize);
        ++this.recordsWritten;
    }
    
    private void padAsNeeded() throws IOException {
        final int n = this.recordsWritten % this.recordsPerBlock;
        if (n != 0) {
            for (int i = n; i < this.recordsPerBlock; ++i) {
                this.writeEOFRecord();
            }
        }
    }
    
    private void addPaxHeadersForBigNumbers(final Map map, final TarArchiveEntry tarArchiveEntry) {
        this.addPaxHeaderForBigNumber(map, "size", tarArchiveEntry.getSize(), 8589934591L);
        this.addPaxHeaderForBigNumber(map, "gid", tarArchiveEntry.getGroupId(), 2097151L);
        this.addPaxHeaderForBigNumber(map, "mtime", tarArchiveEntry.getModTime().getTime() / 1000L, 8589934591L);
        this.addPaxHeaderForBigNumber(map, "uid", tarArchiveEntry.getUserId(), 2097151L);
        this.addPaxHeaderForBigNumber(map, "SCHILY.devmajor", tarArchiveEntry.getDevMajor(), 2097151L);
        this.addPaxHeaderForBigNumber(map, "SCHILY.devminor", tarArchiveEntry.getDevMinor(), 2097151L);
        this.failForBigNumber("mode", tarArchiveEntry.getMode(), 2097151L);
    }
    
    private void addPaxHeaderForBigNumber(final Map map, final String s, final long n, final long n2) {
        if (n < 0L || n > n2) {
            map.put(s, String.valueOf(n));
        }
    }
    
    private void failForBigNumbers(final TarArchiveEntry tarArchiveEntry) {
        this.failForBigNumber("entry size", tarArchiveEntry.getSize(), 8589934591L);
        this.failForBigNumber("group id", tarArchiveEntry.getGroupId(), 2097151L);
        this.failForBigNumber("last modification time", tarArchiveEntry.getModTime().getTime() / 1000L, 8589934591L);
        this.failForBigNumber("user id", tarArchiveEntry.getUserId(), 2097151L);
        this.failForBigNumber("mode", tarArchiveEntry.getMode(), 2097151L);
        this.failForBigNumber("major device number", tarArchiveEntry.getDevMajor(), 2097151L);
        this.failForBigNumber("minor device number", tarArchiveEntry.getDevMinor(), 2097151L);
    }
    
    private void failForBigNumber(final String s, final long n, final long n2) {
        if (n < 0L || n > n2) {
            throw new RuntimeException(s + " '" + n + "' is too big ( > " + n2 + " )");
        }
    }
    
    private boolean handleLongName(final String s, final Map map, final String s2, final byte b, final String s3) throws IOException {
        final ByteBuffer encode = this.encoding.encode(s);
        final int n = encode.limit() - encode.position();
        if (n >= 100) {
            if (this.longFileMode == 3) {
                map.put(s2, s);
                return true;
            }
            if (this.longFileMode == 2) {
                final TarArchiveEntry tarArchiveEntry = new TarArchiveEntry("././@LongLink", b);
                tarArchiveEntry.setSize(n + 1);
                this.putArchiveEntry(tarArchiveEntry);
                this.write(encode.array(), encode.arrayOffset(), n);
                this.write(0);
                this.closeArchiveEntry();
            }
            else if (this.longFileMode != 1) {
                throw new RuntimeException(s3 + " '" + s + "' is too long ( > " + 100 + " bytes)");
            }
        }
        return false;
    }
    
    static {
        ASCII = ZipEncodingHelper.getZipEncoding("ASCII");
    }
}
