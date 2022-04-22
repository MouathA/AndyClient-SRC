package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class EntityLargeFireball extends EntityFireball
{
    public int field_92057_e;
    private static final String __OBFID;
    
    public EntityLargeFireball(final World world) {
        super(world);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.field_92057_e = 1;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
                this.func_174815_a(this.shootingEntity, movingObjectPosition.entityHit);
            }
            final boolean gameRuleBooleanValue = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, (float)this.field_92057_e, gameRuleBooleanValue, gameRuleBooleanValue);
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("ExplosionPower", this.field_92057_e);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("ExplosionPower", 99)) {
            this.field_92057_e = nbtTagCompound.getInteger("ExplosionPower");
        }
    }
    
    static {
        __OBFID = "CL_00001719";
    }
}
