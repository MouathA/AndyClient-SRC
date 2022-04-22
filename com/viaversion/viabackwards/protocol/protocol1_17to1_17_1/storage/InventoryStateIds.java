package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public final class InventoryStateIds implements StorableObject
{
    private final Int2IntMap ids;
    
    public InventoryStateIds() {
        (this.ids = new Int2IntOpenHashMap()).defaultReturnValue(Integer.MAX_VALUE);
    }
    
    public void setStateId(final short n, final int n2) {
        this.ids.put(n, n2);
    }
    
    public int removeStateId(final short n) {
        return this.ids.remove(n);
    }
}
