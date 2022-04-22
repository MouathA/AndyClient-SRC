package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public final class InventoryAcknowledgements implements StorableObject
{
    private final IntSet ids;
    
    public InventoryAcknowledgements() {
        this.ids = new IntOpenHashSet();
    }
    
    public void addId(final int n) {
        this.ids.add(n);
    }
    
    public boolean removeId(final int n) {
        return this.ids.remove(n);
    }
}
