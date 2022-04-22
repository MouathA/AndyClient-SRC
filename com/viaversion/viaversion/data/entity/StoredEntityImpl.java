package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.concurrent.*;

public final class StoredEntityImpl implements StoredEntityData
{
    private final Map storedObjects;
    private final EntityType type;
    
    public StoredEntityImpl(final EntityType type) {
        this.storedObjects = new ConcurrentHashMap();
        this.type = type;
    }
    
    @Override
    public EntityType type() {
        return this.type;
    }
    
    @Override
    public Object get(final Class clazz) {
        return this.storedObjects.get(clazz);
    }
    
    @Override
    public boolean has(final Class clazz) {
        return this.storedObjects.containsKey(clazz);
    }
    
    @Override
    public void put(final Object o) {
        this.storedObjects.put(o.getClass(), o);
    }
}
