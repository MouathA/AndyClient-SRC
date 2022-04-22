package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;

public class ItemExpBottle extends Item
{
    private static final String __OBFID;
    
    public ItemExpBottle() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemExpBottle.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityExpBottle(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00000028";
    }
}
