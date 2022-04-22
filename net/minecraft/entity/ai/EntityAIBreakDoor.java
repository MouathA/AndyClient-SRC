package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int field_75358_j;
    private static final String __OBFID;
    
    public EntityAIBreakDoor(final EntityLiving entityLiving) {
        super(entityLiving);
        this.field_75358_j = -1;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return false;
        }
        if (!this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            return false;
        }
        final BlockDoor doorBlock = this.doorBlock;
        return !BlockDoor.func_176514_f(this.theEntity.worldObj, this.field_179507_b);
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        final double distanceSq = this.theEntity.getDistanceSq(this.field_179507_b);
        if (this.breakingTime <= 240) {
            final BlockDoor doorBlock = this.doorBlock;
            if (!BlockDoor.func_176514_f(this.theEntity.worldObj, this.field_179507_b) && distanceSq < 4.0) {
                return false;
            }
        }
        return false;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.field_179507_b, -1);
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(20) == 0) {
            this.theEntity.worldObj.playAuxSFX(1010, this.field_179507_b, 0);
        }
        ++this.breakingTime;
        final int field_75358_j = (int)(this.breakingTime / 240.0f * 10.0f);
        if (field_75358_j != this.field_75358_j) {
            this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.field_179507_b, field_75358_j);
            this.field_75358_j = field_75358_j;
        }
        if (this.breakingTime == 240 && this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            this.theEntity.worldObj.setBlockToAir(this.field_179507_b);
            this.theEntity.worldObj.playAuxSFX(1012, this.field_179507_b, 0);
            this.theEntity.worldObj.playAuxSFX(2001, this.field_179507_b, Block.getIdFromBlock(this.doorBlock));
        }
    }
    
    static {
        __OBFID = "CL_00001577";
    }
}
