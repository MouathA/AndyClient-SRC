package Mood.Host.Helper;

public class TimeHelper
{
    private static long lastMS;
    public long time;
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasReached(final long n) {
        return this.getCurrentMS() - TimeHelper.lastMS >= n;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }
    
    public boolean hasTimeElapsed(final long n, final boolean b) {
        if (n < 150L) {
            if (this.getTime() >= n / 1.63) {
                if (b) {
                    this.reset();
                }
                return true;
            }
        }
        else if (this.getTime() >= n) {
            if (b) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public void reset() {
        TimeHelper.lastMS = this.getCurrentMS();
    }
    
    public boolean isDelayComplete(final float n) {
        return System.currentTimeMillis() - TimeHelper.lastMS >= n;
    }
    
    public void setLastMS(final long n) {
        TimeHelper.lastMS = System.currentTimeMillis();
    }
}
