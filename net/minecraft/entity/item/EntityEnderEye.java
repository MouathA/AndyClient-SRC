package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class EntityEnderEye extends Entity
{
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;
    private static final String __OBFID;
    
    public EntityEnderEye(final World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0 * 64.0;
        return n < n2 * n2;
    }
    
    public EntityEnderEye(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    public void func_180465_a(final BlockPos blockPos) {
        final double targetX = blockPos.getX();
        final int y = blockPos.getY();
        final double targetZ = blockPos.getZ();
        final double n = targetX - this.posX;
        final double n2 = targetZ - this.posZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        if (sqrt_double > 12.0f) {
            this.targetX = this.posX + n / sqrt_double * 12.0;
            this.targetZ = this.posZ + n2 / sqrt_double * 12.0;
            this.targetY = this.posY + 8.0;
        }
        else {
            this.targetX = targetX;
            this.targetY = y;
            this.targetZ = targetZ;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = (this.rand.nextInt(5) > 0);
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (!this.worldObj.isRemote) {
            final double n = this.targetX - this.posX;
            final double n2 = this.targetZ - this.posZ;
            final float n3 = (float)Math.sqrt(n * n + n2 * n2);
            final float n4 = (float)Math.atan2(n2, n);
            double n5 = sqrt_double + (n3 - sqrt_double) * 0.0025;
            if (n3 < 1.0f) {
                n5 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(n4) * n5;
            this.motionZ = Math.sin(n4) * n5;
            if (this.posY < this.targetY) {
                this.motionY += (1.0 - this.motionY) * 0.014999999664723873;
            }
            else {
                this.motionY += (-1.0 - this.motionY) * 0.014999999664723873;
            }
        }
        final float n6 = 0.25f;
        if (!this.isInWater()) {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX - this.motionX * n6 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * n6 - 0.5, this.posZ - this.motionZ * n6 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ, new int[0]);
        }
        if (!this.worldObj.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.worldObj.isRemote) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
                }
                else {
                    this.worldObj.playAuxSFX(2003, new BlockPos(this), 0);
                }
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001716";
    }
}
