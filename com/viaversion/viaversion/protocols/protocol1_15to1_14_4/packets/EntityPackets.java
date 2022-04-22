package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class EntityPackets
{
    public static void register(final Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        final MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4 = (MetadataRewriter1_15To1_14_4)protocol1_15To1_14_4.get(MetadataRewriter1_15To1_14_4.class);
        metadataRewriter1_15To1_14_4.registerTrackerWithData(ClientboundPackets1_14.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper(metadataRewriter1_15To1_14_4) {
            final MetadataRewriter1_15To1_14_4 val$metadataRewriter;
            
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
                this.handler(this.val$metadataRewriter.trackerHandler());
                this.handler(EntityPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4, final PacketWrapper packetWrapper) throws Exception {
                EntityPackets.access$000(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), metadataRewriter1_15To1_14_4);
            }
        });
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper(metadataRewriter1_15To1_14_4) {
            final MetadataRewriter1_15To1_14_4 val$metadataRewriter;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4, final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.user().getEntityTracker(Protocol1_15To1_14_4.class).addEntity(intValue, Entity1_15Types.PLAYER);
                EntityPackets.access$000(packetWrapper, intValue, metadataRewriter1_15To1_14_4);
            }
        });
        metadataRewriter1_15To1_14_4.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter1_15To1_14_4.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
    }
    
    private static void sendMetadataPacket(final PacketWrapper packetWrapper, final int n, final EntityRewriter entityRewriter) throws Exception {
        final List list = (List)packetWrapper.read(Types1_14.METADATA_LIST);
        if (list.isEmpty()) {
            return;
        }
        packetWrapper.send(Protocol1_15To1_14_4.class);
        packetWrapper.cancel();
        entityRewriter.handleMetadata(n, list, packetWrapper.user());
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, packetWrapper.user());
        create.write(Type.VAR_INT, n);
        create.write(Types1_14.METADATA_LIST, list);
        create.send(Protocol1_15To1_14_4.class);
    }
    
    public static int getNewEntityId(final int n) {
        return (n >= 4) ? (n + 1) : n;
    }
    
    static void access$000(final PacketWrapper packetWrapper, final int n, final EntityRewriter entityRewriter) throws Exception {
        sendMetadataPacket(packetWrapper, n, entityRewriter);
    }
}
