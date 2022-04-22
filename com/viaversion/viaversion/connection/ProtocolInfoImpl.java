package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.version.*;

public class ProtocolInfoImpl implements ProtocolInfo
{
    private final UserConnection connection;
    private State state;
    private int protocolVersion;
    private int serverProtocolVersion;
    private String username;
    private UUID uuid;
    private ProtocolPipeline pipeline;
    
    public ProtocolInfoImpl(final UserConnection connection) {
        this.state = State.HANDSHAKE;
        this.protocolVersion = -1;
        this.serverProtocolVersion = -1;
        this.connection = connection;
    }
    
    @Override
    public State getState() {
        return this.state;
    }
    
    @Override
    public void setState(final State state) {
        this.state = state;
    }
    
    @Override
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    @Override
    public void setProtocolVersion(final int n) {
        this.protocolVersion = ProtocolVersion.getProtocol(n).getVersion();
    }
    
    @Override
    public int getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }
    
    @Override
    public void setServerProtocolVersion(final int n) {
        this.serverProtocolVersion = ProtocolVersion.getProtocol(n).getVersion();
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Override
    public UUID getUuid() {
        return this.uuid;
    }
    
    @Override
    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
    
    @Override
    public ProtocolPipeline getPipeline() {
        return this.pipeline;
    }
    
    @Override
    public void setPipeline(final ProtocolPipeline pipeline) {
        this.pipeline = pipeline;
    }
    
    @Override
    public UserConnection getUser() {
        return this.connection;
    }
    
    @Override
    public String toString() {
        return "ProtocolInfo{state=" + this.state + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + '\'' + ", uuid=" + this.uuid + '}';
    }
}
