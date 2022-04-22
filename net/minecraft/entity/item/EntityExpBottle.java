package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityExpBottle extends EntityThrowable
{
    private static final String __OBFID;
    
    public EntityExpBottle(final World world) {
        super(world);
    }
    
    public EntityExpBottle(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntityExpBottle(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }
    
    @Override
    protected float func_70182_d() {
        return 0.7f;
    }
    
    @Override
    protected float func_70183_g() {
        return -20.0f;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            this.worldObj.playAuxSFX(2002, new BlockPos(this), 0);
            int i = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
            }
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00001726";
    }
}
