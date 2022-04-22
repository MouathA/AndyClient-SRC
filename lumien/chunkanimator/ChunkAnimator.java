package lumien.chunkanimator;

import lumien.chunkanimator.handler.*;

public class ChunkAnimator
{
    public static ChunkAnimator INSTANCE;
    public AnimationHandler animationHandler;
    public int mode;
    public int animationDuration;
    public int easingFunction;
    public boolean disableAroundPlayer;
    
    public ChunkAnimator() {
        this.mode = 0;
        this.animationDuration = 2000;
        this.easingFunction = 1;
        this.disableAroundPlayer = false;
        ChunkAnimator.INSTANCE = this;
    }
    
    public void onStart() {
        this.animationHandler = new AnimationHandler();
        this.syncWithModule();
    }
    
    public void syncWithModule() {
    }
}
