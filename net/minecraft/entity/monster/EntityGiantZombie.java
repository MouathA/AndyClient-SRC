package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityGiantZombie extends EntityMob
{
    private static final String __OBFID;
    
    public EntityGiantZombie(final World world) {
        super(world);
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }
    
    @Override
    public float getEyeHeight() {
        return 10.440001f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
    }
    
    @Override
    public float func_180484_a(final BlockPos blockPos) {
        return this.worldObj.getLightBrightness(blockPos) - 0.5f;
    }
    
    static {
        __OBFID = "CL_00001690";
    }
}
