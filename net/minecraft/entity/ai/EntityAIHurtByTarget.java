package net.minecraft.entity.ai;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;

public class EntityAIHurtByTarget extends EntityAITarget
{
    private boolean entityCallsForHelp;
    private int revengeTimerOld;
    private final Class[] field_179447_c;
    private static final String __OBFID;
    
    public EntityAIHurtByTarget(final EntityCreature entityCreature, final boolean entityCallsForHelp, final Class... field_179447_c) {
        super(entityCreature, false);
        this.entityCallsForHelp = entityCallsForHelp;
        this.field_179447_c = field_179447_c;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.taskOwner.getRevengeTimer() != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();
        if (this.entityCallsForHelp) {
            final double targetDistance = this.getTargetDistance();
            for (final EntityCreature entityCreature : this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(targetDistance, 10.0, targetDistance))) {
                if (this.taskOwner != entityCreature && entityCreature.getAttackTarget() == null && !entityCreature.isOnSameTeam(this.taskOwner.getAITarget())) {
                    final Class[] field_179447_c = this.field_179447_c;
                    while (0 < field_179447_c.length && entityCreature.getClass() != field_179447_c[0]) {
                        int n = 0;
                        ++n;
                    }
                    if (true) {
                        continue;
                    }
                    this.func_179446_a(entityCreature, this.taskOwner.getAITarget());
                }
            }
        }
        super.startExecuting();
    }
    
    protected void func_179446_a(final EntityCreature entityCreature, final EntityLivingBase attackTarget) {
        entityCreature.setAttackTarget(attackTarget);
    }
    
    static {
        __OBFID = "CL_00001619";
    }
}
