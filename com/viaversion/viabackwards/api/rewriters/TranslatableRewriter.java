package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.api.data.*;
import java.util.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.libs.gson.*;

public class TranslatableRewriter extends ComponentRewriter
{
    private static final Map TRANSLATABLES;
    protected final Map newTranslatables;
    
    public static void loadTranslatables() {
        for (final Map.Entry<String, V> entry : VBMappingDataLoader.loadData("translation-mappings.json").entrySet()) {
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            TranslatableRewriter.TRANSLATABLES.put(entry.getKey(), hashMap);
            for (final Map.Entry<String, V> entry2 : ((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
                hashMap.put(entry2.getKey(), ((JsonElement)entry2.getValue()).getAsString());
            }
        }
    }
    
    public TranslatableRewriter(final BackwardsProtocol backwardsProtocol) {
        this(backwardsProtocol, backwardsProtocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }
    
    public TranslatableRewriter(final BackwardsProtocol backwardsProtocol, final String s) {
        super(backwardsProtocol);
        final Map newTranslatables = TranslatableRewriter.TRANSLATABLES.get(s);
        if (newTranslatables == null) {
            ViaBackwards.getPlatform().getLogger().warning("Error loading " + s + " translatables!");
            this.newTranslatables = new HashMap();
        }
        else {
            this.newTranslatables = newTranslatables;
        }
    }
    
    public void registerPing() {
        this.protocol.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    public void registerDisconnect(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    @Override
    public void registerChatMessage(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    public void registerLegacyOpenWindow(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    public void registerOpenWindow(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    public void registerTabList(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    public void registerCombatKill(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TranslatableRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }
    
    @Override
    protected void handleTranslate(final JsonObject jsonObject, final String s) {
        final String s2 = this.newTranslatables.get(s);
        if (s2 != null) {
            jsonObject.addProperty("translate", s2);
        }
    }
    
    static {
        TRANSLATABLES = new HashMap();
    }
}
