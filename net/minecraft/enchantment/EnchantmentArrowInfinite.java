package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowInfinite extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentArrowInfinite(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName("arrowInfinite");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 20;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    static {
        __OBFID = "CL_00000100";
    }
}
