package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockGrass extends Block implements IGrowable
{
    public static final PropertyBool SNOWY;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000251";
        SNOWY = PropertyBool.create("snowy");
    }
    
    protected BlockGrass() {
        super(Material.grass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockGrass.SNOWY, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos.offsetUp()).getBlock();
        return blockState.withProperty(BlockGrass.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return this.getBlockColor();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.func_180286_a(blockAccess, blockPos);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.offsetUp()) < 4 && world.getBlockState(blockPos.offsetUp()).getBlock().getLightOpacity() > 2) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
            else if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9) {}
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        blockPos.offsetUp();
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockGrass.SNOWY });
    }
}
