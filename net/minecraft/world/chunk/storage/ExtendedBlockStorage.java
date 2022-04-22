package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import optifine.*;

public class ExtendedBlockStorage
{
    private int yBase;
    private int blockRefCount;
    private int tickRefCount;
    private char[] data;
    private NibbleArray blocklightArray;
    private NibbleArray skylightArray;
    private static final String __OBFID;
    
    public ExtendedBlockStorage(final int yBase, final boolean b) {
        this.yBase = yBase;
        this.data = new char[4096];
        this.blocklightArray = new NibbleArray();
        if (b) {
            this.skylightArray = new NibbleArray();
        }
    }
    
    public IBlockState get(final int n, final int n2, final int n3) {
        final IBlockState blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[n2 << 8 | n3 << 4 | n]);
        return (blockState != null) ? blockState : Blocks.air.getDefaultState();
    }
    
    public void set(final int n, final int n2, final int n3, IBlockState blockState) {
        if (Reflector.IExtendedBlockState.isInstance(blockState)) {
            blockState = (IBlockState)Reflector.call(blockState, Reflector.IExtendedBlockState_getClean, new Object[0]);
        }
        final Block block = this.get(n, n2, n3).getBlock();
        final Block block2 = blockState.getBlock();
        if (block != Blocks.air) {
            --this.blockRefCount;
            if (block.getTickRandomly()) {
                --this.tickRefCount;
            }
        }
        if (block2 != Blocks.air) {
            ++this.blockRefCount;
            if (block2.getTickRandomly()) {
                ++this.tickRefCount;
            }
        }
        this.data[n2 << 8 | n3 << 4 | n] = (char)Block.BLOCK_STATE_IDS.get(blockState);
    }
    
    public Block getBlockByExtId(final int n, final int n2, final int n3) {
        return this.get(n, n2, n3).getBlock();
    }
    
    public int getExtBlockMetadata(final int n, final int n2, final int n3) {
        final IBlockState value = this.get(n, n2, n3);
        return value.getBlock().getMetaFromState(value);
    }
    
    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }
    
    public boolean getNeedsRandomTick() {
        return this.tickRefCount > 0;
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
    
    public void removeInvalidBlocks() {
        Block.BLOCK_STATE_IDS.getObjectList().size();
        this.blockRefCount = 0;
        this.tickRefCount = 0;
    }
    
    public char[] getData() {
        return this.data;
    }
    
    public void setData(final char[] data) {
        this.data = data;
    }
    
    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }
    
    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }
    
    public void setBlocklightArray(final NibbleArray blocklightArray) {
        this.blocklightArray = blocklightArray;
    }
    
    public void setSkylightArray(final NibbleArray skylightArray) {
        this.skylightArray = skylightArray;
    }
    
    static {
        __OBFID = "CL_00000375";
    }
}
