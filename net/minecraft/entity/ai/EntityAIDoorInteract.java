package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected BlockPos field_179507_b;
    protected BlockDoor doorBlock;
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    float entityPositionZ;
    private static final String __OBFID;
    
    public EntityAIDoorInteract(final EntityLiving theEntity) {
        this.field_179507_b = BlockPos.ORIGIN;
        this.theEntity = theEntity;
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return false;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final PathEntity path = pathNavigateGround.getPath();
        if (path != null && !path.isFinished() && pathNavigateGround.func_179686_g()) {
            while (0 < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength())) {
                final PathPoint pathPointFromIndex = path.getPathPointFromIndex(0);
                this.field_179507_b = new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord + 1, pathPointFromIndex.zCoord);
                if (this.theEntity.getDistanceSq(this.field_179507_b.getX(), this.theEntity.posY, this.field_179507_b.getZ()) <= 2.25) {
                    this.doorBlock = this.func_179506_a(this.field_179507_b);
                    if (this.doorBlock != null) {
                        return true;
                    }
                }
                int n = 0;
                ++n;
            }
            this.field_179507_b = new BlockPos(this.theEntity).offsetUp();
            this.doorBlock = this.func_179506_a(this.field_179507_b);
            return this.doorBlock != null;
        }
        return false;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }
    
    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)(this.field_179507_b.getX() + 0.5f - this.theEntity.posX);
        this.entityPositionZ = (float)(this.field_179507_b.getZ() + 0.5f - this.theEntity.posZ);
    }
    
    @Override
    public void updateTask() {
        if (this.entityPositionX * (float)(this.field_179507_b.getX() + 0.5f - this.theEntity.posX) + this.entityPositionZ * (float)(this.field_179507_b.getZ() + 0.5f - this.theEntity.posZ) < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }
    
    private BlockDoor func_179506_a(final BlockPos blockPos) {
        final Block block = this.theEntity.worldObj.getBlockState(blockPos).getBlock();
        return (block instanceof BlockDoor && block.getMaterial() == Material.wood) ? ((BlockDoor)block) : null;
    }
    
    static {
        __OBFID = "CL_00001581";
    }
}
