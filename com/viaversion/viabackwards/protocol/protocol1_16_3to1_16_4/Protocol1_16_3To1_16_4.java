package com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_16_3To1_16_4 extends BackwardsProtocol
{
    public Protocol1_16_3To1_16_4() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_16_3To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(Protocol1_16_3To1_16_4$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.read(Type.VAR_INT) == 1) {
                    packetWrapper.write(Type.VAR_INT, 40);
                }
                else {
                    packetWrapper.write(Type.VAR_INT, ((PlayerHandStorage)packetWrapper.user().get(PlayerHandStorage.class)).getCurrentHand());
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_16_2.HELD_ITEM_CHANGE, new PacketRemapper() {
            final Protocol1_16_3To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_3To1_16_4$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((PlayerHandStorage)packetWrapper.user().get(PlayerHandStorage.class)).setCurrentHand((short)packetWrapper.passthrough(Type.SHORT));
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new PlayerHandStorage());
    }
}
