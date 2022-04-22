package org.apache.logging.log4j.core.async;

import java.net.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;

public class AsyncLoggerContext extends LoggerContext
{
    public AsyncLoggerContext(final String s) {
        super(s);
    }
    
    public AsyncLoggerContext(final String s, final Object o) {
        super(s, o);
    }
    
    public AsyncLoggerContext(final String s, final Object o, final URI uri) {
        super(s, o, uri);
    }
    
    public AsyncLoggerContext(final String s, final Object o, final String s2) {
        super(s, o, s2);
    }
    
    @Override
    protected Logger newInstance(final LoggerContext loggerContext, final String s, final MessageFactory messageFactory) {
        return new AsyncLogger(loggerContext, s, messageFactory);
    }
    
    @Override
    public void stop() {
        super.stop();
    }
}
