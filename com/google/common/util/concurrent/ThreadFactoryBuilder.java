package com.google.common.util.concurrent;

import com.google.common.base.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public final class ThreadFactoryBuilder
{
    private String nameFormat;
    private Boolean daemon;
    private Integer priority;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private ThreadFactory backingThreadFactory;
    
    public ThreadFactoryBuilder() {
        this.nameFormat = null;
        this.daemon = null;
        this.priority = null;
        this.uncaughtExceptionHandler = null;
        this.backingThreadFactory = null;
    }
    
    public ThreadFactoryBuilder setNameFormat(final String nameFormat) {
        String.format(nameFormat, 0);
        this.nameFormat = nameFormat;
        return this;
    }
    
    public ThreadFactoryBuilder setDaemon(final boolean b) {
        this.daemon = b;
        return this;
    }
    
    public ThreadFactoryBuilder setPriority(final int n) {
        Preconditions.checkArgument(n >= 1, "Thread priority (%s) must be >= %s", n, 1);
        Preconditions.checkArgument(n <= 10, "Thread priority (%s) must be <= %s", n, 10);
        this.priority = n;
        return this;
    }
    
    public ThreadFactoryBuilder setUncaughtExceptionHandler(final Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }
    
    public ThreadFactoryBuilder setThreadFactory(final ThreadFactory threadFactory) {
        this.backingThreadFactory = (ThreadFactory)Preconditions.checkNotNull(threadFactory);
        return this;
    }
    
    public ThreadFactory build() {
        return build(this);
    }
    
    private static ThreadFactory build(final ThreadFactoryBuilder threadFactoryBuilder) {
        final String nameFormat = threadFactoryBuilder.nameFormat;
        return new ThreadFactory((threadFactoryBuilder.backingThreadFactory != null) ? threadFactoryBuilder.backingThreadFactory : Executors.defaultThreadFactory(), nameFormat, (nameFormat != null) ? new AtomicLong(0L) : null, threadFactoryBuilder.daemon, threadFactoryBuilder.priority, threadFactoryBuilder.uncaughtExceptionHandler) {
            final ThreadFactory val$backingThreadFactory;
            final String val$nameFormat;
            final AtomicLong val$count;
            final Boolean val$daemon;
            final Integer val$priority;
            final Thread.UncaughtExceptionHandler val$uncaughtExceptionHandler;
            
            @Override
            public Thread newThread(final Runnable runnable) {
                final Thread thread = this.val$backingThreadFactory.newThread(runnable);
                if (this.val$nameFormat != null) {
                    thread.setName(String.format(this.val$nameFormat, this.val$count.getAndIncrement()));
                }
                if (this.val$daemon != null) {
                    thread.setDaemon(this.val$daemon);
                }
                if (this.val$priority != null) {
                    thread.setPriority(this.val$priority);
                }
                if (this.val$uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(this.val$uncaughtExceptionHandler);
                }
                return thread;
            }
        };
    }
}
