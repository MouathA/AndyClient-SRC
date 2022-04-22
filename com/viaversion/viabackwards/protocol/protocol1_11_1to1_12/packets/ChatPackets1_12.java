package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class ChatPackets1_12 extends RewriterBase
{
    private final ComponentRewriter componentRewriter;
    
    public ChatPackets1_12(final Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
        this.componentRewriter = new ComponentRewriter() {
            final ChatPackets1_12 this$0;
            
            @Override
            protected void handleTranslate(final JsonObject jsonObject, final String s) {
                final String value = AdvancementTranslations.get(s);
                if (value != null) {
                    jsonObject.addProperty("translate", value);
                }
            }
        };
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.CHAT_MESSAGE, new PacketRemapper() {
            final ChatPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ChatPackets1_12.access$000(this.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    static ComponentRewriter access$000(final ChatPackets1_12 chatPackets1_12) {
        return chatPackets1_12.componentRewriter;
    }
}
