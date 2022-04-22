package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class RingBufferLogEventTranslator implements EventTranslator
{
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
    
    public void translateTo(final RingBufferLogEvent ringBufferLogEvent, final long n) {
        ringBufferLogEvent.setValues(this.asyncLogger, this.loggerName, this.marker, this.fqcn, this.level, this.message, this.thrown, this.contextMap, this.contextStack, this.threadName, this.location, this.currentTimeMillis);
    }
    
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
    
    public void translateTo(final Object o, final long n) {
        this.translateTo((RingBufferLogEvent)o, n);
    }
}
