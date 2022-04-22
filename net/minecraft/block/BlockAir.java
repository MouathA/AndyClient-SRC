package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockAir extends Block
{
    private static final String __OBFID;
    
    protected BlockAir() {
        super(Material.air);
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return false;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    static {
        __OBFID = "CL_00000190";
    }
}
