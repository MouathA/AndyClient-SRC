package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.*;

public class EntityObjectData extends EntityData
{
    private final int objectData;
    
    public EntityObjectData(final BackwardsProtocol backwardsProtocol, final String s, final int n, final int n2, final int objectData) {
        super(backwardsProtocol, s, n, n2);
        this.objectData = objectData;
    }
    
    @Override
    public boolean isObjectType() {
        return true;
    }
    
    @Override
    public int objectData() {
        return this.objectData;
    }
}
