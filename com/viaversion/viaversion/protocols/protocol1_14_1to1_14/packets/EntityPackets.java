package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets;

import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class EntityPackets
{
    public static void register(final Protocol1_14_1To1_14 protocol1_14_1To1_14) {
        final MetadataRewriter1_14_1To1_14 metadataRewriter1_14_1To1_14 = (MetadataRewriter1_14_1To1_14)protocol1_14_1To1_14.get(MetadataRewriter1_14_1To1_14.class);
        protocol1_14_1To1_14.registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper(metadataRewriter1_14_1To1_14) {
            final MetadataRewriter1_14_1To1_14 val$metadataRewriter;
            
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
                this.map(Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
            }
        });
        metadataRewriter1_14_1To1_14.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        protocol1_14_1To1_14.registerClientbound(ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper(metadataRewriter1_14_1To1_14) {
            final MetadataRewriter1_14_1To1_14 val$metadataRewriter;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, Entity1_14Types.PLAYER));
            }
        });
        metadataRewriter1_14_1To1_14.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
    }
}
