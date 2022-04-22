package org.apache.logging.log4j.core.web;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.lookup.*;
import javax.servlet.*;
import java.net.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.core.selector.*;
import org.apache.logging.log4j.core.config.*;

final class Log4jWebInitializerImpl implements Log4jWebInitializer
{
    private static final Object MUTEX;
    private final StrSubstitutor substitutor;
    private final ServletContext servletContext;
    private String name;
    private NamedContextSelector selector;
    private LoggerContext loggerContext;
    private boolean initialized;
    private boolean deinitialized;
    
    private Log4jWebInitializerImpl(final ServletContext servletContext) {
        this.substitutor = new StrSubstitutor(new Interpolator());
        this.initialized = false;
        this.deinitialized = false;
        this.servletContext = servletContext;
    }
    
    @Override
    public synchronized void initialize() throws UnavailableException {
        if (this.deinitialized) {
            throw new IllegalStateException("Cannot initialize Log4jWebInitializer after it was destroyed.");
        }
        if (!this.initialized) {
            this.initialized = true;
            this.name = this.substitutor.replace(this.servletContext.getInitParameter("log4jContextName"));
            final String replace = this.substitutor.replace(this.servletContext.getInitParameter("log4jConfiguration"));
            if ("true".equals(this.servletContext.getInitParameter("isLog4jContextSelectorNamed"))) {
                this.initializeJndi(replace);
            }
            else {
                this.initializeNonJndi(replace);
            }
        }
    }
    
    private void initializeJndi(final String s) throws UnavailableException {
        URI uri = null;
        if (s != null) {
            uri = new URI(s);
        }
        if (this.name == null) {
            throw new UnavailableException("A log4jContextName context parameter is required");
        }
        final LoggerContextFactory factory = LogManager.getFactory();
        if (!(factory instanceof Log4jContextFactory)) {
            this.servletContext.log("Potential problem: Factory is not an instance of Log4jContextFactory.");
            return;
        }
        final ContextSelector selector = ((Log4jContextFactory)factory).getSelector();
        if (selector instanceof NamedContextSelector) {
            this.selector = (NamedContextSelector)selector;
            final LoggerContext locateContext = this.selector.locateContext(this.name, this.servletContext, uri);
            ContextAnchor.THREAD_CONTEXT.set(locateContext);
            if (locateContext.getStatus() == LoggerContext.Status.INITIALIZED) {
                locateContext.start();
            }
            ContextAnchor.THREAD_CONTEXT.remove();
            this.loggerContext = locateContext;
            this.servletContext.log("Created logger context for [" + this.name + "] using [" + locateContext.getClass().getClassLoader() + "].");
            return;
        }
        this.servletContext.log("Potential problem: Selector is not an instance of NamedContextSelector.");
    }
    
    private void initializeNonJndi(final String s) {
        if (this.name == null) {
            this.name = this.servletContext.getServletContextName();
        }
        if (this.name == null && s == null) {
            this.servletContext.log("No Log4j context configuration provided. This is very unusual.");
            return;
        }
        this.loggerContext = Configurator.initialize(this.name, this.getClassLoader(), s, this.servletContext);
    }
    
    @Override
    public synchronized void deinitialize() {
        if (!this.initialized) {
            throw new IllegalStateException("Cannot deinitialize Log4jWebInitializer because it has not initialized.");
        }
        if (!this.deinitialized) {
            this.deinitialized = true;
            if (this.loggerContext != null) {
                this.servletContext.log("Removing LoggerContext for [" + this.name + "].");
                if (this.selector != null) {
                    this.selector.removeContext(this.name);
                }
                this.loggerContext.stop();
                this.loggerContext.setExternalContext(null);
                this.loggerContext = null;
            }
        }
    }
    
    @Override
    public void setLoggerContext() {
        if (this.loggerContext != null) {
            ContextAnchor.THREAD_CONTEXT.set(this.loggerContext);
        }
    }
    
    @Override
    public void clearLoggerContext() {
        ContextAnchor.THREAD_CONTEXT.remove();
    }
    
    private ClassLoader getClassLoader() {
        return this.servletContext.getClassLoader();
    }
    
    static Log4jWebInitializer getLog4jWebInitializer(final ServletContext servletContext) {
        // monitorenter(mutex = Log4jWebInitializerImpl.MUTEX)
        Log4jWebInitializer log4jWebInitializer = (Log4jWebInitializer)servletContext.getAttribute(Log4jWebInitializerImpl.INITIALIZER_ATTRIBUTE);
        if (log4jWebInitializer == null) {
            log4jWebInitializer = new Log4jWebInitializerImpl(servletContext);
            servletContext.setAttribute(Log4jWebInitializerImpl.INITIALIZER_ATTRIBUTE, (Object)log4jWebInitializer);
        }
        // monitorexit(mutex)
        return log4jWebInitializer;
    }
    
    static {
        MUTEX = new Object();
        Class.forName("org.apache.logging.log4j.core.web.JNDIContextFilter");
        throw new IllegalStateException("You are using Log4j 2 in a web application with the old, extinct log4j-web artifact. This is not supported and could cause serious runtime problems. Pleaseremove the log4j-web JAR file from your application.");
    }
}
