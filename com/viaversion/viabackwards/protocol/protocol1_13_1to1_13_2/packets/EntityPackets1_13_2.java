package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class EntityPackets1_13_2
{
    public static void register(final Protocol1_13_1To1_13_2 protocol1_13_1To1_13_2) {
        protocol1_13_1To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() {
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
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13_2$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        for (final Metadata metadata : (List)packetWrapper.get(Types1_13.METADATA_LIST, 0)) {
                            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                        }
                    }
                });
            }
        });
        protocol1_13_1To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13_2$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        for (final Metadata metadata : (List)packetWrapper.get(Types1_13.METADATA_LIST, 0)) {
                            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                        }
                    }
                });
            }
        });
        protocol1_13_1To1_13_2.registerClientbound(ClientboundPackets1_13.ENTITY_METADATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13_2$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        for (final Metadata metadata : (List)packetWrapper.get(Types1_13.METADATA_LIST, 0)) {
                            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                        }
                    }
                });
            }
        });
    }
}
