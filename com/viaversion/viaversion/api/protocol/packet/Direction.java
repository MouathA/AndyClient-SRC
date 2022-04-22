package com.viaversion.viaversion.api.protocol.packet;

public enum Direction
{
    CLIENTBOUND("CLIENTBOUND", 0), 
    SERVERBOUND("SERVERBOUND", 1);
    
    private static final Direction[] $VALUES;
    
    private Direction(final String s, final int n) {
    }
    
    static {
        $VALUES = new Direction[] { Direction.CLIENTBOUND, Direction.SERVERBOUND };
    }
}
