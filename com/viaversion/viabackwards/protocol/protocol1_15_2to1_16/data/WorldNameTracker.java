package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.api.connection.*;

public class WorldNameTracker implements StorableObject
{
    private String worldName;
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }
}
