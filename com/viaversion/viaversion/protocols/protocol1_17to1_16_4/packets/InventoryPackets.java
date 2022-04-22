package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;

public final class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        super(protocol1_17To1_16_4);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.handler(InventoryPackets$2::lambda$registerMap$0);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$1);
            }
            
            private void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.read(Type.VAR_INT)) {
                    packetWrapper.read(Type.SHORT);
                    packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                    int n = 0;
                    ++n;
                }
                Item item = (Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue == 5 || intValue == 1) {
                    item = null;
                }
                else {
                    this.this$0.handleItemToServer(item);
                }
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.SHORT, 0);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.WINDOW_CONFIRMATION, null, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                final short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                if (!(boolean)packetWrapper.read(Type.BOOLEAN)) {
                    final int n = 0x40000000 | shortValue << 16 | (shortValue2 & 0xFFFF);
                    ((InventoryAcknowledgements)packetWrapper.user().get(InventoryAcknowledgements.class)).addId(n);
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_17.PING);
                    create.write(Type.INT, n);
                    create.send(Protocol1_17To1_16_4.class);
                }
                packetWrapper.cancel();
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.PONG, null, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.INT);
                if ((intValue & 0x40000000) != 0x0 && ((InventoryAcknowledgements)packetWrapper.user().get(InventoryAcknowledgements.class)).removeId(intValue)) {
                    final short n = (short)(intValue >> 16 & 0xFF);
                    final short n2 = (short)(intValue & 0xFFFF);
                    final PacketWrapper create = packetWrapper.create(ServerboundPackets1_16_2.WINDOW_CONFIRMATION);
                    create.write(Type.UNSIGNED_BYTE, n);
                    create.write(Type.SHORT, n2);
                    create.write(Type.BOOLEAN, true);
                    create.sendToServer(Protocol1_17To1_16_4.class);
                }
                packetWrapper.cancel();
            }
        });
    }
}
