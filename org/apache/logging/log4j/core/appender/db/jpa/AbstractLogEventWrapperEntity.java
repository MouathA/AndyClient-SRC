package org.apache.logging.log4j.core.appender.db.jpa;

import org.apache.logging.log4j.core.*;
import javax.persistence.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractLogEventWrapperEntity implements LogEvent
{
    private static final long serialVersionUID = 1L;
    private final LogEvent wrappedEvent;
    
    protected AbstractLogEventWrapperEntity() {
        this(new NullLogEvent(null));
    }
    
    protected AbstractLogEventWrapperEntity(final LogEvent wrappedEvent) {
        if (wrappedEvent == null) {
            throw new IllegalArgumentException("The wrapped event cannot be null.");
        }
        this.wrappedEvent = wrappedEvent;
    }
    
    @Transient
    protected final LogEvent getWrappedEvent() {
        return this.wrappedEvent;
    }
    
    public void setLevel(final Level level) {
    }
    
    public void setLoggerName(final String s) {
    }
    
    public void setSource(final StackTraceElement stackTraceElement) {
    }
    
    public void setMessage(final Message message) {
    }
    
    public void setMarker(final Marker marker) {
    }
    
    public void setThreadName(final String s) {
    }
    
    public void setMillis(final long n) {
    }
    
    public void setThrown(final Throwable t) {
    }
    
    public void setContextMap(final Map map) {
    }
    
    public void setContextStack(final ThreadContext.ContextStack contextStack) {
    }
    
    public void setFQCN(final String s) {
    }
    
    @Transient
    @Override
    public final boolean isIncludeLocation() {
        return this.getWrappedEvent().isIncludeLocation();
    }
    
    @Override
    public final void setIncludeLocation(final boolean includeLocation) {
        this.getWrappedEvent().setIncludeLocation(includeLocation);
    }
    
    @Transient
    @Override
    public final boolean isEndOfBatch() {
        return this.getWrappedEvent().isEndOfBatch();
    }
    
    @Override
    public final void setEndOfBatch(final boolean endOfBatch) {
        this.getWrappedEvent().setEndOfBatch(endOfBatch);
    }
    
    private static class NullLogEvent implements LogEvent
    {
        private static final long serialVersionUID = 1L;
        
        private NullLogEvent() {
        }
        
        @Override
        public Level getLevel() {
            return null;
        }
        
        @Override
        public String getLoggerName() {
            return null;
        }
        
        @Override
        public StackTraceElement getSource() {
            return null;
        }
        
        @Override
        public Message getMessage() {
            return null;
        }
        
        @Override
        public Marker getMarker() {
            return null;
        }
        
        @Override
        public String getThreadName() {
            return null;
        }
        
        @Override
        public long getMillis() {
            return 0L;
        }
        
        @Override
        public Throwable getThrown() {
            return null;
        }
        
        @Override
        public Map getContextMap() {
            return null;
        }
        
        @Override
        public ThreadContext.ContextStack getContextStack() {
            return null;
        }
        
        @Override
        public String getFQCN() {
            return null;
        }
        
        @Override
        public boolean isIncludeLocation() {
            return false;
        }
        
        @Override
        public void setIncludeLocation(final boolean b) {
        }
        
        @Override
        public boolean isEndOfBatch() {
            return false;
        }
        
        @Override
        public void setEndOfBatch(final boolean b) {
        }
        
        NullLogEvent(final AbstractLogEventWrapperEntity$1 object) {
            this();
        }
    }
}
