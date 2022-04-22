package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.entity.*;

public class EntityAIFollowGolem extends EntityAIBase
{
    private EntityVillager theVillager;
    private EntityIronGolem theGolem;
    private int takeGolemRoseTick;
    private boolean tookGolemRose;
    private static final String __OBFID;
    
    public EntityAIFollowGolem(final EntityVillager theVillager) {
        this.theVillager = theVillager;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theVillager.getGrowingAge() >= 0) {
            return false;
        }
        if (!this.theVillager.worldObj.isDaytime()) {
            return false;
        }
        final List entitiesWithinAABB = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0, 2.0, 6.0));
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }
        for (final EntityIronGolem theGolem : entitiesWithinAABB) {
            if (theGolem.getHoldRoseTick() > 0) {
                this.theGolem = theGolem;
                break;
            }
        }
        return this.theGolem != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.theGolem.getHoldRoseTick() > 0;
    }
    
    @Override
    public void startExecuting() {
        this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
        this.tookGolemRose = false;
        this.theGolem.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.theGolem = null;
        this.theVillager.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0f, 30.0f);
        if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
            this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5);
            this.tookGolemRose = true;
        }
        if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity(this.theGolem) < 4.0) {
            this.theGolem.setHoldingRose(false);
            this.theVillager.getNavigator().clearPathEntity();
        }
    }
    
    static {
        __OBFID = "CL_00001615";
    }
}
