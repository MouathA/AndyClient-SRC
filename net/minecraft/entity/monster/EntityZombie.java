package net.minecraft.entity.monster;

import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.potion.*;

public class EntityZombie extends EntityMob
{
    protected static final IAttribute field_110186_bp;
    private static final UUID babySpeedBoostUUID;
    private static final AttributeModifier babySpeedBoostModifier;
    private final EntityAIBreakDoor field_146075_bs;
    private int conversionTime;
    private boolean field_146076_bu;
    private float field_146074_bv;
    private float field_146073_bw;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001702";
        field_110186_bp = new RangedAttribute(null, "zombie.spawnReinforcements", 0.0, 0.0, 1.0).setDescription("Spawn Reinforcements Chance");
        babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        babySpeedBoostModifier = new AttributeModifier(EntityZombie.babySpeedBoostUUID, "Baby speed boost", 0.5, 1);
    }
    
    public EntityZombie(final World world) {
        super(world);
        this.field_146075_bs = new EntityAIBreakDoor(this);
        this.field_146076_bu = false;
        this.field_146074_bv = -1.0f;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.func_175456_n();
        this.setSize(0.6f, 1.95f);
    }
    
    protected void func_175456_n() {
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, true));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap().registerAttribute(EntityZombie.field_110186_bp).setBaseValue(this.rand.nextDouble() * 0.10000000149011612);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, 0);
        this.getDataWatcher().addObject(13, 0);
        this.getDataWatcher().addObject(14, 0);
    }
    
    @Override
    public int getTotalArmorValue() {
        final int n = super.getTotalArmorValue() + 2;
        if (20 > 20) {}
        return 20;
    }
    
    public boolean func_146072_bX() {
        return this.field_146076_bu;
    }
    
    public void func_146070_a(final boolean field_146076_bu) {
        if (this.field_146076_bu != field_146076_bu) {
            this.field_146076_bu = field_146076_bu;
            if (field_146076_bu) {
                this.tasks.addTask(1, this.field_146075_bs);
            }
            else {
                this.tasks.removeTask(this.field_146075_bs);
            }
        }
    }
    
    @Override
    public boolean isChild() {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(entityPlayer);
    }
    
    public void setChild(final boolean b) {
        this.getDataWatcher().updateObject(12, (byte)(byte)(b ? 1 : 0));
        if (this.worldObj != null && !this.worldObj.isRemote) {
            final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            entityAttribute.removeModifier(EntityZombie.babySpeedBoostModifier);
            if (b) {
                entityAttribute.applyModifier(EntityZombie.babySpeedBoostModifier);
            }
        }
        this.func_146071_k(b);
    }
    
    public boolean isVillager() {
        return this.getDataWatcher().getWatchableObjectByte(13) == 1;
    }
    
    public void setVillager(final boolean b) {
        this.getDataWatcher().updateObject(13, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
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
        if (this.isRiding() && this.getAttackTarget() != null && this.ridingEntity instanceof EntityChicken) {
            ((EntityLiving)this.ridingEntity).getNavigator().setPath(this.getNavigator().getPath(), 1.5);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (super.attackEntityFrom(damageSource, n)) {
            EntityLivingBase attackTarget = this.getAttackTarget();
            if (attackTarget == null && damageSource.getEntity() instanceof EntityLivingBase) {
                attackTarget = (EntityLivingBase)damageSource.getEntity();
            }
            if (attackTarget != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < this.getEntityAttribute(EntityZombie.field_110186_bp).getAttributeValue()) {
                final int floor_double = MathHelper.floor_double(this.posX);
                final int floor_double2 = MathHelper.floor_double(this.posY);
                final int floor_double3 = MathHelper.floor_double(this.posZ);
                final EntityZombie entityZombie = new EntityZombie(this.worldObj);
                while (0 < 50) {
                    final int n2 = floor_double + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    final int n3 = floor_double2 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    final int n4 = floor_double3 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, n3 - 1, n4)) && this.worldObj.getLightFromNeighbors(new BlockPos(n2, n3, n4)) < 10) {
                        entityZombie.setPosition(n2, n3, n4);
                        if (!this.worldObj.func_175636_b(n2, n3, n4, 7.0) && this.worldObj.checkNoEntityCollision(entityZombie.getEntityBoundingBox(), (Entity)entityZombie) && this.worldObj.getCollidingBoundingBoxes(entityZombie, entityZombie.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(entityZombie.getEntityBoundingBox())) {
                            this.worldObj.spawnEntityInWorld(entityZombie);
                            entityZombie.setAttackTarget(attackTarget);
                            entityZombie.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
                            this.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806, 0));
                            entityZombie.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806, 0));
                            break;
                        }
                    }
                    int n5 = 0;
                    ++n5;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.isConverting()) {
            this.conversionTime -= this.getConversionTimeBoost();
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntityAsMob = super.attackEntityAsMob(entity);
        if (attackEntityAsMob) {
            final int difficultyId = this.worldObj.getDifficulty().getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < difficultyId * 0.3f) {
                entity.setFire(2 * difficultyId);
            }
        }
        return attackEntityAsMob;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.zombie.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.rotten_flesh;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected void addRandomArmor() {
        switch (this.rand.nextInt(3)) {
            case 0: {
                this.dropItem(Items.iron_ingot, 1);
                break;
            }
            case 1: {
                this.dropItem(Items.carrot, 1);
                break;
            }
            case 2: {
                this.dropItem(Items.potato, 1);
                break;
            }
        }
    }
    
    @Override
    protected void func_180481_a(final DifficultyInstance difficultyInstance) {
        super.func_180481_a(difficultyInstance);
        if (this.rand.nextFloat() < ((this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.05f : 0.01f)) {
            if (this.rand.nextInt(3) == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
            }
            else {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.isChild()) {
            nbtTagCompound.setBoolean("IsBaby", true);
        }
        if (this.isVillager()) {
            nbtTagCompound.setBoolean("IsVillager", true);
        }
        nbtTagCompound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        nbtTagCompound.setBoolean("CanBreakDoors", this.func_146072_bX());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        if (nbtTagCompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (nbtTagCompound.hasKey("ConversionTime", 99) && nbtTagCompound.getInteger("ConversionTime") > -1) {
            this.startConversion(nbtTagCompound.getInteger("ConversionTime"));
        }
        this.func_146070_a(nbtTagCompound.getBoolean("CanBreakDoors"));
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        super.onKillEntity(entityLivingBase);
        if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && entityLivingBase instanceof EntityVillager) {
            if (this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            final EntityZombie entityZombie = new EntityZombie(this.worldObj);
            entityZombie.copyLocationAndAnglesFrom(entityLivingBase);
            this.worldObj.removeEntity(entityLivingBase);
            entityZombie.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
            entityZombie.setVillager(true);
            if (entityLivingBase.isChild()) {
                entityZombie.setChild(true);
            }
            this.worldObj.spawnEntityInWorld(entityZombie);
            this.worldObj.playAuxSFXAtEntity(null, 1016, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
        }
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.74f;
        if (this.isChild()) {
            n -= (float)0.81;
        }
        return n;
    }
    
    @Override
    protected boolean func_175448_a(final ItemStack itemStack) {
        return (itemStack.getItem() != Items.egg || !this.isChild() || !this.isRiding()) && super.func_175448_a(itemStack);
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        IEntityLivingData func_180482_a = super.func_180482_a(difficultyInstance, entityLivingData);
        final float func_180170_c = difficultyInstance.func_180170_c();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * func_180170_c);
        if (func_180482_a == null) {
            func_180482_a = new GroupData(this.worldObj.rand.nextFloat() < 0.05f, this.worldObj.rand.nextFloat() < 0.05f, null);
        }
        if (func_180482_a instanceof GroupData) {
            final GroupData groupData = (GroupData)func_180482_a;
            if (groupData.field_142046_b) {
                this.setVillager(true);
            }
            if (groupData.field_142048_a) {
                this.setChild(true);
                if (this.worldObj.rand.nextFloat() < 0.05) {
                    final List func_175647_a = this.worldObj.func_175647_a(EntityChicken.class, this.getEntityBoundingBox().expand(5.0, 3.0, 5.0), IEntitySelector.field_152785_b);
                    if (!func_175647_a.isEmpty()) {
                        final EntityChicken entityChicken = func_175647_a.get(0);
                        entityChicken.func_152117_i(true);
                        this.mountEntity(entityChicken);
                    }
                }
                else if (this.worldObj.rand.nextFloat() < 0.05) {
                    final EntityChicken entityChicken2 = new EntityChicken(this.worldObj);
                    entityChicken2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    entityChicken2.func_180482_a(difficultyInstance, null);
                    entityChicken2.func_152117_i(true);
                    this.worldObj.spawnEntityInWorld(entityChicken2);
                    this.mountEntity(entityChicken2);
                }
            }
        }
        this.func_146070_a(this.rand.nextFloat() < func_180170_c * 0.1f);
        this.func_180481_a(difficultyInstance);
        this.func_180483_b(difficultyInstance);
        if (this.getEquipmentInSlot(4) == null) {
            final Calendar currentDate = this.worldObj.getCurrentDate();
            if (currentDate.get(2) + 1 == 10 && currentDate.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806, 0));
        final double n = this.rand.nextDouble() * 1.5 * func_180170_c;
        if (n > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", n, 2));
        }
        if (this.rand.nextFloat() < func_180170_c * 0.05f) {
            this.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
            this.func_146070_a(true);
        }
        return func_180482_a;
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
        if (currentEquippedItem != null && currentEquippedItem.getItem() == Items.golden_apple && currentEquippedItem.getMetadata() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentEquippedItem;
                --itemStack.stackSize;
            }
            if (currentEquippedItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                this.startConversion(this.rand.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }
    
    protected void startConversion(final int conversionTime) {
        this.conversionTime = conversionTime;
        this.getDataWatcher().updateObject(14, 1);
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, conversionTime, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
        this.worldObj.setEntityState(this, (byte)16);
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            if (!this.isSlient()) {
                this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.zombie.remedy", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isConverting();
    }
    
    public boolean isConverting() {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }
    
    protected void convertToVillager() {
        final EntityVillager entityVillager = new EntityVillager(this.worldObj);
        entityVillager.copyLocationAndAnglesFrom(this);
        entityVillager.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityVillager)), null);
        entityVillager.setLookingForHome();
        if (this.isChild()) {
            entityVillager.setGrowingAge(-24000);
        }
        this.worldObj.removeEntity(this);
        this.worldObj.spawnEntityInWorld(entityVillager);
        entityVillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
        this.worldObj.playAuxSFXAtEntity(null, 1017, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    }
    
    protected int getConversionTimeBoost() {
        if (this.rand.nextFloat() < 0.01f) {
            for (int n = (int)this.posX - 4; n < (int)this.posX + 4 && 0 < 14; ++n) {
                for (int n2 = (int)this.posY - 4; n2 < (int)this.posY + 4 && 0 < 14; ++n2) {
                    for (int n3 = (int)this.posZ - 4; n3 < (int)this.posZ + 4 && 0 < 14; ++n3) {
                        final Block block = this.worldObj.getBlockState(new BlockPos(n, n2, n3)).getBlock();
                        if (block == Blocks.iron_bars || block == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3f) {
                                int n4 = 0;
                                ++n4;
                            }
                            int n5 = 0;
                            ++n5;
                        }
                    }
                }
            }
        }
        return 1;
    }
    
    public void func_146071_k(final boolean b) {
        this.func_146069_a(b ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void setSize(final float field_146074_bv, final float field_146073_bw) {
        final boolean b = this.field_146074_bv > 0.0f && this.field_146073_bw > 0.0f;
        this.field_146074_bv = field_146074_bv;
        this.field_146073_bw = field_146073_bw;
        if (!b) {
            this.func_146069_a(1.0f);
        }
    }
    
    protected final void func_146069_a(final float n) {
        super.setSize(this.field_146074_bv * n, this.field_146073_bw * n);
    }
    
    @Override
    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0f);
        }
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean field_142048_a;
        public boolean field_142046_b;
        private static final String __OBFID;
        final EntityZombie this$0;
        
        private GroupData(final EntityZombie this$0, final boolean field_142048_a, final boolean field_142046_b) {
            this.this$0 = this$0;
            this.field_142048_a = false;
            this.field_142046_b = false;
            this.field_142048_a = field_142048_a;
            this.field_142046_b = field_142046_b;
        }
        
        GroupData(final EntityZombie entityZombie, final boolean b, final boolean b2, final Object o) {
            this(entityZombie, b, b2);
        }
        
        static {
            __OBFID = "CL_00001704";
        }
    }
}
