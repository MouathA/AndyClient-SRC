package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.stats.*;

public class ItemFishingRod extends Item
{
    private static final String __OBFID;
    
    public ItemFishingRod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.fishEntity != null) {
            itemStack.damageItem(entityPlayer.fishEntity.handleHookRetraction(), entityPlayer);
            entityPlayer.swingItem();
        }
        else {
            world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemFishingRod.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityFishHook(world, entityPlayer));
            }
            entityPlayer.swingItem();
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }
        return itemStack;
    }
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return super.isItemTool(itemStack);
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    static {
        __OBFID = "CL_00000034";
    }
}
