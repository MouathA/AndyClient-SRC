package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.*;

public class ExtendedBlockStorage
{
    private int yBase;
    private byte[] blockLSBArray;
    private NibbleArray blockMSBArray;
    private NibbleArray blockMetadataArray;
    private NibbleArray blocklightArray;
    private NibbleArray skylightArray;
    
    public ExtendedBlockStorage(final int yBase, final boolean b) {
        this.yBase = yBase;
        this.blockLSBArray = new byte[4096];
        this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length);
        this.blocklightArray = new NibbleArray(this.blockLSBArray.length);
        if (b) {
            this.skylightArray = new NibbleArray(this.blockLSBArray.length);
        }
    }
    
    public int getExtBlockMetadata(final int n, final int n2, final int n3) {
        return this.blockMetadataArray.get(n, n2, n3);
    }
    
    public void setExtBlockMetadata(final int n, final int n2, final int n3, final int n4) {
        this.blockMetadataArray.set(n, n2, n3, n4);
    }
    
    public int getYLocation() {
        return this.yBase;
    }
    
    public void setExtSkylightValue(final int n, final int n2, final int n3, final int n4) {
        this.skylightArray.set(n, n2, n3, n4);
    }
    
    public int getExtSkylightValue(final int n, final int n2, final int n3) {
        return this.skylightArray.get(n, n2, n3);
    }
    
    public void setExtBlocklightValue(final int n, final int n2, final int n3, final int n4) {
        this.blocklightArray.set(n, n2, n3, n4);
    }
    
    public int getExtBlocklightValue(final int n, final int n2, final int n3) {
        return this.blocklightArray.get(n, n2, n3);
    }
    
    public byte[] getBlockLSBArray() {
        return this.blockLSBArray;
    }
    
    public boolean isEmpty() {
        return this.blockMSBArray == null;
    }
    
    public void clearMSBArray() {
        this.blockMSBArray = null;
    }
    
    public NibbleArray getBlockMSBArray() {
        return this.blockMSBArray;
    }
    
    public NibbleArray getMetadataArray() {
        return this.blockMetadataArray;
    }
    
    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }
    
    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }
    
    public void setBlockLSBArray(final byte[] blockLSBArray) {
        this.blockLSBArray = blockLSBArray;
    }
    
    public void setBlockMSBArray(final NibbleArray blockMSBArray) {
        this.blockMSBArray = blockMSBArray;
    }
    
    public void setBlockMetadataArray(final NibbleArray blockMetadataArray) {
        this.blockMetadataArray = blockMetadataArray;
    }
    
    public void setBlocklightArray(final NibbleArray blocklightArray) {
        this.blocklightArray = blocklightArray;
    }
    
    public void setSkylightArray(final NibbleArray skylightArray) {
        this.skylightArray = skylightArray;
    }
    
    public NibbleArray createBlockMSBArray() {
        return this.blockMSBArray = new NibbleArray(this.blockLSBArray.length);
    }
}
