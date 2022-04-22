package org.apache.logging.log4j.status;

import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.simple.*;
import java.util.concurrent.locks.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import java.util.concurrent.*;

public final class StatusLogger extends AbstractLogger
{
    public static final String MAX_STATUS_ENTRIES = "log4j2.status.entries";
    private static final String NOT_AVAIL = "?";
    private static final PropertiesUtil PROPS;
    private static final int MAX_ENTRIES;
    private static final String DEFAULT_STATUS_LEVEL;
    private static final StatusLogger STATUS_LOGGER;
    private final SimpleLogger logger;
    private final CopyOnWriteArrayList listeners;
    private final ReentrantReadWriteLock listenersLock;
    private final Queue messages;
    private final ReentrantLock msgLock;
    private int listenersLevel;
    
    private StatusLogger() {
        this.listeners = new CopyOnWriteArrayList();
        this.listenersLock = new ReentrantReadWriteLock();
        this.messages = new BoundedQueue(StatusLogger.MAX_ENTRIES);
        this.msgLock = new ReentrantLock();
        this.logger = new SimpleLogger("StatusLogger", Level.ERROR, false, true, false, false, "", null, StatusLogger.PROPS, System.err);
        this.listenersLevel = Level.toLevel(StatusLogger.DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
    }
    
    public static StatusLogger getLogger() {
        return StatusLogger.STATUS_LOGGER;
    }
    
    public Level getLevel() {
        return this.logger.getLevel();
    }
    
    public void setLevel(final Level level) {
        this.logger.setLevel(level);
    }
    
    public void registerListener(final StatusListener statusListener) {
        this.listenersLock.writeLock().lock();
        this.listeners.add(statusListener);
        final Level statusLevel = statusListener.getStatusLevel();
        if (this.listenersLevel < statusLevel.intLevel()) {
            this.listenersLevel = statusLevel.intLevel();
        }
        this.listenersLock.writeLock().unlock();
    }
    
    public void removeListener(final StatusListener statusListener) {
        this.listenersLock.writeLock().lock();
        this.listeners.remove(statusListener);
        int intLevel = Level.toLevel(StatusLogger.DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
        final Iterator<StatusListener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            final int intLevel2 = iterator.next().getStatusLevel().intLevel();
            if (intLevel < intLevel2) {
                intLevel = intLevel2;
            }
        }
        this.listenersLevel = intLevel;
        this.listenersLock.writeLock().unlock();
    }
    
    public Iterator getListeners() {
        return this.listeners.iterator();
    }
    
    public void reset() {
        this.listeners.clear();
        this.clear();
    }
    
    public List getStatusData() {
        this.msgLock.lock();
        final ArrayList list = new ArrayList(this.messages);
        this.msgLock.unlock();
        return list;
    }
    
    public void clear() {
        this.msgLock.lock();
        this.messages.clear();
        this.msgLock.unlock();
    }
    
    @Override
    public void log(final Marker marker, final String s, final Level level, final Message message, final Throwable t) {
        StackTraceElement stackTraceElement = null;
        if (s != null) {
            stackTraceElement = this.getStackTraceElement(s, Thread.currentThread().getStackTrace());
        }
        final StatusData statusData = new StatusData(stackTraceElement, level, message, t);
        this.msgLock.lock();
        this.messages.add(statusData);
        this.msgLock.unlock();
        if (this.listeners.size() > 0) {
            for (final StatusListener statusListener : this.listeners) {
                if (statusData.getLevel().isAtLeastAsSpecificAs(statusListener.getStatusLevel())) {
                    statusListener.log(statusData);
                }
            }
        }
        else {
            this.logger.log(marker, s, level, message, t);
        }
    }
    
    private StackTraceElement getStackTraceElement(final String s, final StackTraceElement[] array) {
        if (s == null) {
            return null;
        }
        while (0 < array.length) {
            final StackTraceElement stackTraceElement = array[0];
            if (true) {
                return stackTraceElement;
            }
            final String className = stackTraceElement.getClassName();
            if (!s.equals(className)) {
                if ("?".equals(className)) {
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s) {
        return this.isEnabled(level, marker);
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s, final Throwable t) {
        return this.isEnabled(level, marker);
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s, final Object... array) {
        return this.isEnabled(level, marker);
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.isEnabled(level, marker);
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.isEnabled(level, marker);
    }
    
    @Override
    public boolean isEnabled(final Level level, final Marker marker) {
        if (this.listeners.size() > 0) {
            return this.listenersLevel >= level.intLevel();
        }
        switch (level) {
            case FATAL: {
                return this.logger.isFatalEnabled(marker);
            }
            case TRACE: {
                return this.logger.isTraceEnabled(marker);
            }
            case DEBUG: {
                return this.logger.isDebugEnabled(marker);
            }
            case INFO: {
                return this.logger.isInfoEnabled(marker);
            }
            case WARN: {
                return this.logger.isWarnEnabled(marker);
            }
            case ERROR: {
                return this.logger.isErrorEnabled(marker);
            }
            default: {
                return false;
            }
        }
    }
    
    static Queue access$000(final StatusLogger statusLogger) {
        return statusLogger.messages;
    }
    
    static {
        PROPS = new PropertiesUtil("log4j2.StatusLogger.properties");
        MAX_ENTRIES = StatusLogger.PROPS.getIntegerProperty("log4j2.status.entries", 200);
        DEFAULT_STATUS_LEVEL = StatusLogger.PROPS.getStringProperty("log4j2.StatusLogger.level");
        STATUS_LOGGER = new StatusLogger();
    }
    
    private class BoundedQueue extends ConcurrentLinkedQueue
    {
        private static final long serialVersionUID = -3945953719763255337L;
        private final int size;
        final StatusLogger this$0;
        
        public BoundedQueue(final StatusLogger this$0, final int size) {
            this.this$0 = this$0;
            this.size = size;
        }
        
        @Override
        public boolean add(final Object o) {
            while (StatusLogger.access$000(this.this$0).size() > this.size) {
                StatusLogger.access$000(this.this$0).poll();
            }
            return super.add(o);
        }
    }
}
