package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EnchantmentThorns extends Enchantment
{
    private static final String __OBFID;
    
    public EnchantmentThorns(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_TORSO);
        this.setName("thorns");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 10 + 20 * (n - 1);
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
        return itemStack.getItem() instanceof ItemArmor || super.canApply(itemStack);
    }
    
    @Override
    public void func_151367_b(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        final Random rng = entityLivingBase.getRNG();
        final ItemStack func_92099_a = EnchantmentHelper.func_92099_a(Enchantment.thorns, entityLivingBase);
        if (rng <= 0) {
            entity.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), (float)func_92095_b(n, rng));
            entity.playSound("damage.thorns", 0.5f, 1.0f);
            if (func_92099_a != null) {
                func_92099_a.damageItem(3, entityLivingBase);
            }
        }
        else if (func_92099_a != null) {
            func_92099_a.damageItem(1, entityLivingBase);
        }
    }
    
    public static int func_92095_b(final int n, final Random random) {
        return (n > 10) ? (n - 10) : (1 + random.nextInt(4));
    }
    
    static {
        __OBFID = "CL_00000122";
    }
}
