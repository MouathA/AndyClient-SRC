package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class ItemFireball extends Item
{
    private static final String __OBFID;
    
    public ItemFireball() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(offset).getBlock().getMaterial() == Material.air) {
            world.playSoundEffect(offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, "item.fireCharge.use", 1.0f, (ItemFireball.itemRand.nextFloat() - ItemFireball.itemRand.nextFloat()) * 0.2f + 1.0f);
            world.setBlockState(offset, Blocks.fire.getDefaultState());
        }
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000029";
    }
}
