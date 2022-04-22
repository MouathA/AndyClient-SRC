package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.utils.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;

public class ZipFile implements Closeable
{
    private static final int HASH_SIZE = 509;
    static final int NIBLET_MASK = 15;
    static final int BYTE_SHIFT = 8;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private final List entries;
    private final Map nameMap;
    private final String encoding;
    private final ZipEncoding zipEncoding;
    private final String archiveName;
    private final RandomAccessFile archive;
    private final boolean useUnicodeExtraFields;
    private boolean closed;
    private final byte[] DWORD_BUF;
    private final byte[] WORD_BUF;
    private final byte[] CFH_BUF;
    private final byte[] SHORT_BUF;
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG;
    static final int MIN_EOCD_SIZE = 22;
    private static final int MAX_EOCD_SIZE = 65557;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26L;
    private final Comparator OFFSET_COMPARATOR;
    
    public ZipFile(final File file) throws IOException {
        this(file, "UTF8");
    }
    
    public ZipFile(final String s) throws IOException {
        this(new File(s), "UTF8");
    }
    
    public ZipFile(final String s, final String s2) throws IOException {
        this(new File(s), s2, true);
    }
    
    public ZipFile(final File file, final String s) throws IOException {
        this(file, s, true);
    }
    
    public ZipFile(final File file, final String encoding, final boolean useUnicodeExtraFields) throws IOException {
        this.entries = new LinkedList();
        this.nameMap = new HashMap(509);
        this.DWORD_BUF = new byte[8];
        this.WORD_BUF = new byte[4];
        this.CFH_BUF = new byte[42];
        this.SHORT_BUF = new byte[2];
        this.OFFSET_COMPARATOR = new Comparator() {
            final ZipFile this$0;
            
            public int compare(final ZipArchiveEntry zipArchiveEntry, final ZipArchiveEntry zipArchiveEntry2) {
                if (zipArchiveEntry == zipArchiveEntry2) {
                    return 0;
                }
                final Entry entry = (zipArchiveEntry instanceof Entry) ? ((Entry)zipArchiveEntry) : null;
                final Entry entry2 = (zipArchiveEntry2 instanceof Entry) ? ((Entry)zipArchiveEntry2) : null;
                if (entry == null) {
                    return 1;
                }
                if (entry2 == null) {
                    return -1;
                }
                final long n = OffsetEntry.access$200(entry.getOffsetEntry()) - OffsetEntry.access$200(entry2.getOffsetEntry());
                return (n == 0L) ? 0 : ((n < 0L) ? -1 : 1);
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((ZipArchiveEntry)o, (ZipArchiveEntry)o2);
            }
        };
        this.archiveName = file.getAbsolutePath();
        this.encoding = encoding;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
        this.useUnicodeExtraFields = useUnicodeExtraFields;
        this.archive = new RandomAccessFile(file, "r");
        this.resolveLocalFileHeaderData(this.populateFromCentralDirectory());
        if (!true) {
            this.closed = true;
            IOUtils.closeQuietly(this.archive);
        }
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }
    
    public static void closeQuietly(final ZipFile zipFile) {
        IOUtils.closeQuietly(zipFile);
    }
    
    public Enumeration getEntries() {
        return Collections.enumeration((Collection<Object>)this.entries);
    }
    
    public Enumeration getEntriesInPhysicalOrder() {
        final ZipArchiveEntry[] array = this.entries.toArray(new ZipArchiveEntry[0]);
        Arrays.sort(array, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(array));
    }
    
    public ZipArchiveEntry getEntry(final String s) {
        final LinkedList<ZipArchiveEntry> list = this.nameMap.get(s);
        return (list != null) ? list.getFirst() : null;
    }
    
    public Iterable getEntries(final String s) {
        final List<Object> list = this.nameMap.get(s);
        return (list != null) ? list : Collections.emptyList();
    }
    
    public Iterable getEntriesInPhysicalOrder(final String s) {
        ZipArchiveEntry[] array = new ZipArchiveEntry[0];
        if (this.nameMap.containsKey(s)) {
            array = (ZipArchiveEntry[])this.nameMap.get(s).toArray(array);
            Arrays.sort(array, this.OFFSET_COMPARATOR);
        }
        return Arrays.asList(array);
    }
    
    public boolean canReadEntryData(final ZipArchiveEntry zipArchiveEntry) {
        return ZipUtil.canHandleEntryData(zipArchiveEntry);
    }
    
    public InputStream getInputStream(final ZipArchiveEntry zipArchiveEntry) throws IOException, ZipException {
        if (!(zipArchiveEntry instanceof Entry)) {
            return null;
        }
        final OffsetEntry offsetEntry = ((Entry)zipArchiveEntry).getOffsetEntry();
        ZipUtil.checkRequestedFeatures(zipArchiveEntry);
        final BoundedInputStream boundedInputStream = new BoundedInputStream(OffsetEntry.access$000(offsetEntry), zipArchiveEntry.getCompressedSize());
        switch (ZipMethod.getMethodByCode(zipArchiveEntry.getMethod())) {
            case STORED: {
                return boundedInputStream;
            }
            case UNSHRINKING: {
                return new UnshrinkingInputStream(boundedInputStream);
            }
            case IMPLODING: {
                return new ExplodingInputStream(zipArchiveEntry.getGeneralPurposeBit().getSlidingDictionarySize(), zipArchiveEntry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(boundedInputStream));
            }
            case DEFLATED: {
                boundedInputStream.addDummy();
                final Inflater inflater = new Inflater(true);
                return new InflaterInputStream((InputStream)boundedInputStream, inflater, inflater) {
                    final Inflater val$inflater;
                    final ZipFile this$0;
                    
                    @Override
                    public void close() throws IOException {
                        super.close();
                        this.val$inflater.end();
                    }
                };
            }
            default: {
                throw new ZipException("Found unsupported compression method " + zipArchiveEntry.getMethod());
            }
        }
    }
    
    public String getUnixSymlink(final ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry != null && zipArchiveEntry.isUnixSymlink()) {
            final InputStream inputStream = this.getInputStream(zipArchiveEntry);
            final String decode = this.zipEncoding.decode(IOUtils.toByteArray(inputStream));
            if (inputStream != null) {
                inputStream.close();
            }
            return decode;
        }
        return null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (!this.closed) {
            System.err.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
            this.close();
        }
        super.finalize();
    }
    
    private Map populateFromCentralDirectory() throws IOException {
        final HashMap hashMap = new HashMap();
        this.positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long n = ZipLong.getValue(this.WORD_BUF);
        if (n != ZipFile.CFH_SIG && this.startsWithLocalFileHeader()) {
            throw new IOException("central directory is empty, can't expand corrupt archive.");
        }
        while (n == ZipFile.CFH_SIG) {
            this.readCentralDirectoryEntry(hashMap);
            this.archive.readFully(this.WORD_BUF);
            n = ZipLong.getValue(this.WORD_BUF);
        }
        return hashMap;
    }
    
    private void readCentralDirectoryEntry(final Map map) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        final OffsetEntry offsetEntry = new OffsetEntry(null);
        final Entry entry = new Entry(offsetEntry);
        final int value = ZipShort.getValue(this.CFH_BUF, 0);
        int n = 0;
        n += 2;
        entry.setPlatform(value >> 8 & 0xF);
        n += 2;
        final GeneralPurposeBit parse = GeneralPurposeBit.parse(this.CFH_BUF, 0);
        final boolean usesUTF8ForNames = parse.usesUTF8ForNames();
        final ZipEncoding zipEncoding = usesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        entry.setGeneralPurposeBit(parse);
        n += 2;
        entry.setMethod(ZipShort.getValue(this.CFH_BUF, 0));
        n += 2;
        entry.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.CFH_BUF, 0)));
        n += 4;
        entry.setCrc(ZipLong.getValue(this.CFH_BUF, 0));
        n += 4;
        entry.setCompressedSize(ZipLong.getValue(this.CFH_BUF, 0));
        n += 4;
        entry.setSize(ZipLong.getValue(this.CFH_BUF, 0));
        n += 4;
        final int value2 = ZipShort.getValue(this.CFH_BUF, 0);
        n += 2;
        final int value3 = ZipShort.getValue(this.CFH_BUF, 0);
        n += 2;
        final int value4 = ZipShort.getValue(this.CFH_BUF, 0);
        n += 2;
        final int value5 = ZipShort.getValue(this.CFH_BUF, 0);
        n += 2;
        entry.setInternalAttributes(ZipShort.getValue(this.CFH_BUF, 0));
        n += 2;
        entry.setExternalAttributes(ZipLong.getValue(this.CFH_BUF, 0));
        n += 4;
        final byte[] array = new byte[value2];
        this.archive.readFully(array);
        entry.setName(zipEncoding.decode(array), array);
        OffsetEntry.access$202(offsetEntry, ZipLong.getValue(this.CFH_BUF, 0));
        this.entries.add(entry);
        final byte[] centralDirectoryExtra = new byte[value3];
        this.archive.readFully(centralDirectoryExtra);
        entry.setCentralDirectoryExtra(centralDirectoryExtra);
        this.setSizesAndOffsetFromZip64Extra(entry, offsetEntry, value5);
        final byte[] array2 = new byte[value4];
        this.archive.readFully(array2);
        entry.setComment(zipEncoding.decode(array2));
        if (!usesUTF8ForNames && this.useUnicodeExtraFields) {
            map.put(entry, new NameAndComment(array, array2, null));
        }
    }
    
    private void setSizesAndOffsetFromZip64Extra(final ZipArchiveEntry zipArchiveEntry, final OffsetEntry offsetEntry, final int n) throws IOException {
        final Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField != null) {
            final boolean b = zipArchiveEntry.getSize() == 4294967295L;
            final boolean b2 = zipArchiveEntry.getCompressedSize() == 4294967295L;
            final boolean b3 = OffsetEntry.access$200(offsetEntry) == 4294967295L;
            zip64ExtendedInformationExtraField.reparseCentralDirectoryData(b, b2, b3, n == 65535);
            if (b) {
                zipArchiveEntry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            }
            else if (b2) {
                zip64ExtendedInformationExtraField.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            }
            if (b2) {
                zipArchiveEntry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
            }
            else if (b) {
                zip64ExtendedInformationExtraField.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
            }
            if (b3) {
                OffsetEntry.access$202(offsetEntry, zip64ExtendedInformationExtraField.getRelativeHeaderOffset().getLongValue());
            }
        }
    }
    
    private void positionAtCentralDirectory() throws IOException {
        this.positionAtEndOfCentralDirectoryRecord();
        final boolean b = this.archive.getFilePointer() > 20L;
        if (b) {
            this.archive.seek(this.archive.getFilePointer() - 20L);
            this.archive.readFully(this.WORD_BUF);
            Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (!false) {
            if (b) {
                this.skipBytes(16);
            }
            this.positionAtCentralDirectory32();
        }
        else {
            this.positionAtCentralDirectory64();
        }
    }
    
    private void positionAtCentralDirectory64() throws IOException {
        this.skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (!Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
            throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
        }
        this.skipBytes(44);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
    }
    
    private void positionAtCentralDirectory32() throws IOException {
        this.skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }
    
    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        if (!this.tryToLocateSignature(22L, 65557L, ZipArchiveOutputStream.EOCD_SIG)) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }
    
    private boolean tryToLocateSignature(final long n, final long n2, final byte[] array) throws IOException {
        long n3 = this.archive.length() - n;
        final long max = Math.max(0L, this.archive.length() - n2);
        if (n3 >= 0L) {
            while (n3 >= max) {
                this.archive.seek(n3);
                final int read = this.archive.read();
                if (read == -1) {
                    break;
                }
                if (read == array[0] && this.archive.read() == array[1] && this.archive.read() == array[2] && this.archive.read() == array[3]) {
                    break;
                }
                --n3;
            }
        }
        if (true) {
            this.archive.seek(n3);
        }
        return true;
    }
    
    private void skipBytes(final int n) throws IOException {
        while (0 < n) {
            if (this.archive.skipBytes(n - 0) <= 0) {
                throw new EOFException();
            }
        }
    }
    
    private void resolveLocalFileHeaderData(final Map map) throws IOException {
        for (final Entry entry : this.entries) {
            final OffsetEntry offsetEntry = entry.getOffsetEntry();
            final long access$200 = OffsetEntry.access$200(offsetEntry);
            this.archive.seek(access$200 + 26L);
            this.archive.readFully(this.SHORT_BUF);
            final int value = ZipShort.getValue(this.SHORT_BUF);
            this.archive.readFully(this.SHORT_BUF);
            final int value2 = ZipShort.getValue(this.SHORT_BUF);
            int skipBytes;
            for (int i = value; i > 0; i -= skipBytes) {
                skipBytes = this.archive.skipBytes(i);
                if (skipBytes <= 0) {
                    throw new IOException("failed to skip file name in local file header");
                }
            }
            final byte[] extra = new byte[value2];
            this.archive.readFully(extra);
            entry.setExtra(extra);
            OffsetEntry.access$002(offsetEntry, access$200 + 26L + 2L + 2L + value + value2);
            if (map.containsKey(entry)) {
                final NameAndComment nameAndComment = map.get(entry);
                ZipUtil.setNameAndCommentFromExtraFields(entry, NameAndComment.access$400(nameAndComment), NameAndComment.access$500(nameAndComment));
            }
            final String name = entry.getName();
            LinkedList<?> list = this.nameMap.get(name);
            if (list == null) {
                list = new LinkedList<Object>();
                this.nameMap.put(name, list);
            }
            list.addLast(entry);
        }
    }
    
    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0L);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.LFH_SIG);
    }
    
    static RandomAccessFile access$600(final ZipFile zipFile) {
        return zipFile.archive;
    }
    
    static {
        CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
    }
    
    private static class Entry extends ZipArchiveEntry
    {
        private final OffsetEntry offsetEntry;
        
        Entry(final OffsetEntry offsetEntry) {
            this.offsetEntry = offsetEntry;
        }
        
        OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }
        
        @Override
        public int hashCode() {
            return 3 * super.hashCode() + (int)(OffsetEntry.access$200(this.offsetEntry) % 2147483647L);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (super.equals(o)) {
                final Entry entry = (Entry)o;
                return OffsetEntry.access$200(this.offsetEntry) == OffsetEntry.access$200(entry.offsetEntry) && OffsetEntry.access$000(this.offsetEntry) == OffsetEntry.access$000(entry.offsetEntry);
            }
            return false;
        }
    }
    
    private static final class OffsetEntry
    {
        private long headerOffset;
        private long dataOffset;
        
        private OffsetEntry() {
            this.headerOffset = -1L;
            this.dataOffset = -1L;
        }
        
        static long access$000(final OffsetEntry offsetEntry) {
            return offsetEntry.dataOffset;
        }
        
        OffsetEntry(final ZipFile$1 inflaterInputStream) {
            this();
        }
        
        static long access$202(final OffsetEntry offsetEntry, final long headerOffset) {
            return offsetEntry.headerOffset = headerOffset;
        }
        
        static long access$200(final OffsetEntry offsetEntry) {
            return offsetEntry.headerOffset;
        }
        
        static long access$002(final OffsetEntry offsetEntry, final long dataOffset) {
            return offsetEntry.dataOffset = dataOffset;
        }
    }
    
    private static final class NameAndComment
    {
        private final byte[] name;
        private final byte[] comment;
        
        private NameAndComment(final byte[] name, final byte[] comment) {
            this.name = name;
            this.comment = comment;
        }
        
        NameAndComment(final byte[] array, final byte[] array2, final ZipFile$1 inflaterInputStream) {
            this(array, array2);
        }
        
        static byte[] access$400(final NameAndComment nameAndComment) {
            return nameAndComment.name;
        }
        
        static byte[] access$500(final NameAndComment nameAndComment) {
            return nameAndComment.comment;
        }
    }
    
    private class BoundedInputStream extends InputStream
    {
        private long remaining;
        private long loc;
        private boolean addDummyByte;
        final ZipFile this$0;
        
        BoundedInputStream(final ZipFile this$0, final long loc, final long remaining) {
            this.this$0 = this$0;
            this.addDummyByte = false;
            this.remaining = remaining;
            this.loc = loc;
        }
        
        @Override
        public int read() throws IOException {
            if (this.remaining-- > 0L) {
                // monitorenter(access$600 = ZipFile.access$600(this.this$0))
                ZipFile.access$600(this.this$0).seek(this.loc++);
                // monitorexit(access$600)
                return ZipFile.access$600(this.this$0).read();
            }
            if (this.addDummyByte) {
                this.addDummyByte = false;
                return 0;
            }
            return -1;
        }
        
        @Override
        public int read(final byte[] array, final int n, int n2) throws IOException {
            if (this.remaining <= 0L) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    array[n] = 0;
                    return 1;
                }
                return -1;
            }
            else {
                if (n2 <= 0) {
                    return 0;
                }
                if (n2 > this.remaining) {
                    n2 = (int)this.remaining;
                }
                // monitorenter(access$600 = ZipFile.access$600(this.this$0))
                ZipFile.access$600(this.this$0).seek(this.loc);
                ZipFile.access$600(this.this$0).read(array, n, n2);
                // monitorexit(access$600)
                if (-1 > 0) {
                    --this.loc;
                    ++this.remaining;
                }
                return -1;
            }
        }
        
        void addDummy() {
            this.addDummyByte = true;
        }
    }
}
