package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class ScoreboardPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.TEAMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(ScoreboardPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                if (byteValue == 0 || byteValue == 2) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.read(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                }
                if (byteValue == 0 || byteValue == 3 || byteValue == 4) {
                    packetWrapper.passthrough(Type.STRING_ARRAY);
                }
            }
        });
    }
}
