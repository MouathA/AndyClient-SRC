package io.netty.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;
import java.util.*;

public final class GlobalEventExecutor extends AbstractEventExecutor
{
    private static final InternalLogger logger;
    private static final long SCHEDULE_PURGE_INTERVAL;
    public static final GlobalEventExecutor INSTANCE;
    final BlockingQueue taskQueue;
    final Queue delayedTaskQueue;
    final ScheduledFutureTask purgeTask;
    private final ThreadFactory threadFactory;
    private final TaskRunner taskRunner;
    private final AtomicBoolean started;
    Thread thread;
    private final Future terminationFuture;
    
    private GlobalEventExecutor() {
        this.taskQueue = new LinkedBlockingQueue();
        this.delayedTaskQueue = new PriorityQueue();
        this.purgeTask = new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(null), (Object)null), ScheduledFutureTask.deadlineNanos(GlobalEventExecutor.SCHEDULE_PURGE_INTERVAL), -GlobalEventExecutor.SCHEDULE_PURGE_INTERVAL);
        this.threadFactory = new DefaultThreadFactory(this.getClass());
        this.taskRunner = new TaskRunner();
        this.started = new AtomicBoolean();
        this.terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
        this.delayedTaskQueue.add(this.purgeTask);
    }
    
    @Override
    public EventExecutorGroup parent() {
        return null;
    }
    
    Runnable takeTask() {
        final BlockingQueue taskQueue = this.taskQueue;
        while (true) {
            final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
            if (scheduledFutureTask == null) {
                return taskQueue.take();
            }
            final long delayNanos = scheduledFutureTask.delayNanos();
            Runnable runnable;
            if (delayNanos > 0L) {
                runnable = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
            }
            else {
                runnable = taskQueue.poll();
            }
            if (runnable == null) {
                this.fetchFromDelayedQueue();
                runnable = taskQueue.poll();
            }
            if (runnable != null) {
                return runnable;
            }
        }
    }
    
    private void fetchFromDelayedQueue() {
        long nanoTime = 0L;
        while (true) {
            final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
            if (scheduledFutureTask == null) {
                break;
            }
            if (nanoTime == 0L) {
                nanoTime = ScheduledFutureTask.nanoTime();
            }
            if (scheduledFutureTask.deadlineNanos() > nanoTime) {
                break;
            }
            this.delayedTaskQueue.remove();
            this.taskQueue.add(scheduledFutureTask);
        }
    }
    
    public int pendingTasks() {
        return this.taskQueue.size();
    }
    
    private void addTask(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        this.taskQueue.add(runnable);
    }
    
    @Override
    public boolean inEventLoop(final Thread thread) {
        return thread == this.thread;
    }
    
    @Override
    public Future shutdownGracefully(final long n, final long n2, final TimeUnit timeUnit) {
        return this.terminationFuture();
    }
    
    @Override
    public Future terminationFuture() {
        return this.terminationFuture;
    }
    
    @Deprecated
    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isShuttingDown() {
        return false;
    }
    
    @Override
    public boolean isShutdown() {
        return false;
    }
    
    @Override
    public boolean isTerminated() {
        return false;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) {
        return false;
    }
    
    public boolean awaitInactivity(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        final Thread thread = this.thread;
        if (thread == null) {
            throw new IllegalStateException("thread was not started");
        }
        thread.join(timeUnit.toMillis(n));
        return !thread.isAlive();
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        this.addTask(runnable);
        if (!this.inEventLoop()) {
            this.startThread();
        }
    }
    
    @Override
    public ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", n));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, runnable, null, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n))));
    }
    
    @Override
    public ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        if (callable == null) {
            throw new NullPointerException("callable");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", n));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n))));
    }
    
    @Override
    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", n));
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", n2));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(runnable, (Object)null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n)), timeUnit.toNanos(n2)));
    }
    
    @Override
    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", n));
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", n2));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(runnable, (Object)null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n)), -timeUnit.toNanos(n2)));
    }
    
    private ScheduledFuture schedule(final ScheduledFutureTask scheduledFutureTask) {
        if (scheduledFutureTask == null) {
            throw new NullPointerException("task");
        }
        if (this.inEventLoop()) {
            this.delayedTaskQueue.add(scheduledFutureTask);
        }
        else {
            this.execute(new Runnable(scheduledFutureTask) {
                final ScheduledFutureTask val$task;
                final GlobalEventExecutor this$0;
                
                @Override
                public void run() {
                    this.this$0.delayedTaskQueue.add(this.val$task);
                }
            });
        }
        return scheduledFutureTask;
    }
    
    private void startThread() {
        if (this.started.compareAndSet(false, true)) {
            final Thread thread = this.threadFactory.newThread(this.taskRunner);
            thread.start();
            this.thread = thread;
        }
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleWithFixedDelay(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleAtFixedRate(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        return this.schedule(callable, n, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        return this.schedule(runnable, n, timeUnit);
    }
    
    static InternalLogger access$100() {
        return GlobalEventExecutor.logger;
    }
    
    static AtomicBoolean access$200(final GlobalEventExecutor globalEventExecutor) {
        return globalEventExecutor.started;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
        SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
        INSTANCE = new GlobalEventExecutor();
    }
    
    private final class PurgeTask implements Runnable
    {
        final GlobalEventExecutor this$0;
        
        private PurgeTask(final GlobalEventExecutor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            final Iterator iterator = this.this$0.delayedTaskQueue.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isCancelled()) {
                    iterator.remove();
                }
            }
        }
        
        PurgeTask(final GlobalEventExecutor globalEventExecutor, final GlobalEventExecutor$1 runnable) {
            this(globalEventExecutor);
        }
    }
    
    final class TaskRunner implements Runnable
    {
        static final boolean $assertionsDisabled;
        final GlobalEventExecutor this$0;
        
        TaskRunner(final GlobalEventExecutor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            while (true) {
                final Runnable takeTask = this.this$0.takeTask();
                if (takeTask != null) {
                    takeTask.run();
                    if (takeTask != this.this$0.purgeTask) {
                        continue;
                    }
                }
                if (this.this$0.taskQueue.isEmpty() && this.this$0.delayedTaskQueue.size() == 1) {
                    final boolean compareAndSet = GlobalEventExecutor.access$200(this.this$0).compareAndSet(true, false);
                    assert compareAndSet;
                    if (this.this$0.taskQueue.isEmpty() && this.this$0.delayedTaskQueue.size() == 1) {
                        break;
                    }
                    if (!GlobalEventExecutor.access$200(this.this$0).compareAndSet(false, true)) {
                        break;
                    }
                    continue;
                }
            }
        }
        
        static {
            $assertionsDisabled = !GlobalEventExecutor.class.desiredAssertionStatus();
        }
    }
}
