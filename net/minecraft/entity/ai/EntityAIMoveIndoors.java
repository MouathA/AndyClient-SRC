package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.util.*;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo doorInfo;
    private int insidePosX;
    private int insidePosZ;
    private static final String __OBFID;
    
    public EntityAIMoveIndoors(final EntityCreature entityObj) {
        this.insidePosX = -1;
        this.insidePosZ = -1;
        this.entityObj = entityObj;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final BlockPos blockPos = new BlockPos(this.entityObj);
        if ((this.entityObj.worldObj.isDaytime() && (!this.entityObj.worldObj.isRaining() || this.entityObj.worldObj.getBiomeGenForCoords(blockPos).canSpawnLightningBolt())) || this.entityObj.worldObj.provider.getHasNoSky()) {
            return false;
        }
        if (this.entityObj.getRNG().nextInt(50) != 0) {
            return false;
        }
        if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
            return false;
        }
        final Village func_176056_a = this.entityObj.worldObj.getVillageCollection().func_176056_a(blockPos, 14);
        if (func_176056_a == null) {
            return false;
        }
        this.doorInfo = func_176056_a.func_179863_c(blockPos);
        return this.doorInfo != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.insidePosX = -1;
        final BlockPos func_179856_e = this.doorInfo.func_179856_e();
        final int x = func_179856_e.getX();
        final int y = func_179856_e.getY();
        final int z = func_179856_e.getZ();
        if (this.entityObj.getDistanceSq(func_179856_e) > 256.0) {
            final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, new Vec3(x + 0.5, y, z + 0.5));
            if (randomTargetBlockTowards != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord, 1.0);
            }
        }
        else {
            this.entityObj.getNavigator().tryMoveToXYZ(x + 0.5, y, z + 0.5, 1.0);
        }
    }
    
    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.func_179856_e().getX();
        this.insidePosZ = this.doorInfo.func_179856_e().getZ();
        this.doorInfo = null;
    }
    
    static {
        __OBFID = "CL_00001596";
    }
}
