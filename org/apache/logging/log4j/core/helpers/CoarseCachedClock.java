package org.apache.logging.log4j.core.helpers;

import java.util.concurrent.locks.*;

public final class CoarseCachedClock implements Clock
{
    private static CoarseCachedClock instance;
    private long millis;
    private final Thread updater;
    
    private CoarseCachedClock() {
        this.millis = System.currentTimeMillis();
        (this.updater = new Thread("Clock Updater Thread") {
            final CoarseCachedClock this$0;
            
            @Override
            public void run() {
                while (true) {
                    CoarseCachedClock.access$002(this.this$0, System.currentTimeMillis());
                    LockSupport.parkNanos(1000000L);
                }
            }
        }).setDaemon(true);
        this.updater.start();
    }
    
    public static CoarseCachedClock instance() {
        return CoarseCachedClock.instance;
    }
    
    @Override
    public long currentTimeMillis() {
        return this.millis;
    }
    
    static long access$002(final CoarseCachedClock coarseCachedClock, final long millis) {
        return coarseCachedClock.millis = millis;
    }
    
    static {
        CoarseCachedClock.instance = new CoarseCachedClock();
    }
}
