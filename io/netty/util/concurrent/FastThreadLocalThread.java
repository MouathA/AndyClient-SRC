package io.netty.util.concurrent;

import io.netty.util.internal.*;

public class FastThreadLocalThread extends Thread
{
    private InternalThreadLocalMap threadLocalMap;
    
    public FastThreadLocalThread() {
    }
    
    public FastThreadLocalThread(final Runnable runnable) {
        super(runnable);
    }
    
    public FastThreadLocalThread(final ThreadGroup threadGroup, final Runnable runnable) {
        super(threadGroup, runnable);
    }
    
    public FastThreadLocalThread(final String s) {
        super(s);
    }
    
    public FastThreadLocalThread(final ThreadGroup threadGroup, final String s) {
        super(threadGroup, s);
    }
    
    public FastThreadLocalThread(final Runnable runnable, final String s) {
        super(runnable, s);
    }
    
    public FastThreadLocalThread(final ThreadGroup threadGroup, final Runnable runnable, final String s) {
        super(threadGroup, runnable, s);
    }
    
    public FastThreadLocalThread(final ThreadGroup threadGroup, final Runnable runnable, final String s, final long n) {
        super(threadGroup, runnable, s, n);
    }
    
    public final InternalThreadLocalMap threadLocalMap() {
        return this.threadLocalMap;
    }
    
    public final void setThreadLocalMap(final InternalThreadLocalMap threadLocalMap) {
        this.threadLocalMap = threadLocalMap;
    }
}
