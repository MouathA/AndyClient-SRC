package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntitySnowball extends EntityThrowable
{
    private static final String __OBFID;
    
    public EntitySnowball(final World world) {
        super(world);
    }
    
    public EntitySnowball(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntitySnowball(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition.entityHit != null) {
            if (movingObjectPosition.entityHit instanceof EntityBlaze) {}
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
        }
        while (0 < 8) {
            this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
            int n = 0;
            ++n;
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00001722";
    }
}
