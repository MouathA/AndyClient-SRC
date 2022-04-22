package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAISit extends EntityAIBase
{
    private EntityTameable theEntity;
    private boolean isSitting;
    private static final String __OBFID;
    
    public EntityAISit(final EntityTameable theEntity) {
        this.theEntity = theEntity;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }
        if (this.theEntity.isInWater()) {
            return false;
        }
        if (!this.theEntity.onGround) {
            return false;
        }
        final EntityLivingBase func_180492_cm = this.theEntity.func_180492_cm();
        return func_180492_cm == null || ((this.theEntity.getDistanceSqToEntity(func_180492_cm) >= 144.0 || func_180492_cm.getAITarget() == null) && this.isSitting);
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }
    
    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }
    
    public void setSitting(final boolean isSitting) {
        this.isSitting = isSitting;
    }
    
    static {
        __OBFID = "CL_00001613";
    }
}
