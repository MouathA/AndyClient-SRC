package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;

public class AbstractLoggerWrapper extends AbstractLogger
{
    protected final AbstractLogger logger;
    
    public AbstractLoggerWrapper(final AbstractLogger logger, final String s, final MessageFactory messageFactory) {
        super(s, messageFactory);
        this.logger = logger;
    }
    
    @Override
    public void log(final Marker marker, final String s, final Level level, final Message message, final Throwable t) {
        this.logger.log(marker, s, level, message, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s) {
        return this.logger.isEnabled(level, marker, s);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s, final Throwable t) {
        return this.logger.isEnabled(level, marker, s, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s, final Object... array) {
        return this.logger.isEnabled(level, marker, s, array);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.logger.isEnabled(level, marker, o, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.logger.isEnabled(level, marker, message, t);
    }
}
