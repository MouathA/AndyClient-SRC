package org.apache.commons.compress.archivers.zip;

public final class UnparseableExtraFieldData implements ZipExtraField
{
    private static final ZipShort HEADER_ID;
    private byte[] localFileData;
    private byte[] centralDirectoryData;
    
    public ZipShort getHeaderId() {
        return UnparseableExtraFieldData.HEADER_ID;
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort((this.localFileData == null) ? 0 : this.localFileData.length);
    }
    
    public ZipShort getCentralDirectoryLength() {
        return (this.centralDirectoryData == null) ? this.getLocalFileDataLength() : new ZipShort(this.centralDirectoryData.length);
    }
    
    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localFileData);
    }
    
    public byte[] getCentralDirectoryData() {
        return (this.centralDirectoryData == null) ? this.getLocalFileDataData() : ZipUtil.copy(this.centralDirectoryData);
    }
    
    public void parseFromLocalFileData(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.localFileData = new byte[n2], 0, n2);
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.centralDirectoryData = new byte[n2], 0, n2);
        if (this.localFileData == null) {
            this.parseFromLocalFileData(array, n, n2);
        }
    }
    
    static {
        HEADER_ID = new ZipShort(44225);
    }
}
