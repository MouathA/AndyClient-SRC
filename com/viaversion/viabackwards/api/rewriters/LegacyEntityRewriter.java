package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import java.util.function.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.api.entities.storage.*;

public abstract class LegacyEntityRewriter extends EntityRewriterBase
{
    private final Map objectTypes;
    
    protected LegacyEntityRewriter(final BackwardsProtocol backwardsProtocol) {
        this(backwardsProtocol, MetaType1_9.String, MetaType1_9.Boolean);
    }
    
    protected LegacyEntityRewriter(final BackwardsProtocol backwardsProtocol, final MetaType metaType, final MetaType metaType2) {
        super(backwardsProtocol, metaType, 2, metaType2, 3);
        this.objectTypes = new HashMap();
    }
    
    protected EntityObjectData mapObjectType(final ObjectType objectType, final ObjectType objectType2, final int n) {
        final EntityObjectData entityObjectData = new EntityObjectData((BackwardsProtocol)this.protocol, objectType.getType().name(), objectType.getId(), objectType2.getId(), n);
        this.objectTypes.put(objectType, entityObjectData);
        return entityObjectData;
    }
    
    protected EntityData getObjectData(final ObjectType objectType) {
        return this.objectTypes.get(objectType);
    }
    
    protected void registerRespawn(final ClientboundPacketType clientboundPacketType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper() {
            final LegacyEntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(LegacyEntityRewriter$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
            }
        });
    }
    
    protected void registerJoinGame(final ClientboundPacketType clientboundPacketType, final EntityType entityType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper(entityType) {
            final EntityType val$playerType;
            final LegacyEntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final EntityType entityType, final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                this.this$0.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.INT, 0), entityType);
            }
        });
    }
    
    @Override
    public void registerMetadataRewriter(final ClientboundPacketType clientboundPacketType, final Type type, final Type type2) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper(type, type2) {
            final Type val$oldMetaType;
            final Type val$newMetaType;
            final LegacyEntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                if (this.val$oldMetaType != null) {
                    this.map(this.val$oldMetaType, this.val$newMetaType);
                }
                else {
                    this.map(this.val$newMetaType);
                }
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(type, 0), packetWrapper.user());
            }
        });
    }
    
    @Override
    public void registerMetadataRewriter(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.registerMetadataRewriter(clientboundPacketType, null, type);
    }
    
    protected PacketHandler getMobSpawnRewriter(final Type type) {
        return this::lambda$getMobSpawnRewriter$0;
    }
    
    protected PacketHandler getObjectTrackerHandler() {
        return this::lambda$getObjectTrackerHandler$1;
    }
    
    protected PacketHandler getTrackerAndMetaHandler(final Type type, final EntityType entityType) {
        return this::lambda$getTrackerAndMetaHandler$2;
    }
    
    protected PacketHandler getObjectRewriter(final Function function) {
        return this::lambda$getObjectRewriter$3;
    }
    
    protected EntityType getObjectTypeFromId(final int n) {
        return this.typeFromId(n);
    }
    
    @Deprecated
    protected void addTrackedEntity(final PacketWrapper packetWrapper, final int n, final EntityType entityType) throws Exception {
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
    }
    
    private void lambda$getObjectRewriter$3(final Function function, final PacketWrapper packetWrapper) throws Exception {
        final ObjectType objectType = function.apply(packetWrapper.get(Type.BYTE, 0));
        if (objectType == null) {
            ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + packetWrapper.get(Type.BYTE, 0));
            return;
        }
        final EntityData objectData = this.getObjectData(objectType);
        if (objectData != null) {
            packetWrapper.set(Type.BYTE, 0, (byte)objectData.replacementId());
            if (objectData.objectData() != -1) {
                packetWrapper.set(Type.INT, 0, objectData.objectData());
            }
        }
    }
    
    private void lambda$getTrackerAndMetaHandler$2(final EntityType entityType, final Type type, final PacketWrapper packetWrapper) throws Exception {
        this.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), entityType);
        this.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(type, 0), packetWrapper.user());
    }
    
    private void lambda$getObjectTrackerHandler$1(final PacketWrapper packetWrapper) throws Exception {
        this.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), this.getObjectTypeFromId((byte)packetWrapper.get(Type.BYTE, 0)));
    }
    
    private void lambda$getMobSpawnRewriter$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
        final EntityType entityType = this.tracker(packetWrapper.user()).entityType(intValue);
        final List list = (List)packetWrapper.get(type, 0);
        this.handleMetadata(intValue, list, packetWrapper.user());
        final EntityData entityDataForType = this.entityDataForType(entityType);
        if (entityDataForType != null) {
            packetWrapper.set(Type.VAR_INT, 1, entityDataForType.replacementId());
            if (entityDataForType.hasBaseMeta()) {
                entityDataForType.defaultMeta().createMeta(new WrappedMetadata(list));
            }
        }
    }
}
