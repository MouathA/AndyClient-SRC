package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_13_1To1_13_2 extends BackwardsProtocol
{
    public Protocol1_13_1To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }
    
    @Override
    protected void registerPackets() {
        InventoryPackets1_13_2.register(this);
        WorldPackets1_13_2.register(this);
        EntityPackets1_13_2.register(this);
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_13_1To1_13_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, new PacketRemapper() {
            final Protocol1_13_1To1_13_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13_1To1_13_2$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.BOOLEAN);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int intValue = 0;
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.COMPONENT);
                                packetWrapper.passthrough(Type.COMPONENT);
                                packetWrapper.write(Type.FLAT_ITEM, packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
                                packetWrapper.passthrough(Type.VAR_INT);
                                intValue = (int)packetWrapper.passthrough(Type.INT);
                                if (false) {
                                    packetWrapper.passthrough(Type.STRING);
                                }
                                packetWrapper.passthrough(Type.FLOAT);
                                packetWrapper.passthrough(Type.FLOAT);
                            }
                            packetWrapper.passthrough(Type.STRING_ARRAY);
                            while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                                packetWrapper.passthrough(Type.STRING_ARRAY);
                                ++intValue;
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
    }
}
