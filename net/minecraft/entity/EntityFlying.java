package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public abstract class EntityFlying extends EntityLiving
{
    private static final String __OBFID;
    
    public EntityFlying(final World world) {
        super(world);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isInWater()) {
            this.moveFlying(n, n2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
        }
        else if (this.func_180799_ab()) {
            this.moveFlying(n, n2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        else {
            float n3 = 0.91f;
            if (this.onGround) {
                n3 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
            }
            final float n4 = 0.16277136f / (n3 * n3 * n3);
            this.moveFlying(n, n2, this.onGround ? (0.1f * n4) : 0.02f);
            float n5 = 0.91f;
            if (this.onGround) {
                n5 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= n5;
            this.motionY *= n5;
            this.motionZ *= n5;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double n6 = this.posX - this.prevPosX;
        final double n7 = this.posZ - this.prevPosZ;
        float n8 = MathHelper.sqrt_double(n6 * n6 + n7 * n7) * 4.0f;
        if (n8 > 1.0f) {
            n8 = 1.0f;
        }
        this.limbSwingAmount += (n8 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }
    
    @Override
    public boolean isOnLadder() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001545";
    }
}
