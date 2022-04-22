package net.minecraft.entity;

import net.minecraft.world.*;
import org.apache.commons.lang3.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public abstract class EntityHanging extends Entity
{
    private int tickCounter1;
    protected BlockPos field_174861_a;
    public EnumFacing field_174860_b;
    private static final String __OBFID;
    
    public EntityHanging(final World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityHanging(final World world, final BlockPos field_174861_a) {
        this(world);
        this.field_174861_a = field_174861_a;
    }
    
    @Override
    protected void entityInit() {
    }
    
    protected void func_174859_a(final EnumFacing field_174860_b) {
        Validate.notNull(field_174860_b);
        Validate.isTrue(field_174860_b.getAxis().isHorizontal());
        this.field_174860_b = field_174860_b;
        final float n = (float)(this.field_174860_b.getHorizontalIndex() * 90);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        this.func_174856_o();
    }
    
    private void func_174856_o() {
        if (this.field_174860_b != null) {
            final double n = this.field_174861_a.getX() + 0.5;
            final double n2 = this.field_174861_a.getY() + 0.5;
            final double n3 = this.field_174861_a.getZ() + 0.5;
            final double func_174858_a = this.func_174858_a(this.getWidthPixels());
            final double func_174858_a2 = this.func_174858_a(this.getHeightPixels());
            final double n4 = n - this.field_174860_b.getFrontOffsetX() * 0.46875;
            final double n5 = n3 - this.field_174860_b.getFrontOffsetZ() * 0.46875;
            final double posY = n2 + func_174858_a2;
            final EnumFacing rotateYCCW = this.field_174860_b.rotateYCCW();
            final double posX = n4 + func_174858_a * rotateYCCW.getFrontOffsetX();
            final double posZ = n5 + func_174858_a * rotateYCCW.getFrontOffsetZ();
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            double n6 = this.getWidthPixels();
            final double n7 = this.getHeightPixels();
            double n8 = this.getWidthPixels();
            if (this.field_174860_b.getAxis() == EnumFacing.Axis.Z) {
                n8 = 1.0;
            }
            else {
                n6 = 1.0;
            }
            final double n9 = n6 / 32.0;
            final double n10 = n7 / 32.0;
            final double n11 = n8 / 32.0;
            this.func_174826_a(new AxisAlignedBB(posX - n9, posY - n10, posZ - n11, posX + n9, posY + n10, posZ + n11));
        }
    }
    
    private double func_174858_a(final int n) {
        return (n % 32 == 0) ? 0.5 : 0.0;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
            this.tickCounter1 = 0;
            if (!this.isDead && this == 0) {
                this.setDead();
                this.onBroken(null);
            }
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean hitByEntity(final Entity entity) {
        return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f);
    }
    
    @Override
    public EnumFacing func_174811_aO() {
        return this.field_174860_b;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(damageSource.getEntity());
        }
        return true;
    }
    
    @Override
    public void moveEntity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    @Override
    public void addVelocity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Facing", (byte)this.field_174860_b.getHorizontalIndex());
        nbtTagCompound.setInteger("TileX", this.func_174857_n().getX());
        nbtTagCompound.setInteger("TileY", this.func_174857_n().getY());
        nbtTagCompound.setInteger("TileZ", this.func_174857_n().getZ());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.field_174861_a = new BlockPos(nbtTagCompound.getInteger("TileX"), nbtTagCompound.getInteger("TileY"), nbtTagCompound.getInteger("TileZ"));
        EnumFacing enumFacing;
        if (nbtTagCompound.hasKey("Direction", 99)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Direction"));
            this.field_174861_a = this.field_174861_a.offset(enumFacing);
        }
        else if (nbtTagCompound.hasKey("Facing", 99)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Facing"));
        }
        else {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Dir"));
        }
        this.func_174859_a(enumFacing);
    }
    
    public abstract int getWidthPixels();
    
    public abstract int getHeightPixels();
    
    public abstract void onBroken(final Entity p0);
    
    @Override
    protected boolean shouldSetPosAfterLoading() {
        return false;
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final BlockPos field_174861_a = this.field_174861_a;
        this.field_174861_a = new BlockPos(posX, posY, posZ);
        if (!this.field_174861_a.equals(field_174861_a)) {
            this.func_174856_o();
            this.isAirBorne = true;
        }
    }
    
    public BlockPos func_174857_n() {
        return this.field_174861_a;
    }
    
    static {
        __OBFID = "CL_00001546";
    }
}
