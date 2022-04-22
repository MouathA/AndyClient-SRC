package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;

public class ZipArchiveEntry extends ZipEntry implements ArchiveEntry
{
    public static final int PLATFORM_UNIX = 3;
    public static final int PLATFORM_FAT = 0;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_SHIFT = 16;
    private static final byte[] EMPTY;
    private int method;
    private long size;
    private int internalAttributes;
    private int platform;
    private long externalAttributes;
    private LinkedHashMap extraFields;
    private UnparseableExtraFieldData unparseableExtra;
    private String name;
    private byte[] rawName;
    private GeneralPurposeBit gpb;
    
    public ZipArchiveEntry(final String name) {
        super(name);
        this.method = -1;
        this.size = -1L;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0L;
        this.extraFields = null;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        this.setName(name);
    }
    
    public ZipArchiveEntry(final ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
        this.method = -1;
        this.size = -1L;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0L;
        this.extraFields = null;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        this.setName(zipEntry.getName());
        final byte[] extra = zipEntry.getExtra();
        if (extra != null) {
            this.setExtraFields(ExtraFieldUtils.parse(extra, true, ExtraFieldUtils.UnparseableExtraField.READ));
        }
        else {
            this.setExtra();
        }
        this.setMethod(zipEntry.getMethod());
        this.size = zipEntry.getSize();
    }
    
    public ZipArchiveEntry(final ZipArchiveEntry zipArchiveEntry) throws ZipException {
        this((ZipEntry)zipArchiveEntry);
        this.setInternalAttributes(zipArchiveEntry.getInternalAttributes());
        this.setExternalAttributes(zipArchiveEntry.getExternalAttributes());
        this.setExtraFields(zipArchiveEntry.getExtraFields(true));
    }
    
    protected ZipArchiveEntry() {
        this("");
    }
    
    public ZipArchiveEntry(final File file, final String s) {
        this((file.isDirectory() && !s.endsWith("/")) ? (s + "/") : s);
        if (file.isFile()) {
            this.setSize(file.length());
        }
        this.setTime(file.lastModified());
    }
    
    @Override
    public Object clone() {
        final ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)super.clone();
        zipArchiveEntry.setInternalAttributes(this.getInternalAttributes());
        zipArchiveEntry.setExternalAttributes(this.getExternalAttributes());
        zipArchiveEntry.setExtraFields(this.getExtraFields(true));
        return zipArchiveEntry;
    }
    
    @Override
    public int getMethod() {
        return this.method;
    }
    
    @Override
    public void setMethod(final int method) {
        if (method < 0) {
            throw new IllegalArgumentException("ZIP compression method can not be negative: " + method);
        }
        this.method = method;
    }
    
    public int getInternalAttributes() {
        return this.internalAttributes;
    }
    
    public void setInternalAttributes(final int internalAttributes) {
        this.internalAttributes = internalAttributes;
    }
    
    public long getExternalAttributes() {
        return this.externalAttributes;
    }
    
    public void setExternalAttributes(final long externalAttributes) {
        this.externalAttributes = externalAttributes;
    }
    
    public void setUnixMode(final int n) {
        this.setExternalAttributes(n << 16 | (((n & 0x80) == 0x0) ? 1 : 0) | (this.isDirectory() ? 16 : 0));
        this.platform = 3;
    }
    
    public int getUnixMode() {
        return (this.platform != 3) ? 0 : ((int)(this.getExternalAttributes() >> 16 & 0xFFFFL));
    }
    
    public boolean isUnixSymlink() {
        return (this.getUnixMode() & 0xA000) == 0xA000;
    }
    
    public int getPlatform() {
        return this.platform;
    }
    
    protected void setPlatform(final int platform) {
        this.platform = platform;
    }
    
    public void setExtraFields(final ZipExtraField[] array) {
        this.extraFields = new LinkedHashMap();
        while (0 < array.length) {
            final ZipExtraField zipExtraField = array[0];
            if (zipExtraField instanceof UnparseableExtraFieldData) {
                this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
            }
            else {
                this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
            }
            int n = 0;
            ++n;
        }
        this.setExtra();
    }
    
    public ZipExtraField[] getExtraFields() {
        return this.getExtraFields(false);
    }
    
    public ZipExtraField[] getExtraFields(final boolean b) {
        if (this.extraFields == null) {
            return (!b || this.unparseableExtra == null) ? new ZipExtraField[0] : new ZipExtraField[] { this.unparseableExtra };
        }
        final ArrayList<UnparseableExtraFieldData> list = new ArrayList<UnparseableExtraFieldData>(this.extraFields.values());
        if (b && this.unparseableExtra != null) {
            list.add(this.unparseableExtra);
        }
        return list.toArray(new ZipExtraField[0]);
    }
    
    public void addExtraField(final ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
        }
        else {
            if (this.extraFields == null) {
                this.extraFields = new LinkedHashMap();
            }
            this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
        }
        this.setExtra();
    }
    
    public void addAsFirstExtraField(final ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
        }
        else {
            final LinkedHashMap extraFields = this.extraFields;
            (this.extraFields = new LinkedHashMap()).put(zipExtraField.getHeaderId(), zipExtraField);
            if (extraFields != null) {
                extraFields.remove(zipExtraField.getHeaderId());
                this.extraFields.putAll(extraFields);
            }
        }
        this.setExtra();
    }
    
    public void removeExtraField(final ZipShort zipShort) {
        if (this.extraFields == null) {
            throw new NoSuchElementException();
        }
        if (this.extraFields.remove(zipShort) == null) {
            throw new NoSuchElementException();
        }
        this.setExtra();
    }
    
    public void removeUnparseableExtraFieldData() {
        if (this.unparseableExtra == null) {
            throw new NoSuchElementException();
        }
        this.unparseableExtra = null;
        this.setExtra();
    }
    
    public ZipExtraField getExtraField(final ZipShort zipShort) {
        if (this.extraFields != null) {
            return this.extraFields.get(zipShort);
        }
        return null;
    }
    
    public UnparseableExtraFieldData getUnparseableExtraFieldData() {
        return this.unparseableExtra;
    }
    
    @Override
    public void setExtra(final byte[] array) throws RuntimeException {
        this.mergeExtraFields(ExtraFieldUtils.parse(array, true, ExtraFieldUtils.UnparseableExtraField.READ), true);
    }
    
    protected void setExtra() {
        super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(this.getExtraFields(true)));
    }
    
    public void setCentralDirectoryExtra(final byte[] array) {
        this.mergeExtraFields(ExtraFieldUtils.parse(array, false, ExtraFieldUtils.UnparseableExtraField.READ), false);
    }
    
    public byte[] getLocalFileDataExtra() {
        final byte[] extra = this.getExtra();
        return (extra != null) ? extra : ZipArchiveEntry.EMPTY;
    }
    
    public byte[] getCentralDirectoryExtra() {
        return ExtraFieldUtils.mergeCentralDirectoryData(this.getExtraFields(true));
    }
    
    @Override
    public String getName() {
        return (this.name == null) ? super.getName() : this.name;
    }
    
    @Override
    public boolean isDirectory() {
        return this.getName().endsWith("/");
    }
    
    protected void setName(String replace) {
        if (replace != null && this.getPlatform() == 0 && replace.indexOf("/") == -1) {
            replace = replace.replace('\\', '/');
        }
        this.name = replace;
    }
    
    @Override
    public long getSize() {
        return this.size;
    }
    
    @Override
    public void setSize(final long size) {
        if (size < 0L) {
            throw new IllegalArgumentException("invalid entry size");
        }
        this.size = size;
    }
    
    protected void setName(final String name, final byte[] rawName) {
        this.setName(name);
        this.rawName = rawName;
    }
    
    public byte[] getRawName() {
        if (this.rawName != null) {
            final byte[] array = new byte[this.rawName.length];
            System.arraycopy(this.rawName, 0, array, 0, this.rawName.length);
            return array;
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    public GeneralPurposeBit getGeneralPurposeBit() {
        return this.gpb;
    }
    
    public void setGeneralPurposeBit(final GeneralPurposeBit gpb) {
        this.gpb = gpb;
    }
    
    private void mergeExtraFields(final ZipExtraField[] extraFields, final boolean b) throws ZipException {
        if (this.extraFields == null) {
            this.setExtraFields(extraFields);
        }
        else {
            while (0 < extraFields.length) {
                final ZipExtraField zipExtraField = extraFields[0];
                ZipExtraField zipExtraField2;
                if (zipExtraField instanceof UnparseableExtraFieldData) {
                    zipExtraField2 = this.unparseableExtra;
                }
                else {
                    zipExtraField2 = this.getExtraField(zipExtraField.getHeaderId());
                }
                if (zipExtraField2 == null) {
                    this.addExtraField(zipExtraField);
                }
                else if (b) {
                    final byte[] localFileDataData = zipExtraField.getLocalFileDataData();
                    zipExtraField2.parseFromLocalFileData(localFileDataData, 0, localFileDataData.length);
                }
                else {
                    final byte[] centralDirectoryData = zipExtraField.getCentralDirectoryData();
                    zipExtraField2.parseFromCentralDirectoryData(centralDirectoryData, 0, centralDirectoryData.length);
                }
                int n = 0;
                ++n;
            }
            this.setExtra();
        }
    }
    
    public Date getLastModifiedDate() {
        return new Date(this.getTime());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)o;
        final String name = this.getName();
        final String name2 = zipArchiveEntry.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        }
        else if (!name.equals(name2)) {
            return false;
        }
        String comment = this.getComment();
        String comment2 = zipArchiveEntry.getComment();
        if (comment == null) {
            comment = "";
        }
        if (comment2 == null) {
            comment2 = "";
        }
        return this.getTime() == zipArchiveEntry.getTime() && comment.equals(comment2) && this.getInternalAttributes() == zipArchiveEntry.getInternalAttributes() && this.getPlatform() == zipArchiveEntry.getPlatform() && this.getExternalAttributes() == zipArchiveEntry.getExternalAttributes() && this.getMethod() == zipArchiveEntry.getMethod() && this.getSize() == zipArchiveEntry.getSize() && this.getCrc() == zipArchiveEntry.getCrc() && this.getCompressedSize() == zipArchiveEntry.getCompressedSize() && Arrays.equals(this.getCentralDirectoryExtra(), zipArchiveEntry.getCentralDirectoryExtra()) && Arrays.equals(this.getLocalFileDataExtra(), zipArchiveEntry.getLocalFileDataExtra()) && this.gpb.equals(zipArchiveEntry.gpb);
    }
    
    static {
        EMPTY = new byte[0];
    }
}
