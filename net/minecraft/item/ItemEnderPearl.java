package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;

public class ItemEnderPearl extends Item
{
    private static final String __OBFID;
    
    public ItemEnderPearl() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            return itemStack;
        }
        --itemStack.stackSize;
        world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemEnderPearl.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEnderPearl(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00000027";
    }
}
