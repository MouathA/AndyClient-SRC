package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentOxygen extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentOxygen(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_HEAD);
        this.setName("oxygen");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 10 * n;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 30;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    static {
        __OBFID = "CL_00000120";
    }
}
