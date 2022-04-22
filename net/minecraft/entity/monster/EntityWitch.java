package net.minecraft.entity.monster;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final UUID field_110184_bp;
    private static final AttributeModifier field_110185_bq;
    private static final Item[] witchDrops;
    private int witchAttackTimer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001701";
        field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        field_110185_bq = new AttributeModifier(EntityWitch.field_110184_bp, "Drinking speed penalty", -0.25, 0).setSaved(false);
        witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
    }
    
    public EntityWitch(final World world) {
        super(world);
        this.setSize(0.6f, 1.95f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, 0);
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    public void setAggressive(final boolean b) {
        this.getDataWatcher().updateObject(21, (byte)(byte)(b ? 1 : 0));
    }
    
    public boolean getAggressive() {
        return this.getDataWatcher().getWatchableObjectByte(21) == 1;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.getAggressive()) {
                if (this.witchAttackTimer-- <= 0) {
                    this.setAggressive(false);
                    final ItemStack heldItem = this.getHeldItem();
                    this.setCurrentItemOrArmor(0, null);
                    if (heldItem != null && heldItem.getItem() == Items.potionitem) {
                        final List effects = Items.potionitem.getEffects(heldItem);
                        if (effects != null) {
                            final Iterator<PotionEffect> iterator = effects.iterator();
                            while (iterator.hasNext()) {
                                this.addPotionEffect(new PotionEffect(iterator.next()));
                            }
                        }
                    }
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityWitch.field_110185_bq);
                }
            }
            else {
                if (this.rand.nextFloat() >= 0.15f || !this.isInsideOfMaterial(Material.water) || this.isPotionActive(Potion.waterBreathing)) {
                    if (this.rand.nextFloat() >= 0.15f || !this.isBurning() || this.isPotionActive(Potion.fireResistance)) {
                        if (this.rand.nextFloat() >= 0.05f || this.getHealth() >= this.getMaxHealth()) {
                            if (this.rand.nextFloat() >= 0.25f || this.getAttackTarget() == null || this.isPotionActive(Potion.moveSpeed) || this.getAttackTarget().getDistanceSqToEntity(this) <= 121.0) {
                                if (this.rand.nextFloat() >= 0.25f || this.getAttackTarget() == null || this.isPotionActive(Potion.moveSpeed) || this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {}
                            }
                        }
                    }
                }
                if (16274 > -1) {
                    this.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, 16274));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.setAggressive(true);
                    final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                    entityAttribute.removeModifier(EntityWitch.field_110185_bq);
                    entityAttribute.applyModifier(EntityWitch.field_110185_bq);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.worldObj.setEntityState(this, (byte)15);
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            while (0 < this.rand.nextInt(35) + 10) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0, new int[0]);
                int n = 0;
                ++n;
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float applyPotionDamageCalculations) {
        applyPotionDamageCalculations = super.applyPotionDamageCalculations(damageSource, applyPotionDamageCalculations);
        if (damageSource.getEntity() == this) {
            applyPotionDamageCalculations = 0.0f;
        }
        if (damageSource.isMagicDamage()) {
            applyPotionDamageCalculations *= (float)0.15;
        }
        return applyPotionDamageCalculations;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3) + 1) {
            int nextInt = this.rand.nextInt(3);
            final Item item = EntityWitch.witchDrops[this.rand.nextInt(EntityWitch.witchDrops.length)];
            if (n > 0) {
                nextInt += this.rand.nextInt(n + 1);
            }
            while (0 < nextInt) {
                this.dropItem(item, 1);
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        if (!this.getAggressive()) {
            final EntityPotion entityPotion = new EntityPotion(this.worldObj, this, 32732);
            final double n2 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.100000023841858;
            final EntityPotion entityPotion2 = entityPotion;
            entityPotion2.rotationPitch += 20.0f;
            final double n3 = entityLivingBase.posX + entityLivingBase.motionX - this.posX;
            final double n4 = n2 - this.posY;
            final double n5 = entityLivingBase.posZ + entityLivingBase.motionZ - this.posZ;
            final float sqrt_double = MathHelper.sqrt_double(n3 * n3 + n5 * n5);
            if (sqrt_double >= 8.0f && !entityLivingBase.isPotionActive(Potion.moveSlowdown)) {
                entityPotion.setPotionDamage(32698);
            }
            else if (entityLivingBase.getHealth() >= 8.0f && !entityLivingBase.isPotionActive(Potion.poison)) {
                entityPotion.setPotionDamage(32660);
            }
            else if (sqrt_double <= 3.0f && !entityLivingBase.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25f) {
                entityPotion.setPotionDamage(32696);
            }
            entityPotion.setThrowableHeading(n3, n4 + sqrt_double * 0.2f, n5, 0.75f, 8.0f);
            this.worldObj.spawnEntityInWorld(entityPotion);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 1.62f;
    }
}
