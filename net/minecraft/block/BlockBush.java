package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockBush extends Block
{
    private static final String __OBFID;
    
    protected BlockBush(final Material material) {
        super(material);
        this.setTickRandomly(true);
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 3.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    protected BlockBush() {
        this(Material.plants);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }
    
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        this.func_176475_e(world, blockPos, blockState);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.func_176475_e(world, blockPos, blockState);
    }
    
    protected void func_176475_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        }
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
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
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    static {
        __OBFID = "CL_00000208";
    }
}
