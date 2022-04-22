package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class PlayerPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_14.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(PlayerPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, 0L);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_14.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(PlayerPackets$2::lambda$registerMap$0);
                this.handler(PlayerPackets$2::lambda$registerMap$1);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$2::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, !Via.getConfig().is1_15InstantRespawn());
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, 0L);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().getEntityTracker(Protocol1_15To1_14_4.class).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_15Types.PLAYER);
            }
        });
    }
}
