package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.flare.fastutil.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.google.common.base.*;

public class EntityTrackerBase implements EntityTracker, ClientEntityIdChangeListener
{
    private final Int2ObjectMap entityTypes;
    private final Int2ObjectMap entityData;
    private final UserConnection connection;
    private final EntityType playerType;
    private int clientEntityId;
    private int currentWorldSectionHeight;
    private int currentMinY;
    private String currentWorld;
    private int biomesSent;
    
    public EntityTrackerBase(final UserConnection userConnection, final EntityType entityType) {
        this(userConnection, entityType, false);
    }
    
    public EntityTrackerBase(final UserConnection connection, final EntityType playerType, final boolean b) {
        this.entityTypes = Int2ObjectSyncMap.hashmap();
        this.clientEntityId = -1;
        this.currentWorldSectionHeight = 16;
        this.biomesSent = -1;
        this.connection = connection;
        this.playerType = playerType;
        this.entityData = (b ? Int2ObjectSyncMap.hashmap() : null);
    }
    
    @Override
    public UserConnection user() {
        return this.connection;
    }
    
    @Override
    public void addEntity(final int n, final EntityType entityType) {
        this.entityTypes.put(n, entityType);
    }
    
    @Override
    public boolean hasEntity(final int n) {
        return this.entityTypes.containsKey(n);
    }
    
    @Override
    public EntityType entityType(final int n) {
        return (EntityType)this.entityTypes.get(n);
    }
    
    @Override
    public StoredEntityData entityData(final int n) {
        Preconditions.checkArgument(this.entityData != null, (Object)"Entity data storage has to be explicitly enabled via the constructor");
        final EntityType entityType = this.entityType(n);
        return (entityType != null) ? ((StoredEntityData)this.entityData.computeIfAbsent(n, EntityTrackerBase::lambda$entityData$0)) : null;
    }
    
    @Override
    public StoredEntityData entityDataIfPresent(final int n) {
        Preconditions.checkArgument(this.entityData != null, (Object)"Entity data storage has to be explicitly enabled via the constructor");
        return (StoredEntityData)this.entityData.get(n);
    }
    
    @Override
    public void removeEntity(final int n) {
        this.entityTypes.remove(n);
        if (this.entityData != null) {
            this.entityData.remove(n);
        }
    }
    
    @Override
    public void clearEntities() {
        this.entityTypes.clear();
        if (this.entityData != null) {
            this.entityData.clear();
        }
    }
    
    @Override
    public int clientEntityId() {
        return this.clientEntityId;
    }
    
    @Override
    public void setClientEntityId(final int clientEntityId) {
        Preconditions.checkNotNull(this.playerType);
        this.entityTypes.put(clientEntityId, this.playerType);
        if (this.clientEntityId != -1 && this.entityData != null) {
            final StoredEntityData storedEntityData = (StoredEntityData)this.entityData.remove(this.clientEntityId);
            if (storedEntityData != null) {
                this.entityData.put(clientEntityId, storedEntityData);
            }
        }
        this.clientEntityId = clientEntityId;
    }
    
    @Override
    public int currentWorldSectionHeight() {
        return this.currentWorldSectionHeight;
    }
    
    @Override
    public void setCurrentWorldSectionHeight(final int currentWorldSectionHeight) {
        this.currentWorldSectionHeight = currentWorldSectionHeight;
    }
    
    @Override
    public int currentMinY() {
        return this.currentMinY;
    }
    
    @Override
    public void setCurrentMinY(final int currentMinY) {
        this.currentMinY = currentMinY;
    }
    
    @Override
    public String currentWorld() {
        return this.currentWorld;
    }
    
    @Override
    public void setCurrentWorld(final String currentWorld) {
        this.currentWorld = currentWorld;
    }
    
    @Override
    public int biomesSent() {
        return this.biomesSent;
    }
    
    @Override
    public void setBiomesSent(final int biomesSent) {
        this.biomesSent = biomesSent;
    }
    
    private static StoredEntityData lambda$entityData$0(final EntityType entityType, final int n) {
        return new StoredEntityImpl(entityType);
    }
}
