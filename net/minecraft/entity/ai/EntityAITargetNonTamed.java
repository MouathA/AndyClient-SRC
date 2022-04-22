package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
    private EntityTameable theTameable;
    private static final String __OBFID;
    
    public EntityAITargetNonTamed(final EntityTameable theTameable, final Class clazz, final boolean b, final Predicate predicate) {
        super(theTameable, clazz, 10, b, false, predicate);
        this.theTameable = theTameable;
    }
    
    @Override
    public boolean shouldExecute() {
        return !this.theTameable.isTamed() && super.shouldExecute();
    }
    
    static {
        __OBFID = "CL_00001623";
    }
}
