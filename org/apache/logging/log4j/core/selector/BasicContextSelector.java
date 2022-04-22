package org.apache.logging.log4j.core.selector;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.impl.*;
import java.net.*;
import java.util.*;

public class BasicContextSelector implements ContextSelector
{
    private static final LoggerContext CONTEXT;
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        final LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        return (loggerContext != null) ? loggerContext : BasicContextSelector.CONTEXT;
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        final LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        return (loggerContext != null) ? loggerContext : BasicContextSelector.CONTEXT;
    }
    
    public LoggerContext locateContext(final String s, final String s2) {
        return BasicContextSelector.CONTEXT;
    }
    
    @Override
    public void removeContext(final LoggerContext loggerContext) {
    }
    
    @Override
    public List getLoggerContexts() {
        final ArrayList<LoggerContext> list = new ArrayList<LoggerContext>();
        list.add(BasicContextSelector.CONTEXT);
        return Collections.unmodifiableList((List<?>)list);
    }
    
    static {
        CONTEXT = new LoggerContext("Default");
    }
}
