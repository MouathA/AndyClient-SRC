package org.apache.commons.compress.archivers.zip;

import java.io.*;
import java.util.zip.*;
import java.util.*;

public class X5455_ExtendedTimestamp implements ZipExtraField, Cloneable, Serializable
{
    private static final ZipShort HEADER_ID;
    private static final long serialVersionUID = 1L;
    public static final byte MODIFY_TIME_BIT = 1;
    public static final byte ACCESS_TIME_BIT = 2;
    public static final byte CREATE_TIME_BIT = 4;
    private byte flags;
    private boolean bit0_modifyTimePresent;
    private boolean bit1_accessTimePresent;
    private boolean bit2_createTimePresent;
    private ZipLong modifyTime;
    private ZipLong accessTime;
    private ZipLong createTime;
    
    public ZipShort getHeaderId() {
        return X5455_ExtendedTimestamp.HEADER_ID;
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(1 + (this.bit0_modifyTimePresent ? 4 : 0) + ((this.bit1_accessTimePresent && this.accessTime != null) ? 4 : 0) + ((this.bit2_createTimePresent && this.createTime != null) ? 4 : 0));
    }
    
    public ZipShort getCentralDirectoryLength() {
        return new ZipShort(1 + (this.bit0_modifyTimePresent ? 4 : 0));
    }
    
    public byte[] getLocalFileDataData() {
        final byte[] array2;
        final byte[] array = array2 = new byte[this.getLocalFileDataLength().getValue()];
        final int n = 0;
        int n2 = 0;
        ++n2;
        array2[n] = 0;
        if (this.bit0_modifyTimePresent) {
            final byte[] array3 = array;
            final int n3 = 0;
            array3[n3] |= 0x1;
            System.arraycopy(this.modifyTime.getBytes(), 0, array, 0, 4);
            n2 += 4;
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            final byte[] array4 = array;
            final int n4 = 0;
            array4[n4] |= 0x2;
            System.arraycopy(this.accessTime.getBytes(), 0, array, 0, 4);
            n2 += 4;
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            final byte[] array5 = array;
            final int n5 = 0;
            array5[n5] |= 0x4;
            System.arraycopy(this.createTime.getBytes(), 0, array, 0, 4);
            n2 += 4;
        }
        return array;
    }
    
    public byte[] getCentralDirectoryData() {
        final byte[] array = new byte[this.getCentralDirectoryLength().getValue()];
        System.arraycopy(this.getLocalFileDataData(), 0, array, 0, array.length);
        return array;
    }
    
    public void parseFromLocalFileData(final byte[] array, int n, final int n2) throws ZipException {
        this.reset();
        final int n3 = n + n2;
        this.setFlags(array[n++]);
        if (this.bit0_modifyTimePresent) {
            this.modifyTime = new ZipLong(array, n);
            n += 4;
        }
        if (this.bit1_accessTimePresent && n + 4 <= n3) {
            this.accessTime = new ZipLong(array, n);
            n += 4;
        }
        if (this.bit2_createTimePresent && n + 4 <= n3) {
            this.createTime = new ZipLong(array, n);
            n += 4;
        }
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) throws ZipException {
        this.reset();
        this.parseFromLocalFileData(array, n, n2);
    }
    
    private void reset() {
        this.setFlags((byte)0);
        this.modifyTime = null;
        this.accessTime = null;
        this.createTime = null;
    }
    
    public void setFlags(final byte flags) {
        this.flags = flags;
        this.bit0_modifyTimePresent = ((flags & 0x1) == 0x1);
        this.bit1_accessTimePresent = ((flags & 0x2) == 0x2);
        this.bit2_createTimePresent = ((flags & 0x4) == 0x4);
    }
    
    public byte getFlags() {
        return this.flags;
    }
    
    public boolean isBit0_modifyTimePresent() {
        return this.bit0_modifyTimePresent;
    }
    
    public boolean isBit1_accessTimePresent() {
        return this.bit1_accessTimePresent;
    }
    
    public boolean isBit2_createTimePresent() {
        return this.bit2_createTimePresent;
    }
    
    public ZipLong getModifyTime() {
        return this.modifyTime;
    }
    
    public ZipLong getAccessTime() {
        return this.accessTime;
    }
    
    public ZipLong getCreateTime() {
        return this.createTime;
    }
    
    public Date getModifyJavaTime() {
        return (this.modifyTime != null) ? new Date(this.modifyTime.getValue() * 1000L) : null;
    }
    
    public Date getAccessJavaTime() {
        return (this.accessTime != null) ? new Date(this.accessTime.getValue() * 1000L) : null;
    }
    
    public Date getCreateJavaTime() {
        return (this.createTime != null) ? new Date(this.createTime.getValue() * 1000L) : null;
    }
    
    public void setModifyTime(final ZipLong modifyTime) {
        this.bit0_modifyTimePresent = (modifyTime != null);
        this.flags = (byte)((modifyTime != null) ? (this.flags | 0x1) : (this.flags & 0xFFFFFFFE));
        this.modifyTime = modifyTime;
    }
    
    public void setAccessTime(final ZipLong accessTime) {
        this.bit1_accessTimePresent = (accessTime != null);
        this.flags = (byte)((accessTime != null) ? (this.flags | 0x2) : (this.flags & 0xFFFFFFFD));
        this.accessTime = accessTime;
    }
    
    public void setCreateTime(final ZipLong createTime) {
        this.bit2_createTimePresent = (createTime != null);
        this.flags = (byte)((createTime != null) ? (this.flags | 0x4) : (this.flags & 0xFFFFFFFB));
        this.createTime = createTime;
    }
    
    public void setModifyJavaTime(final Date date) {
        this.setModifyTime(dateToZipLong(date));
    }
    
    public void setAccessJavaTime(final Date date) {
        this.setAccessTime(dateToZipLong(date));
    }
    
    public void setCreateJavaTime(final Date date) {
        this.setCreateTime(dateToZipLong(date));
    }
    
    private static ZipLong dateToZipLong(final Date date) {
        if (date == null) {
            return null;
        }
        final long n = date.getTime() / 1000L;
        if (n >= 4294967296L) {
            throw new IllegalArgumentException("Cannot set an X5455 timestamp larger than 2^32: " + n);
        }
        return new ZipLong(n);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("0x5455 Zip Extra Field: Flags=");
        sb.append(Integer.toBinaryString(ZipUtil.unsignedIntToSignedByte(this.flags))).append(" ");
        if (this.bit0_modifyTimePresent && this.modifyTime != null) {
            sb.append(" Modify:[").append(this.getModifyJavaTime()).append("] ");
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            sb.append(" Access:[").append(this.getAccessJavaTime()).append("] ");
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            sb.append(" Create:[").append(this.getCreateJavaTime()).append("] ");
        }
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof X5455_ExtendedTimestamp) {
            final X5455_ExtendedTimestamp x5455_ExtendedTimestamp = (X5455_ExtendedTimestamp)o;
            return (this.flags & 0x7) == (x5455_ExtendedTimestamp.flags & 0x7) && (this.modifyTime == x5455_ExtendedTimestamp.modifyTime || (this.modifyTime != null && this.modifyTime.equals(x5455_ExtendedTimestamp.modifyTime))) && (this.accessTime == x5455_ExtendedTimestamp.accessTime || (this.accessTime != null && this.accessTime.equals(x5455_ExtendedTimestamp.accessTime))) && (this.createTime == x5455_ExtendedTimestamp.createTime || (this.createTime != null && this.createTime.equals(x5455_ExtendedTimestamp.createTime)));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int n = -123 * (this.flags & 0x7);
        if (this.modifyTime != null) {
            n ^= this.modifyTime.hashCode();
        }
        if (this.accessTime != null) {
            n ^= Integer.rotateLeft(this.accessTime.hashCode(), 11);
        }
        if (this.createTime != null) {
            n ^= Integer.rotateLeft(this.createTime.hashCode(), 22);
        }
        return n;
    }
    
    static {
        HEADER_ID = new ZipShort(21589);
    }
}
