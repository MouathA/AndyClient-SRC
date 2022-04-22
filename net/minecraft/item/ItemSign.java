package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class ItemSign extends Item
{
    private static final String __OBFID;
    
    public ItemSign() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
            return false;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (!Blocks.standing_sign.canPlaceBlockAt(world, offset)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        if (enumFacing == EnumFacing.UP) {
            world.setBlockState(offset, Blocks.standing_sign.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, MathHelper.floor_double((entityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF), 3);
        }
        else {
            world.setBlockState(offset, Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.field_176412_a, enumFacing), 3);
        }
        --itemStack.stackSize;
        final TileEntity tileEntity = world.getTileEntity(offset);
        if (tileEntity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(world, offset, itemStack)) {
            entityPlayer.func_175141_a((TileEntitySign)tileEntity);
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000064";
    }
}
