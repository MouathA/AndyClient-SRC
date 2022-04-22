package com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_16_4To1_16_3 extends AbstractProtocol
{
    public Protocol1_16_4To1_16_3() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_16_4To1_16_3 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(Protocol1_16_4To1_16_3$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, (int)(((int)packetWrapper.read(Type.VAR_INT) == 40) ? 1 : 0));
            }
        });
    }
}
