package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLivingBase theTarget;
    private int field_142050_e;
    private static final String __OBFID;
    
    public EntityAIOwnerHurtTarget(final EntityTameable theEntityTameable) {
        super(theEntityTameable, false);
        this.theEntityTameable = theEntityTameable;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        }
        final EntityLivingBase func_180492_cm = this.theEntityTameable.func_180492_cm();
        if (func_180492_cm == null) {
            return false;
        }
        this.theTarget = func_180492_cm.getLastAttacker();
        return func_180492_cm.getLastAttackerTime() != this.field_142050_e && this.isSuitableTarget(this.theTarget, false) && this.theEntityTameable.func_142018_a(this.theTarget, func_180492_cm);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        final EntityLivingBase func_180492_cm = this.theEntityTameable.func_180492_cm();
        if (func_180492_cm != null) {
            this.field_142050_e = func_180492_cm.getLastAttackerTime();
        }
        super.startExecuting();
    }
    
    static {
        __OBFID = "CL_00001625";
    }
}
