package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.selector.*;
import org.apache.logging.log4j.core.jmx.*;
import org.apache.logging.log4j.core.*;
import java.net.*;

public class Log4jContextFactory implements LoggerContextFactory
{
    private static final StatusLogger LOGGER;
    private ContextSelector selector;
    
    public Log4jContextFactory() {
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector");
        if (stringProperty != null) {
            final Class loadClass = Loader.loadClass(stringProperty);
            if (loadClass != null && ContextSelector.class.isAssignableFrom(loadClass)) {
                this.selector = (ContextSelector)loadClass.newInstance();
            }
        }
        if (this.selector == null) {
            this.selector = new ClassLoaderContextSelector();
        }
        Server.registerMBeans(this.selector);
    }
    
    public ContextSelector getSelector() {
        return this.selector;
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        final LoggerContext context = this.selector.getContext(s, classLoader, b);
        if (context.getStatus() == LoggerContext.Status.INITIALIZED) {
            context.start();
        }
        return context;
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        final LoggerContext context = this.selector.getContext(s, classLoader, b, uri);
        if (context.getStatus() == LoggerContext.Status.INITIALIZED) {
            context.start();
        }
        return context;
    }
    
    @Override
    public void removeContext(final org.apache.logging.log4j.spi.LoggerContext loggerContext) {
        if (loggerContext instanceof LoggerContext) {
            this.selector.removeContext((LoggerContext)loggerContext);
        }
    }
    
    @Override
    public org.apache.logging.log4j.spi.LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        return this.getContext(s, classLoader, b, uri);
    }
    
    @Override
    public org.apache.logging.log4j.spi.LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return this.getContext(s, classLoader, b);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
