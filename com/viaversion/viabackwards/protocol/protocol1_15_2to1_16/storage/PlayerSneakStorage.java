package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage;

import com.viaversion.viaversion.api.connection.*;

public class PlayerSneakStorage implements StorableObject
{
    private boolean sneaking;
    
    public boolean isSneaking() {
        return this.sneaking;
    }
    
    public void setSneaking(final boolean sneaking) {
        this.sneaking = sneaking;
    }
}
