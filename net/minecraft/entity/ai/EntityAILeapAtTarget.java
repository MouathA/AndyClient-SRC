package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAILeapAtTarget extends EntityAIBase
{
    EntityLiving leaper;
    EntityLivingBase leapTarget;
    float leapMotionY;
    private static final String __OBFID;
    
    public EntityAILeapAtTarget(final EntityLiving leaper, final float leapMotionY) {
        this.leaper = leaper;
        this.leapMotionY = leapMotionY;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return false;
        }
        final double distanceSqToEntity = this.leaper.getDistanceSqToEntity(this.leapTarget);
        return distanceSqToEntity >= 4.0 && distanceSqToEntity <= 16.0 && this.leaper.onGround && this.leaper.getRNG().nextInt(5) == 0;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.leaper.onGround;
    }
    
    @Override
    public void startExecuting() {
        final double n = this.leapTarget.posX - this.leaper.posX;
        final double n2 = this.leapTarget.posZ - this.leaper.posZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        final EntityLiving leaper = this.leaper;
        leaper.motionX += n / sqrt_double * 0.5 * 0.800000011920929 + this.leaper.motionX * 0.20000000298023224;
        final EntityLiving leaper2 = this.leaper;
        leaper2.motionZ += n2 / sqrt_double * 0.5 * 0.800000011920929 + this.leaper.motionZ * 0.20000000298023224;
        this.leaper.motionY = this.leapMotionY;
    }
    
    static {
        __OBFID = "CL_00001591";
    }
}
