package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import com.lmax.disruptor.dsl.*;
import java.util.concurrent.*;
import com.lmax.disruptor.util.*;
import org.apache.logging.log4j.status.*;
import com.lmax.disruptor.*;

class AsyncLoggerConfigHelper
{
    private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
    private static final int HALF_A_SECOND = 500;
    private static final int RINGBUFFER_MIN_SIZE = 128;
    private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
    private static final Logger LOGGER;
    private static ThreadFactory threadFactory;
    private static Disruptor disruptor;
    private static ExecutorService executor;
    private static final EventFactory FACTORY;
    private final EventTranslator translator;
    private final ThreadLocal currentLogEvent;
    private final AsyncLoggerConfig asyncLoggerConfig;
    
    public AsyncLoggerConfigHelper(final AsyncLoggerConfig asyncLoggerConfig) {
        this.translator = (EventTranslator)new EventTranslator() {
            final AsyncLoggerConfigHelper this$0;
            
            public void translateTo(final Log4jEventWrapper log4jEventWrapper, final long n) {
                Log4jEventWrapper.access$102(log4jEventWrapper, AsyncLoggerConfigHelper.access$200(this.this$0).get());
                Log4jEventWrapper.access$302(log4jEventWrapper, AsyncLoggerConfigHelper.access$400(this.this$0));
            }
            
            public void translateTo(final Object o, final long n) {
                this.translateTo((Log4jEventWrapper)o, n);
            }
        };
        this.currentLogEvent = new ThreadLocal();
        this.asyncLoggerConfig = asyncLoggerConfig;
    }
    
    private static synchronized void initDisruptor() {
        if (AsyncLoggerConfigHelper.disruptor != null) {
            AsyncLoggerConfigHelper.LOGGER.trace("AsyncLoggerConfigHelper not starting new disruptor, using existing object. Ref count is {}.", 0);
            return;
        }
        AsyncLoggerConfigHelper.LOGGER.trace("AsyncLoggerConfigHelper creating new disruptor. Ref count is {}.", 0);
        final int calculateRingBufferSize = calculateRingBufferSize();
        final WaitStrategy waitStrategy = createWaitStrategy();
        AsyncLoggerConfigHelper.executor = Executors.newSingleThreadExecutor(AsyncLoggerConfigHelper.threadFactory);
        AsyncLoggerConfigHelper.disruptor = new Disruptor(AsyncLoggerConfigHelper.FACTORY, calculateRingBufferSize, (Executor)AsyncLoggerConfigHelper.executor, ProducerType.MULTI, waitStrategy);
        final Log4jEventWrapperHandler[] array = { new Log4jEventWrapperHandler(null) };
        final ExceptionHandler exceptionHandler = getExceptionHandler();
        AsyncLoggerConfigHelper.disruptor.handleExceptionsWith(exceptionHandler);
        AsyncLoggerConfigHelper.disruptor.handleEventsWith((EventHandler[])array);
        AsyncLoggerConfigHelper.LOGGER.debug("Starting AsyncLoggerConfig disruptor with ringbuffer size={}, waitStrategy={}, exceptionHandler={}...", AsyncLoggerConfigHelper.disruptor.getRingBuffer().getBufferSize(), waitStrategy.getClass().getSimpleName(), exceptionHandler);
        AsyncLoggerConfigHelper.disruptor.start();
    }
    
    private static WaitStrategy createWaitStrategy() {
        final String property = System.getProperty("AsyncLoggerConfig.WaitStrategy");
        AsyncLoggerConfigHelper.LOGGER.debug("property AsyncLoggerConfig.WaitStrategy={}", property);
        if ("Sleep".equals(property)) {
            return (WaitStrategy)new SleepingWaitStrategy();
        }
        if ("Yield".equals(property)) {
            return (WaitStrategy)new YieldingWaitStrategy();
        }
        if ("Block".equals(property)) {
            return (WaitStrategy)new BlockingWaitStrategy();
        }
        return (WaitStrategy)new SleepingWaitStrategy();
    }
    
    private static int calculateRingBufferSize() {
        final String property = System.getProperty("AsyncLoggerConfig.RingBufferSize", String.valueOf(262144));
        Integer.parseInt(property);
        if (128 < 128) {
            AsyncLoggerConfigHelper.LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", property, 128);
        }
        return Util.ceilingNextPowerOfTwo(262144);
    }
    
    private static ExceptionHandler getExceptionHandler() {
        final String property = System.getProperty("AsyncLoggerConfig.ExceptionHandler");
        if (property == null) {
            return null;
        }
        return (ExceptionHandler)Class.forName(property).newInstance();
    }
    
    static synchronized void claim() {
        AsyncLoggerConfigHelper.count = 1;
    }
    
    static synchronized void release() {
        final int n = -1;
        AsyncLoggerConfigHelper.count = -1;
        if (n > 0) {
            AsyncLoggerConfigHelper.LOGGER.trace("AsyncLoggerConfigHelper: not shutting down disruptor: ref count is {}.", 0);
            return;
        }
        final Disruptor disruptor = AsyncLoggerConfigHelper.disruptor;
        if (disruptor == null) {
            AsyncLoggerConfigHelper.LOGGER.trace("AsyncLoggerConfigHelper: disruptor already shut down: ref count is {}.", 0);
            return;
        }
        AsyncLoggerConfigHelper.LOGGER.trace("AsyncLoggerConfigHelper: shutting down disruptor: ref count is {}.", 0);
        AsyncLoggerConfigHelper.disruptor = null;
        disruptor.shutdown();
        final RingBuffer ringBuffer = disruptor.getRingBuffer();
        while (0 < 20 && !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize())) {
            Thread.sleep(500L);
            int n2 = 0;
            ++n2;
        }
        AsyncLoggerConfigHelper.executor.shutdown();
        AsyncLoggerConfigHelper.executor = null;
    }
    
    public void callAppendersFromAnotherThread(final LogEvent logEvent) {
        this.currentLogEvent.set(logEvent);
        AsyncLoggerConfigHelper.disruptor.publishEvent(this.translator);
    }
    
    static ThreadLocal access$200(final AsyncLoggerConfigHelper asyncLoggerConfigHelper) {
        return asyncLoggerConfigHelper.currentLogEvent;
    }
    
    static AsyncLoggerConfig access$400(final AsyncLoggerConfigHelper asyncLoggerConfigHelper) {
        return asyncLoggerConfigHelper.asyncLoggerConfig;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        AsyncLoggerConfigHelper.threadFactory = new DaemonThreadFactory("AsyncLoggerConfig-");
        FACTORY = (EventFactory)new EventFactory() {
            public Log4jEventWrapper newInstance() {
                return new Log4jEventWrapper(null);
            }
            
            public Object newInstance() {
                return this.newInstance();
            }
        };
    }
    
    private static class Log4jEventWrapperHandler implements SequenceReportingEventHandler
    {
        private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
        private Sequence sequenceCallback;
        private int counter;
        
        private Log4jEventWrapperHandler() {
        }
        
        public void setSequenceCallback(final Sequence sequenceCallback) {
            this.sequenceCallback = sequenceCallback;
        }
        
        public void onEvent(final Log4jEventWrapper log4jEventWrapper, final long n, final boolean endOfBatch) throws Exception {
            Log4jEventWrapper.access$100(log4jEventWrapper).setEndOfBatch(endOfBatch);
            Log4jEventWrapper.access$300(log4jEventWrapper).asyncCallAppenders(Log4jEventWrapper.access$100(log4jEventWrapper));
            log4jEventWrapper.clear();
            if (++this.counter > 50) {
                this.sequenceCallback.set(n);
                this.counter = 0;
            }
        }
        
        public void onEvent(final Object o, final long n, final boolean b) throws Exception {
            this.onEvent((Log4jEventWrapper)o, n, b);
        }
        
        Log4jEventWrapperHandler(final AsyncLoggerConfigHelper$1 eventFactory) {
            this();
        }
    }
    
    private static class Log4jEventWrapper
    {
        private AsyncLoggerConfig loggerConfig;
        private LogEvent event;
        
        private Log4jEventWrapper() {
        }
        
        public void clear() {
            this.loggerConfig = null;
            this.event = null;
        }
        
        Log4jEventWrapper(final AsyncLoggerConfigHelper$1 eventFactory) {
            this();
        }
        
        static LogEvent access$102(final Log4jEventWrapper log4jEventWrapper, final LogEvent event) {
            return log4jEventWrapper.event = event;
        }
        
        static AsyncLoggerConfig access$302(final Log4jEventWrapper log4jEventWrapper, final AsyncLoggerConfig loggerConfig) {
            return log4jEventWrapper.loggerConfig = loggerConfig;
        }
        
        static LogEvent access$100(final Log4jEventWrapper log4jEventWrapper) {
            return log4jEventWrapper.event;
        }
        
        static AsyncLoggerConfig access$300(final Log4jEventWrapper log4jEventWrapper) {
            return log4jEventWrapper.loggerConfig;
        }
    }
}
