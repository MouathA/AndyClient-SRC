package com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_14_3To1_14_2 extends AbstractProtocol
{
    public Protocol1_14_3To1_14_2() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, null, null);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() {
            final Protocol1_14_3To1_14_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_14_3To1_14_2$1 this$1;
                    
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
                            int booleanValue = 0;
                            ++booleanValue;
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        int booleanValue = ((boolean)packetWrapper.passthrough(Type.BOOLEAN)) ? 1 : 0;
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
    }
}
