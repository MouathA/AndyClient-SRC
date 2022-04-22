package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.status.*;

public class DefaultErrorHandler implements ErrorHandler
{
    private static final Logger LOGGER;
    private static final int MAX_EXCEPTIONS = 3;
    private static final int EXCEPTION_INTERVAL = 300000;
    private int exceptionCount;
    private long lastException;
    private final Appender appender;
    
    public DefaultErrorHandler(final Appender appender) {
        this.exceptionCount = 0;
        this.appender = appender;
    }
    
    @Override
    public void error(final String s) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.lastException + 300000L < currentTimeMillis || this.exceptionCount++ < 3) {
            DefaultErrorHandler.LOGGER.error(s);
        }
        this.lastException = currentTimeMillis;
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.lastException + 300000L < currentTimeMillis || this.exceptionCount++ < 3) {
            DefaultErrorHandler.LOGGER.error(s, t);
        }
        this.lastException = currentTimeMillis;
        if (!this.appender.ignoreExceptions() && t != null && !(t instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(s, t);
        }
    }
    
    @Override
    public void error(final String s, final LogEvent logEvent, final Throwable t) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.lastException + 300000L < currentTimeMillis || this.exceptionCount++ < 3) {
            DefaultErrorHandler.LOGGER.error(s, t);
        }
        this.lastException = currentTimeMillis;
        if (!this.appender.ignoreExceptions() && t != null && !(t instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(s, t);
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
