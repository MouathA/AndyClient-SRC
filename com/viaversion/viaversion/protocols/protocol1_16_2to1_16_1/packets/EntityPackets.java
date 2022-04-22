package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.*;

public class EntityPackets
{
    public static void register(final Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        final MetadataRewriter1_16_2To1_16_1 metadataRewriter1_16_2To1_16_1 = (MetadataRewriter1_16_2To1_16_1)protocol1_16_2To1_16_1.get(MetadataRewriter1_16_2To1_16_1.class);
        metadataRewriter1_16_2To1_16_1.registerTrackerWithData(ClientboundPackets1_16.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        metadataRewriter1_16_2To1_16_1.registerTracker(ClientboundPackets1_16.SPAWN_MOB);
        metadataRewriter1_16_2To1_16_1.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        metadataRewriter1_16_2To1_16_1.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST);
        metadataRewriter1_16_2To1_16_1.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketRemapper(protocol1_16_2To1_16_1) {
            final Protocol1_16_2To1_16_1 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(EntityPackets$1::lambda$registerMap$0);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.handler(EntityPackets$1::lambda$registerMap$1);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE, Type.VAR_INT);
                this.handler(EntityPackets$1::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().getEntityTracker(Protocol1_16_2To1_16_1.class).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_16_2Types.PLAYER);
            }
            
            private static void lambda$registerMap$1(final Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1, final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.NBT);
                packetWrapper.write(Type.NBT, protocol1_16_2To1_16_1.getMappingData().getDimensionRegistry());
                packetWrapper.write(Type.NBT, EntityPackets.getDimensionData((String)packetWrapper.read(Type.STRING)));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                packetWrapper.write(Type.BOOLEAN, (shortValue & 0x8) != 0x0);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)(shortValue & 0xFFFFFFF7));
            }
        });
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.NBT, EntityPackets.getDimensionData((String)packetWrapper.read(Type.STRING)));
            }
        });
    }
    
    public static CompoundTag getDimensionData(final String s) {
        final CompoundTag compoundTag = Protocol1_16_2To1_16_1.MAPPINGS.getDimensionDataMap().get(s);
        if (compoundTag == null) {
            Via.getPlatform().getLogger().severe("Could not get dimension data of " + s);
            throw new NullPointerException("Dimension data for " + s + " is null!");
        }
        return compoundTag;
    }
}
