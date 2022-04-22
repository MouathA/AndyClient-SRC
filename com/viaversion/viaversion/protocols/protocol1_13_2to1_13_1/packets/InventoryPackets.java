package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLAT_ITEM_ARRAY, Type.FLAT_VAR_INT_ITEM_ARRAY);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.equals("minecraft:trader_list") || s.equals("trader_list")) {
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final InventoryPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.STRING);
                            final String s = (String)packetWrapper.passthrough(Type.STRING);
                            if (s.equals("crafting_shapeless")) {
                                packetWrapper.passthrough(Type.STRING);
                                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                    int n = 0;
                                    ++n;
                                }
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                            }
                            else if (s.equals("crafting_shaped")) {
                                final int n2 = (int)packetWrapper.passthrough(Type.VAR_INT) * (int)packetWrapper.passthrough(Type.VAR_INT);
                                packetWrapper.passthrough(Type.STRING);
                                while (0 < n2) {
                                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                    int n = 0;
                                    ++n;
                                }
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                            }
                            else if (s.equals("smelting")) {
                                packetWrapper.passthrough(Type.STRING);
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                                packetWrapper.passthrough(Type.FLOAT);
                                packetWrapper.passthrough(Type.VAR_INT);
                            }
                            int n3 = 0;
                            ++n3;
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
    }
}
