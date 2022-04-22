package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;

import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;

public class ItemRewriter
{
    public static Item toClient(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag == null) {
            item.setTag(tag = new CompoundTag());
        }
        final CompoundTag compoundTag = new CompoundTag();
        tag.put("ViaRewind1_7_6_10to1_8", compoundTag);
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
                if (short1 == 8) {
                    final String s = "§r§7Depth Strider ";
                    listTag.remove(tag2);
                    value.add(new StringTag(s + Enchantments.ENCHANTMENTS.getOrDefault(short2, "enchantment.level." + short2)));
                }
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
        if (item.identifier() == 387 && tag.contains("pages")) {
            final ListTag listTag3 = (ListTag)tag.get("pages");
            final ListTag listTag4 = new ListTag(StringTag.class);
            compoundTag.put("pages", listTag4);
            while (0 < listTag3.size()) {
                final StringTag stringTag = (StringTag)listTag3.get(0);
                final String value2 = stringTag.getValue();
                listTag4.add(new StringTag(value2));
                stringTag.setValue(ChatUtil.jsonToLegacy(value2));
                int n = 0;
                ++n;
            }
        }
        ReplacementRegistry1_7_6_10to1_8.replace(item);
        if (compoundTag.size() == 2 && (short)compoundTag.get("id").getValue() == item.identifier() && (short)compoundTag.get("data").getValue() == item.data()) {
            item.tag().remove("ViaRewind1_7_6_10to1_8");
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
        final CompoundTag tag = item.tag();
        if (tag == null || !item.tag().contains("ViaRewind1_7_6_10to1_8")) {
            return item;
        }
        final CompoundTag compoundTag = (CompoundTag)tag.remove("ViaRewind1_7_6_10to1_8");
        item.setIdentifier((short)compoundTag.get("id").getValue());
        item.setData((short)compoundTag.get("data").getValue());
        if (compoundTag.contains("noDisplay")) {
            tag.remove("display");
        }
        if (compoundTag.contains("displayName")) {
            CompoundTag compoundTag2 = (CompoundTag)tag.get("display");
            if (compoundTag2 == null) {
                tag.put("display", compoundTag2 = new CompoundTag());
            }
            final StringTag stringTag = (StringTag)compoundTag2.get("Name");
            if (stringTag == null) {
                compoundTag2.put("Name", new StringTag((String)compoundTag.get("displayName").getValue()));
            }
            else {
                stringTag.setValue((String)compoundTag.get("displayName").getValue());
            }
        }
        else if (tag.contains("display")) {
            ((CompoundTag)tag.get("display")).remove("Name");
        }
        if (item.identifier() == 387) {
            final ListTag listTag = (ListTag)compoundTag.get("pages");
            tag.remove("pages");
            tag.put("pages", listTag);
        }
        return item;
    }
}
