package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class BaseProtocol extends AbstractProtocol
{
    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundHandshakePackets.CLIENT_INTENTION, new PacketRemapper() {
            final BaseProtocol this$0;
            
            @Override
            public void registerMap() {
                this.handler(BaseProtocol$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.UNSIGNED_SHORT);
                final int intValue2 = (int)packetWrapper.passthrough(Type.VAR_INT);
                final ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
                protocolInfo.setProtocolVersion(intValue);
                final VersionProvider versionProvider = (VersionProvider)Via.getManager().getProviders().get(VersionProvider.class);
                if (versionProvider == null) {
                    packetWrapper.user().setActive(false);
                    return;
                }
                final int closestServerProtocol = versionProvider.getClosestServerProtocol(packetWrapper.user());
                protocolInfo.setServerProtocolVersion(closestServerProtocol);
                List<ProtocolPathEntry> protocolPath = null;
                if (protocolInfo.getProtocolVersion() >= closestServerProtocol || Via.getPlatform().isOldClientsAllowed()) {
                    protocolPath = (List<ProtocolPathEntry>)Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), closestServerProtocol);
                }
                final ProtocolPipeline pipeline = packetWrapper.user().getProtocolInfo().getPipeline();
                if (protocolPath != null) {
                    final ArrayList list = new ArrayList<Protocol>(protocolPath.size());
                    for (final ProtocolPathEntry protocolPathEntry : protocolPath) {
                        list.add(protocolPathEntry.protocol());
                        Via.getManager().getProtocolManager().completeMappingDataLoading(protocolPathEntry.protocol().getClass());
                    }
                    pipeline.add(list);
                    packetWrapper.set(Type.VAR_INT, 0, ProtocolVersion.getProtocol(closestServerProtocol).getOriginalVersion());
                }
                pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(closestServerProtocol));
                if (intValue2 == 1) {
                    protocolInfo.setState(State.STATUS);
                }
                else if (intValue2 == 2) {
                    protocolInfo.setState(State.LOGIN);
                }
            }
        });
    }
    
    @Override
    public boolean isBaseProtocol() {
        return true;
    }
    
    @Override
    public void register(final ViaProviders viaProviders) {
        viaProviders.register(VersionProvider.class, new BaseVersionProvider());
    }
    
    @Override
    public void transform(final Direction direction, final State state, final PacketWrapper packetWrapper) throws Exception {
        super.transform(direction, state, packetWrapper);
        if (direction == Direction.SERVERBOUND && state == State.HANDSHAKE && packetWrapper.getId() != 0) {
            packetWrapper.user().setActive(false);
        }
    }
}
