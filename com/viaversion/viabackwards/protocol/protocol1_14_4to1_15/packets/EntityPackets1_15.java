package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import com.viaversion.viaversion.api.protocol.remapper.*;

public class EntityPackets1_15 extends EntityRewriter
{
    public EntityPackets1_15(final Protocol1_14_4To1_15 protocol1_14_4To1_15) {
        super(protocol1_14_4To1_15);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.UPDATE_HEALTH, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.handler(EntityPackets1_15$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((float)packetWrapper.passthrough(Type.FLOAT) > 0.0f) {
                    return;
                }
                if (!((ImmediateRespawn)packetWrapper.user().get(ImmediateRespawn.class)).isImmediateRespawn()) {
                    return;
                }
                final PacketWrapper create = packetWrapper.create(ServerboundPackets1_14.CLIENT_STATUS);
                create.write(Type.VAR_INT, 0);
                create.sendToServer(Protocol1_14_4To1_15.class);
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.GAME_EVENT, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(EntityPackets1_15$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                    ((ImmediateRespawn)packetWrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn((float)packetWrapper.get(Type.FLOAT, 0) == 1.0f);
                }
            }
        });
        this.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
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
                this.handler(EntityPackets1_15$3::lambda$registerMap$0);
                this.handler(this::lambda$registerMap$1);
            }
            
            private void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                this.this$0.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.VAR_INT, 0), Entity1_15Types.getTypeFromId(intValue));
                packetWrapper.set(Type.VAR_INT, 1, EntityTypeMapping.getOldEntityId(intValue));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Types1_14.METADATA_LIST, new ArrayList());
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.LONG, Type.NOTHING);
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.map(Type.LONG, Type.NOTHING);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets1_15.access$000(this.this$0, Entity1_15Types.PLAYER, Type.INT));
                this.handler(EntityPackets1_15$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ImmediateRespawn)packetWrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn(!(boolean)packetWrapper.read(Type.BOOLEAN));
            }
        });
        this.registerTracker(ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, Entity1_15Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, Entity1_15Types.LIGHTNING_BOLT);
        this.registerTracker(ClientboundPackets1_15.SPAWN_PAINTING, Entity1_15Types.PAINTING);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(EntityPackets1_15$6::lambda$registerMap$0);
                this.handler(EntityPackets1_15.access$100(this.this$0, Entity1_15Types.PLAYER, Type.VAR_INT));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Types1_14.METADATA_LIST, new ArrayList());
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper() {
            final EntityPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (this.this$0.tracker(packetWrapper.user()).entityType((int)packetWrapper.get(Type.VAR_INT, 0)) != Entity1_15Types.BEE) {
                    return;
                }
                int intValue;
                final int n = intValue = (int)packetWrapper.get(Type.INT, 0);
                while (0 < n) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    if (s.equals("generic.flyingSpeed")) {
                        --intValue;
                        packetWrapper.read(Type.DOUBLE);
                        while (0 < (int)packetWrapper.read(Type.VAR_INT)) {
                            packetWrapper.read(Type.UUID);
                            packetWrapper.read(Type.DOUBLE);
                            packetWrapper.read(Type.BYTE);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    else {
                        packetWrapper.write(Type.STRING, s);
                        packetWrapper.passthrough(Type.DOUBLE);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough(Type.DOUBLE);
                            packetWrapper.passthrough(Type.BYTE);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                if (intValue != n) {
                    packetWrapper.set(Type.INT, 0, intValue);
                }
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.registerMetaTypeHandler(Types1_14.META_TYPES.itemType, Types1_14.META_TYPES.blockStateType, Types1_14.META_TYPES.particleType, null);
        this.filter().filterFamily(Entity1_15Types.LIVINGENTITY).removeIndex(12);
        this.filter().type(Entity1_15Types.BEE).cancel(15);
        this.filter().type(Entity1_15Types.BEE).cancel(16);
        this.mapEntityTypeWithData(Entity1_15Types.BEE, Entity1_15Types.PUFFERFISH).jsonName().spawnMetadata(EntityPackets1_15::lambda$registerRewrites$0);
        this.filter().type(Entity1_15Types.ENDERMAN).cancel(16);
        this.filter().type(Entity1_15Types.TRIDENT).cancel(10);
        this.filter().type(Entity1_15Types.WOLF).addIndex(17);
        this.filter().type(Entity1_15Types.WOLF).index(8).handler(EntityPackets1_15::lambda$registerRewrites$1);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_15Types.getTypeFromId(n);
    }
    
    @Override
    public int newEntityId(final int n) {
        return EntityTypeMapping.getOldEntityId(n);
    }
    
    private static void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metaHandlerEvent.createExtraMeta(new Metadata(17, Types1_14.META_TYPES.floatType, metaHandlerEvent.meta().value()));
    }
    
    private static void lambda$registerRewrites$0(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(14, Types1_14.META_TYPES.booleanType, false));
        wrappedMetadata.add(new Metadata(15, Types1_14.META_TYPES.varIntType, 2));
    }
    
    static PacketHandler access$000(final EntityPackets1_15 entityPackets1_15, final EntityType entityType, final Type type) {
        return entityPackets1_15.getTrackerHandler(entityType, type);
    }
    
    static PacketHandler access$100(final EntityPackets1_15 entityPackets1_15, final EntityType entityType, final Type type) {
        return entityPackets1_15.getTrackerHandler(entityType, type);
    }
}
