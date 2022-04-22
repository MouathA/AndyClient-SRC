package org.apache.commons.compress.archivers.zip;

import java.util.zip.*;

public class AsiExtraField implements ZipExtraField, UnixStat, Cloneable
{
    private static final ZipShort HEADER_ID;
    private static final int WORD = 4;
    private int mode;
    private int uid;
    private int gid;
    private String link;
    private boolean dirFlag;
    private CRC32 crc;
    
    public AsiExtraField() {
        this.mode = 0;
        this.uid = 0;
        this.gid = 0;
        this.link = "";
        this.dirFlag = false;
        this.crc = new CRC32();
    }
    
    public ZipShort getHeaderId() {
        return AsiExtraField.HEADER_ID;
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(14 + this.getLinkedFile().getBytes().length);
    }
    
    public ZipShort getCentralDirectoryLength() {
        return this.getLocalFileDataLength();
    }
    
    public byte[] getLocalFileDataData() {
        final byte[] array = new byte[this.getLocalFileDataLength().getValue() - 4];
        System.arraycopy(ZipShort.getBytes(this.getMode()), 0, array, 0, 2);
        final byte[] bytes = this.getLinkedFile().getBytes();
        System.arraycopy(ZipLong.getBytes(bytes.length), 0, array, 2, 4);
        System.arraycopy(ZipShort.getBytes(this.getUserId()), 0, array, 6, 2);
        System.arraycopy(ZipShort.getBytes(this.getGroupId()), 0, array, 8, 2);
        System.arraycopy(bytes, 0, array, 10, bytes.length);
        this.crc.reset();
        this.crc.update(array);
        final long value = this.crc.getValue();
        final byte[] array2 = new byte[array.length + 4];
        System.arraycopy(ZipLong.getBytes(value), 0, array2, 0, 4);
        System.arraycopy(array, 0, array2, 4, array.length);
        return array2;
    }
    
    public byte[] getCentralDirectoryData() {
        return this.getLocalFileDataData();
    }
    
    public void setUserId(final int uid) {
        this.uid = uid;
    }
    
    public int getUserId() {
        return this.uid;
    }
    
    public void setGroupId(final int gid) {
        this.gid = gid;
    }
    
    public int getGroupId() {
        return this.gid;
    }
    
    public void setLinkedFile(final String link) {
        this.link = link;
        this.mode = this.getMode(this.mode);
    }
    
    public String getLinkedFile() {
        return this.link;
    }
    
    public boolean isLink() {
        return this.getLinkedFile().length() != 0;
    }
    
    public void setMode(final int n) {
        this.mode = this.getMode(n);
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public void setDirectory(final boolean dirFlag) {
        this.dirFlag = dirFlag;
        this.mode = this.getMode(this.mode);
    }
    
    public boolean isDirectory() {
        return this.dirFlag && !this.isLink();
    }
    
    public void parseFromLocalFileData(final byte[] array, final int n, final int n2) throws ZipException {
        final long value = ZipLong.getValue(array, n);
        final byte[] array2 = new byte[n2 - 4];
        System.arraycopy(array, n + 4, array2, 0, n2 - 4);
        this.crc.reset();
        this.crc.update(array2);
        final long value2 = this.crc.getValue();
        if (value != value2) {
            throw new ZipException("bad CRC checksum " + Long.toHexString(value) + " instead of " + Long.toHexString(value2));
        }
        final int value3 = ZipShort.getValue(array2, 0);
        final byte[] array3 = new byte[(int)ZipLong.getValue(array2, 2)];
        this.uid = ZipShort.getValue(array2, 6);
        this.gid = ZipShort.getValue(array2, 8);
        if (array3.length == 0) {
            this.link = "";
        }
        else {
            System.arraycopy(array2, 10, array3, 0, array3.length);
            this.link = new String(array3);
        }
        this.setDirectory((value3 & 0x4000) != 0x0);
        this.setMode(value3);
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) throws ZipException {
        this.parseFromLocalFileData(array, n, n2);
    }
    
    protected int getMode(final int n) {
        if (!this.isLink()) {
            if (this.isDirectory()) {}
        }
        return 0x4000 | (n & 0xFFF);
    }
    
    public Object clone() {
        final AsiExtraField asiExtraField = (AsiExtraField)super.clone();
        asiExtraField.crc = new CRC32();
        return asiExtraField;
    }
    
    static {
        HEADER_ID = new ZipShort(30062);
    }
}
