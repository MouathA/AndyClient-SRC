package io.netty.handler.codec.socks;

public enum SocksMessageType
{
    REQUEST("REQUEST", 0), 
    RESPONSE("RESPONSE", 1), 
    UNKNOWN("UNKNOWN", 2);
    
    private static final SocksMessageType[] $VALUES;
    
    private SocksMessageType(final String s, final int n) {
    }
    
    static {
        $VALUES = new SocksMessageType[] { SocksMessageType.REQUEST, SocksMessageType.RESPONSE, SocksMessageType.UNKNOWN };
    }
}
