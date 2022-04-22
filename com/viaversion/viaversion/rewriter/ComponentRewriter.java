package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public class ComponentRewriter
{
    protected final Protocol protocol;
    
    public ComponentRewriter(final Protocol protocol) {
        this.protocol = protocol;
    }
    
    public ComponentRewriter() {
        this.protocol = null;
    }
    
    public void registerComponentPacket(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final ComponentRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    @Deprecated
    public void registerChatMessage(final ClientboundPacketType clientboundPacketType) {
        this.registerComponentPacket(clientboundPacketType);
    }
    
    public void registerBossBar(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final ComponentRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue == 0 || intValue == 3) {
                    this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                }
            }
        });
    }
    
    public void registerCombatEvent(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final ComponentRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.passthrough(Type.VAR_INT) == 2) {
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.INT);
                    this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                }
            }
        });
    }
    
    public void registerTitle(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final ComponentRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                if (intValue >= 0 && intValue <= 2) {
                    this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                }
            }
        });
    }
    
    public JsonElement processText(final String s) {
        final JsonElement string = JsonParser.parseString(s);
        this.processText(string);
        return string;
    }
    
    public void processText(final JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return;
        }
        if (jsonElement.isJsonArray()) {
            this.processAsArray(jsonElement);
            return;
        }
        if (jsonElement.isJsonPrimitive()) {
            this.handleText(jsonElement.getAsJsonPrimitive());
            return;
        }
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive asJsonPrimitive = asJsonObject.getAsJsonPrimitive("text");
        if (asJsonPrimitive != null) {
            this.handleText(asJsonPrimitive);
        }
        final JsonElement value = asJsonObject.get("translate");
        if (value != null) {
            this.handleTranslate(asJsonObject, value.getAsString());
            final JsonElement value2 = asJsonObject.get("with");
            if (value2 != null) {
                this.processAsArray(value2);
            }
        }
        final JsonElement value3 = asJsonObject.get("extra");
        if (value3 != null) {
            this.processAsArray(value3);
        }
        final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("hoverEvent");
        if (asJsonObject2 != null) {
            this.handleHoverEvent(asJsonObject2);
        }
    }
    
    protected void handleText(final JsonPrimitive jsonPrimitive) {
    }
    
    protected void handleTranslate(final JsonObject jsonObject, final String s) {
    }
    
    protected void handleHoverEvent(final JsonObject jsonObject) {
        final String asString = jsonObject.getAsJsonPrimitive("action").getAsString();
        if (asString.equals("show_text")) {
            final JsonElement value = jsonObject.get("value");
            this.processText((value != null) ? value : jsonObject.get("contents"));
        }
        else if (asString.equals("show_entity")) {
            final JsonObject asJsonObject = jsonObject.getAsJsonObject("contents");
            if (asJsonObject != null) {
                this.processText(asJsonObject.get("name"));
            }
        }
    }
    
    private void processAsArray(final JsonElement jsonElement) {
        final Iterator iterator = jsonElement.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            this.processText(iterator.next());
        }
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
}
