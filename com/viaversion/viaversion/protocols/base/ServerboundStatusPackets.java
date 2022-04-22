package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundStatusPackets implements ServerboundPacketType
{
    STATUS_REQUEST("STATUS_REQUEST", 0), 
    PING_REQUEST("PING_REQUEST", 1);
    
    private static final ServerboundStatusPackets[] $VALUES;
    
    private ServerboundStatusPackets(final String s, final int n) {
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
        return State.STATUS;
    }
    
    static {
        $VALUES = new ServerboundStatusPackets[] { ServerboundStatusPackets.STATUS_REQUEST, ServerboundStatusPackets.PING_REQUEST };
    }
}
