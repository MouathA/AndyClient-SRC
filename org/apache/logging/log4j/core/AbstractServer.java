package org.apache.logging.log4j.core;

import org.apache.logging.log4j.*;

public class AbstractServer
{
    private final LoggerContext context;
    
    protected AbstractServer() {
        this.context = (LoggerContext)LogManager.getContext(false);
    }
    
    protected void log(final LogEvent logEvent) {
        final Logger logger = this.context.getLogger(logEvent.getLoggerName());
        if (logger.config.filter(logEvent.getLevel(), logEvent.getMarker(), logEvent.getMessage(), logEvent.getThrown())) {
            logger.config.logEvent(logEvent);
        }
    }
}
