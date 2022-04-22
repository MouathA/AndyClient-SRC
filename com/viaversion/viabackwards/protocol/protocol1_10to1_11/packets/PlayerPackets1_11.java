package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class PlayerPackets1_11
{
    private static final ValueTransformer TO_NEW_FLOAT;
    
    public void register(final Protocol1_10To1_11 protocol1_10To1_11) {
        protocol1_10To1_11.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketRemapper() {
            final PlayerPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets1_11$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue == 2) {
                    final JsonElement jsonElement = (JsonElement)packetWrapper.read(Type.COMPONENT);
                    packetWrapper.clearPacket();
                    packetWrapper.setId(ClientboundPackets1_9_3.CHAT_MESSAGE.ordinal());
                    final String serialize = LegacyComponentSerializer.legacySection().serialize(GsonComponentSerializer.gson().deserialize(jsonElement.toString()));
                    final JsonObject jsonObject = new JsonObject();
                    jsonObject.getAsJsonObject().addProperty("text", serialize);
                    packetWrapper.write(Type.COMPONENT, jsonObject);
                    packetWrapper.write(Type.BYTE, 2);
                }
                else if (intValue > 2) {
                    packetWrapper.set(Type.VAR_INT, 0, intValue - 1);
                }
            }
        });
        protocol1_10To1_11.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() {
            final PlayerPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets1_11$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.VAR_INT);
            }
        });
        protocol1_10To1_11.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            final PlayerPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
            }
        });
    }
    
    static ValueTransformer access$000() {
        return PlayerPackets1_11.TO_NEW_FLOAT;
    }
    
    static {
        TO_NEW_FLOAT = new ValueTransformer() {
            public Float transform(final PacketWrapper packetWrapper, final Short n) throws Exception {
                return n / 15.0f;
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Short)o);
            }
        };
    }
}
