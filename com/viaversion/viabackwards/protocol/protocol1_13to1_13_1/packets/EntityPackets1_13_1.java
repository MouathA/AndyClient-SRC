package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_13_1 extends LegacyEntityRewriter
{
    public EntityPackets1_13_1(final Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        super(protocol1_13To1_13_1);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13_1$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        final Entity1_13Types.EntityType typeFromId = Entity1_13Types.getTypeFromId(byteValue, true);
                        if (typeFromId == null) {
                            ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + byteValue);
                            return;
                        }
                        if (typeFromId.is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            packetWrapper.set(Type.INT, 0, ((Protocol1_13To1_13_1)EntityPackets1_13_1.access$000(this.this$1.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.INT, 0)));
                        }
                        this.this$1.this$0.tracker(packetWrapper.user()).addEntity(intValue, typeFromId);
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_13_1 this$0;
            
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
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.access$100(this.this$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_13_1$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        this.this$1.this$0.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(Types1_13.METADATA_LIST, 0), packetWrapper.user());
                    }
                });
            }
        });
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13.METADATA_LIST);
                this.handler(EntityPackets1_13_1.access$200(this.this$0, Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_PAINTING, Entity1_13Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_13.RESPAWN);
        this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_ARROW).cancel(7);
        this.filter().type(Entity1_13Types.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
        this.filter().type(Entity1_13Types.EntityType.TRIDENT).index(8).toIndex(7);
        this.filter().filterFamily(Entity1_13Types.EntityType.MINECART_ABSTRACT).index(9).handler(this::lambda$registerRewrites$1);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }
    
    private void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            ((Protocol1_13To1_13_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_13.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
    }
    
    static Protocol access$000(final EntityPackets1_13_1 entityPackets1_13_1) {
        return entityPackets1_13_1.protocol;
    }
    
    static PacketHandler access$100(final EntityPackets1_13_1 entityPackets1_13_1) {
        return entityPackets1_13_1.getTrackerHandler();
    }
    
    static PacketHandler access$200(final EntityPackets1_13_1 entityPackets1_13_1, final Type type, final EntityType entityType) {
        return entityPackets1_13_1.getTrackerAndMetaHandler(type, entityType);
    }
}
