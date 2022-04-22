package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;

public class CommandRewriter1_13_1 extends CommandRewriter
{
    public CommandRewriter1_13_1(final Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:dimension", CommandRewriter1_13_1::lambda$new$0);
    }
    
    @Override
    protected String handleArgumentType(final String s) {
        if (s.equals("minecraft:column_pos")) {
            return "minecraft:vec2";
        }
        if (s.equals("minecraft:dimension")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(s);
    }
    
    private static void lambda$new$0(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 0);
    }
}
