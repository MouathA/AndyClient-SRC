package io.netty.handler.codec.socks;

public enum SocksCmdType
{
    CONNECT("CONNECT", 0, (byte)1), 
    BIND("BIND", 1, (byte)2), 
    UDP("UDP", 2, (byte)3), 
    UNKNOWN("UNKNOWN", 3, (byte)(-1));
    
    private final byte b;
    private static final SocksCmdType[] $VALUES;
    
    private SocksCmdType(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksCmdType fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksCmdType valueOf(final byte b) {
        final SocksCmdType[] values = values();
        while (0 < values.length) {
            final SocksCmdType socksCmdType = values[0];
            if (socksCmdType.b == b) {
                return socksCmdType;
            }
            int n = 0;
            ++n;
        }
        return SocksCmdType.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksCmdType[] { SocksCmdType.CONNECT, SocksCmdType.BIND, SocksCmdType.UDP, SocksCmdType.UNKNOWN };
    }
}
