package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityAIWatchClosest extends EntityAIBase
{
    protected EntityLiving theWatcher;
    protected Entity closestEntity;
    protected float maxDistanceForPlayer;
    private int lookTime;
    private float field_75331_e;
    protected Class watchedClass;
    private static final String __OBFID;
    
    public EntityAIWatchClosest(final EntityLiving theWatcher, final Class watchedClass, final float maxDistanceForPlayer) {
        this.theWatcher = theWatcher;
        this.watchedClass = watchedClass;
        this.maxDistanceForPlayer = maxDistanceForPlayer;
        this.field_75331_e = 0.02f;
        this.setMutexBits(2);
    }
    
    public EntityAIWatchClosest(final EntityLiving theWatcher, final Class watchedClass, final float maxDistanceForPlayer, final float field_75331_e) {
        this.theWatcher = theWatcher;
        this.watchedClass = watchedClass;
        this.maxDistanceForPlayer = maxDistanceForPlayer;
        this.field_75331_e = field_75331_e;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e) {
            return false;
        }
        if (this.theWatcher.getAttackTarget() != null) {
            this.closestEntity = this.theWatcher.getAttackTarget();
        }
        if (this.watchedClass == EntityPlayer.class) {
            this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.maxDistanceForPlayer);
        }
        else {
            this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.getEntityBoundingBox().expand(this.maxDistanceForPlayer, 3.0, this.maxDistanceForPlayer), this.theWatcher);
        }
        return this.closestEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.closestEntity.isEntityAlive() && this.theWatcher.getDistanceSqToEntity(this.closestEntity) <= this.maxDistanceForPlayer * this.maxDistanceForPlayer && this.lookTime > 0;
    }
    
    @Override
    public void startExecuting() {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }
    
    @Override
    public void resetTask() {
        this.closestEntity = null;
    }
    
    @Override
    public void updateTask() {
        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0f, (float)this.theWatcher.getVerticalFaceSpeed());
        --this.lookTime;
    }
    
    static {
        __OBFID = "CL_00001592";
    }
}
