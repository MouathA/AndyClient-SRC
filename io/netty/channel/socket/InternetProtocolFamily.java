package io.netty.channel.socket;

public enum InternetProtocolFamily
{
    IPv4("IPv4", 0), 
    IPv6("IPv6", 1);
    
    private static final InternetProtocolFamily[] $VALUES;
    
    private InternetProtocolFamily(final String s, final int n) {
    }
    
    static {
        $VALUES = new InternetProtocolFamily[] { InternetProtocolFamily.IPv4, InternetProtocolFamily.IPv6 };
    }
}
