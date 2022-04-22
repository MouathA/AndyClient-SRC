package org.apache.commons.compress.archivers.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.apache.commons.compress.archivers.*;
import java.nio.*;

public class ZipArchiveOutputStream extends ArchiveOutputStream
{
    static final int BUFFER_SIZE = 512;
    protected boolean finished;
    private static final int DEFLATER_BLOCK_SIZE = 8192;
    public static final int DEFLATED = 8;
    public static final int DEFAULT_COMPRESSION = -1;
    public static final int STORED = 0;
    static final String DEFAULT_ENCODING = "UTF8";
    @Deprecated
    public static final int EFS_FLAG = 2048;
    private static final byte[] EMPTY;
    private CurrentEntry entry;
    private String comment;
    private int level;
    private boolean hasCompressionLevelChanged;
    private int method;
    private final List entries;
    private final CRC32 crc;
    private long written;
    private long cdOffset;
    private long cdLength;
    private static final byte[] ZERO;
    private static final byte[] LZERO;
    private final Map offsets;
    private String encoding;
    private ZipEncoding zipEncoding;
    protected final Deflater def;
    private final byte[] buf;
    private final RandomAccessFile raf;
    private final OutputStream out;
    private boolean useUTF8Flag;
    private boolean fallbackToUTF8;
    private UnicodeExtraFieldPolicy createUnicodeExtraFields;
    private boolean hasUsedZip64;
    private Zip64Mode zip64Mode;
    static final byte[] LFH_SIG;
    static final byte[] DD_SIG;
    static final byte[] CFH_SIG;
    static final byte[] EOCD_SIG;
    static final byte[] ZIP64_EOCD_SIG;
    static final byte[] ZIP64_EOCD_LOC_SIG;
    private static final byte[] ONE;
    
    public ZipArchiveOutputStream(final OutputStream out) {
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.crc = new CRC32();
        this.written = 0L;
        this.cdOffset = 0L;
        this.cdLength = 0L;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.def = new Deflater(this.level, true);
        this.buf = new byte[512];
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        this.out = out;
        this.raf = null;
    }
    
    public ZipArchiveOutputStream(final File file) throws IOException {
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.crc = new CRC32();
        this.written = 0L;
        this.cdOffset = 0L;
        this.cdLength = 0L;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.def = new Deflater(this.level, true);
        this.buf = new byte[512];
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        final OutputStream out = null;
        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(0L);
        this.out = out;
        this.raf = raf;
    }
    
    public boolean isSeekable() {
        return this.raf != null;
    }
    
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
        if (this.useUTF8Flag && !ZipEncodingHelper.isUTF8(encoding)) {
            this.useUTF8Flag = false;
        }
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void setUseLanguageEncodingFlag(final boolean b) {
        this.useUTF8Flag = (b && ZipEncodingHelper.isUTF8(this.encoding));
    }
    
    public void setCreateUnicodeExtraFields(final UnicodeExtraFieldPolicy createUnicodeExtraFields) {
        this.createUnicodeExtraFields = createUnicodeExtraFields;
    }
    
    public void setFallbackToUTF8(final boolean fallbackToUTF8) {
        this.fallbackToUTF8 = fallbackToUTF8;
    }
    
    public void setUseZip64(final Zip64Mode zip64Mode) {
        this.zip64Mode = zip64Mode;
    }
    
    @Override
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        this.cdOffset = this.written;
        final Iterator<ZipArchiveEntry> iterator = this.entries.iterator();
        while (iterator.hasNext()) {
            this.writeCentralFileHeader(iterator.next());
        }
        this.cdLength = this.written - this.cdOffset;
        this.writeZip64CentralDirectory();
        this.writeCentralDirectoryEnd();
        this.offsets.clear();
        this.entries.clear();
        this.def.end();
        this.finished = true;
    }
    
    @Override
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry == null) {
            throw new IOException("No current entry to close");
        }
        if (!CurrentEntry.access$000(this.entry)) {
            this.write(ZipArchiveOutputStream.EMPTY, 0, 0);
        }
        this.flushDeflater();
        final Zip64Mode effectiveZip64Mode = this.getEffectiveZip64Mode(CurrentEntry.access$100(this.entry));
        final long n = this.written - CurrentEntry.access$200(this.entry);
        final long value = this.crc.getValue();
        this.crc.reset();
        final boolean handleSizesAndCrc = this.handleSizesAndCrc(n, value, effectiveZip64Mode);
        if (this.raf != null) {
            this.rewriteSizesAndCrc(handleSizesAndCrc);
        }
        this.writeDataDescriptor(CurrentEntry.access$100(this.entry));
        this.entry = null;
    }
    
    private void flushDeflater() throws IOException {
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            this.def.finish();
            while (!this.def.finished()) {
                this.deflate();
            }
        }
    }
    
    private boolean handleSizesAndCrc(final long compressedSize, final long n, final Zip64Mode zip64Mode) throws ZipException {
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            CurrentEntry.access$100(this.entry).setSize(CurrentEntry.access$300(this.entry));
            CurrentEntry.access$100(this.entry).setCompressedSize(compressedSize);
            CurrentEntry.access$100(this.entry).setCrc(n);
            this.def.reset();
        }
        else if (this.raf == null) {
            if (CurrentEntry.access$100(this.entry).getCrc() != n) {
                throw new ZipException("bad CRC checksum for entry " + CurrentEntry.access$100(this.entry).getName() + ": " + Long.toHexString(CurrentEntry.access$100(this.entry).getCrc()) + " instead of " + Long.toHexString(n));
            }
            if (CurrentEntry.access$100(this.entry).getSize() != compressedSize) {
                throw new ZipException("bad size for entry " + CurrentEntry.access$100(this.entry).getName() + ": " + CurrentEntry.access$100(this.entry).getSize() + " instead of " + compressedSize);
            }
        }
        else {
            CurrentEntry.access$100(this.entry).setSize(compressedSize);
            CurrentEntry.access$100(this.entry).setCompressedSize(compressedSize);
            CurrentEntry.access$100(this.entry).setCrc(n);
        }
        final boolean b = zip64Mode == Zip64Mode.Always || CurrentEntry.access$100(this.entry).getSize() >= 4294967295L || CurrentEntry.access$100(this.entry).getCompressedSize() >= 4294967295L;
        if (b && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(CurrentEntry.access$100(this.entry)));
        }
        return b;
    }
    
    private void rewriteSizesAndCrc(final boolean b) throws IOException {
        final long filePointer = this.raf.getFilePointer();
        this.raf.seek(CurrentEntry.access$400(this.entry));
        this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getCrc()));
        if (CurrentEntry.access$100(this.entry) == null || !b) {
            this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getCompressedSize()));
            this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getSize()));
        }
        else {
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        }
        if (CurrentEntry.access$100(this.entry) != null) {
            this.raf.seek(CurrentEntry.access$400(this.entry) + 12L + 4L + this.getName(CurrentEntry.access$100(this.entry)).limit() + 4L);
            this.writeOut(ZipEightByteInteger.getBytes(CurrentEntry.access$100(this.entry).getSize()));
            this.writeOut(ZipEightByteInteger.getBytes(CurrentEntry.access$100(this.entry).getCompressedSize()));
            if (!b) {
                this.raf.seek(CurrentEntry.access$400(this.entry) - 10L);
                this.writeOut(ZipShort.getBytes(10));
                CurrentEntry.access$100(this.entry).removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                CurrentEntry.access$100(this.entry).setExtra();
                if (CurrentEntry.access$500(this.entry)) {
                    this.hasUsedZip64 = false;
                }
            }
        }
        this.raf.seek(filePointer);
    }
    
    @Override
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry != null) {
            this.closeArchiveEntry();
        }
        this.entry = new CurrentEntry((ZipArchiveEntry)archiveEntry, null);
        this.entries.add(CurrentEntry.access$100(this.entry));
        this.setDefaults(CurrentEntry.access$100(this.entry));
        final Zip64Mode effectiveZip64Mode = this.getEffectiveZip64Mode(CurrentEntry.access$100(this.entry));
        this.validateSizeInformation(effectiveZip64Mode);
        if (CurrentEntry.access$100(this.entry) != effectiveZip64Mode) {
            final Zip64ExtendedInformationExtraField zip64Extra = this.getZip64Extra(CurrentEntry.access$100(this.entry));
            ZipEightByteInteger zero = ZipEightByteInteger.ZERO;
            if (CurrentEntry.access$100(this.entry).getMethod() == 0 && CurrentEntry.access$100(this.entry).getSize() != -1L) {
                zero = new ZipEightByteInteger(CurrentEntry.access$100(this.entry).getSize());
            }
            zip64Extra.setSize(zero);
            zip64Extra.setCompressedSize(zero);
            CurrentEntry.access$100(this.entry).setExtra();
        }
        if (CurrentEntry.access$100(this.entry).getMethod() == 8 && this.hasCompressionLevelChanged) {
            this.def.setLevel(this.level);
            this.hasCompressionLevelChanged = false;
        }
        this.writeLocalFileHeader(CurrentEntry.access$100(this.entry));
    }
    
    private void setDefaults(final ZipArchiveEntry zipArchiveEntry) {
        if (zipArchiveEntry.getMethod() == -1) {
            zipArchiveEntry.setMethod(this.method);
        }
        if (zipArchiveEntry.getTime() == -1L) {
            zipArchiveEntry.setTime(System.currentTimeMillis());
        }
    }
    
    private void validateSizeInformation(final Zip64Mode zip64Mode) throws ZipException {
        if (CurrentEntry.access$100(this.entry).getMethod() == 0 && this.raf == null) {
            if (CurrentEntry.access$100(this.entry).getSize() == -1L) {
                throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
            }
            if (CurrentEntry.access$100(this.entry).getCrc() == -1L) {
                throw new ZipException("crc checksum is required for STORED method when not writing to a file");
            }
            CurrentEntry.access$100(this.entry).setCompressedSize(CurrentEntry.access$100(this.entry).getSize());
        }
        if ((CurrentEntry.access$100(this.entry).getSize() >= 4294967295L || CurrentEntry.access$100(this.entry).getCompressedSize() >= 4294967295L) && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(CurrentEntry.access$100(this.entry)));
        }
    }
    
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    public void setLevel(final int level) {
        if (level < -1 || level > 9) {
            throw new IllegalArgumentException("Invalid compression level: " + level);
        }
        this.hasCompressionLevelChanged = (this.level != level);
        this.level = level;
    }
    
    public void setMethod(final int method) {
        this.method = method;
    }
    
    @Override
    public boolean canWriteEntryData(final ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof ZipArchiveEntry) {
            final ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)archiveEntry;
            return zipArchiveEntry.getMethod() != ZipMethod.IMPLODING.getCode() && zipArchiveEntry.getMethod() != ZipMethod.UNSHRINKING.getCode() && ZipUtil.canHandleEntryData(zipArchiveEntry);
        }
        return false;
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.entry == null) {
            throw new IllegalStateException("No current entry");
        }
        ZipUtil.checkRequestedFeatures(CurrentEntry.access$100(this.entry));
        CurrentEntry.access$002(this.entry, true);
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            this.writeDeflated(array, n, n2);
        }
        else {
            this.writeOut(array, n, n2);
            this.written += n2;
        }
        this.crc.update(array, n, n2);
        this.count(n2);
    }
    
    private void writeDeflated(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 > 0 && !this.def.finished()) {
            CurrentEntry.access$314(this.entry, n2);
            if (n2 <= 8192) {
                this.def.setInput(array, n, n2);
                this.deflateUntilInputIsNeeded();
            }
            else {
                final int n3 = n2 / 8192;
                while (0 < n3) {
                    this.def.setInput(array, n + 0, 8192);
                    this.deflateUntilInputIsNeeded();
                    int n4 = 0;
                    ++n4;
                }
                int n4 = n3 * 8192;
                if (0 < n2) {
                    this.def.setInput(array, n + 0, n2 - 0);
                    this.deflateUntilInputIsNeeded();
                }
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.destroy();
    }
    
    @Override
    public void flush() throws IOException {
        if (this.out != null) {
            this.out.flush();
        }
    }
    
    protected final void deflate() throws IOException {
        final int deflate = this.def.deflate(this.buf, 0, this.buf.length);
        if (deflate > 0) {
            this.writeOut(this.buf, 0, deflate);
            this.written += deflate;
        }
    }
    
    protected void writeLocalFileHeader(final ZipArchiveEntry zipArchiveEntry) throws IOException {
        final boolean canEncode = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        final ByteBuffer name = this.getName(zipArchiveEntry);
        if (this.createUnicodeExtraFields != UnicodeExtraFieldPolicy.NEVER) {
            this.addUnicodeExtraFields(zipArchiveEntry, canEncode, name);
        }
        this.offsets.put(zipArchiveEntry, this.written);
        this.writeOut(ZipArchiveOutputStream.LFH_SIG);
        this.written += 4L;
        final int method = zipArchiveEntry.getMethod();
        this.writeVersionNeededToExtractAndGeneralPurposeBits(method, !canEncode && this.fallbackToUTF8, this.hasZip64Extra(zipArchiveEntry));
        this.written += 4L;
        this.writeOut(ZipShort.getBytes(method));
        this.written += 2L;
        this.writeOut(ZipUtil.toDosTime(zipArchiveEntry.getTime()));
        this.written += 4L;
        CurrentEntry.access$402(this.entry, this.written);
        if (method == 8 || this.raf != null) {
            this.writeOut(ZipArchiveOutputStream.LZERO);
            if (CurrentEntry.access$100(this.entry) != null) {
                this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            }
            else {
                this.writeOut(ZipArchiveOutputStream.LZERO);
                this.writeOut(ZipArchiveOutputStream.LZERO);
            }
        }
        else {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
            byte[] array = ZipLong.ZIP64_MAGIC.getBytes();
            if (zipArchiveEntry != null) {
                array = ZipLong.getBytes(zipArchiveEntry.getSize());
            }
            this.writeOut(array);
            this.writeOut(array);
        }
        this.written += 12L;
        this.writeOut(ZipShort.getBytes(name.limit()));
        this.written += 2L;
        final byte[] localFileDataExtra = zipArchiveEntry.getLocalFileDataExtra();
        this.writeOut(ZipShort.getBytes(localFileDataExtra.length));
        this.written += 2L;
        this.writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
        this.written += name.limit();
        this.writeOut(localFileDataExtra);
        this.written += localFileDataExtra.length;
        CurrentEntry.access$202(this.entry, this.written);
    }
    
    private void addUnicodeExtraFields(final ZipArchiveEntry zipArchiveEntry, final boolean b, final ByteBuffer byteBuffer) throws IOException {
        if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !b) {
            zipArchiveEntry.addExtraField(new UnicodePathExtraField(zipArchiveEntry.getName(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position()));
        }
        final String comment = zipArchiveEntry.getComment();
        if (comment != null && !"".equals(comment)) {
            final boolean canEncode = this.zipEncoding.canEncode(comment);
            if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !canEncode) {
                final ByteBuffer encode = this.getEntryEncoding(zipArchiveEntry).encode(comment);
                zipArchiveEntry.addExtraField(new UnicodeCommentExtraField(comment, encode.array(), encode.arrayOffset(), encode.limit() - encode.position()));
            }
        }
    }
    
    protected void writeDataDescriptor(final ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry.getMethod() != 8 || this.raf != null) {
            return;
        }
        this.writeOut(ZipArchiveOutputStream.DD_SIG);
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
        if (zipArchiveEntry != null) {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getSize()));
        }
        else {
            this.writeOut(ZipEightByteInteger.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipEightByteInteger.getBytes(zipArchiveEntry.getSize()));
        }
        this.written += 24;
    }
    
    protected void writeCentralFileHeader(final ZipArchiveEntry zipArchiveEntry) throws IOException {
        this.writeOut(ZipArchiveOutputStream.CFH_SIG);
        this.written += 4L;
        final long longValue = this.offsets.get(zipArchiveEntry);
        final boolean b = zipArchiveEntry == null || zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L || longValue >= 4294967295L;
        if (b && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        this.handleZip64Extra(zipArchiveEntry, longValue, b);
        this.writeOut(ZipShort.getBytes(zipArchiveEntry.getPlatform() << 8 | (this.hasUsedZip64 ? 45 : 20)));
        this.written += 2L;
        final int method = zipArchiveEntry.getMethod();
        this.writeVersionNeededToExtractAndGeneralPurposeBits(method, !this.zipEncoding.canEncode(zipArchiveEntry.getName()) && this.fallbackToUTF8, b);
        this.written += 4L;
        this.writeOut(ZipShort.getBytes(method));
        this.written += 2L;
        this.writeOut(ZipUtil.toDosTime(zipArchiveEntry.getTime()));
        this.written += 4L;
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
        if (zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L) {
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        }
        else {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getSize()));
        }
        this.written += 12L;
        final ByteBuffer name = this.getName(zipArchiveEntry);
        this.writeOut(ZipShort.getBytes(name.limit()));
        this.written += 2L;
        final byte[] centralDirectoryExtra = zipArchiveEntry.getCentralDirectoryExtra();
        this.writeOut(ZipShort.getBytes(centralDirectoryExtra.length));
        this.written += 2L;
        String comment = zipArchiveEntry.getComment();
        if (comment == null) {
            comment = "";
        }
        final ByteBuffer encode = this.getEntryEncoding(zipArchiveEntry).encode(comment);
        this.writeOut(ZipShort.getBytes(encode.limit()));
        this.written += 2L;
        this.writeOut(ZipArchiveOutputStream.ZERO);
        this.written += 2L;
        this.writeOut(ZipShort.getBytes(zipArchiveEntry.getInternalAttributes()));
        this.written += 2L;
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getExternalAttributes()));
        this.written += 4L;
        this.writeOut(ZipLong.getBytes(Math.min(longValue, 4294967295L)));
        this.written += 4L;
        this.writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
        this.written += name.limit();
        this.writeOut(centralDirectoryExtra);
        this.written += centralDirectoryExtra.length;
        this.writeOut(encode.array(), encode.arrayOffset(), encode.limit() - encode.position());
        this.written += encode.limit();
    }
    
    private void handleZip64Extra(final ZipArchiveEntry zipArchiveEntry, final long n, final boolean b) {
        if (b) {
            final Zip64ExtendedInformationExtraField zip64Extra = this.getZip64Extra(zipArchiveEntry);
            if (zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L) {
                zip64Extra.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
                zip64Extra.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            }
            else {
                zip64Extra.setCompressedSize(null);
                zip64Extra.setSize(null);
            }
            if (n >= 4294967295L) {
                zip64Extra.setRelativeHeaderOffset(new ZipEightByteInteger(n));
            }
            zipArchiveEntry.setExtra();
        }
    }
    
    protected void writeCentralDirectoryEnd() throws IOException {
        this.writeOut(ZipArchiveOutputStream.EOCD_SIG);
        this.writeOut(ZipArchiveOutputStream.ZERO);
        this.writeOut(ZipArchiveOutputStream.ZERO);
        final int size = this.entries.size();
        if (size > 65535 && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive contains more than 65535 entries.");
        }
        if (this.cdOffset > 4294967295L && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        final byte[] bytes = ZipShort.getBytes(Math.min(size, 65535));
        this.writeOut(bytes);
        this.writeOut(bytes);
        this.writeOut(ZipLong.getBytes(Math.min(this.cdLength, 4294967295L)));
        this.writeOut(ZipLong.getBytes(Math.min(this.cdOffset, 4294967295L)));
        final ByteBuffer encode = this.zipEncoding.encode(this.comment);
        this.writeOut(ZipShort.getBytes(encode.limit()));
        this.writeOut(encode.array(), encode.arrayOffset(), encode.limit() - encode.position());
    }
    
    protected void writeZip64CentralDirectory() throws IOException {
        if (this.zip64Mode == Zip64Mode.Never) {
            return;
        }
        if (!this.hasUsedZip64 && (this.cdOffset >= 4294967295L || this.cdLength >= 4294967295L || this.entries.size() >= 65535)) {
            this.hasUsedZip64 = true;
        }
        if (!this.hasUsedZip64) {
            return;
        }
        final long written = this.written;
        this.writeOut(ZipArchiveOutputStream.ZIP64_EOCD_SIG);
        this.writeOut(ZipEightByteInteger.getBytes(44L));
        this.writeOut(ZipShort.getBytes(45));
        this.writeOut(ZipShort.getBytes(45));
        this.writeOut(ZipArchiveOutputStream.LZERO);
        this.writeOut(ZipArchiveOutputStream.LZERO);
        final byte[] bytes = ZipEightByteInteger.getBytes(this.entries.size());
        this.writeOut(bytes);
        this.writeOut(bytes);
        this.writeOut(ZipEightByteInteger.getBytes(this.cdLength));
        this.writeOut(ZipEightByteInteger.getBytes(this.cdOffset));
        this.writeOut(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG);
        this.writeOut(ZipArchiveOutputStream.LZERO);
        this.writeOut(ZipEightByteInteger.getBytes(written));
        this.writeOut(ZipArchiveOutputStream.ONE);
    }
    
    protected final void writeOut(final byte[] array) throws IOException {
        this.writeOut(array, 0, array.length);
    }
    
    protected final void writeOut(final byte[] array, final int n, final int n2) throws IOException {
        if (this.raf != null) {
            this.raf.write(array, n, n2);
        }
        else {
            this.out.write(array, n, n2);
        }
    }
    
    private void deflateUntilInputIsNeeded() throws IOException {
        while (!this.def.needsInput()) {
            this.deflate();
        }
    }
    
    private void writeVersionNeededToExtractAndGeneralPurposeBits(final int n, final boolean b, final boolean b2) throws IOException {
        final GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useUTF8ForNames(this.useUTF8Flag || b);
        if (n == 8 && this.raf == null) {
            generalPurposeBit.useDataDescriptor(true);
        }
        if (b2) {}
        this.writeOut(ZipShort.getBytes(45));
        this.writeOut(generalPurposeBit.encode());
    }
    
    @Override
    public ArchiveEntry createArchiveEntry(final File file, final String s) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ZipArchiveEntry(file, s);
    }
    
    private Zip64ExtendedInformationExtraField getZip64Extra(final ZipArchiveEntry zipArchiveEntry) {
        if (this.entry != null) {
            CurrentEntry.access$502(this.entry, !this.hasUsedZip64);
        }
        this.hasUsedZip64 = true;
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField == null) {
            zip64ExtendedInformationExtraField = new Zip64ExtendedInformationExtraField();
        }
        zipArchiveEntry.addAsFirstExtraField(zip64ExtendedInformationExtraField);
        return zip64ExtendedInformationExtraField;
    }
    
    private Zip64Mode getEffectiveZip64Mode(final ZipArchiveEntry zipArchiveEntry) {
        if (this.zip64Mode != Zip64Mode.AsNeeded || this.raf != null || zipArchiveEntry.getMethod() != 8 || zipArchiveEntry.getSize() != -1L) {
            return this.zip64Mode;
        }
        return Zip64Mode.Never;
    }
    
    private ZipEncoding getEntryEncoding(final ZipArchiveEntry zipArchiveEntry) {
        return (!this.zipEncoding.canEncode(zipArchiveEntry.getName()) && this.fallbackToUTF8) ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
    }
    
    private ByteBuffer getName(final ZipArchiveEntry zipArchiveEntry) throws IOException {
        return this.getEntryEncoding(zipArchiveEntry).encode(zipArchiveEntry.getName());
    }
    
    void destroy() throws IOException {
        if (this.raf != null) {
            this.raf.close();
        }
        if (this.out != null) {
            this.out.close();
        }
    }
    
    static {
        EMPTY = new byte[0];
        ZERO = new byte[] { 0, 0 };
        LZERO = new byte[] { 0, 0, 0, 0 };
        LFH_SIG = ZipLong.LFH_SIG.getBytes();
        DD_SIG = ZipLong.DD_SIG.getBytes();
        CFH_SIG = ZipLong.CFH_SIG.getBytes();
        EOCD_SIG = ZipLong.getBytes(101010256L);
        ZIP64_EOCD_SIG = ZipLong.getBytes(101075792L);
        ZIP64_EOCD_LOC_SIG = ZipLong.getBytes(117853008L);
        ONE = ZipLong.getBytes(1L);
    }
    
    private static final class CurrentEntry
    {
        private final ZipArchiveEntry entry;
        private long localDataStart;
        private long dataStart;
        private long bytesRead;
        private boolean causedUseOfZip64;
        private boolean hasWritten;
        
        private CurrentEntry(final ZipArchiveEntry entry) {
            this.localDataStart = 0L;
            this.dataStart = 0L;
            this.bytesRead = 0L;
            this.causedUseOfZip64 = false;
            this.entry = entry;
        }
        
        static boolean access$000(final CurrentEntry currentEntry) {
            return currentEntry.hasWritten;
        }
        
        static ZipArchiveEntry access$100(final CurrentEntry currentEntry) {
            return currentEntry.entry;
        }
        
        static long access$200(final CurrentEntry currentEntry) {
            return currentEntry.dataStart;
        }
        
        static long access$300(final CurrentEntry currentEntry) {
            return currentEntry.bytesRead;
        }
        
        static long access$400(final CurrentEntry currentEntry) {
            return currentEntry.localDataStart;
        }
        
        static boolean access$500(final CurrentEntry currentEntry) {
            return currentEntry.causedUseOfZip64;
        }
        
        CurrentEntry(final ZipArchiveEntry zipArchiveEntry, final ZipArchiveOutputStream$1 object) {
            this(zipArchiveEntry);
        }
        
        static boolean access$002(final CurrentEntry currentEntry, final boolean hasWritten) {
            return currentEntry.hasWritten = hasWritten;
        }
        
        static long access$314(final CurrentEntry currentEntry, final long n) {
            return currentEntry.bytesRead += n;
        }
        
        static long access$402(final CurrentEntry currentEntry, final long localDataStart) {
            return currentEntry.localDataStart = localDataStart;
        }
        
        static long access$202(final CurrentEntry currentEntry, final long dataStart) {
            return currentEntry.dataStart = dataStart;
        }
        
        static boolean access$502(final CurrentEntry currentEntry, final boolean causedUseOfZip64) {
            return currentEntry.causedUseOfZip64 = causedUseOfZip64;
        }
    }
    
    public static final class UnicodeExtraFieldPolicy
    {
        public static final UnicodeExtraFieldPolicy ALWAYS;
        public static final UnicodeExtraFieldPolicy NEVER;
        public static final UnicodeExtraFieldPolicy NOT_ENCODEABLE;
        private final String name;
        
        private UnicodeExtraFieldPolicy(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            ALWAYS = new UnicodeExtraFieldPolicy("always");
            NEVER = new UnicodeExtraFieldPolicy("never");
            NOT_ENCODEABLE = new UnicodeExtraFieldPolicy("not encodeable");
        }
    }
}
