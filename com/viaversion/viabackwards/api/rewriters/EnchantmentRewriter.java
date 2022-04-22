package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class EnchantmentRewriter
{
    private final Map enchantmentMappings;
    private final ItemRewriter itemRewriter;
    private final boolean jsonFormat;
    
    public EnchantmentRewriter(final ItemRewriter itemRewriter, final boolean jsonFormat) {
        this.enchantmentMappings = new HashMap();
        this.itemRewriter = itemRewriter;
        this.jsonFormat = jsonFormat;
    }
    
    public EnchantmentRewriter(final ItemRewriter itemRewriter) {
        this(itemRewriter, true);
    }
    
    public void registerEnchantment(final String s, final String s2) {
        this.enchantmentMappings.put(s, s2);
    }
    
    public void handleToClient(final Item item) {
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return;
        }
        if (tag.get("Enchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.get("StoredEnchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(tag, true);
        }
    }
    
    public void handleToServer(final Item item) {
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return;
        }
        if (tag.contains(this.itemRewriter.getNbtTagName() + "|Enchantments")) {
            this.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.itemRewriter.getNbtTagName() + "|StoredEnchantments")) {
            this.rewriteEnchantmentsToServer(tag, true);
        }
    }
    
    public void rewriteEnchantmentsToClient(final CompoundTag compoundTag, final boolean b) {
        final String s = b ? "StoredEnchantments" : "Enchantments";
        final ListTag listTag = (ListTag)compoundTag.get(s);
        final ArrayList<StringTag> value = new ArrayList<StringTag>();
        final Iterator iterator = listTag.iterator();
        while (iterator.hasNext()) {
            final CompoundTag compoundTag2 = iterator.next();
            final Tag value2 = compoundTag2.get("id");
            if (!(value2 instanceof StringTag)) {
                continue;
            }
            final String s2 = this.enchantmentMappings.get(((StringTag)value2).getValue());
            if (s2 == null) {
                continue;
            }
            if (!true) {
                this.itemRewriter.saveListTag(compoundTag, listTag, s);
            }
            iterator.remove();
            String s3 = s2 + " " + getRomanNumber(((NumberTag)compoundTag2.get("lvl")).asInt());
            if (this.jsonFormat) {
                s3 = ChatRewriter.legacyTextToJsonString(s3);
            }
            value.add(new StringTag(s3));
        }
        if (!value.isEmpty()) {
            if (!b && listTag.size() == 0) {
                final CompoundTag compoundTag3 = new CompoundTag();
                compoundTag3.put("id", new StringTag());
                compoundTag3.put("lvl", new ShortTag((short)0));
                listTag.add(compoundTag3);
            }
            CompoundTag compoundTag4 = (CompoundTag)compoundTag.get("display");
            if (compoundTag4 == null) {
                compoundTag.put("display", compoundTag4 = new CompoundTag());
            }
            ListTag listTag2 = (ListTag)compoundTag4.get("Lore");
            if (listTag2 == null) {
                compoundTag4.put("Lore", listTag2 = new ListTag(StringTag.class));
            }
            else {
                this.itemRewriter.saveListTag(compoundTag4, listTag2, "Lore");
            }
            value.addAll((Collection<?>)listTag2.getValue());
            listTag2.setValue(value);
        }
    }
    
    public void rewriteEnchantmentsToServer(final CompoundTag compoundTag, final boolean b) {
        this.itemRewriter.restoreListTag(compoundTag, b ? "StoredEnchantments" : "Enchantments");
    }
    
    public static String getRomanNumber(final int n) {
        switch (n) {
            case 1: {
                return "I";
            }
            case 2: {
                return "II";
            }
            case 3: {
                return "III";
            }
            case 4: {
                return "IV";
            }
            case 5: {
                return "V";
            }
            case 6: {
                return "VI";
            }
            case 7: {
                return "VII";
            }
            case 8: {
                return "VIII";
            }
            case 9: {
                return "IX";
            }
            case 10: {
                return "X";
            }
            default: {
                return Integer.toString(n);
            }
        }
    }
}
