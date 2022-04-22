package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundHandshakePackets implements ServerboundPacketType
{
    CLIENT_INTENTION("CLIENT_INTENTION", 0);
    
    private static final ServerboundHandshakePackets[] $VALUES;
    
    private ServerboundHandshakePackets(final String s, final int n) {
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
        return State.HANDSHAKE;
    }
    
    static {
        $VALUES = new ServerboundHandshakePackets[] { ServerboundHandshakePackets.CLIENT_INTENTION };
    }
}
