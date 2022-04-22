package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;

public class BlockJukebox extends BlockContainer
{
    public static final PropertyBool HAS_RECORD;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000260";
        HAS_RECORD = PropertyBool.create("has_record");
    }
    
    protected BlockJukebox() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockJukebox.HAS_RECORD, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (withProperty.getValue(BlockJukebox.HAS_RECORD)) {
            this.dropRecord(world, blockPos, withProperty);
            withProperty = withProperty.withProperty(BlockJukebox.HAS_RECORD, false);
            world.setBlockState(blockPos, withProperty, 2);
            return true;
        }
        return false;
    }
    
    public void insertRecord(final World world, final BlockPos blockPos, final IBlockState blockState, final ItemStack itemStack) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityJukebox) {
                ((TileEntityJukebox)tileEntity).setRecord(new ItemStack(itemStack.getItem(), 1, itemStack.getMetadata()));
                world.setBlockState(blockPos, blockState.withProperty(BlockJukebox.HAS_RECORD, true), 2);
            }
        }
    }
    
    private void dropRecord(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityJukebox) {
                final TileEntityJukebox tileEntityJukebox = (TileEntityJukebox)tileEntity;
                final ItemStack record = tileEntityJukebox.getRecord();
                if (record != null) {
                    world.playAuxSFX(1005, blockPos, 0);
                    world.func_175717_a(blockPos, null);
                    tileEntityJukebox.setRecord(null);
                    final float n = 0.7f;
                    final EntityItem entityItem = new EntityItem(world, blockPos.getX() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getY() + (world.rand.nextFloat() * n + (1.0f - n) * 0.2 + 0.6), blockPos.getZ() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), record.copy());
                    entityItem.setDefaultPickupDelay();
                    world.spawnEntityInWorld(entityItem);
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.dropRecord(world, blockPos, blockState);
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, 0);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityJukebox();
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityJukebox) {
            final ItemStack record = ((TileEntityJukebox)tileEntity).getRecord();
            if (record != null) {
                return Item.getIdFromItem(record.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
            }
        }
        return 0;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockJukebox.HAS_RECORD, n > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockJukebox.HAS_RECORD)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockJukebox.HAS_RECORD });
    }
    
    public static class TileEntityJukebox extends TileEntity
    {
        private ItemStack record;
        private static final String __OBFID;
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            if (nbtTagCompound.hasKey("RecordItem", 10)) {
                this.setRecord(ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("RecordItem")));
            }
            else if (nbtTagCompound.getInteger("Record") > 0) {
                this.setRecord(new ItemStack(Item.getItemById(nbtTagCompound.getInteger("Record")), 1, 0));
            }
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            if (this.getRecord() != null) {
                nbtTagCompound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
            }
        }
        
        public ItemStack getRecord() {
            return this.record;
        }
        
        public void setRecord(final ItemStack record) {
            this.record = record;
            this.markDirty();
        }
        
        static {
            __OBFID = "CL_00000261";
        }
    }
}
