package com.google.common.util.concurrent;

import java.util.logging.*;
import com.google.common.annotations.*;
import javax.annotation.concurrent.*;
import java.util.concurrent.*;
import com.google.common.base.*;
import javax.annotation.*;

public final class ExecutionList
{
    @VisibleForTesting
    static final Logger log;
    @GuardedBy("this")
    private RunnableExecutorPair runnables;
    @GuardedBy("this")
    private boolean executed;
    
    public void add(final Runnable runnable, final Executor executor) {
        Preconditions.checkNotNull(runnable, (Object)"Runnable was null.");
        Preconditions.checkNotNull(executor, (Object)"Executor was null.");
        // monitorenter(this)
        if (!this.executed) {
            this.runnables = new RunnableExecutorPair(runnable, executor, this.runnables);
            // monitorexit(this)
            return;
        }
        // monitorexit(this)
        executeListener(runnable, executor);
    }
    
    public void execute() {
        // monitorenter(this)
        if (this.executed) {
            // monitorexit(this)
            return;
        }
        this.executed = true;
        RunnableExecutorPair runnableExecutorPair = this.runnables;
        this.runnables = null;
        // monitorexit(this)
        RunnableExecutorPair next;
        RunnableExecutorPair runnableExecutorPair2;
        for (next = null; runnableExecutorPair != null; runnableExecutorPair = runnableExecutorPair.next, runnableExecutorPair2.next = next, next = runnableExecutorPair2) {
            runnableExecutorPair2 = runnableExecutorPair;
        }
        while (next != null) {
            executeListener(next.runnable, next.executor);
            next = next.next;
        }
    }
    
    private static void executeListener(final Runnable runnable, final Executor executor) {
        executor.execute(runnable);
    }
    
    static {
        log = Logger.getLogger(ExecutionList.class.getName());
    }
    
    private static final class RunnableExecutorPair
    {
        final Runnable runnable;
        final Executor executor;
        @Nullable
        RunnableExecutorPair next;
        
        RunnableExecutorPair(final Runnable runnable, final Executor executor, final RunnableExecutorPair next) {
            this.runnable = runnable;
            this.executor = executor;
            this.next = next;
        }
    }
}
