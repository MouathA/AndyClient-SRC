package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;
import java.io.*;

public class Log4jLogEvent implements LogEvent
{
    private static final long serialVersionUID = -1351367343806656055L;
    private static final String NOT_AVAIL = "?";
    private final String fqcnOfLogger;
    private final Marker marker;
    private final Level level;
    private final String name;
    private final Message message;
    private final long timestamp;
    private final ThrowableProxy throwable;
    private final Map mdc;
    private final ThreadContext.ContextStack ndc;
    private String threadName;
    private StackTraceElement location;
    private boolean includeLocation;
    private boolean endOfBatch;
    
    public Log4jLogEvent(final long n) {
        this("", null, "", null, null, (ThrowableProxy)null, null, null, null, null, n);
    }
    
    public Log4jLogEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final Throwable t) {
        this(s, marker, s2, level, message, null, t);
    }
    
    public Log4jLogEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final List list, final Throwable t) {
        this(s, marker, s2, level, message, t, createMap(list), (ThreadContext.getDepth() == 0) ? null : ThreadContext.cloneStack(), null, null, System.currentTimeMillis());
    }
    
    public Log4jLogEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final Throwable t, final Map map, final ThreadContext.ContextStack contextStack, final String s3, final StackTraceElement stackTraceElement, final long n) {
        this(s, marker, s2, level, message, (t == null) ? null : new ThrowableProxy(t), map, contextStack, s3, stackTraceElement, n);
    }
    
    public static Log4jLogEvent createEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final ThrowableProxy throwableProxy, final Map map, final ThreadContext.ContextStack contextStack, final String s3, final StackTraceElement stackTraceElement, final long n) {
        return new Log4jLogEvent(s, marker, s2, level, message, throwableProxy, map, contextStack, s3, stackTraceElement, n);
    }
    
    private Log4jLogEvent(final String name, final Marker marker, final String fqcnOfLogger, final Level level, final Message message, final ThrowableProxy throwable, final Map mdc, final ThreadContext.ContextStack ndc, final String threadName, final StackTraceElement location, final long n) {
        this.threadName = null;
        this.endOfBatch = false;
        this.name = name;
        this.marker = marker;
        this.fqcnOfLogger = fqcnOfLogger;
        this.level = level;
        this.message = message;
        this.throwable = throwable;
        this.mdc = mdc;
        this.ndc = ndc;
        this.timestamp = ((message instanceof TimestampMessage) ? ((TimestampMessage)message).getTimestamp() : n);
        this.threadName = threadName;
        this.location = location;
        if (message != null && message instanceof LoggerNameAwareMessage) {
            ((LoggerNameAwareMessage)message).setLoggerName(this.name);
        }
    }
    
    private static Map createMap(final List list) {
        final Map immutableContext = ThreadContext.getImmutableContext();
        if (immutableContext == null && (list == null || list.size() == 0)) {
            return null;
        }
        if (list == null || list.size() == 0) {
            return immutableContext;
        }
        final HashMap hashMap = new HashMap<Object, Object>(immutableContext);
        for (final Property property : list) {
            if (!hashMap.containsKey(property.getName())) {
                hashMap.put(property.getName(), property.getValue());
            }
        }
        return Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
    
    @Override
    public Level getLevel() {
        return this.level;
    }
    
    @Override
    public String getLoggerName() {
        return this.name;
    }
    
    @Override
    public Message getMessage() {
        return this.message;
    }
    
    @Override
    public String getThreadName() {
        if (this.threadName == null) {
            this.threadName = Thread.currentThread().getName();
        }
        return this.threadName;
    }
    
    @Override
    public long getMillis() {
        return this.timestamp;
    }
    
    @Override
    public Throwable getThrown() {
        return (this.throwable == null) ? null : this.throwable.getThrowable();
    }
    
    public ThrowableProxy getThrownProxy() {
        return this.throwable;
    }
    
    @Override
    public Marker getMarker() {
        return this.marker;
    }
    
    @Override
    public String getFQCN() {
        return this.fqcnOfLogger;
    }
    
    @Override
    public Map getContextMap() {
        return (this.mdc == null) ? ThreadContext.EMPTY_MAP : this.mdc;
    }
    
    @Override
    public ThreadContext.ContextStack getContextStack() {
        return (this.ndc == null) ? ThreadContext.EMPTY_STACK : this.ndc;
    }
    
    @Override
    public StackTraceElement getSource() {
        if (this.location != null) {
            return this.location;
        }
        if (this.fqcnOfLogger == null || !this.includeLocation) {
            return null;
        }
        return this.location = calcLocation(this.fqcnOfLogger);
    }
    
    public static StackTraceElement calcLocation(final String s) {
        if (s == null) {
            return null;
        }
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        while (0 < stackTrace.length) {
            final StackTraceElement stackTraceElement = stackTrace[0];
            if (!s.equals(stackTraceElement.getClassName())) {
                return stackTraceElement;
            }
            int n = 0;
            ++n;
        }
        return null;
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
    public boolean isEndOfBatch() {
        return this.endOfBatch;
    }
    
    @Override
    public void setEndOfBatch(final boolean endOfBatch) {
        this.endOfBatch = endOfBatch;
    }
    
    protected Object writeReplace() {
        return new LogEventProxy(this, this.includeLocation);
    }
    
    public static Serializable serialize(final Log4jLogEvent log4jLogEvent, final boolean b) {
        return new LogEventProxy(log4jLogEvent, b);
    }
    
    public static Log4jLogEvent deserialize(final Serializable s) {
        if (s == null) {
            throw new NullPointerException("Event cannot be null");
        }
        if (s instanceof LogEventProxy) {
            final LogEventProxy logEventProxy = (LogEventProxy)s;
            final Log4jLogEvent log4jLogEvent = new Log4jLogEvent(LogEventProxy.access$000(logEventProxy), LogEventProxy.access$100(logEventProxy), LogEventProxy.access$200(logEventProxy), LogEventProxy.access$300(logEventProxy), LogEventProxy.access$400(logEventProxy), LogEventProxy.access$500(logEventProxy), LogEventProxy.access$600(logEventProxy), LogEventProxy.access$700(logEventProxy), LogEventProxy.access$800(logEventProxy), LogEventProxy.access$900(logEventProxy), LogEventProxy.access$1000(logEventProxy));
            log4jLogEvent.setEndOfBatch(LogEventProxy.access$1100(logEventProxy));
            log4jLogEvent.setIncludeLocation(LogEventProxy.access$1200(logEventProxy));
            return log4jLogEvent;
        }
        throw new IllegalArgumentException("Event is not a serialized LogEvent: " + s.toString());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Logger=").append(this.name.isEmpty() ? "root" : this.name);
        sb.append(" Level=").append(this.level.name());
        sb.append(" Message=").append(this.message.getFormattedMessage());
        return sb.toString();
    }
    
    static String access$1300(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.fqcnOfLogger;
    }
    
    static Marker access$1400(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.marker;
    }
    
    static Level access$1500(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.level;
    }
    
    static String access$1600(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.name;
    }
    
    static Message access$1700(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.message;
    }
    
    static long access$1800(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.timestamp;
    }
    
    static ThrowableProxy access$1900(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.throwable;
    }
    
    static Map access$2000(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.mdc;
    }
    
    static ThreadContext.ContextStack access$2100(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.ndc;
    }
    
    static boolean access$2200(final Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.endOfBatch;
    }
    
    Log4jLogEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final ThrowableProxy throwableProxy, final Map map, final ThreadContext.ContextStack contextStack, final String s3, final StackTraceElement stackTraceElement, final long n, final Log4jLogEvent$1 object) {
        this(s, marker, s2, level, message, throwableProxy, map, contextStack, s3, stackTraceElement, n);
    }
    
    private static class LogEventProxy implements Serializable
    {
        private static final long serialVersionUID = -7139032940312647146L;
        private final String fqcnOfLogger;
        private final Marker marker;
        private final Level level;
        private final String name;
        private final Message message;
        private final long timestamp;
        private final ThrowableProxy throwable;
        private final Map mdc;
        private final ThreadContext.ContextStack ndc;
        private final String threadName;
        private final StackTraceElement location;
        private final boolean isLocationRequired;
        private final boolean isEndOfBatch;
        
        public LogEventProxy(final Log4jLogEvent log4jLogEvent, final boolean isLocationRequired) {
            this.fqcnOfLogger = Log4jLogEvent.access$1300(log4jLogEvent);
            this.marker = Log4jLogEvent.access$1400(log4jLogEvent);
            this.level = Log4jLogEvent.access$1500(log4jLogEvent);
            this.name = Log4jLogEvent.access$1600(log4jLogEvent);
            this.message = Log4jLogEvent.access$1700(log4jLogEvent);
            this.timestamp = Log4jLogEvent.access$1800(log4jLogEvent);
            this.throwable = Log4jLogEvent.access$1900(log4jLogEvent);
            this.mdc = Log4jLogEvent.access$2000(log4jLogEvent);
            this.ndc = Log4jLogEvent.access$2100(log4jLogEvent);
            this.location = (isLocationRequired ? log4jLogEvent.getSource() : null);
            this.threadName = log4jLogEvent.getThreadName();
            this.isLocationRequired = isLocationRequired;
            this.isEndOfBatch = Log4jLogEvent.access$2200(log4jLogEvent);
        }
        
        protected Object readResolve() {
            final Log4jLogEvent log4jLogEvent = new Log4jLogEvent(this.name, this.marker, this.fqcnOfLogger, this.level, this.message, this.throwable, this.mdc, this.ndc, this.threadName, this.location, this.timestamp, null);
            log4jLogEvent.setEndOfBatch(this.isEndOfBatch);
            log4jLogEvent.setIncludeLocation(this.isLocationRequired);
            return log4jLogEvent;
        }
        
        static String access$000(final LogEventProxy logEventProxy) {
            return logEventProxy.name;
        }
        
        static Marker access$100(final LogEventProxy logEventProxy) {
            return logEventProxy.marker;
        }
        
        static String access$200(final LogEventProxy logEventProxy) {
            return logEventProxy.fqcnOfLogger;
        }
        
        static Level access$300(final LogEventProxy logEventProxy) {
            return logEventProxy.level;
        }
        
        static Message access$400(final LogEventProxy logEventProxy) {
            return logEventProxy.message;
        }
        
        static ThrowableProxy access$500(final LogEventProxy logEventProxy) {
            return logEventProxy.throwable;
        }
        
        static Map access$600(final LogEventProxy logEventProxy) {
            return logEventProxy.mdc;
        }
        
        static ThreadContext.ContextStack access$700(final LogEventProxy logEventProxy) {
            return logEventProxy.ndc;
        }
        
        static String access$800(final LogEventProxy logEventProxy) {
            return logEventProxy.threadName;
        }
        
        static StackTraceElement access$900(final LogEventProxy logEventProxy) {
            return logEventProxy.location;
        }
        
        static long access$1000(final LogEventProxy logEventProxy) {
            return logEventProxy.timestamp;
        }
        
        static boolean access$1100(final LogEventProxy logEventProxy) {
            return logEventProxy.isEndOfBatch;
        }
        
        static boolean access$1200(final LogEventProxy logEventProxy) {
            return logEventProxy.isLocationRequired;
        }
    }
}
