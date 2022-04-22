package io.netty.handler.codec.spdy;

public class SpdySessionStatus implements Comparable
{
    public static final SpdySessionStatus OK;
    public static final SpdySessionStatus PROTOCOL_ERROR;
    public static final SpdySessionStatus INTERNAL_ERROR;
    private final int code;
    private final String statusPhrase;
    
    public static SpdySessionStatus valueOf(final int n) {
        switch (n) {
            case 0: {
                return SpdySessionStatus.OK;
            }
            case 1: {
                return SpdySessionStatus.PROTOCOL_ERROR;
            }
            case 2: {
                return SpdySessionStatus.INTERNAL_ERROR;
            }
            default: {
                return new SpdySessionStatus(n, "UNKNOWN (" + n + ')');
            }
        }
    }
    
    public SpdySessionStatus(final int code, final String statusPhrase) {
        if (statusPhrase == null) {
            throw new NullPointerException("statusPhrase");
        }
        this.code = code;
        this.statusPhrase = statusPhrase;
    }
    
    public int code() {
        return this.code;
    }
    
    public String statusPhrase() {
        return this.statusPhrase;
    }
    
    @Override
    public int hashCode() {
        return this.code();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof SpdySessionStatus && this.code() == ((SpdySessionStatus)o).code();
    }
    
    @Override
    public String toString() {
        return this.statusPhrase();
    }
    
    public int compareTo(final SpdySessionStatus spdySessionStatus) {
        return this.code() - spdySessionStatus.code();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((SpdySessionStatus)o);
    }
    
    static {
        OK = new SpdySessionStatus(0, "OK");
        PROTOCOL_ERROR = new SpdySessionStatus(1, "PROTOCOL_ERROR");
        INTERNAL_ERROR = new SpdySessionStatus(2, "INTERNAL_ERROR");
    }
}
