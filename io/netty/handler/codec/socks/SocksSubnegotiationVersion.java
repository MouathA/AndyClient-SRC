package io.netty.handler.codec.socks;

public enum SocksSubnegotiationVersion
{
    AUTH_PASSWORD("AUTH_PASSWORD", 0, (byte)1), 
    UNKNOWN("UNKNOWN", 1, (byte)(-1));
    
    private final byte b;
    private static final SocksSubnegotiationVersion[] $VALUES;
    
    private SocksSubnegotiationVersion(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksSubnegotiationVersion fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksSubnegotiationVersion valueOf(final byte b) {
        final SocksSubnegotiationVersion[] values = values();
        while (0 < values.length) {
            final SocksSubnegotiationVersion socksSubnegotiationVersion = values[0];
            if (socksSubnegotiationVersion.b == b) {
                return socksSubnegotiationVersion;
            }
            int n = 0;
            ++n;
        }
        return SocksSubnegotiationVersion.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksSubnegotiationVersion[] { SocksSubnegotiationVersion.AUTH_PASSWORD, SocksSubnegotiationVersion.UNKNOWN };
    }
}
