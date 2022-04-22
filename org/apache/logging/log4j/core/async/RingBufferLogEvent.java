package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.lookup.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;
import com.lmax.disruptor.*;

public class RingBufferLogEvent implements LogEvent
{
    private static final long serialVersionUID = 8462119088943934758L;
    public static final Factory FACTORY;
    private AsyncLogger asyncLogger;
    private String loggerName;
    private Marker marker;
    private String fqcn;
    private Level level;
    private Message message;
    private Throwable thrown;
    private Map contextMap;
    private ThreadContext.ContextStack contextStack;
    private String threadName;
    private StackTraceElement location;
    private long currentTimeMillis;
    private boolean endOfBatch;
    private boolean includeLocation;
    
    public void setValues(final AsyncLogger asyncLogger, final String loggerName, final Marker marker, final String fqcn, final Level level, final Message message, final Throwable thrown, final Map contextMap, final ThreadContext.ContextStack contextStack, final String threadName, final StackTraceElement location, final long currentTimeMillis) {
        this.asyncLogger = asyncLogger;
        this.loggerName = loggerName;
        this.marker = marker;
        this.fqcn = fqcn;
        this.level = level;
        this.message = message;
        this.thrown = thrown;
        this.contextMap = contextMap;
        this.contextStack = contextStack;
        this.threadName = threadName;
        this.location = location;
        this.currentTimeMillis = currentTimeMillis;
    }
    
    public void execute(final boolean endOfBatch) {
        this.endOfBatch = endOfBatch;
        this.asyncLogger.actualAsyncLog(this);
    }
    
    @Override
    public boolean isEndOfBatch() {
        return this.endOfBatch;
    }
    
    @Override
    public void setEndOfBatch(final boolean endOfBatch) {
        this.endOfBatch = endOfBatch;
    }
    
    @Override
    public boolean isIncludeLocation() {
        return this.includeLocation;
    }
    
    @Override
    public void setIncludeLocation(final boolean includeLocation) {
        this.includeLocation = includeLocation;
    }
    
    @Override
    public String getLoggerName() {
        return this.loggerName;
    }
    
    @Override
    public Marker getMarker() {
        return this.marker;
    }
    
    @Override
    public String getFQCN() {
        return this.fqcn;
    }
    
    @Override
    public Level getLevel() {
        return this.level;
    }
    
    @Override
    public Message getMessage() {
        if (this.message == null) {
            this.message = new SimpleMessage("");
        }
        return this.message;
    }
    
    @Override
    public Throwable getThrown() {
        return this.thrown;
    }
    
    @Override
    public Map getContextMap() {
        return this.contextMap;
    }
    
    @Override
    public ThreadContext.ContextStack getContextStack() {
        return this.contextStack;
    }
    
    @Override
    public String getThreadName() {
        return this.threadName;
    }
    
    @Override
    public StackTraceElement getSource() {
        return this.location;
    }
    
    @Override
    public long getMillis() {
        return this.currentTimeMillis;
    }
    
    public void mergePropertiesIntoContextMap(final Map map, final StrSubstitutor strSubstitutor) {
        if (map == null) {
            return;
        }
        final HashMap<String, String> contextMap = (this.contextMap == null) ? new HashMap<String, String>() : new HashMap<String, String>(this.contextMap);
        for (final Map.Entry<Property, V> entry : map.entrySet()) {
            final Property property = entry.getKey();
            if (contextMap.containsKey(property.getName())) {
                continue;
            }
            contextMap.put(property.getName(), ((boolean)entry.getValue()) ? strSubstitutor.replace(property.getValue()) : property.getValue());
        }
        this.contextMap = contextMap;
    }
    
    public void clear() {
        this.setValues(null, null, null, null, null, null, null, null, null, null, null, 0L);
    }
    
    static {
        FACTORY = new Factory(null);
    }
    
    private static class Factory implements EventFactory
    {
        private Factory() {
        }
        
        public RingBufferLogEvent newInstance() {
            return new RingBufferLogEvent();
        }
        
        public Object newInstance() {
            return this.newInstance();
        }
        
        Factory(final RingBufferLogEvent$1 object) {
            this();
        }
    }
}
