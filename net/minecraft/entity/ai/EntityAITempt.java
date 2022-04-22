package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityAITempt extends EntityAIBase
{
    private EntityCreature temptedEntity;
    private double field_75282_b;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double field_75278_f;
    private double field_75279_g;
    private EntityPlayer temptingPlayer;
    private int delayTemptCounter;
    private boolean isRunning;
    private Item field_151484_k;
    private boolean scaredByPlayerMovement;
    private boolean field_75286_m;
    private static final String __OBFID;
    
    public EntityAITempt(final EntityCreature temptedEntity, final double field_75282_b, final Item field_151484_k, final boolean scaredByPlayerMovement) {
        this.temptedEntity = temptedEntity;
        this.field_75282_b = field_75282_b;
        this.field_151484_k = field_151484_k;
        this.scaredByPlayerMovement = scaredByPlayerMovement;
        this.setMutexBits(3);
        if (!(temptedEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        }
        this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return false;
        }
        final ItemStack currentEquippedItem = this.temptingPlayer.getCurrentEquippedItem();
        return currentEquippedItem != null && currentEquippedItem.getItem() == this.field_151484_k;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs(this.temptingPlayer.rotationPitch - this.field_75278_f) > 5.0 || Math.abs(this.temptingPlayer.rotationYaw - this.field_75279_g) > 5.0) {
                    return false;
                }
            }
            else {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }
            this.field_75278_f = this.temptingPlayer.rotationPitch;
            this.field_75279_g = this.temptingPlayer.rotationYaw;
        }
        return this.shouldExecute();
    }
    
    @Override
    public void startExecuting() {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
        this.field_75286_m = ((PathNavigateGround)this.temptedEntity.getNavigator()).func_179689_e();
        ((PathNavigateGround)this.temptedEntity.getNavigator()).func_179690_a(false);
    }
    
    @Override
    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
        this.isRunning = false;
        ((PathNavigateGround)this.temptedEntity.getNavigator()).func_179690_a(this.field_75286_m);
    }
    
    @Override
    public void updateTask() {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0f, (float)this.temptedEntity.getVerticalFaceSpeed());
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator().clearPathEntity();
        }
        else {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.field_75282_b);
        }
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    static {
        __OBFID = "CL_00001616";
    }
}
