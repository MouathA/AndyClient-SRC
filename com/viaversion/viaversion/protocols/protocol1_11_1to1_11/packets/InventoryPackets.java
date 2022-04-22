package com.viaversion.viaversion.protocols.protocol1_11_1to1_11.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_11_1To1_11 protocol1_11_1To1_11) {
        super(protocol1_11_1To1_11);
    }
    
    public void registerPackets() {
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 452) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return null;
    }
}
