package org.apache.commons.compress.archivers.ar;

import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.*;
import java.io.*;

public class ArArchiveOutputStream extends ArchiveOutputStream
{
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_BSD = 1;
    private final OutputStream out;
    private long entryOffset;
    private ArArchiveEntry prevEntry;
    private boolean haveUnclosedEntry;
    private int longFileMode;
    private boolean finished;
    
    public ArArchiveOutputStream(final OutputStream out) {
        this.entryOffset = 0L;
        this.haveUnclosedEntry = false;
        this.longFileMode = 0;
        this.finished = false;
        this.out = out;
    }
    
    public void setLongFileMode(final int longFileMode) {
        this.longFileMode = longFileMode;
    }
    
    private long writeArchiveHeader() throws IOException {
        final byte[] asciiBytes = ArchiveUtils.toAsciiBytes("!<arch>\n");
        this.out.write(asciiBytes);
        return asciiBytes.length;
    }
    
    @Override
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.prevEntry == null || !this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.entryOffset % 2L != 0L) {
            this.out.write(10);
        }
        this.haveUnclosedEntry = false;
    }
    
    @Override
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        final ArArchiveEntry prevEntry = (ArArchiveEntry)archiveEntry;
        if (this.prevEntry == null) {
            this.writeArchiveHeader();
        }
        else {
            if (this.prevEntry.getLength() != this.entryOffset) {
                throw new IOException("length does not match entry (" + this.prevEntry.getLength() + " != " + this.entryOffset);
            }
            if (this.haveUnclosedEntry) {
                this.closeArchiveEntry();
            }
        }
        this.writeEntryHeader(this.prevEntry = prevEntry);
        this.entryOffset = 0L;
        this.haveUnclosedEntry = true;
    }
    
    private long fill(final long n, final long n2, final char c) throws IOException {
        final long n3 = n2 - n;
        if (n3 > 0L) {
            while (0 < n3) {
                this.write(c);
                int n4 = 0;
                ++n4;
            }
        }
        return n2;
    }
    
    private long write(final String s) throws IOException {
        final byte[] bytes = s.getBytes("ascii");
        this.write(bytes);
        return bytes.length;
    }
    
    private long writeEntryHeader(final ArArchiveEntry arArchiveEntry) throws IOException {
        final long n = 0L;
        final String name = arArchiveEntry.getName();
        if (0 == this.longFileMode && name.length() > 16) {
            throw new IOException("filename too long, > 16 chars: " + name);
        }
        long n2;
        if (1 == this.longFileMode && (name.length() > 16 || name.indexOf(" ") > -1)) {
            n2 = n + this.write("#1/" + String.valueOf(name.length()));
        }
        else {
            n2 = n + this.write(name);
        }
        final long fill = this.fill(n2, 16L, ' ');
        final String string = "" + arArchiveEntry.getLastModified();
        if (string.length() > 12) {
            throw new IOException("modified too long");
        }
        final long fill2 = this.fill(fill + this.write(string), 28L, ' ');
        final String string2 = "" + arArchiveEntry.getUserId();
        if (string2.length() > 6) {
            throw new IOException("userid too long");
        }
        final long fill3 = this.fill(fill2 + this.write(string2), 34L, ' ');
        final String string3 = "" + arArchiveEntry.getGroupId();
        if (string3.length() > 6) {
            throw new IOException("groupid too long");
        }
        final long fill4 = this.fill(fill3 + this.write(string3), 40L, ' ');
        final String string4 = "" + Integer.toString(arArchiveEntry.getMode(), 8);
        if (string4.length() > 8) {
            throw new IOException("filemode too long");
        }
        final long fill5 = this.fill(fill4 + this.write(string4), 48L, ' ');
        final String value = String.valueOf(arArchiveEntry.getLength() + name.length());
        if (value.length() > 10) {
            throw new IOException("size too long");
        }
        return this.fill(fill5 + this.write(value), 58L, ' ') + this.write("`\n") + this.write(name);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.count(n2);
        this.entryOffset += n2;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.out.close();
        this.prevEntry = null;
    }
    
    @Override
    public ArchiveEntry createArchiveEntry(final File file, final String s) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ArArchiveEntry(file, s);
    }
    
    @Override
    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        }
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
    }
}
