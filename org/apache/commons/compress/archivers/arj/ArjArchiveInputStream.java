package org.apache.commons.compress.archivers.arj;

import java.util.zip.*;
import java.io.*;
import java.util.*;
import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.*;

public class ArjArchiveInputStream extends ArchiveInputStream
{
    private static final int ARJ_MAGIC_1 = 96;
    private static final int ARJ_MAGIC_2 = 234;
    private final DataInputStream in;
    private final String charsetName;
    private final MainHeader mainHeader;
    private LocalFileHeader currentLocalFileHeader;
    private InputStream currentInputStream;
    
    public ArjArchiveInputStream(final InputStream inputStream, final String charsetName) throws ArchiveException {
        this.currentLocalFileHeader = null;
        this.currentInputStream = null;
        this.in = new DataInputStream(inputStream);
        this.charsetName = charsetName;
        this.mainHeader = this.readMainHeader();
        if ((this.mainHeader.arjFlags & 0x1) != 0x0) {
            throw new ArchiveException("Encrypted ARJ files are unsupported");
        }
        if ((this.mainHeader.arjFlags & 0x4) != 0x0) {
            throw new ArchiveException("Multi-volume ARJ files are unsupported");
        }
    }
    
    public ArjArchiveInputStream(final InputStream inputStream) throws ArchiveException {
        this(inputStream, "CP437");
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    private int read8(final DataInputStream dataInputStream) throws IOException {
        final int unsignedByte = dataInputStream.readUnsignedByte();
        this.count(1);
        return unsignedByte;
    }
    
    private int read16(final DataInputStream dataInputStream) throws IOException {
        final int unsignedShort = dataInputStream.readUnsignedShort();
        this.count(2);
        return Integer.reverseBytes(unsignedShort) >>> 16;
    }
    
    private int read32(final DataInputStream dataInputStream) throws IOException {
        final int int1 = dataInputStream.readInt();
        this.count(4);
        return Integer.reverseBytes(int1);
    }
    
    private String readString(final DataInputStream dataInputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int unsignedByte;
        while ((unsignedByte = dataInputStream.readUnsignedByte()) != 0) {
            byteArrayOutputStream.write(unsignedByte);
        }
        if (this.charsetName != null) {
            return new String(byteArrayOutputStream.toByteArray(), this.charsetName);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
    
    private void readFully(final DataInputStream dataInputStream, final byte[] array) throws IOException {
        dataInputStream.readFully(array);
        this.count(array.length);
    }
    
    private byte[] readHeader() throws IOException {
        byte[] array = null;
        do {
            int n = this.read8(this.in);
            do {
                n = this.read8(this.in);
            } while (0 != 96 && n != 234);
            final int read16 = this.read16(this.in);
            if (read16 == 0) {
                return null;
            }
            if (read16 > 2600) {
                continue;
            }
            array = new byte[read16];
            this.readFully(this.in, array);
            final long n2 = (long)this.read32(this.in) & 0xFFFFFFFFL;
            final CRC32 crc32 = new CRC32();
            crc32.update(array);
            if (n2 == crc32.getValue()) {}
        } while (!true);
        return array;
    }
    
    private MainHeader readMainHeader() throws IOException {
        final byte[] header = this.readHeader();
        if (header == null) {
            throw new IOException("Archive ends without any headers");
        }
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(header));
        final int unsignedByte = dataInputStream.readUnsignedByte();
        final byte[] array = new byte[unsignedByte - 1];
        dataInputStream.readFully(array);
        final DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(array));
        final MainHeader mainHeader = new MainHeader();
        mainHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        mainHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        mainHeader.hostOS = dataInputStream2.readUnsignedByte();
        mainHeader.arjFlags = dataInputStream2.readUnsignedByte();
        mainHeader.securityVersion = dataInputStream2.readUnsignedByte();
        mainHeader.fileType = dataInputStream2.readUnsignedByte();
        mainHeader.reserved = dataInputStream2.readUnsignedByte();
        mainHeader.dateTimeCreated = this.read32(dataInputStream2);
        mainHeader.dateTimeModified = this.read32(dataInputStream2);
        mainHeader.archiveSize = (0xFFFFFFFFL & (long)this.read32(dataInputStream2));
        mainHeader.securityEnvelopeFilePosition = this.read32(dataInputStream2);
        mainHeader.fileSpecPosition = this.read16(dataInputStream2);
        mainHeader.securityEnvelopeLength = this.read16(dataInputStream2);
        this.pushedBackBytes(20L);
        mainHeader.encryptionVersion = dataInputStream2.readUnsignedByte();
        mainHeader.lastChapter = dataInputStream2.readUnsignedByte();
        if (unsignedByte >= 33) {
            mainHeader.arjProtectionFactor = dataInputStream2.readUnsignedByte();
            mainHeader.arjFlags2 = dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
        }
        mainHeader.name = this.readString(dataInputStream);
        mainHeader.comment = this.readString(dataInputStream);
        final int read16 = this.read16(this.in);
        if (read16 > 0) {
            mainHeader.extendedHeaderBytes = new byte[read16];
            this.readFully(this.in, mainHeader.extendedHeaderBytes);
            final long n = 0xFFFFFFFFL & (long)this.read32(this.in);
            final CRC32 crc32 = new CRC32();
            crc32.update(mainHeader.extendedHeaderBytes);
            if (n != crc32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
        }
        return mainHeader;
    }
    
    private LocalFileHeader readLocalFileHeader() throws IOException {
        final byte[] header = this.readHeader();
        if (header == null) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(header));
        final int unsignedByte = dataInputStream.readUnsignedByte();
        final byte[] array = new byte[unsignedByte - 1];
        dataInputStream.readFully(array);
        final DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(array));
        final LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        localFileHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        localFileHeader.hostOS = dataInputStream2.readUnsignedByte();
        localFileHeader.arjFlags = dataInputStream2.readUnsignedByte();
        localFileHeader.method = dataInputStream2.readUnsignedByte();
        localFileHeader.fileType = dataInputStream2.readUnsignedByte();
        localFileHeader.reserved = dataInputStream2.readUnsignedByte();
        localFileHeader.dateTimeModified = this.read32(dataInputStream2);
        localFileHeader.compressedSize = (0xFFFFFFFFL & (long)this.read32(dataInputStream2));
        localFileHeader.originalSize = (0xFFFFFFFFL & (long)this.read32(dataInputStream2));
        localFileHeader.originalCrc32 = (0xFFFFFFFFL & (long)this.read32(dataInputStream2));
        localFileHeader.fileSpecPosition = this.read16(dataInputStream2);
        localFileHeader.fileAccessMode = this.read16(dataInputStream2);
        this.pushedBackBytes(20L);
        localFileHeader.firstChapter = dataInputStream2.readUnsignedByte();
        localFileHeader.lastChapter = dataInputStream2.readUnsignedByte();
        this.readExtraData(unsignedByte, dataInputStream2, localFileHeader);
        localFileHeader.name = this.readString(dataInputStream);
        localFileHeader.comment = this.readString(dataInputStream);
        final ArrayList<byte[]> list = new ArrayList<byte[]>();
        int read16;
        while ((read16 = this.read16(this.in)) > 0) {
            final byte[] array2 = new byte[read16];
            this.readFully(this.in, array2);
            final long n = 0xFFFFFFFFL & (long)this.read32(this.in);
            final CRC32 crc32 = new CRC32();
            crc32.update(array2);
            if (n != crc32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
            list.add(array2);
        }
        localFileHeader.extendedHeaders = list.toArray(new byte[list.size()][]);
        return localFileHeader;
    }
    
    private void readExtraData(final int n, final DataInputStream dataInputStream, final LocalFileHeader localFileHeader) throws IOException {
        if (n >= 33) {
            localFileHeader.extendedFilePosition = this.read32(dataInputStream);
            if (n >= 45) {
                localFileHeader.dateTimeAccessed = this.read32(dataInputStream);
                localFileHeader.dateTimeCreated = this.read32(dataInputStream);
                localFileHeader.originalSizeEvenForVolumes = this.read32(dataInputStream);
                this.pushedBackBytes(12L);
            }
            this.pushedBackBytes(4L);
        }
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 2 && (0xFF & array[0]) == 0x60 && (0xFF & array[1]) == 0xEA;
    }
    
    public String getArchiveName() {
        return this.mainHeader.name;
    }
    
    public String getArchiveComment() {
        return this.mainHeader.comment;
    }
    
    @Override
    public ArjArchiveEntry getNextEntry() throws IOException {
        if (this.currentInputStream != null) {
            IOUtils.skip(this.currentInputStream, Long.MAX_VALUE);
            this.currentInputStream.close();
            this.currentLocalFileHeader = null;
            this.currentInputStream = null;
        }
        this.currentLocalFileHeader = this.readLocalFileHeader();
        if (this.currentLocalFileHeader != null) {
            this.currentInputStream = new BoundedInputStream(this.in, this.currentLocalFileHeader.compressedSize);
            if (this.currentLocalFileHeader.method == 0) {
                this.currentInputStream = new CRC32VerifyingInputStream(this.currentInputStream, this.currentLocalFileHeader.originalSize, this.currentLocalFileHeader.originalCrc32);
            }
            return new ArjArchiveEntry(this.currentLocalFileHeader);
        }
        this.currentInputStream = null;
        return null;
    }
    
    @Override
    public boolean canReadEntryData(final ArchiveEntry archiveEntry) {
        return archiveEntry instanceof ArjArchiveEntry && ((ArjArchiveEntry)archiveEntry).getMethod() == 0;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.currentLocalFileHeader == null) {
            throw new IllegalStateException("No current arj entry");
        }
        if (this.currentLocalFileHeader.method != 0) {
            throw new IOException("Unsupported compression method " + this.currentLocalFileHeader.method);
        }
        return this.currentInputStream.read(array, n, n2);
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextEntry();
    }
}
