package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class EntitySmallFireball extends EntityFireball
{
    private static final String __OBFID;
    
    public EntitySmallFireball(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0f);
                if (true) {
                    this.func_174815_a(this.shootingEntity, movingObjectPosition.entityHit);
                    if (!movingObjectPosition.entityHit.isImmuneToFire()) {
                        movingObjectPosition.entityHit.setFire(5);
                    }
                }
            }
            else {
                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving) {
                    this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                }
                if (true) {
                    final BlockPos offset = movingObjectPosition.func_178782_a().offset(movingObjectPosition.field_178784_b);
                    if (this.worldObj.isAirBlock(offset)) {
                        this.worldObj.setBlockState(offset, Blocks.fire.getDefaultState());
                    }
                }
            }
            this.setDead();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return false;
    }
    
    static {
        __OBFID = "CL_00001721";
    }
}
