package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public abstract class EntityRewriter extends EntityRewriterBase
{
    protected EntityRewriter(final BackwardsProtocol backwardsProtocol) {
        this(backwardsProtocol, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
    }
    
    protected EntityRewriter(final BackwardsProtocol backwardsProtocol, final MetaType metaType, final MetaType metaType2) {
        super(backwardsProtocol, metaType, 2, metaType2, 3);
    }
    
    @Override
    public void registerTrackerWithData(final ClientboundPacketType clientboundPacketType, final EntityType entityType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper(entityType) {
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(this.this$0.getSpawnTrackerWithDataHandler(this.val$fallingBlockType));
            }
        });
    }
    
    public PacketHandler getSpawnTrackerWithDataHandler(final EntityType entityType) {
        return this::lambda$getSpawnTrackerWithDataHandler$0;
    }
    
    public void registerSpawnTracker(final ClientboundPacketType clientboundPacketType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper() {
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                EntityRewriter.access$000(this.this$0, packetWrapper);
            }
        });
    }
    
    private EntityType setOldEntityId(final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
        final EntityType typeFromId = this.typeFromId(intValue);
        this.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.VAR_INT, 0), typeFromId);
        final int entityId = this.newEntityId(typeFromId.getId());
        if (intValue != entityId) {
            packetWrapper.set(Type.VAR_INT, 1, entityId);
        }
        return typeFromId;
    }
    
    private void lambda$getSpawnTrackerWithDataHandler$0(final EntityType entityType, final PacketWrapper oldEntityId) throws Exception {
        if (this.setOldEntityId(oldEntityId) == entityType) {
            oldEntityId.set(Type.INT, 0, ((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId((int)oldEntityId.get(Type.INT, 0)));
        }
    }
    
    static EntityType access$000(final EntityRewriter entityRewriter, final PacketWrapper oldEntityId) throws Exception {
        return entityRewriter.setOldEntityId(oldEntityId);
    }
}
