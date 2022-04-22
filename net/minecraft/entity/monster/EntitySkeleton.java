package net.minecraft.entity.monster;

import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack aiArrowAttack;
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private static final String __OBFID;
    
    public EntitySkeleton(final World world) {
        super(world);
        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0f);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate() {
            private static final String __OBFID;
            final EntitySkeleton this$0;
            
            public boolean func_179945_a(final Entity entity) {
                return entity instanceof EntityWolf;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179945_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002203";
            }
        }, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        if (world != null && !world.isRemote) {
            this.setCombatTask();
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte)0));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.skeleton.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (this.getSkeletonType() == 1 && entity instanceof EntityLivingBase) {
                ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            final float brightness = this.getBrightness(1.0f);
            final BlockPos blockPos = new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);
            if (brightness > 0.5f && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f && this.worldObj.isAgainstSky(blockPos)) {
                final ItemStack equipmentInSlot = this.getEquipmentInSlot(4);
                if (equipmentInSlot != null && equipmentInSlot.isItemStackDamageable()) {
                    equipmentInSlot.setItemDamage(equipmentInSlot.getItemDamage() + this.rand.nextInt(2));
                    if (equipmentInSlot.getItemDamage() >= equipmentInSlot.getMaxDamage()) {
                        this.renderBrokenItemStack(equipmentInSlot);
                        this.setCurrentItemOrArmor(4, null);
                    }
                }
                if (false) {
                    this.setFire(8);
                }
            }
        }
        if (this.worldObj.isRemote && this.getSkeletonType() == 1) {
            this.setSize(0.72f, 2.535f);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        if (this.ridingEntity instanceof EntityCreature) {
            this.renderYawOffset = ((EntityCreature)this.ridingEntity).renderYawOffset;
        }
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getSourceOfDamage() instanceof EntityArrow && damageSource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)damageSource.getEntity();
            final double n = entityPlayer.posX - this.posX;
            final double n2 = entityPlayer.posZ - this.posZ;
            if (n * n + n2 * n2 >= 2500.0) {
                entityPlayer.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
        else if (damageSource.getEntity() instanceof EntityCreeper && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, (int)((this.getSkeletonType() == 1) ? 1 : 0)), 0.0f);
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Items.arrow;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        if (this.getSkeletonType() == 1) {
            while (0 < this.rand.nextInt(3 + n) - 1) {
                this.dropItem(Items.coal, 1);
                ++n2;
            }
        }
        else {
            while (0 < this.rand.nextInt(3 + n)) {
                this.dropItem(Items.arrow, 1);
                ++n2;
            }
        }
        while (0 < this.rand.nextInt(3 + n)) {
            this.dropItem(Items.bone, 1);
            ++n2;
        }
    }
    
    @Override
    protected void addRandomArmor() {
        if (this.getSkeletonType() == 1) {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0f);
        }
    }
    
    @Override
    protected void func_180481_a(final DifficultyInstance difficultyInstance) {
        super.func_180481_a(difficultyInstance);
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, IEntityLivingData func_180482_a) {
        func_180482_a = super.func_180482_a(difficultyInstance, func_180482_a);
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
            this.tasks.addTask(4, this.aiAttackOnCollide);
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
        }
        else {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.func_180481_a(difficultyInstance);
            this.func_180483_b(difficultyInstance);
        }
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * difficultyInstance.func_180170_c());
        if (this.getEquipmentInSlot(4) == null) {
            final Calendar currentDate = this.worldObj.getCurrentDate();
            if (currentDate.get(2) + 1 == 10 && currentDate.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
        return func_180482_a;
    }
    
    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        final ItemStack heldItem = this.getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.bow) {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        final EntityArrow entityArrow = new EntityArrow(this.worldObj, this, entityLivingBase, 1.6f, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
        final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityArrow.setDamage(n * 2.0f + this.rand.nextGaussian() * 0.25 + this.worldObj.getDifficulty().getDifficultyId() * 0.11f);
        if (enchantmentLevel > 0) {
            entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
        }
        if (enchantmentLevel2 > 0) {
            entityArrow.setKnockbackStrength(enchantmentLevel2);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1) {
            entityArrow.setFire(100);
        }
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(entityArrow);
    }
    
    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }
    
    public void setSkeletonType(final int n) {
        this.dataWatcher.updateObject(13, (byte)n);
        this.isImmuneToFire = (n == 1);
        if (n == 1) {
            this.setSize(0.72f, 2.535f);
        }
        else {
            this.setSize(0.6f, 1.95f);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("SkeletonType", 99)) {
            this.setSkeletonType(nbtTagCompound.getByte("SkeletonType"));
        }
        this.setCombatTask();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        super.setCurrentItemOrArmor(n, itemStack);
        if (!this.worldObj.isRemote && n == 0) {
            this.setCombatTask();
        }
    }
    
    @Override
    public float getEyeHeight() {
        return (this.getSkeletonType() == 1) ? super.getEyeHeight() : 1.74f;
    }
    
    @Override
    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }
    
    static {
        __OBFID = "CL_00001697";
    }
}
