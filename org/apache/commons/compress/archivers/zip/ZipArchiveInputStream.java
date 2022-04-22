package org.apache.commons.compress.archivers.zip;

import java.nio.*;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.utils.*;
import java.io.*;
import java.util.zip.*;

public class ZipArchiveInputStream extends ArchiveInputStream
{
    private final ZipEncoding zipEncoding;
    private final boolean useUnicodeExtraFields;
    private final InputStream in;
    private final Inflater inf;
    private final ByteBuffer buf;
    private CurrentEntry current;
    private boolean closed;
    private boolean hitCentralDirectory;
    private ByteArrayInputStream lastStoredEntry;
    private boolean allowStoredEntriesWithDataDescriptor;
    private static final int LFH_LEN = 30;
    private static final int CFH_LEN = 46;
    private static final long TWO_EXP_32 = 4294967296L;
    private final byte[] LFH_BUF;
    private final byte[] SKIP_BUF;
    private final byte[] SHORT_BUF;
    private final byte[] WORD_BUF;
    private final byte[] TWO_DWORD_BUF;
    private int entriesRead;
    private static final byte[] LFH;
    private static final byte[] CFH;
    private static final byte[] DD;
    
    public ZipArchiveInputStream(final InputStream inputStream) {
        this(inputStream, "UTF8");
    }
    
    public ZipArchiveInputStream(final InputStream inputStream, final String s) {
        this(inputStream, s, true);
    }
    
    public ZipArchiveInputStream(final InputStream inputStream, final String s, final boolean b) {
        this(inputStream, s, b, false);
    }
    
    public ZipArchiveInputStream(final InputStream inputStream, final String s, final boolean useUnicodeExtraFields, final boolean allowStoredEntriesWithDataDescriptor) {
        this.inf = new Inflater(true);
        this.buf = ByteBuffer.allocate(512);
        this.current = null;
        this.closed = false;
        this.hitCentralDirectory = false;
        this.lastStoredEntry = null;
        this.allowStoredEntriesWithDataDescriptor = false;
        this.LFH_BUF = new byte[30];
        this.SKIP_BUF = new byte[1024];
        this.SHORT_BUF = new byte[2];
        this.WORD_BUF = new byte[4];
        this.TWO_DWORD_BUF = new byte[16];
        this.entriesRead = 0;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(s);
        this.useUnicodeExtraFields = useUnicodeExtraFields;
        this.in = new PushbackInputStream(inputStream, this.buf.capacity());
        this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
        this.buf.limit(0);
    }
    
    public ZipArchiveEntry getNextZipEntry() throws IOException {
        if (this.closed || this.hitCentralDirectory) {
            return null;
        }
        if (this.current != null) {
            this.closeEntry();
        }
        if (false) {
            this.readFirstLocalFileHeader(this.LFH_BUF);
        }
        else {
            this.readFully(this.LFH_BUF);
        }
        final ZipLong zipLong = new ZipLong(this.LFH_BUF);
        if (zipLong.equals(ZipLong.CFH_SIG) || zipLong.equals(ZipLong.AED_SIG)) {
            this.hitCentralDirectory = true;
            this.skipRemainderOfArchive();
        }
        if (!zipLong.equals(ZipLong.LFH_SIG)) {
            return null;
        }
        this.current = new CurrentEntry(null);
        final int value = ZipShort.getValue(this.LFH_BUF, 4);
        int n = 0;
        n += 2;
        CurrentEntry.access$100(this.current).setPlatform(value >> 8 & 0xF);
        final GeneralPurposeBit parse = GeneralPurposeBit.parse(this.LFH_BUF, 4);
        final boolean usesUTF8ForNames = parse.usesUTF8ForNames();
        final ZipEncoding zipEncoding = usesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        CurrentEntry.access$202(this.current, parse.usesDataDescriptor());
        CurrentEntry.access$100(this.current).setGeneralPurposeBit(parse);
        n += 2;
        CurrentEntry.access$100(this.current).setMethod(ZipShort.getValue(this.LFH_BUF, 4));
        n += 2;
        CurrentEntry.access$100(this.current).setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.LFH_BUF, 4)));
        n += 4;
        ZipLong zipLong2 = null;
        ZipLong zipLong3 = null;
        if (!CurrentEntry.access$200(this.current)) {
            CurrentEntry.access$100(this.current).setCrc(ZipLong.getValue(this.LFH_BUF, 4));
            n += 4;
            zipLong3 = new ZipLong(this.LFH_BUF, 4);
            n += 4;
            zipLong2 = new ZipLong(this.LFH_BUF, 4);
            n += 4;
        }
        else {
            n += 12;
        }
        final int value2 = ZipShort.getValue(this.LFH_BUF, 4);
        n += 2;
        final int value3 = ZipShort.getValue(this.LFH_BUF, 4);
        n += 2;
        final byte[] array = new byte[value2];
        this.readFully(array);
        CurrentEntry.access$100(this.current).setName(zipEncoding.decode(array), array);
        final byte[] extra = new byte[value3];
        this.readFully(extra);
        CurrentEntry.access$100(this.current).setExtra(extra);
        if (!usesUTF8ForNames && this.useUnicodeExtraFields) {
            ZipUtil.setNameAndCommentFromExtraFields(CurrentEntry.access$100(this.current), array, null);
        }
        this.processZip64Extra(zipLong2, zipLong3);
        if (CurrentEntry.access$100(this.current).getCompressedSize() != -1L) {
            if (CurrentEntry.access$100(this.current).getMethod() == ZipMethod.UNSHRINKING.getCode()) {
                CurrentEntry.access$302(this.current, new UnshrinkingInputStream(new BoundedInputStream(this.in, CurrentEntry.access$100(this.current).getCompressedSize())));
            }
            else if (CurrentEntry.access$100(this.current).getMethod() == ZipMethod.IMPLODING.getCode()) {
                CurrentEntry.access$302(this.current, new ExplodingInputStream(CurrentEntry.access$100(this.current).getGeneralPurposeBit().getSlidingDictionarySize(), CurrentEntry.access$100(this.current).getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BoundedInputStream(this.in, CurrentEntry.access$100(this.current).getCompressedSize())));
            }
        }
        ++this.entriesRead;
        return CurrentEntry.access$100(this.current);
    }
    
    private void readFirstLocalFileHeader(final byte[] array) throws IOException {
        this.readFully(array);
        final ZipLong zipLong = new ZipLong(array);
        if (zipLong.equals(ZipLong.DD_SIG)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.SPLITTING);
        }
        if (zipLong.equals(ZipLong.SINGLE_SEGMENT_SPLIT_MARKER)) {
            final byte[] array2 = new byte[4];
            this.readFully(array2);
            System.arraycopy(array, 4, array, 0, 26);
            System.arraycopy(array2, 0, array, 26, 4);
        }
    }
    
    private void processZip64Extra(final ZipLong zipLong, final ZipLong zipLong2) {
        final Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)CurrentEntry.access$100(this.current).getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        CurrentEntry.access$402(this.current, zip64ExtendedInformationExtraField != null);
        if (!CurrentEntry.access$200(this.current)) {
            if (zip64ExtendedInformationExtraField != null && (zipLong2.equals(ZipLong.ZIP64_MAGIC) || zipLong.equals(ZipLong.ZIP64_MAGIC))) {
                CurrentEntry.access$100(this.current).setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
                CurrentEntry.access$100(this.current).setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            }
            else {
                CurrentEntry.access$100(this.current).setCompressedSize(zipLong2.getValue());
                CurrentEntry.access$100(this.current).setSize(zipLong.getValue());
            }
        }
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextZipEntry();
    }
    
    @Override
    public boolean canReadEntryData(final ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof ZipArchiveEntry) {
            final ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)archiveEntry;
            return ZipUtil.canHandleEntryData(zipArchiveEntry) && this.supportsDataDescriptorFor(zipArchiveEntry);
        }
        return false;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        if (this.current == null) {
            return -1;
        }
        if (n > array.length || n2 < 0 || n < 0 || array.length - n < n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ZipUtil.checkRequestedFeatures(CurrentEntry.access$100(this.current));
        if (!this.supportsDataDescriptorFor(CurrentEntry.access$100(this.current))) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.DATA_DESCRIPTOR, CurrentEntry.access$100(this.current));
        }
        int n3;
        if (CurrentEntry.access$100(this.current).getMethod() == 0) {
            n3 = this.readStored(array, n, n2);
        }
        else if (CurrentEntry.access$100(this.current).getMethod() == 8) {
            n3 = this.readDeflated(array, n, n2);
        }
        else {
            if (CurrentEntry.access$100(this.current).getMethod() != ZipMethod.UNSHRINKING.getCode() && CurrentEntry.access$100(this.current).getMethod() != ZipMethod.IMPLODING.getCode()) {
                throw new UnsupportedZipFeatureException(ZipMethod.getMethodByCode(CurrentEntry.access$100(this.current).getMethod()), CurrentEntry.access$100(this.current));
            }
            n3 = CurrentEntry.access$300(this.current).read(array, n, n2);
        }
        if (n3 >= 0) {
            CurrentEntry.access$500(this.current).update(array, n, n3);
        }
        return n3;
    }
    
    private int readStored(final byte[] array, final int n, final int n2) throws IOException {
        if (CurrentEntry.access$200(this.current)) {
            if (this.lastStoredEntry == null) {
                this.readStoredEntry();
            }
            return this.lastStoredEntry.read(array, n, n2);
        }
        final long size = CurrentEntry.access$100(this.current).getSize();
        if (CurrentEntry.access$600(this.current) >= size) {
            return -1;
        }
        if (this.buf.position() >= this.buf.limit()) {
            this.buf.position(0);
            final int read = this.in.read(this.buf.array());
            if (read == -1) {
                return -1;
            }
            this.buf.limit(read);
            this.count(read);
            CurrentEntry.access$714(this.current, read);
        }
        int min = Math.min(this.buf.remaining(), n2);
        if (size - CurrentEntry.access$600(this.current) < min) {
            min = (int)(size - CurrentEntry.access$600(this.current));
        }
        this.buf.get(array, n, min);
        CurrentEntry.access$614(this.current, min);
        return min;
    }
    
    private int readDeflated(final byte[] array, final int n, final int n2) throws IOException {
        final int fromInflater = this.readFromInflater(array, n, n2);
        if (fromInflater <= 0) {
            if (this.inf.finished()) {
                return -1;
            }
            if (this.inf.needsDictionary()) {
                throw new ZipException("This archive needs a preset dictionary which is not supported by Commons Compress.");
            }
            if (fromInflater == -1) {
                throw new IOException("Truncated ZIP file");
            }
        }
        return fromInflater;
    }
    
    private int readFromInflater(final byte[] array, final int n, final int n2) throws IOException {
        do {
            if (this.inf.needsInput()) {
                final int fill = this.fill();
                if (fill > 0) {
                    CurrentEntry.access$714(this.current, this.buf.limit());
                }
                else {
                    if (fill == -1) {
                        return -1;
                    }
                    break;
                }
            }
            this.inf.inflate(array, n, n2);
        } while (!false && this.inf.needsInput());
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.in.close();
            this.inf.end();
        }
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n >= 0L) {
            long n2;
            int read;
            for (n2 = 0L; n2 < n; n2 += read) {
                final long n3 = n - n2;
                read = this.read(this.SKIP_BUF, 0, (int)((this.SKIP_BUF.length > n3) ? n3 : this.SKIP_BUF.length));
                if (read == -1) {
                    return n2;
                }
            }
            return n2;
        }
        throw new IllegalArgumentException();
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= ZipArchiveOutputStream.LFH_SIG.length && (checksig(array, ZipArchiveOutputStream.LFH_SIG) || checksig(array, ZipArchiveOutputStream.EOCD_SIG) || checksig(array, ZipArchiveOutputStream.DD_SIG) || checksig(array, ZipLong.SINGLE_SEGMENT_SPLIT_MARKER.getBytes()));
    }
    
    private static boolean checksig(final byte[] array, final byte[] array2) {
        while (0 < array2.length) {
            if (array[0] != array2[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private void closeEntry() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        if (this.current == null) {
            return;
        }
        if (CurrentEntry.access$700(this.current) <= CurrentEntry.access$100(this.current).getCompressedSize() && !CurrentEntry.access$200(this.current)) {
            this.drainCurrentEntryData();
        }
        else {
            this.skip(Long.MAX_VALUE);
            final int n = (int)(CurrentEntry.access$700(this.current) - ((CurrentEntry.access$100(this.current).getMethod() == 8) ? this.getBytesInflated() : CurrentEntry.access$600(this.current)));
            if (n > 0) {
                this.pushback(this.buf.array(), this.buf.limit() - n, n);
            }
        }
        if (this.lastStoredEntry == null && CurrentEntry.access$200(this.current)) {
            this.readDataDescriptor();
        }
        this.inf.reset();
        this.buf.clear().flip();
        this.current = null;
        this.lastStoredEntry = null;
    }
    
    private void drainCurrentEntryData() throws IOException {
        long n2;
        for (long n = CurrentEntry.access$100(this.current).getCompressedSize() - CurrentEntry.access$700(this.current); n > 0L; n -= n2) {
            n2 = this.in.read(this.buf.array(), 0, (int)Math.min(this.buf.capacity(), n));
            if (n2 < 0L) {
                throw new EOFException("Truncated ZIP entry: " + CurrentEntry.access$100(this.current).getName());
            }
            this.count(n2);
        }
    }
    
    private long getBytesInflated() {
        long bytesRead = this.inf.getBytesRead();
        if (CurrentEntry.access$700(this.current) >= 4294967296L) {
            while (bytesRead + 4294967296L <= CurrentEntry.access$700(this.current)) {
                bytesRead += 4294967296L;
            }
        }
        return bytesRead;
    }
    
    private int fill() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        final int read = this.in.read(this.buf.array());
        if (read > 0) {
            this.buf.limit(read);
            this.count(this.buf.limit());
            this.inf.setInput(this.buf.array(), 0, this.buf.limit());
        }
        return read;
    }
    
    private void readFully(final byte[] array) throws IOException {
        final int fully = IOUtils.readFully(this.in, array);
        this.count(fully);
        if (fully < array.length) {
            throw new EOFException();
        }
    }
    
    private void readDataDescriptor() throws IOException {
        this.readFully(this.WORD_BUF);
        ZipLong zipLong = new ZipLong(this.WORD_BUF);
        if (ZipLong.DD_SIG.equals(zipLong)) {
            this.readFully(this.WORD_BUF);
            zipLong = new ZipLong(this.WORD_BUF);
        }
        CurrentEntry.access$100(this.current).setCrc(zipLong.getValue());
        this.readFully(this.TWO_DWORD_BUF);
        final ZipLong zipLong2 = new ZipLong(this.TWO_DWORD_BUF, 8);
        if (zipLong2.equals(ZipLong.CFH_SIG) || zipLong2.equals(ZipLong.LFH_SIG)) {
            this.pushback(this.TWO_DWORD_BUF, 8, 8);
            CurrentEntry.access$100(this.current).setCompressedSize(ZipLong.getValue(this.TWO_DWORD_BUF));
            CurrentEntry.access$100(this.current).setSize(ZipLong.getValue(this.TWO_DWORD_BUF, 4));
        }
        else {
            CurrentEntry.access$100(this.current).setCompressedSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF));
            CurrentEntry.access$100(this.current).setSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF, 8));
        }
    }
    
    private boolean supportsDataDescriptorFor(final ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesDataDescriptor() || (this.allowStoredEntriesWithDataDescriptor && zipArchiveEntry.getMethod() == 0) || zipArchiveEntry.getMethod() == 8;
    }
    
    private void readStoredEntry() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final int n = CurrentEntry.access$400(this.current) ? 20 : 12;
        while (!false) {
            final int read = this.in.read(this.buf.array(), 0, 512);
            if (read <= 0) {
                throw new IOException("Truncated ZIP file");
            }
            if (read + 0 < 4) {
                continue;
            }
            this.bufferContainsSignature(byteArrayOutputStream, 0, read, n);
            if (false) {
                continue;
            }
            this.cacheBytesRead(byteArrayOutputStream, 0, read, n);
        }
        this.lastStoredEntry = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    
    private boolean bufferContainsSignature(final ByteArrayOutputStream byteArrayOutputStream, final int n, final int n2, final int n3) throws IOException {
        while (!true && 0 < n2 - 4) {
            if (this.buf.array()[0] == ZipArchiveInputStream.LFH[0] && this.buf.array()[1] == ZipArchiveInputStream.LFH[1]) {
                if ((this.buf.array()[2] != ZipArchiveInputStream.LFH[2] || this.buf.array()[3] != ZipArchiveInputStream.LFH[3]) && (this.buf.array()[0] != ZipArchiveInputStream.CFH[2] || this.buf.array()[3] != ZipArchiveInputStream.CFH[3])) {
                    if (this.buf.array()[2] == ZipArchiveInputStream.DD[2] && this.buf.array()[3] == ZipArchiveInputStream.DD[3]) {}
                }
                if (true) {
                    this.pushback(this.buf.array(), n + n2 - 0, 0);
                    byteArrayOutputStream.write(this.buf.array(), 0, 0);
                    this.readDataDescriptor();
                }
            }
            int n4 = 0;
            ++n4;
        }
        return true;
    }
    
    private int cacheBytesRead(final ByteArrayOutputStream byteArrayOutputStream, int n, final int n2, final int n3) {
        final int n4 = n + n2 - n3 - 3;
        if (n4 > 0) {
            byteArrayOutputStream.write(this.buf.array(), 0, n4);
            System.arraycopy(this.buf.array(), n4, this.buf.array(), 0, n3 + 3);
            n = n3 + 3;
        }
        else {
            n += n2;
        }
        return n;
    }
    
    private void pushback(final byte[] array, final int n, final int n2) throws IOException {
        ((PushbackInputStream)this.in).unread(array, n, n2);
        this.pushedBackBytes(n2);
    }
    
    private void skipRemainderOfArchive() throws IOException {
        this.realSkip(this.entriesRead * 46 - 30);
        this.findEocdRecord();
        this.realSkip(16L);
        this.readFully(this.SHORT_BUF);
        this.realSkip(ZipShort.getValue(this.SHORT_BUF));
    }
    
    private void findEocdRecord() throws IOException {
        int oneByte;
        while (false || (oneByte = this.readOneByte()) > -1) {
            if (!this.isFirstByteOfEocdSig(-1)) {
                continue;
            }
            this.readOneByte();
            if (-1 != ZipArchiveOutputStream.EOCD_SIG[1]) {
                if (-1 == -1) {
                    break;
                }
                this.isFirstByteOfEocdSig(-1);
            }
            else {
                this.readOneByte();
                if (-1 != ZipArchiveOutputStream.EOCD_SIG[2]) {
                    if (-1 == -1) {
                        break;
                    }
                    this.isFirstByteOfEocdSig(-1);
                }
                else {
                    this.readOneByte();
                    if (-1 == -1) {
                        break;
                    }
                    if (-1 == ZipArchiveOutputStream.EOCD_SIG[3]) {
                        break;
                    }
                    this.isFirstByteOfEocdSig(-1);
                }
            }
        }
    }
    
    private void realSkip(final long n) throws IOException {
        if (n >= 0L) {
            int read;
            for (long n2 = 0L; n2 < n; n2 += read) {
                final long n3 = n - n2;
                read = this.in.read(this.SKIP_BUF, 0, (int)((this.SKIP_BUF.length > n3) ? n3 : this.SKIP_BUF.length));
                if (read == -1) {
                    return;
                }
                this.count(read);
            }
            return;
        }
        throw new IllegalArgumentException();
    }
    
    private int readOneByte() throws IOException {
        final int read = this.in.read();
        if (read != -1) {
            this.count(1);
        }
        return read;
    }
    
    private boolean isFirstByteOfEocdSig(final int n) {
        return n == ZipArchiveOutputStream.EOCD_SIG[0];
    }
    
    static void access$800(final ZipArchiveInputStream zipArchiveInputStream, final int n) {
        zipArchiveInputStream.count(n);
    }
    
    static CurrentEntry access$900(final ZipArchiveInputStream zipArchiveInputStream) {
        return zipArchiveInputStream.current;
    }
    
    static void access$1000(final ZipArchiveInputStream zipArchiveInputStream, final int n) {
        zipArchiveInputStream.count(n);
    }
    
    static {
        LFH = ZipLong.LFH_SIG.getBytes();
        CFH = ZipLong.CFH_SIG.getBytes();
        DD = ZipLong.DD_SIG.getBytes();
    }
    
    private class BoundedInputStream extends InputStream
    {
        private final InputStream in;
        private final long max;
        private long pos;
        final ZipArchiveInputStream this$0;
        
        public BoundedInputStream(final ZipArchiveInputStream this$0, final InputStream in, final long max) {
            this.this$0 = this$0;
            this.pos = 0L;
            this.max = max;
            this.in = in;
        }
        
        @Override
        public int read() throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return -1;
            }
            final int read = this.in.read();
            ++this.pos;
            ZipArchiveInputStream.access$800(this.this$0, 1);
            CurrentEntry.access$708(ZipArchiveInputStream.access$900(this.this$0));
            return read;
        }
        
        @Override
        public int read(final byte[] array) throws IOException {
            return this.read(array, 0, array.length);
        }
        
        @Override
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return -1;
            }
            final int read = this.in.read(array, n, (int)((this.max >= 0L) ? Math.min(n2, this.max - this.pos) : n2));
            if (read == -1) {
                return -1;
            }
            this.pos += read;
            ZipArchiveInputStream.access$1000(this.this$0, read);
            CurrentEntry.access$714(ZipArchiveInputStream.access$900(this.this$0), read);
            return read;
        }
        
        @Override
        public long skip(final long n) throws IOException {
            final long skip = this.in.skip((this.max >= 0L) ? Math.min(n, this.max - this.pos) : n);
            this.pos += skip;
            return skip;
        }
        
        @Override
        public int available() throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return 0;
            }
            return this.in.available();
        }
    }
    
    private static final class CurrentEntry
    {
        private final ZipArchiveEntry entry;
        private boolean hasDataDescriptor;
        private boolean usesZip64;
        private long bytesRead;
        private long bytesReadFromStream;
        private final CRC32 crc;
        private InputStream in;
        
        private CurrentEntry() {
            this.entry = new ZipArchiveEntry();
            this.crc = new CRC32();
        }
        
        CurrentEntry(final ZipArchiveInputStream$1 object) {
            this();
        }
        
        static ZipArchiveEntry access$100(final CurrentEntry currentEntry) {
            return currentEntry.entry;
        }
        
        static boolean access$202(final CurrentEntry currentEntry, final boolean hasDataDescriptor) {
            return currentEntry.hasDataDescriptor = hasDataDescriptor;
        }
        
        static boolean access$200(final CurrentEntry currentEntry) {
            return currentEntry.hasDataDescriptor;
        }
        
        static InputStream access$302(final CurrentEntry currentEntry, final InputStream in) {
            return currentEntry.in = in;
        }
        
        static boolean access$402(final CurrentEntry currentEntry, final boolean usesZip64) {
            return currentEntry.usesZip64 = usesZip64;
        }
        
        static InputStream access$300(final CurrentEntry currentEntry) {
            return currentEntry.in;
        }
        
        static CRC32 access$500(final CurrentEntry currentEntry) {
            return currentEntry.crc;
        }
        
        static long access$600(final CurrentEntry currentEntry) {
            return currentEntry.bytesRead;
        }
        
        static long access$714(final CurrentEntry currentEntry, final long n) {
            return currentEntry.bytesReadFromStream += n;
        }
        
        static long access$614(final CurrentEntry currentEntry, final long n) {
            return currentEntry.bytesRead += n;
        }
        
        static long access$700(final CurrentEntry currentEntry) {
            return currentEntry.bytesReadFromStream;
        }
        
        static boolean access$400(final CurrentEntry currentEntry) {
            return currentEntry.usesZip64;
        }
        
        static long access$708(final CurrentEntry currentEntry) {
            return currentEntry.bytesReadFromStream++;
        }
    }
}
