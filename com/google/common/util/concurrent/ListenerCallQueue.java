package com.google.common.util.concurrent;

import java.util.logging.*;
import java.util.concurrent.*;
import javax.annotation.concurrent.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

final class ListenerCallQueue implements Runnable
{
    private static final Logger logger;
    private final Object listener;
    private final Executor executor;
    @GuardedBy("this")
    private final Queue waitQueue;
    @GuardedBy("this")
    private boolean isThreadScheduled;
    
    ListenerCallQueue(final Object o, final Executor executor) {
        this.waitQueue = Queues.newArrayDeque();
        this.listener = Preconditions.checkNotNull(o);
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }
    
    synchronized void add(final Callback callback) {
        this.waitQueue.add(callback);
    }
    
    void execute() {
        // monitorenter(this)
        if (!this.isThreadScheduled) {
            this.isThreadScheduled = true;
        }
        // monitorexit(this)
        if (true) {
            this.executor.execute(this);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            // monitorenter(this)
            Preconditions.checkState(this.isThreadScheduled);
            final Callback callback = this.waitQueue.poll();
            if (callback == null) {
                break;
            }
            // monitorexit(this)
            callback.call(this.listener);
        }
        this.isThreadScheduled = false;
        // monitorexit(this)
        if (false) {
            // monitorenter(this)
            this.isThreadScheduled = false;
        }
        // monitorexit(this)
    }
    
    static {
        logger = Logger.getLogger(ListenerCallQueue.class.getName());
    }
    
    abstract static class Callback
    {
        private final String methodCall;
        
        Callback(final String methodCall) {
            this.methodCall = methodCall;
        }
        
        abstract void call(final Object p0);
        
        void enqueueOn(final Iterable iterable) {
            final Iterator<ListenerCallQueue> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                iterator.next().add(this);
            }
        }
        
        static String access$000(final Callback callback) {
            return callback.methodCall;
        }
    }
}
