package org.apache.logging.log4j.core.helpers;

import java.util.concurrent.locks.*;

public final class CachedClock implements Clock
{
    private static final int UPDATE_THRESHOLD = 1023;
    private static CachedClock instance;
    private long millis;
    private short count;
    private final Thread updater;
    
    private CachedClock() {
        this.millis = System.currentTimeMillis();
        this.count = 0;
        (this.updater = new Thread("Clock Updater Thread") {
            final CachedClock this$0;
            
            @Override
            public void run() {
                while (true) {
                    CachedClock.access$002(this.this$0, System.currentTimeMillis());
                    LockSupport.parkNanos(1000000L);
                }
            }
        }).setDaemon(true);
        this.updater.start();
    }
    
    public static CachedClock instance() {
        return CachedClock.instance;
    }
    
    @Override
    public long currentTimeMillis() {
        final short count = (short)(this.count + 1);
        this.count = count;
        if ((count & 0x3FF) == 0x3FF) {
            this.millis = System.currentTimeMillis();
        }
        return this.millis;
    }
    
    static long access$002(final CachedClock cachedClock, final long millis) {
        return cachedClock.millis = millis;
    }
    
    static {
        CachedClock.instance = new CachedClock();
    }
}
