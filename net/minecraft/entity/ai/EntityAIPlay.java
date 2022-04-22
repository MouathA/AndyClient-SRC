package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class EntityAIPlay extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityLivingBase targetVillager;
    private double field_75261_c;
    private int playTime;
    private static final String __OBFID;
    
    public EntityAIPlay(final EntityVillager villagerObj, final double field_75261_c) {
        this.villagerObj = villagerObj;
        this.field_75261_c = field_75261_c;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() >= 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(400) != 0) {
            return false;
        }
        final List entitiesWithinAABB = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0, 3.0, 6.0));
        double n = Double.MAX_VALUE;
        for (final EntityVillager targetVillager : entitiesWithinAABB) {
            if (targetVillager != this.villagerObj && !targetVillager.isPlaying() && targetVillager.getGrowingAge() < 0) {
                final double distanceSqToEntity = targetVillager.getDistanceSqToEntity(this.villagerObj);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                this.targetVillager = targetVillager;
            }
        }
        return this.targetVillager != null || RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3) != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.playTime > 0;
    }
    
    @Override
    public void startExecuting() {
        if (this.targetVillager != null) {
            this.villagerObj.setPlaying(true);
        }
        this.playTime = 1000;
    }
    
    @Override
    public void resetTask() {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }
    
    @Override
    public void updateTask() {
        --this.playTime;
        if (this.targetVillager != null) {
            if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0) {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.field_75261_c);
            }
        }
        else if (this.villagerObj.getNavigator().noPath()) {
            final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            if (randomTarget == null) {
                return;
            }
            this.villagerObj.getNavigator().tryMoveToXYZ(randomTarget.xCoord, randomTarget.yCoord, randomTarget.zCoord, this.field_75261_c);
        }
    }
    
    static {
        __OBFID = "CL_00001605";
    }
}
