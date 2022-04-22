package io.netty.handler.codec.socks;

public enum SocksRequestType
{
    INIT("INIT", 0), 
    AUTH("AUTH", 1), 
    CMD("CMD", 2), 
    UNKNOWN("UNKNOWN", 3);
    
    private static final SocksRequestType[] $VALUES;
    
    private SocksRequestType(final String s, final int n) {
    }
    
    static {
        $VALUES = new SocksRequestType[] { SocksRequestType.INIT, SocksRequestType.AUTH, SocksRequestType.CMD, SocksRequestType.UNKNOWN };
    }
}
