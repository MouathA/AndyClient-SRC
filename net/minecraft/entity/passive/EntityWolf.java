package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class EntityWolf extends EntityTameable
{
    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isWet;
    private boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private static final String __OBFID;
    
    public EntityWolf(final World world) {
        super(world);
        this.setSize(0.6f, 0.8f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate() {
            private static final String __OBFID;
            final EntityWolf this$0;
            
            public boolean func_180094_a(final Entity entity) {
                return entity instanceof EntitySheep || entity instanceof EntityRabbit;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180094_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002229";
            }
        }));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.setTamed(false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        super.setAttackTarget(attackTarget);
        if (attackTarget == null) {
            this.setAngry(false);
        }
        else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }
    
    @Override
    protected void updateAITasks() {
        this.dataWatcher.updateObject(18, this.getHealth());
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)EnumDyeColor.RED.func_176765_a()));
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.wolf.step", 0.15f, 1.0f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Angry", this.isAngry());
        nbtTagCompound.setByte("CollarColor", (byte)this.func_175546_cu().getDyeColorDamage());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setAngry(nbtTagCompound.getBoolean("Angry"));
        if (nbtTagCompound.hasKey("CollarColor", 99)) {
            this.func_175547_a(EnumDyeColor.func_176766_a(nbtTagCompound.getByte("CollarColor")));
        }
    }
    
    @Override
    protected String getLivingSound() {
        return this.isAngry() ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0f) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemById(-1);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, (byte)8);
        }
        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.headRotationCourseOld = this.headRotationCourse;
        if (this.func_70922_bv()) {
            this.headRotationCourse += (1.0f - this.headRotationCourse) * 0.4f;
        }
        else {
            this.headRotationCourse += (0.0f - this.headRotationCourse) * 0.4f;
        }
        if (this.isWet()) {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.prevTimeWolfIsShaking >= 2.0f) {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                final float n = (float)this.getEntityBoundingBox().minY;
                while (0 < (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * 3.1415927f) * 7.0f)) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f, n + 0.8f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f, this.motionX, this.motionY, this.motionZ, new int[0]);
                    int n2 = 0;
                    ++n2;
                }
            }
        }
    }
    
    public boolean isWolfWet() {
        return this.isWet;
    }
    
    public float getShadingWhileWet(final float n) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n) / 2.0f * 0.25f;
    }
    
    public float getShakeAngle(final float n, final float n2) {
        float n3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n + n2) / 1.8f;
        if (n3 < 0.0f) {
            n3 = 0.0f;
        }
        else if (n3 > 1.0f) {
            n3 = 1.0f;
        }
        return MathHelper.sin(n3 * 3.1415927f) * MathHelper.sin(n3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    public float getInterestedAngle(final float n) {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * n) * 0.15f * 3.1415927f;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        this.aiSit.setSitting(false);
        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
            n = (n + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
        if (attackEntity) {
            this.func_174815_a(this, entity);
        }
        return attackEntity;
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (currentItem != null) {
                if (currentItem.getItem() instanceof ItemFood) {
                    final ItemFood itemFood = (ItemFood)currentItem.getItem();
                    if (itemFood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0f) {
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = currentItem;
                            --itemStack.stackSize;
                        }
                        this.heal((float)itemFood.getHealAmount(currentItem));
                        if (currentItem.stackSize <= 0) {
                            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                        }
                        return true;
                    }
                }
                else if (currentItem.getItem() == Items.dye) {
                    final EnumDyeColor func_176766_a = EnumDyeColor.func_176766_a(currentItem.getMetadata());
                    if (func_176766_a != this.func_175546_cu()) {
                        this.func_175547_a(func_176766_a);
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = currentItem;
                            if (--itemStack2.stackSize <= 0) {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                            }
                        }
                        return true;
                    }
                }
            }
            if (this.func_152114_e(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(currentItem)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPathEntity();
                this.setAttackTarget(null);
            }
        }
        else if (currentItem != null && currentItem.getItem() == Items.bone && !this.isAngry()) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack3 = currentItem;
                --itemStack3.stackSize;
            }
            if (currentItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.navigator.clearPathEntity();
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0f);
                    this.func_152115_b(entityPlayer.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                }
                else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }
            return true;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804f : (this.isTamed() ? ((0.55f - (20.0f - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02f) * 3.1415927f) : 0.62831855f);
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemFood && ((ItemFood)itemStack.getItem()).isWolfsFavoriteMeat();
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }
    
    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0x0;
    }
    
    public void setAngry(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x2));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFD));
        }
    }
    
    public EnumDyeColor func_175546_cu() {
        return EnumDyeColor.func_176766_a(this.dataWatcher.getWatchableObjectByte(20) & 0xF);
    }
    
    public void func_175547_a(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(20, (byte)(enumDyeColor.getDyeColorDamage() & 0xF));
    }
    
    @Override
    public EntityWolf createChild(final EntityAgeable entityAgeable) {
        final EntityWolf entityWolf = new EntityWolf(this.worldObj);
        final String func_152113_b = this.func_152113_b();
        if (func_152113_b != null && func_152113_b.trim().length() > 0) {
            entityWolf.func_152115_b(func_152113_b);
            entityWolf.setTamed(true);
        }
        return entityWolf;
    }
    
    public void func_70918_i(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(19, 1);
        }
        else {
            this.dataWatcher.updateObject(19, 0);
        }
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityAnimal instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf entityWolf = (EntityWolf)entityAnimal;
        return entityWolf.isTamed() && !entityWolf.isSitting() && (this.isInLove() && entityWolf.isInLove());
    }
    
    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    public boolean func_142018_a(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        if (!(entityLivingBase instanceof EntityCreeper) && !(entityLivingBase instanceof EntityGhast)) {
            if (entityLivingBase instanceof EntityWolf) {
                final EntityWolf entityWolf = (EntityWolf)entityLivingBase;
                if (entityWolf.isTamed() && entityWolf.func_180492_cm() == entityLivingBase2) {
                    return false;
                }
            }
            return (!(entityLivingBase instanceof EntityPlayer) || !(entityLivingBase2 instanceof EntityPlayer) || ((EntityPlayer)entityLivingBase2).canAttackPlayer((EntityPlayer)entityLivingBase)) && (!(entityLivingBase instanceof EntityHorse) || !((EntityHorse)entityLivingBase).isTame());
        }
        return false;
    }
    
    public boolean allowLeashing() {
        return !this.isAngry() && super.allowLeashing();
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001654";
    }
}
