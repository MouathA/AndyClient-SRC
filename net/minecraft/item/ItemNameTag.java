package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class ItemNameTag extends Item
{
    private static final String __OBFID;
    
    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (!itemStack.hasDisplayName()) {
            return false;
        }
        if (entityLivingBase instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entityLivingBase;
            entityLiving.setCustomNameTag(itemStack.getDisplayName());
            entityLiving.enablePersistence();
            --itemStack.stackSize;
            return true;
        }
        return super.itemInteractionForEntity(itemStack, entityPlayer, entityLivingBase);
    }
    
    static {
        __OBFID = "CL_00000052";
    }
}
