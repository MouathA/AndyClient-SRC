package io.netty.handler.codec.socks;

public enum SocksCmdStatus
{
    SUCCESS("SUCCESS", 0, (byte)0), 
    FAILURE("FAILURE", 1, (byte)1), 
    FORBIDDEN("FORBIDDEN", 2, (byte)2), 
    NETWORK_UNREACHABLE("NETWORK_UNREACHABLE", 3, (byte)3), 
    HOST_UNREACHABLE("HOST_UNREACHABLE", 4, (byte)4), 
    REFUSED("REFUSED", 5, (byte)5), 
    TTL_EXPIRED("TTL_EXPIRED", 6, (byte)6), 
    COMMAND_NOT_SUPPORTED("COMMAND_NOT_SUPPORTED", 7, (byte)7), 
    ADDRESS_NOT_SUPPORTED("ADDRESS_NOT_SUPPORTED", 8, (byte)8), 
    UNASSIGNED("UNASSIGNED", 9, (byte)(-1));
    
    private final byte b;
    private static final SocksCmdStatus[] $VALUES;
    
    private SocksCmdStatus(final String s, final int n, final byte b) {
        this.b = b;
    }
    
    @Deprecated
    public static SocksCmdStatus fromByte(final byte b) {
        return valueOf(b);
    }
    
    public static SocksCmdStatus valueOf(final byte b) {
        final SocksCmdStatus[] values = values();
        while (0 < values.length) {
            final SocksCmdStatus socksCmdStatus = values[0];
            if (socksCmdStatus.b == b) {
                return socksCmdStatus;
            }
            int n = 0;
            ++n;
        }
        return SocksCmdStatus.UNASSIGNED;
    }
    
    public byte byteValue() {
        return this.b;
    }
    
    static {
        $VALUES = new SocksCmdStatus[] { SocksCmdStatus.SUCCESS, SocksCmdStatus.FAILURE, SocksCmdStatus.FORBIDDEN, SocksCmdStatus.NETWORK_UNREACHABLE, SocksCmdStatus.HOST_UNREACHABLE, SocksCmdStatus.REFUSED, SocksCmdStatus.TTL_EXPIRED, SocksCmdStatus.COMMAND_NOT_SUPPORTED, SocksCmdStatus.ADDRESS_NOT_SUPPORTED, SocksCmdStatus.UNASSIGNED };
    }
}
