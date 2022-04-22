package io.netty.handler.logging;

import io.netty.util.internal.logging.*;

public enum LogLevel
{
    TRACE("TRACE", 0, InternalLogLevel.TRACE), 
    DEBUG("DEBUG", 1, InternalLogLevel.DEBUG), 
    INFO("INFO", 2, InternalLogLevel.INFO), 
    WARN("WARN", 3, InternalLogLevel.WARN), 
    ERROR("ERROR", 4, InternalLogLevel.ERROR);
    
    private final InternalLogLevel internalLevel;
    private static final LogLevel[] $VALUES;
    
    private LogLevel(final String s, final int n, final InternalLogLevel internalLevel) {
        this.internalLevel = internalLevel;
    }
    
    InternalLogLevel toInternalLevel() {
        return this.internalLevel;
    }
    
    static {
        $VALUES = new LogLevel[] { LogLevel.TRACE, LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR };
    }
}
