package net.minecraft.enchantment;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;

public class EnchantmentDurability extends Enchantment
{
    private static final String __OBFID;
    
    protected EnchantmentDurability(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BREAKABLE);
        this.setName("durability");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 5 + (n - 1) * 8;
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
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.isItemStackDamageable() || super.canApply(itemStack);
    }
    
    public static boolean negateDamage(final ItemStack itemStack, final int n, final Random random) {
        return (!(itemStack.getItem() instanceof ItemArmor) || random.nextFloat() >= 0.6f) && random.nextInt(n + 1) > 0;
    }
    
    static {
        __OBFID = "CL_00000103";
    }
}
