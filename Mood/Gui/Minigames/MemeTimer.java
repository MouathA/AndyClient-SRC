package Mood.Gui.Minigames;

public class MemeTimer
{
    private long lastMS;
    
    public MemeTimer() {
        this.lastMS = 0L;
        this.reset();
    }
    
    public int convertToMS(final int n) {
        return 1000 / n;
    }
    
    public long getDelay() {
        return System.currentTimeMillis() - this.lastMS;
    }
    
    public boolean hasTimeReached(final long n) {
        return this.getDelay() >= n;
    }
    
    public void reset() {
        this.setLastMS(System.currentTimeMillis());
    }
    
    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }
}
