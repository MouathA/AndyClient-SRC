package org.apache.logging.log4j.core.web;

import javax.servlet.*;
import java.io.*;

public class Log4jServletFilter implements Filter
{
    static final String ALREADY_FILTERED_ATTRIBUTE;
    private ServletContext servletContext;
    private Log4jWebInitializer initializer;
    
    public void init(final FilterConfig filterConfig) throws ServletException {
        (this.servletContext = filterConfig.getServletContext()).log("Log4jServletFilter initialized.");
        (this.initializer = Log4jWebInitializerImpl.getLog4jWebInitializer(this.servletContext)).clearLoggerContext();
    }
    
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getAttribute(Log4jServletFilter.ALREADY_FILTERED_ATTRIBUTE) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            servletRequest.setAttribute(Log4jServletFilter.ALREADY_FILTERED_ATTRIBUTE, (Object)true);
            this.initializer.setLoggerContext();
            filterChain.doFilter(servletRequest, servletResponse);
            this.initializer.clearLoggerContext();
        }
    }
    
    public void destroy() {
        if (this.servletContext == null || this.initializer == null) {
            throw new IllegalStateException("Filter destroyed before it was initialized.");
        }
        this.servletContext.log("Log4jServletFilter destroyed.");
        this.initializer.setLoggerContext();
    }
    
    static {
        ALREADY_FILTERED_ATTRIBUTE = Log4jServletFilter.class.getName() + ".FILTERED";
    }
}
