package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_10 extends LegacyEntityRewriter
{
    public EntityPackets1_10(final Protocol1_9_4To1_10 protocol1_9_4To1_10) {
        super(protocol1_9_4To1_10);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_10 this$0;
            
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
                this.handler(EntityPackets1_10.access$000(this.this$0));
                this.handler(EntityPackets1_10.access$100(this.this$0, EntityPackets1_10$1::lambda$registerMap$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_10$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Optional byId = Entity1_12Types.ObjectType.findById((byte)packetWrapper.get(Type.BYTE, 0));
                        if (byId.isPresent() && byId.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            final int intValue = (int)packetWrapper.get(Type.INT, 0);
                            final Block handleBlock = ((Protocol1_9_4To1_10)EntityPackets1_10.access$200(this.this$1.this$0)).getItemRewriter().handleBlock(intValue & 0xFFF, intValue >> 12 & 0xF);
                            if (handleBlock == null) {
                                return;
                            }
                            packetWrapper.set(Type.INT, 0, handleBlock.getId() | handleBlock.getData() << 12);
                        }
                    }
                });
            }
            
            private static ObjectType lambda$registerMap$0(final Byte b) {
                return Entity1_11Types.ObjectType.findById(b).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_10Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_10Types.EntityType.WEATHER);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_10.access$300(this.this$0, Type.UNSIGNED_BYTE, 0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_10$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final EntityType entityType = this.this$1.this$0.tracker(packetWrapper.user()).entityType((int)packetWrapper.get(Type.VAR_INT, 0));
                        final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                        this.this$1.this$0.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
                        final EntityData access$400 = EntityPackets1_10.access$400(this.this$1.this$0, entityType);
                        if (access$400 != null) {
                            final WrappedMetadata wrappedMetadata = new WrappedMetadata(list);
                            packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)access$400.replacementId());
                            if (access$400.hasBaseMeta()) {
                                access$400.defaultMeta().createMeta(wrappedMetadata);
                            }
                        }
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_10Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_10Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_10.access$500(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }
    
    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_10Types.EntityType.POLAR_BEAR, Entity1_10Types.EntityType.SHEEP).plainName();
        this.filter().type(Entity1_10Types.EntityType.POLAR_BEAR).index(13).handler(EntityPackets1_10::lambda$registerRewrites$0);
        this.filter().type(Entity1_10Types.EntityType.ZOMBIE).index(13).handler(EntityPackets1_10::lambda$registerRewrites$1);
        this.filter().type(Entity1_10Types.EntityType.SKELETON).index(12).handler(EntityPackets1_10::lambda$registerRewrites$2);
        this.filter().removeIndex(5);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_10Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_10Types.getTypeFromId(n, true);
    }
    
    private static void lambda$registerRewrites$2(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if ((int)metadata.getValue() == 2) {
            metadata.setValue(0);
        }
    }
    
    private static void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if ((int)metadata.getValue() == 6) {
            metadata.setValue(0);
        }
    }
    
    private static void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setTypeAndValue(MetaType1_9.Byte, (byte)(metadata.getValue() ? 14 : 0));
    }
    
    static PacketHandler access$000(final EntityPackets1_10 entityPackets1_10) {
        return entityPackets1_10.getObjectTrackerHandler();
    }
    
    static PacketHandler access$100(final EntityPackets1_10 entityPackets1_10, final Function function) {
        return entityPackets1_10.getObjectRewriter(function);
    }
    
    static Protocol access$200(final EntityPackets1_10 entityPackets1_10) {
        return entityPackets1_10.protocol;
    }
    
    static PacketHandler access$300(final EntityPackets1_10 entityPackets1_10, final Type type, final int n) {
        return entityPackets1_10.getTrackerHandler(type, n);
    }
    
    static EntityData access$400(final EntityPackets1_10 entityPackets1_10, final EntityType entityType) {
        return entityPackets1_10.entityDataForType(entityType);
    }
    
    static PacketHandler access$500(final EntityPackets1_10 entityPackets1_10, final Type type, final EntityType entityType) {
        return entityPackets1_10.getTrackerAndMetaHandler(type, entityType);
    }
}
