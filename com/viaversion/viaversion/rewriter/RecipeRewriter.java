package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public abstract class RecipeRewriter
{
    protected final Protocol protocol;
    protected final Map recipeHandlers;
    
    protected RecipeRewriter(final Protocol protocol) {
        this.recipeHandlers = new HashMap();
        this.protocol = protocol;
    }
    
    public void handle(final PacketWrapper packetWrapper, final String s) throws Exception {
        final RecipeConsumer recipeConsumer = this.recipeHandlers.get(s);
        if (recipeConsumer != null) {
            recipeConsumer.accept(packetWrapper);
        }
    }
    
    public void registerDefaultHandler(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final RecipeRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    final String replace = ((String)packetWrapper.passthrough(Type.STRING)).replace("minecraft:", "");
                    final String s = (String)packetWrapper.passthrough(Type.STRING);
                    this.this$0.handle(packetWrapper, replace);
                    int n = 0;
                    ++n;
                }
            }
        });
    }
    
    protected void rewrite(final Item item) {
        if (this.protocol.getItemRewriter() != null) {
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
    }
    
    @FunctionalInterface
    public interface RecipeConsumer
    {
        void accept(final PacketWrapper p0) throws Exception;
    }
}
