package com.mojang.util;

import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import java.util.concurrent.locks.*;

@Plugin(name = "Queue", category = "Core", elementType = "appender", printObject = true)
public class QueueLogAppender extends AbstractAppender
{
    private static final int MAX_CAPACITY = 250;
    private static final Map QUEUES;
    private static final ReadWriteLock QUEUE_LOCK;
    private final BlockingQueue queue;
    
    public QueueLogAppender(final String s, final Filter filter, final Layout layout, final boolean b, final BlockingQueue queue) {
        super(s, filter, layout, b);
        this.queue = queue;
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        if (this.queue.size() >= 250) {
            this.queue.clear();
        }
        this.queue.add(this.getLayout().toSerializable(logEvent).toString());
    }
    
    @PluginFactory
    public static QueueLogAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("Layout") Layout layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("target") String s3) {
        final boolean boolean1 = Boolean.parseBoolean(s2);
        if (s == null) {
            QueueLogAppender.LOGGER.error("No name provided for QueueLogAppender");
            return null;
        }
        if (s3 == null) {
            s3 = s;
        }
        QueueLogAppender.QUEUE_LOCK.writeLock().lock();
        BlockingQueue<?> blockingQueue = QueueLogAppender.QUEUES.get(s3);
        if (blockingQueue == null) {
            blockingQueue = new LinkedBlockingQueue<Object>();
            QueueLogAppender.QUEUES.put(s3, blockingQueue);
        }
        QueueLogAppender.QUEUE_LOCK.writeLock().unlock();
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        return new QueueLogAppender(s, filter, layout, boolean1, blockingQueue);
    }
    
    public static String getNextLogEvent(final String s) {
        QueueLogAppender.QUEUE_LOCK.readLock().lock();
        final BlockingQueue<String> blockingQueue = QueueLogAppender.QUEUES.get(s);
        QueueLogAppender.QUEUE_LOCK.readLock().unlock();
        if (blockingQueue != null) {
            return blockingQueue.take();
        }
        return null;
    }
    
    static {
        QUEUES = new HashMap();
        QUEUE_LOCK = new ReentrantReadWriteLock();
    }
}
