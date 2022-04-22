package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public abstract class ItemRewriterBase extends ItemRewriter
{
    protected final String nbtTagName;
    protected final boolean jsonNameFormat;
    
    protected ItemRewriterBase(final BackwardsProtocol backwardsProtocol, final boolean jsonNameFormat) {
        super(backwardsProtocol);
        this.jsonNameFormat = jsonNameFormat;
        this.nbtTagName = "VB|" + backwardsProtocol.getClass().getSimpleName();
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        this.restoreDisplayTag(item);
        return item;
    }
    
    protected boolean hasBackupTag(final CompoundTag compoundTag, final String s) {
        return compoundTag.contains(this.nbtTagName + "|o" + s);
    }
    
    protected void saveStringTag(final CompoundTag compoundTag, final StringTag stringTag, final String s) {
        final String string = this.nbtTagName + "|o" + s;
        if (!compoundTag.contains(string)) {
            compoundTag.put(string, new StringTag(stringTag.getValue()));
        }
    }
    
    protected void saveListTag(final CompoundTag compoundTag, final ListTag listTag, final String s) {
        final String string = this.nbtTagName + "|o" + s;
        if (!compoundTag.contains(string)) {
            final ListTag listTag2 = new ListTag();
            final Iterator<Tag> iterator = listTag.getValue().iterator();
            while (iterator.hasNext()) {
                listTag2.add(iterator.next().clone());
            }
            compoundTag.put(string, listTag2);
        }
    }
    
    protected void restoreDisplayTag(final Item item) {
        if (item.tag() == null) {
            return;
        }
        final CompoundTag compoundTag = (CompoundTag)item.tag().get("display");
        if (compoundTag != null) {
            if (compoundTag.remove(this.nbtTagName + "|customName") != null) {
                compoundTag.remove("Name");
            }
            else {
                this.restoreStringTag(compoundTag, "Name");
            }
            this.restoreListTag(compoundTag, "Lore");
        }
    }
    
    protected void restoreStringTag(final CompoundTag compoundTag, final String s) {
        final StringTag stringTag = (StringTag)compoundTag.remove(this.nbtTagName + "|o" + s);
        if (stringTag != null) {
            compoundTag.put(s, new StringTag(stringTag.getValue()));
        }
    }
    
    protected void restoreListTag(final CompoundTag compoundTag, final String s) {
        final ListTag listTag = (ListTag)compoundTag.remove(this.nbtTagName + "|o" + s);
        if (listTag != null) {
            compoundTag.put(s, new ListTag(listTag.getValue()));
        }
    }
    
    public String getNbtTagName() {
        return this.nbtTagName;
    }
}
