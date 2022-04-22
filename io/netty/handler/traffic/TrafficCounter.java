package io.netty.handler.traffic;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;

public class TrafficCounter
{
    private static final InternalLogger logger;
    private final AtomicLong currentWrittenBytes;
    private final AtomicLong currentReadBytes;
    private final AtomicLong cumulativeWrittenBytes;
    private final AtomicLong cumulativeReadBytes;
    private long lastCumulativeTime;
    private long lastWriteThroughput;
    private long lastReadThroughput;
    private final AtomicLong lastTime;
    private long lastWrittenBytes;
    private long lastReadBytes;
    private long lastNonNullWrittenBytes;
    private long lastNonNullWrittenTime;
    private long lastNonNullReadTime;
    private long lastNonNullReadBytes;
    final AtomicLong checkInterval;
    final String name;
    private final AbstractTrafficShapingHandler trafficShapingHandler;
    private final ScheduledExecutorService executor;
    private Runnable monitor;
    private ScheduledFuture scheduledFuture;
    final AtomicBoolean monitorActive;
    
    public synchronized void start() {
        if (this.monitorActive.get()) {
            return;
        }
        this.lastTime.set(System.currentTimeMillis());
        if (this.checkInterval.get() > 0L) {
            this.monitorActive.set(true);
            this.monitor = new TrafficMonitoringTask(this.trafficShapingHandler, this);
            this.scheduledFuture = this.executor.schedule(this.monitor, this.checkInterval.get(), TimeUnit.MILLISECONDS);
        }
    }
    
    public synchronized void stop() {
        if (!this.monitorActive.get()) {
            return;
        }
        this.monitorActive.set(false);
        this.resetAccounting(System.currentTimeMillis());
        if (this.trafficShapingHandler != null) {
            this.trafficShapingHandler.doAccounting(this);
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
    }
    
    synchronized void resetAccounting(final long n) {
        final long n2 = n - this.lastTime.getAndSet(n);
        if (n2 == 0L) {
            return;
        }
        if (TrafficCounter.logger.isDebugEnabled() && n2 > 2L * this.checkInterval()) {
            TrafficCounter.logger.debug("Acct schedule not ok: " + n2 + " > 2*" + this.checkInterval() + " from " + this.name);
        }
        this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
        this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
        this.lastReadThroughput = this.lastReadBytes / n2 * 1000L;
        this.lastWriteThroughput = this.lastWrittenBytes / n2 * 1000L;
        if (this.lastWrittenBytes > 0L) {
            this.lastNonNullWrittenBytes = this.lastWrittenBytes;
            this.lastNonNullWrittenTime = n;
        }
        if (this.lastReadBytes > 0L) {
            this.lastNonNullReadBytes = this.lastReadBytes;
            this.lastNonNullReadTime = n;
        }
    }
    
    public TrafficCounter(final AbstractTrafficShapingHandler trafficShapingHandler, final ScheduledExecutorService executor, final String name, final long n) {
        this.currentWrittenBytes = new AtomicLong();
        this.currentReadBytes = new AtomicLong();
        this.cumulativeWrittenBytes = new AtomicLong();
        this.cumulativeReadBytes = new AtomicLong();
        this.lastTime = new AtomicLong();
        this.checkInterval = new AtomicLong(1000L);
        this.monitorActive = new AtomicBoolean();
        this.trafficShapingHandler = trafficShapingHandler;
        this.executor = executor;
        this.name = name;
        this.lastCumulativeTime = System.currentTimeMillis();
        this.configure(n);
    }
    
    public void configure(final long n) {
        final long n2 = n / 10L * 10L;
        if (this.checkInterval.get() != n2) {
            this.checkInterval.set(n2);
            if (n2 <= 0L) {
                this.stop();
                this.lastTime.set(System.currentTimeMillis());
            }
            else {
                this.start();
            }
        }
    }
    
    void bytesRecvFlowControl(final long n) {
        this.currentReadBytes.addAndGet(n);
        this.cumulativeReadBytes.addAndGet(n);
    }
    
    void bytesWriteFlowControl(final long n) {
        this.currentWrittenBytes.addAndGet(n);
        this.cumulativeWrittenBytes.addAndGet(n);
    }
    
    public long checkInterval() {
        return this.checkInterval.get();
    }
    
    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }
    
    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }
    
    public long lastReadBytes() {
        return this.lastReadBytes;
    }
    
    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }
    
    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }
    
    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }
    
    public long lastTime() {
        return this.lastTime.get();
    }
    
    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }
    
    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }
    
    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }
    
    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0L);
        this.cumulativeWrittenBytes.set(0L);
    }
    
    public String name() {
        return this.name;
    }
    
    public synchronized long readTimeToWait(final long n, final long n2, final long n3) {
        final long currentTimeMillis = System.currentTimeMillis();
        this.bytesRecvFlowControl(n);
        if (n2 == 0L) {
            return 0L;
        }
        final long value = this.currentReadBytes.get();
        final long n4 = currentTimeMillis - this.lastTime.get();
        if (n4 <= 10L || value <= 0L) {
            if (this.lastNonNullReadBytes > 0L && this.lastNonNullReadTime + 10L < currentTimeMillis) {
                final long n5 = value + this.lastNonNullReadBytes;
                final long n6 = currentTimeMillis - this.lastNonNullReadTime;
                final long n7 = (n5 * 1000L / n2 - n6) / 10L * 10L;
                if (n7 > 10L) {
                    if (TrafficCounter.logger.isDebugEnabled()) {
                        TrafficCounter.logger.debug("Time: " + n7 + ":" + n5 + ":" + n6);
                    }
                    return (n7 > n3) ? n3 : n7;
                }
            }
            else {
                final long n8 = value + this.lastReadBytes;
                final long n9 = 10L;
                final long n10 = (n8 * 1000L / n2 - n9) / 10L * 10L;
                if (n10 > 10L) {
                    if (TrafficCounter.logger.isDebugEnabled()) {
                        TrafficCounter.logger.debug("Time: " + n10 + ":" + n8 + ":" + n9);
                    }
                    return (n10 > n3) ? n3 : n10;
                }
            }
            return 0L;
        }
        final long n11 = (value * 1000L / n2 - n4) / 10L * 10L;
        if (n11 > 10L) {
            if (TrafficCounter.logger.isDebugEnabled()) {
                TrafficCounter.logger.debug("Time: " + n11 + ":" + value + ":" + n4);
            }
            return (n11 > n3) ? n3 : n11;
        }
        return 0L;
    }
    
    public synchronized long writeTimeToWait(final long n, final long n2, final long n3) {
        this.bytesWriteFlowControl(n);
        if (n2 == 0L) {
            return 0L;
        }
        final long value = this.currentWrittenBytes.get();
        final long currentTimeMillis = System.currentTimeMillis();
        final long n4 = currentTimeMillis - this.lastTime.get();
        if (n4 <= 10L || value <= 0L) {
            if (this.lastNonNullWrittenBytes > 0L && this.lastNonNullWrittenTime + 10L < currentTimeMillis) {
                final long n5 = value + this.lastNonNullWrittenBytes;
                final long n6 = currentTimeMillis - this.lastNonNullWrittenTime;
                final long n7 = (n5 * 1000L / n2 - n6) / 10L * 10L;
                if (n7 > 10L) {
                    if (TrafficCounter.logger.isDebugEnabled()) {
                        TrafficCounter.logger.debug("Time: " + n7 + ":" + n5 + ":" + n6);
                    }
                    return (n7 > n3) ? n3 : n7;
                }
            }
            else {
                final long n8 = value + this.lastWrittenBytes;
                final long n9 = 10L + Math.abs(n4);
                final long n10 = (n8 * 1000L / n2 - n9) / 10L * 10L;
                if (n10 > 10L) {
                    if (TrafficCounter.logger.isDebugEnabled()) {
                        TrafficCounter.logger.debug("Time: " + n10 + ":" + n8 + ":" + n9);
                    }
                    return (n10 > n3) ? n3 : n10;
                }
            }
            return 0L;
        }
        final long n11 = (value * 1000L / n2 - n4) / 10L * 10L;
        if (n11 > 10L) {
            if (TrafficCounter.logger.isDebugEnabled()) {
                TrafficCounter.logger.debug("Time: " + n11 + ":" + value + ":" + n4);
            }
            return (n11 > n3) ? n3 : n11;
        }
        return 0L;
    }
    
    @Override
    public String toString() {
        return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10) + " KB/s, Write: " + (this.lastWriteThroughput >> 10) + " KB/s Current Read: " + (this.currentReadBytes.get() >> 10) + " KB Current Write: " + (this.currentWrittenBytes.get() >> 10) + " KB";
    }
    
    static ScheduledFuture access$002(final TrafficCounter trafficCounter, final ScheduledFuture scheduledFuture) {
        return trafficCounter.scheduledFuture = scheduledFuture;
    }
    
    static ScheduledExecutorService access$100(final TrafficCounter trafficCounter) {
        return trafficCounter.executor;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
    }
    
    private static class TrafficMonitoringTask implements Runnable
    {
        private final AbstractTrafficShapingHandler trafficShapingHandler1;
        private final TrafficCounter counter;
        
        protected TrafficMonitoringTask(final AbstractTrafficShapingHandler trafficShapingHandler1, final TrafficCounter counter) {
            this.trafficShapingHandler1 = trafficShapingHandler1;
            this.counter = counter;
        }
        
        @Override
        public void run() {
            if (!this.counter.monitorActive.get()) {
                return;
            }
            this.counter.resetAccounting(System.currentTimeMillis());
            if (this.trafficShapingHandler1 != null) {
                this.trafficShapingHandler1.doAccounting(this.counter);
            }
            TrafficCounter.access$002(this.counter, TrafficCounter.access$100(this.counter).schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS));
        }
    }
}
