package org.apache.logging.log4j.core.web;

import javax.servlet.*;

public class Log4jServletContextListener implements ServletContextListener
{
    private ServletContext servletContext;
    private Log4jWebInitializer initializer;
    
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        (this.servletContext = servletContextEvent.getServletContext()).log("Log4jServletContextListener ensuring that Log4j starts up properly.");
        (this.initializer = Log4jWebInitializerImpl.getLog4jWebInitializer(this.servletContext)).initialize();
        this.initializer.setLoggerContext();
    }
    
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        if (this.servletContext == null || this.initializer == null) {
            throw new IllegalStateException("Context destroyed before it was initialized.");
        }
        this.servletContext.log("Log4jServletContextListener ensuring that Log4j shuts down properly.");
        this.initializer.clearLoggerContext();
        this.initializer.deinitialize();
    }
}
