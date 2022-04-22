package io.netty.handler.codec.socks;

public enum SocksAddressType
{
    IPv4("IPv4", 0, (byte)1), 
    DOMAIN("DOMAIN", 1, (byte)3), 
    IPv6("IPv6", 2, (byte)4), 
    UNKNOWN("UNKNOWN", 3, (byte)(-1));
    
    private final byte b;
    private static final SocksAddressType[] $VALUES;
    
    private SocksAddressType(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksAddressType fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksAddressType valueOf(final byte b) {
        final SocksAddressType[] values = values();
        while (0 < values.length) {
            final SocksAddressType socksAddressType = values[0];
            if (socksAddressType.b == b) {
                return socksAddressType;
            }
            int n = 0;
            ++n;
        }
        return SocksAddressType.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksAddressType[] { SocksAddressType.IPv4, SocksAddressType.DOMAIN, SocksAddressType.IPv6, SocksAddressType.UNKNOWN };
    }
}
