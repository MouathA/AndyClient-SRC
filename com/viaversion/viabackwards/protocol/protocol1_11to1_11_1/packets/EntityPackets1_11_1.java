package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;

public class EntityPackets1_11_1 extends LegacyEntityRewriter
{
    public EntityPackets1_11_1(final Protocol1_11To1_11_1 protocol1_11To1_11_1) {
        super(protocol1_11To1_11_1);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_11_1 this$0;
            
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
                this.handler(EntityPackets1_11_1.access$000(this.this$0));
                this.handler(EntityPackets1_11_1.access$100(this.this$0, EntityPackets1_11_1$1::lambda$registerMap$0));
            }
            
            private static ObjectType lambda$registerMap$0(final Byte b) {
                return Entity1_11Types.ObjectType.findById(b).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_11_1 this$0;
            
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
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11_1.access$200(this.this$0));
                this.handler(EntityPackets1_11_1.access$300(this.this$0, Types1_9.METADATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_11_1 this$0;
            
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
                this.handler(EntityPackets1_11_1.access$400(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().type(Entity1_11Types.EntityType.FIREWORK).cancel(7);
        this.filter().type(Entity1_11Types.EntityType.PIG).cancel(14);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }
    
    static PacketHandler access$000(final EntityPackets1_11_1 entityPackets1_11_1) {
        return entityPackets1_11_1.getObjectTrackerHandler();
    }
    
    static PacketHandler access$100(final EntityPackets1_11_1 entityPackets1_11_1, final Function function) {
        return entityPackets1_11_1.getObjectRewriter(function);
    }
    
    static PacketHandler access$200(final EntityPackets1_11_1 entityPackets1_11_1) {
        return entityPackets1_11_1.getTrackerHandler();
    }
    
    static PacketHandler access$300(final EntityPackets1_11_1 entityPackets1_11_1, final Type type) {
        return entityPackets1_11_1.getMobSpawnRewriter(type);
    }
    
    static PacketHandler access$400(final EntityPackets1_11_1 entityPackets1_11_1, final Type type, final EntityType entityType) {
        return entityPackets1_11_1.getTrackerAndMetaHandler(type, entityType);
    }
}
