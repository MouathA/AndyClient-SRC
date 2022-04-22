package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemSlab extends ItemBlock
{
    private final BlockSlab field_150949_c;
    private final BlockSlab field_179226_c;
    private static final String __OBFID;
    
    public ItemSlab(final Block block, final BlockSlab field_150949_c, final BlockSlab field_179226_c) {
        super(block);
        this.field_150949_c = field_150949_c;
        this.field_179226_c = field_179226_c;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.field_150949_c.getFullSlabName(itemStack.getMetadata());
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (!entityPlayer.func_175151_a(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final Object func_176553_a = this.field_150949_c.func_176553_a(itemStack);
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.field_150949_c) {
            final IProperty func_176551_l = this.field_150949_c.func_176551_l();
            final Comparable value = blockState.getValue(func_176551_l);
            final BlockSlab.EnumBlockHalf enumBlockHalf = (BlockSlab.EnumBlockHalf)blockState.getValue(BlockSlab.HALF_PROP);
            if (((enumFacing == EnumFacing.UP && enumBlockHalf == BlockSlab.EnumBlockHalf.BOTTOM) || (enumFacing == EnumFacing.DOWN && enumBlockHalf == BlockSlab.EnumBlockHalf.TOP)) && value == func_176553_a) {
                final IBlockState withProperty = this.field_179226_c.getDefaultState().withProperty(func_176551_l, value);
                if (world.checkNoEntityCollision(this.field_179226_c.getCollisionBoundingBox(world, blockPos, withProperty)) && world.setBlockState(blockPos, withProperty, 3)) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, this.field_179226_c.stepSound.getPlaceSound(), (this.field_179226_c.stepSound.getVolume() + 1.0f) / 2.0f, this.field_179226_c.stepSound.getFrequency() * 0.8f);
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        return blockPos.offset(enumFacing) == func_176553_a || super.onItemUse(itemStack, entityPlayer, world, blockPos, enumFacing, n, n2, n3);
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, BlockPos offset, final EnumFacing enumFacing, final EntityPlayer entityPlayer, final ItemStack itemStack) {
        final BlockPos blockPos = offset;
        final IProperty func_176551_l = this.field_150949_c.func_176551_l();
        final Object func_176553_a = this.field_150949_c.func_176553_a(itemStack);
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == this.field_150949_c) {
            final boolean b = blockState.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;
            if (((enumFacing == EnumFacing.UP && !b) || (enumFacing == EnumFacing.DOWN && b)) && func_176553_a == blockState.getValue(func_176551_l)) {
                return true;
            }
        }
        offset = offset.offset(enumFacing);
        final IBlockState blockState2 = world.getBlockState(offset);
        return (blockState2.getBlock() == this.field_150949_c && func_176553_a == blockState2.getValue(func_176551_l)) || super.canPlaceBlockOnSide(world, blockPos, enumFacing, entityPlayer, itemStack);
    }
    
    static {
        __OBFID = "CL_00000071";
    }
}
