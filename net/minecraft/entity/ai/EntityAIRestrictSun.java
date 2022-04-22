package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature theEntity;
    private static final String __OBFID;
    
    public EntityAIRestrictSun(final EntityCreature theEntity) {
        this.theEntity = theEntity;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }
    
    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.theEntity.getNavigator()).func_179685_e(true);
    }
    
    @Override
    public void resetTask() {
        ((PathNavigateGround)this.theEntity.getNavigator()).func_179685_e(false);
    }
    
    static {
        __OBFID = "CL_00001611";
    }
}
