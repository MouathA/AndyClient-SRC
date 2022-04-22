package net.minecraft.item;

import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemTool extends Item
{
    private Set effectiveBlocksTool;
    protected float efficiencyOnProperMaterial;
    private float damageVsEntity;
    protected ToolMaterial toolMaterial;
    private static final String __OBFID;
    
    protected ItemTool(final float n, final ToolMaterial toolMaterial, final Set effectiveBlocksTool) {
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = toolMaterial;
        this.effectiveBlocksTool = effectiveBlocksTool;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = n + toolMaterial.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return this.effectiveBlocksTool.contains(block) ? this.efficiencyOnProperMaterial : 1.0f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        itemStack.damageItem(2, entityLivingBase2);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(1, entityLivingBase);
        }
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.toolMaterial.getBaseItemForRepair() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(ItemTool.itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
        return itemAttributeModifiers;
    }
    
    static {
        __OBFID = "CL_00000019";
    }
}
