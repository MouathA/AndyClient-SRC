package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;

public class EntityPackets
{
    public static void register(final Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        final MetadataRewriter1_13To1_12_2 metadataRewriter1_13To1_12_2 = (MetadataRewriter1_13To1_12_2)protocol1_13To1_12_2.get(MetadataRewriter1_13To1_12_2.class);
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_ENTITY, new PacketRemapper() {
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
                    final EntityPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final Entity1_13Types.EntityType typeFromId = Entity1_13Types.getTypeFromId((byte)packetWrapper.get(Type.BYTE, 0), true);
                        if (typeFromId == null) {
                            return;
                        }
                        packetWrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(intValue, typeFromId);
                        if (typeFromId.is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            (int)packetWrapper.get(Type.INT, 0);
                            packetWrapper.set(Type.INT, 0, WorldPackets.toNewId(80));
                        }
                        if (typeFromId.is(Entity1_13Types.EntityType.ITEM_FRAME)) {
                            (int)packetWrapper.get(Type.INT, 0);
                            switch (5) {
                                case 0: {}
                            }
                            packetWrapper.set(Type.INT, 0, 5);
                        }
                    }
                });
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_MOB, new PacketRemapper(metadataRewriter1_13To1_12_2) {
            final MetadataRewriter1_13To1_12_2 val$metadataRewriter;
            
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
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_PLAYER, new PacketRemapper(metadataRewriter1_13To1_12_2) {
            final MetadataRewriter1_13To1_12_2 val$metadataRewriter;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        metadataRewriter1_13To1_12_2.registerRemoveEntities(ClientboundPackets1_12_1.DESTROY_ENTITIES);
        metadataRewriter1_13To1_12_2.registerMetadataRewriter(ClientboundPackets1_12_1.ENTITY_METADATA, Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
    }
}
