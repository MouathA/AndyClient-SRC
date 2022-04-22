package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class ItemGlassBottle extends Item
{
    private static final String __OBFID;
    
    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos func_178782_a = movingObjectPositionFromPlayer.func_178782_a();
            if (!world.isBlockModifiable(entityPlayer, func_178782_a)) {
                return itemStack;
            }
            if (!entityPlayer.func_175151_a(func_178782_a.offset(movingObjectPositionFromPlayer.field_178784_b), movingObjectPositionFromPlayer.field_178784_b, itemStack)) {
                return itemStack;
            }
            if (world.getBlockState(func_178782_a).getBlock().getMaterial() == Material.water) {
                --itemStack.stackSize;
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                if (itemStack.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00001776";
    }
}
