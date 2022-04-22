package net.minecraft.enchantment;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public abstract class Enchantment
{
    private static final Enchantment[] field_180311_a;
    public static final Enchantment[] enchantmentsList;
    private static final Map field_180307_E;
    public static final Enchantment field_180310_c;
    public static final Enchantment fireProtection;
    public static final Enchantment field_180309_e;
    public static final Enchantment blastProtection;
    public static final Enchantment field_180308_g;
    public static final Enchantment field_180317_h;
    public static final Enchantment aquaAffinity;
    public static final Enchantment thorns;
    public static final Enchantment field_180316_k;
    public static final Enchantment field_180314_l;
    public static final Enchantment field_180315_m;
    public static final Enchantment field_180312_n;
    public static final Enchantment field_180313_o;
    public static final Enchantment fireAspect;
    public static final Enchantment looting;
    public static final Enchantment efficiency;
    public static final Enchantment silkTouch;
    public static final Enchantment unbreaking;
    public static final Enchantment fortune;
    public static final Enchantment power;
    public static final Enchantment punch;
    public static final Enchantment flame;
    public static final Enchantment infinity;
    public static final Enchantment luckOfTheSea;
    public static final Enchantment lure;
    public final int effectId;
    private final int weight;
    public EnumEnchantmentType type;
    protected String name;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000105";
        field_180311_a = new Enchantment[256];
        field_180307_E = Maps.newHashMap();
        field_180310_c = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
        fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
        field_180309_e = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
        blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
        field_180308_g = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
        field_180317_h = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
        aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
        thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
        field_180316_k = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
        field_180314_l = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
        field_180315_m = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
        field_180312_n = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
        field_180313_o = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
        fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
        looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
        efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
        silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
        unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
        fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
        power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
        punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
        flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
        infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
        luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
        lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
        final ArrayList arrayList = Lists.newArrayList();
        final Enchantment[] field_180311_a2 = Enchantment.field_180311_a;
        while (0 < field_180311_a2.length) {
            final Enchantment enchantment = field_180311_a2[0];
            if (enchantment != null) {
                arrayList.add(enchantment);
            }
            int n = 0;
            ++n;
        }
        enchantmentsList = arrayList.toArray(new Enchantment[arrayList.size()]);
    }
    
    public static Enchantment func_180306_c(final int n) {
        return (n >= 0 && n < Enchantment.field_180311_a.length) ? Enchantment.field_180311_a[n] : null;
    }
    
    protected Enchantment(final int effectId, final ResourceLocation resourceLocation, final int weight, final EnumEnchantmentType type) {
        this.effectId = effectId;
        this.weight = weight;
        this.type = type;
        if (Enchantment.field_180311_a[effectId] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.field_180311_a[effectId] = this;
        Enchantment.field_180307_E.put(resourceLocation, this);
    }
    
    public static Enchantment func_180305_b(final String s) {
        return Enchantment.field_180307_E.get(new ResourceLocation(s));
    }
    
    public static String[] func_180304_c() {
        final String[] array = new String[Enchantment.field_180307_E.size()];
        for (final ResourceLocation resourceLocation : Enchantment.field_180307_E.keySet()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = resourceLocation.toString();
        }
        return array;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getMinLevel() {
        return 1;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public int getMinEnchantability(final int n) {
        return 1 + n * 10;
    }
    
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 5;
    }
    
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        return 0;
    }
    
    public float func_152376_a(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        return 0.0f;
    }
    
    public boolean canApplyTogether(final Enchantment enchantment) {
        return this != enchantment;
    }
    
    public Enchantment setName(final String name) {
        this.name = name;
        return this;
    }
    
    public String getName() {
        return "enchantment." + this.name;
    }
    
    public String getTranslatedName(final int n) {
        return String.valueOf(StatCollector.translateToLocal(this.getName())) + " " + StatCollector.translateToLocal("enchantment.level." + n);
    }
    
    public boolean canApply(final ItemStack itemStack) {
        return this.type.canEnchantItem(itemStack.getItem());
    }
    
    public void func_151368_a(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public void func_151367_b(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public static Enchantment getEnchantmentById(final int n) {
        return (n >= 0 && n < Enchantment.enchantmentsList.length) ? Enchantment.enchantmentsList[n] : null;
    }
}
