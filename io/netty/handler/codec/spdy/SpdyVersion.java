package io.netty.handler.codec.spdy;

public enum SpdyVersion
{
    SPDY_3_1("SPDY_3_1", 0, 3, 1);
    
    private final int version;
    private final int minorVersion;
    private static final SpdyVersion[] $VALUES;
    
    private SpdyVersion(final String s, final int n, final int version, final int minorVersion) {
        this.version = version;
        this.minorVersion = minorVersion;
    }
    
    int getVersion() {
        return this.version;
    }
    
    int getMinorVersion() {
        return this.minorVersion;
    }
    
    static {
        $VALUES = new SpdyVersion[] { SpdyVersion.SPDY_3_1 };
    }
}
