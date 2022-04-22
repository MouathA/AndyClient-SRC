package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockStandingSign extends BlockSign
{
    public static final PropertyInteger ROTATION_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002060";
        ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
    }
    
    public BlockStandingSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStandingSign.ROTATION_PROP, 0));
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.offsetDown()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockStandingSign.ROTATION_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStandingSign.ROTATION_PROP });
    }
}
