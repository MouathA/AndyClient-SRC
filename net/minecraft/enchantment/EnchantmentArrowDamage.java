package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowDamage extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentArrowDamage(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName("arrowDamage");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 1 + (n - 1) * 10;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 15;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    static {
        __OBFID = "CL_00000098";
    }
}
