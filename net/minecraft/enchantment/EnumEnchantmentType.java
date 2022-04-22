package net.minecraft.enchantment;

import net.minecraft.item.*;

public enum EnumEnchantmentType
{
    ALL("ALL", 0, "ALL", 0), 
    ARMOR("ARMOR", 1, "ARMOR", 1), 
    ARMOR_FEET("ARMOR_FEET", 2, "ARMOR_FEET", 2), 
    ARMOR_LEGS("ARMOR_LEGS", 3, "ARMOR_LEGS", 3), 
    ARMOR_TORSO("ARMOR_TORSO", 4, "ARMOR_TORSO", 4), 
    ARMOR_HEAD("ARMOR_HEAD", 5, "ARMOR_HEAD", 5), 
    WEAPON("WEAPON", 6, "WEAPON", 6), 
    DIGGER("DIGGER", 7, "DIGGER", 7), 
    FISHING_ROD("FISHING_ROD", 8, "FISHING_ROD", 8), 
    BREAKABLE("BREAKABLE", 9, "BREAKABLE", 9), 
    BOW("BOW", 10, "BOW", 10);
    
    private static final EnumEnchantmentType[] $VALUES;
    private static final String __OBFID;
    private static final EnumEnchantmentType[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00000106";
        ENUM$VALUES = new EnumEnchantmentType[] { EnumEnchantmentType.ALL, EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE, EnumEnchantmentType.BOW };
        $VALUES = new EnumEnchantmentType[] { EnumEnchantmentType.ALL, EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE, EnumEnchantmentType.BOW };
    }
    
    private EnumEnchantmentType(final String s, final int n, final String s2, final int n2) {
    }
    
    public boolean canEnchantItem(final Item item) {
        if (this == EnumEnchantmentType.ALL) {
            return true;
        }
        if (this == EnumEnchantmentType.BREAKABLE && item.isDamageable()) {
            return true;
        }
        if (!(item instanceof ItemArmor)) {
            return (item instanceof ItemSword) ? (this == EnumEnchantmentType.WEAPON) : ((item instanceof ItemTool) ? (this == EnumEnchantmentType.DIGGER) : ((item instanceof ItemBow) ? (this == EnumEnchantmentType.BOW) : (item instanceof ItemFishingRod && this == EnumEnchantmentType.FISHING_ROD)));
        }
        if (this == EnumEnchantmentType.ARMOR) {
            return true;
        }
        final ItemArmor itemArmor = (ItemArmor)item;
        return (itemArmor.armorType == 0) ? (this == EnumEnchantmentType.ARMOR_HEAD) : ((itemArmor.armorType == 2) ? (this == EnumEnchantmentType.ARMOR_LEGS) : ((itemArmor.armorType == 1) ? (this == EnumEnchantmentType.ARMOR_TORSO) : (itemArmor.armorType == 3 && this == EnumEnchantmentType.ARMOR_FEET)));
    }
}
