package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;

public class ItemBucketMilk extends Item
{
    private static final String __OBFID;
    
    public ItemBucketMilk() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        if (!world.isRemote) {
            entityPlayer.clearActivePotions();
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return (itemStack.stackSize <= 0) ? new ItemStack(Items.bucket) : itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.DRINK;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00000048";
    }
}
