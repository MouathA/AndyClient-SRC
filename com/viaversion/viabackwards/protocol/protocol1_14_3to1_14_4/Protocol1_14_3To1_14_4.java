package com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;

public class Protocol1_14_3To1_14_4 extends BackwardsProtocol
{
    public Protocol1_14_3To1_14_4() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING, ClientboundPackets1_14.BLOCK_CHANGE, new PacketRemapper() {
            final Protocol1_14_3To1_14_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(Protocol1_14_3To1_14_4$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                if ((boolean)packetWrapper.read(Type.BOOLEAN) && intValue == 0) {
                    packetWrapper.cancel();
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() {
            final Protocol1_14_3To1_14_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_14_3To1_14_4$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.VAR_INT);
                        while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            }
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.read(Type.INT);
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
    }
}
