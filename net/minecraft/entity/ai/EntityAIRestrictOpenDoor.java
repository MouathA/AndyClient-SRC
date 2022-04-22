package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.village.*;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;
    private static final String __OBFID;
    
    public EntityAIRestrictOpenDoor(final EntityCreature entityObj) {
        this.entityObj = entityObj;
        if (!(entityObj.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(this.entityObj);
        final Village func_176056_a = this.entityObj.worldObj.getVillageCollection().func_176056_a(blockPos, 16);
        if (func_176056_a == null) {
            return false;
        }
        this.frontDoor = func_176056_a.func_179865_b(blockPos);
        return this.frontDoor != null && this.frontDoor.func_179846_b(blockPos) < 2.25;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.worldObj.isDaytime() && (!this.frontDoor.func_179851_i() && this.frontDoor.func_179850_c(new BlockPos(this.entityObj)));
    }
    
    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.entityObj.getNavigator()).func_179688_b(false);
        ((PathNavigateGround)this.entityObj.getNavigator()).func_179691_c(false);
    }
    
    @Override
    public void resetTask() {
        ((PathNavigateGround)this.entityObj.getNavigator()).func_179688_b(true);
        ((PathNavigateGround)this.entityObj.getNavigator()).func_179691_c(true);
        this.frontDoor = null;
    }
    
    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
    
    static {
        __OBFID = "CL_00001610";
    }
}
