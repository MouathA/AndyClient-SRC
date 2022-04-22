package io.netty.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.*;

final class ScheduledFutureTask extends PromiseTask implements ScheduledFuture
{
    private static final AtomicLong nextTaskId;
    private static final long START_TIME;
    private final long id;
    private final Queue delayedTaskQueue;
    private long deadlineNanos;
    private final long periodNanos;
    static final boolean $assertionsDisabled;
    
    static long nanoTime() {
        return System.nanoTime() - ScheduledFutureTask.START_TIME;
    }
    
    static long deadlineNanos(final long n) {
        return nanoTime() + n;
    }
    
    ScheduledFutureTask(final EventExecutor eventExecutor, final Queue queue, final Runnable runnable, final Object o, final long n) {
        this(eventExecutor, queue, PromiseTask.toCallable(runnable, o), n);
    }
    
    ScheduledFutureTask(final EventExecutor eventExecutor, final Queue delayedTaskQueue, final Callable callable, final long deadlineNanos, final long periodNanos) {
        super(eventExecutor, callable);
        this.id = ScheduledFutureTask.nextTaskId.getAndIncrement();
        if (periodNanos == 0L) {
            throw new IllegalArgumentException("period: 0 (expected: != 0)");
        }
        this.delayedTaskQueue = delayedTaskQueue;
        this.deadlineNanos = deadlineNanos;
        this.periodNanos = periodNanos;
    }
    
    ScheduledFutureTask(final EventExecutor eventExecutor, final Queue delayedTaskQueue, final Callable callable, final long deadlineNanos) {
        super(eventExecutor, callable);
        this.id = ScheduledFutureTask.nextTaskId.getAndIncrement();
        this.delayedTaskQueue = delayedTaskQueue;
        this.deadlineNanos = deadlineNanos;
        this.periodNanos = 0L;
    }
    
    @Override
    protected EventExecutor executor() {
        return super.executor();
    }
    
    public long deadlineNanos() {
        return this.deadlineNanos;
    }
    
    public long delayNanos() {
        return Math.max(0L, this.deadlineNanos() - nanoTime());
    }
    
    public long delayNanos(final long n) {
        return Math.max(0L, this.deadlineNanos() - (n - ScheduledFutureTask.START_TIME));
    }
    
    @Override
    public long getDelay(final TimeUnit timeUnit) {
        return timeUnit.convert(this.delayNanos(), TimeUnit.NANOSECONDS);
    }
    
    @Override
    public int compareTo(final Delayed delayed) {
        if (this == delayed) {
            return 0;
        }
        final ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask)delayed;
        final long n = this.deadlineNanos() - scheduledFutureTask.deadlineNanos();
        if (n < 0L) {
            return -1;
        }
        if (n > 0L) {
            return 1;
        }
        if (this.id < scheduledFutureTask.id) {
            return -1;
        }
        if (this.id == scheduledFutureTask.id) {
            throw new Error();
        }
        return 1;
    }
    
    @Override
    public void run() {
        assert this.executor().inEventLoop();
        if (this.periodNanos == 0L) {
            if (this.setUncancellableInternal()) {
                this.setSuccessInternal(this.task.call());
            }
        }
        else if (!this.isCancelled()) {
            this.task.call();
            if (!this.executor().isShutdown()) {
                final long periodNanos = this.periodNanos;
                if (periodNanos > 0L) {
                    this.deadlineNanos += periodNanos;
                }
                else {
                    this.deadlineNanos = nanoTime() - periodNanos;
                }
                if (!this.isCancelled()) {
                    this.delayedTaskQueue.add(this);
                }
            }
        }
    }
    
    @Override
    protected StringBuilder toStringBuilder() {
        final StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        stringBuilder.append(" id: ");
        stringBuilder.append(this.id);
        stringBuilder.append(", deadline: ");
        stringBuilder.append(this.deadlineNanos);
        stringBuilder.append(", period: ");
        stringBuilder.append(this.periodNanos);
        stringBuilder.append(')');
        return stringBuilder;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Delayed)o);
    }
    
    static {
        $assertionsDisabled = !ScheduledFutureTask.class.desiredAssertionStatus();
        nextTaskId = new AtomicLong();
        START_TIME = System.nanoTime();
    }
}
