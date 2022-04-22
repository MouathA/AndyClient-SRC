package org.apache.commons.compress.archivers.dump;

import org.apache.commons.compress.archivers.zip.*;
import java.io.*;
import java.util.*;
import org.apache.commons.compress.archivers.*;

public class DumpArchiveInputStream extends ArchiveInputStream
{
    private DumpArchiveSummary summary;
    private DumpArchiveEntry active;
    private boolean isClosed;
    private boolean hasHitEOF;
    private long entrySize;
    private long entryOffset;
    private int readIdx;
    private final byte[] readBuf;
    private byte[] blockBuffer;
    private int recordOffset;
    private long filepos;
    protected TapeInputStream raw;
    private final Map names;
    private final Map pending;
    private Queue queue;
    private final ZipEncoding encoding;
    
    public DumpArchiveInputStream(final InputStream inputStream) throws ArchiveException {
        this(inputStream, null);
    }
    
    public DumpArchiveInputStream(final InputStream inputStream, final String s) throws ArchiveException {
        this.readBuf = new byte[1024];
        this.names = new HashMap();
        this.pending = new HashMap();
        this.raw = new TapeInputStream(inputStream);
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(s);
        final byte[] record = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(record)) {
            throw new UnrecognizedFormatException();
        }
        this.summary = new DumpArchiveSummary(record, this.encoding);
        this.raw.resetBlockSize(this.summary.getNTRec(), this.summary.isCompressed());
        this.blockBuffer = new byte[4096];
        this.readCLRI();
        this.readBITS();
        this.names.put(2, new Dirent(2, 2, 4, "."));
        this.queue = new PriorityQueue(10, new Comparator() {
            final DumpArchiveInputStream this$0;
            
            public int compare(final DumpArchiveEntry dumpArchiveEntry, final DumpArchiveEntry dumpArchiveEntry2) {
                if (dumpArchiveEntry.getOriginalName() == null || dumpArchiveEntry2.getOriginalName() == null) {
                    return Integer.MAX_VALUE;
                }
                return dumpArchiveEntry.getOriginalName().compareTo(dumpArchiveEntry2.getOriginalName());
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((DumpArchiveEntry)o, (DumpArchiveEntry)o2);
            }
        });
    }
    
    @Deprecated
    @Override
    public int getCount() {
        return (int)this.getBytesRead();
    }
    
    @Override
    public long getBytesRead() {
        return this.raw.getBytesRead();
    }
    
    public DumpArchiveSummary getSummary() {
        return this.summary;
    }
    
    private void readCLRI() throws IOException {
        final byte[] record = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(record)) {
            throw new InvalidFormatException();
        }
        this.active = DumpArchiveEntry.parse(record);
        if (DumpArchiveConstants.SEGMENT_TYPE.CLRI != this.active.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(1024 * this.active.getHeaderCount()) == -1L) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }
    
    private void readBITS() throws IOException {
        final byte[] record = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(record)) {
            throw new InvalidFormatException();
        }
        this.active = DumpArchiveEntry.parse(record);
        if (DumpArchiveConstants.SEGMENT_TYPE.BITS != this.active.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(1024 * this.active.getHeaderCount()) == -1L) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }
    
    public DumpArchiveEntry getNextDumpEntry() throws IOException {
        return this.getNextEntry();
    }
    
    @Override
    public DumpArchiveEntry getNextEntry() throws IOException {
        DumpArchiveEntry active = null;
        String path = null;
        if (!this.queue.isEmpty()) {
            return this.queue.remove();
        }
        while (active == null) {
            if (this.hasHitEOF) {
                return null;
            }
            while (this.readIdx < this.active.getHeaderCount()) {
                if (!this.active.isSparseRecord(this.readIdx++) && this.raw.skip(1024L) == -1L) {
                    throw new EOFException();
                }
            }
            this.readIdx = 0;
            this.filepos = this.raw.getBytesRead();
            final byte[] record = this.raw.readRecord();
            if (!DumpArchiveUtil.verify(record)) {
                throw new InvalidFormatException();
            }
            this.active = DumpArchiveEntry.parse(record);
            while (DumpArchiveConstants.SEGMENT_TYPE.ADDR == this.active.getHeaderType()) {
                if (this.raw.skip(1024 * (this.active.getHeaderCount() - this.active.getHeaderHoles())) == -1L) {
                    throw new EOFException();
                }
                this.filepos = this.raw.getBytesRead();
                final byte[] record2 = this.raw.readRecord();
                if (!DumpArchiveUtil.verify(record2)) {
                    throw new InvalidFormatException();
                }
                this.active = DumpArchiveEntry.parse(record2);
            }
            if (DumpArchiveConstants.SEGMENT_TYPE.END == this.active.getHeaderType()) {
                this.hasHitEOF = true;
                return null;
            }
            active = this.active;
            if (active.isDirectory()) {
                this.readDirectoryEntry(this.active);
                this.entryOffset = 0L;
                this.entrySize = 0L;
                this.readIdx = this.active.getHeaderCount();
            }
            else {
                this.entryOffset = 0L;
                this.entrySize = this.active.getEntrySize();
                this.readIdx = 0;
            }
            this.recordOffset = this.readBuf.length;
            path = this.getPath(active);
            if (path != null) {
                continue;
            }
            active = null;
        }
        active.setName(path);
        active.setSimpleName(((Dirent)this.names.get(active.getIno())).getName());
        active.setOffset(this.filepos);
        return active;
    }
    
    private void readDirectoryEntry(DumpArchiveEntry parse) throws IOException {
        byte[] peek;
        for (long entrySize = parse.getEntrySize(); false || DumpArchiveConstants.SEGMENT_TYPE.ADDR == parse.getHeaderType(); parse = DumpArchiveEntry.parse(peek), entrySize -= 1024L) {
            if (!false) {
                this.raw.readRecord();
            }
            if (!this.names.containsKey(parse.getIno()) && DumpArchiveConstants.SEGMENT_TYPE.INODE == parse.getHeaderType()) {
                this.pending.put(parse.getIno(), parse);
            }
            final int n = 1024 * parse.getHeaderCount();
            if (this.blockBuffer.length < n) {
                this.blockBuffer = new byte[n];
            }
            if (this.raw.read(this.blockBuffer, 0, n) != n) {
                throw new EOFException();
            }
            while (0 < n - 8 && 0 < entrySize - 8L) {
                final int convert32 = DumpArchiveUtil.convert32(this.blockBuffer, 0);
                DumpArchiveUtil.convert16(this.blockBuffer, 4);
                final byte b = this.blockBuffer[6];
                final String decode = DumpArchiveUtil.decode(this.encoding, this.blockBuffer, 8, this.blockBuffer[7]);
                if (!".".equals(decode)) {
                    if ("..".equals(decode)) {
                        continue;
                    }
                    this.names.put(convert32, new Dirent(convert32, parse.getIno(), b, decode));
                    for (final Map.Entry<K, DumpArchiveEntry> entry : this.pending.entrySet()) {
                        final String path = this.getPath(entry.getValue());
                        if (path != null) {
                            entry.getValue().setName(path);
                            entry.getValue().setSimpleName(((Dirent)this.names.get(entry.getKey())).getName());
                            this.queue.add(entry.getValue());
                        }
                    }
                    final Iterator iterator2 = this.queue.iterator();
                    while (iterator2.hasNext()) {
                        this.pending.remove(iterator2.next().getIno());
                    }
                }
            }
            peek = this.raw.peek();
            if (!DumpArchiveUtil.verify(peek)) {
                throw new InvalidFormatException();
            }
        }
    }
    
    private String getPath(final DumpArchiveEntry dumpArchiveEntry) {
        final Stack<String> stack = new Stack<String>();
        int n = dumpArchiveEntry.getIno();
        while (true) {
            while (this.names.containsKey(n)) {
                final Dirent dirent = this.names.get(n);
                stack.push(dirent.getName());
                if (dirent.getIno() == dirent.getParentIno()) {
                    if (stack.isEmpty()) {
                        this.pending.put(dumpArchiveEntry.getIno(), dumpArchiveEntry);
                        return null;
                    }
                    final StringBuilder sb = new StringBuilder(stack.pop());
                    while (!stack.isEmpty()) {
                        sb.append('/');
                        sb.append(stack.pop());
                    }
                    return sb.toString();
                }
                else {
                    n = dirent.getParentIno();
                }
            }
            stack.clear();
            continue;
        }
    }
    
    @Override
    public int read(final byte[] array, int n, int i) throws IOException {
        if (this.hasHitEOF || this.isClosed || this.entryOffset >= this.entrySize) {
            return -1;
        }
        if (this.active == null) {
            throw new IllegalStateException("No current dump entry");
        }
        if (i + this.entryOffset > this.entrySize) {
            i = (int)(this.entrySize - this.entryOffset);
        }
        while (i > 0) {
            final int n2 = (i > this.readBuf.length - this.recordOffset) ? (this.readBuf.length - this.recordOffset) : i;
            if (this.recordOffset + n2 <= this.readBuf.length) {
                System.arraycopy(this.readBuf, this.recordOffset, array, n, n2);
                this.recordOffset += n2;
                i -= n2;
                n += n2;
            }
            if (i > 0) {
                if (this.readIdx >= 512) {
                    final byte[] record = this.raw.readRecord();
                    if (!DumpArchiveUtil.verify(record)) {
                        throw new InvalidFormatException();
                    }
                    this.active = DumpArchiveEntry.parse(record);
                    this.readIdx = 0;
                }
                if (!this.active.isSparseRecord(this.readIdx++)) {
                    if (this.raw.read(this.readBuf, 0, this.readBuf.length) != this.readBuf.length) {
                        throw new EOFException();
                    }
                }
                else {
                    Arrays.fill(this.readBuf, (byte)0);
                }
                this.recordOffset = 0;
            }
        }
        this.entryOffset += 0;
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.isClosed) {
            this.isClosed = true;
            this.raw.close();
        }
    }
    
    public static boolean matches(final byte[] array, final int n) {
        if (n < 32) {
            return false;
        }
        if (n >= 1024) {
            return DumpArchiveUtil.verify(array);
        }
        return 60012 == DumpArchiveUtil.convert32(array, 24);
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextEntry();
    }
}
