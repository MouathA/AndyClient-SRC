package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockHopper extends BlockContainer
{
    public static final PropertyDirection field_176430_a;
    public static final PropertyBool field_176429_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000257";
        field_176430_a = PropertyDirection.create("facing", new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180180_a(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.UP;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180180_a((EnumFacing)o);
            }
            
            static {
                __OBFID = "CL_00002106";
            }
        });
        field_176429_b = PropertyBool.create("enabled");
    }
    
    public BlockHopper() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHopper.field_176430_a, EnumFacing.DOWN).withProperty(BlockHopper.field_176429_b, true));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final float n = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        EnumFacing enumFacing2 = enumFacing.getOpposite();
        if (enumFacing2 == EnumFacing.UP) {
            enumFacing2 = EnumFacing.DOWN;
        }
        return this.getDefaultState().withProperty(BlockHopper.field_176430_a, enumFacing2).withProperty(BlockHopper.field_176429_b, true);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityHopper();
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityHopper) {
                ((TileEntityHopper)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176427_e(world, blockPos, blockState);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.func_176427_e(world, blockPos, blockState);
    }
    
    private void func_176427_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean b = !world.isBlockPowered(blockPos);
        if (b != (boolean)blockState.getValue(BlockHopper.field_176429_b)) {
            world.setBlockState(blockPos, blockState.withProperty(BlockHopper.field_176429_b, b), 4);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int getRenderType() {
        return 3;
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
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    public static EnumFacing func_176428_b(final int n) {
        return EnumFacing.getFront(n & 0x7);
    }
    
    public static boolean getActiveStateFromMetadata(final int n) {
        return (n & 0x8) != 0x8;
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
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockHopper.field_176430_a, func_176428_b(n)).withProperty(BlockHopper.field_176429_b, getActiveStateFromMetadata(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockHopper.field_176430_a)).getIndex();
        if (!(boolean)blockState.getValue(BlockHopper.field_176429_b)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHopper.field_176430_a, BlockHopper.field_176429_b });
    }
}
