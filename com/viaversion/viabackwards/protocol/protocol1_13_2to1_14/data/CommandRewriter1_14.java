package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;

public class CommandRewriter1_14 extends CommandRewriter
{
    public CommandRewriter1_14(final Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:nbt_tag", CommandRewriter1_14::lambda$new$0);
        this.parserHandlers.put("minecraft:time", CommandRewriter1_14::lambda$new$1);
    }
    
    @Override
    protected String handleArgumentType(final String s) {
        switch (s.hashCode()) {
            case -783223342: {
                if (s.equals("minecraft:nbt_compound_tag")) {
                    break;
                }
                break;
            }
            case 543170382: {
                if (s.equals("minecraft:nbt_tag")) {
                    break;
                }
                break;
            }
            case -1006391174: {
                if (s.equals("minecraft:time")) {}
                break;
            }
        }
        switch (2) {
            case 0: {
                return "minecraft:nbt";
            }
            case 1: {
                return "brigadier:string";
            }
            case 2: {
                return "brigadier:integer";
            }
            default: {
                return super.handleArgumentType(s);
            }
        }
    }
    
    private static void lambda$new$1(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.BYTE, 1);
        packetWrapper.write(Type.INT, 0);
    }
    
    private static void lambda$new$0(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 2);
    }
}
