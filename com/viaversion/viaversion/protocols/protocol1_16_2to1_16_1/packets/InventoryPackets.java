package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        super(protocol1_16_2To1_16_1);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16.UNLOCK_RECIPES, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16.DECLARE_RECIPES);
        this.registerClickWindow(ServerboundPackets1_16_2.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_2To1_16_1)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }
}
