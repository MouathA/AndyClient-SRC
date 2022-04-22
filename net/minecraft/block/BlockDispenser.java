package net.minecraft.block;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.dispenser.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;

public class BlockDispenser extends BlockContainer
{
    public static final PropertyDirection FACING;
    public static final PropertyBool TRIGGERED;
    public static final RegistryDefaulted dispenseBehaviorRegistry;
    protected Random rand;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000229";
        FACING = PropertyDirection.create("facing");
        TRIGGERED = PropertyBool.create("triggered");
        dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }
    
    protected BlockDispenser() {
        super(Material.rock);
        this.rand = new Random();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDispenser.FACING, EnumFacing.NORTH).withProperty(BlockDispenser.TRIGGERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public int tickRate(final World world) {
        return 4;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.setDefaultDirection(world, blockPos, blockState);
    }
    
    private void setDefaultDirection(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockDispenser.FACING);
            final boolean fullBlock = world.getBlockState(blockPos.offsetNorth()).getBlock().isFullBlock();
            final boolean fullBlock2 = world.getBlockState(blockPos.offsetSouth()).getBlock().isFullBlock();
            if (enumFacing == EnumFacing.NORTH && fullBlock && !fullBlock2) {
                enumFacing = EnumFacing.SOUTH;
            }
            else if (enumFacing == EnumFacing.SOUTH && fullBlock2 && !fullBlock) {
                enumFacing = EnumFacing.NORTH;
            }
            else {
                final boolean fullBlock3 = world.getBlockState(blockPos.offsetWest()).getBlock().isFullBlock();
                final boolean fullBlock4 = world.getBlockState(blockPos.offsetEast()).getBlock().isFullBlock();
                if (enumFacing == EnumFacing.WEST && fullBlock3 && !fullBlock4) {
                    enumFacing = EnumFacing.EAST;
                }
                else if (enumFacing == EnumFacing.EAST && fullBlock4 && !fullBlock3) {
                    enumFacing = EnumFacing.WEST;
                }
            }
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.FACING, enumFacing).withProperty(BlockDispenser.TRIGGERED, false), 2);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
        }
        return true;
    }
    
    protected void func_176439_d(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int func_146017_i = tileEntityDispenser.func_146017_i();
            if (func_146017_i < 0) {
                world.playAuxSFX(1001, blockPos, 0);
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(func_146017_i);
                final IBehaviorDispenseItem func_149940_a = this.func_149940_a(stackInSlot);
                if (func_149940_a != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                    final ItemStack dispense = func_149940_a.dispense(blockSourceImpl, stackInSlot);
                    tileEntityDispenser.setInventorySlotContents(func_146017_i, (dispense.stackSize == 0) ? null : dispense);
                }
            }
        }
    }
    
    protected IBehaviorDispenseItem func_149940_a(final ItemStack itemStack) {
        return (IBehaviorDispenseItem)BlockDispenser.dispenseBehaviorRegistry.getObject((itemStack == null) ? null : itemStack.getItem());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean b = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.offsetUp());
        final boolean booleanValue = (boolean)blockState.getValue(BlockDispenser.TRIGGERED);
        if (b && !booleanValue) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.TRIGGERED, true), 4);
        }
        else if (!b && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.TRIGGERED, false), 4);
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            this.func_176439_d(world, blockPos);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDispenser();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, BlockPistonBase.func_180695_a(world, blockPos, entityLivingBase)).withProperty(BlockDispenser.TRIGGERED, false);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.FACING, BlockPistonBase.func_180695_a(world, blockPos, entityLivingBase)), 2);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                ((TileEntityDispenser)tileEntity).func_146018_a(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    public static IPosition getDispensePosition(final IBlockSource blockSource) {
        final EnumFacing facing = getFacing(blockSource.getBlockMetadata());
        return new PositionImpl(blockSource.getX() + 0.7 * facing.getFrontOffsetX(), blockSource.getY() + 0.7 * facing.getFrontOffsetY(), blockSource.getZ() + 0.7 * facing.getFrontOffsetZ());
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getFront(n & 0x7);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, getFacing(n)).withProperty(BlockDispenser.TRIGGERED, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockDispenser.FACING)).getIndex();
        if (blockState.getValue(BlockDispenser.TRIGGERED)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDispenser.FACING, BlockDispenser.TRIGGERED });
    }
}
