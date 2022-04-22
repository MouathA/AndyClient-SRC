package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.util.*;

public class EntityBat extends EntityAmbientCreature
{
    private BlockPos spawnPosition;
    private static final String __OBFID;
    
    public EntityBat(final World world) {
        super(world);
        this.setSize(0.5f, 0.9f);
        this.setIsBatHanging(true);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }
    
    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }
    
    @Override
    protected String getLivingSound() {
        return (this.getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.bat.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.bat.death";
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    protected void collideWithEntity(final Entity entity) {
    }
    
    @Override
    protected void collideWithNearbyEntities() {
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0);
    }
    
    public boolean getIsBatHanging() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setIsBatHanging(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFE));
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.posY = MathHelper.floor_double(this.posY) + 1.0 - this.height;
        }
        else {
            this.motionY *= 0.6000000238418579;
        }
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        final BlockPos blockPos = new BlockPos(this);
        final BlockPos offsetUp = blockPos.offsetUp();
        if (this.getIsBatHanging()) {
            if (!this.worldObj.getBlockState(offsetUp).getBlock().isNormalCube()) {
                this.setIsBatHanging(false);
                this.worldObj.playAuxSFXAtEntity(null, 1015, blockPos, 0);
            }
            else {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }
                if (this.worldObj.getClosestPlayerToEntity(this, 4.0) != null) {
                    this.setIsBatHanging(false);
                    this.worldObj.playAuxSFXAtEntity(null, 1015, blockPos, 0);
                }
            }
        }
        else {
            if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0) {
                this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }
            final double n = this.spawnPosition.getX() + 0.5 - this.posX;
            final double n2 = this.spawnPosition.getY() + 0.1 - this.posY;
            final double n3 = this.spawnPosition.getZ() + 0.5 - this.posZ;
            this.motionX += (Math.signum(n) * 0.5 - this.motionX) * 0.10000000149011612;
            this.motionY += (Math.signum(n2) * 0.699999988079071 - this.motionY) * 0.10000000149011612;
            this.motionZ += (Math.signum(n3) * 0.5 - this.motionZ) * 0.10000000149011612;
            final float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float((float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += wrapAngleTo180_float;
            if (this.rand.nextInt(100) == 0 && this.worldObj.getBlockState(offsetUp).getBlock().isNormalCube()) {
                this.setIsBatHanging(true);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (!this.worldObj.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.dataWatcher.updateObject(16, nbtTagCompound.getByte("BatFlags"));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockPos.getY() >= 63) {
            return false;
        }
        final int lightFromNeighbors = this.worldObj.getLightFromNeighbors(blockPos);
        if (!this.func_175569_a(this.worldObj.getCurrentDate())) {
            if (this.rand.nextBoolean()) {
                return false;
            }
        }
        return lightFromNeighbors <= this.rand.nextInt(7) && super.getCanSpawnHere();
    }
    
    private boolean func_175569_a(final Calendar calendar) {
        return (calendar.get(2) + 1 == 10 && calendar.get(5) >= 20) || (calendar.get(2) + 1 == 11 && calendar.get(5) <= 3);
    }
    
    @Override
    public float getEyeHeight() {
        return this.height / 2.0f;
    }
    
    static {
        __OBFID = "CL_00001637";
    }
}
