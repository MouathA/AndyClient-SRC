package com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_12_2To1_12_1 extends AbstractProtocol
{
    public Protocol1_12_2To1_12_1() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_12_2To1_12_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.LONG);
            }
        });
        this.registerServerbound(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_12_2To1_12_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.LONG, Type.VAR_INT);
            }
        });
    }
}
