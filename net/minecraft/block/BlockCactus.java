package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockCactus extends Block
{
    public static final PropertyInteger AGE_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000210";
        AGE_PROP = PropertyInteger.create("age", 0, 15);
    }
    
    protected BlockCactus() {
        super(Material.cactus);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCactus.AGE_PROP, 0));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final BlockPos offsetUp = blockPos.offsetUp();
        if (world.isAirBlock(offsetUp)) {
            while (world.getBlockState(blockPos.offsetDown(1)).getBlock() == this) {
                int n = 0;
                ++n;
            }
            if (1 < 3) {
                final int intValue = (int)blockState.getValue(BlockCactus.AGE_PROP);
                if (intValue == 15) {
                    world.setBlockState(offsetUp, this.getDefaultState());
                    final IBlockState withProperty = blockState.withProperty(BlockCactus.AGE_PROP, 0);
                    world.setBlockState(blockPos, withProperty, 4);
                    this.onNeighborBlockChange(world, offsetUp, withProperty, this);
                }
                else {
                    world.setBlockState(blockPos, blockState.withProperty(BlockCactus.AGE_PROP, intValue + 1), 4);
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + 1 - n, blockPos.getY() + 1 - n, blockPos.getZ() + 1 - n);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + 1 - n, blockPos.getY() + 1, blockPos.getZ() + 1 - n);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockPos)) {
            world.destroyBlock(blockPos, true);
        }
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next())).getBlock().getMaterial().isSolid()) {
                return false;
            }
        }
        final Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
        return block == Blocks.cactus || block == Blocks.sand;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        entity.attackEntityFrom(DamageSource.cactus, 1.0f);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCactus.AGE_PROP, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCactus.AGE_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCactus.AGE_PROP });
    }
}
