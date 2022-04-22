package net.minecraft.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityDragonPart extends Entity
{
    public final IEntityMultiPart entityDragonObj;
    public final String field_146032_b;
    private static final String __OBFID;
    
    public EntityDragonPart(final IEntityMultiPart entityDragonObj, final String field_146032_b, final float n, final float n2) {
        super(entityDragonObj.func_82194_d());
        this.setSize(n, n2);
        this.entityDragonObj = entityDragonObj;
        this.field_146032_b = field_146032_b;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return !this.func_180431_b(damageSource) && this.entityDragonObj.attackEntityFromPart(this, damageSource, n);
    }
    
    @Override
    public boolean isEntityEqual(final Entity entity) {
        return this == entity || this.entityDragonObj == entity;
    }
    
    static {
        __OBFID = "CL_00001657";
    }
}
