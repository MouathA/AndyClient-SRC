package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockBarrier extends Block
{
    private static final String __OBFID;
    
    protected BlockBarrier() {
        super(Material.barrier);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0f);
        this.disableStats();
        this.translucent = true;
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public float getAmbientOcclusionLightValue() {
        return 1.0f;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    static {
        __OBFID = "CL_00002139";
    }
}
