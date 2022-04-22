package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;

public class Protocol1_9To1_8 extends AbstractProtocol
{
    public static final ValueTransformer FIX_JSON;
    private final EntityRewriter metadataRewriter;
    
    public Protocol1_9To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
        this.metadataRewriter = new MetadataRewriter1_9To1_8(this);
    }
    
    public static JsonElement fixJson(String string) {
        if (string == null || string.equalsIgnoreCase("null")) {
            string = "{\"text\":\"\"}";
        }
        else {
            if ((!string.startsWith("\"") || !string.endsWith("\"")) && (!string.startsWith("{") || !string.endsWith("}"))) {
                return constructJson(string);
            }
            if (string.startsWith("\"") && string.endsWith("\"")) {
                string = "{\"text\":" + string + "}";
            }
        }
        return (JsonElement)GsonUtil.getGson().fromJson(string, JsonObject.class);
    }
    
    private static JsonElement constructJson(final String s) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", s);
        return jsonObject;
    }
    
    public static Item getHandItem(final UserConnection userConnection) {
        return ((HandItemProvider)Via.getManager().getProviders().get(HandItemProvider.class)).getHandItem(userConnection);
    }
    
    public static boolean isSword(final int n) {
        return n == 267 || n == 268 || n == 272 || n == 276 || n == 283;
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() {
            final Protocol1_9To1_8 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_9To1_8$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.isReadable(Type.COMPONENT, 0)) {
                    return;
                }
                packetWrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson((String)packetWrapper.read(Type.STRING)));
            }
        });
        SpawnPackets.register(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
    }
    
    @Override
    public void register(final ViaProviders viaProviders) {
        viaProviders.register(HandItemProvider.class, new HandItemProvider());
        viaProviders.register(CommandBlockProvider.class, new CommandBlockProvider());
        viaProviders.register(EntityIdProvider.class, new EntityIdProvider());
        viaProviders.register(BossBarProvider.class, new BossBarProvider());
        viaProviders.register(MainHandProvider.class, new MainHandProvider());
        viaProviders.register(CompressionProvider.class, new CompressionProvider());
        viaProviders.require(MovementTransmitterProvider.class);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_9(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new MovementTracker());
        userConnection.put(new InventoryTracker());
        userConnection.put(new CommandBlockStorage());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
    
    static {
        FIX_JSON = new ValueTransformer(Type.COMPONENT) {
            public JsonElement transform(final PacketWrapper packetWrapper, final String s) {
                return Protocol1_9To1_8.fixJson(s);
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (String)o);
            }
        };
    }
}
