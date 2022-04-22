package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockSnow extends Block
{
    public static final PropertyInteger LAYERS_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000309";
        LAYERS_PROP = PropertyInteger.create("layers", 1, 8);
    }
    
    protected BlockSnow() {
        super(Material.snow);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSnow.LAYERS_PROP, 1));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (int)blockAccess.getBlockState(blockPos).getValue(BlockSnow.LAYERS_PROP) < 5;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + ((int)blockState.getValue(BlockSnow.LAYERS_PROP) - 1) * 0.125f, blockPos.getZ() + this.maxZ);
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
    public void setBlockBoundsForItemRender() {
        this.getBoundsForLayers(0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.getBoundsForLayers((int)blockAccess.getBlockState(blockPos).getValue(BlockSnow.LAYERS_PROP));
    }
    
    protected void getBoundsForLayers(final int n) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, n / 8.0f, 1.0f);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos.offsetDown());
        final Block block = blockState.getBlock();
        return block != Blocks.ice && block != Blocks.packed_ice && (block.getMaterial() == Material.leaves || (block == this && (int)blockState.getValue(BlockSnow.LAYERS_PROP) == 7) || (block.isOpaqueCube() && block.blockMaterial.blocksMovement()));
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    private boolean checkAndDropBlock(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockToAir)) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            return false;
        }
        return true;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockToAir, final IBlockState blockState, final TileEntity tileEntity) {
        Block.spawnAsEntity(world, blockToAir, new ItemStack(Items.snowball, (int)blockState.getValue(BlockSnow.LAYERS_PROP) + 1, 0));
        world.setBlockToAir(blockToAir);
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.snowball;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > 11) {
            this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing == EnumFacing.UP || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSnow.LAYERS_PROP, (n & 0x7) + 1);
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return (int)world.getBlockState(blockPos).getValue(BlockSnow.LAYERS_PROP) == 1;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockSnow.LAYERS_PROP) - 1;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSnow.LAYERS_PROP });
    }
}
