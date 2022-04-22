package io.netty.handler.codec.socks;

public enum SocksAuthScheme
{
    NO_AUTH("NO_AUTH", 0, (byte)0), 
    AUTH_GSSAPI("AUTH_GSSAPI", 1, (byte)1), 
    AUTH_PASSWORD("AUTH_PASSWORD", 2, (byte)2), 
    UNKNOWN("UNKNOWN", 3, (byte)(-1));
    
    private final byte b;
    private static final SocksAuthScheme[] $VALUES;
    
    private SocksAuthScheme(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksAuthScheme fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksAuthScheme valueOf(final byte b) {
        final SocksAuthScheme[] values = values();
        while (0 < values.length) {
            final SocksAuthScheme socksAuthScheme = values[0];
            if (socksAuthScheme.b == b) {
                return socksAuthScheme;
            }
            int n = 0;
            ++n;
        }
        return SocksAuthScheme.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksAuthScheme[] { SocksAuthScheme.NO_AUTH, SocksAuthScheme.AUTH_GSSAPI, SocksAuthScheme.AUTH_PASSWORD, SocksAuthScheme.UNKNOWN };
    }
}
