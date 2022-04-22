package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    private static final String __OBFID;
    
    public EntityAIMoveTowardsRestriction(final EntityCreature theEntity, final double movementSpeed) {
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        }
        final BlockPos func_180486_cf = this.theEntity.func_180486_cf();
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(func_180486_cf.getX(), func_180486_cf.getY(), func_180486_cf.getZ()));
        if (randomTargetBlockTowards == null) {
            return false;
        }
        this.movePosX = randomTargetBlockTowards.xCoord;
        this.movePosY = randomTargetBlockTowards.yCoord;
        this.movePosZ = randomTargetBlockTowards.zCoord;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
    
    static {
        __OBFID = "CL_00001598";
    }
}
