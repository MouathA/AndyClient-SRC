package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public interface Item
{
    int identifier();
    
    void setIdentifier(final int p0);
    
    int amount();
    
    void setAmount(final int p0);
    
    default short data() {
        return 0;
    }
    
    default void setData(final short n) {
        throw new UnsupportedOperationException();
    }
    
    CompoundTag tag();
    
    void setTag(final CompoundTag p0);
    
    Item copy();
}
