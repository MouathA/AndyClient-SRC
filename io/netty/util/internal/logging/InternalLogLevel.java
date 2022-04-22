package io.netty.util.internal.logging;

public enum InternalLogLevel
{
    TRACE("TRACE", 0), 
    DEBUG("DEBUG", 1), 
    INFO("INFO", 2), 
    WARN("WARN", 3), 
    ERROR("ERROR", 4);
    
    private static final InternalLogLevel[] $VALUES;
    
    private InternalLogLevel(final String s, final int n) {
    }
    
    static {
        $VALUES = new InternalLogLevel[] { InternalLogLevel.TRACE, InternalLogLevel.DEBUG, InternalLogLevel.INFO, InternalLogLevel.WARN, InternalLogLevel.ERROR };
    }
}
