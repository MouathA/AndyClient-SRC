package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.core.selector.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import java.net.*;

public class AsyncLoggerContextSelector implements ContextSelector
{
    private static final AsyncLoggerContext CONTEXT;
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return AsyncLoggerContextSelector.CONTEXT;
    }
    
    @Override
    public List getLoggerContexts() {
        final ArrayList<AsyncLoggerContext> list = new ArrayList<AsyncLoggerContext>();
        list.add(AsyncLoggerContextSelector.CONTEXT);
        return Collections.unmodifiableList((List<?>)list);
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        return AsyncLoggerContextSelector.CONTEXT;
    }
    
    @Override
    public void removeContext(final LoggerContext loggerContext) {
    }
    
    static {
        CONTEXT = new AsyncLoggerContext("AsyncLoggerContext");
    }
}
