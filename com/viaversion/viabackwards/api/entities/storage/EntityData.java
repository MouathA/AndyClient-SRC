package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class EntityData
{
    private final BackwardsProtocol protocol;
    private final int id;
    private final int replacementId;
    private final String key;
    private NameVisibility nameVisibility;
    private MetaCreator defaultMeta;
    
    public EntityData(final BackwardsProtocol backwardsProtocol, final EntityType entityType, final int n) {
        this(backwardsProtocol, entityType.name(), entityType.getId(), n);
    }
    
    public EntityData(final BackwardsProtocol protocol, final String s, final int id, final int replacementId) {
        this.nameVisibility = NameVisibility.NONE;
        this.protocol = protocol;
        this.id = id;
        this.replacementId = replacementId;
        this.key = s.toLowerCase(Locale.ROOT);
    }
    
    public EntityData jsonName() {
        this.nameVisibility = NameVisibility.JSON;
        return this;
    }
    
    public EntityData plainName() {
        this.nameVisibility = NameVisibility.PLAIN;
        return this;
    }
    
    public EntityData spawnMetadata(final MetaCreator defaultMeta) {
        this.defaultMeta = defaultMeta;
        return this;
    }
    
    public boolean hasBaseMeta() {
        return this.defaultMeta != null;
    }
    
    public int typeId() {
        return this.id;
    }
    
    public Object mobName() {
        if (this.nameVisibility == NameVisibility.NONE) {
            return null;
        }
        String s = this.protocol.getMappingData().mappedEntityName(this.key);
        if (s == null) {
            ViaBackwards.getPlatform().getLogger().warning("Entity name for " + this.key + " not found in protocol " + this.protocol.getClass().getSimpleName());
            s = this.key;
        }
        return (this.nameVisibility == NameVisibility.JSON) ? ChatRewriter.legacyTextToJson(s) : s;
    }
    
    public int replacementId() {
        return this.replacementId;
    }
    
    public MetaCreator defaultMeta() {
        return this.defaultMeta;
    }
    
    public boolean isObjectType() {
        return false;
    }
    
    public int objectData() {
        return -1;
    }
    
    @Override
    public String toString() {
        return "EntityData{id=" + this.id + ", mobName='" + this.key + '\'' + ", replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
    }
    
    private enum NameVisibility
    {
        PLAIN("PLAIN", 0), 
        JSON("JSON", 1), 
        NONE("NONE", 2);
        
        private static final NameVisibility[] $VALUES;
        
        private NameVisibility(final String s, final int n) {
        }
        
        static {
            $VALUES = new NameVisibility[] { NameVisibility.PLAIN, NameVisibility.JSON, NameVisibility.NONE };
        }
    }
    
    @FunctionalInterface
    public interface MetaCreator
    {
        void createMeta(final WrappedMetadata p0);
    }
}
