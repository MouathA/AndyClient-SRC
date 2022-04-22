package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;

public class BlockFenceGate extends BlockDirectional
{
    public static final PropertyBool field_176466_a;
    public static final PropertyBool field_176465_b;
    public static final PropertyBool field_176467_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000243";
        field_176466_a = PropertyBool.create("open");
        field_176465_b = PropertyBool.create("powered");
        field_176467_M = PropertyBool.create("in_wall");
    }
    
    public BlockFenceGate() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFenceGate.field_176466_a, false).withProperty(BlockFenceGate.field_176465_b, false).withProperty(BlockFenceGate.field_176467_M, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = ((EnumFacing)withProperty.getValue(BlockFenceGate.AGE)).getAxis();
        if ((axis == EnumFacing.Axis.Z && (blockAccess.getBlockState(blockPos.offsetWest()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.offsetEast()).getBlock() == Blocks.cobblestone_wall)) || (axis == EnumFacing.Axis.X && (blockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == Blocks.cobblestone_wall))) {
            withProperty = withProperty.withProperty(BlockFenceGate.field_176467_M, true);
        }
        return withProperty;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid() && super.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockFenceGate.field_176466_a)) {
            return null;
        }
        return (((EnumFacing)blockState.getValue(BlockFenceGate.AGE)).getAxis() == EnumFacing.Axis.Z) ? new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 0.375f, blockPos.getX() + 1, blockPos.getY() + 1.5f, blockPos.getZ() + 0.625f) : new AxisAlignedBB(blockPos.getX() + 0.375f, blockPos.getY(), blockPos.getZ(), blockPos.getX() + 0.625f, blockPos.getY() + 1.5f, blockPos.getZ() + 1);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockFenceGate.AGE)).getAxis() == EnumFacing.Axis.Z) {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
        else {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
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
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (boolean)blockAccess.getBlockState(blockPos).getValue(BlockFenceGate.field_176466_a);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockFenceGate.AGE, entityLivingBase.func_174811_aO()).withProperty(BlockFenceGate.field_176466_a, false).withProperty(BlockFenceGate.field_176465_b, false).withProperty(BlockFenceGate.field_176467_M, false);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue(BlockFenceGate.field_176466_a)) {
            blockState = blockState.withProperty(BlockFenceGate.field_176466_a, false);
            world.setBlockState(blockPos, blockState, 2);
        }
        else {
            final EnumFacing fromAngle = EnumFacing.fromAngle(entityPlayer.rotationYaw);
            if (blockState.getValue(BlockFenceGate.AGE) == fromAngle.getOpposite()) {
                blockState = blockState.withProperty(BlockFenceGate.AGE, fromAngle);
            }
            blockState = blockState.withProperty(BlockFenceGate.field_176466_a, true);
            world.setBlockState(blockPos, blockState, 2);
        }
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)blockState.getValue(BlockFenceGate.field_176466_a)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            if (blockPowered || block.canProvidePower()) {
                if (blockPowered && !(boolean)blockState.getValue(BlockFenceGate.field_176466_a) && !(boolean)blockState.getValue(BlockFenceGate.field_176465_b)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.field_176466_a, true).withProperty(BlockFenceGate.field_176465_b, true), 2);
                    world.playAuxSFXAtEntity(null, 1003, blockPos, 0);
                }
                else if (!blockPowered && (boolean)blockState.getValue(BlockFenceGate.field_176466_a) && (boolean)blockState.getValue(BlockFenceGate.field_176465_b)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.field_176466_a, false).withProperty(BlockFenceGate.field_176465_b, false), 2);
                    world.playAuxSFXAtEntity(null, 1006, blockPos, 0);
                }
                else if (blockPowered != (boolean)blockState.getValue(BlockFenceGate.field_176465_b)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.field_176465_b, blockPowered), 2);
                }
            }
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFenceGate.AGE, EnumFacing.getHorizontal(n)).withProperty(BlockFenceGate.field_176466_a, (n & 0x4) != 0x0).withProperty(BlockFenceGate.field_176465_b, (n & 0x8) != 0x0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockFenceGate.AGE)).getHorizontalIndex();
        if (blockState.getValue(BlockFenceGate.field_176465_b)) {
            n |= 0x8;
        }
        if (blockState.getValue(BlockFenceGate.field_176466_a)) {
            n |= 0x4;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFenceGate.AGE, BlockFenceGate.field_176466_a, BlockFenceGate.field_176465_b, BlockFenceGate.field_176467_M });
    }
}
