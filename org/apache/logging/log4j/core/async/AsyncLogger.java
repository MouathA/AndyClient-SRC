package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.status.*;
import com.lmax.disruptor.util.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import com.lmax.disruptor.dsl.*;
import java.util.concurrent.*;
import com.lmax.disruptor.*;

public class AsyncLogger extends Logger
{
    private static final int HALF_A_SECOND = 500;
    private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
    private static final int RINGBUFFER_MIN_SIZE = 128;
    private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
    private static final StatusLogger LOGGER;
    private static Disruptor disruptor;
    private static Clock clock;
    private static ExecutorService executor;
    private final ThreadLocal threadlocalInfo;
    
    private static int calculateRingBufferSize() {
        final String property = System.getProperty("AsyncLogger.RingBufferSize", String.valueOf(262144));
        Integer.parseInt(property);
        if (128 < 128) {
            AsyncLogger.LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", property, 128);
        }
        return Util.ceilingNextPowerOfTwo(262144);
    }
    
    private static WaitStrategy createWaitStrategy() {
        final String property = System.getProperty("AsyncLogger.WaitStrategy");
        AsyncLogger.LOGGER.debug("property AsyncLogger.WaitStrategy={}", property);
        if ("Sleep".equals(property)) {
            AsyncLogger.LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
            return (WaitStrategy)new SleepingWaitStrategy();
        }
        if ("Yield".equals(property)) {
            AsyncLogger.LOGGER.debug("disruptor event handler uses YieldingWaitStrategy");
            return (WaitStrategy)new YieldingWaitStrategy();
        }
        if ("Block".equals(property)) {
            AsyncLogger.LOGGER.debug("disruptor event handler uses BlockingWaitStrategy");
            return (WaitStrategy)new BlockingWaitStrategy();
        }
        AsyncLogger.LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
        return (WaitStrategy)new SleepingWaitStrategy();
    }
    
    private static ExceptionHandler getExceptionHandler() {
        final String property = System.getProperty("AsyncLogger.ExceptionHandler");
        if (property == null) {
            AsyncLogger.LOGGER.debug("No AsyncLogger.ExceptionHandler specified");
            return null;
        }
        final ExceptionHandler exceptionHandler = (ExceptionHandler)Class.forName(property).newInstance();
        AsyncLogger.LOGGER.debug("AsyncLogger.ExceptionHandler=" + exceptionHandler);
        return exceptionHandler;
    }
    
    public AsyncLogger(final LoggerContext loggerContext, final String s, final MessageFactory messageFactory) {
        super(loggerContext, s, messageFactory);
        this.threadlocalInfo = new ThreadLocal();
    }
    
    @Override
    public void log(final Marker marker, final String s, final Level level, final Message message, final Throwable t) {
        Info info = this.threadlocalInfo.get();
        if (info == null) {
            info = new Info(null);
            Info.access$102(info, new RingBufferLogEventTranslator());
            Info.access$202(info, Thread.currentThread().getName());
            this.threadlocalInfo.set(info);
        }
        Info.access$100(info).setValues(this, this.getName(), marker, s, level, message, t, ThreadContext.getImmutableContext(), ThreadContext.getImmutableStack(), Info.access$200(info), this.config.loggerConfig.isIncludeLocation() ? this.location(s) : null, AsyncLogger.clock.currentTimeMillis());
        AsyncLogger.disruptor.publishEvent((EventTranslator)Info.access$100(info));
    }
    
    private StackTraceElement location(final String s) {
        return Log4jLogEvent.calcLocation(s);
    }
    
    public void actualAsyncLog(final RingBufferLogEvent ringBufferLogEvent) {
        ringBufferLogEvent.mergePropertiesIntoContextMap(this.config.loggerConfig.getProperties(), this.config.config.getStrSubstitutor());
        this.config.logEvent(ringBufferLogEvent);
    }
    
    public static void stop() {
        final Disruptor disruptor = AsyncLogger.disruptor;
        AsyncLogger.disruptor = null;
        disruptor.shutdown();
        final RingBuffer ringBuffer = disruptor.getRingBuffer();
        while (0 < 20 && !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize())) {
            Thread.sleep(500L);
            int n = 0;
            ++n;
        }
        AsyncLogger.executor.shutdown();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        AsyncLogger.clock = ClockFactory.getClock();
        AsyncLogger.executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory("AsyncLogger-"));
        AsyncLogger.disruptor = new Disruptor((EventFactory)RingBufferLogEvent.FACTORY, calculateRingBufferSize(), (Executor)AsyncLogger.executor, ProducerType.MULTI, createWaitStrategy());
        final RingBufferLogEventHandler[] array = { new RingBufferLogEventHandler() };
        AsyncLogger.disruptor.handleExceptionsWith(getExceptionHandler());
        AsyncLogger.disruptor.handleEventsWith((EventHandler[])array);
        AsyncLogger.LOGGER.debug("Starting AsyncLogger disruptor with ringbuffer size {}...", AsyncLogger.disruptor.getRingBuffer().getBufferSize());
        AsyncLogger.disruptor.start();
    }
    
    private static class Info
    {
        private RingBufferLogEventTranslator translator;
        private String cachedThreadName;
        
        private Info() {
        }
        
        Info(final AsyncLogger$1 object) {
            this();
        }
        
        static RingBufferLogEventTranslator access$102(final Info info, final RingBufferLogEventTranslator translator) {
            return info.translator = translator;
        }
        
        static String access$202(final Info info, final String cachedThreadName) {
            return info.cachedThreadName = cachedThreadName;
        }
        
        static String access$200(final Info info) {
            return info.cachedThreadName;
        }
        
        static RingBufferLogEventTranslator access$100(final Info info) {
            return info.translator;
        }
    }
}
