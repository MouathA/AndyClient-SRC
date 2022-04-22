package net.minecraft.entity.ai;

public abstract class EntityAIBase
{
    private int mutexBits;
    private static final String __OBFID;
    
    public abstract boolean shouldExecute();
    
    public boolean continueExecuting() {
        return this.shouldExecute();
    }
    
    public boolean isInterruptible() {
        return true;
    }
    
    public void startExecuting() {
    }
    
    public void resetTask() {
    }
    
    public void updateTask() {
    }
    
    public void setMutexBits(final int mutexBits) {
        this.mutexBits = mutexBits;
    }
    
    public int getMutexBits() {
        return this.mutexBits;
    }
    
    static {
        __OBFID = "CL_00001587";
    }
}
