package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.logging.*;
import com.google.common.base.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.util.*;
import io.netty.util.concurrent.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;

public class BaseProtocol1_7 extends AbstractProtocol
{
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundStatusPackets.STATUS_RESPONSE, new PacketRemapper() {
            final BaseProtocol1_7 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final BaseProtocol1_7$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
                        JsonElement jsonElement = (JsonElement)GsonUtil.getGson().fromJson((String)packetWrapper.get(Type.STRING, 0), JsonElement.class);
                        JsonObject asJsonObject;
                        if (jsonElement.isJsonObject()) {
                            if (jsonElement.getAsJsonObject().has("version")) {
                                asJsonObject = jsonElement.getAsJsonObject().get("version").getAsJsonObject();
                                if (asJsonObject.has("protocol")) {
                                    asJsonObject.get("protocol").getAsLong().intValue();
                                }
                            }
                            else {
                                jsonElement.getAsJsonObject().add("version", asJsonObject = new JsonObject());
                            }
                        }
                        else {
                            jsonElement = new JsonObject();
                            jsonElement.getAsJsonObject().add("version", asJsonObject = new JsonObject());
                        }
                        if (Via.getConfig().isSendSupportedVersions()) {
                            asJsonObject.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
                        }
                        if (!Via.getAPI().getServerVersion().isKnown()) {
                            ((ProtocolManagerImpl)Via.getManager().getProtocolManager()).setServerProtocol(new ServerProtocolVersionSingleton(ProtocolVersion.getProtocol(0).getVersion()));
                        }
                        final VersionProvider versionProvider = (VersionProvider)Via.getManager().getProviders().get(VersionProvider.class);
                        if (versionProvider == null) {
                            packetWrapper.user().setActive(false);
                            return;
                        }
                        final int closestServerProtocol = versionProvider.getClosestServerProtocol(packetWrapper.user());
                        List protocolPath = null;
                        if (protocolInfo.getProtocolVersion() >= closestServerProtocol || Via.getPlatform().isOldClientsAllowed()) {
                            protocolPath = Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), closestServerProtocol);
                        }
                        if (protocolPath != null) {
                            if (closestServerProtocol == 0 || !false) {
                                asJsonObject.addProperty("protocol", ProtocolVersion.getProtocol(protocolInfo.getProtocolVersion()).getOriginalVersion());
                            }
                        }
                        else {
                            packetWrapper.user().setActive(false);
                        }
                        if (Via.getConfig().blockedProtocolVersions().contains(protocolInfo.getProtocolVersion())) {
                            asJsonObject.addProperty("protocol", -1);
                        }
                        packetWrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(jsonElement));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundLoginPackets.GAME_PROFILE, new PacketRemapper() {
            final BaseProtocol1_7 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BaseProtocol1_7$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
                        protocolInfo.setState(State.PLAY);
                        protocolInfo.setUuid(this.this$1.this$0.passthroughLoginUUID(packetWrapper));
                        final String username = (String)packetWrapper.passthrough(Type.STRING);
                        protocolInfo.setUsername(username);
                        Via.getManager().getConnectionManager().onLoginSuccess(packetWrapper.user());
                        if (!protocolInfo.getPipeline().hasNonBaseProtocols()) {
                            packetWrapper.user().setActive(false);
                        }
                        if (Via.getManager().isDebug()) {
                            Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[] { username, protocolInfo.getProtocolVersion(), Joiner.on(", ").join(protocolInfo.getPipeline().pipes(), ", ", new Object[0]) });
                        }
                    }
                });
            }
        });
        this.registerServerbound(ServerboundLoginPackets.HELLO, new PacketRemapper() {
            final BaseProtocol1_7 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BaseProtocol1_7$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (Via.getConfig().blockedProtocolVersions().contains(packetWrapper.user().getProtocolInfo().getProtocolVersion())) {
                            if (!packetWrapper.user().getChannel().isOpen()) {
                                return;
                            }
                            if (!packetWrapper.user().shouldApplyBlockProtocol()) {
                                return;
                            }
                            final PacketWrapper create = PacketWrapper.create(ClientboundLoginPackets.LOGIN_DISCONNECT, packetWrapper.user());
                            Protocol1_9To1_8.FIX_JSON.write(create, ChatColorUtil.translateAlternateColorCodes(Via.getConfig().getBlockedDisconnectMsg()));
                            packetWrapper.cancel();
                            create.sendFuture(BaseProtocol.class).addListener(BaseProtocol1_7$3$1::lambda$handle$0);
                        }
                    }
                    
                    private static void lambda$handle$0(final PacketWrapper packetWrapper, final Future future) throws Exception {
                        packetWrapper.user().getChannel().close();
                    }
                });
            }
        });
    }
    
    @Override
    public boolean isBaseProtocol() {
        return true;
    }
    
    public static String addDashes(final String s) {
        final StringBuilder sb = new StringBuilder(s);
        sb.insert(20, '-');
        sb.insert(16, '-');
        sb.insert(12, '-');
        sb.insert(8, '-');
        return sb.toString();
    }
    
    protected UUID passthroughLoginUUID(final PacketWrapper packetWrapper) throws Exception {
        String addDashes = (String)packetWrapper.passthrough(Type.STRING);
        if (addDashes.length() == 32) {
            addDashes = addDashes(addDashes);
        }
        return UUID.fromString(addDashes);
    }
}
