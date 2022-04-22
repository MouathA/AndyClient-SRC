package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals
{
    private static final String __OBFID;
    
    public EntityWaterMob(final World world) {
        super(world);
    }
    
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return true;
    }
    
    @Override
    public boolean handleLavaMovement() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this);
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return true;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }
    
    @Override
    public void onEntityUpdate() {
        int air = this.getAir();
        super.onEntityUpdate();
        if (this.isEntityAlive() && !this.isInWater()) {
            --air;
            this.setAir(air);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.drown, 2.0f);
            }
        }
        else {
            this.setAir(300);
        }
    }
    
    @Override
    public boolean isPushedByWater() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001653";
    }
}
