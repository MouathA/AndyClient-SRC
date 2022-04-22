package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;

public class CommandRewriter1_16_2 extends CommandRewriter
{
    public CommandRewriter1_16_2(final Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:angle", CommandRewriter1_16_2::lambda$new$0);
    }
    
    @Override
    protected String handleArgumentType(final String s) {
        if (s.equals("minecraft:angle")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(s);
    }
    
    private static void lambda$new$0(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 0);
    }
}
