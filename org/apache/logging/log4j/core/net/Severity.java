package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.*;

public enum Severity
{
    EMERG("EMERG", 0, 0), 
    ALERT("ALERT", 1, 1), 
    CRITICAL("CRITICAL", 2, 2), 
    ERROR("ERROR", 3, 3), 
    WARNING("WARNING", 4, 4), 
    NOTICE("NOTICE", 5, 5), 
    INFO("INFO", 6, 6), 
    DEBUG("DEBUG", 7, 7);
    
    private final int code;
    private static final Severity[] $VALUES;
    
    private Severity(final String s, final int n, final int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public boolean isEqual(final String s) {
        return this.name().equalsIgnoreCase(s);
    }
    
    public static Severity getSeverity(final Level level) {
        switch (level) {
            case ALL: {
                return Severity.DEBUG;
            }
            case TRACE: {
                return Severity.DEBUG;
            }
            case DEBUG: {
                return Severity.DEBUG;
            }
            case INFO: {
                return Severity.INFO;
            }
            case WARN: {
                return Severity.WARNING;
            }
            case ERROR: {
                return Severity.ERROR;
            }
            case FATAL: {
                return Severity.ALERT;
            }
            case OFF: {
                return Severity.EMERG;
            }
            default: {
                return Severity.DEBUG;
            }
        }
    }
    
    static {
        $VALUES = new Severity[] { Severity.EMERG, Severity.ALERT, Severity.CRITICAL, Severity.ERROR, Severity.WARNING, Severity.NOTICE, Severity.INFO, Severity.DEBUG };
    }
}
