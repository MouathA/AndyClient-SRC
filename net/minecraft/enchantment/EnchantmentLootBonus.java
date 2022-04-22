package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentLootBonus extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentLootBonus(final int n, final ResourceLocation resourceLocation, final int n2, final EnumEnchantmentType enumEnchantmentType) {
        super(n, resourceLocation, n2, enumEnchantmentType);
        if (enumEnchantmentType == EnumEnchantmentType.DIGGER) {
            this.setName("lootBonusDigger");
        }
        else if (enumEnchantmentType == EnumEnchantmentType.FISHING_ROD) {
            this.setName("lootBonusFishing");
        }
        else {
            this.setName("lootBonus");
        }
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
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment.effectId != EnchantmentLootBonus.silkTouch.effectId;
    }
    
    static {
        __OBFID = "CL_00000119";
    }
}
