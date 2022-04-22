package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature theEntityCreature;
    protected double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    private static final String __OBFID;
    
    public EntityAIPanic(final EntityCreature theEntityCreature, final double speed) {
        this.theEntityCreature = theEntityCreature;
        this.speed = speed;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
            return false;
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
        if (randomTarget == null) {
            return false;
        }
        this.randPosX = randomTarget.xCoord;
        this.randPosY = randomTarget.yCoord;
        this.randPosZ = randomTarget.zCoord;
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theEntityCreature.getNavigator().noPath();
    }
    
    static {
        __OBFID = "CL_00001604";
    }
}
