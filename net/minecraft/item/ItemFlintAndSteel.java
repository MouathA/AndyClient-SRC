package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class ItemFlintAndSteel extends Item
{
    private static final String __OBFID;
    
    public ItemFlintAndSteel() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        offset = offset.offset(enumFacing);
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(offset).getBlock().getMaterial() == Material.air) {
            world.playSoundEffect(offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.itemRand.nextFloat() * 0.4f + 0.8f);
            world.setBlockState(offset, Blocks.fire.getDefaultState());
        }
        itemStack.damageItem(1, entityPlayer);
        return true;
    }
    
    static {
        __OBFID = "CL_00000035";
    }
}
