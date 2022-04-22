package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockReed extends Block
{
    public static final PropertyInteger field_176355_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000300";
        field_176355_a = PropertyInteger.create("age", 0, 15);
    }
    
    protected BlockReed() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockReed.field_176355_a, 0));
        final float n = 0.375f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if ((world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.reeds || this.func_176353_e(world, blockPos, blockState)) && world.isAirBlock(blockPos.offsetUp())) {
            while (world.getBlockState(blockPos.offsetDown(1)).getBlock() == this) {
                int n = 0;
                ++n;
            }
            if (1 < 3) {
                final int intValue = (int)blockState.getValue(BlockReed.field_176355_a);
                if (intValue == 15) {
                    world.setBlockState(blockPos.offsetUp(), this.getDefaultState());
                    world.setBlockState(blockPos, blockState.withProperty(BlockReed.field_176355_a, 0), 4);
                }
                else {
                    world.setBlockState(blockPos, blockState.withProperty(BlockReed.field_176355_a, intValue + 1), 4);
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
        if (block == this) {
            return true;
        }
        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
            return false;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next()).offsetDown()).getBlock().getMaterial() == Material.water) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.func_176353_e(world, blockPos, blockState);
    }
    
    protected final boolean func_176353_e(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (this.func_176354_d(world, blockToAir)) {
            return true;
        }
        this.dropBlockAsItem(world, blockToAir, blockState, 0);
        world.setBlockToAir(blockToAir);
        return false;
    }
    
    public boolean func_176354_d(final World world, final BlockPos blockPos) {
        return this.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.reeds;
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
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.reeds;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).func_180627_b(blockPos);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockReed.field_176355_a, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockReed.field_176355_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockReed.field_176355_a });
    }
}
