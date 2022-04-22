package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class EntityAIControlledByPlayer extends EntityAIBase
{
    private final EntityLiving thisEntity;
    private final float maxSpeed;
    private float currentSpeed;
    private boolean speedBoosted;
    private int speedBoostTime;
    private int maxSpeedBoostTime;
    private static final String __OBFID;
    
    public EntityAIControlledByPlayer(final EntityLiving thisEntity, final float maxSpeed) {
        this.thisEntity = thisEntity;
        this.maxSpeed = maxSpeed;
        this.setMutexBits(7);
    }
    
    @Override
    public void startExecuting() {
        this.currentSpeed = 0.0f;
    }
    
    @Override
    public void resetTask() {
        this.speedBoosted = false;
        this.currentSpeed = 0.0f;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered());
    }
    
    @Override
    public void updateTask() {
        final EntityPlayer entityPlayer = (EntityPlayer)this.thisEntity.riddenByEntity;
        final EntityCreature entityCreature = (EntityCreature)this.thisEntity;
        float n = MathHelper.wrapAngleTo180_float(entityPlayer.rotationYaw - this.thisEntity.rotationYaw) * 0.5f;
        if (n > 5.0f) {
            n = 5.0f;
        }
        if (n < -5.0f) {
            n = -5.0f;
        }
        this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + n);
        if (this.currentSpeed < this.maxSpeed) {
            this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01f;
        }
        if (this.currentSpeed > this.maxSpeed) {
            this.currentSpeed = this.maxSpeed;
        }
        final int floor_double = MathHelper.floor_double(this.thisEntity.posX);
        final int floor_double2 = MathHelper.floor_double(this.thisEntity.posY);
        final int floor_double3 = MathHelper.floor_double(this.thisEntity.posZ);
        float currentSpeed = this.currentSpeed;
        if (this.speedBoosted) {
            if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
                this.speedBoosted = false;
            }
            currentSpeed += currentSpeed * 1.15f * MathHelper.sin(this.speedBoostTime / (float)this.maxSpeedBoostTime * 3.1415927f);
        }
        float n2 = 0.91f;
        if (this.thisEntity.onGround) {
            n2 = this.thisEntity.worldObj.getBlockState(new BlockPos(MathHelper.floor_float((float)floor_double), MathHelper.floor_float((float)floor_double2) - 1, MathHelper.floor_float((float)floor_double3))).getBlock().slipperiness * 0.91f;
        }
        final float n3 = 0.16277136f / (n2 * n2 * n2);
        final float sin = MathHelper.sin(entityCreature.rotationYaw * 3.1415927f / 180.0f);
        final float cos = MathHelper.cos(entityCreature.rotationYaw * 3.1415927f / 180.0f);
        final float n4 = currentSpeed * (entityCreature.getAIMoveSpeed() * n3 / Math.max(currentSpeed, 1.0f));
        float n5 = -(n4 * sin);
        float n6 = n4 * cos;
        if (MathHelper.abs(n5) > MathHelper.abs(n6)) {
            if (n5 < 0.0f) {
                n5 -= this.thisEntity.width / 2.0f;
            }
            if (n5 > 0.0f) {
                n5 += this.thisEntity.width / 2.0f;
            }
            n6 = 0.0f;
        }
        else {
            n5 = 0.0f;
            if (n6 < 0.0f) {
                n6 -= this.thisEntity.width / 2.0f;
            }
            if (n6 > 0.0f) {
                n6 += this.thisEntity.width / 2.0f;
            }
        }
        final int floor_double4 = MathHelper.floor_double(this.thisEntity.posX + n5);
        final int floor_double5 = MathHelper.floor_double(this.thisEntity.posZ + n6);
        final int floor_float = MathHelper.floor_float(this.thisEntity.width + 1.0f);
        final int floor_float2 = MathHelper.floor_float(this.thisEntity.height + entityPlayer.height + 1.0f);
        final int floor_float3 = MathHelper.floor_float(this.thisEntity.width + 1.0f);
        if (floor_double != floor_double4 || floor_double3 != floor_double5) {
            final Block block = this.thisEntity.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3)).getBlock();
            if (!this.isStairOrSlab(block) && (block.getMaterial() != Material.air || !this.isStairOrSlab(this.thisEntity.worldObj.getBlockState(new BlockPos(floor_double, floor_double2 - 1, floor_double3)).getBlock())) && WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, floor_double4, floor_double2, floor_double5, floor_float, floor_float2, floor_float3, false, false, true) == 0 && 1 == WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, floor_double, floor_double2 + 1, floor_double3, floor_float, floor_float2, floor_float3, false, false, true) && 1 == WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, floor_double4, floor_double2 + 1, floor_double5, floor_float, floor_float2, floor_float3, false, false, true)) {
                entityCreature.getJumpHelper().setJumping();
            }
        }
        if (!entityPlayer.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5f && this.thisEntity.getRNG().nextFloat() < 0.006f && !this.speedBoosted) {
            final ItemStack heldItem = entityPlayer.getHeldItem();
            if (heldItem != null && heldItem.getItem() == Items.carrot_on_a_stick) {
                heldItem.damageItem(1, entityPlayer);
                if (heldItem.stackSize == 0) {
                    final ItemStack itemStack = new ItemStack(Items.fishing_rod);
                    itemStack.setTagCompound(heldItem.getTagCompound());
                    entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = itemStack;
                }
            }
        }
        this.thisEntity.moveEntityWithHeading(0.0f, currentSpeed);
    }
    
    private boolean isStairOrSlab(final Block block) {
        return block instanceof BlockStairs || block instanceof BlockSlab;
    }
    
    public boolean isSpeedBoosted() {
        return this.speedBoosted;
    }
    
    public void boostSpeed() {
        this.speedBoosted = true;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
    }
    
    public boolean isControlledByPlayer() {
        return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3f;
    }
    
    static {
        __OBFID = "CL_00001580";
    }
}
