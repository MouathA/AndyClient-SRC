package com.viaversion.viaversion;

import com.viaversion.viaversion.legacy.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.api.legacy.*;

public abstract class ViaAPIBase implements ViaAPI
{
    private final LegacyAPI legacy;
    
    public ViaAPIBase() {
        this.legacy = new LegacyAPI();
    }
    
    @Override
    public ServerProtocolVersion getServerVersion() {
        return Via.getManager().getProtocolManager().getServerProtocolVersion();
    }
    
    @Override
    public int getPlayerVersion(final UUID uuid) {
        final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        return (connectedClient != null) ? connectedClient.getProtocolInfo().getProtocolVersion() : -1;
    }
    
    @Override
    public String getVersion() {
        return Via.getPlatform().getPluginVersion();
    }
    
    @Override
    public boolean isInjected(final UUID uuid) {
        return Via.getManager().getConnectionManager().isClientConnected(uuid);
    }
    
    @Override
    public UserConnection getConnection(final UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }
    
    @Override
    public void sendRawPacket(final UUID uuid, final ByteBuf byteBuf) throws IllegalArgumentException {
        if (!this.isInjected(uuid)) {
            throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
        }
        Via.getManager().getConnectionManager().getConnectedClient(uuid).scheduleSendRawPacket(byteBuf);
    }
    
    @Override
    public SortedSet getSupportedVersions() {
        final TreeSet set = new TreeSet((SortedSet<E>)Via.getManager().getProtocolManager().getSupportedVersions());
        set.removeIf(Via.getPlatform().getConf().blockedProtocolVersions()::contains);
        return set;
    }
    
    @Override
    public SortedSet getFullSupportedVersions() {
        return Via.getManager().getProtocolManager().getSupportedVersions();
    }
    
    @Override
    public LegacyViaAPI legacyAPI() {
        return this.legacy;
    }
}
