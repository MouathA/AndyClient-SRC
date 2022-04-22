package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentDigging extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentDigging(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.DIGGER);
        this.setName("digging");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 1 + 10 * (n - 1);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() == Items.shears || super.canApply(itemStack);
    }
    
    static {
        __OBFID = "CL_00000104";
    }
}
