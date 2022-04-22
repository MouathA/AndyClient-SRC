package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.*;

public class AppenderLoggingException extends LoggingException
{
    private static final long serialVersionUID = 6545990597472958303L;
    
    public AppenderLoggingException(final String s) {
        super(s);
    }
    
    public AppenderLoggingException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public AppenderLoggingException(final Throwable t) {
        super(t);
    }
}
