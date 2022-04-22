package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        super(protocol1_12To1_11_1);
    }
    
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
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
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.ITEM));
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.ITEM));
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
        ((Protocol1_12To1_11_1)this.protocol).registerServerbound(ServerboundPackets1_12.CLICK_WINDOW, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final InventoryPackets$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                        if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                            this.this$1.this$0.handleItemToServer(item);
                            return;
                        }
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 1 && byteValue == 0 && item == null) {
                            if (((InventoryQuickMoveProvider)Via.getManager().getProviders().get(InventoryQuickMoveProvider.class)).registerQuickMoveAction((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0), (short)packetWrapper.get(Type.SHORT, 0), (short)packetWrapper.get(Type.SHORT, 1), packetWrapper.user())) {
                                packetWrapper.cancel();
                            }
                        }
                        else {
                            this.this$1.this$0.handleItemToServer(item);
                        }
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short)0);
        }
        if ((item.identifier() >= 235 && item.identifier() <= 252) | item.identifier() == 453) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short)14);
        }
        return item;
    }
}
