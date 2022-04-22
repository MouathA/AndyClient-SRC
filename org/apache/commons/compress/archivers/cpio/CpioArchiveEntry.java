package org.apache.commons.compress.archivers.cpio;

import org.apache.commons.compress.archivers.*;
import java.io.*;
import java.util.*;

public class CpioArchiveEntry implements CpioConstants, ArchiveEntry
{
    private final short fileFormat;
    private final int headerSize;
    private final int alignmentBoundary;
    private long chksum;
    private long filesize;
    private long gid;
    private long inode;
    private long maj;
    private long min;
    private long mode;
    private long mtime;
    private String name;
    private long nlink;
    private long rmaj;
    private long rmin;
    private long uid;
    
    public CpioArchiveEntry(final short fileFormat) {
        this.chksum = 0L;
        this.filesize = 0L;
        this.gid = 0L;
        this.inode = 0L;
        this.maj = 0L;
        this.min = 0L;
        this.mode = 0L;
        this.mtime = 0L;
        this.nlink = 0L;
        this.rmaj = 0L;
        this.rmin = 0L;
        this.uid = 0L;
        switch (fileFormat) {
            case 1: {
                this.headerSize = 110;
                this.alignmentBoundary = 4;
                break;
            }
            case 2: {
                this.headerSize = 110;
                this.alignmentBoundary = 4;
                break;
            }
            case 4: {
                this.headerSize = 76;
                this.alignmentBoundary = 0;
                break;
            }
            case 8: {
                this.headerSize = 26;
                this.alignmentBoundary = 2;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown header type");
            }
        }
        this.fileFormat = fileFormat;
    }
    
    public CpioArchiveEntry(final String s) {
        this((short)1, s);
    }
    
    public CpioArchiveEntry(final short n, final String name) {
        this(n);
        this.name = name;
    }
    
    public CpioArchiveEntry(final String s, final long size) {
        this(s);
        this.setSize(size);
    }
    
    public CpioArchiveEntry(final short n, final String s, final long size) {
        this(n, s);
        this.setSize(size);
    }
    
    public CpioArchiveEntry(final File file, final String s) {
        this((short)1, file, s);
    }
    
    public CpioArchiveEntry(final short n, final File file, final String s) {
        this(n, s, file.isFile() ? file.length() : 0L);
        if (file.isDirectory()) {
            this.setMode(16384L);
        }
        else {
            if (!file.isFile()) {
                throw new IllegalArgumentException("Cannot determine type of file " + file.getName());
            }
            this.setMode(32768L);
        }
        this.setTime(file.lastModified() / 1000L);
    }
    
    private void checkNewFormat() {
        if ((this.fileFormat & 0x3) == 0x0) {
            throw new UnsupportedOperationException();
        }
    }
    
    private void checkOldFormat() {
        if ((this.fileFormat & 0xC) == 0x0) {
            throw new UnsupportedOperationException();
        }
    }
    
    public long getChksum() {
        this.checkNewFormat();
        return this.chksum;
    }
    
    public long getDevice() {
        this.checkOldFormat();
        return this.min;
    }
    
    public long getDeviceMaj() {
        this.checkNewFormat();
        return this.maj;
    }
    
    public long getDeviceMin() {
        this.checkNewFormat();
        return this.min;
    }
    
    public long getSize() {
        return this.filesize;
    }
    
    public short getFormat() {
        return this.fileFormat;
    }
    
    public long getGID() {
        return this.gid;
    }
    
    public int getHeaderSize() {
        return this.headerSize;
    }
    
    public int getAlignmentBoundary() {
        return this.alignmentBoundary;
    }
    
    public int getHeaderPadCount() {
        if (this.alignmentBoundary == 0) {
            return 0;
        }
        int n = this.headerSize + 1;
        if (this.name != null) {
            n += this.name.length();
        }
        final int n2 = n % this.alignmentBoundary;
        if (n2 > 0) {
            return this.alignmentBoundary - n2;
        }
        return 0;
    }
    
    public int getDataPadCount() {
        if (this.alignmentBoundary == 0) {
            return 0;
        }
        final int n = (int)(this.filesize % this.alignmentBoundary);
        if (n > 0) {
            return this.alignmentBoundary - n;
        }
        return 0;
    }
    
    public long getInode() {
        return this.inode;
    }
    
    public long getMode() {
        return (this.mode == 0L && !"TRAILER!!!".equals(this.name)) ? 32768L : this.mode;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getNumberOfLinks() {
        return (this.nlink == 0L) ? ((this == 0) ? 2L : 1L) : this.nlink;
    }
    
    public long getRemoteDevice() {
        this.checkOldFormat();
        return this.rmin;
    }
    
    public long getRemoteDeviceMaj() {
        this.checkNewFormat();
        return this.rmaj;
    }
    
    public long getRemoteDeviceMin() {
        this.checkNewFormat();
        return this.rmin;
    }
    
    public long getTime() {
        return this.mtime;
    }
    
    public Date getLastModifiedDate() {
        return new Date(1000L * this.getTime());
    }
    
    public long getUID() {
        return this.uid;
    }
    
    public boolean isBlockDevice() {
        return CpioUtil.fileType(this.mode) == 24576L;
    }
    
    public boolean isCharacterDevice() {
        return CpioUtil.fileType(this.mode) == 8192L;
    }
    
    public boolean isNetwork() {
        return CpioUtil.fileType(this.mode) == 36864L;
    }
    
    public boolean isPipe() {
        return CpioUtil.fileType(this.mode) == 4096L;
    }
    
    public boolean isRegularFile() {
        return CpioUtil.fileType(this.mode) == 32768L;
    }
    
    public boolean isSocket() {
        return CpioUtil.fileType(this.mode) == 49152L;
    }
    
    public boolean isSymbolicLink() {
        return CpioUtil.fileType(this.mode) == 40960L;
    }
    
    public void setChksum(final long chksum) {
        this.checkNewFormat();
        this.chksum = chksum;
    }
    
    public void setDevice(final long min) {
        this.checkOldFormat();
        this.min = min;
    }
    
    public void setDeviceMaj(final long maj) {
        this.checkNewFormat();
        this.maj = maj;
    }
    
    public void setDeviceMin(final long min) {
        this.checkNewFormat();
        this.min = min;
    }
    
    public void setSize(final long filesize) {
        if (filesize < 0L || filesize > 4294967295L) {
            throw new IllegalArgumentException("invalid entry size <" + filesize + ">");
        }
        this.filesize = filesize;
    }
    
    public void setGID(final long gid) {
        this.gid = gid;
    }
    
    public void setInode(final long inode) {
        this.inode = inode;
    }
    
    public void setMode(final long mode) {
        final long n = mode & 0xF000L;
        switch ((int)n) {
            case 4096:
            case 8192:
            case 16384:
            case 24576:
            case 32768:
            case 36864:
            case 40960:
            case 49152: {
                this.mode = mode;
            }
            default: {
                throw new IllegalArgumentException("Unknown mode. Full: " + Long.toHexString(mode) + " Masked: " + Long.toHexString(n));
            }
        }
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setNumberOfLinks(final long nlink) {
        this.nlink = nlink;
    }
    
    public void setRemoteDevice(final long rmin) {
        this.checkOldFormat();
        this.rmin = rmin;
    }
    
    public void setRemoteDeviceMaj(final long rmaj) {
        this.checkNewFormat();
        this.rmaj = rmaj;
    }
    
    public void setRemoteDeviceMin(final long rmin) {
        this.checkNewFormat();
        this.rmin = rmin;
    }
    
    public void setTime(final long mtime) {
        this.mtime = mtime;
    }
    
    public void setUID(final long uid) {
        this.uid = uid;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + ((this.name == null) ? 0 : this.name.hashCode());
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry)o;
        if (this.name == null) {
            if (cpioArchiveEntry.name != null) {
                return false;
            }
        }
        else if (!this.name.equals(cpioArchiveEntry.name)) {
            return false;
        }
        return true;
    }
}
