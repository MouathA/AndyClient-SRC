package org.apache.commons.lang3.concurrent;

import java.util.concurrent.*;

public class TimedSemaphore
{
    public static final int NO_LIMIT = 0;
    private static final int THREAD_POOL_SIZE = 1;
    private final ScheduledExecutorService executorService;
    private final long period;
    private final TimeUnit unit;
    private final boolean ownExecutor;
    private ScheduledFuture task;
    private long totalAcquireCount;
    private long periodCount;
    private int limit;
    private int acquireCount;
    private int lastCallsPerPeriod;
    private boolean shutdown;
    
    public TimedSemaphore(final long n, final TimeUnit timeUnit, final int n2) {
        this(null, n, timeUnit, n2);
    }
    
    public TimedSemaphore(final ScheduledExecutorService executorService, final long period, final TimeUnit unit, final int limit) {
        if (period <= 0L) {
            throw new IllegalArgumentException("Time period must be greater 0!");
        }
        this.period = period;
        this.unit = unit;
        if (executorService != null) {
            this.executorService = executorService;
            this.ownExecutor = false;
        }
        else {
            final ScheduledThreadPoolExecutor executorService2 = new ScheduledThreadPoolExecutor(1);
            executorService2.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
            executorService2.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            this.executorService = executorService2;
            this.ownExecutor = true;
        }
        this.setLimit(limit);
    }
    
    public final synchronized int getLimit() {
        return this.limit;
    }
    
    public final synchronized void setLimit(final int limit) {
        this.limit = limit;
    }
    
    public synchronized void shutdown() {
        if (!this.shutdown) {
            if (this.ownExecutor) {
                this.getExecutorService().shutdownNow();
            }
            if (this.task != null) {
                this.task.cancel(false);
            }
            this.shutdown = true;
        }
    }
    
    public synchronized boolean isShutdown() {
        return this.shutdown;
    }
    
    public synchronized void acquire() throws InterruptedException {
        if (this.isShutdown()) {
            throw new IllegalStateException("TimedSemaphore is shut down!");
        }
        if (this.task == null) {
            this.task = this.startTimer();
        }
        final boolean b = this.getLimit() <= 0 || this.acquireCount < this.getLimit();
        this.wait();
    }
    
    public synchronized int getLastAcquiresPerPeriod() {
        return this.lastCallsPerPeriod;
    }
    
    public synchronized int getAcquireCount() {
        return this.acquireCount;
    }
    
    public synchronized int getAvailablePermits() {
        return this.getLimit() - this.getAcquireCount();
    }
    
    public synchronized double getAverageCallsPerPeriod() {
        return (this.periodCount == 0L) ? 0.0 : (this.totalAcquireCount / (double)this.periodCount);
    }
    
    public long getPeriod() {
        return this.period;
    }
    
    public TimeUnit getUnit() {
        return this.unit;
    }
    
    protected ScheduledExecutorService getExecutorService() {
        return this.executorService;
    }
    
    protected ScheduledFuture startTimer() {
        return this.getExecutorService().scheduleAtFixedRate(new Runnable() {
            final TimedSemaphore this$0;
            
            @Override
            public void run() {
                this.this$0.endOfPeriod();
            }
        }, this.getPeriod(), this.getPeriod(), this.getUnit());
    }
    
    synchronized void endOfPeriod() {
        this.lastCallsPerPeriod = this.acquireCount;
        this.totalAcquireCount += this.acquireCount;
        ++this.periodCount;
        this.acquireCount = 0;
        this.notifyAll();
    }
}
