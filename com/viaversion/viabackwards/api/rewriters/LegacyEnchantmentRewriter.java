package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class LegacyEnchantmentRewriter
{
    private final Map enchantmentMappings;
    private final String nbtTagName;
    private Set hideLevelForEnchants;
    
    public LegacyEnchantmentRewriter(final String nbtTagName) {
        this.enchantmentMappings = new HashMap();
        this.nbtTagName = nbtTagName;
    }
    
    public void registerEnchantment(final int n, final String s) {
        this.enchantmentMappings.put((short)n, s);
    }
    
    public void rewriteEnchantmentsToClient(final CompoundTag compoundTag, final boolean b) {
        final String s = b ? "StoredEnchantments" : "ench";
        final ListTag listTag = (ListTag)compoundTag.get(s);
        final ListTag listTag2 = new ListTag(CompoundTag.class);
        final ArrayList<StringTag> value = new ArrayList<StringTag>();
        for (final Tag tag : listTag.clone()) {
            final Tag value2 = ((CompoundTag)tag).get("id");
            if (value2 == null) {
                continue;
            }
            final short short1 = ((NumberTag)value2).asShort();
            final String s2 = this.enchantmentMappings.get(short1);
            if (s2 == null) {
                continue;
            }
            listTag.remove(tag);
            final short short2 = ((NumberTag)((CompoundTag)tag).get("lvl")).asShort();
            if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(short1)) {
                value.add(new StringTag(s2));
            }
            else {
                value.add(new StringTag(s2 + " " + EnchantmentRewriter.getRomanNumber(short2)));
            }
            listTag2.add(tag);
        }
        if (!value.isEmpty()) {
            if (!b && listTag.size() == 0) {
                final CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.put("id", new ShortTag((short)0));
                compoundTag2.put("lvl", new ShortTag((short)0));
                listTag.add(compoundTag2);
                compoundTag.put(this.nbtTagName + "|dummyEnchant", new ByteTag());
                IntTag intTag = (IntTag)compoundTag.get("HideFlags");
                if (intTag == null) {
                    intTag = new IntTag();
                }
                else {
                    compoundTag.put(this.nbtTagName + "|oldHideFlags", new IntTag(intTag.asByte()));
                }
                intTag.setValue(intTag.asByte() | 0x1);
                compoundTag.put("HideFlags", intTag);
            }
            compoundTag.put(this.nbtTagName + "|" + s, listTag2);
            CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("display");
            if (compoundTag3 == null) {
                compoundTag.put("display", compoundTag3 = new CompoundTag());
            }
            ListTag listTag3 = (ListTag)compoundTag3.get("Lore");
            if (listTag3 == null) {
                compoundTag3.put("Lore", listTag3 = new ListTag(StringTag.class));
            }
            value.addAll((Collection<?>)listTag3.getValue());
            listTag3.setValue(value);
        }
    }
    
    public void rewriteEnchantmentsToServer(final CompoundTag compoundTag, final boolean b) {
        final String s = b ? "StoredEnchantments" : "ench";
        final ListTag listTag = (ListTag)compoundTag.remove(this.nbtTagName + "|" + s);
        ListTag listTag2 = (ListTag)compoundTag.get(s);
        if (listTag2 == null) {
            listTag2 = new ListTag(CompoundTag.class);
        }
        if (!b && compoundTag.remove(this.nbtTagName + "|dummyEnchant") != null) {
            for (final Tag tag : listTag2.clone()) {
                final short short1 = ((NumberTag)((CompoundTag)tag).get("id")).asShort();
                final short short2 = ((NumberTag)((CompoundTag)tag).get("lvl")).asShort();
                if (short1 == 0 && short2 == 0) {
                    listTag2.remove(tag);
                }
            }
            final IntTag intTag = (IntTag)compoundTag.remove(this.nbtTagName + "|oldHideFlags");
            if (intTag != null) {
                compoundTag.put("HideFlags", new IntTag(intTag.asByte()));
            }
            else {
                compoundTag.remove("HideFlags");
            }
        }
        final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("display");
        final ListTag listTag3 = (compoundTag2 != null) ? ((ListTag)compoundTag2.get("Lore")) : null;
        final Iterator iterator2 = listTag.clone().iterator();
        while (iterator2.hasNext()) {
            listTag2.add(iterator2.next());
            if (listTag3 != null && listTag3.size() != 0) {
                listTag3.remove(listTag3.get(0));
            }
        }
        if (listTag3 != null && listTag3.size() == 0) {
            compoundTag2.remove("Lore");
            if (compoundTag2.isEmpty()) {
                compoundTag.remove("display");
            }
        }
        compoundTag.put(s, listTag2);
    }
    
    public void setHideLevelForEnchants(final int... array) {
        this.hideLevelForEnchants = new HashSet();
        while (0 < array.length) {
            this.hideLevelForEnchants.add((short)array[0]);
            int n = 0;
            ++n;
        }
    }
}
