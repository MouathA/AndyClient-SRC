package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.village.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityAIVillagerMate extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityVillager mate;
    private World worldObj;
    private int matingTimeout;
    Village villageObj;
    private static final String __OBFID;
    
    public EntityAIVillagerMate(final EntityVillager villagerObj) {
        this.villagerObj = villagerObj;
        this.worldObj = villagerObj.worldObj;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() != 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(500) != 0) {
            return false;
        }
        this.villageObj = this.worldObj.getVillageCollection().func_176056_a(new BlockPos(this.villagerObj), 0);
        if (this.villageObj == null) {
            return false;
        }
        if (!this.checkSufficientDoorsPresentForNewVillager() || !this.villagerObj.func_175550_n(true)) {
            return false;
        }
        final Entity nearestEntityWithinAABB = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0, 3.0, 8.0), this.villagerObj);
        if (nearestEntityWithinAABB == null) {
            return false;
        }
        this.mate = (EntityVillager)nearestEntityWithinAABB;
        return this.mate.getGrowingAge() == 0 && this.mate.func_175550_n(true);
    }
    
    @Override
    public void startExecuting() {
        this.matingTimeout = 300;
        this.villagerObj.setMating(true);
    }
    
    @Override
    public void resetTask() {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating(false);
    }
    
    @Override
    public boolean continueExecuting() {
        return this.matingTimeout >= 0 && this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.func_175550_n(false);
    }
    
    @Override
    public void updateTask() {
        --this.matingTimeout;
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0f, 30.0f);
        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25) {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25);
        }
        else if (this.matingTimeout == 0 && this.mate.isMating()) {
            this.giveBirth();
        }
        if (this.villagerObj.getRNG().nextInt(35) == 0) {
            this.worldObj.setEntityState(this.villagerObj, (byte)12);
        }
    }
    
    private boolean checkSufficientDoorsPresentForNewVillager() {
        return this.villageObj.isMatingSeason() && this.villageObj.getNumVillagers() < (int)((float)this.villageObj.getNumVillageDoors() * 0.35);
    }
    
    private void giveBirth() {
        final EntityVillager func_180488_b = this.villagerObj.func_180488_b(this.mate);
        this.mate.setGrowingAge(6000);
        this.villagerObj.setGrowingAge(6000);
        this.mate.func_175549_o(false);
        this.villagerObj.func_175549_o(false);
        func_180488_b.setGrowingAge(-24000);
        func_180488_b.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(func_180488_b);
        this.worldObj.setEntityState(func_180488_b, (byte)12);
    }
    
    static {
        __OBFID = "CL_00001594";
    }
}
