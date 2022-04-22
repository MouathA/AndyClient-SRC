package io.netty.util.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import io.netty.util.internal.*;
import java.util.*;

public class DefaultThreadFactory implements ThreadFactory
{
    private static final AtomicInteger poolId;
    private final AtomicInteger nextId;
    private final String prefix;
    private final boolean daemon;
    private final int priority;
    
    public DefaultThreadFactory(final Class clazz) {
        this(clazz, false, 5);
    }
    
    public DefaultThreadFactory(final String s) {
        this(s, false, 5);
    }
    
    public DefaultThreadFactory(final Class clazz, final boolean b) {
        this(clazz, b, 5);
    }
    
    public DefaultThreadFactory(final String s, final boolean b) {
        this(s, b, 5);
    }
    
    public DefaultThreadFactory(final Class clazz, final int n) {
        this(clazz, false, n);
    }
    
    public DefaultThreadFactory(final String s, final int n) {
        this(s, false, n);
    }
    
    public DefaultThreadFactory(final Class clazz, final boolean b, final int n) {
        this(toPoolName(clazz), b, n);
    }
    
    private static String toPoolName(final Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("poolType");
        }
        final String simpleClassName = StringUtil.simpleClassName(clazz);
        switch (simpleClassName.length()) {
            case 0: {
                return "unknown";
            }
            case 1: {
                return simpleClassName.toLowerCase(Locale.US);
            }
            default: {
                if (Character.isUpperCase(simpleClassName.charAt(0)) && Character.isLowerCase(simpleClassName.charAt(1))) {
                    return Character.toLowerCase(simpleClassName.charAt(0)) + simpleClassName.substring(1);
                }
                return simpleClassName;
            }
        }
    }
    
    public DefaultThreadFactory(final String s, final boolean daemon, final int priority) {
        this.nextId = new AtomicInteger();
        if (s == null) {
            throw new NullPointerException("poolName");
        }
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("priority: " + priority + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
        }
        this.prefix = s + '-' + DefaultThreadFactory.poolId.incrementAndGet() + '-';
        this.daemon = daemon;
        this.priority = priority;
    }
    
    @Override
    public Thread newThread(final Runnable runnable) {
        final Thread thread = this.newThread(new DefaultRunnableDecorator(runnable), this.prefix + this.nextId.incrementAndGet());
        if (thread.isDaemon()) {
            if (!this.daemon) {
                thread.setDaemon(false);
            }
        }
        else if (this.daemon) {
            thread.setDaemon(true);
        }
        if (thread.getPriority() != this.priority) {
            thread.setPriority(this.priority);
        }
        return thread;
    }
    
    protected Thread newThread(final Runnable runnable, final String s) {
        return new FastThreadLocalThread(runnable, s);
    }
    
    static {
        poolId = new AtomicInteger();
    }
    
    private static final class DefaultRunnableDecorator implements Runnable
    {
        private final Runnable r;
        
        DefaultRunnableDecorator(final Runnable r) {
            this.r = r;
        }
        
        @Override
        public void run() {
            this.r.run();
        }
    }
}
