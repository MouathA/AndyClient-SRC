package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public abstract class EntityMob extends EntityCreature implements IMob
{
    protected final EntityAIBase field_175455_a;
    private static final String __OBFID;
    
    public EntityMob(final World world) {
        super(world);
        this.field_175455_a = new EntityAIAvoidEntity(this, new Predicate() {
            private static final String __OBFID;
            final EntityMob this$0;
            
            public boolean func_179911_a(final Entity entity) {
                return entity instanceof EntityCreeper && ((EntityCreeper)entity).getCreeperState() > 0;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179911_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002208";
            }
        }, 4.0f, 1.0, 2.0);
        this.experienceValue = 5;
    }
    
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        if (this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    @Override
    protected String getSwimSound() {
        return "game.hostile.swim";
    }
    
    @Override
    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (super.attackEntityFrom(damageSource, n)) {
            final Entity entity = damageSource.getEntity();
            return this.riddenByEntity == entity || this.ridingEntity == entity || true;
        }
        return false;
    }
    
    @Override
    protected String getHurtSound() {
        return "game.hostile.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "game.hostile.die";
    }
    
    @Override
    protected String func_146067_o(final int n) {
        return (n > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        if (entity instanceof EntityLivingBase) {
            n += EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)entity).getCreatureAttribute());
            final int n2 = 0 + EnchantmentHelper.getRespiration(this);
        }
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), n);
        if (attackEntity) {
            if (0 > 0) {
                entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * 0 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * 0 * 0.5f);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
            if (fireAspectModifier > 0) {
                entity.setFire(fireAspectModifier * 4);
            }
            this.func_174815_a(this, entity);
        }
        return attackEntity;
    }
    
    @Override
    public float func_180484_a(final BlockPos blockPos) {
        return 0.5f - this.worldObj.getLightBrightness(blockPos);
    }
    
    protected boolean isValidLightLevel() {
        final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockPos) > this.rand.nextInt(32)) {
            return false;
        }
        int n = this.worldObj.getLightFromNeighbors(blockPos);
        if (this.worldObj.isThundering()) {
            final int skylightSubtracted = this.worldObj.getSkylightSubtracted();
            this.worldObj.setSkylightSubtracted(10);
            n = this.worldObj.getLightFromNeighbors(blockPos);
            this.worldObj.setSkylightSubtracted(skylightSubtracted);
        }
        return n <= this.rand.nextInt(8);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }
    
    @Override
    protected boolean func_146066_aG() {
        return true;
    }
    
    static {
        __OBFID = "CL_00001692";
    }
}
