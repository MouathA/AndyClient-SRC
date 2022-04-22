package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class ItemRewriter
{
    private static final Map ENTTIY_NAME_TO_ID;
    private static final Map ENTTIY_ID_TO_NAME;
    private static final Map POTION_NAME_TO_ID;
    private static final Map POTION_ID_TO_NAME;
    private static final Int2IntMap POTION_INDEX;
    
    public static void toServer(final Item item) {
        if (item != null) {
            if (item.identifier() == 383 && item.data() == 0) {
                final CompoundTag tag = item.tag();
                if (tag != null && tag.get("EntityTag") instanceof CompoundTag) {
                    final CompoundTag compoundTag = (CompoundTag)tag.get("EntityTag");
                    if (compoundTag.get("id") instanceof StringTag) {
                        final StringTag stringTag = (StringTag)compoundTag.get("id");
                        if (ItemRewriter.ENTTIY_NAME_TO_ID.containsKey(stringTag.getValue())) {
                            (int)ItemRewriter.ENTTIY_NAME_TO_ID.get(stringTag.getValue());
                        }
                    }
                    tag.remove("EntityTag");
                }
                item.setTag(tag);
                item.setData((short)0);
            }
            if (item.identifier() == 373) {
                final CompoundTag tag2 = item.tag();
                if (tag2 != null && tag2.get("Potion") instanceof StringTag) {
                    final String replace = ((StringTag)tag2.get("Potion")).getValue().replace("minecraft:", "");
                    if (ItemRewriter.POTION_NAME_TO_ID.containsKey(replace)) {
                        (int)ItemRewriter.POTION_NAME_TO_ID.get(replace);
                    }
                    tag2.remove("Potion");
                }
                item.setTag(tag2);
                item.setData((short)0);
            }
            if (item.identifier() == 438) {
                final CompoundTag tag3 = item.tag();
                item.setIdentifier(373);
                if (tag3 != null && tag3.get("Potion") instanceof StringTag) {
                    final String replace2 = ((StringTag)tag3.get("Potion")).getValue().replace("minecraft:", "");
                    if (ItemRewriter.POTION_NAME_TO_ID.containsKey(replace2)) {
                        final int n = ItemRewriter.POTION_NAME_TO_ID.get(replace2) + 8192;
                    }
                    tag3.remove("Potion");
                }
                item.setTag(tag3);
                item.setData((short)0);
            }
            if ((item.identifier() >= 198 && item.identifier() <= 212) | (item.identifier() == 397 && item.data() == 5) | (item.identifier() >= 432 && item.identifier() <= 448)) {
                item.setIdentifier(1);
                item.setData((short)0);
            }
        }
    }
    
    public static void rewriteBookToServer(final Item item) {
        if (item.identifier() != 387) {
            return;
        }
        final ListTag listTag = (ListTag)item.tag().get("pages");
        if (listTag == null) {
            return;
        }
        while (0 < listTag.size()) {
            final Tag value = listTag.get(0);
            if (value instanceof StringTag) {
                final StringTag stringTag = (StringTag)value;
                final String value2 = stringTag.getValue();
                String value3;
                if (value2.replaceAll(" ", "").isEmpty()) {
                    value3 = "\"" + fixBookSpaceChars(value2) + "\"";
                }
                else {
                    value3 = fixBookSpaceChars(value2);
                }
                stringTag.setValue(value3);
            }
            int n = 0;
            ++n;
        }
    }
    
    private static String fixBookSpaceChars(String string) {
        if (!string.startsWith(" ")) {
            return string;
        }
        string = "§r" + string;
        return string;
    }
    
    public static void toClient(final Item item) {
        if (item != null) {
            if (item.identifier() == 383 && item.data() != 0) {
                CompoundTag tag = item.tag();
                if (tag == null) {
                    tag = new CompoundTag();
                }
                final CompoundTag compoundTag = new CompoundTag();
                final String s = ItemRewriter.ENTTIY_ID_TO_NAME.get((int)item.data());
                if (s != null) {
                    compoundTag.put("id", new StringTag(s));
                    tag.put("EntityTag", compoundTag);
                }
                item.setTag(tag);
                item.setData((short)0);
            }
            if (item.identifier() == 373) {
                CompoundTag tag2 = item.tag();
                if (tag2 == null) {
                    tag2 = new CompoundTag();
                }
                if (item.data() >= 16384) {
                    item.setIdentifier(438);
                    item.setData((short)(item.data() - 8192));
                }
                tag2.put("Potion", new StringTag("minecraft:" + potionNameFromDamage(item.data())));
                item.setTag(tag2);
                item.setData((short)0);
            }
            if (item.identifier() == 387) {
                CompoundTag tag3 = item.tag();
                if (tag3 == null) {
                    tag3 = new CompoundTag();
                }
                final ListTag listTag = (ListTag)tag3.get("pages");
                if (listTag == null) {
                    tag3.put("pages", new ListTag(Collections.singletonList(new StringTag(Protocol1_9To1_8.fixJson("").toString()))));
                    item.setTag(tag3);
                    return;
                }
                while (0 < listTag.size()) {
                    if (listTag.get(0) instanceof StringTag) {
                        final StringTag stringTag = (StringTag)listTag.get(0);
                        stringTag.setValue(Protocol1_9To1_8.fixJson(stringTag.getValue()).toString());
                    }
                    int n = 0;
                    ++n;
                }
                item.setTag(tag3);
            }
        }
    }
    
    public static String potionNameFromDamage(final short n) {
        final String s = ItemRewriter.POTION_ID_TO_NAME.get((int)n);
        if (s != null) {
            return s;
        }
        if (n == 0) {
            return "water";
        }
        final int n2 = n & 0xF;
        final int n3 = n & 0x3F;
        final boolean b = (n & 0x20) > 0;
        final boolean b2 = (n & 0x40) > 0;
        String s2 = null;
        Label_0293: {
            switch (n2) {
                case 1: {
                    s2 = "regeneration";
                    break;
                }
                case 2: {
                    s2 = "swiftness";
                    break;
                }
                case 3: {
                    s2 = "fire_resistance";
                    break;
                }
                case 4: {
                    s2 = "poison";
                    break;
                }
                case 5: {
                    s2 = "healing";
                    break;
                }
                case 6: {
                    s2 = "night_vision";
                    break;
                }
                case 8: {
                    s2 = "weakness";
                    break;
                }
                case 9: {
                    s2 = "strength";
                    break;
                }
                case 10: {
                    s2 = "slowness";
                    break;
                }
                case 11: {
                    s2 = "leaping";
                    break;
                }
                case 12: {
                    s2 = "harming";
                    break;
                }
                case 13: {
                    s2 = "water_breathing";
                    break;
                }
                case 14: {
                    s2 = "invisibility";
                    break;
                }
                default: {
                    switch (n3) {
                        case 0: {
                            s2 = "mundane";
                            break Label_0293;
                        }
                        case 16: {
                            s2 = "awkward";
                            break Label_0293;
                        }
                        case 32: {
                            s2 = "thick";
                            break Label_0293;
                        }
                        default: {
                            s2 = "empty";
                            break Label_0293;
                        }
                    }
                    break;
                }
            }
        }
        if (n2 > 0) {}
        return s2;
    }
    
    public static int getNewEffectID(int intValue) {
        if (intValue >= 16384) {
            intValue -= 8192;
        }
        final int value = ItemRewriter.POTION_INDEX.get(intValue);
        if (value != -1) {
            return value;
        }
        intValue = ItemRewriter.POTION_NAME_TO_ID.get(potionNameFromDamage((short)intValue));
        final int value2;
        return ((value2 = ItemRewriter.POTION_INDEX.get(intValue)) != -1) ? value2 : false;
    }
    
    private static void registerEntity(final int n, final String s) {
        ItemRewriter.ENTTIY_ID_TO_NAME.put(n, s);
        ItemRewriter.ENTTIY_NAME_TO_ID.put(s, n);
    }
    
    private static void registerPotion(final int n, final String s) {
        ItemRewriter.POTION_INDEX.put(n, ItemRewriter.POTION_ID_TO_NAME.size());
        ItemRewriter.POTION_ID_TO_NAME.put(n, s);
        ItemRewriter.POTION_NAME_TO_ID.put(s, n);
    }
    
    static {
        ENTTIY_NAME_TO_ID = new HashMap();
        ENTTIY_ID_TO_NAME = new HashMap();
        POTION_NAME_TO_ID = new HashMap();
        POTION_ID_TO_NAME = new HashMap();
        POTION_INDEX = new Int2IntOpenHashMap(36, 0.99f);
        registerEntity(1, "Item");
        registerEntity(2, "XPOrb");
        registerEntity(7, "ThrownEgg");
        registerEntity(8, "LeashKnot");
        registerEntity(9, "Painting");
        registerEntity(10, "Arrow");
        registerEntity(11, "Snowball");
        registerEntity(12, "Fireball");
        registerEntity(13, "SmallFireball");
        registerEntity(14, "ThrownEnderpearl");
        registerEntity(15, "EyeOfEnderSignal");
        registerEntity(16, "ThrownPotion");
        registerEntity(17, "ThrownExpBottle");
        registerEntity(18, "ItemFrame");
        registerEntity(19, "WitherSkull");
        registerEntity(20, "PrimedTnt");
        registerEntity(21, "FallingSand");
        registerEntity(22, "FireworksRocketEntity");
        registerEntity(30, "ArmorStand");
        registerEntity(40, "MinecartCommandBlock");
        registerEntity(41, "Boat");
        registerEntity(42, "MinecartRideable");
        registerEntity(43, "MinecartChest");
        registerEntity(44, "MinecartFurnace");
        registerEntity(45, "MinecartTNT");
        registerEntity(46, "MinecartHopper");
        registerEntity(47, "MinecartSpawner");
        registerEntity(48, "Mob");
        registerEntity(49, "Monster");
        registerEntity(50, "Creeper");
        registerEntity(51, "Skeleton");
        registerEntity(52, "Spider");
        registerEntity(53, "Giant");
        registerEntity(54, "Zombie");
        registerEntity(55, "Slime");
        registerEntity(56, "Ghast");
        registerEntity(57, "PigZombie");
        registerEntity(58, "Enderman");
        registerEntity(59, "CaveSpider");
        registerEntity(60, "Silverfish");
        registerEntity(61, "Blaze");
        registerEntity(62, "LavaSlime");
        registerEntity(63, "EnderDragon");
        registerEntity(64, "WitherBoss");
        registerEntity(65, "Bat");
        registerEntity(66, "Witch");
        registerEntity(67, "Endermite");
        registerEntity(68, "Guardian");
        registerEntity(90, "Pig");
        registerEntity(91, "Sheep");
        registerEntity(92, "Cow");
        registerEntity(93, "Chicken");
        registerEntity(94, "Squid");
        registerEntity(95, "Wolf");
        registerEntity(96, "MushroomCow");
        registerEntity(97, "SnowMan");
        registerEntity(98, "Ozelot");
        registerEntity(99, "VillagerGolem");
        registerEntity(100, "EntityHorse");
        registerEntity(101, "Rabbit");
        registerEntity(120, "Villager");
        registerEntity(200, "EnderCrystal");
        registerPotion(-1, "empty");
        registerPotion(0, "water");
        registerPotion(64, "mundane");
        registerPotion(32, "thick");
        registerPotion(16, "awkward");
        registerPotion(8198, "night_vision");
        registerPotion(8262, "long_night_vision");
        registerPotion(8206, "invisibility");
        registerPotion(8270, "long_invisibility");
        registerPotion(8203, "leaping");
        registerPotion(8267, "long_leaping");
        registerPotion(8235, "strong_leaping");
        registerPotion(8195, "fire_resistance");
        registerPotion(8259, "long_fire_resistance");
        registerPotion(8194, "swiftness");
        registerPotion(8258, "long_swiftness");
        registerPotion(8226, "strong_swiftness");
        registerPotion(8202, "slowness");
        registerPotion(8266, "long_slowness");
        registerPotion(8205, "water_breathing");
        registerPotion(8269, "long_water_breathing");
        registerPotion(8261, "healing");
        registerPotion(8229, "strong_healing");
        registerPotion(8204, "harming");
        registerPotion(8236, "strong_harming");
        registerPotion(8196, "poison");
        registerPotion(8260, "long_poison");
        registerPotion(8228, "strong_poison");
        registerPotion(8193, "regeneration");
        registerPotion(8257, "long_regeneration");
        registerPotion(8225, "strong_regeneration");
        registerPotion(8201, "strength");
        registerPotion(8265, "long_strength");
        registerPotion(8233, "strong_strength");
        registerPotion(8200, "weakness");
        registerPotion(8264, "long_weakness");
    }
}
