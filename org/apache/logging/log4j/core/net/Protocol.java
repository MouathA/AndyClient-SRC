package org.apache.logging.log4j.core.net;

public enum Protocol
{
    TCP("TCP", 0), 
    UDP("UDP", 1);
    
    private static final Protocol[] $VALUES;
    
    private Protocol(final String s, final int n) {
    }
    
    public boolean isEqual(final String s) {
        return this.name().equalsIgnoreCase(s);
    }
    
    static {
        $VALUES = new Protocol[] { Protocol.TCP, Protocol.UDP };
    }
}
