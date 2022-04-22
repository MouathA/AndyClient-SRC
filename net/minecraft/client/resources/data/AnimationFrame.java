package net.minecraft.client.resources.data;

public class AnimationFrame
{
    private final int frameIndex;
    private final int frameTime;
    private static final String __OBFID;
    
    public AnimationFrame(final int n) {
        this(n, -1);
    }
    
    public AnimationFrame(final int frameIndex, final int frameTime) {
        this.frameIndex = frameIndex;
        this.frameTime = frameTime;
    }
    
    public boolean hasNoTime() {
        return this.frameTime == -1;
    }
    
    public int getFrameTime() {
        return this.frameTime;
    }
    
    public int getFrameIndex() {
        return this.frameIndex;
    }
    
    static {
        __OBFID = "CL_00001104";
    }
}
