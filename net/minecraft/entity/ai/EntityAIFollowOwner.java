package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntityAIFollowOwner extends EntityAIBase
{
    private EntityTameable thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private double field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;
    private static final String __OBFID;
    
    public EntityAIFollowOwner(final EntityTameable thePet, final double field_75336_f, final float minDist, final float maxDist) {
        this.thePet = thePet;
        this.theWorld = thePet.worldObj;
        this.field_75336_f = field_75336_f;
        this.petPathfinder = thePet.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits(3);
        if (!(thePet.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase func_180492_cm = this.thePet.func_180492_cm();
        if (func_180492_cm == null) {
            return false;
        }
        if (this.thePet.isSitting()) {
            return false;
        }
        if (this.thePet.getDistanceSqToEntity(func_180492_cm) < this.minDist * this.minDist) {
            return false;
        }
        this.theOwner = func_180492_cm;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.thePet.isSitting();
    }
    
    @Override
    public void startExecuting() {
        this.field_75343_h = 0;
        this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).func_179689_e();
        ((PathNavigateGround)this.thePet.getNavigator()).func_179690_a(false);
    }
    
    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        ((PathNavigateGround)this.thePet.getNavigator()).func_179690_a(true);
    }
    
    @Override
    public void updateTask() {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0f, (float)this.thePet.getVerticalFaceSpeed());
        if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f) && !this.thePet.getLeashed() && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                final int n = MathHelper.floor_double(this.theOwner.posX) - 2;
                final int n2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
                final int floor_double = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);
                while (0 <= 4) {
                    while (0 <= 4) {
                        if ((0 < 1 || 0 < 1 || 0 > 3 || 0 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(n + 0, floor_double - 1, n2 + 0)) && !this.theWorld.getBlockState(new BlockPos(n + 0, floor_double, n2 + 0)).getBlock().isFullCube() && !this.theWorld.getBlockState(new BlockPos(n + 0, floor_double + 1, n2 + 0)).getBlock().isFullCube()) {
                            this.thePet.setLocationAndAngles(n + 0 + 0.5f, floor_double, n2 + 0 + 0.5f, this.thePet.rotationYaw, this.thePet.rotationPitch);
                            this.petPathfinder.clearPathEntity();
                            return;
                        }
                        int n3 = 0;
                        ++n3;
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00001585";
    }
}
