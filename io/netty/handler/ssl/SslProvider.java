package io.netty.handler.ssl;

public enum SslProvider
{
    JDK("JDK", 0), 
    OPENSSL("OPENSSL", 1);
    
    private static final SslProvider[] $VALUES;
    
    private SslProvider(final String s, final int n) {
    }
    
    static {
        $VALUES = new SslProvider[] { SslProvider.JDK, SslProvider.OPENSSL };
    }
}
