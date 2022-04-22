package io.netty.handler.codec.socks;

public enum SocksAuthStatus
{
    SUCCESS("SUCCESS", 0, (byte)0), 
    FAILURE("FAILURE", 1, (byte)(-1));
    
    private final byte b;
    private static final SocksAuthStatus[] $VALUES;
    
    private SocksAuthStatus(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksAuthStatus fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksAuthStatus valueOf(final byte b) {
        final SocksAuthStatus[] values = values();
        while (0 < values.length) {
            final SocksAuthStatus socksAuthStatus = values[0];
            if (socksAuthStatus.b == b) {
                return socksAuthStatus;
            }
            int n = 0;
            ++n;
        }
        return SocksAuthStatus.FAILURE;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksAuthStatus[] { SocksAuthStatus.SUCCESS, SocksAuthStatus.FAILURE };
    }
}
