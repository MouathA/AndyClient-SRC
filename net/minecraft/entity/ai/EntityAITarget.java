package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;

public abstract class EntityAITarget extends EntityAIBase
{
    protected final EntityCreature taskOwner;
    protected boolean shouldCheckSight;
    private boolean nearbyOnly;
    private int targetSearchStatus;
    private int targetSearchDelay;
    private int targetUnseenTicks;
    private static final String __OBFID;
    
    public EntityAITarget(final EntityCreature entityCreature, final boolean b) {
        this(entityCreature, b, false);
    }
    
    public EntityAITarget(final EntityCreature taskOwner, final boolean shouldCheckSight, final boolean nearbyOnly) {
        this.taskOwner = taskOwner;
        this.shouldCheckSight = shouldCheckSight;
        this.nearbyOnly = nearbyOnly;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.taskOwner.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        final Team team = this.taskOwner.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return false;
        }
        final double targetDistance = this.getTargetDistance();
        if (this.taskOwner.getDistanceSqToEntity(attackTarget) > targetDistance * targetDistance) {
            return false;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(attackTarget)) {
                this.targetUnseenTicks = 0;
            }
            else if (++this.targetUnseenTicks > 60) {
                return false;
            }
        }
        return !(attackTarget instanceof EntityPlayer) || !((EntityPlayer)attackTarget).capabilities.disableDamage;
    }
    
    protected double getTargetDistance() {
        final IAttributeInstance entityAttribute = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (entityAttribute == null) ? 16.0 : entityAttribute.getAttributeValue();
    }
    
    @Override
    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }
    
    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget(null);
    }
    
    public static boolean func_179445_a(final EntityLiving entityLiving, final EntityLivingBase entityLivingBase, final boolean b, final boolean b2) {
        if (entityLivingBase == null) {
            return false;
        }
        if (entityLivingBase == entityLiving) {
            return false;
        }
        if (!entityLivingBase.isEntityAlive()) {
            return false;
        }
        if (!entityLiving.canAttackClass(entityLivingBase.getClass())) {
            return false;
        }
        final Team team = entityLiving.getTeam();
        final Team team2 = entityLivingBase.getTeam();
        if (team != null && team2 == team) {
            return false;
        }
        if (entityLiving instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)entityLiving).func_152113_b())) {
            if (entityLivingBase instanceof IEntityOwnable && ((IEntityOwnable)entityLiving).func_152113_b().equals(((IEntityOwnable)entityLivingBase).func_152113_b())) {
                return false;
            }
            if (entityLivingBase == ((IEntityOwnable)entityLiving).getOwner()) {
                return false;
            }
        }
        else if (entityLivingBase instanceof EntityPlayer && !b && ((EntityPlayer)entityLivingBase).capabilities.disableDamage) {
            return false;
        }
        return !b2 || entityLiving.getEntitySenses().canSee(entityLivingBase);
    }
    
    protected boolean isSuitableTarget(final EntityLivingBase entityLivingBase, final boolean b) {
        if (!func_179445_a(this.taskOwner, entityLivingBase, b, this.shouldCheckSight)) {
            return false;
        }
        if (!this.taskOwner.func_180485_d(new BlockPos(entityLivingBase))) {
            return false;
        }
        if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
                this.targetSearchStatus = 0;
            }
            if (this.targetSearchStatus == 0) {
                this.targetSearchStatus = (this.canEasilyReach(entityLivingBase) ? 1 : 2);
            }
            if (this.targetSearchStatus == 2) {
                return false;
            }
        }
        return true;
    }
    
    private boolean canEasilyReach(final EntityLivingBase entityLivingBase) {
        this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
        final PathEntity pathToEntityLiving = this.taskOwner.getNavigator().getPathToEntityLiving(entityLivingBase);
        if (pathToEntityLiving == null) {
            return false;
        }
        final PathPoint finalPathPoint = pathToEntityLiving.getFinalPathPoint();
        if (finalPathPoint == null) {
            return false;
        }
        final int n = finalPathPoint.xCoord - MathHelper.floor_double(entityLivingBase.posX);
        final int n2 = finalPathPoint.zCoord - MathHelper.floor_double(entityLivingBase.posZ);
        return n * n + n2 * n2 <= 2.25;
    }
    
    static {
        __OBFID = "CL_00001626";
    }
}
