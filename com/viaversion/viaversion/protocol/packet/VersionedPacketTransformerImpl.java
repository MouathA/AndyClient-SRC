package com.viaversion.viaversion.protocol.packet;

import com.viaversion.viaversion.api.protocol.version.*;
import com.google.common.base.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;

public class VersionedPacketTransformerImpl implements VersionedPacketTransformer
{
    private final int inputProtocolVersion;
    private final Class clientboundPacketsClass;
    private final Class serverboundPacketsClass;
    
    public VersionedPacketTransformerImpl(final ProtocolVersion protocolVersion, final Class clientboundPacketsClass, final Class serverboundPacketsClass) {
        Preconditions.checkNotNull(protocolVersion);
        Preconditions.checkArgument(clientboundPacketsClass != null || serverboundPacketsClass != null, (Object)"Either the clientbound or serverbound packets class has to be non-null");
        this.inputProtocolVersion = protocolVersion.getVersion();
        this.clientboundPacketsClass = clientboundPacketsClass;
        this.serverboundPacketsClass = serverboundPacketsClass;
    }
    
    @Override
    public boolean send(final PacketWrapper packetWrapper) throws Exception {
        this.validatePacket(packetWrapper);
        return this.transformAndSendPacket(packetWrapper, true);
    }
    
    @Override
    public boolean send(final UserConnection userConnection, final ClientboundPacketType clientboundPacketType, final Consumer consumer) throws Exception {
        return this.createAndSend(userConnection, clientboundPacketType, consumer);
    }
    
    @Override
    public boolean send(final UserConnection userConnection, final ServerboundPacketType serverboundPacketType, final Consumer consumer) throws Exception {
        return this.createAndSend(userConnection, serverboundPacketType, consumer);
    }
    
    @Override
    public boolean scheduleSend(final PacketWrapper packetWrapper) throws Exception {
        this.validatePacket(packetWrapper);
        return this.transformAndSendPacket(packetWrapper, false);
    }
    
    @Override
    public boolean scheduleSend(final UserConnection userConnection, final ClientboundPacketType clientboundPacketType, final Consumer consumer) throws Exception {
        return this.scheduleCreateAndSend(userConnection, clientboundPacketType, consumer);
    }
    
    @Override
    public boolean scheduleSend(final UserConnection userConnection, final ServerboundPacketType serverboundPacketType, final Consumer consumer) throws Exception {
        return this.scheduleCreateAndSend(userConnection, serverboundPacketType, consumer);
    }
    
    @Override
    public PacketWrapper transform(final PacketWrapper packetWrapper) throws Exception {
        this.validatePacket(packetWrapper);
        this.transformPacket(packetWrapper);
        return packetWrapper.isCancelled() ? null : packetWrapper;
    }
    
    @Override
    public PacketWrapper transform(final UserConnection userConnection, final ClientboundPacketType clientboundPacketType, final Consumer consumer) throws Exception {
        return this.createAndTransform(userConnection, clientboundPacketType, consumer);
    }
    
    @Override
    public PacketWrapper transform(final UserConnection userConnection, final ServerboundPacketType serverboundPacketType, final Consumer consumer) throws Exception {
        return this.createAndTransform(userConnection, serverboundPacketType, consumer);
    }
    
    private void validatePacket(final PacketWrapper packetWrapper) {
        if (packetWrapper.user() == null) {
            throw new IllegalArgumentException("PacketWrapper does not have a targetted UserConnection");
        }
        if (packetWrapper.getPacketType() == null) {
            throw new IllegalArgumentException("PacketWrapper does not have a valid packet type");
        }
        if (packetWrapper.getPacketType().getClass() != ((packetWrapper.getPacketType().direction() == Direction.CLIENTBOUND) ? this.clientboundPacketsClass : this.serverboundPacketsClass)) {
            throw new IllegalArgumentException("PacketWrapper packet type is of the wrong packet class");
        }
    }
    
    private boolean transformAndSendPacket(final PacketWrapper packetWrapper, final boolean b) throws Exception {
        this.transformPacket(packetWrapper);
        if (packetWrapper.isCancelled()) {
            return false;
        }
        if (b) {
            if (packetWrapper.getPacketType().direction() == Direction.CLIENTBOUND) {
                packetWrapper.sendRaw();
            }
            else {
                packetWrapper.sendToServerRaw();
            }
        }
        else if (packetWrapper.getPacketType().direction() == Direction.CLIENTBOUND) {
            packetWrapper.scheduleSendRaw();
        }
        else {
            packetWrapper.scheduleSendToServerRaw();
        }
        return true;
    }
    
    private void transformPacket(final PacketWrapper packetWrapper) throws Exception {
        final PacketType packetType = packetWrapper.getPacketType();
        final UserConnection user = packetWrapper.user();
        final boolean b = packetType.direction() == Direction.CLIENTBOUND;
        final int n = b ? this.inputProtocolVersion : user.getProtocolInfo().getServerProtocolVersion();
        final int n2 = b ? user.getProtocolInfo().getProtocolVersion() : this.inputProtocolVersion;
        final List protocolPath = Via.getManager().getProtocolManager().getProtocolPath(n2, n);
        ArrayList list = null;
        if (protocolPath != null) {
            list = new ArrayList<Protocol>(protocolPath.size());
            final Iterator<ProtocolPathEntry> iterator = protocolPath.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().protocol());
            }
        }
        else if (n != n2) {
            throw new RuntimeException("No protocol path between client version " + n2 + " and server version " + n);
        }
        if (list != null) {
            packetWrapper.resetReader();
            packetWrapper.apply(packetType.direction(), State.PLAY, 0, list, b);
        }
    }
    
    private boolean createAndSend(final UserConnection userConnection, final PacketType packetType, final Consumer consumer) throws Exception {
        final PacketWrapper create = PacketWrapper.create(packetType, userConnection);
        consumer.accept(create);
        return this.send(create);
    }
    
    private boolean scheduleCreateAndSend(final UserConnection userConnection, final PacketType packetType, final Consumer consumer) throws Exception {
        final PacketWrapper create = PacketWrapper.create(packetType, userConnection);
        consumer.accept(create);
        return this.scheduleSend(create);
    }
    
    private PacketWrapper createAndTransform(final UserConnection userConnection, final PacketType packetType, final Consumer consumer) throws Exception {
        final PacketWrapper create = PacketWrapper.create(packetType, userConnection);
        consumer.accept(create);
        return this.transform(create);
    }
}
