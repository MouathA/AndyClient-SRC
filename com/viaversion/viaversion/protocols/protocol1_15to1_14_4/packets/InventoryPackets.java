package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        super(protocol1_15To1_14_4);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_14.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipment(ClientboundPackets1_14.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_14.DECLARE_RECIPES);
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }
}
