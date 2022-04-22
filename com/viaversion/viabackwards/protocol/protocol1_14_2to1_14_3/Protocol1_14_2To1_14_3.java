package com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.rewriter.*;

public class Protocol1_14_2To1_14_3 extends BackwardsProtocol
{
    public Protocol1_14_2To1_14_3() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() {
            final Protocol1_14_2To1_14_3 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_14_2To1_14_3$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.VAR_INT);
                        while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            }
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.FLOAT);
                            int n = 0;
                            ++n;
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_14.DECLARE_RECIPES, new PacketRemapper((RecipeRewriter)new RecipeRewriter1_14(this)) {
            final RecipeRewriter val$recipeHandler;
            final Protocol1_14_2To1_14_3 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_14_2To1_14_3$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final RecipeRewriter recipeRewriter, final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                while (0 < intValue) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    final String replace = s.replace("minecraft:", "");
                    final String s2 = (String)packetWrapper.read(Type.STRING);
                    if (replace.equals("crafting_special_repairitem")) {
                        int n = 0;
                        ++n;
                    }
                    else {
                        packetWrapper.write(Type.STRING, s);
                        packetWrapper.write(Type.STRING, s2);
                        recipeRewriter.handle(packetWrapper, replace);
                    }
                    int n2 = 0;
                    ++n2;
                }
                packetWrapper.set(Type.VAR_INT, 0, intValue - 0);
            }
        });
    }
}
