package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public final class PingRequests implements StorableObject
{
    private final IntSet ids;
    
    public PingRequests() {
        this.ids = new IntOpenHashSet();
    }
    
    public void addId(final short n) {
        this.ids.add(n);
    }
    
    public boolean removeId(final short n) {
        return this.ids.remove(n);
    }
}
