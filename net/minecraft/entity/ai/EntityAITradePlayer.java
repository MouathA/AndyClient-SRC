package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class EntityAITradePlayer extends EntityAIBase
{
    private EntityVillager villager;
    private static final String __OBFID;
    
    public EntityAITradePlayer(final EntityVillager villager) {
        this.villager = villager;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.villager.isEntityAlive()) {
            return false;
        }
        if (this.villager.isInWater()) {
            return false;
        }
        if (!this.villager.onGround) {
            return false;
        }
        if (this.villager.velocityChanged) {
            return false;
        }
        final EntityPlayer customer = this.villager.getCustomer();
        return customer != null && this.villager.getDistanceSqToEntity(customer) <= 16.0 && customer.openContainer instanceof Container;
    }
    
    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.villager.setCustomer(null);
    }
    
    static {
        __OBFID = "CL_00001617";
    }
}
