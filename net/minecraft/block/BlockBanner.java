package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class BlockBanner extends BlockContainer
{
    public static final PropertyDirection FACING_PROP;
    public static final PropertyInteger ROTATION_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002143";
        FACING_PROP = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
    }
    
    protected BlockBanner() {
        super(Material.wood);
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBanner();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.banner;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.banner;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBanner) {
            final ItemStack itemStack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            nbtTagCompound.removeTag("x");
            nbtTagCompound.removeTag("y");
            nbtTagCompound.removeTag("z");
            nbtTagCompound.removeTag("id");
            itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
        }
        else {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        }
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityBanner) {
            final ItemStack itemStack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            nbtTagCompound.removeTag("x");
            nbtTagCompound.removeTag("y");
            nbtTagCompound.removeTag("z");
            nbtTagCompound.removeTag("id");
            itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, null);
        }
    }
    
    public static class BlockBannerHanging extends BlockBanner
    {
        private static final String __OBFID;
        
        public BlockBannerHanging() {
            this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBannerHanging.FACING_PROP, EnumFacing.NORTH));
        }
        
        @Override
        public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
            final EnumFacing enumFacing = (EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockBannerHanging.FACING_PROP);
            final float n = 0.0f;
            final float n2 = 0.78125f;
            final float n3 = 0.0f;
            final float n4 = 1.0f;
            final float n5 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            switch (SwitchEnumFacing.SWITCH_MAP[enumFacing.ordinal()]) {
                default: {
                    this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                    break;
                }
                case 3: {
                    this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                    break;
                }
            }
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockBannerHanging.FACING_PROP)).getOpposite())).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            EnumFacing enumFacing = EnumFacing.getFront(n);
            if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
                enumFacing = EnumFacing.NORTH;
            }
            return this.getDefaultState().withProperty(BlockBannerHanging.FACING_PROP, enumFacing);
        }
        
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return ((EnumFacing)blockState.getValue(BlockBannerHanging.FACING_PROP)).getIndex();
        }
        
        @Override
        protected BlockState createBlockState() {
            return new BlockState(this, new IProperty[] { BlockBannerHanging.FACING_PROP });
        }
        
        static {
            __OBFID = "CL_00002140";
        }
    }
    
    static final class SwitchEnumFacing
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002142";
            (SwitchEnumFacing.SWITCH_MAP = new int[EnumFacing.values().length])[EnumFacing.NORTH.ordinal()] = 1;
            SwitchEnumFacing.SWITCH_MAP[EnumFacing.SOUTH.ordinal()] = 2;
            SwitchEnumFacing.SWITCH_MAP[EnumFacing.WEST.ordinal()] = 3;
            SwitchEnumFacing.SWITCH_MAP[EnumFacing.EAST.ordinal()] = 4;
        }
    }
    
    public static class BlockBannerStanding extends BlockBanner
    {
        private static final String __OBFID;
        
        public BlockBannerStanding() {
            this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBannerStanding.ROTATION_PROP, 0));
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.offsetDown()).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            return this.getDefaultState().withProperty(BlockBannerStanding.ROTATION_PROP, n);
        }
        
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return (int)blockState.getValue(BlockBannerStanding.ROTATION_PROP);
        }
        
        @Override
        protected BlockState createBlockState() {
            return new BlockState(this, new IProperty[] { BlockBannerStanding.ROTATION_PROP });
        }
        
        static {
            __OBFID = "CL_00002141";
        }
    }
}
