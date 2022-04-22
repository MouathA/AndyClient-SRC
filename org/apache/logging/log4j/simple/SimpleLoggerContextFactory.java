package org.apache.logging.log4j.simple;

import org.apache.logging.log4j.spi.*;
import java.net.*;

public class SimpleLoggerContextFactory implements LoggerContextFactory
{
    private static LoggerContext context;
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return SimpleLoggerContextFactory.context;
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        return SimpleLoggerContextFactory.context;
    }
    
    @Override
    public void removeContext(final LoggerContext loggerContext) {
    }
    
    static {
        SimpleLoggerContextFactory.context = new SimpleLoggerContext();
    }
}
