package com.google.common.util.concurrent;

import javax.annotation.concurrent.*;
import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@ThreadSafe
@Beta
public abstract class RateLimiter
{
    private final SleepingTicker ticker;
    private final long offsetNanos;
    double storedPermits;
    double maxPermits;
    double stableIntervalMicros;
    private final Object mutex;
    private long nextFreeTicketMicros;
    
    public static RateLimiter create(final double n) {
        return create(SleepingTicker.SYSTEM_TICKER, n);
    }
    
    @VisibleForTesting
    static RateLimiter create(final SleepingTicker sleepingTicker, final double rate) {
        final Bursty bursty = new Bursty(sleepingTicker, 1.0);
        bursty.setRate(rate);
        return bursty;
    }
    
    public static RateLimiter create(final double n, final long n2, final TimeUnit timeUnit) {
        return create(SleepingTicker.SYSTEM_TICKER, n, n2, timeUnit);
    }
    
    @VisibleForTesting
    static RateLimiter create(final SleepingTicker sleepingTicker, final double rate, final long n, final TimeUnit timeUnit) {
        final WarmingUp warmingUp = new WarmingUp(sleepingTicker, n, timeUnit);
        warmingUp.setRate(rate);
        return warmingUp;
    }
    
    @VisibleForTesting
    static RateLimiter createWithCapacity(final SleepingTicker sleepingTicker, final double rate, final long n, final TimeUnit timeUnit) {
        final Bursty bursty = new Bursty(sleepingTicker, timeUnit.toNanos(n) / 1.0E9);
        bursty.setRate(rate);
        return bursty;
    }
    
    private RateLimiter(final SleepingTicker ticker) {
        this.mutex = new Object();
        this.nextFreeTicketMicros = 0L;
        this.ticker = ticker;
        this.offsetNanos = ticker.read();
    }
    
    public final void setRate(final double n) {
        Preconditions.checkArgument(n > 0.0 && !Double.isNaN(n), (Object)"rate must be positive");
        // monitorenter(mutex = this.mutex)
        this.resync(this.readSafeMicros());
        this.doSetRate(n, this.stableIntervalMicros = TimeUnit.SECONDS.toMicros(1L) / n);
    }
    // monitorexit(mutex)
    
    abstract void doSetRate(final double p0, final double p1);
    
    public final double getRate() {
        return TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
    }
    
    public double acquire() {
        return this.acquire(1);
    }
    
    public double acquire(final int n) {
        final long reserve = this.reserve(n);
        this.ticker.sleepMicrosUninterruptibly(reserve);
        return 1.0 * reserve / TimeUnit.SECONDS.toMicros(1L);
    }
    
    long reserve() {
        return this.reserve(1);
    }
    
    long reserve(final int n) {
        checkPermits(n);
        // monitorenter(mutex = this.mutex)
        // monitorexit(mutex)
        return this.reserveNextTicket(n, this.readSafeMicros());
    }
    
    public boolean tryAcquire(final long n, final TimeUnit timeUnit) {
        return this.tryAcquire(1, n, timeUnit);
    }
    
    public boolean tryAcquire(final int n) {
        return this.tryAcquire(n, 0L, TimeUnit.MICROSECONDS);
    }
    
    public boolean tryAcquire() {
        return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }
    
    public boolean tryAcquire(final int n, final long n2, final TimeUnit timeUnit) {
        final long micros = timeUnit.toMicros(n2);
        checkPermits(n);
        // monitorenter(mutex = this.mutex)
        final long safeMicros = this.readSafeMicros();
        if (this.nextFreeTicketMicros > safeMicros + micros) {
            // monitorexit(mutex)
            return false;
        }
        final long reserveNextTicket = this.reserveNextTicket(n, safeMicros);
        // monitorexit(mutex)
        this.ticker.sleepMicrosUninterruptibly(reserveNextTicket);
        return true;
    }
    
    private static void checkPermits(final int n) {
        Preconditions.checkArgument(n > 0, (Object)"Requested permits must be positive");
    }
    
    private long reserveNextTicket(final double n, final long n2) {
        this.resync(n2);
        final long max = Math.max(0L, this.nextFreeTicketMicros - n2);
        final double min = Math.min(n, this.storedPermits);
        this.nextFreeTicketMicros += this.storedPermitsToWaitTime(this.storedPermits, min) + (long)((n - min) * this.stableIntervalMicros);
        this.storedPermits -= min;
        return max;
    }
    
    abstract long storedPermitsToWaitTime(final double p0, final double p1);
    
    private void resync(final long nextFreeTicketMicros) {
        if (nextFreeTicketMicros > this.nextFreeTicketMicros) {
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (nextFreeTicketMicros - this.nextFreeTicketMicros) / this.stableIntervalMicros);
            this.nextFreeTicketMicros = nextFreeTicketMicros;
        }
    }
    
    private long readSafeMicros() {
        return TimeUnit.NANOSECONDS.toMicros(this.ticker.read() - this.offsetNanos);
    }
    
    @Override
    public String toString() {
        return String.format("RateLimiter[stableRate=%3.1fqps]", 1000000.0 / this.stableIntervalMicros);
    }
    
    RateLimiter(final SleepingTicker sleepingTicker, final RateLimiter$1 object) {
        this(sleepingTicker);
    }
    
    @VisibleForTesting
    abstract static class SleepingTicker extends Ticker
    {
        static final SleepingTicker SYSTEM_TICKER;
        
        abstract void sleepMicrosUninterruptibly(final long p0);
        
        static {
            SYSTEM_TICKER = new SleepingTicker() {
                @Override
                public long read() {
                    return Ticker.systemTicker().read();
                }
                
                public void sleepMicrosUninterruptibly(final long n) {
                    if (n > 0L) {
                        Uninterruptibles.sleepUninterruptibly(n, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }
    }
    
    private static class Bursty extends RateLimiter
    {
        final double maxBurstSeconds;
        
        Bursty(final SleepingTicker sleepingTicker, final double maxBurstSeconds) {
            super(sleepingTicker, null);
            this.maxBurstSeconds = maxBurstSeconds;
        }
        
        @Override
        void doSetRate(final double n, final double n2) {
            final double maxPermits = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * n;
            this.storedPermits = ((maxPermits == 0.0) ? 0.0 : (this.storedPermits * this.maxPermits / maxPermits));
        }
        
        @Override
        long storedPermitsToWaitTime(final double n, final double n2) {
            return 0L;
        }
    }
    
    private static class WarmingUp extends RateLimiter
    {
        final long warmupPeriodMicros;
        private double slope;
        private double halfPermits;
        
        WarmingUp(final SleepingTicker sleepingTicker, final long n, final TimeUnit timeUnit) {
            super(sleepingTicker, null);
            this.warmupPeriodMicros = timeUnit.toMicros(n);
        }
        
        @Override
        void doSetRate(final double n, final double n2) {
            final double maxPermits = this.maxPermits;
            this.maxPermits = this.warmupPeriodMicros / n2;
            this.halfPermits = this.maxPermits / 2.0;
            this.slope = (n2 * 3.0 - n2) / this.halfPermits;
            if (maxPermits == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0;
            }
            else {
                this.storedPermits = ((maxPermits == 0.0) ? this.maxPermits : (this.storedPermits * this.maxPermits / maxPermits));
            }
        }
        
        @Override
        long storedPermitsToWaitTime(final double n, double n2) {
            final double n3 = n - this.halfPermits;
            long n4 = 0L;
            if (n3 > 0.0) {
                final double min = Math.min(n3, n2);
                n4 = (long)(min * (this.permitsToTime(n3) + this.permitsToTime(n3 - min)) / 2.0);
                n2 -= min;
            }
            return (long)(n4 + this.stableIntervalMicros * n2);
        }
        
        private double permitsToTime(final double n) {
            return this.stableIntervalMicros + n * this.slope;
        }
    }
}
