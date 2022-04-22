package com.google.common.util.concurrent;

import java.util.concurrent.*;
import java.util.logging.*;
import javax.annotation.concurrent.*;
import java.util.*;
import com.google.common.base.*;

final class SerializingExecutor implements Executor
{
    private static final Logger log;
    private final Executor executor;
    @GuardedBy("internalLock")
    private final Queue waitQueue;
    @GuardedBy("internalLock")
    private boolean isThreadScheduled;
    private final TaskRunner taskRunner;
    private final Object internalLock;
    
    public SerializingExecutor(final Executor executor) {
        this.waitQueue = new ArrayDeque();
        this.isThreadScheduled = false;
        this.taskRunner = new TaskRunner(null);
        this.internalLock = new Object() {
            final SerializingExecutor this$0;
            
            @Override
            public String toString() {
                return "SerializingExecutor lock: " + super.toString();
            }
        };
        Preconditions.checkNotNull(executor, (Object)"'executor' must not be null.");
        this.executor = executor;
    }
    
    @Override
    public void execute(final Runnable runnable) {
        Preconditions.checkNotNull(runnable, (Object)"'r' must not be null.");
        // monitorenter(internalLock = this.internalLock)
        this.waitQueue.add(runnable);
        if (!this.isThreadScheduled) {
            this.isThreadScheduled = true;
        }
        // monitorexit(internalLock)
        if (true) {
            this.executor.execute(this.taskRunner);
            if (false) {
                // monitorenter(internalLock2 = this.internalLock)
                this.isThreadScheduled = false;
            }
            // monitorexit(internalLock2)
        }
    }
    
    static boolean access$100(final SerializingExecutor serializingExecutor) {
        return serializingExecutor.isThreadScheduled;
    }
    
    static Object access$200(final SerializingExecutor serializingExecutor) {
        return serializingExecutor.internalLock;
    }
    
    static Queue access$300(final SerializingExecutor serializingExecutor) {
        return serializingExecutor.waitQueue;
    }
    
    static boolean access$102(final SerializingExecutor serializingExecutor, final boolean isThreadScheduled) {
        return serializingExecutor.isThreadScheduled = isThreadScheduled;
    }
    
    static Logger access$400() {
        return SerializingExecutor.log;
    }
    
    static {
        log = Logger.getLogger(SerializingExecutor.class.getName());
    }
    
    private class TaskRunner implements Runnable
    {
        final SerializingExecutor this$0;
        
        private TaskRunner(final SerializingExecutor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            while (true) {
                Preconditions.checkState(SerializingExecutor.access$100(this.this$0));
                // monitorenter(access$200 = SerializingExecutor.access$200(this.this$0))
                final Runnable runnable = SerializingExecutor.access$300(this.this$0).poll();
                if (runnable == null) {
                    break;
                }
                // monitorexit(access$200)
                runnable.run();
            }
            SerializingExecutor.access$102(this.this$0, false);
        }
        // monitorexit(access$200)
        
        TaskRunner(final SerializingExecutor serializingExecutor, final SerializingExecutor$1 object) {
            this(serializingExecutor);
        }
    }
}
