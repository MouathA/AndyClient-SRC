package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets1_13_1 extends ItemRewriter
{
    public InventoryPackets1_13_1(final Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        super(protocol1_13To1_13_1);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final InventoryPackets1_13_1$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((String)packetWrapper.passthrough(Type.STRING)).equals("minecraft:trader_list")) {
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
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
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
    }
}
