package net.minecraft.entity;

import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;

public abstract class EntityCreature extends EntityLiving
{
    public static final UUID field_110179_h;
    public static final AttributeModifier field_110181_i;
    private BlockPos homePosition;
    private float maximumHomeDistance;
    private EntityAIBase aiBase;
    private boolean field_110180_bt;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001558";
        field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        field_110181_i = new AttributeModifier(EntityCreature.field_110179_h, "Fleeing speed bonus", 2.0, 2).setSaved(false);
    }
    
    public EntityCreature(final World world) {
        super(world);
        this.homePosition = BlockPos.ORIGIN;
        this.maximumHomeDistance = -1.0f;
        this.aiBase = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
    
    public float func_180484_a(final BlockPos blockPos) {
        return 0.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.func_180484_a(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0f;
    }
    
    public boolean hasPath() {
        return !this.navigator.noPath();
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.func_180485_d(new BlockPos(this));
    }
    
    public boolean func_180485_d(final BlockPos blockPos) {
        return this.maximumHomeDistance == -1.0f || this.homePosition.distanceSq(blockPos) < this.maximumHomeDistance * this.maximumHomeDistance;
    }
    
    public void func_175449_a(final BlockPos homePosition, final int n) {
        this.homePosition = homePosition;
        this.maximumHomeDistance = (float)n;
    }
    
    public BlockPos func_180486_cf() {
        return this.homePosition;
    }
    
    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0f;
    }
    
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            final Entity leashedToEntity = this.getLeashedToEntity();
            this.func_175449_a(new BlockPos((int)leashedToEntity.posX, (int)leashedToEntity.posY, (int)leashedToEntity.posZ), 5);
            final float distanceToEntity = this.getDistanceToEntity(leashedToEntity);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (distanceToEntity > 10.0f) {
                    this.clearLeashed(true, true);
                }
                return;
            }
            if (!this.field_110180_bt) {
                this.tasks.addTask(2, this.aiBase);
                if (this.getNavigator() instanceof PathNavigateGround) {
                    ((PathNavigateGround)this.getNavigator()).func_179690_a(false);
                }
                this.field_110180_bt = true;
            }
            this.func_142017_o(distanceToEntity);
            if (distanceToEntity > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(leashedToEntity, 1.0);
            }
            if (distanceToEntity > 6.0f) {
                final double n = (leashedToEntity.posX - this.posX) / distanceToEntity;
                final double n2 = (leashedToEntity.posY - this.posY) / distanceToEntity;
                final double n3 = (leashedToEntity.posZ - this.posZ) / distanceToEntity;
                this.motionX += n * Math.abs(n) * 0.4;
                this.motionY += n2 * Math.abs(n2) * 0.4;
                this.motionZ += n3 * Math.abs(n3) * 0.4;
            }
            if (distanceToEntity > 10.0f) {
                this.clearLeashed(true, true);
            }
        }
        else if (!this.getLeashed() && this.field_110180_bt) {
            this.field_110180_bt = false;
            this.tasks.removeTask(this.aiBase);
            if (this.getNavigator() instanceof PathNavigateGround) {
                ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
            }
            this.detachHome();
        }
    }
    
    protected void func_142017_o(final float n) {
    }
}
