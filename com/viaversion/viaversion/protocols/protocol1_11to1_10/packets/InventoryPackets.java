package com.viaversion.viaversion.protocols.protocol1_11to1_10.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_11To1_10 protocol1_11To1_10) {
        super(protocol1_11To1_10);
    }
    
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((String)packetWrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                EntityIdRewriter.toClientItem((Item)packetWrapper.passthrough(Type.ITEM));
                                EntityIdRewriter.toClientItem((Item)packetWrapper.passthrough(Type.ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    EntityIdRewriter.toClientItem((Item)packetWrapper.passthrough(Type.ITEM));
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
        this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        EntityIdRewriter.toClientItem(item);
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        EntityIdRewriter.toServerItem(item);
        if (item == null) {
            return null;
        }
        if ((item.identifier() >= 218 && item.identifier() <= 234) | (item.identifier() == 449 || item.identifier() == 450)) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }
}
