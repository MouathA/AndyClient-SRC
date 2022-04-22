package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.core.*;
import java.net.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.impl.*;

public final class Configurator
{
    protected static final StatusLogger LOGGER;
    
    private Configurator() {
    }
    
    public static LoggerContext initialize(final String s, final ClassLoader classLoader, final String s2) {
        return initialize(s, classLoader, s2, null);
    }
    
    public static LoggerContext initialize(final String s, final ClassLoader classLoader, final String s2, final Object o) {
        return initialize(s, classLoader, (s2 == null) ? null : new URI(s2), o);
    }
    
    public static LoggerContext initialize(final String s, final String s2) {
        return initialize(s, null, s2);
    }
    
    public static LoggerContext initialize(final String s, final ClassLoader classLoader, final URI uri) {
        return initialize(s, classLoader, uri, null);
    }
    
    public static LoggerContext initialize(final String s, final ClassLoader classLoader, final URI uri, final Object externalContext) {
        final org.apache.logging.log4j.spi.LoggerContext context = LogManager.getContext(classLoader, false, uri);
        if (context instanceof LoggerContext) {
            final LoggerContext loggerContext = (LoggerContext)context;
            ContextAnchor.THREAD_CONTEXT.set(loggerContext);
            if (externalContext != null) {
                loggerContext.setExternalContext(externalContext);
            }
            loggerContext.start(ConfigurationFactory.getInstance().getConfiguration(s, uri));
            ContextAnchor.THREAD_CONTEXT.remove();
            return loggerContext;
        }
        Configurator.LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", ((LoggerContext)context).getClass().getName(), LoggerContext.class.getName());
        return null;
    }
    
    public static LoggerContext initialize(final ClassLoader classLoader, final ConfigurationFactory.ConfigurationSource configurationSource) {
        final org.apache.logging.log4j.spi.LoggerContext context = LogManager.getContext(classLoader, false, (configurationSource.getLocation() == null) ? null : new URI(configurationSource.getLocation()));
        if (context instanceof LoggerContext) {
            final LoggerContext loggerContext = (LoggerContext)context;
            ContextAnchor.THREAD_CONTEXT.set(loggerContext);
            loggerContext.start(ConfigurationFactory.getInstance().getConfiguration(configurationSource));
            ContextAnchor.THREAD_CONTEXT.remove();
            return loggerContext;
        }
        Configurator.LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", ((LoggerContext)context).getClass().getName(), LoggerContext.class.getName());
        return null;
    }
    
    public static void shutdown(final LoggerContext loggerContext) {
        if (loggerContext != null) {
            loggerContext.stop();
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
