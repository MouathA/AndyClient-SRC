package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

public class EntityEnderPearl extends EntityThrowable
{
    private static final String __OBFID;
    
    public EntityEnderPearl(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntityEnderPearl(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        final EntityLivingBase thrower = this.getThrower();
        if (movingObjectPosition.entityHit != null) {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0.0f);
        }
        while (0 < 32) {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0, this.posZ, this.rand.nextGaussian(), 0.0, this.rand.nextGaussian(), new int[0]);
            int n = 0;
            ++n;
        }
        if (!this.worldObj.isRemote) {
            if (thrower instanceof EntityPlayerMP) {
                final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)thrower;
                if (entityPlayerMP.playerNetServerHandler.getNetworkManager().isChannelOpen() && entityPlayerMP.worldObj == this.worldObj && !entityPlayerMP.isPlayerSleeping()) {
                    if (this.rand.nextFloat() < 0.05f && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
                        final EntityEndermite entityEndermite = new EntityEndermite(this.worldObj);
                        entityEndermite.setSpawnedByPlayer(true);
                        entityEndermite.setLocationAndAngles(thrower.posX, thrower.posY, thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
                        this.worldObj.spawnEntityInWorld(entityEndermite);
                    }
                    if (thrower.isRiding()) {
                        thrower.mountEntity(null);
                    }
                    thrower.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    thrower.fallDistance = 0.0f;
                    thrower.attackEntityFrom(DamageSource.fall, 5.0f);
                }
            }
            this.setDead();
        }
    }
    
    @Override
    public void onUpdate() {
        final EntityLivingBase thrower = this.getThrower();
        if (thrower != null && thrower instanceof EntityPlayer && !thrower.isEntityAlive()) {
            this.setDead();
        }
        else {
            super.onUpdate();
        }
    }
    
    static {
        __OBFID = "CL_00001725";
    }
}
