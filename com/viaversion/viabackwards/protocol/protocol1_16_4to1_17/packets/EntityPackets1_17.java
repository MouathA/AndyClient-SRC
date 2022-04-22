package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.protocol.remapper.*;

public final class EntityPackets1_17 extends EntityRewriter
{
    private boolean warned;
    
    public EntityPackets1_17(final Protocol1_16_4To1_17 protocol1_16_4To1_17) {
        super(protocol1_16_4To1_17);
    }
    
    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_17.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_17.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_EXPERIENCE_ORB, Entity1_17Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PAINTING, Entity1_17Types.PAINTING);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_17.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_16.METADATA_LIST);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_16_2.DESTROY_ENTITIES, new PacketRemapper() {
            final EntityPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                this.this$0.tracker(packetWrapper.user()).removeEntity(intValue);
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { intValue });
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(EntityPackets1_17$2::lambda$registerMap$0);
                this.handler(EntityPackets1_17.access$000(this.this$0, Entity1_17Types.PLAYER, Type.INT));
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this::lambda$registerMap$1);
            }
            
            private void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                final Iterator iterator = ((ListTag)((CompoundTag)compoundTag.get("minecraft:worldgen/biome")).get("value")).iterator();
                while (iterator.hasNext()) {
                    final StringTag stringTag = (StringTag)((CompoundTag)((CompoundTag)iterator.next()).get("element")).get("category");
                    if (stringTag.getValue().equalsIgnoreCase("underground")) {
                        stringTag.setValue("none");
                    }
                }
                final Iterator iterator2 = ((ListTag)((CompoundTag)compoundTag.get("minecraft:dimension_type")).get("value")).iterator();
                while (iterator2.hasNext()) {
                    EntityPackets1_17.access$100(this.this$0, (CompoundTag)((CompoundTag)iterator2.next()).get("element"), false);
                }
                EntityPackets1_17.access$100(this.this$0, (CompoundTag)packetWrapper.get(Type.NBT, 1), true);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((byte)packetWrapper.get(Type.BYTE, 0) == -1) {
                    packetWrapper.set(Type.BYTE, 0, 0);
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.RESPAWN, new PacketRemapper() {
            final EntityPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandler(0));
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_17.access$100(this.this$0, (CompoundTag)packetWrapper.get(Type.NBT, 0), true);
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.PLAYER_POSITION, new PacketRemapper() {
            final EntityPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(EntityPackets1_17$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.BOOLEAN);
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.ENTITY_PROPERTIES, new PacketRemapper() {
            final EntityPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(EntityPackets1_17$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.INT, packetWrapper.read(Type.VAR_INT));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_ENTER, ClientboundPackets1_16_2.COMBAT_EVENT, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_END, ClientboundPackets1_16_2.COMBAT_EVENT, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_KILL, ClientboundPackets1_16_2.COMBAT_EVENT, 2);
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, Types1_16.META_TYPES.optionalComponentType);
        this.mapTypes(Entity1_17Types.values(), Entity1_16_2Types.class);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(17);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(18);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(19);
        this.filter().type(Entity1_17Types.GLOW_SQUID).cancel(16);
        this.filter().type(Entity1_17Types.GOAT).cancel(17);
        this.mapEntityTypeWithData(Entity1_17Types.AXOLOTL, Entity1_17Types.TROPICAL_FISH).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GOAT, Entity1_17Types.SHEEP).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_SQUID, Entity1_17Types.SQUID).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_ITEM_FRAME, Entity1_17Types.ITEM_FRAME);
        this.filter().type(Entity1_17Types.SHULKER).addIndex(17);
        this.filter().removeIndex(7);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_17Types.getTypeFromId(n);
    }
    
    private void reduceExtendedHeight(final CompoundTag compoundTag, final boolean b) {
        final IntTag intTag = (IntTag)compoundTag.get("min_y");
        final IntTag intTag2 = (IntTag)compoundTag.get("height");
        final IntTag intTag3 = (IntTag)compoundTag.get("logical_height");
        if (intTag.asInt() != 0 || intTag2.asInt() > 256 || intTag3.asInt() > 256) {
            if (b && !this.warned) {
                ViaBackwards.getPlatform().getLogger().warning("Custom worlds heights are NOT SUPPORTED for 1.16 players and older and may lead to errors!");
                ViaBackwards.getPlatform().getLogger().warning("You have min/max set to " + intTag.asInt() + "/" + intTag2.asInt());
                this.warned = true;
            }
            intTag2.setValue(Math.min(256, intTag2.asInt()));
            intTag3.setValue(Math.min(256, intTag3.asInt()));
        }
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
        final MetaType metaType = metadata.metaType();
        if (metaType == Types1_16.META_TYPES.particleType) {
            final Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 16) {
                particle.getArguments().subList(4, 7).clear();
            }
            else if (particle.getId() == 37) {
                particle.setId(0);
                particle.getArguments().clear();
                return;
            }
            this.rewriteParticle(particle);
        }
        else if (metaType == Types1_16.META_TYPES.poseType) {
            final int intValue = (int)metadata.value();
            if (intValue == 6) {
                metadata.setValue(1);
            }
            else if (intValue > 6) {
                metadata.setValue(intValue - 1);
            }
        }
    }
    
    static PacketHandler access$000(final EntityPackets1_17 entityPackets1_17, final EntityType entityType, final Type type) {
        return entityPackets1_17.getTrackerHandler(entityType, type);
    }
    
    static void access$100(final EntityPackets1_17 entityPackets1_17, final CompoundTag compoundTag, final boolean b) {
        entityPackets1_17.reduceExtendedHeight(compoundTag, b);
    }
}
