package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.zip.*;
import java.io.*;
import org.apache.commons.compress.utils.*;
import java.util.*;
import org.apache.commons.compress.archivers.*;

public class TarArchiveInputStream extends ArchiveInputStream
{
    private static final int SMALL_BUFFER_SIZE = 256;
    private final byte[] SMALL_BUF;
    private final int recordSize;
    private final int blockSize;
    private boolean hasHitEOF;
    private long entrySize;
    private long entryOffset;
    private final InputStream is;
    private TarArchiveEntry currEntry;
    private final ZipEncoding encoding;
    
    public TarArchiveInputStream(final InputStream inputStream) {
        this(inputStream, 10240, 512);
    }
    
    public TarArchiveInputStream(final InputStream inputStream, final String s) {
        this(inputStream, 10240, 512, s);
    }
    
    public TarArchiveInputStream(final InputStream inputStream, final int n) {
        this(inputStream, n, 512);
    }
    
    public TarArchiveInputStream(final InputStream inputStream, final int n, final String s) {
        this(inputStream, n, 512, s);
    }
    
    public TarArchiveInputStream(final InputStream inputStream, final int n, final int n2) {
        this(inputStream, n, n2, null);
    }
    
    public TarArchiveInputStream(final InputStream is, final int blockSize, final int recordSize, final String s) {
        this.SMALL_BUF = new byte[256];
        this.is = is;
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(s);
        this.recordSize = recordSize;
        this.blockSize = blockSize;
    }
    
    @Override
    public void close() throws IOException {
        this.is.close();
    }
    
    public int getRecordSize() {
        return this.recordSize;
    }
    
    @Override
    public int available() throws IOException {
        if (this.entrySize - this.entryOffset > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int)(this.entrySize - this.entryOffset);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n <= 0L) {
            return 0L;
        }
        final long skip = this.is.skip(Math.min(n, this.entrySize - this.entryOffset));
        this.count(skip);
        this.entryOffset += skip;
        return skip;
    }
    
    @Override
    public synchronized void reset() {
    }
    
    public TarArchiveEntry getNextTarEntry() throws IOException {
        if (this.hasHitEOF) {
            return null;
        }
        if (this.currEntry != null) {
            IOUtils.skip(this, Long.MAX_VALUE);
            this.skipRecordPadding();
        }
        final byte[] record = this.getRecord();
        if (record == null) {
            return this.currEntry = null;
        }
        this.currEntry = new TarArchiveEntry(record, this.encoding);
        this.entryOffset = 0L;
        this.entrySize = this.currEntry.getSize();
        if (this.currEntry.isGNULongLinkEntry()) {
            final byte[] longNameData = this.getLongNameData();
            if (longNameData == null) {
                return null;
            }
            this.currEntry.setLinkName(this.encoding.decode(longNameData));
        }
        if (this.currEntry.isGNULongNameEntry()) {
            final byte[] longNameData2 = this.getLongNameData();
            if (longNameData2 == null) {
                return null;
            }
            this.currEntry.setName(this.encoding.decode(longNameData2));
        }
        if (this.currEntry.isPaxHeader()) {
            this.paxHeaders();
        }
        if (this.currEntry.isGNUSparse()) {
            this.readGNUSparse();
        }
        this.entrySize = this.currEntry.getSize();
        return this.currEntry;
    }
    
    private void skipRecordPadding() throws IOException {
        if (this.entrySize > 0L && this.entrySize % this.recordSize != 0L) {
            this.count(IOUtils.skip(this.is, (this.entrySize / this.recordSize + 1L) * this.recordSize - this.entrySize));
        }
    }
    
    protected byte[] getLongNameData() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (this.read(this.SMALL_BUF) >= 0) {
            byteArrayOutputStream.write(this.SMALL_BUF, 0, 0);
        }
        this.getNextEntry();
        if (this.currEntry == null) {
            return null;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length = byteArray.length;
        while (0 > 0 && byteArray[-1] == 0) {
            --length;
        }
        if (0 != byteArray.length) {
            final byte[] array = new byte[0];
            System.arraycopy(byteArray, 0, array, 0, 0);
            byteArray = array;
        }
        return byteArray;
    }
    
    private byte[] getRecord() throws IOException {
        byte[] record = this.readRecord();
        this.hasHitEOF = this.isEOFRecord(record);
        if (this.hasHitEOF && record != null) {
            this.tryToConsumeSecondEOFRecord();
            this.consumeRemainderOfLastBlock();
            record = null;
        }
        return record;
    }
    
    protected boolean isEOFRecord(final byte[] array) {
        return array == null || ArchiveUtils.isArrayZero(array, this.recordSize);
    }
    
    protected byte[] readRecord() throws IOException {
        final byte[] array = new byte[this.recordSize];
        final int fully = IOUtils.readFully(this.is, array);
        this.count(fully);
        if (fully != this.recordSize) {
            return null;
        }
        return array;
    }
    
    private void paxHeaders() throws IOException {
        final Map paxHeaders = this.parsePaxHeaders(this);
        this.getNextEntry();
        this.applyPaxHeadersToCurrentEntry(paxHeaders);
    }
    
    Map parsePaxHeaders(final InputStream inputStream) throws IOException {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        while (true) {
            int n;
            if ((n = inputStream.read()) != -1) {
                int n2 = 0;
                ++n2;
                if (n != 32) {
                    continue;
                }
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while ((n = inputStream.read()) != -1) {
                    ++n2;
                    if (n == 61) {
                        final String string = byteArrayOutputStream.toString("UTF-8");
                        final byte[] array = new byte[0];
                        final int fully = IOUtils.readFully(inputStream, array);
                        if (fully != 0) {
                            throw new IOException("Failed to read Paxheader. Expected " + 0 + " bytes, read " + fully);
                        }
                        hashMap.put(string, new String(array, 0, -1, "UTF-8"));
                        break;
                    }
                    else {
                        byteArrayOutputStream.write((byte)n);
                    }
                }
            }
            if (n == -1) {
                return hashMap;
            }
        }
    }
    
    private void applyPaxHeadersToCurrentEntry(final Map map) {
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            final String s = entry.getKey();
            final String s2 = (String)entry.getValue();
            if ("path".equals(s)) {
                this.currEntry.setName(s2);
            }
            else if ("linkpath".equals(s)) {
                this.currEntry.setLinkName(s2);
            }
            else if ("gid".equals(s)) {
                this.currEntry.setGroupId(Integer.parseInt(s2));
            }
            else if ("gname".equals(s)) {
                this.currEntry.setGroupName(s2);
            }
            else if ("uid".equals(s)) {
                this.currEntry.setUserId(Integer.parseInt(s2));
            }
            else if ("uname".equals(s)) {
                this.currEntry.setUserName(s2);
            }
            else if ("size".equals(s)) {
                this.currEntry.setSize(Long.parseLong(s2));
            }
            else if ("mtime".equals(s)) {
                this.currEntry.setModTime((long)(Double.parseDouble(s2) * 1000.0));
            }
            else if ("SCHILY.devminor".equals(s)) {
                this.currEntry.setDevMinor(Integer.parseInt(s2));
            }
            else {
                if (!"SCHILY.devmajor".equals(s)) {
                    continue;
                }
                this.currEntry.setDevMajor(Integer.parseInt(s2));
            }
        }
    }
    
    private void readGNUSparse() throws IOException {
        if (this.currEntry.isExtended()) {
            byte[] record;
            do {
                record = this.getRecord();
                if (record == null) {
                    this.currEntry = null;
                    break;
                }
            } while (new TarArchiveSparseEntry(record).isExtended());
        }
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextTarEntry();
    }
    
    private void tryToConsumeSecondEOFRecord() throws IOException {
        final boolean markSupported = this.is.markSupported();
        if (markSupported) {
            this.is.mark(this.recordSize);
        }
        final boolean b = !this.isEOFRecord(this.readRecord());
        if (true && markSupported) {
            this.pushedBackBytes(this.recordSize);
            this.is.reset();
        }
    }
    
    @Override
    public int read(final byte[] array, final int n, int min) throws IOException {
        if (this.hasHitEOF || this.entryOffset >= this.entrySize) {
            return -1;
        }
        if (this.currEntry == null) {
            throw new IllegalStateException("No current tar entry");
        }
        min = Math.min(min, this.available());
        this.is.read(array, n, min);
        if (0 == -1) {
            if (min > 0) {
                throw new IOException("Truncated TAR archive");
            }
            this.hasHitEOF = true;
        }
        else {
            this.count(0);
            this.entryOffset += 0;
        }
        return 0;
    }
    
    @Override
    public boolean canReadEntryData(final ArchiveEntry archiveEntry) {
        return archiveEntry instanceof TarArchiveEntry && !((TarArchiveEntry)archiveEntry).isGNUSparse();
    }
    
    public TarArchiveEntry getCurrentEntry() {
        return this.currEntry;
    }
    
    protected final void setCurrentEntry(final TarArchiveEntry currEntry) {
        this.currEntry = currEntry;
    }
    
    protected final boolean isAtEOF() {
        return this.hasHitEOF;
    }
    
    protected final void setAtEOF(final boolean hasHitEOF) {
        this.hasHitEOF = hasHitEOF;
    }
    
    private void consumeRemainderOfLastBlock() throws IOException {
        final long n = this.getBytesRead() % this.blockSize;
        if (n > 0L) {
            this.count(IOUtils.skip(this.is, this.blockSize - n));
        }
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 265 && ((ArchiveUtils.matchAsciiBuffer("ustar\u0000", array, 257, 6) && ArchiveUtils.matchAsciiBuffer("00", array, 263, 2)) || (ArchiveUtils.matchAsciiBuffer("ustar ", array, 257, 6) && (ArchiveUtils.matchAsciiBuffer(" \u0000", array, 263, 2) || ArchiveUtils.matchAsciiBuffer("0\u0000", array, 263, 2))) || (ArchiveUtils.matchAsciiBuffer("ustar\u0000", array, 257, 6) && ArchiveUtils.matchAsciiBuffer("\u0000\u0000", array, 263, 2)));
    }
}
