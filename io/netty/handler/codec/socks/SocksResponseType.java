package io.netty.handler.codec.socks;

public enum SocksResponseType
{
    INIT("INIT", 0), 
    AUTH("AUTH", 1), 
    CMD("CMD", 2), 
    UNKNOWN("UNKNOWN", 3);
    
    private static final SocksResponseType[] $VALUES;
    
    private SocksResponseType(final String s, final int n) {
    }
    
    static {
        $VALUES = new SocksResponseType[] { SocksResponseType.INIT, SocksResponseType.AUTH, SocksResponseType.CMD, SocksResponseType.UNKNOWN };
    }
}
