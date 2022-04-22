package org.apache.logging.log4j;

import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.message.*;

public final class EventLogger
{
    private static final String NAME = "EventLogger";
    public static final Marker EVENT_MARKER;
    private static final String FQCN;
    private static AbstractLoggerWrapper loggerWrapper;
    
    private EventLogger() {
    }
    
    public static void logEvent(final StructuredDataMessage structuredDataMessage) {
        EventLogger.loggerWrapper.log(EventLogger.EVENT_MARKER, EventLogger.FQCN, Level.OFF, structuredDataMessage, null);
    }
    
    public static void logEvent(final StructuredDataMessage structuredDataMessage, final Level level) {
        EventLogger.loggerWrapper.log(EventLogger.EVENT_MARKER, EventLogger.FQCN, level, structuredDataMessage, null);
    }
    
    static {
        EVENT_MARKER = MarkerManager.getMarker("EVENT");
        FQCN = EventLogger.class.getName();
        final Logger logger = LogManager.getLogger("EventLogger");
        if (!(logger instanceof AbstractLogger)) {
            throw new LoggingException("Logger returned must be based on AbstractLogger");
        }
        EventLogger.loggerWrapper = new AbstractLoggerWrapper((AbstractLogger)logger, "EventLogger", null);
    }
}
