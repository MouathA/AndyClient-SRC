package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable theDefendingTameable;
    EntityLivingBase theOwnerAttacker;
    private int field_142051_e;
    private static final String __OBFID;
    
    public EntityAIOwnerHurtByTarget(final EntityTameable theDefendingTameable) {
        super(theDefendingTameable, false);
        this.theDefendingTameable = theDefendingTameable;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return false;
        }
        final EntityLivingBase func_180492_cm = this.theDefendingTameable.func_180492_cm();
        if (func_180492_cm == null) {
            return false;
        }
        this.theOwnerAttacker = func_180492_cm.getAITarget();
        return func_180492_cm.getRevengeTimer() != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.func_142018_a(this.theOwnerAttacker, func_180492_cm);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        final EntityLivingBase func_180492_cm = this.theDefendingTameable.func_180492_cm();
        if (func_180492_cm != null) {
            this.field_142051_e = func_180492_cm.getRevengeTimer();
        }
        super.startExecuting();
    }
    
    static {
        __OBFID = "CL_00001624";
    }
}
