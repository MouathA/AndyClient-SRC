package org.apache.commons.compress.archivers.zip;

import java.util.zip.*;

public abstract class AbstractUnicodeExtraField implements ZipExtraField
{
    private long nameCRC32;
    private byte[] unicodeName;
    private byte[] data;
    
    protected AbstractUnicodeExtraField() {
    }
    
    protected AbstractUnicodeExtraField(final String s, final byte[] array, final int n, final int n2) {
        final CRC32 crc32 = new CRC32();
        crc32.update(array, n, n2);
        this.nameCRC32 = crc32.getValue();
        this.unicodeName = s.getBytes("UTF-8");
    }
    
    protected AbstractUnicodeExtraField(final String s, final byte[] array) {
        this(s, array, 0, array.length);
    }
    
    private void assembleData() {
        if (this.unicodeName == null) {
            return;
        }
        (this.data = new byte[5 + this.unicodeName.length])[0] = 1;
        System.arraycopy(ZipLong.getBytes(this.nameCRC32), 0, this.data, 1, 4);
        System.arraycopy(this.unicodeName, 0, this.data, 5, this.unicodeName.length);
    }
    
    public long getNameCRC32() {
        return this.nameCRC32;
    }
    
    public void setNameCRC32(final long nameCRC32) {
        this.nameCRC32 = nameCRC32;
        this.data = null;
    }
    
    public byte[] getUnicodeName() {
        Object o = null;
        if (this.unicodeName != null) {
            o = new byte[this.unicodeName.length];
            System.arraycopy(this.unicodeName, 0, o, 0, ((byte[])o).length);
        }
        return (byte[])o;
    }
    
    public void setUnicodeName(final byte[] array) {
        if (array != null) {
            System.arraycopy(array, 0, this.unicodeName = new byte[array.length], 0, array.length);
        }
        else {
            this.unicodeName = null;
        }
        this.data = null;
    }
    
    public byte[] getCentralDirectoryData() {
        if (this.data == null) {
            this.assembleData();
        }
        Object o = null;
        if (this.data != null) {
            o = new byte[this.data.length];
            System.arraycopy(this.data, 0, o, 0, ((byte[])o).length);
        }
        return (byte[])o;
    }
    
    public ZipShort getCentralDirectoryLength() {
        if (this.data == null) {
            this.assembleData();
        }
        return new ZipShort((this.data != null) ? this.data.length : 0);
    }
    
    public byte[] getLocalFileDataData() {
        return this.getCentralDirectoryData();
    }
    
    public ZipShort getLocalFileDataLength() {
        return this.getCentralDirectoryLength();
    }
    
    public void parseFromLocalFileData(final byte[] array, final int n, final int n2) throws ZipException {
        if (n2 < 5) {
            throw new ZipException("UniCode path extra data must have at least 5 bytes.");
        }
        final byte b = array[n];
        if (b != 1) {
            throw new ZipException("Unsupported version [" + b + "] for UniCode path extra data.");
        }
        this.nameCRC32 = ZipLong.getValue(array, n + 1);
        System.arraycopy(array, n + 5, this.unicodeName = new byte[n2 - 5], 0, n2 - 5);
        this.data = null;
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) throws ZipException {
        this.parseFromLocalFileData(array, n, n2);
    }
}
