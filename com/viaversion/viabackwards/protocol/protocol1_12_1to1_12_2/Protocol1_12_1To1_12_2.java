package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_12_1To1_12_2 extends BackwardsProtocol
{
    public Protocol1_12_1To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_12_1To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_12_1To1_12_2$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Long n = (Long)packetWrapper.read(Type.LONG);
                        ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(n);
                        packetWrapper.write(Type.VAR_INT, n.hashCode());
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_12_1To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_12_1To1_12_2$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final long keepAlive = ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).getKeepAlive();
                        if (intValue != Long.hashCode(keepAlive)) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.LONG, keepAlive);
                        ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(2147483647L);
                    }
                });
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new KeepAliveTracker());
    }
}
