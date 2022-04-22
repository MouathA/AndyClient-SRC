package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentUntouching extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentUntouching(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.DIGGER);
        this.setName("untouching");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 15;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment.effectId != EnchantmentUntouching.fortune.effectId;
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() == Items.shears || super.canApply(itemStack);
    }
    
    static {
        __OBFID = "CL_00000123";
    }
}
