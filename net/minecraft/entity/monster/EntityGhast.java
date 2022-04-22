package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import java.util.*;
import net.minecraft.util.*;

public class EntityGhast extends EntityFlying implements IMob
{
    private int explosionStrength;
    private static final String __OBFID;
    
    public EntityGhast(final World world) {
        super(world);
        this.explosionStrength = 1;
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        this.moveHelper = new GhastMoveHelper();
        this.tasks.addTask(5, new AIRandomFly());
        this.tasks.addTask(7, new AILookAround());
        this.tasks.addTask(7, new AIFireballAttack());
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    public boolean func_110182_bF() {
        return this.dataWatcher.getWatchableObjectByte(16) != 0;
    }
    
    public void func_175454_a(final boolean b) {
        this.dataWatcher.updateObject(16, (byte)(byte)(b ? 1 : 0));
    }
    
    public int func_175453_cd() {
        return this.explosionStrength;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if ("fireball".equals(damageSource.getDamageType()) && damageSource.getEntity() instanceof EntityPlayer) {
            super.attackEntityFrom(damageSource, 1000.0f);
            ((EntityPlayer)damageSource.getEntity()).triggerAchievement(AchievementList.ghast);
            return true;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.ghast.moan";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.ghast.death";
    }
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.ghast_tear, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(3) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.gunpowder, 1);
            ++n2;
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 10.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("ExplosionPower", this.explosionStrength);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("ExplosionPower", 99)) {
            this.explosionStrength = nbtTagCompound.getInteger("ExplosionPower");
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 2.6f;
    }
    
    static {
        __OBFID = "CL_00001689";
    }
    
    class AIFireballAttack extends EntityAIBase
    {
        private EntityGhast field_179470_b;
        public int field_179471_a;
        private static final String __OBFID;
        final EntityGhast this$0;
        
        AIFireballAttack(final EntityGhast entityGhast) {
            this.this$0 = entityGhast;
            this.field_179470_b = entityGhast;
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179470_b.getAttackTarget() != null;
        }
        
        @Override
        public void startExecuting() {
            this.field_179471_a = 0;
        }
        
        @Override
        public void resetTask() {
            this.field_179470_b.func_175454_a(false);
        }
        
        @Override
        public void updateTask() {
            final EntityLivingBase attackTarget = this.field_179470_b.getAttackTarget();
            final double n = 64.0;
            if (attackTarget.getDistanceSqToEntity(this.field_179470_b) < n * n && this.field_179470_b.canEntityBeSeen(attackTarget)) {
                final World worldObj = this.field_179470_b.worldObj;
                ++this.field_179471_a;
                if (this.field_179471_a == 10) {
                    worldObj.playAuxSFXAtEntity(null, 1007, new BlockPos(this.field_179470_b), 0);
                }
                if (this.field_179471_a == 20) {
                    final double n2 = 4.0;
                    final Vec3 look = this.field_179470_b.getLook(1.0f);
                    final double n3 = attackTarget.posX - (this.field_179470_b.posX + look.xCoord * n2);
                    final double n4 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (0.5 + this.field_179470_b.posY + this.field_179470_b.height / 2.0f);
                    final double n5 = attackTarget.posZ - (this.field_179470_b.posZ + look.zCoord * n2);
                    worldObj.playAuxSFXAtEntity(null, 1008, new BlockPos(this.field_179470_b), 0);
                    final EntityLargeFireball entityLargeFireball = new EntityLargeFireball(worldObj, this.field_179470_b, n3, n4, n5);
                    entityLargeFireball.field_92057_e = this.field_179470_b.func_175453_cd();
                    entityLargeFireball.posX = this.field_179470_b.posX + look.xCoord * n2;
                    entityLargeFireball.posY = this.field_179470_b.posY + this.field_179470_b.height / 2.0f + 0.5;
                    entityLargeFireball.posZ = this.field_179470_b.posZ + look.zCoord * n2;
                    worldObj.spawnEntityInWorld(entityLargeFireball);
                    this.field_179471_a = -40;
                }
            }
            else if (this.field_179471_a > 0) {
                --this.field_179471_a;
            }
            this.field_179470_b.func_175454_a(this.field_179471_a > 10);
        }
        
        static {
            __OBFID = "CL_00002215";
        }
    }
    
    class AILookAround extends EntityAIBase
    {
        private EntityGhast field_179472_a;
        private static final String __OBFID;
        final EntityGhast this$0;
        
        public AILookAround(final EntityGhast entityGhast) {
            this.this$0 = entityGhast;
            this.field_179472_a = entityGhast;
            this.setMutexBits(2);
        }
        
        @Override
        public boolean shouldExecute() {
            return true;
        }
        
        @Override
        public void updateTask() {
            if (this.field_179472_a.getAttackTarget() == null) {
                final EntityGhast field_179472_a = this.field_179472_a;
                final EntityGhast field_179472_a2 = this.field_179472_a;
                final float n = -(float)Math.atan2(this.field_179472_a.motionX, this.field_179472_a.motionZ) * 180.0f / 3.1415927f;
                field_179472_a2.rotationYaw = n;
                field_179472_a.renderYawOffset = n;
            }
            else {
                final EntityLivingBase attackTarget = this.field_179472_a.getAttackTarget();
                final double n2 = 64.0;
                if (attackTarget.getDistanceSqToEntity(this.field_179472_a) < n2 * n2) {
                    final double n3 = attackTarget.posX - this.field_179472_a.posX;
                    final double n4 = attackTarget.posZ - this.field_179472_a.posZ;
                    final EntityGhast field_179472_a3 = this.field_179472_a;
                    final EntityGhast field_179472_a4 = this.field_179472_a;
                    final float n5 = -(float)Math.atan2(n3, n4) * 180.0f / 3.1415927f;
                    field_179472_a4.rotationYaw = n5;
                    field_179472_a3.renderYawOffset = n5;
                }
            }
        }
        
        static {
            __OBFID = "CL_00002217";
        }
    }
    
    class AIRandomFly extends EntityAIBase
    {
        private EntityGhast field_179454_a;
        private static final String __OBFID;
        final EntityGhast this$0;
        
        public AIRandomFly(final EntityGhast entityGhast) {
            this.this$0 = entityGhast;
            this.field_179454_a = entityGhast;
            this.setMutexBits(1);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityMoveHelper moveHelper = this.field_179454_a.getMoveHelper();
            if (!moveHelper.isUpdating()) {
                return true;
            }
            final double n = moveHelper.func_179917_d() - this.field_179454_a.posX;
            final double n2 = moveHelper.func_179919_e() - this.field_179454_a.posY;
            final double n3 = moveHelper.func_179918_f() - this.field_179454_a.posZ;
            final double n4 = n * n + n2 * n2 + n3 * n3;
            return n4 < 1.0 || n4 > 3600.0;
        }
        
        @Override
        public boolean continueExecuting() {
            return false;
        }
        
        @Override
        public void startExecuting() {
            final Random rng = this.field_179454_a.getRNG();
            this.field_179454_a.getMoveHelper().setMoveTo(this.field_179454_a.posX + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.field_179454_a.posY + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.field_179454_a.posZ + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, 1.0);
        }
        
        static {
            __OBFID = "CL_00002214";
        }
    }
    
    class GhastMoveHelper extends EntityMoveHelper
    {
        private EntityGhast field_179927_g;
        private int field_179928_h;
        private static final String __OBFID;
        final EntityGhast this$0;
        
        public GhastMoveHelper(final EntityGhast entityGhast) {
            super(this.this$0 = entityGhast);
            this.field_179927_g = entityGhast;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.update) {
                final double n = this.posX - this.field_179927_g.posX;
                final double n2 = this.posY - this.field_179927_g.posY;
                final double n3 = this.posZ - this.field_179927_g.posZ;
                final double n4 = n * n + n2 * n2 + n3 * n3;
                if (this.field_179928_h-- <= 0) {
                    this.field_179928_h += this.field_179927_g.getRNG().nextInt(5) + 2;
                    final double n5 = MathHelper.sqrt_double(n4);
                    if (this.func_179926_b(this.posX, this.posY, this.posZ, n5)) {
                        final EntityGhast field_179927_g = this.field_179927_g;
                        field_179927_g.motionX += n / n5 * 0.1;
                        final EntityGhast field_179927_g2 = this.field_179927_g;
                        field_179927_g2.motionY += n2 / n5 * 0.1;
                        final EntityGhast field_179927_g3 = this.field_179927_g;
                        field_179927_g3.motionZ += n3 / n5 * 0.1;
                    }
                    else {
                        this.update = false;
                    }
                }
            }
        }
        
        private boolean func_179926_b(final double n, final double n2, final double n3, final double n4) {
            final double n5 = (n - this.field_179927_g.posX) / n4;
            final double n6 = (n2 - this.field_179927_g.posY) / n4;
            final double n7 = (n3 - this.field_179927_g.posZ) / n4;
            AxisAlignedBB axisAlignedBB = this.field_179927_g.getEntityBoundingBox();
            while (1 < n4) {
                axisAlignedBB = axisAlignedBB.offset(n5, n6, n7);
                if (!this.field_179927_g.worldObj.getCollidingBoundingBoxes(this.field_179927_g, axisAlignedBB).isEmpty()) {
                    return false;
                }
                int n8 = 0;
                ++n8;
            }
            return true;
        }
        
        static {
            __OBFID = "CL_00002216";
        }
    }
}
