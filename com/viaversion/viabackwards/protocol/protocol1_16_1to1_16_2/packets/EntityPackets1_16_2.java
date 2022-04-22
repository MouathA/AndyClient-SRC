package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.*;
import com.viaversion.viabackwards.api.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.remapper.*;

public class EntityPackets1_16_2 extends EntityRewriter
{
    private final Set oldDimensions;
    
    public EntityPackets1_16_2(final Protocol1_16_1To1_16_2 protocol1_16_1To1_16_2) {
        super(protocol1_16_1To1_16_2);
        this.oldDimensions = Sets.newHashSet("minecraft:overworld", "minecraft:the_nether", "minecraft:the_end");
    }
    
    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_EXPERIENCE_ORB, Entity1_16_2Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PAINTING, Entity1_16_2Types.PAINTING);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16_2.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(EntityPackets1_16_2$1::lambda$registerMap$0);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.handler(this::lambda$registerMap$1);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.handler(EntityPackets1_16_2$1::lambda$registerMap$2);
                this.handler(EntityPackets1_16_2.access$000(this.this$0, Entity1_16_2Types.PLAYER, Type.INT));
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)Math.max((int)packetWrapper.read(Type.VAR_INT), 255));
            }
            
            private void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.NBT);
                packetWrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
                packetWrapper.write(Type.STRING, EntityPackets1_16_2.access$100(this.this$0, (CompoundTag)packetWrapper.read(Type.NBT)));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                if (booleanValue) {
                    shortValue |= 0x8;
                }
                packetWrapper.write(Type.UNSIGNED_BYTE, shortValue);
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.RESPAWN, new PacketRemapper() {
            final EntityPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, EntityPackets1_16_2.access$100(this.this$0, (CompoundTag)packetWrapper.read(Type.NBT)));
            }
        });
    }
    
    private String getDimensionFromData(final CompoundTag compoundTag) {
        final StringTag stringTag = (StringTag)compoundTag.get("effects");
        return (stringTag != null && this.oldDimensions.contains(stringTag.getValue())) ? stringTag.getValue() : "minecraft:overworld";
    }
    
    @Override
    protected void registerRewrites() {
        this.registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, Types1_16.META_TYPES.particleType, Types1_16.META_TYPES.optionalComponentType);
        this.mapTypes(Entity1_16_2Types.values(), Entity1_16Types.class);
        this.mapEntityTypeWithData(Entity1_16_2Types.PIGLIN_BRUTE, Entity1_16_2Types.PIGLIN).jsonName();
        this.filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(15).toIndex(16);
        this.filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(16).toIndex(15);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_16_2Types.getTypeFromId(n);
    }
    
    static PacketHandler access$000(final EntityPackets1_16_2 entityPackets1_16_2, final EntityType entityType, final Type type) {
        return entityPackets1_16_2.getTrackerHandler(entityType, type);
    }
    
    static String access$100(final EntityPackets1_16_2 entityPackets1_16_2, final CompoundTag compoundTag) {
        return entityPackets1_16_2.getDimensionFromData(compoundTag);
    }
}
