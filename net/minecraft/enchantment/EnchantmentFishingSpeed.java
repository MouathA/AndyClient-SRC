package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentFishingSpeed extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentFishingSpeed(final int n, final ResourceLocation resourceLocation, final int n2, final EnumEnchantmentType enumEnchantmentType) {
        super(n, resourceLocation, n2, enumEnchantmentType);
        this.setName("fishingSpeed");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 15 + (n - 1) * 9;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    static {
        __OBFID = "CL_00000117";
    }
}
