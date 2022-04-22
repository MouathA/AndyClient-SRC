package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityTNTPrimed extends Entity
{
    public int fuse;
    private EntityLivingBase tntPlacedBy;
    private static final String __OBFID;
    
    public EntityTNTPrimed(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
    }
    
    public EntityTNTPrimed(final World world, final double prevPosX, final double prevPosY, final double prevPosZ, final EntityLivingBase tntPlacedBy) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        final float n = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.motionX = -(float)Math.sin(n) * 0.02f;
        this.motionY = 0.20000000298023224;
        this.motionZ = -(float)Math.cos(n) * 0.02f;
        this.fuse = 80;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
        this.tntPlacedBy = tntPlacedBy;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setDead();
            if (!this.worldObj.isRemote) {
                this.explode();
            }
        }
        else {
            this.handleWaterMovement();
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    private void explode() {
        this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 2.0f, this.posZ, 4.0f, true);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Fuse", (byte)this.fuse);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.fuse = nbtTagCompound.getByte("Fuse");
    }
    
    public EntityLivingBase getTntPlacedBy() {
        return this.tntPlacedBy;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.0f;
    }
    
    static {
        __OBFID = "CL_00001681";
    }
}
