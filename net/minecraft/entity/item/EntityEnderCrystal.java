package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityEnderCrystal extends Entity
{
    public int innerRotation;
    public int health;
    private static final String __OBFID;
    
    public EntityEnderCrystal(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 2.0f);
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityEnderCrystal(final World world, final double n, final double n2, final double n3) {
        this(world);
        this.setPosition(n, n2, n3);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(8, this.health);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.innerRotation;
        this.dataWatcher.updateObject(8, this.health);
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posY);
        final int floor_double3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.provider instanceof WorldProviderEnd && this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3)).getBlock() != Blocks.fire) {
            this.worldObj.setBlockState(new BlockPos(floor_double, floor_double2, floor_double3), Blocks.fire.getDefaultState());
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.health = 0;
            if (this.health <= 0) {
                this.setDead();
                if (!this.worldObj.isRemote) {
                    this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0f, true);
                }
            }
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00001658";
    }
}
