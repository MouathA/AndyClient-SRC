package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundLoginPackets implements ClientboundPacketType
{
    LOGIN_DISCONNECT("LOGIN_DISCONNECT", 0), 
    HELLO("HELLO", 1), 
    GAME_PROFILE("GAME_PROFILE", 2), 
    LOGIN_COMPRESSION("LOGIN_COMPRESSION", 3), 
    CUSTOM_QUERY("CUSTOM_QUERY", 4);
    
    private static final ClientboundLoginPackets[] $VALUES;
    
    private ClientboundLoginPackets(final String s, final int n) {
    }
    
    @Override
    public final int getId() {
        return this.ordinal();
    }
    
    @Override
    public final String getName() {
        return this.name();
    }
    
    @Override
    public final State state() {
        return State.LOGIN;
    }
    
    static {
        $VALUES = new ClientboundLoginPackets[] { ClientboundLoginPackets.LOGIN_DISCONNECT, ClientboundLoginPackets.HELLO, ClientboundLoginPackets.GAME_PROFILE, ClientboundLoginPackets.LOGIN_COMPRESSION, ClientboundLoginPackets.CUSTOM_QUERY };
    }
}
