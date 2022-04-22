package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLiving theEntity;
    EntityLivingBase theVictim;
    int attackCountdown;
    private static final String __OBFID;
    
    public EntityAIOcelotAttack(final EntityLiving theEntity) {
        this.theEntity = theEntity;
        this.theWorld = theEntity.worldObj;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.theEntity.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        this.theVictim = attackTarget;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.theVictim.isEntityAlive() && this.theEntity.getDistanceSqToEntity(this.theVictim) <= 225.0 && (!this.theEntity.getNavigator().noPath() || this.shouldExecute());
    }
    
    @Override
    public void resetTask() {
        this.theVictim = null;
        this.theEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0f, 30.0f);
        final double n = this.theEntity.width * 2.0f * this.theEntity.width * 2.0f;
        final double distanceSq = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
        double n2 = 0.8;
        if (distanceSq > n && distanceSq < 16.0) {
            n2 = 1.33;
        }
        else if (distanceSq < 225.0) {
            n2 = 0.6;
        }
        this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, n2);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
        if (distanceSq <= n && this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.theEntity.attackEntityAsMob(this.theVictim);
        }
    }
    
    static {
        __OBFID = "CL_00001600";
    }
}
