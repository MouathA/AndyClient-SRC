package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class WorldPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PARTICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.INT, 0) == 27) {
                            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                        }
                    }
                });
            }
        });
    }
}
