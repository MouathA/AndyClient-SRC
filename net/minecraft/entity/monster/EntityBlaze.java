package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;

public class EntityBlaze extends EntityMob
{
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private static final String __OBFID;
    
    public EntityBlaze(final World world) {
        super(world);
        this.heightOffset = 0.5f;
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.tasks.addTask(4, new AIFireballAttack());
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.blaze.death";
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        if (this.worldObj.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSlient()) {
                this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.fire", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
            while (0 < 2) {
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
                int n = 0;
                ++n;
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        final EntityLivingBase attackTarget = this.getAttackTarget();
        if (attackTarget != null && attackTarget.posY + attackTarget.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected Item getDropItem() {
        return Items.blaze_rod;
    }
    
    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (b) {
            while (0 < this.rand.nextInt(2 + n)) {
                this.dropItem(Items.blaze_rod, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void func_70844_e(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x1);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFE);
        }
        this.dataWatcher.updateObject(16, b2);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    static {
        __OBFID = "CL_00001682";
    }
    
    class AIFireballAttack extends EntityAIBase
    {
        private EntityBlaze field_179469_a;
        private int field_179467_b;
        private int field_179468_c;
        private static final String __OBFID;
        final EntityBlaze this$0;
        
        public AIFireballAttack(final EntityBlaze entityBlaze) {
            this.this$0 = entityBlaze;
            this.field_179469_a = entityBlaze;
            this.setMutexBits(3);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.field_179469_a.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }
        
        @Override
        public void startExecuting() {
            this.field_179467_b = 0;
        }
        
        @Override
        public void resetTask() {
            this.field_179469_a.func_70844_e(false);
        }
        
        @Override
        public void updateTask() {
            --this.field_179468_c;
            final EntityLivingBase attackTarget = this.field_179469_a.getAttackTarget();
            final double distanceSqToEntity = this.field_179469_a.getDistanceSqToEntity(attackTarget);
            if (distanceSqToEntity < 4.0) {
                if (this.field_179468_c <= 0) {
                    this.field_179468_c = 20;
                    this.field_179469_a.attackEntityAsMob(attackTarget);
                }
                this.field_179469_a.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
            }
            else if (distanceSqToEntity < 256.0) {
                final double n = attackTarget.posX - this.field_179469_a.posX;
                final double n2 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (this.field_179469_a.posY + this.field_179469_a.height / 2.0f);
                final double n3 = attackTarget.posZ - this.field_179469_a.posZ;
                if (this.field_179468_c <= 0) {
                    ++this.field_179467_b;
                    if (this.field_179467_b == 1) {
                        this.field_179468_c = 60;
                        this.field_179469_a.func_70844_e(true);
                    }
                    else if (this.field_179467_b <= 4) {
                        this.field_179468_c = 6;
                    }
                    else {
                        this.field_179468_c = 100;
                        this.field_179467_b = 0;
                        this.field_179469_a.func_70844_e(false);
                    }
                    if (this.field_179467_b > 1) {
                        final float n4 = MathHelper.sqrt_float(MathHelper.sqrt_double(distanceSqToEntity)) * 0.5f;
                        this.field_179469_a.worldObj.playAuxSFXAtEntity(null, 1009, new BlockPos((int)this.field_179469_a.posX, (int)this.field_179469_a.posY, (int)this.field_179469_a.posZ), 0);
                        while (0 < 1) {
                            final EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.field_179469_a.worldObj, this.field_179469_a, n + this.field_179469_a.getRNG().nextGaussian() * n4, n2, n3 + this.field_179469_a.getRNG().nextGaussian() * n4);
                            entitySmallFireball.posY = this.field_179469_a.posY + this.field_179469_a.height / 2.0f + 0.5;
                            this.field_179469_a.worldObj.spawnEntityInWorld(entitySmallFireball);
                            int n5 = 0;
                            ++n5;
                        }
                    }
                }
                this.field_179469_a.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0f, 10.0f);
            }
            else {
                this.field_179469_a.getNavigator().clearPathEntity();
                this.field_179469_a.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
            }
            super.updateTask();
        }
        
        static {
            __OBFID = "CL_00002225";
        }
    }
}
