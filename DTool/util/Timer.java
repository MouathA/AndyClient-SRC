package DTool.util;

public class Timer
{
    private long prevMS;
    public long lastMS;
    private long previousTime;
    
    public Timer() {
        this.prevMS = 0L;
        this.lastMS = System.currentTimeMillis();
    }
    
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimeElapsed(final long n, final boolean b) {
        if (System.currentTimeMillis() - this.lastMS > n) {
            if (b) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public boolean hasReached(final long n) {
        return this.getCurrentMS() - this.lastMS >= n;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean delay(final float n) {
        return this.getTime() - this.prevMS >= n;
    }
    
    public boolean isDelayComplete(final long n) {
        return System.currentTimeMillis() - this.lastMS >= n;
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }
    
    public static long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean check(final float n) {
        return getCurrentTime() - this.previousTime >= n;
    }
}
