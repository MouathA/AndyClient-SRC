package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.debug.*;
import java.util.logging.*;
import com.viaversion.viaversion.api.platform.*;

public class ProtocolPipelineImpl extends AbstractSimpleProtocol implements ProtocolPipeline
{
    private final UserConnection userConnection;
    private List protocolList;
    private Set protocolSet;
    
    public ProtocolPipelineImpl(final UserConnection userConnection) {
        this.userConnection = userConnection;
        userConnection.getProtocolInfo().setPipeline(this);
        this.registerPackets();
    }
    
    @Override
    protected void registerPackets() {
        this.protocolList = new CopyOnWriteArrayList();
        this.protocolSet = Sets.newSetFromMap(new ConcurrentHashMap());
        final Protocol baseProtocol = Via.getManager().getProtocolManager().getBaseProtocol();
        this.protocolList.add(baseProtocol);
        this.protocolSet.add(baseProtocol.getClass());
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
    }
    
    @Override
    public void add(final Protocol protocol) {
        this.protocolList.add(protocol);
        this.protocolSet.add(protocol.getClass());
        protocol.init(this.userConnection);
        if (!protocol.isBaseProtocol()) {
            this.moveBaseProtocolsToTail();
        }
    }
    
    @Override
    public void add(final Collection collection) {
        this.protocolList.addAll(collection);
        for (final Protocol protocol : collection) {
            protocol.init(this.userConnection);
            this.protocolSet.add(protocol.getClass());
        }
        this.moveBaseProtocolsToTail();
    }
    
    private void moveBaseProtocolsToTail() {
        List<Protocol> list = null;
        for (final Protocol protocol : this.protocolList) {
            if (protocol.isBaseProtocol()) {
                if (list == null) {
                    list = new ArrayList<Protocol>();
                }
                list.add(protocol);
            }
        }
        if (list != null) {
            this.protocolList.removeAll(list);
            this.protocolList.addAll(list);
        }
    }
    
    @Override
    public void transform(final Direction direction, final State state, final PacketWrapper packetWrapper) throws Exception {
        final int id = packetWrapper.getId();
        packetWrapper.apply(direction, state, 0, this.protocolList, direction == Direction.CLIENTBOUND);
        super.transform(direction, state, packetWrapper);
        final DebugHandler debugHandler = Via.getManager().debugHandler();
        if (debugHandler.enabled() && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper)) {
            this.logPacket(direction, state, packetWrapper, id);
        }
    }
    
    private void logPacket(final Direction direction, final State state, final PacketWrapper packetWrapper, final int n) {
        final int protocolVersion = this.userConnection.getProtocolInfo().getProtocolVersion();
        final ViaPlatform platform = Via.getPlatform();
        final String username = packetWrapper.user().getProtocolInfo().getUsername();
        platform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} (0x{4}) -> {5} (0x{6}) [{7}] {8}", new Object[] { (username != null) ? (username + " ") : "", direction, state, n, Integer.toHexString(n), packetWrapper.getId(), Integer.toHexString(packetWrapper.getId()), Integer.toString(protocolVersion), packetWrapper });
    }
    
    @Override
    public boolean contains(final Class clazz) {
        return this.protocolSet.contains(clazz);
    }
    
    @Override
    public Protocol getProtocol(final Class clazz) {
        for (final Protocol protocol : this.protocolList) {
            if (protocol.getClass() == clazz) {
                return protocol;
            }
        }
        return null;
    }
    
    @Override
    public List pipes() {
        return this.protocolList;
    }
    
    @Override
    public boolean hasNonBaseProtocols() {
        final Iterator<Protocol> iterator = this.protocolList.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isBaseProtocol()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void cleanPipes() {
        this.registerPackets();
    }
}
