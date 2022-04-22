package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;

public class BlockChest extends BlockContainer
{
    public static final PropertyDirection FACING_PROP;
    private final Random rand;
    public final int chestType;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000214";
        FACING_PROP = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockChest(final int chestType) {
        super(Material.wood);
        this.rand = new Random();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockChest.FACING_PROP, EnumFacing.NORTH));
        this.chestType = chestType;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
    public int getRenderType() {
        return 2;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (blockAccess.getBlockState(blockPos.offsetWest()).getBlock() == this) {
            this.setBlockBounds(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getBlockState(blockPos.offsetEast()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForSurroundingChests(world, blockPos, blockState);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this) {
                this.checkForSurroundingChests(world, offset, blockState2);
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockChest.FACING_PROP, entityLivingBase.func_174811_aO());
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final EnumFacing opposite = EnumFacing.getHorizontal(MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3).getOpposite();
        withProperty = withProperty.withProperty(BlockChest.FACING_PROP, opposite);
        final BlockPos offsetNorth = blockPos.offsetNorth();
        final BlockPos offsetSouth = blockPos.offsetSouth();
        final BlockPos offsetWest = blockPos.offsetWest();
        final BlockPos offsetEast = blockPos.offsetEast();
        final boolean b = this == world.getBlockState(offsetNorth).getBlock();
        final boolean b2 = this == world.getBlockState(offsetSouth).getBlock();
        final boolean b3 = this == world.getBlockState(offsetWest).getBlock();
        final boolean b4 = this == world.getBlockState(offsetEast).getBlock();
        if (!b && !b2 && !b3 && !b4) {
            world.setBlockState(blockPos, withProperty, 3);
        }
        else if (opposite.getAxis() == EnumFacing.Axis.X && (b || b2)) {
            if (b) {
                world.setBlockState(offsetNorth, withProperty, 3);
            }
            else {
                world.setBlockState(offsetSouth, withProperty, 3);
            }
            world.setBlockState(blockPos, withProperty, 3);
        }
        else if (opposite.getAxis() == EnumFacing.Axis.Z && (b3 || b4)) {
            if (b3) {
                world.setBlockState(offsetWest, withProperty, 3);
            }
            else {
                world.setBlockState(offsetEast, withProperty, 3);
            }
            world.setBlockState(blockPos, withProperty, 3);
        }
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                ((TileEntityChest)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    public IBlockState checkForSurroundingChests(final World world, final BlockPos blockPos, IBlockState withProperty) {
        if (world.isRemote) {
            return withProperty;
        }
        final IBlockState blockState = world.getBlockState(blockPos.offsetNorth());
        final IBlockState blockState2 = world.getBlockState(blockPos.offsetSouth());
        final IBlockState blockState3 = world.getBlockState(blockPos.offsetWest());
        final IBlockState blockState4 = world.getBlockState(blockPos.offsetEast());
        EnumFacing enumFacing = (EnumFacing)withProperty.getValue(BlockChest.FACING_PROP);
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        final Block block3 = blockState3.getBlock();
        final Block block4 = blockState4.getBlock();
        if (block != this && block2 != this) {
            final boolean fullBlock = block.isFullBlock();
            final boolean fullBlock2 = block2.isFullBlock();
            if (block3 == this || block4 == this) {
                final BlockPos blockPos2 = (block3 == this) ? blockPos.offsetWest() : blockPos.offsetEast();
                final IBlockState blockState5 = world.getBlockState(blockPos2.offsetNorth());
                final IBlockState blockState6 = world.getBlockState(blockPos2.offsetSouth());
                enumFacing = EnumFacing.SOUTH;
                EnumFacing enumFacing2;
                if (block3 == this) {
                    enumFacing2 = (EnumFacing)blockState3.getValue(BlockChest.FACING_PROP);
                }
                else {
                    enumFacing2 = (EnumFacing)blockState4.getValue(BlockChest.FACING_PROP);
                }
                if (enumFacing2 == EnumFacing.NORTH) {
                    enumFacing = EnumFacing.NORTH;
                }
                final Block block5 = blockState5.getBlock();
                final Block block6 = blockState6.getBlock();
                if ((fullBlock || block5.isFullBlock()) && !fullBlock2 && !block6.isFullBlock()) {
                    enumFacing = EnumFacing.SOUTH;
                }
                if ((fullBlock2 || block6.isFullBlock()) && !fullBlock && !block5.isFullBlock()) {
                    enumFacing = EnumFacing.NORTH;
                }
            }
        }
        else {
            final BlockPos blockPos3 = (block == this) ? blockPos.offsetNorth() : blockPos.offsetSouth();
            final IBlockState blockState7 = world.getBlockState(blockPos3.offsetWest());
            final IBlockState blockState8 = world.getBlockState(blockPos3.offsetEast());
            enumFacing = EnumFacing.EAST;
            EnumFacing enumFacing3;
            if (block == this) {
                enumFacing3 = (EnumFacing)blockState.getValue(BlockChest.FACING_PROP);
            }
            else {
                enumFacing3 = (EnumFacing)blockState2.getValue(BlockChest.FACING_PROP);
            }
            if (enumFacing3 == EnumFacing.WEST) {
                enumFacing = EnumFacing.WEST;
            }
            final Block block7 = blockState7.getBlock();
            final Block block8 = blockState8.getBlock();
            if ((block3.isFullBlock() || block7.isFullBlock()) && !block4.isFullBlock() && !block8.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
            }
            if ((block4.isFullBlock() || block8.isFullBlock()) && !block3.isFullBlock() && !block7.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
        }
        withProperty = withProperty.withProperty(BlockChest.FACING_PROP, enumFacing);
        world.setBlockState(blockPos, withProperty, 3);
        return withProperty;
    }
    
    public IBlockState func_176458_f(final World world, final BlockPos blockPos, final IBlockState blockState) {
        EnumFacing enumFacing = null;
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            final IBlockState blockState2 = world.getBlockState(blockPos.offset(enumFacing2));
            if (blockState2.getBlock() == this) {
                return blockState;
            }
            if (!blockState2.getBlock().isFullBlock()) {
                continue;
            }
            if (enumFacing != null) {
                enumFacing = null;
                break;
            }
            enumFacing = enumFacing2;
        }
        if (enumFacing != null) {
            return blockState.withProperty(BlockChest.FACING_PROP, enumFacing.getOpposite());
        }
        EnumFacing enumFacing3 = (EnumFacing)blockState.getValue(BlockChest.FACING_PROP);
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.rotateY();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        return blockState.withProperty(BlockChest.FACING_PROP, enumFacing3);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final BlockPos offsetWest = blockPos.offsetWest();
        final BlockPos offsetEast = blockPos.offsetEast();
        final BlockPos offsetNorth = blockPos.offsetNorth();
        final BlockPos offsetSouth = blockPos.offsetSouth();
        int n = 0;
        if (world.getBlockState(offsetWest).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, offsetWest)) {
                return false;
            }
            ++n;
        }
        if (world.getBlockState(offsetEast).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, offsetEast)) {
                return false;
            }
            ++n;
        }
        if (world.getBlockState(offsetNorth).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, offsetNorth)) {
                return false;
            }
            ++n;
        }
        if (world.getBlockState(offsetSouth).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, offsetSouth)) {
                return false;
            }
            ++n;
        }
        return 0 <= 1;
    }
    
    private boolean isSurroundingBlockChest(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return false;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next())).getBlock() == this) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            tileEntity.updateContainingBlockInfo();
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final ILockableContainer lockableContainer = this.getLockableContainer(world, blockPos);
        if (lockableContainer != null) {
            entityPlayer.displayGUIChest(lockableContainer);
        }
        return true;
    }
    
    public ILockableContainer getLockableContainer(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!(tileEntity instanceof TileEntityChest)) {
            return null;
        }
        IInventory inventory = (TileEntityChest)tileEntity;
        if (this.cannotOpenChest(world, blockPos)) {
            return null;
        }
        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            final BlockPos offset = blockPos.offset(enumFacing);
            if (world.getBlockState(offset).getBlock() == this) {
                if (this.cannotOpenChest(world, offset)) {
                    return null;
                }
                final TileEntity tileEntity2 = world.getTileEntity(offset);
                if (!(tileEntity2 instanceof TileEntityChest)) {
                    continue;
                }
                if (enumFacing != EnumFacing.WEST && enumFacing != EnumFacing.NORTH) {
                    inventory = new InventoryLargeChest("container.chestDouble", (ILockableContainer)inventory, (ILockableContainer)tileEntity2);
                }
                else {
                    inventory = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileEntity2, (ILockableContainer)inventory);
                }
            }
        }
        return (TileEntityChest)inventory;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityChest();
    }
    
    @Override
    public boolean canProvidePower() {
        return this.chestType == 1;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        if (!this.canProvidePower()) {
            return 0;
        }
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            final int numPlayersUsing = ((TileEntityChest)tileEntity).numPlayersUsing;
        }
        return MathHelper.clamp_int(0, 0, 15);
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP) ? this.isProvidingWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    private boolean cannotOpenChest(final World world, final BlockPos blockPos) {
        return this.isBelowSolidBlock(world, blockPos) || this.isOcelotSittingOnChest(world, blockPos);
    }
    
    private boolean isBelowSolidBlock(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }
    
    private boolean isOcelotSittingOnChest(final World world, final BlockPos blockPos) {
        final Iterator<Entity> iterator = world.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1)).iterator();
        while (iterator.hasNext()) {
            if (((EntityOcelot)iterator.next()).isSitting()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(this.getLockableContainer(world, blockPos));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockChest.FACING_PROP, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockChest.FACING_PROP)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockChest.FACING_PROP });
    }
}
