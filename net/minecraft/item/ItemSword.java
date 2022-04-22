package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemSword extends Item
{
    private float field_150934_a;
    private final ToolMaterial repairMaterial;
    private static final String __OBFID;
    
    public ItemSword(final ToolMaterial repairMaterial) {
        this.repairMaterial = repairMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(repairMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.field_150934_a = 4.0f + repairMaterial.getDamageVsEntity();
    }
    
    public float func_150931_i() {
        return this.repairMaterial.getDamageVsEntity();
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        if (block == Blocks.web) {
            return 15.0f;
        }
        final Material material = block.getMaterial();
        return (material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd) ? 1.0f : 1.5f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        itemStack.damageItem(1, entityLivingBase2);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(2, entityLivingBase);
        }
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BLOCK;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.web;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.repairMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.repairMaterial.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.repairMaterial.getBaseItemForRepair() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(ItemSword.itemModifierUUID, "Weapon modifier", this.field_150934_a, 0));
        return itemAttributeModifiers;
    }
    
    static {
        __OBFID = "CL_00000072";
    }
}
