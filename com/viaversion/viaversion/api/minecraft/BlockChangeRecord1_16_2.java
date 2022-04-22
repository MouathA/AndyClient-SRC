package com.viaversion.viaversion.api.minecraft;

import com.google.common.base.*;

public class BlockChangeRecord1_16_2 implements BlockChangeRecord
{
    private final byte sectionX;
    private final byte sectionY;
    private final byte sectionZ;
    private int blockId;
    
    public BlockChangeRecord1_16_2(final byte sectionX, final byte sectionY, final byte sectionZ, final int blockId) {
        this.sectionX = sectionX;
        this.sectionY = sectionY;
        this.sectionZ = sectionZ;
        this.blockId = blockId;
    }
    
    public BlockChangeRecord1_16_2(final int n, final int n2, final int n3, final int n4) {
        this((byte)n, (byte)n2, (byte)n3, n4);
    }
    
    @Override
    public byte getSectionX() {
        return this.sectionX;
    }
    
    @Override
    public byte getSectionY() {
        return this.sectionY;
    }
    
    @Override
    public byte getSectionZ() {
        return this.sectionZ;
    }
    
    @Override
    public short getY(final int n) {
        Preconditions.checkArgument(n >= 0, (Object)("Invalid chunkSectionY: " + n));
        return (short)((n << 4) + this.sectionY);
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
