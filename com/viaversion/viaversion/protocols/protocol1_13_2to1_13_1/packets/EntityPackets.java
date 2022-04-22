package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public class EntityPackets
{
    public static void register(final Protocol protocol) {
        final PacketHandler packetHandler = EntityPackets::lambda$register$0;
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper(packetHandler) {
            final PacketHandler val$metaTypeHandler;
            
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
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper(packetHandler) {
            final PacketHandler val$metaTypeHandler;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.ENTITY_METADATA, new PacketRemapper(packetHandler) {
            final PacketHandler val$metaTypeHandler;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
    }
    
    private static void lambda$register$0(final PacketWrapper packetWrapper) throws Exception {
        for (final Metadata metadata : (List)packetWrapper.get(Types1_13_2.METADATA_LIST, 0)) {
            metadata.setMetaType(Types1_13_2.META_TYPES.byId(metadata.metaType().typeId()));
        }
    }
}
