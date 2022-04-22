package org.apache.logging.log4j.core.appender;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;
import java.util.*;

@Plugin(name = "Async", category = "Core", elementType = "appender", printObject = true)
public final class AsyncAppender extends AbstractAppender
{
    private static final int DEFAULT_QUEUE_SIZE = 128;
    private static final String SHUTDOWN = "Shutdown";
    private final BlockingQueue queue;
    private final boolean blocking;
    private final Configuration config;
    private final AppenderRef[] appenderRefs;
    private final String errorRef;
    private final boolean includeLocation;
    private AppenderControl errorAppender;
    private AsyncThread thread;
    private static final AtomicLong threadSequence;
    
    private AsyncAppender(final String s, final Filter filter, final AppenderRef[] appenderRefs, final String errorRef, final int n, final boolean blocking, final boolean b, final Configuration config, final boolean includeLocation) {
        super(s, filter, null, b);
        this.queue = new ArrayBlockingQueue(n);
        this.blocking = blocking;
        this.config = config;
        this.appenderRefs = appenderRefs;
        this.errorRef = errorRef;
        this.includeLocation = includeLocation;
    }
    
    @Override
    public void start() {
        final Map appenders = this.config.getAppenders();
        final ArrayList<AppenderControl> list = new ArrayList<AppenderControl>();
        final AppenderRef[] appenderRefs = this.appenderRefs;
        while (0 < appenderRefs.length) {
            final AppenderRef appenderRef = appenderRefs[0];
            if (appenders.containsKey(appenderRef.getRef())) {
                list.add(new AppenderControl(appenders.get(appenderRef.getRef()), appenderRef.getLevel(), appenderRef.getFilter()));
            }
            else {
                AsyncAppender.LOGGER.error("No appender named {} was configured", appenderRef);
            }
            int n = 0;
            ++n;
        }
        if (this.errorRef != null) {
            if (appenders.containsKey(this.errorRef)) {
                this.errorAppender = new AppenderControl(appenders.get(this.errorRef), null, null);
            }
            else {
                AsyncAppender.LOGGER.error("Unable to set up error Appender. No appender named {} was configured", this.errorRef);
            }
        }
        if (list.size() > 0) {
            (this.thread = new AsyncThread(list, this.queue)).setName("AsyncAppender-" + this.getName());
        }
        else if (this.errorRef == null) {
            throw new ConfigurationException("No appenders are available for AsyncAppender " + this.getName());
        }
        this.thread.start();
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
        this.thread.shutdown();
        this.thread.join();
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        if (!this.isStarted()) {
            throw new IllegalStateException("AsyncAppender " + this.getName() + " is not active");
        }
        if (logEvent instanceof Log4jLogEvent) {
            if (this.blocking) {
                this.queue.put(Log4jLogEvent.serialize((Log4jLogEvent)logEvent, this.includeLocation));
            }
            else {
                this.queue.offer(Log4jLogEvent.serialize((Log4jLogEvent)logEvent, this.includeLocation));
            }
        }
    }
    
    @PluginFactory
    public static AsyncAppender createAppender(@PluginElement("AppenderRef") final AppenderRef[] array, @PluginAttribute("errorRef") @PluginAliases({ "error-ref" }) final String s, @PluginAttribute("blocking") final String s2, @PluginAttribute("bufferSize") final String s3, @PluginAttribute("name") final String s4, @PluginAttribute("includeLocation") final String s5, @PluginElement("Filter") final Filter filter, @PluginConfiguration final Configuration configuration, @PluginAttribute("ignoreExceptions") final String s6) {
        if (s4 == null) {
            AsyncAppender.LOGGER.error("No name provided for AsyncAppender");
            return null;
        }
        if (array == null) {
            AsyncAppender.LOGGER.error("No appender references provided to AsyncAppender {}", s4);
        }
        return new AsyncAppender(s4, filter, array, s, AbstractAppender.parseInt(s3, 128), Booleans.parseBoolean(s2, true), Booleans.parseBoolean(s6, true), configuration, Boolean.parseBoolean(s5));
    }
    
    static AtomicLong access$000() {
        return AsyncAppender.threadSequence;
    }
    
    static AppenderControl access$100(final AsyncAppender asyncAppender) {
        return asyncAppender.errorAppender;
    }
    
    static {
        threadSequence = new AtomicLong(1L);
    }
    
    private class AsyncThread extends Thread
    {
        private boolean shutdown;
        private final List appenders;
        private final BlockingQueue queue;
        final AsyncAppender this$0;
        
        public AsyncThread(final AsyncAppender this$0, final List appenders, final BlockingQueue queue) {
            this.this$0 = this$0;
            this.shutdown = false;
            this.appenders = appenders;
            this.queue = queue;
            this.setDaemon(true);
            this.setName("AsyncAppenderThread" + AsyncAppender.access$000().getAndIncrement());
        }
        
        @Override
        public void run() {
            while (!this.shutdown) {
                final Serializable s = this.queue.take();
                if (s != null && s instanceof String && "Shutdown".equals(s.toString())) {
                    this.shutdown = true;
                }
                else {
                    final Log4jLogEvent deserialize = Log4jLogEvent.deserialize(s);
                    deserialize.setEndOfBatch(this.queue.isEmpty());
                    final Iterator<AppenderControl> iterator = this.appenders.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().callAppender(deserialize);
                    }
                }
            }
            while (!this.queue.isEmpty()) {
                final Serializable s2 = this.queue.take();
                if (s2 instanceof Log4jLogEvent) {
                    final Log4jLogEvent deserialize2 = Log4jLogEvent.deserialize(s2);
                    deserialize2.setEndOfBatch(this.queue.isEmpty());
                    final Iterator<AppenderControl> iterator2 = this.appenders.iterator();
                    while (iterator2.hasNext()) {
                        iterator2.next().callAppender(deserialize2);
                    }
                }
            }
        }
        
        public void shutdown() {
            this.shutdown = true;
            if (this.queue.isEmpty()) {
                this.queue.offer("Shutdown");
            }
        }
    }
}
