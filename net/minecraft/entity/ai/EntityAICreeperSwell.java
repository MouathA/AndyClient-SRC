package net.minecraft.entity.ai;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class EntityAICreeperSwell extends EntityAIBase
{
    EntityCreeper swellingCreeper;
    EntityLivingBase creeperAttackTarget;
    private static final String __OBFID;
    
    public EntityAICreeperSwell(final EntityCreeper swellingCreeper) {
        this.swellingCreeper = swellingCreeper;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || (attackTarget != null && this.swellingCreeper.getDistanceSqToEntity(attackTarget) < 9.0);
    }
    
    @Override
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }
    
    @Override
    public void resetTask() {
        this.creeperAttackTarget = null;
    }
    
    @Override
    public void updateTask() {
        if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else {
            this.swellingCreeper.setCreeperState(1);
        }
    }
    
    static {
        __OBFID = "CL_00001614";
    }
}
