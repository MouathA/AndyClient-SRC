package com.viaversion.viaversion.protocols.protocol1_9_1to1_9;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_9_1To1_9 extends AbstractProtocol
{
    public Protocol1_9_1To1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() {
            final Protocol1_9_1To1_9 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketRemapper() {
            final Protocol1_9_1To1_9 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_1To1_9$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue >= 415) {
                            packetWrapper.set(Type.VAR_INT, 0, intValue + 1);
                        }
                    }
                });
            }
        });
    }
}
