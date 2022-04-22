package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockBrewingStand extends BlockContainer
{
    public static final PropertyBool[] BOTTLE_PROPS;
    private final Random rand;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000207";
        BOTTLE_PROPS = new PropertyBool[] { PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2") };
    }
    
    public BlockBrewingStand() {
        super(Material.iron);
        this.rand = new Random();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBrewingStand.BOTTLE_PROPS[0], false).withProperty(BlockBrewingStand.BOTTLE_PROPS[1], false).withProperty(BlockBrewingStand.BOTTLE_PROPS[2], false));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBrewingStand();
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBrewingStand) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
        }
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand)tileEntity).func_145937_a(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + 0.4f + random.nextFloat() * 0.2f, blockPos.getY() + 0.7f + random.nextFloat() * 0.3f, blockPos.getZ() + 0.4f + random.nextFloat() * 0.2f, 0.0, 0.0, 0.0, new int[0]);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBrewingStand) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.brewing_stand;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.brewing_stand;
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
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState = this.getDefaultState();
        while (0 < 3) {
            blockState = blockState.withProperty(BlockBrewingStand.BOTTLE_PROPS[0], (n & 0x1) > 0);
            int n2 = 0;
            ++n2;
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        while (0 < 3) {
            if (blockState.getValue(BlockBrewingStand.BOTTLE_PROPS[0])) {}
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockBrewingStand.BOTTLE_PROPS[0], BlockBrewingStand.BOTTLE_PROPS[1], BlockBrewingStand.BOTTLE_PROPS[2] });
    }
}
