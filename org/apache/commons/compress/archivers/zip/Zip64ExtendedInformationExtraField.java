package org.apache.commons.compress.archivers.zip;

import java.util.zip.*;

public class Zip64ExtendedInformationExtraField implements ZipExtraField
{
    static final ZipShort HEADER_ID;
    private static final String LFH_MUST_HAVE_BOTH_SIZES_MSG = "Zip64 extended information must contain both size values in the local file header.";
    private static final byte[] EMPTY;
    private ZipEightByteInteger size;
    private ZipEightByteInteger compressedSize;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipLong diskStart;
    private byte[] rawCentralDirectoryData;
    
    public Zip64ExtendedInformationExtraField() {
    }
    
    public Zip64ExtendedInformationExtraField(final ZipEightByteInteger zipEightByteInteger, final ZipEightByteInteger zipEightByteInteger2) {
        this(zipEightByteInteger, zipEightByteInteger2, null, null);
    }
    
    public Zip64ExtendedInformationExtraField(final ZipEightByteInteger size, final ZipEightByteInteger compressedSize, final ZipEightByteInteger relativeHeaderOffset, final ZipLong diskStart) {
        this.size = size;
        this.compressedSize = compressedSize;
        this.relativeHeaderOffset = relativeHeaderOffset;
        this.diskStart = diskStart;
    }
    
    public ZipShort getHeaderId() {
        return Zip64ExtendedInformationExtraField.HEADER_ID;
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort((this.size != null) ? 16 : 0);
    }
    
    public ZipShort getCentralDirectoryLength() {
        return new ZipShort(((this.size != null) ? 8 : 0) + ((this.compressedSize != null) ? 8 : 0) + ((this.relativeHeaderOffset != null) ? 8 : 0) + ((this.diskStart != null) ? 4 : 0));
    }
    
    public byte[] getLocalFileDataData() {
        if (this.size == null && this.compressedSize == null) {
            return Zip64ExtendedInformationExtraField.EMPTY;
        }
        if (this.size == null || this.compressedSize == null) {
            throw new IllegalArgumentException("Zip64 extended information must contain both size values in the local file header.");
        }
        final byte[] array = new byte[16];
        this.addSizes(array);
        return array;
    }
    
    public byte[] getCentralDirectoryData() {
        final byte[] array = new byte[this.getCentralDirectoryLength().getValue()];
        int addSizes = this.addSizes(array);
        if (this.relativeHeaderOffset != null) {
            System.arraycopy(this.relativeHeaderOffset.getBytes(), 0, array, addSizes, 8);
            addSizes += 8;
        }
        if (this.diskStart != null) {
            System.arraycopy(this.diskStart.getBytes(), 0, array, addSizes, 4);
            addSizes += 4;
        }
        return array;
    }
    
    public void parseFromLocalFileData(final byte[] array, int n, final int n2) throws ZipException {
        if (n2 == 0) {
            return;
        }
        if (n2 < 16) {
            throw new ZipException("Zip64 extended information must contain both size values in the local file header.");
        }
        this.size = new ZipEightByteInteger(array, n);
        n += 8;
        this.compressedSize = new ZipEightByteInteger(array, n);
        n += 8;
        int n3 = n2 - 16;
        if (n3 >= 8) {
            this.relativeHeaderOffset = new ZipEightByteInteger(array, n);
            n += 8;
            n3 -= 8;
        }
        if (n3 >= 4) {
            this.diskStart = new ZipLong(array, n);
            n += 4;
            n3 -= 4;
        }
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, int n, final int n2) throws ZipException {
        System.arraycopy(array, n, this.rawCentralDirectoryData = new byte[n2], 0, n2);
        if (n2 >= 28) {
            this.parseFromLocalFileData(array, n, n2);
        }
        else if (n2 == 24) {
            this.size = new ZipEightByteInteger(array, n);
            n += 8;
            this.compressedSize = new ZipEightByteInteger(array, n);
            n += 8;
            this.relativeHeaderOffset = new ZipEightByteInteger(array, n);
        }
        else if (n2 % 8 == 4) {
            this.diskStart = new ZipLong(array, n + n2 - 4);
        }
    }
    
    public void reparseCentralDirectoryData(final boolean b, final boolean b2, final boolean b3, final boolean b4) throws ZipException {
        if (this.rawCentralDirectoryData != null) {
            final int n = (b ? 8 : 0) + (b2 ? 8 : 0) + (b3 ? 8 : 0) + (b4 ? 4 : 0);
            if (this.rawCentralDirectoryData.length < n) {
                throw new ZipException("central directory zip64 extended information extra field's length doesn't match central directory data.  Expected length " + n + " but is " + this.rawCentralDirectoryData.length);
            }
            int n2 = 0;
            if (b) {
                this.size = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                n2 += 8;
            }
            if (b2) {
                this.compressedSize = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                n2 += 8;
            }
            if (b3) {
                this.relativeHeaderOffset = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                n2 += 8;
            }
            if (b4) {
                this.diskStart = new ZipLong(this.rawCentralDirectoryData, 0);
                n2 += 4;
            }
        }
    }
    
    public ZipEightByteInteger getSize() {
        return this.size;
    }
    
    public void setSize(final ZipEightByteInteger size) {
        this.size = size;
    }
    
    public ZipEightByteInteger getCompressedSize() {
        return this.compressedSize;
    }
    
    public void setCompressedSize(final ZipEightByteInteger compressedSize) {
        this.compressedSize = compressedSize;
    }
    
    public ZipEightByteInteger getRelativeHeaderOffset() {
        return this.relativeHeaderOffset;
    }
    
    public void setRelativeHeaderOffset(final ZipEightByteInteger relativeHeaderOffset) {
        this.relativeHeaderOffset = relativeHeaderOffset;
    }
    
    public ZipLong getDiskStartNumber() {
        return this.diskStart;
    }
    
    public void setDiskStartNumber(final ZipLong diskStart) {
        this.diskStart = diskStart;
    }
    
    private int addSizes(final byte[] array) {
        int n = 0;
        if (this.size != null) {
            System.arraycopy(this.size.getBytes(), 0, array, 0, 8);
            n += 8;
        }
        if (this.compressedSize != null) {
            System.arraycopy(this.compressedSize.getBytes(), 0, array, 0, 8);
            n += 8;
        }
        return 0;
    }
    
    static {
        HEADER_ID = new ZipShort(1);
        EMPTY = new byte[0];
    }
}
