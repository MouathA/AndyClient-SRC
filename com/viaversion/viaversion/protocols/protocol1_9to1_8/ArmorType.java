package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import java.util.*;

public enum ArmorType
{
    LEATHER_HELMET("LEATHER_HELMET", 0, 1, 298, "minecraft:leather_helmet"), 
    LEATHER_CHESTPLATE("LEATHER_CHESTPLATE", 1, 3, 299, "minecraft:leather_chestplate"), 
    LEATHER_LEGGINGS("LEATHER_LEGGINGS", 2, 2, 300, "minecraft:leather_leggings"), 
    LEATHER_BOOTS("LEATHER_BOOTS", 3, 1, 301, "minecraft:leather_boots"), 
    CHAINMAIL_HELMET("CHAINMAIL_HELMET", 4, 2, 302, "minecraft:chainmail_helmet"), 
    CHAINMAIL_CHESTPLATE("CHAINMAIL_CHESTPLATE", 5, 5, 303, "minecraft:chainmail_chestplate"), 
    CHAINMAIL_LEGGINGS("CHAINMAIL_LEGGINGS", 6, 4, 304, "minecraft:chainmail_leggings"), 
    CHAINMAIL_BOOTS("CHAINMAIL_BOOTS", 7, 1, 305, "minecraft:chainmail_boots"), 
    IRON_HELMET("IRON_HELMET", 8, 2, 306, "minecraft:iron_helmet"), 
    IRON_CHESTPLATE("IRON_CHESTPLATE", 9, 6, 307, "minecraft:iron_chestplate"), 
    IRON_LEGGINGS("IRON_LEGGINGS", 10, 5, 308, "minecraft:iron_leggings"), 
    IRON_BOOTS("IRON_BOOTS", 11, 2, 309, "minecraft:iron_boots"), 
    DIAMOND_HELMET("DIAMOND_HELMET", 12, 3, 310, "minecraft:diamond_helmet"), 
    DIAMOND_CHESTPLATE("DIAMOND_CHESTPLATE", 13, 8, 311, "minecraft:diamond_chestplate"), 
    DIAMOND_LEGGINGS("DIAMOND_LEGGINGS", 14, 6, 312, "minecraft:diamond_leggings"), 
    DIAMOND_BOOTS("DIAMOND_BOOTS", 15, 3, 313, "minecraft:diamond_boots"), 
    GOLD_HELMET("GOLD_HELMET", 16, 2, 314, "minecraft:gold_helmet"), 
    GOLD_CHESTPLATE("GOLD_CHESTPLATE", 17, 5, 315, "minecraft:gold_chestplate"), 
    GOLD_LEGGINGS("GOLD_LEGGINGS", 18, 3, 316, "minecraft:gold_leggings"), 
    GOLD_BOOTS("GOLD_BOOTS", 19, 1, 317, "minecraft:gold_boots"), 
    NONE("NONE", 20, 0, 0, "none");
    
    private static final Map armor;
    private final int armorPoints;
    private final int id;
    private final String type;
    private static final ArmorType[] $VALUES;
    
    private ArmorType(final String s, final int n, final int armorPoints, final int id, final String type) {
        this.armorPoints = armorPoints;
        this.id = id;
        this.type = type;
    }
    
    public int getArmorPoints() {
        return this.armorPoints;
    }
    
    public String getType() {
        return this.type;
    }
    
    public static ArmorType findById(final int n) {
        final ArmorType armorType = ArmorType.armor.get(n);
        return (armorType == null) ? ArmorType.NONE : armorType;
    }
    
    public static ArmorType findByType(final String s) {
        final ArmorType[] values = values();
        while (0 < values.length) {
            final ArmorType armorType = values[0];
            if (armorType.getType().equals(s)) {
                return armorType;
            }
            int n = 0;
            ++n;
        }
        return ArmorType.NONE;
    }
    
    public static boolean isArmor(final int n) {
        return ArmorType.armor.containsKey(n);
    }
    
    public static boolean isArmor(final String s) {
        final ArmorType[] values = values();
        while (0 < values.length) {
            if (values[0].getType().equals(s)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        $VALUES = new ArmorType[] { ArmorType.LEATHER_HELMET, ArmorType.LEATHER_CHESTPLATE, ArmorType.LEATHER_LEGGINGS, ArmorType.LEATHER_BOOTS, ArmorType.CHAINMAIL_HELMET, ArmorType.CHAINMAIL_CHESTPLATE, ArmorType.CHAINMAIL_LEGGINGS, ArmorType.CHAINMAIL_BOOTS, ArmorType.IRON_HELMET, ArmorType.IRON_CHESTPLATE, ArmorType.IRON_LEGGINGS, ArmorType.IRON_BOOTS, ArmorType.DIAMOND_HELMET, ArmorType.DIAMOND_CHESTPLATE, ArmorType.DIAMOND_LEGGINGS, ArmorType.DIAMOND_BOOTS, ArmorType.GOLD_HELMET, ArmorType.GOLD_CHESTPLATE, ArmorType.GOLD_LEGGINGS, ArmorType.GOLD_BOOTS, ArmorType.NONE };
        armor = new HashMap();
        final ArmorType[] values = values();
        while (0 < values.length) {
            final ArmorType armorType = values[0];
            ArmorType.armor.put(armorType.getId(), armorType);
            int n = 0;
            ++n;
        }
    }
}
