package de.gerrygames.viarewind.protocol.protocol1_8to1_9.items;

import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import java.util.*;
import java.lang.reflect.*;

public class ItemRewriter
{
    private static Map ENTTIY_NAME_TO_ID;
    private static Map ENTTIY_ID_TO_NAME;
    private static Map POTION_NAME_TO_ID;
    private static Map POTION_ID_TO_NAME;
    private static Map POTION_NAME_INDEX;
    
    public static Item toClient(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag == null) {
            item.setTag(tag = new CompoundTag());
        }
        final CompoundTag compoundTag = new CompoundTag();
        tag.put("ViaRewind1_8to1_9", compoundTag);
        compoundTag.put("id", new ShortTag((short)item.identifier()));
        compoundTag.put("data", new ShortTag(item.data()));
        CompoundTag compoundTag2 = (CompoundTag)tag.get("display");
        if (compoundTag2 != null && compoundTag2.contains("Name")) {
            compoundTag.put("displayName", new StringTag((String)compoundTag2.get("Name").getValue()));
        }
        if (compoundTag2 != null && compoundTag2.contains("Lore")) {
            compoundTag.put("lore", new ListTag(((ListTag)compoundTag2.get("Lore")).getValue()));
        }
        if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
            final ListTag listTag = (ListTag)(tag.contains("ench") ? tag.get("ench") : ((ListTag)tag.get("StoredEnchantments")));
            final ArrayList<StringTag> value = new ArrayList<StringTag>();
            for (final Tag tag2 : new ArrayList<Tag>(listTag.getValue())) {
                final short short1 = ((NumberTag)((CompoundTag)tag2).get("id")).asShort();
                final short short2 = ((NumberTag)((CompoundTag)tag2).get("lvl")).asShort();
                String s;
                if (short1 == 70) {
                    s = "§r§7Mending ";
                }
                else {
                    if (short1 != 9) {
                        continue;
                    }
                    s = "§r§7Frost Walker ";
                }
                listTag.remove(tag2);
                value.add(new StringTag(s + Enchantments.ENCHANTMENTS.getOrDefault(short2, "enchantment.level." + short2)));
            }
            if (!value.isEmpty()) {
                if (compoundTag2 == null) {
                    tag.put("display", compoundTag2 = new CompoundTag());
                    compoundTag.put("noDisplay", new ByteTag());
                }
                ListTag listTag2 = (ListTag)compoundTag2.get("Lore");
                if (listTag2 == null) {
                    compoundTag2.put("Lore", listTag2 = new ListTag(StringTag.class));
                }
                value.addAll((Collection<?>)listTag2.getValue());
                listTag2.setValue(value);
            }
        }
        if (item.data() != 0 && tag.contains("Unbreakable")) {
            final ByteTag byteTag = (ByteTag)tag.get("Unbreakable");
            if (byteTag.asByte() != 0) {
                compoundTag.put("Unbreakable", new ByteTag(byteTag.asByte()));
                tag.remove("Unbreakable");
                if (compoundTag2 == null) {
                    tag.put("display", compoundTag2 = new CompoundTag());
                    compoundTag.put("noDisplay", new ByteTag());
                }
                ListTag listTag3 = (ListTag)compoundTag2.get("Lore");
                if (listTag3 == null) {
                    compoundTag2.put("Lore", listTag3 = new ListTag(StringTag.class));
                }
                listTag3.add(new StringTag("§9Unbreakable"));
            }
        }
        if (tag.contains("AttributeModifiers")) {
            compoundTag.put("AttributeModifiers", tag.get("AttributeModifiers").clone());
        }
        int n = 0;
        if (item.identifier() == 383 && item.data() == 0) {
            if (tag.contains("EntityTag")) {
                final CompoundTag compoundTag3 = (CompoundTag)tag.get("EntityTag");
                if (compoundTag3.contains("id")) {
                    final StringTag stringTag = (StringTag)compoundTag3.get("id");
                    if (ItemRewriter.ENTTIY_NAME_TO_ID.containsKey(stringTag.getValue())) {
                        n = ItemRewriter.ENTTIY_NAME_TO_ID.get(stringTag.getValue());
                    }
                    else if (compoundTag2 == null) {
                        tag.put("display", compoundTag2 = new CompoundTag());
                        compoundTag.put("noDisplay", new ByteTag());
                        compoundTag2.put("Name", new StringTag("§rSpawn " + stringTag.getValue()));
                    }
                }
            }
            item.setData((short)0);
        }
        ReplacementRegistry1_8to1_9.replace(item);
        if (item.identifier() == 373 || item.identifier() == 438 || item.identifier() == 441) {
            if (tag.contains("Potion")) {
                String s2 = ((StringTag)tag.get("Potion")).getValue().replace("minecraft:", "");
                if (ItemRewriter.POTION_NAME_TO_ID.containsKey(s2)) {
                    n = (int)ItemRewriter.POTION_NAME_TO_ID.get(s2);
                }
                if (item.identifier() == 438) {
                    s2 += "_splash";
                }
                else if (item.identifier() == 441) {
                    s2 += "_lingering";
                }
                if ((compoundTag2 == null || !compoundTag2.contains("Name")) && ItemRewriter.POTION_NAME_INDEX.containsKey(s2)) {
                    if (compoundTag2 == null) {
                        tag.put("display", compoundTag2 = new CompoundTag());
                        compoundTag.put("noDisplay", new ByteTag());
                    }
                    compoundTag2.put("Name", new StringTag((String)ItemRewriter.POTION_NAME_INDEX.get(s2)));
                }
            }
            if (item.identifier() == 438 || item.identifier() == 441) {
                item.setIdentifier(373);
                n += 8192;
            }
            item.setData((short)0);
        }
        if (tag.contains("AttributeModifiers")) {
            final ListTag listTag4 = (ListTag)tag.get("AttributeModifiers");
            while (0 < listTag4.size()) {
                final CompoundTag compoundTag4 = (CompoundTag)listTag4.get(0);
                final String s3 = (String)compoundTag4.get("AttributeName").getValue();
                int n2 = 0;
                if (!Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(compoundTag4)) {
                    listTag4.remove(compoundTag4);
                    --n2;
                }
                ++n2;
            }
        }
        if (compoundTag.size() == 2 && (short)compoundTag.get("id").getValue() == item.identifier() && (short)compoundTag.get("data").getValue() == item.data()) {
            item.tag().remove("ViaRewind1_8to1_9");
            if (item.tag().isEmpty()) {
                item.setTag(null);
            }
        }
        return item;
    }
    
    public static Item toServer(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (item.identifier() == 383 && item.data() != 0) {
            if (tag == null) {
                item.setTag(tag = new CompoundTag());
            }
            if (!tag.contains("EntityTag") && ItemRewriter.ENTTIY_ID_TO_NAME.containsKey((int)item.data())) {
                final CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("id", new StringTag(ItemRewriter.ENTTIY_ID_TO_NAME.get((int)item.data())));
                tag.put("EntityTag", compoundTag);
            }
            item.setData((short)0);
        }
        if (item.identifier() == 373 && (tag == null || !tag.contains("Potion"))) {
            if (tag == null) {
                item.setTag(tag = new CompoundTag());
            }
            if (item.data() >= 16384) {
                item.setIdentifier(438);
                item.setData((short)(item.data() - 8192));
            }
            tag.put("Potion", new StringTag("minecraft:" + ((item.data() == 8192) ? "water" : com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.potionNameFromDamage(item.data()))));
            item.setData((short)0);
        }
        if (tag == null || !item.tag().contains("ViaRewind1_8to1_9")) {
            return item;
        }
        final CompoundTag compoundTag2 = (CompoundTag)tag.remove("ViaRewind1_8to1_9");
        item.setIdentifier((short)compoundTag2.get("id").getValue());
        item.setData((short)compoundTag2.get("data").getValue());
        if (compoundTag2.contains("noDisplay")) {
            tag.remove("display");
        }
        if (compoundTag2.contains("Unbreakable")) {
            tag.put("Unbreakable", compoundTag2.get("Unbreakable").clone());
        }
        if (compoundTag2.contains("displayName")) {
            CompoundTag compoundTag3 = (CompoundTag)tag.get("display");
            if (compoundTag3 == null) {
                tag.put("display", compoundTag3 = new CompoundTag());
            }
            final StringTag stringTag = (StringTag)compoundTag3.get("Name");
            if (stringTag == null) {
                compoundTag3.put("Name", new StringTag((String)compoundTag2.get("displayName").getValue()));
            }
            else {
                stringTag.setValue((String)compoundTag2.get("displayName").getValue());
            }
        }
        else if (tag.contains("display")) {
            ((CompoundTag)tag.get("display")).remove("Name");
        }
        if (compoundTag2.contains("lore")) {
            CompoundTag compoundTag4 = (CompoundTag)tag.get("display");
            if (compoundTag4 == null) {
                tag.put("display", compoundTag4 = new CompoundTag());
            }
            final ListTag listTag = (ListTag)compoundTag4.get("Lore");
            if (listTag == null) {
                compoundTag4.put("Lore", new ListTag((List)compoundTag2.get("lore").getValue()));
            }
            else {
                listTag.setValue((List)compoundTag2.get("lore").getValue());
            }
        }
        else if (tag.contains("display")) {
            ((CompoundTag)tag.get("display")).remove("Lore");
        }
        tag.remove("AttributeModifiers");
        if (compoundTag2.contains("AttributeModifiers")) {
            tag.put("AttributeModifiers", compoundTag2.get("AttributeModifiers"));
        }
        return item;
    }
    
    static {
        ItemRewriter.POTION_NAME_INDEX = new HashMap();
        final Field[] declaredFields = ItemRewriter.class.getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            final Field declaredField = com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.class.getDeclaredField(field.getName());
            declaredField.setAccessible(true);
            field.setAccessible(true);
            field.set(null, declaredField.get(null));
            int n = 0;
            ++n;
        }
        ItemRewriter.POTION_NAME_TO_ID.put("luck", 8203);
        ItemRewriter.POTION_NAME_INDEX.put("water", "§rWater Bottle");
        ItemRewriter.POTION_NAME_INDEX.put("mundane", "§rMundane Potion");
        ItemRewriter.POTION_NAME_INDEX.put("thick", "§rThick Potion");
        ItemRewriter.POTION_NAME_INDEX.put("awkward", "§rAwkward Potion");
        ItemRewriter.POTION_NAME_INDEX.put("water_splash", "§rSplash Water Bottle");
        ItemRewriter.POTION_NAME_INDEX.put("mundane_splash", "§rMundane Splash Potion");
        ItemRewriter.POTION_NAME_INDEX.put("thick_splash", "§rThick Splash Potion");
        ItemRewriter.POTION_NAME_INDEX.put("awkward_splash", "§rAwkward Splash Potion");
        ItemRewriter.POTION_NAME_INDEX.put("water_lingering", "§rLingering Water Bottle");
        ItemRewriter.POTION_NAME_INDEX.put("mundane_lingering", "§rMundane Lingering Potion");
        ItemRewriter.POTION_NAME_INDEX.put("thick_lingering", "§rThick Lingering Potion");
        ItemRewriter.POTION_NAME_INDEX.put("awkward_lingering", "§rAwkward Lingering Potion");
        ItemRewriter.POTION_NAME_INDEX.put("night_vision_lingering", "§rLingering Potion of Night Vision");
        ItemRewriter.POTION_NAME_INDEX.put("long_night_vision_lingering", "§rLingering Potion of Night Vision");
        ItemRewriter.POTION_NAME_INDEX.put("invisibility_lingering", "§rLingering Potion of Invisibility");
        ItemRewriter.POTION_NAME_INDEX.put("long_invisibility_lingering", "§rLingering Potion of Invisibility");
        ItemRewriter.POTION_NAME_INDEX.put("leaping_lingering", "§rLingering Potion of Leaping");
        ItemRewriter.POTION_NAME_INDEX.put("long_leaping_lingering", "§rLingering Potion of Leaping");
        ItemRewriter.POTION_NAME_INDEX.put("strong_leaping_lingering", "§rLingering Potion of Leaping");
        ItemRewriter.POTION_NAME_INDEX.put("fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
        ItemRewriter.POTION_NAME_INDEX.put("long_fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
        ItemRewriter.POTION_NAME_INDEX.put("swiftness_lingering", "§rLingering Potion of Swiftness");
        ItemRewriter.POTION_NAME_INDEX.put("long_swiftness_lingering", "§rLingering Potion of Swiftness");
        ItemRewriter.POTION_NAME_INDEX.put("strong_swiftness_lingering", "§rLingering Potion of Swiftness");
        ItemRewriter.POTION_NAME_INDEX.put("slowness_lingering", "§rLingering Potion of Slowness");
        ItemRewriter.POTION_NAME_INDEX.put("long_slowness_lingering", "§rLingering Potion of Slowness");
        ItemRewriter.POTION_NAME_INDEX.put("water_breathing_lingering", "§rLingering Potion of Water Breathing");
        ItemRewriter.POTION_NAME_INDEX.put("long_water_breathing_lingering", "§rLingering Potion of Water Breathing");
        ItemRewriter.POTION_NAME_INDEX.put("healing_lingering", "§rLingering Potion of Healing");
        ItemRewriter.POTION_NAME_INDEX.put("strong_healing_lingering", "§rLingering Potion of Healing");
        ItemRewriter.POTION_NAME_INDEX.put("harming_lingering", "§rLingering Potion of Harming");
        ItemRewriter.POTION_NAME_INDEX.put("strong_harming_lingering", "§rLingering Potion of Harming");
        ItemRewriter.POTION_NAME_INDEX.put("poison_lingering", "§rLingering Potion of Poisen");
        ItemRewriter.POTION_NAME_INDEX.put("long_poison_lingering", "§rLingering Potion of Poisen");
        ItemRewriter.POTION_NAME_INDEX.put("strong_poison_lingering", "§rLingering Potion of Poisen");
        ItemRewriter.POTION_NAME_INDEX.put("regeneration_lingering", "§rLingering Potion of Regeneration");
        ItemRewriter.POTION_NAME_INDEX.put("long_regeneration_lingering", "§rLingering Potion of Regeneration");
        ItemRewriter.POTION_NAME_INDEX.put("strong_regeneration_lingering", "§rLingering Potion of Regeneration");
        ItemRewriter.POTION_NAME_INDEX.put("strength_lingering", "§rLingering Potion of Strength");
        ItemRewriter.POTION_NAME_INDEX.put("long_strength_lingering", "§rLingering Potion of Strength");
        ItemRewriter.POTION_NAME_INDEX.put("strong_strength_lingering", "§rLingering Potion of Strength");
        ItemRewriter.POTION_NAME_INDEX.put("weakness_lingering", "§rLingering Potion of Weakness");
        ItemRewriter.POTION_NAME_INDEX.put("long_weakness_lingering", "§rLingering Potion of Weakness");
        ItemRewriter.POTION_NAME_INDEX.put("luck_lingering", "§rLingering Potion of Luck");
        ItemRewriter.POTION_NAME_INDEX.put("luck", "§rPotion of Luck");
        ItemRewriter.POTION_NAME_INDEX.put("luck_splash", "§rSplash Potion of Luck");
    }
}
