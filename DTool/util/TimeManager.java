package DTool.util;

import java.util.concurrent.*;

public class TimeManager
{
    private long last;
    
    public final synchronized void resetTime() {
        this.last = System.nanoTime();
    }
    
    public final synchronized boolean sleep(final long n) {
        return this.sleep(n, TimeUnit.MILLISECONDS);
    }
    
    public synchronized boolean sleep(final long n, final TimeUnit timeUnit) {
        return timeUnit.convert(System.nanoTime() - this.last, TimeUnit.NANOSECONDS) >= n;
    }
    
    public final long convertToMillis(final double n) {
        return (long)(1000.0 / n);
    }
}
