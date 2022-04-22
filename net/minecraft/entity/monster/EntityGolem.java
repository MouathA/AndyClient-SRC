package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
    private static final String __OBFID;
    
    public EntityGolem(final World world) {
        super(world);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected String getLivingSound() {
        return "none";
    }
    
    @Override
    protected String getHurtSound() {
        return "none";
    }
    
    @Override
    protected String getDeathSound() {
        return "none";
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001644";
    }
}
