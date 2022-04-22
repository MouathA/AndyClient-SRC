package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    private float[] field_82220_d;
    private float[] field_82221_e;
    private float[] field_82217_f;
    private float[] field_82218_g;
    private int[] field_82223_h;
    private int[] field_82224_i;
    private int field_82222_j;
    private static final Predicate attackEntitySelector;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001661";
        attackEntitySelector = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180027_a(final Entity entity) {
                return entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180027_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001662";
            }
        };
    }
    
    public EntityWither(final World world) {
        super(world);
        this.field_82220_d = new float[2];
        this.field_82221_e = new float[2];
        this.field_82217_f = new float[2];
        this.field_82218_g = new float[2];
        this.field_82223_h = new int[2];
        this.field_82224_i = new int[2];
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).func_179693_d(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0f));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, EntityWither.attackEntitySelector));
        this.experienceValue = 50;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Invul", this.getInvulTime());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setInvulTime(nbtTagCompound.getInteger("Invul"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.wither.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wither.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wither.death";
    }
    
    @Override
    public void onLivingUpdate() {
        this.motionY *= 0.6000000238418579;
        if (!this.worldObj.isRemote && this.getWatchedTargetId(0) > 0) {
            final Entity entityByID = this.worldObj.getEntityByID(this.getWatchedTargetId(0));
            if (entityByID != null) {
                if (this.posY < entityByID.posY || (this <= 0 && this.posY < entityByID.posY + 5.0)) {
                    if (this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }
                    this.motionY += (0.5 - this.motionY) * 0.6000000238418579;
                }
                final double n = entityByID.posX - this.posX;
                final double n2 = entityByID.posZ - this.posZ;
                final double n3 = n * n + n2 * n2;
                if (n3 > 9.0) {
                    final double n4 = MathHelper.sqrt_double(n3);
                    this.motionX += (n / n4 * 0.5 - this.motionX) * 0.6000000238418579;
                    this.motionZ += (n2 / n4 * 0.5 - this.motionZ) * 0.6000000238418579;
                }
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806) {
            this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776f - 90.0f;
        }
        super.onLivingUpdate();
        this.isArmored();
        if (this.getInvulTime() > 0) {}
    }
    
    @Override
    protected void updateAITasks() {
        if (this.getInvulTime() > 0) {
            final int n = this.getInvulTime() - 1;
            this.setInvulTime(1);
            if (this.ticksExisted % 10 == 0) {
                this.heal(10.0f);
            }
        }
        else {
            super.updateAITasks();
            if (this.getAttackTarget() != null) {
                this.func_82211_c(0, this.getAttackTarget().getEntityId());
            }
            else {
                this.func_82211_c(0, 0);
            }
            if (this.field_82222_j > 0) {
                --this.field_82222_j;
                if (this.field_82222_j == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    MathHelper.floor_double(this.posY);
                    MathHelper.floor_double(this.posX);
                    MathHelper.floor_double(this.posZ);
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
        }
    }
    
    public void func_82206_m() {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public int getTotalArmorValue() {
        return 4;
    }
    
    private double func_82214_u(final int n) {
        if (n <= 0) {
            return this.posX;
        }
        return this.posX + MathHelper.cos((this.renderYawOffset + 180 * (n - 1)) / 180.0f * 3.1415927f) * 1.3;
    }
    
    private double func_82208_v(final int n) {
        return (n <= 0) ? (this.posY + 3.0) : (this.posY + 2.2);
    }
    
    private double func_82213_w(final int n) {
        if (n <= 0) {
            return this.posZ;
        }
        return this.posZ + MathHelper.sin((this.renderYawOffset + 180 * (n - 1)) / 180.0f * 3.1415927f) * 1.3;
    }
    
    private float func_82204_b(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    private void launchWitherSkullToEntity(final int n, final EntityLivingBase entityLivingBase) {
        this.launchWitherSkullToCoords(n, entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.5, entityLivingBase.posZ, n == 0 && this.rand.nextFloat() < 0.001f);
    }
    
    private void launchWitherSkullToCoords(final int n, final double n2, final double n3, final double n4, final boolean b) {
        this.worldObj.playAuxSFXAtEntity(null, 1014, new BlockPos(this), 0);
        final double func_82214_u = this.func_82214_u(n);
        final double func_82208_v = this.func_82208_v(n);
        final double func_82213_w = this.func_82213_w(n);
        final EntityWitherSkull entityWitherSkull = new EntityWitherSkull(this.worldObj, this, n2 - func_82214_u, n3 - func_82208_v, n4 - func_82213_w);
        if (b) {
            entityWitherSkull.setInvulnerable(true);
        }
        entityWitherSkull.posY = func_82208_v;
        entityWitherSkull.posX = func_82214_u;
        entityWitherSkull.posZ = func_82213_w;
        this.worldObj.spawnEntityInWorld(entityWitherSkull);
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        this.launchWitherSkullToEntity(0, entityLivingBase);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (damageSource == DamageSource.drown || damageSource.getEntity() instanceof EntityWither) {
            return false;
        }
        if (this.getInvulTime() > 0 && damageSource != DamageSource.outOfWorld) {
            return false;
        }
        if (this <= 0 && damageSource.getSourceOfDamage() instanceof EntityArrow) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == this.getCreatureAttribute()) {
            return false;
        }
        if (this.field_82222_j <= 0) {
            this.field_82222_j = 20;
        }
        while (0 < this.field_82224_i.length) {
            final int[] field_82224_i = this.field_82224_i;
            final int n2 = 0;
            field_82224_i[n2] += 3;
            int n3 = 0;
            ++n3;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final EntityItem dropItem = this.dropItem(Items.nether_star, 1);
        if (dropItem != null) {
            dropItem.func_174873_u();
        }
        if (!this.worldObj.isRemote) {
            final Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0, 100.0, 50.0)).iterator();
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.killWither);
            }
        }
    }
    
    @Override
    protected void despawnEntity() {
        this.entityAge = 0;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    public void addPotionEffect(final PotionEffect potionEffect) {
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }
    
    public float func_82207_a(final int n) {
        return this.field_82221_e[n];
    }
    
    public float func_82210_r(final int n) {
        return this.field_82220_d[n];
    }
    
    public int getInvulTime() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    public void setInvulTime(final int n) {
        this.dataWatcher.updateObject(20, n);
    }
    
    public int getWatchedTargetId(final int n) {
        return this.dataWatcher.getWatchableObjectInt(17 + n);
    }
    
    public void func_82211_c(final int n, final int n2) {
        this.dataWatcher.updateObject(17 + n, n2);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        this.ridingEntity = null;
    }
}
