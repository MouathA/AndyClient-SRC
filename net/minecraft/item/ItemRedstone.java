package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class ItemRedstone extends Item
{
    private static final String __OBFID;
    
    public ItemRedstone() {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final BlockPos blockPos2 = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos) ? blockPos : blockPos.offset(enumFacing);
        if (!entityPlayer.func_175151_a(blockPos2, enumFacing, itemStack)) {
            return false;
        }
        if (!world.canBlockBePlaced(world.getBlockState(blockPos2).getBlock(), blockPos2, false, enumFacing, null, itemStack)) {
            return false;
        }
        if (Blocks.redstone_wire.canPlaceBlockAt(world, blockPos2)) {
            --itemStack.stackSize;
            world.setBlockState(blockPos2, Blocks.redstone_wire.getDefaultState());
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00000058";
    }
}
