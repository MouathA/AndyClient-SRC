package com.viaversion.viaversion.connection;

import java.util.concurrent.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import io.netty.util.concurrent.*;
import java.util.*;
import io.netty.channel.*;

public class ConnectionManagerImpl implements ConnectionManager
{
    protected final Map clients;
    protected final Set connections;
    
    public ConnectionManagerImpl() {
        this.clients = new ConcurrentHashMap();
        this.connections = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
    }
    
    @Override
    public void onLoginSuccess(final UserConnection userConnection) {
        Objects.requireNonNull(userConnection, "connection is null!");
        this.connections.add(userConnection);
        if (this.isFrontEnd(userConnection)) {
            final UUID uuid = userConnection.getProtocolInfo().getUuid();
            if (this.clients.put(uuid, userConnection) != null) {
                Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + uuid + ")");
            }
        }
        if (userConnection.getChannel() != null) {
            userConnection.getChannel().closeFuture().addListener((GenericFutureListener)this::lambda$onLoginSuccess$0);
        }
    }
    
    @Override
    public void onDisconnect(final UserConnection userConnection) {
        Objects.requireNonNull(userConnection, "connection is null!");
        this.connections.remove(userConnection);
        if (this.isFrontEnd(userConnection)) {
            this.clients.remove(userConnection.getProtocolInfo().getUuid());
        }
    }
    
    @Override
    public Map getConnectedClients() {
        return Collections.unmodifiableMap((Map<?, ?>)this.clients);
    }
    
    @Override
    public UserConnection getConnectedClient(final UUID uuid) {
        return this.clients.get(uuid);
    }
    
    @Override
    public UUID getConnectedClientId(final UserConnection userConnection) {
        if (userConnection.getProtocolInfo() == null) {
            return null;
        }
        final UUID uuid = userConnection.getProtocolInfo().getUuid();
        if (userConnection.equals(this.clients.get(uuid))) {
            return uuid;
        }
        return null;
    }
    
    @Override
    public Set getConnections() {
        return Collections.unmodifiableSet((Set<?>)this.connections);
    }
    
    @Override
    public boolean isClientConnected(final UUID uuid) {
        return this.clients.containsKey(uuid);
    }
    
    private void lambda$onLoginSuccess$0(final UserConnection userConnection, final ChannelFuture channelFuture) throws Exception {
        this.onDisconnect(userConnection);
    }
}
