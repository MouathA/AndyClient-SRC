package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockEnderChest extends BlockContainer
{
    public static final PropertyDirection field_176437_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000238";
        field_176437_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockEnderChest() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockEnderChest.field_176437_a, EnumFacing.NORTH));
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
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 8;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockEnderChest.field_176437_a, entityLivingBase.func_174811_aO().getOpposite());
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockEnderChest.field_176437_a, entityLivingBase.func_174811_aO().getOpposite()), 2);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final InventoryEnderChest inventoryEnderChest = entityPlayer.getInventoryEnderChest();
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (inventoryEnderChest == null || !(tileEntity instanceof TileEntityEnderChest)) {
            return true;
        }
        if (world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube()) {
            return true;
        }
        if (world.isRemote) {
            return true;
        }
        inventoryEnderChest.setChestTileEntity((TileEntityEnderChest)tileEntity);
        entityPlayer.displayGUIChest(inventoryEnderChest);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        while (0 < 3) {
            final int n = random.nextInt(2) * 2 - 1;
            final int n2 = random.nextInt(2) * 2 - 1;
            world.spawnParticle(EnumParticleTypes.PORTAL, blockPos.getX() + 0.5 + 0.25 * n, blockPos.getY() + random.nextFloat(), blockPos.getZ() + 0.5 + 0.25 * n2, random.nextFloat() * n, (random.nextFloat() - 0.5) * 0.125, random.nextFloat() * n2, new int[0]);
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockEnderChest.field_176437_a, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockEnderChest.field_176437_a)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockEnderChest.field_176437_a });
    }
}
