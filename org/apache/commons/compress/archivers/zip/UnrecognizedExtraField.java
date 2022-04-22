package org.apache.commons.compress.archivers.zip;

public class UnrecognizedExtraField implements ZipExtraField
{
    private ZipShort headerId;
    private byte[] localData;
    private byte[] centralData;
    
    public void setHeaderId(final ZipShort headerId) {
        this.headerId = headerId;
    }
    
    public ZipShort getHeaderId() {
        return this.headerId;
    }
    
    public void setLocalFileDataData(final byte[] array) {
        this.localData = ZipUtil.copy(array);
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort((this.localData != null) ? this.localData.length : 0);
    }
    
    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localData);
    }
    
    public void setCentralDirectoryData(final byte[] array) {
        this.centralData = ZipUtil.copy(array);
    }
    
    public ZipShort getCentralDirectoryLength() {
        if (this.centralData != null) {
            return new ZipShort(this.centralData.length);
        }
        return this.getLocalFileDataLength();
    }
    
    public byte[] getCentralDirectoryData() {
        if (this.centralData != null) {
            return ZipUtil.copy(this.centralData);
        }
        return this.getLocalFileDataData();
    }
    
    public void parseFromLocalFileData(final byte[] array, final int n, final int n2) {
        final byte[] localFileDataData = new byte[n2];
        System.arraycopy(array, n, localFileDataData, 0, n2);
        this.setLocalFileDataData(localFileDataData);
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) {
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        this.setCentralDirectoryData(array2);
        if (this.localData == null) {
            this.setLocalFileDataData(array2);
        }
    }
}
