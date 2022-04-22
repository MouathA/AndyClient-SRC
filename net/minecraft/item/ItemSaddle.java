package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ItemSaddle extends Item
{
    private static final String __OBFID;
    
    public ItemSaddle() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityLivingBase;
            if (!entityPig.getSaddled() && !entityPig.isChild()) {
                entityPig.setSaddled(true);
                entityPig.worldObj.playSoundAtEntity(entityPig, "mob.horse.leather", 0.5f, 1.0f);
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        this.itemInteractionForEntity(itemStack, null, entityLivingBase);
        return true;
    }
    
    static {
        __OBFID = "CL_00000059";
    }
}
