package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityFireworkRocket extends Entity
{
    private int fireworkAge;
    private int lifetime;
    private static final String __OBFID;
    
    public EntityFireworkRocket(final World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObjectByDataType(8, 5);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        return n < 4096.0;
    }
    
    public EntityFireworkRocket(final World world, final double n, final double n2, final double n3, final ItemStack itemStack) {
        super(world);
        this.fireworkAge = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
        if (itemStack != null && itemStack.hasTagCompound()) {
            this.dataWatcher.updateObject(8, itemStack);
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            if (compoundTag != null) {
                final int n4 = 1 + compoundTag.getByte("Flight");
            }
        }
        this.motionX = this.rand.nextGaussian() * 0.001;
        this.motionZ = this.rand.nextGaussian() * 0.001;
        this.motionY = 0.05;
        this.lifetime = 10 + this.rand.nextInt(6) + this.rand.nextInt(7);
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
        this.motionX *= 1.15;
        this.motionZ *= 1.15;
        this.motionY += 0.04;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
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
        if (this.fireworkAge == 0 && !this.isSlient()) {
            this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0f, 1.0f);
        }
        ++this.fireworkAge;
        if (this.worldObj.isRemote && this.fireworkAge % 2 < 2) {
            this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY - 0.3, this.posZ, this.rand.nextGaussian() * 0.05, -this.motionY * 0.5, this.rand.nextGaussian() * 0.05, new int[0]);
        }
        if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
            this.worldObj.setEntityState(this, (byte)17);
            this.setDead();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 17 && this.worldObj.isRemote) {
            final ItemStack watchableObjectItemStack = this.dataWatcher.getWatchableObjectItemStack(8);
            NBTTagCompound compoundTag = null;
            if (watchableObjectItemStack != null && watchableObjectItemStack.hasTagCompound()) {
                compoundTag = watchableObjectItemStack.getTagCompound().getCompoundTag("Fireworks");
            }
            this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, compoundTag);
        }
        super.handleHealthUpdate(b);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("Life", this.fireworkAge);
        nbtTagCompound.setInteger("LifeTime", this.lifetime);
        final ItemStack watchableObjectItemStack = this.dataWatcher.getWatchableObjectItemStack(8);
        if (watchableObjectItemStack != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            watchableObjectItemStack.writeToNBT(nbtTagCompound2);
            nbtTagCompound.setTag("FireworksItem", nbtTagCompound2);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.fireworkAge = nbtTagCompound.getInteger("Life");
        this.lifetime = nbtTagCompound.getInteger("LifeTime");
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("FireworksItem");
        if (compoundTag != null) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(compoundTag);
            if (loadItemStackFromNBT != null) {
                this.dataWatcher.updateObject(8, loadItemStackFromNBT);
            }
        }
    }
    
    @Override
    public float getBrightness(final float n) {
        return super.getBrightness(n);
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return super.getBrightnessForRender(n);
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001718";
    }
}
