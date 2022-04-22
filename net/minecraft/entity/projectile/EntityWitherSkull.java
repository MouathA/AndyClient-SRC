package net.minecraft.entity.projectile;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWitherSkull extends EntityFireball
{
    private static final String __OBFID;
    
    public EntityWitherSkull(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntityWitherSkull(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73f : super.getMotionFactor();
    }
    
    public EntityWitherSkull(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    @Override
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        float n = super.getExplosionResistance(explosion, world, blockPos, blockState);
        if (this.isInvulnerable() && blockState.getBlock() != Blocks.bedrock && blockState.getBlock() != Blocks.end_portal && blockState.getBlock() != Blocks.end_portal_frame && blockState.getBlock() != Blocks.command_block) {
            n = Math.min(0.8f, n);
        }
        return n;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f)) {
                        if (!movingObjectPosition.entityHit.isEntityAlive()) {
                            this.shootingEntity.heal(5.0f);
                        }
                        else {
                            this.func_174815_a(this.shootingEntity, movingObjectPosition.entityHit);
                        }
                    }
                }
                else {
                    movingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0f);
                }
                if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
                    if (this.worldObj.getDifficulty() != EnumDifficulty.NORMAL) {
                        if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {}
                    }
                    if (40 > 0) {
                        ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 800, 1));
                    }
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
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
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, 0);
    }
    
    public boolean isInvulnerable() {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }
    
    public void setInvulnerable(final boolean b) {
        this.dataWatcher.updateObject(10, (byte)(byte)(b ? 1 : 0));
    }
    
    static {
        __OBFID = "CL_00001728";
    }
}
