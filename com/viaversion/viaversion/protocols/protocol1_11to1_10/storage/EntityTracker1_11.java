package com.viaversion.viaversion.protocols.protocol1_11to1_10.storage;

import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.flare.fastutil.*;

public class EntityTracker1_11 extends EntityTrackerBase
{
    private final IntSet holograms;
    
    public EntityTracker1_11(final UserConnection userConnection) {
        super(userConnection, Entity1_11Types.EntityType.PLAYER);
        this.holograms = Int2ObjectSyncMap.hashset();
    }
    
    @Override
    public void removeEntity(final int n) {
        super.removeEntity(n);
        this.removeHologram(n);
    }
    
    public boolean addHologram(final int n) {
        return this.holograms.add(n);
    }
    
    public boolean isHologram(final int n) {
        return this.holograms.contains(n);
    }
    
    public void removeHologram(final int n) {
        this.holograms.remove(n);
    }
}
