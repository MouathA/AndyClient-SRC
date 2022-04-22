package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentWaterWalker extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentWaterWalker(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_FEET);
        this.setName("waterWalker");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return n * 10;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 15;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    static {
        __OBFID = "CL_00002155";
    }
}
