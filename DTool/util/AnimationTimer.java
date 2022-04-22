package DTool.util;

public class AnimationTimer
{
    private final int delay;
    private int bottom;
    private int top;
    private int timer;
    private boolean wasRising;
    private DelayTimer helpertimer;
    
    public AnimationTimer(final int n) {
        this.helpertimer = new DelayTimer();
        this.delay = n;
        this.top = n;
        this.bottom = 0;
    }
    
    public void update(final boolean b) {
        if (this.helpertimer.hasPassed(10.0)) {
            if (b) {
                if (this.timer < this.delay) {
                    if (!this.wasRising) {
                        this.bottom = this.timer;
                    }
                    ++this.timer;
                }
                this.wasRising = true;
            }
            else {
                if (this.timer > 0) {
                    if (this.wasRising) {
                        this.top = this.timer;
                    }
                    --this.timer;
                }
                this.wasRising = false;
            }
            this.helpertimer.reset();
        }
    }
    
    public void reset() {
        this.timer = 0;
        this.wasRising = false;
        this.helpertimer.reset();
        this.top = this.delay;
        this.bottom = 0;
    }
    
    public double getValue() {
        return this.wasRising ? Math.sin((this.timer - this.bottom) / (double)(this.delay - this.bottom) * 3.141592653589793 / 2.0) : (1.0 - Math.cos(this.timer / (double)this.top * 3.141592653589793 / 2.0));
    }
}
