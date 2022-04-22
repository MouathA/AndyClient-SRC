package com.viaversion.viaversion.api.minecraft;

public class BlockChangeRecord1_8 implements BlockChangeRecord
{
    private final byte sectionX;
    private final short y;
    private final byte sectionZ;
    private int blockId;
    
    public BlockChangeRecord1_8(final byte sectionX, final short y, final byte sectionZ, final int blockId) {
        this.sectionX = sectionX;
        this.y = y;
        this.sectionZ = sectionZ;
        this.blockId = blockId;
    }
    
    public BlockChangeRecord1_8(final int n, final int n2, final int n3, final int n4) {
        this((byte)n, (short)n2, (byte)n3, n4);
    }
    
    @Override
    public byte getSectionX() {
        return this.sectionX;
    }
    
    @Override
    public byte getSectionY() {
        return (byte)(this.y & 0xF);
    }
    
    @Override
    public short getY(final int n) {
        return this.y;
    }
    
    @Override
    public byte getSectionZ() {
        return this.sectionZ;
    }
    
    @Override
    public int getBlockId() {
        return this.blockId;
    }
    
    @Override
    public void setBlockId(final int blockId) {
        this.blockId = blockId;
    }
}
