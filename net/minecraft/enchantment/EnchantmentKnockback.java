package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentKnockback extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.setName("knockback");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 5 + 20 * (n - 1);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
    
    static {
        __OBFID = "CL_00000118";
    }
}
