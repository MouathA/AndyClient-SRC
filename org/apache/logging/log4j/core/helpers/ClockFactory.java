package org.apache.logging.log4j.core.helpers;

import org.apache.logging.log4j.status.*;

public final class ClockFactory
{
    public static final String PROPERTY_NAME = "log4j.Clock";
    private static final StatusLogger LOGGER;
    
    private ClockFactory() {
    }
    
    public static Clock getClock() {
        return createClock();
    }
    
    private static Clock createClock() {
        final String property = System.getProperty("log4j.Clock");
        if (property == null || "SystemClock".equals(property)) {
            ClockFactory.LOGGER.debug("Using default SystemClock for timestamps");
            return new SystemClock();
        }
        if (CachedClock.class.getName().equals(property) || "CachedClock".equals(property)) {
            ClockFactory.LOGGER.debug("Using specified CachedClock for timestamps");
            return CachedClock.instance();
        }
        if (CoarseCachedClock.class.getName().equals(property) || "CoarseCachedClock".equals(property)) {
            ClockFactory.LOGGER.debug("Using specified CoarseCachedClock for timestamps");
            return CoarseCachedClock.instance();
        }
        final Clock clock = (Clock)Class.forName(property).newInstance();
        ClockFactory.LOGGER.debug("Using {} for timestamps", property);
        return clock;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
