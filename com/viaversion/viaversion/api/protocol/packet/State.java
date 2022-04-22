package com.viaversion.viaversion.api.protocol.packet;

public enum State
{
    HANDSHAKE("HANDSHAKE", 0), 
    STATUS("STATUS", 1), 
    LOGIN("LOGIN", 2), 
    PLAY("PLAY", 3);
    
    private static final State[] $VALUES;
    
    private State(final String s, final int n) {
    }
    
    static {
        $VALUES = new State[] { State.HANDSHAKE, State.STATUS, State.LOGIN, State.PLAY };
    }
}
