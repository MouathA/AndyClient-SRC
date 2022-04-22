package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import javax.servlet.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "web", category = "Lookup")
public class WebLookup implements StrLookup
{
    private static final String ATTR_PREFIX = "attr.";
    private static final String INIT_PARAM_PREFIX = "initParam.";
    
    protected ServletContext getServletContext() {
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        if (loggerContext == null) {
            loggerContext = (LoggerContext)LogManager.getContext(false);
        }
        if (loggerContext != null) {
            final Object externalContext = loggerContext.getExternalContext();
            return (externalContext != null && externalContext instanceof ServletContext) ? ((ServletContext)externalContext) : null;
        }
        return null;
    }
    
    @Override
    public String lookup(final String s) {
        final ServletContext servletContext = this.getServletContext();
        if (servletContext == null) {
            return null;
        }
        if (s.startsWith("attr.")) {
            final Object attribute = servletContext.getAttribute(s.substring(5));
            return (attribute == null) ? null : attribute.toString();
        }
        if (s.startsWith("initParam.")) {
            return servletContext.getInitParameter(s.substring(10));
        }
        if ("rootDir".equals(s)) {
            final String realPath = servletContext.getRealPath("/");
            if (realPath == null) {
                throw new RuntimeException("failed to resolve web:rootDir -- servlet container unable to translate virtual path  to real path (probably not deployed as exploded");
            }
            return realPath;
        }
        else {
            if ("contextPath".equals(s)) {
                return servletContext.getContextPath();
            }
            if ("servletContextName".equals(s)) {
                return servletContext.getServletContextName();
            }
            if ("serverInfo".equals(s)) {
                return servletContext.getServerInfo();
            }
            if ("effectiveMajorVersion".equals(s)) {
                return String.valueOf(servletContext.getEffectiveMajorVersion());
            }
            if ("effectiveMinorVersion".equals(s)) {
                return String.valueOf(servletContext.getEffectiveMinorVersion());
            }
            if ("majorVersion".equals(s)) {
                return String.valueOf(servletContext.getMajorVersion());
            }
            if ("minorVersion".equals(s)) {
                return String.valueOf(servletContext.getMinorVersion());
            }
            if (servletContext.getAttribute(s) != null) {
                return servletContext.getAttribute(s).toString();
            }
            if (servletContext.getInitParameter(s) != null) {
                return servletContext.getInitParameter(s);
            }
            servletContext.log(this.getClass().getName() + " unable to resolve key '" + s + "'");
            return null;
        }
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        return this.lookup(s);
    }
}
