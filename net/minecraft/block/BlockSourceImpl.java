package net.minecraft.block;

import net.minecraft.dispenser.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public class BlockSourceImpl implements IBlockSource
{
    private final World worldObj;
    private final BlockPos pos;
    private static final String __OBFID;
    
    public BlockSourceImpl(final World worldObj, final BlockPos pos) {
        this.worldObj = worldObj;
        this.pos = pos;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public double getX() {
        return this.pos.getX() + 0.5;
    }
    
    @Override
    public double getY() {
        return this.pos.getY() + 0.5;
    }
    
    @Override
    public double getZ() {
        return this.pos.getZ() + 0.5;
    }
    
    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }
    
    @Override
    public Block getBlock() {
        return this.worldObj.getBlockState(this.pos).getBlock();
    }
    
    @Override
    public int getBlockMetadata() {
        final IBlockState blockState = this.worldObj.getBlockState(this.pos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public TileEntity getBlockTileEntity() {
        return this.worldObj.getTileEntity(this.pos);
    }
    
    static {
        __OBFID = "CL_00001194";
    }
}
