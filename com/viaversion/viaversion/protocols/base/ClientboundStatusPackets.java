package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundStatusPackets implements ClientboundPacketType
{
    STATUS_RESPONSE("STATUS_RESPONSE", 0), 
    PONG_RESPONSE("PONG_RESPONSE", 1);
    
    private static final ClientboundStatusPackets[] $VALUES;
    
    private ClientboundStatusPackets(final String s, final int n) {
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
        $VALUES = new ClientboundStatusPackets[] { ClientboundStatusPackets.STATUS_RESPONSE, ClientboundStatusPackets.PONG_RESPONSE };
    }
}
