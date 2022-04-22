package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    protected EntityCreature attacker;
    int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;
    private double field_151497_i;
    private double field_151495_j;
    private double field_151496_k;
    private static final String __OBFID;
    
    public EntityAIAttackOnCollide(final EntityCreature entityCreature, final Class classTarget, final double n, final boolean b) {
        this(entityCreature, n, b);
        this.classTarget = classTarget;
    }
    
    public EntityAIAttackOnCollide(final EntityCreature attacker, final double speedTowardsTarget, final boolean longMemory) {
        this.attacker = attacker;
        this.worldObj = attacker.worldObj;
        this.speedTowardsTarget = speedTowardsTarget;
        this.longMemory = longMemory;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        if (this.classTarget != null && !this.classTarget.isAssignableFrom(attackTarget.getClass())) {
            return false;
        }
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(attackTarget);
        return this.entityPathEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        return attackTarget != null && attackTarget.isEntityAlive() && (this.longMemory ? this.attacker.func_180485_d(new BlockPos(attackTarget)) : (!this.attacker.getNavigator().noPath()));
    }
    
    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.field_75445_i = 0;
    }
    
    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0f, 30.0f);
        final double distanceSq = this.attacker.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY, attackTarget.posZ);
        final double func_179512_a = this.func_179512_a(attackTarget);
        --this.field_75445_i;
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(attackTarget)) && this.field_75445_i <= 0 && ((this.field_151497_i == 0.0 && this.field_151495_j == 0.0 && this.field_151496_k == 0.0) || attackTarget.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0 || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.field_151497_i = attackTarget.posX;
            this.field_151495_j = attackTarget.getEntityBoundingBox().minY;
            this.field_151496_k = attackTarget.posZ;
            this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
            if (distanceSq > 1024.0) {
                this.field_75445_i += 10;
            }
            else if (distanceSq > 256.0) {
                this.field_75445_i += 5;
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(attackTarget, this.speedTowardsTarget)) {
                this.field_75445_i += 15;
            }
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (distanceSq <= func_179512_a && this.attackTick <= 0) {
            this.attackTick = 20;
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }
            this.attacker.attackEntityAsMob(attackTarget);
        }
    }
    
    protected double func_179512_a(final EntityLivingBase entityLivingBase) {
        return this.attacker.width * 2.0f * this.attacker.width * 2.0f + entityLivingBase.width;
    }
    
    static {
        __OBFID = "CL_00001595";
    }
}
