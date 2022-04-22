package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;

public class EntityPackets
{
    public static void register(final Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        final MetadataRewriter1_13_1To1_13 metadataRewriter1_13_1To1_13 = (MetadataRewriter1_13_1To1_13)protocol1_13_1To1_13.get(MetadataRewriter1_13_1To1_13.class);
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper(protocol1_13_1To1_13) {
            final Protocol1_13_1To1_13 val$protocol;
            
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
                        if (typeFromId != null) {
                            if (typeFromId.is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                                packetWrapper.set(Type.INT, 0, this.this$0.val$protocol.getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.INT, 0)));
                            }
                            packetWrapper.user().getEntityTracker(Protocol1_13_1To1_13.class).addEntity(intValue, typeFromId);
                        }
                    }
                });
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper(metadataRewriter1_13_1To1_13) {
            final MetadataRewriter1_13_1To1_13 val$metadataRewriter;
            
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
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper(metadataRewriter1_13_1To1_13) {
            final MetadataRewriter1_13_1To1_13 val$metadataRewriter;
            
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
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        metadataRewriter1_13_1To1_13.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter1_13_1To1_13.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }
}
