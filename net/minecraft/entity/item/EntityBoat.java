package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class EntityBoat extends Entity
{
    private boolean isBoatEmpty;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID;
    
    public EntityBoat(final World world) {
        super(world);
        this.isBoatEmpty = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getEntityBoundingBox();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.getEntityBoundingBox();
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityBoat(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity == damageSource.getEntity() && damageSource instanceof EntityDamageSourceIndirect) {
            return false;
        }
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() + n * 10.0f);
        this.setBeenAttacked();
        final boolean b = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode;
        if (b || this.getDamageTaken() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
            if (!b) {
                this.dropItemWithOffset(Items.boat, 1, 0.0f);
            }
            this.setDead();
        }
        return true;
    }
    
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void func_180426_a(final double boatX, final double boatY, final double boatZ, final float rotationYaw, final float rotationPitch, final int n, final boolean b) {
        if (b && this.riddenByEntity != null) {
            this.posX = boatX;
            this.prevPosX = boatX;
            this.posY = boatY;
            this.prevPosY = boatY;
            this.posZ = boatZ;
            this.prevPosZ = boatZ;
            this.rotationYaw = rotationYaw;
            this.rotationPitch = rotationPitch;
            this.boatPosRotationIncrements = 0;
            this.setPosition(boatX, boatY, boatZ);
            final double n2 = 0.0;
            this.velocityX = n2;
            this.motionX = n2;
            final double n3 = 0.0;
            this.velocityY = n3;
            this.motionY = n3;
            final double n4 = 0.0;
            this.velocityZ = n4;
            this.motionZ = n4;
        }
        else {
            if (this.isBoatEmpty) {
                this.boatPosRotationIncrements = n + 5;
            }
            else {
                final double n5 = boatX - this.posX;
                final double n6 = boatY - this.posY;
                final double n7 = boatZ - this.posZ;
                if (n5 * n5 + n6 * n6 + n7 * n7 <= 1.0) {
                    return;
                }
                this.boatPosRotationIncrements = 3;
            }
            this.boatX = boatX;
            this.boatY = boatY;
            this.boatZ = boatZ;
            this.boatYaw = rotationYaw;
            this.boatPitch = rotationPitch;
            this.motionX = this.velocityX;
            this.motionY = this.velocityY;
            this.motionZ = this.velocityZ;
        }
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final double n = 0.0;
        final double sqrt = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (sqrt > 0.2975) {
            final double cos = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            final double sin = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
            while (0 < 1.0 + sqrt * 60.0) {
                final double n2 = this.rand.nextFloat() * 2.0f - 1.0f;
                final double n3 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX - cos * n2 * 0.8 + sin * n3, this.posY - 0.125, this.posZ - sin * n2 * 0.8 - cos * n3, this.motionX, this.motionY, this.motionZ, new int[0]);
                }
                else {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + cos + sin * n2 * 0.7, this.posY - 0.125, this.posZ + sin - cos * n2 * 0.7, this.motionX, this.motionY, this.motionZ, new int[0]);
                }
                int n4 = 0;
                ++n4;
            }
        }
        if (this.worldObj.isRemote && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                final double n5 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double n6 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double n7 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw) / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(n5, n6, n7);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
        }
        else {
            if (n < 1.0) {
                this.motionY += 0.03999999910593033 * (n * 2.0 - 1.0);
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            if (this.riddenByEntity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)this.riddenByEntity;
                final float n8 = this.riddenByEntity.rotationYaw + -entityLivingBase.moveStrafing * 90.0f;
                this.motionX += -Math.sin(n8 * 3.1415927f / 180.0f) * this.speedMultiplier * entityLivingBase.moveForward * 0.05000000074505806;
                this.motionZ += Math.cos(n8 * 3.1415927f / 180.0f) * this.speedMultiplier * entityLivingBase.moveForward * 0.05000000074505806;
            }
            double sqrt2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt2 > 0.35) {
                final double n9 = 0.35 / sqrt2;
                this.motionX *= n9;
                this.motionZ *= n9;
                sqrt2 = 0.35;
            }
            if (sqrt2 > sqrt && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && sqrt > 0.2) {
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                }
            }
            else {
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
            this.rotationPitch = 0.0f;
            double n10 = this.rotationYaw;
            final double n11 = this.prevPosX - this.posX;
            final double n12 = this.prevPosZ - this.posZ;
            if (n11 * n11 + n12 * n12 > 0.001) {
                n10 = (float)(Math.atan2(n12, n11) * 180.0 / 3.141592653589793);
            }
            double wrapAngleTo180_double = MathHelper.wrapAngleTo180_double(n10 - this.rotationYaw);
            if (wrapAngleTo180_double > 20.0) {
                wrapAngleTo180_double = 20.0;
            }
            if (wrapAngleTo180_double < -20.0) {
                wrapAngleTo180_double = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)wrapAngleTo180_double, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (entitiesWithinAABBExcludingEntity != null && !entitiesWithinAABBExcludingEntity.isEmpty()) {
                    while (0 < entitiesWithinAABBExcludingEntity.size()) {
                        final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
                            entity.applyEntityCollision(this);
                        }
                        int n13 = 0;
                        ++n13;
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }
    
    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX + Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
            return true;
        }
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
        return true;
    }
    
    @Override
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 3.0f) {
                this.fall(this.fallDistance, 1.0f);
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (this.worldObj.getBlockState(new BlockPos(this).offsetDown()).getBlock().getMaterial() != Material.water && n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    public void setDamageTaken(final float n) {
        this.dataWatcher.updateObject(19, n);
    }
    
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setTimeSinceHit(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setIsBoatEmpty(final boolean isBoatEmpty) {
        this.isBoatEmpty = isBoatEmpty;
    }
    
    static {
        __OBFID = "CL_00001667";
    }
}
