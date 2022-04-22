package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowKnockback extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentArrowKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName("arrowKnockback");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 12 + (n - 1) * 20;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 25;
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
    
    static {
        __OBFID = "CL_00000101";
    }
}
