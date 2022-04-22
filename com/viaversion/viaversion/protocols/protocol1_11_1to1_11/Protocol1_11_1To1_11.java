package com.viaversion.viaversion.protocols.protocol1_11_1to1_11;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.packets.*;

public class Protocol1_11_1To1_11 extends AbstractProtocol
{
    private final ItemRewriter itemRewriter;
    
    public Protocol1_11_1To1_11() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.itemRewriter.register();
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
