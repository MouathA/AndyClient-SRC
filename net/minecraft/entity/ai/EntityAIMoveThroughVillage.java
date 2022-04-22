package net.minecraft.entity.ai;

import com.google.common.collect.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movementSpeed;
    private PathEntity entityPathNavigate;
    private VillageDoorInfo doorInfo;
    private boolean isNocturnal;
    private List doorList;
    private static final String __OBFID;
    
    public EntityAIMoveThroughVillage(final EntityCreature theEntity, final double movementSpeed, final boolean isNocturnal) {
        this.doorList = Lists.newArrayList();
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.isNocturnal = isNocturnal;
        this.setMutexBits(1);
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        this.func_75414_f();
        if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
            return false;
        }
        final Village func_176056_a = this.theEntity.worldObj.getVillageCollection().func_176056_a(new BlockPos(this.theEntity), 0);
        if (func_176056_a == null) {
            return false;
        }
        this.doorInfo = this.func_75412_a(func_176056_a);
        if (this.doorInfo == null) {
            return false;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final boolean func_179686_g = pathNavigateGround.func_179686_g();
        pathNavigateGround.func_179688_b(false);
        this.entityPathNavigate = pathNavigateGround.func_179680_a(this.doorInfo.func_179852_d());
        pathNavigateGround.func_179688_b(func_179686_g);
        if (this.entityPathNavigate != null) {
            return true;
        }
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3(this.doorInfo.func_179852_d().getX(), this.doorInfo.func_179852_d().getY(), this.doorInfo.func_179852_d().getZ()));
        if (randomTargetBlockTowards == null) {
            return false;
        }
        pathNavigateGround.func_179688_b(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord);
        pathNavigateGround.func_179688_b(func_179686_g);
        return this.entityPathNavigate != null;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }
        final float n = this.theEntity.width + 4.0f;
        return this.theEntity.getDistanceSq(this.doorInfo.func_179852_d()) > n * n;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }
    
    @Override
    public void resetTask() {
        if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.func_179852_d()) < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }
    
    private VillageDoorInfo func_75412_a(final Village village) {
        VillageDoorInfo villageDoorInfo = null;
        for (final VillageDoorInfo villageDoorInfo2 : village.getVillageDoorInfoList()) {
            if (villageDoorInfo2.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ)) < Integer.MAX_VALUE && !this.func_75413_a(villageDoorInfo2)) {
                villageDoorInfo = villageDoorInfo2;
            }
        }
        return villageDoorInfo;
    }
    
    private boolean func_75413_a(final VillageDoorInfo villageDoorInfo) {
        final Iterator<VillageDoorInfo> iterator = this.doorList.iterator();
        while (iterator.hasNext()) {
            if (villageDoorInfo.func_179852_d().equals(iterator.next().func_179852_d())) {
                return true;
            }
        }
        return false;
    }
    
    private void func_75414_f() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }
    
    static {
        __OBFID = "CL_00001597";
    }
}
