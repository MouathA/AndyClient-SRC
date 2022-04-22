package net.minecraft.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
    private static final String __OBFID;
    
    public EntityAmbientCreature(final World world) {
        super(world);
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    @Override
    protected boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    static {
        __OBFID = "CL_00001636";
    }
}
