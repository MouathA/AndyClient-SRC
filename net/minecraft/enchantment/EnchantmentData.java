package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentData extends WeightedRandom.Item
{
    public final Enchantment enchantmentobj;
    public final int enchantmentLevel;
    private static final String __OBFID;
    
    public EnchantmentData(final Enchantment enchantmentobj, final int enchantmentLevel) {
        super(enchantmentobj.getWeight());
        this.enchantmentobj = enchantmentobj;
        this.enchantmentLevel = enchantmentLevel;
    }
    
    static {
        __OBFID = "CL_00000115";
    }
}
