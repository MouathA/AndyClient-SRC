package org.apache.logging.log4j.core.selector;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.status.*;
import java.net.*;
import org.apache.logging.log4j.core.impl.*;
import java.util.*;
import javax.naming.*;
import java.util.concurrent.*;

public class JNDIContextSelector implements NamedContextSelector
{
    private static final LoggerContext CONTEXT;
    private static final ConcurrentMap CONTEXT_MAP;
    private static final StatusLogger LOGGER;
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return this.getContext(s, classLoader, b, null);
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        final LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        if (loggerContext != null) {
            return loggerContext;
        }
        final String s2 = (String)lookup(new InitialContext(), "java:comp/env/log4j/context-name");
        return (s2 == null) ? JNDIContextSelector.CONTEXT : this.locateContext(s2, null, uri);
    }
    
    @Override
    public LoggerContext locateContext(final String s, final Object o, final URI uri) {
        if (s == null) {
            JNDIContextSelector.LOGGER.error("A context name is required to locate a LoggerContext");
            return null;
        }
        if (!JNDIContextSelector.CONTEXT_MAP.containsKey(s)) {
            JNDIContextSelector.CONTEXT_MAP.putIfAbsent(s, new LoggerContext(s, o, uri));
        }
        return (LoggerContext)JNDIContextSelector.CONTEXT_MAP.get(s);
    }
    
    @Override
    public void removeContext(final LoggerContext loggerContext) {
        for (final Map.Entry<K, LoggerContext> entry : JNDIContextSelector.CONTEXT_MAP.entrySet()) {
            if (entry.getValue().equals(loggerContext)) {
                JNDIContextSelector.CONTEXT_MAP.remove(entry.getKey());
            }
        }
    }
    
    @Override
    public LoggerContext removeContext(final String s) {
        return (LoggerContext)JNDIContextSelector.CONTEXT_MAP.remove(s);
    }
    
    @Override
    public List getLoggerContexts() {
        return Collections.unmodifiableList((List<?>)new ArrayList<Object>(JNDIContextSelector.CONTEXT_MAP.values()));
    }
    
    protected static Object lookup(final Context context, final String s) throws NamingException {
        if (context == null) {
            return null;
        }
        return context.lookup(s);
    }
    
    static {
        CONTEXT = new LoggerContext("Default");
        CONTEXT_MAP = new ConcurrentHashMap();
        LOGGER = StatusLogger.getLogger();
    }
}
