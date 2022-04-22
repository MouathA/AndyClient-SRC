package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class ItemShears extends Item
{
    private static final String __OBFID;
    
    public ItemShears() {
        this.setMaxStackSize(1);
        this.setMaxDamage(238);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getMaterial() != Material.leaves && block != Blocks.web && block != Blocks.tallgrass && block != Blocks.vine && block != Blocks.tripwire && block != Blocks.wool) {
            return super.onBlockDestroyed(itemStack, world, block, blockPos, entityLivingBase);
        }
        itemStack.damageItem(1, entityLivingBase);
        return true;
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.web || block == Blocks.redstone_wire || block == Blocks.tripwire;
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return (block != Blocks.web && block.getMaterial() != Material.leaves) ? ((block == Blocks.wool) ? 5.0f : super.getStrVsBlock(itemStack, block)) : 15.0f;
    }
    
    static {
        __OBFID = "CL_00000062";
    }
}
