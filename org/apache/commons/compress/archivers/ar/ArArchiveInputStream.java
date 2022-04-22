package org.apache.commons.compress.archivers.ar;

import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.*;
import java.io.*;

public class ArArchiveInputStream extends ArchiveInputStream
{
    private final InputStream input;
    private long offset;
    private boolean closed;
    private ArArchiveEntry currentEntry;
    private byte[] namebuffer;
    private long entryOffset;
    private final byte[] NAME_BUF;
    private final byte[] LAST_MODIFIED_BUF;
    private final byte[] ID_BUF;
    private final byte[] FILE_MODE_BUF;
    private final byte[] LENGTH_BUF;
    static final String BSD_LONGNAME_PREFIX = "#1/";
    private static final String BSD_LONGNAME_PATTERN = "^#1/\\d+";
    private static final String GNU_STRING_TABLE_NAME = "//";
    private static final String GNU_LONGNAME_PATTERN = "^/\\d+";
    
    public ArArchiveInputStream(final InputStream input) {
        this.offset = 0L;
        this.currentEntry = null;
        this.namebuffer = null;
        this.entryOffset = -1L;
        this.NAME_BUF = new byte[16];
        this.LAST_MODIFIED_BUF = new byte[12];
        this.ID_BUF = new byte[6];
        this.FILE_MODE_BUF = new byte[8];
        this.LENGTH_BUF = new byte[10];
        this.input = input;
        this.closed = false;
    }
    
    public ArArchiveEntry getNextArEntry() throws IOException {
        if (this.currentEntry != null) {
            IOUtils.skip(this, this.entryOffset + this.currentEntry.getLength() - this.offset);
            this.currentEntry = null;
        }
        if (this.offset == 0L) {
            final byte[] asciiBytes = ArchiveUtils.toAsciiBytes("!<arch>\n");
            final byte[] array = new byte[asciiBytes.length];
            if (IOUtils.readFully(this, array) != asciiBytes.length) {
                throw new IOException("failed to read header. Occured at byte: " + this.getBytesRead());
            }
            while (0 < asciiBytes.length) {
                if (asciiBytes[0] != array[0]) {
                    throw new IOException("invalid header " + ArchiveUtils.toAsciiString(array));
                }
                int fully = 0;
                ++fully;
            }
        }
        if (this.offset % 2L != 0L && this.read() < 0) {
            return null;
        }
        if (this.input.available() == 0) {
            return null;
        }
        IOUtils.readFully(this, this.NAME_BUF);
        IOUtils.readFully(this, this.LAST_MODIFIED_BUF);
        IOUtils.readFully(this, this.ID_BUF);
        final int int1 = this.asInt(this.ID_BUF, true);
        IOUtils.readFully(this, this.ID_BUF);
        IOUtils.readFully(this, this.FILE_MODE_BUF);
        IOUtils.readFully(this, this.LENGTH_BUF);
        final byte[] asciiBytes2 = ArchiveUtils.toAsciiBytes("`\n");
        final byte[] array2 = new byte[asciiBytes2.length];
        int fully = IOUtils.readFully(this, array2);
        if (0 != asciiBytes2.length) {
            throw new IOException("failed to read entry trailer. Occured at byte: " + this.getBytesRead());
        }
        while (0 < asciiBytes2.length) {
            if (asciiBytes2[0] != array2[0]) {
                throw new IOException("invalid entry trailer. not read the content? Occured at byte: " + this.getBytesRead());
            }
            int n = 0;
            ++n;
        }
        this.entryOffset = this.offset;
        String s = ArchiveUtils.toAsciiString(this.NAME_BUF).trim();
        if (isGNUStringTable(s)) {
            this.currentEntry = this.readGNUStringTable(this.LENGTH_BUF);
            return this.getNextArEntry();
        }
        long long1 = this.asLong(this.LENGTH_BUF);
        if (s.endsWith("/")) {
            s = s.substring(0, s.length() - 1);
        }
        else if (this.isGNULongName(s)) {
            final int n = Integer.parseInt(s.substring(1));
            s = this.getExtendedName(0);
        }
        else if (isBSDLongName(s)) {
            s = this.getBSDLongName(s);
            final int n = s.length();
            long1 -= 0;
            this.entryOffset += 0;
        }
        return this.currentEntry = new ArArchiveEntry(s, long1, int1, this.asInt(this.ID_BUF, true), this.asInt(this.FILE_MODE_BUF, 8), this.asLong(this.LAST_MODIFIED_BUF));
    }
    
    private String getExtendedName(final int n) throws IOException {
        if (this.namebuffer == null) {
            throw new IOException("Cannot process GNU long filename as no // record was found");
        }
        for (int i = n; i < this.namebuffer.length; ++i) {
            if (this.namebuffer[i] == 10) {
                if (this.namebuffer[i - 1] == 47) {
                    --i;
                }
                return ArchiveUtils.toAsciiString(this.namebuffer, n, i - n);
            }
        }
        throw new IOException("Failed to read entry: " + n);
    }
    
    private long asLong(final byte[] array) {
        return Long.parseLong(ArchiveUtils.toAsciiString(array).trim());
    }
    
    private int asInt(final byte[] array) {
        return this.asInt(array, 10, false);
    }
    
    private int asInt(final byte[] array, final boolean b) {
        return this.asInt(array, 10, b);
    }
    
    private int asInt(final byte[] array, final int n) {
        return this.asInt(array, n, false);
    }
    
    private int asInt(final byte[] array, final int n, final boolean b) {
        final String trim = ArchiveUtils.toAsciiString(array).trim();
        if (trim.length() == 0 && b) {
            return 0;
        }
        return Integer.parseInt(trim, n);
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextArEntry();
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.input.close();
        }
        this.currentEntry = null;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        int n3 = n2;
        if (this.currentEntry != null) {
            final long n4 = this.entryOffset + this.currentEntry.getLength();
            if (n2 <= 0 || n4 <= this.offset) {
                return -1;
            }
            n3 = (int)Math.min(n2, n4 - this.offset);
        }
        final int read = this.input.read(array, n, n3);
        this.count(read);
        this.offset += ((read > 0) ? read : 0L);
        return read;
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 8 && array[0] == 33 && array[1] == 60 && array[2] == 97 && array[3] == 114 && array[4] == 99 && array[5] == 104 && array[6] == 62 && array[7] == 10;
    }
    
    private static boolean isBSDLongName(final String s) {
        return s != null && s.matches("^#1/\\d+");
    }
    
    private String getBSDLongName(final String s) throws IOException {
        final int int1 = Integer.parseInt(s.substring(3));
        final byte[] array = new byte[int1];
        final int fully = IOUtils.readFully(this.input, array);
        this.count(fully);
        if (fully != int1) {
            throw new EOFException();
        }
        return ArchiveUtils.toAsciiString(array);
    }
    
    private static boolean isGNUStringTable(final String s) {
        return "//".equals(s);
    }
    
    private ArArchiveEntry readGNUStringTable(final byte[] array) throws IOException {
        final int int1 = this.asInt(array);
        this.namebuffer = new byte[int1];
        final int fully = IOUtils.readFully(this, this.namebuffer, 0, int1);
        if (fully != int1) {
            throw new IOException("Failed to read complete // record: expected=" + int1 + " read=" + fully);
        }
        return new ArArchiveEntry("//", int1);
    }
    
    private boolean isGNULongName(final String s) {
        return s != null && s.matches("^/\\d+");
    }
}
