package net.minecraft.enchantment;

import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class EnchantmentHelper
{
    private static final Random enchantmentRand;
    private static final ModifierDamage enchantmentModifierDamage;
    private static final ModifierLiving enchantmentModifierLiving;
    private static final HurtIterator field_151388_d;
    private static final DamageIterator field_151389_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000107";
        enchantmentRand = new Random();
        enchantmentModifierDamage = new ModifierDamage(null);
        enchantmentModifierLiving = new ModifierLiving(null);
        field_151388_d = new HurtIterator(null);
        field_151389_e = new DamageIterator(null);
    }
    
    public static int getEnchantmentLevel(final int n, final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
        if (enchantmentTagList == null) {
            return 0;
        }
        while (0 < enchantmentTagList.tagCount()) {
            final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
            final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
            if (short1 == n) {
                return short2;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static Map getEnchantments(final ItemStack itemStack) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final NBTTagList list = (itemStack.getItem() == Items.enchanted_book) ? Items.enchanted_book.func_92110_g(itemStack) : itemStack.getEnchantmentTagList();
        if (list != null) {
            while (0 < list.tagCount()) {
                linkedHashMap.put((int)list.getCompoundTagAt(0).getShort("id"), (int)list.getCompoundTagAt(0).getShort("lvl"));
                int n = 0;
                ++n;
            }
        }
        return linkedHashMap;
    }
    
    public static void setEnchantments(final Map map, final ItemStack itemStack) {
        final NBTTagList list = new NBTTagList();
        for (final int intValue : map.keySet()) {
            final Enchantment func_180306_c = Enchantment.func_180306_c(intValue);
            if (func_180306_c != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setShort("id", (short)intValue);
                nbtTagCompound.setShort("lvl", (short)(int)map.get(intValue));
                list.appendTag(nbtTagCompound);
                if (itemStack.getItem() != Items.enchanted_book) {
                    continue;
                }
                Items.enchanted_book.addEnchantment(itemStack, new EnchantmentData(func_180306_c, (int)map.get(intValue)));
            }
        }
        if (list.tagCount() > 0) {
            if (itemStack.getItem() != Items.enchanted_book) {
                itemStack.setTagInfo("ench", list);
            }
        }
        else if (itemStack.hasTagCompound()) {
            itemStack.getTagCompound().removeTag("ench");
        }
    }
    
    public static int getMaxEnchantmentLevel(final int n, final ItemStack[] array) {
        if (array == null) {
            return 0;
        }
        while (0 < array.length) {
            if (getEnchantmentLevel(n, array[0]) > 0) {}
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static void applyEnchantmentModifier(final IModifier modifier, final ItemStack itemStack) {
        if (itemStack != null) {
            final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                while (0 < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                    final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
                    if (Enchantment.func_180306_c(short1) != null) {
                        modifier.calculateModifier(Enchantment.func_180306_c(short1), short2);
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    private static void applyEnchantmentModifierArray(final IModifier modifier, final ItemStack[] array) {
        while (0 < array.length) {
            applyEnchantmentModifier(modifier, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public static int getEnchantmentModifierDamage(final ItemStack[] array, final DamageSource source) {
        EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        EnchantmentHelper.enchantmentModifierDamage.source = source;
        applyEnchantmentModifierArray(EnchantmentHelper.enchantmentModifierDamage, array);
        if (EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;
        }
        return (EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1) + EnchantmentHelper.enchantmentRand.nextInt((EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1) + 1);
    }
    
    public static float func_152377_a(final ItemStack itemStack, final EnumCreatureAttribute entityLiving) {
        EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0.0f;
        EnchantmentHelper.enchantmentModifierLiving.entityLiving = entityLiving;
        applyEnchantmentModifier(EnchantmentHelper.enchantmentModifierLiving, itemStack);
        return EnchantmentHelper.enchantmentModifierLiving.livingModifier;
    }
    
    public static void func_151384_a(final EntityLivingBase field_151364_a, final Entity field_151363_b) {
        EnchantmentHelper.field_151388_d.field_151363_b = field_151363_b;
        EnchantmentHelper.field_151388_d.field_151364_a = field_151364_a;
        if (field_151364_a != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.field_151388_d, field_151364_a.getInventory());
        }
        if (field_151363_b instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.field_151388_d, field_151364_a.getHeldItem());
        }
    }
    
    public static void func_151385_b(final EntityLivingBase field_151366_a, final Entity field_151365_b) {
        EnchantmentHelper.field_151389_e.field_151366_a = field_151366_a;
        EnchantmentHelper.field_151389_e.field_151365_b = field_151365_b;
        if (field_151366_a != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.field_151389_e, field_151366_a.getInventory());
        }
        if (field_151366_a instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.field_151389_e, field_151366_a.getHeldItem());
        }
    }
    
    public static int getRespiration(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.field_180313_o.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getFireAspectModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int func_180319_a(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.field_180317_h.effectId, entity.getInventory());
    }
    
    public static int func_180318_b(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.field_180316_k.effectId, entity.getInventory());
    }
    
    public static int getEfficiencyModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, entityLivingBase.getHeldItem());
    }
    
    public static boolean getSilkTouchModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, entityLivingBase.getHeldItem()) > 0;
    }
    
    public static int getFortuneModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fortune.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int func_151386_g(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int func_151387_h(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.lure.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getLootingModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.looting.effectId, entityLivingBase.getHeldItem());
    }
    
    public static boolean getAquaAffinityModifier(final EntityLivingBase entityLivingBase) {
        return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, entityLivingBase.getInventory()) > 0;
    }
    
    public static ItemStack func_92099_a(final Enchantment enchantment, final EntityLivingBase entityLivingBase) {
        final ItemStack[] inventory = entityLivingBase.getInventory();
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null && getEnchantmentLevel(enchantment.effectId, itemStack) > 0) {
                return itemStack;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static int calcItemStackEnchantability(final Random random, final int n, final int n2, final ItemStack itemStack) {
        if (itemStack.getItem().getItemEnchantability() <= 0) {
            return 0;
        }
        if (15 > 15) {}
        final int n3 = random.nextInt(8) + 1 + 7 + random.nextInt(16);
        return (n == 0) ? Math.max(n3 / 3, 1) : ((n == 1) ? (n3 * 2 / 3 + 1) : Math.max(n3, 30));
    }
    
    public static ItemStack addRandomEnchantment(final Random random, final ItemStack itemStack, final int n) {
        final List buildEnchantmentList = buildEnchantmentList(random, itemStack, n);
        final boolean b = itemStack.getItem() == Items.book;
        if (b) {
            itemStack.setItem(Items.enchanted_book);
        }
        if (buildEnchantmentList != null) {
            for (final EnchantmentData enchantmentData : buildEnchantmentList) {
                if (b) {
                    Items.enchanted_book.addEnchantment(itemStack, enchantmentData);
                }
                else {
                    itemStack.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                }
            }
        }
        return itemStack;
    }
    
    public static List buildEnchantmentList(final Random random, final ItemStack itemStack, final int n) {
        final int itemEnchantability = itemStack.getItem().getItemEnchantability();
        if (itemEnchantability <= 0) {
            return null;
        }
        final int n2 = itemEnchantability / 2;
        final int n3 = (int)((1 + random.nextInt((n2 >> 1) + 1) + random.nextInt((n2 >> 1) + 1) + n) * (1.0f + (random.nextFloat() + random.nextFloat() - 1.0f) * 0.15f) + 0.5f);
        if (1 < 1) {}
        ArrayList<EnchantmentData> arrayList = null;
        final Map mapEnchantmentData = mapEnchantmentData(1, itemStack);
        if (mapEnchantmentData != null && !mapEnchantmentData.isEmpty()) {
            final EnchantmentData enchantmentData = (EnchantmentData)WeightedRandom.getRandomItem(random, mapEnchantmentData.values());
            if (enchantmentData != null) {
                arrayList = (ArrayList<EnchantmentData>)Lists.newArrayList();
                arrayList.add(enchantmentData);
                while (random.nextInt(50) <= 1) {
                    final Iterator<Integer> iterator = mapEnchantmentData.keySet().iterator();
                    while (iterator.hasNext()) {
                        final Integer n4 = iterator.next();
                        final Iterator<EnchantmentData> iterator2 = arrayList.iterator();
                        while (iterator2.hasNext() && iterator2.next().enchantmentobj.canApplyTogether(Enchantment.func_180306_c(n4))) {}
                        if (!false) {
                            iterator.remove();
                        }
                    }
                    if (!mapEnchantmentData.isEmpty()) {
                        arrayList.add((EnchantmentData)WeightedRandom.getRandomItem(random, mapEnchantmentData.values()));
                    }
                }
            }
        }
        return arrayList;
    }
    
    public static Map mapEnchantmentData(final int n, final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        HashMap<Integer, EnchantmentData> hashMap = null;
        final boolean b = itemStack.getItem() == Items.book;
        final Enchantment[] enchantmentsList = Enchantment.enchantmentsList;
        while (0 < enchantmentsList.length) {
            final Enchantment enchantment = enchantmentsList[0];
            if (enchantment != null && (enchantment.type.canEnchantItem(item) || b)) {
                for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                    if (n >= enchantment.getMinEnchantability(i) && n <= enchantment.getMaxEnchantability(i)) {
                        if (hashMap == null) {
                            hashMap = (HashMap<Integer, EnchantmentData>)Maps.newHashMap();
                        }
                        hashMap.put(enchantment.effectId, new EnchantmentData(enchantment, i));
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return hashMap;
    }
    
    static final class DamageIterator implements IModifier
    {
        public EntityLivingBase field_151366_a;
        public Entity field_151365_b;
        private static final String __OBFID;
        
        private DamageIterator() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.func_151368_a(this.field_151366_a, this.field_151365_b, n);
        }
        
        DamageIterator(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00000109";
        }
    }
    
    interface IModifier
    {
        void calculateModifier(final Enchantment p0, final int p1);
    }
    
    static final class HurtIterator implements IModifier
    {
        public EntityLivingBase field_151364_a;
        public Entity field_151363_b;
        private static final String __OBFID;
        
        private HurtIterator() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.func_151367_b(this.field_151364_a, this.field_151363_b, n);
        }
        
        HurtIterator(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00000110";
        }
    }
    
    static final class ModifierDamage implements IModifier
    {
        public int damageModifier;
        public DamageSource source;
        private static final String __OBFID;
        
        private ModifierDamage() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.damageModifier += enchantment.calcModifierDamage(n, this.source);
        }
        
        ModifierDamage(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00000114";
        }
    }
    
    static final class ModifierLiving implements IModifier
    {
        public float livingModifier;
        public EnumCreatureAttribute entityLiving;
        private static final String __OBFID;
        
        private ModifierLiving() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.livingModifier += enchantment.func_152376_a(n, this.entityLiving);
        }
        
        ModifierLiving(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00000112";
        }
    }
}
