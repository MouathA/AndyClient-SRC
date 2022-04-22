package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIArrowAttack extends EntityAIBase
{
    private final EntityLiving entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime;
    private double entityMoveSpeed;
    private int field_75318_f;
    private int field_96561_g;
    private int maxRangedAttackTime;
    private float field_96562_i;
    private float maxAttackDistance;
    private static final String __OBFID;
    
    public EntityAIArrowAttack(final IRangedAttackMob rangedAttackMob, final double n, final int n2, final float n3) {
        this(rangedAttackMob, n, n2, n2, n3);
    }
    
    public EntityAIArrowAttack(final IRangedAttackMob rangedAttackEntityHost, final double entityMoveSpeed, final int field_96561_g, final int maxRangedAttackTime, final float field_96562_i) {
        this.rangedAttackTime = -1;
        if (!(rangedAttackEntityHost instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = rangedAttackEntityHost;
        this.entityHost = (EntityLiving)rangedAttackEntityHost;
        this.entityMoveSpeed = entityMoveSpeed;
        this.field_96561_g = field_96561_g;
        this.maxRangedAttackTime = maxRangedAttackTime;
        this.field_96562_i = field_96562_i;
        this.maxAttackDistance = field_96562_i * field_96562_i;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.entityHost.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        this.attackTarget = attackTarget;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }
    
    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }
    
    @Override
    public void updateTask() {
        final double distanceSq = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        final boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        if (canSee) {
            ++this.field_75318_f;
        }
        else {
            this.field_75318_f = 0;
        }
        if (distanceSq <= this.maxAttackDistance && this.field_75318_f >= 20) {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }
        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        final int rangedAttackTime = this.rangedAttackTime - 1;
        this.rangedAttackTime = rangedAttackTime;
        if (rangedAttackTime == 0) {
            if (distanceSq > this.maxAttackDistance || !canSee) {
                return;
            }
            final float n = MathHelper.sqrt_double(distanceSq) / this.field_96562_i;
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, MathHelper.clamp_float(n, 0.1f, 1.0f));
            this.rangedAttackTime = MathHelper.floor_float(n * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
        else if (this.rangedAttackTime < 0) {
            this.rangedAttackTime = MathHelper.floor_float(MathHelper.sqrt_double(distanceSq) / this.field_96562_i * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
    }
    
    static {
        __OBFID = "CL_00001609";
    }
}
