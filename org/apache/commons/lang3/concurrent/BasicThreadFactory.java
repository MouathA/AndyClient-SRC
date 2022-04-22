package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.commons.lang3.builder.*;

public class BasicThreadFactory implements ThreadFactory
{
    private final AtomicLong threadCounter;
    private final ThreadFactory wrappedFactory;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final String namingPattern;
    private final Integer priority;
    private final Boolean daemonFlag;
    
    private BasicThreadFactory(final Builder builder) {
        if (Builder.access$000(builder) == null) {
            this.wrappedFactory = Executors.defaultThreadFactory();
        }
        else {
            this.wrappedFactory = Builder.access$000(builder);
        }
        this.namingPattern = Builder.access$100(builder);
        this.priority = Builder.access$200(builder);
        this.daemonFlag = Builder.access$300(builder);
        this.uncaughtExceptionHandler = Builder.access$400(builder);
        this.threadCounter = new AtomicLong();
    }
    
    public final ThreadFactory getWrappedFactory() {
        return this.wrappedFactory;
    }
    
    public final String getNamingPattern() {
        return this.namingPattern;
    }
    
    public final Boolean getDaemonFlag() {
        return this.daemonFlag;
    }
    
    public final Integer getPriority() {
        return this.priority;
    }
    
    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.uncaughtExceptionHandler;
    }
    
    public long getThreadCount() {
        return this.threadCounter.get();
    }
    
    @Override
    public Thread newThread(final Runnable runnable) {
        final Thread thread = this.getWrappedFactory().newThread(runnable);
        this.initializeThread(thread);
        return thread;
    }
    
    private void initializeThread(final Thread thread) {
        if (this.getNamingPattern() != null) {
            thread.setName(String.format(this.getNamingPattern(), this.threadCounter.incrementAndGet()));
        }
        if (this.getUncaughtExceptionHandler() != null) {
            thread.setUncaughtExceptionHandler(this.getUncaughtExceptionHandler());
        }
        if (this.getPriority() != null) {
            thread.setPriority(this.getPriority());
        }
        if (this.getDaemonFlag() != null) {
            thread.setDaemon(this.getDaemonFlag());
        }
    }
    
    BasicThreadFactory(final Builder builder, final BasicThreadFactory$1 object) {
        this(builder);
    }
    
    public static class Builder implements org.apache.commons.lang3.builder.Builder
    {
        private ThreadFactory wrappedFactory;
        private Thread.UncaughtExceptionHandler exceptionHandler;
        private String namingPattern;
        private Integer priority;
        private Boolean daemonFlag;
        
        public Builder wrappedFactory(final ThreadFactory wrappedFactory) {
            if (wrappedFactory == null) {
                throw new NullPointerException("Wrapped ThreadFactory must not be null!");
            }
            this.wrappedFactory = wrappedFactory;
            return this;
        }
        
        public Builder namingPattern(final String namingPattern) {
            if (namingPattern == null) {
                throw new NullPointerException("Naming pattern must not be null!");
            }
            this.namingPattern = namingPattern;
            return this;
        }
        
        public Builder daemon(final boolean b) {
            this.daemonFlag = b;
            return this;
        }
        
        public Builder priority(final int n) {
            this.priority = n;
            return this;
        }
        
        public Builder uncaughtExceptionHandler(final Thread.UncaughtExceptionHandler exceptionHandler) {
            if (exceptionHandler == null) {
                throw new NullPointerException("Uncaught exception handler must not be null!");
            }
            this.exceptionHandler = exceptionHandler;
            return this;
        }
        
        public void reset() {
            this.wrappedFactory = null;
            this.exceptionHandler = null;
            this.namingPattern = null;
            this.priority = null;
            this.daemonFlag = null;
        }
        
        @Override
        public BasicThreadFactory build() {
            final BasicThreadFactory basicThreadFactory = new BasicThreadFactory(this, null);
            this.reset();
            return basicThreadFactory;
        }
        
        @Override
        public Object build() {
            return this.build();
        }
        
        static ThreadFactory access$000(final Builder builder) {
            return builder.wrappedFactory;
        }
        
        static String access$100(final Builder builder) {
            return builder.namingPattern;
        }
        
        static Integer access$200(final Builder builder) {
            return builder.priority;
        }
        
        static Boolean access$300(final Builder builder) {
            return builder.daemonFlag;
        }
        
        static Thread.UncaughtExceptionHandler access$400(final Builder builder) {
            return builder.exceptionHandler;
        }
    }
}
