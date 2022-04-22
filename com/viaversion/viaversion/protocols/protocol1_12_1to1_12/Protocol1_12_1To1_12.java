package com.viaversion.viaversion.protocols.protocol1_12_1to1_12;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_12_1To1_12 extends AbstractProtocol
{
    public Protocol1_12_1To1_12() {
        super(ClientboundPackets1_12.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12.class, ServerboundPackets1_12_1.class);
    }
    
    @Override
    protected void registerPackets() {
        this.cancelServerbound(ServerboundPackets1_12_1.CRAFT_RECIPE_REQUEST);
    }
}
