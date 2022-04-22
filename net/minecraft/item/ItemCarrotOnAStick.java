package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class ItemCarrotOnAStick extends Item
{
    private static final String __OBFID;
    
    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
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
        if (entityPlayer.isRiding() && entityPlayer.ridingEntity instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityPlayer.ridingEntity;
            if (entityPig.getAIControlledByPlayer().isControlledByPlayer() && itemStack.getMaxDamage() - itemStack.getMetadata() >= 7) {
                entityPig.getAIControlledByPlayer().boostSpeed();
                itemStack.damageItem(7, entityPlayer);
                if (itemStack.stackSize == 0) {
                    final ItemStack itemStack2 = new ItemStack(Items.fishing_rod);
                    itemStack2.setTagCompound(itemStack.getTagCompound());
                    return itemStack2;
                }
            }
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00000001";
    }
}
