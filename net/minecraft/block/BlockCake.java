package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockCake extends Block
{
    public static final PropertyInteger BITES;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000211";
        BITES = PropertyInteger.create("bites", 0, 6);
    }
    
    protected BlockCake() {
        super(Material.cake);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCake.BITES, 0));
        this.setTickRandomly(true);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.0625f;
        this.setBlockBounds((1 + (int)blockAccess.getBlockState(blockPos).getValue(BlockCake.BITES) * 2) / 16.0f, 0.0f, n, 1.0f - n, 0.5f, 1.0f - n);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.0625f;
        this.setBlockBounds(n, 0.0f, n, 1.0f - n, 0.5f, 1.0f - n);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + (1 + (int)blockState.getValue(BlockCake.BITES) * 2) / 16.0f, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + 1 - n, blockPos.getY() + 0.5f, blockPos.getZ() + 1 - n);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return this.getCollisionBoundingBox(world, blockPos, world.getBlockState(blockPos));
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
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.eatCake(world, blockPos, blockState, entityPlayer);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.eatCake(world, blockPos, world.getBlockState(blockPos), entityPlayer);
    }
    
    private void eatCake(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.canEat(false)) {
            entityPlayer.getFoodStats().addStats(2, 0.1f);
            final int intValue = (int)blockState.getValue(BlockCake.BITES);
            if (intValue < 6) {
                world.setBlockState(blockToAir, blockState.withProperty(BlockCake.BITES, intValue + 1), 3);
            }
            else {
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockToAir)) {
            world.setBlockToAir(blockToAir);
        }
    }
    
    private boolean canBlockStay(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.cake;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCake.BITES, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCake.BITES);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCake.BITES });
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return (7 - (int)world.getBlockState(blockPos).getValue(BlockCake.BITES)) * 2;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
}
