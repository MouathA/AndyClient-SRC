package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;

public class CommandRewriter1_16 extends CommandRewriter
{
    public CommandRewriter1_16(final Protocol protocol) {
        super(protocol);
    }
    
    @Override
    protected String handleArgumentType(final String s) {
        if (s.equals("minecraft:uuid")) {
            return "minecraft:game_profile";
        }
        return super.handleArgumentType(s);
    }
}
