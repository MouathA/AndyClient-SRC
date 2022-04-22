package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class DestroyBlockProgress
{
    private final int miningPlayerEntId;
    private final BlockPos field_180247_b;
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;
    private static final String __OBFID;
    
    public DestroyBlockProgress(final int miningPlayerEntId, final BlockPos field_180247_b) {
        this.miningPlayerEntId = miningPlayerEntId;
        this.field_180247_b = field_180247_b;
    }
    
    public BlockPos func_180246_b() {
        return this.field_180247_b;
    }
    
    public void setPartialBlockDamage(final int n) {
        if (10 > 10) {}
        this.partialBlockProgress = 10;
    }
    
    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
    
    public void setCloudUpdateTick(final int createdAtCloudUpdateTick) {
        this.createdAtCloudUpdateTick = createdAtCloudUpdateTick;
    }
    
    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }
    
    static {
        __OBFID = "CL_00001427";
    }
}
