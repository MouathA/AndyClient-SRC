package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class WorldPackets1_13_2
{
    public static void register(final Protocol1_13_1To1_13_2 protocol1_13_1To1_13_2) {
        protocol1_13_1To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PARTICLE, new PacketRemapper() {
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
                    final WorldPackets1_13_2$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.INT, 0) == 27) {
                            packetWrapper.write(Type.FLAT_ITEM, packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
                        }
                    }
                });
            }
        });
    }
}
