package io.netty.handler.codec.socks;

public enum SocksProtocolVersion
{
    SOCKS4a("SOCKS4a", 0, (byte)4), 
    SOCKS5("SOCKS5", 1, (byte)5), 
    UNKNOWN("UNKNOWN", 2, (byte)(-1));
    
    private final byte b;
    private static final SocksProtocolVersion[] $VALUES;
    
    private SocksProtocolVersion(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksProtocolVersion fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksProtocolVersion valueOf(final byte b) {
        final SocksProtocolVersion[] values = values();
        while (0 < values.length) {
            final SocksProtocolVersion socksProtocolVersion = values[0];
            if (socksProtocolVersion.b == b) {
                return socksProtocolVersion;
            }
            int n = 0;
            ++n;
        }
        return SocksProtocolVersion.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksProtocolVersion[] { SocksProtocolVersion.SOCKS4a, SocksProtocolVersion.SOCKS5, SocksProtocolVersion.UNKNOWN };
    }
}
