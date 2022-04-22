package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundLoginPackets implements ServerboundPacketType
{
    HELLO("HELLO", 0), 
    ENCRYPTION_KEY("ENCRYPTION_KEY", 1), 
    CUSTOM_QUERY("CUSTOM_QUERY", 2);
    
    private static final ServerboundLoginPackets[] $VALUES;
    
    private ServerboundLoginPackets(final String s, final int n) {
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
        $VALUES = new ServerboundLoginPackets[] { ServerboundLoginPackets.HELLO, ServerboundLoginPackets.ENCRYPTION_KEY, ServerboundLoginPackets.CUSTOM_QUERY };
    }
}
