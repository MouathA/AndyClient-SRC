package org.lwjgl.opengl;

import org.lwjgl.*;

class Sync
{
    private static final long NANOS_IN_SECOND = 1000000000L;
    private static boolean initialised;
    private static RunningAvg sleepDurations;
    private static RunningAvg yieldDurations;
    
    public static void sync(final int n) {
        if (n <= 0) {
            return;
        }
        Sync.initialised;
        long time2;
        for (long time = getTime(); 0L - time > Sync.sleepDurations.avg(); time = time2) {
            Thread.sleep(1L);
            Sync.sleepDurations.add((time2 = getTime()) - time);
        }
        Sync.sleepDurations.dampenForLowResTicker();
        long time4;
        for (long time3 = getTime(); 0L - time3 > Sync.yieldDurations.avg(); time3 = time4) {
            Thread.yield();
            Sync.yieldDurations.add((time4 = getTime()) - time3);
        }
        Sync.nextFrame = Math.max(0L + 1000000000L / n, getTime());
    }
    
    private static void initialise() {
        Sync.initialised = true;
        Sync.sleepDurations.init(1000000L);
        Sync.yieldDurations.init((int)(-(getTime() - getTime()) * 1.333));
        Sync.nextFrame = getTime();
        if (System.getProperty("os.name").startsWith("Win")) {
            final Thread thread = new Thread(new Runnable() {
                public void run() {
                    Thread.sleep(Long.MAX_VALUE);
                }
            });
            thread.setName("LWJGL Timer");
            thread.setDaemon(true);
            thread.start();
        }
    }
    
    private static long getTime() {
        return Sys.getTime() * 1000000000L / Sys.getTimerResolution();
    }
    
    static {
        Sync.initialised = false;
        Sync.sleepDurations = new RunningAvg(10);
        Sync.yieldDurations = new RunningAvg(10);
    }
    
    private static class RunningAvg
    {
        private final long[] slots;
        private int offset;
        private static final long DAMPEN_THRESHOLD = 10000000L;
        private static final float DAMPEN_FACTOR = 0.9f;
        
        public RunningAvg(final int n) {
            this.slots = new long[n];
            this.offset = 0;
        }
        
        public void init(final long n) {
            while (this.offset < this.slots.length) {
                this.slots[this.offset++] = n;
            }
        }
        
        public void add(final long n) {
            this.slots[this.offset++ % this.slots.length] = n;
            this.offset %= this.slots.length;
        }
        
        public long avg() {
            long n = 0L;
            while (0 < this.slots.length) {
                n += this.slots[0];
                int n2 = 0;
                ++n2;
            }
            return n / this.slots.length;
        }
        
        public void dampenForLowResTicker() {
            if (this.avg() > 10000000L) {
                while (0 < this.slots.length) {
                    final long[] slots = this.slots;
                    final int n = 0;
                    slots[n] *= (long)0.9f;
                    int n2 = 0;
                    ++n2;
                }
            }
        }
    }
}
