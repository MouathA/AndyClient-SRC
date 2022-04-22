package net.minecraft.util;

import net.minecraft.client.*;

public class Timer
{
    public float ticksPerSecond;
    private double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private long lastSyncHRClock;
    private long field_74285_i;
    private double timeSyncAdjustment;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000658";
    }
    
    public Timer(final float ticksPerSecond) {
        this.timeSyncAdjustment = 1.0;
        this.ticksPerSecond = ticksPerSecond;
        this.lastSyncSysClock = Minecraft.getSystemTime();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }
    
    public void updateTimer() {
        final long systemTime = Minecraft.getSystemTime();
        final long n = systemTime - this.lastSyncSysClock;
        final long n2 = System.nanoTime() / 1000000L;
        final double n3 = n2 / 1000.0;
        if (n <= 1000L && n >= 0L) {
            this.field_74285_i += n;
            if (this.field_74285_i > 1000L) {
                this.timeSyncAdjustment += (this.field_74285_i / (double)(n2 - this.lastSyncHRClock) - this.timeSyncAdjustment) * 0.20000000298023224;
                this.lastSyncHRClock = n2;
                this.field_74285_i = 0L;
            }
            if (this.field_74285_i < 0L) {
                this.lastSyncHRClock = n2;
            }
        }
        else {
            this.lastHRTime = n3;
        }
        this.lastSyncSysClock = systemTime;
        final double n4 = (n3 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = n3;
        this.elapsedPartialTicks += (float)(MathHelper.clamp_double(n4, 0.0, 1.0) * 1.0f * this.ticksPerSecond);
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}
