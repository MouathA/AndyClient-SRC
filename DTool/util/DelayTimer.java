package DTool.util;

public class DelayTimer
{
    private long prevTime;
    
    public DelayTimer() {
        this.reset();
    }
    
    public DelayTimer(final long n) {
        this.prevTime = System.currentTimeMillis() - n;
    }
    
    public boolean hasPassed(final double n) {
        return System.currentTimeMillis() - this.prevTime >= n;
    }
    
    public void reset() {
        this.prevTime = System.currentTimeMillis();
    }
    
    public long getPassed() {
        return System.currentTimeMillis() - this.prevTime;
    }
    
    public void reset(final long n) {
        this.prevTime = System.currentTimeMillis() - n;
    }
    
    public boolean isDelayComplete(final double n) {
        return this.hasPassed(n);
    }
}
