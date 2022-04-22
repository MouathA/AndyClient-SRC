package org.apache.logging.log4j;

import java.util.*;

public enum Level
{
    OFF("OFF", 0, 0), 
    FATAL("FATAL", 1, 1), 
    ERROR("ERROR", 2, 2), 
    WARN("WARN", 3, 3), 
    INFO("INFO", 4, 4), 
    DEBUG("DEBUG", 5, 5), 
    TRACE("TRACE", 6, 6), 
    ALL("ALL", 7, Integer.MAX_VALUE);
    
    private final int intLevel;
    private static final Level[] $VALUES;
    
    private Level(final String s, final int n, final int intLevel) {
        this.intLevel = intLevel;
    }
    
    public static Level toLevel(final String s) {
        return toLevel(s, Level.DEBUG);
    }
    
    public static Level toLevel(final String s, final Level level) {
        if (s == null) {
            return level;
        }
        final String upperCase = s.toUpperCase(Locale.ENGLISH);
        final Level[] values = values();
        while (0 < values.length) {
            final Level level2 = values[0];
            if (level2.name().equals(upperCase)) {
                return level2;
            }
            int n = 0;
            ++n;
        }
        return level;
    }
    
    public boolean isAtLeastAsSpecificAs(final Level level) {
        return this.intLevel <= level.intLevel;
    }
    
    public boolean isAtLeastAsSpecificAs(final int n) {
        return this.intLevel <= n;
    }
    
    public boolean lessOrEqual(final Level level) {
        return this.intLevel <= level.intLevel;
    }
    
    public boolean lessOrEqual(final int n) {
        return this.intLevel <= n;
    }
    
    public int intLevel() {
        return this.intLevel;
    }
    
    static {
        $VALUES = new Level[] { Level.OFF, Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE, Level.ALL };
    }
}
