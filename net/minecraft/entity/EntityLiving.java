package net.minecraft.entity;

import net.minecraft.entity.ai.*;
import net.minecraft.world.biome.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import java.util.*;
import optifine.*;
import net.minecraft.entity.monster.*;

public abstract class EntityLiving extends EntityLivingBase
{
    public int livingSoundTime;
    protected int experienceValue;
    private EntityLookHelper lookHelper;
    protected EntityMoveHelper moveHelper;
    protected EntityJumpHelper jumpHelper;
    private EntityBodyHelper bodyHelper;
    protected PathNavigate navigator;
    protected final EntityAITasks tasks;
    protected final EntityAITasks targetTasks;
    private EntityLivingBase attackTarget;
    private EntitySenses senses;
    private ItemStack[] equipment;
    protected float[] equipmentDropChances;
    private boolean canPickUpLoot;
    private boolean persistenceRequired;
    private boolean isLeashed;
    private Entity leashedToEntity;
    private NBTTagCompound leashNBTTag;
    private static final String __OBFID;
    public int randomMobsId;
    public BiomeGenBase spawnBiome;
    public BlockPos spawnPosition;
    
    public EntityLiving(final World world) {
        super(world);
        this.equipment = new ItemStack[5];
        this.equipmentDropChances = new float[5];
        this.randomMobsId = 0;
        this.spawnBiome = null;
        this.spawnPosition = null;
        this.tasks = new EntityAITasks((world != null && world.theProfiler != null) ? world.theProfiler : null);
        this.targetTasks = new EntityAITasks((world != null && world.theProfiler != null) ? world.theProfiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = this.func_175447_b(world);
        this.senses = new EntitySenses(this);
        while (0 < this.equipmentDropChances.length) {
            this.equipmentDropChances[0] = 0.085f;
            int n = 0;
            ++n;
        }
        this.randomMobsId = (int)(this.getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }
    
    protected PathNavigate func_175447_b(final World world) {
        return new PathNavigateGround(this, world);
    }
    
    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }
    
    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }
    
    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }
    
    public PathNavigate getNavigator() {
        return this.navigator;
    }
    
    public EntitySenses getEntitySenses() {
        return this.senses;
    }
    
    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }
    
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        this.attackTarget = attackTarget;
        Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, attackTarget);
    }
    
    public boolean canAttackClass(final Class clazz) {
        return clazz != EntityGhast.class;
    }
    
    public void eatGrassBonus() {
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(15, 0);
    }
    
    public int getTalkInterval() {
        return 80;
    }
    
    public void playLivingSound() {
        final String livingSound = this.getLivingSound();
        if (livingSound != null) {
            this.playSound(livingSound, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.experienceValue > 0) {
            int experienceValue = this.experienceValue;
            final ItemStack[] inventory = this.getInventory();
            while (0 < inventory.length) {
                if (inventory[0] != null && this.equipmentDropChances[0] <= 1.0f) {
                    experienceValue += 1 + this.rand.nextInt(3);
                }
                int n = 0;
                ++n;
            }
            return experienceValue;
        }
        return this.experienceValue;
    }
    
    public void spawnExplosionParticle() {
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)20);
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 20) {
            this.spawnExplosionParticle();
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public void onUpdate() {
        if (Config.isSmoothWorld() && this != 0) {
            this.onUpdateMinimal();
        }
        else {
            super.onUpdate();
            if (!this.worldObj.isRemote) {
                this.updateLeashedState();
            }
        }
    }
    
    @Override
    protected float func_110146_f(final float n, final float n2) {
        this.bodyHelper.updateRenderAngles();
        return n2;
    }
    
    protected String getLivingSound() {
        return null;
    }
    
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            int nextInt = this.rand.nextInt(3);
            if (n > 0) {
                nextInt += this.rand.nextInt(n + 1);
            }
            while (0 < nextInt) {
                this.dropItem(dropItem, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        nbtTagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
        final NBTTagList list = new NBTTagList();
        while (0 < this.equipment.length) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            if (this.equipment[0] != null) {
                this.equipment[0].writeToNBT(nbtTagCompound2);
            }
            list.appendTag(nbtTagCompound2);
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Equipment", list);
        final NBTTagList list2 = new NBTTagList();
        while (0 < this.equipmentDropChances.length) {
            list2.appendTag(new NBTTagFloat(this.equipmentDropChances[0]));
            int n2 = 0;
            ++n2;
        }
        nbtTagCompound.setTag("DropChances", list2);
        nbtTagCompound.setBoolean("Leashed", this.isLeashed);
        if (this.leashedToEntity != null) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            if (this.leashedToEntity instanceof EntityLivingBase) {
                nbtTagCompound3.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
                nbtTagCompound3.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
            }
            else if (this.leashedToEntity instanceof EntityHanging) {
                final BlockPos func_174857_n = ((EntityHanging)this.leashedToEntity).func_174857_n();
                nbtTagCompound3.setInteger("X", func_174857_n.getX());
                nbtTagCompound3.setInteger("Y", func_174857_n.getY());
                nbtTagCompound3.setInteger("Z", func_174857_n.getZ());
            }
            nbtTagCompound.setTag("Leash", nbtTagCompound3);
        }
        if (this != 0) {
            nbtTagCompound.setBoolean("NoAI", this.isAIDisabled());
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("CanPickUpLoot", 1)) {
            this.setCanPickUpLoot(nbtTagCompound.getBoolean("CanPickUpLoot"));
        }
        this.persistenceRequired = nbtTagCompound.getBoolean("PersistenceRequired");
        int n = 0;
        if (nbtTagCompound.hasKey("Equipment", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("Equipment", 10);
            while (0 < this.equipment.length) {
                this.equipment[0] = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
                ++n;
            }
        }
        if (nbtTagCompound.hasKey("DropChances", 9)) {
            final NBTTagList tagList2 = nbtTagCompound.getTagList("DropChances", 5);
            while (0 < tagList2.tagCount()) {
                this.equipmentDropChances[0] = tagList2.getFloat(0);
                ++n;
            }
        }
        this.isLeashed = nbtTagCompound.getBoolean("Leashed");
        if (this.isLeashed && nbtTagCompound.hasKey("Leash", 10)) {
            this.leashNBTTag = nbtTagCompound.getCompoundTag("Leash");
        }
        this.setNoAI(nbtTagCompound.getBoolean("NoAI"));
    }
    
    public void setMoveForward(final float moveForward) {
        this.moveForward = moveForward;
    }
    
    @Override
    public void setAIMoveSpeed(final float n) {
        super.setAIMoveSpeed(n);
        this.setMoveForward(n);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.worldObj.theProfiler.startSection("looting");
        if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            for (final EntityItem entityItem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(1.0, 0.0, 1.0))) {
                if (!entityItem.isDead && entityItem.getEntityItem() != null && !entityItem.func_174874_s()) {
                    this.func_175445_a(entityItem);
                }
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void func_175445_a(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        final int armorPosition = getArmorPosition(entityItem2);
        if (armorPosition > -1) {
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(armorPosition);
            if (equipmentInSlot != null) {
                if (armorPosition == 0) {
                    if (!(entityItem2.getItem() instanceof ItemSword) || equipmentInSlot.getItem() instanceof ItemSword) {
                        if (entityItem2.getItem() instanceof ItemSword && equipmentInSlot.getItem() instanceof ItemSword) {
                            final ItemSword itemSword = (ItemSword)entityItem2.getItem();
                            final ItemSword itemSword2 = (ItemSword)equipmentInSlot.getItem();
                            if (itemSword.func_150931_i() == itemSword2.func_150931_i()) {
                                final boolean b = entityItem2.getMetadata() > equipmentInSlot.getMetadata() || (entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound());
                            }
                            else {
                                final boolean b2 = itemSword.func_150931_i() > itemSword2.func_150931_i();
                            }
                        }
                        else if (entityItem2.getItem() instanceof ItemBow && equipmentInSlot.getItem() instanceof ItemBow) {
                            final boolean b3 = entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound();
                        }
                    }
                }
                else if (!(entityItem2.getItem() instanceof ItemArmor) || equipmentInSlot.getItem() instanceof ItemArmor) {
                    if (entityItem2.getItem() instanceof ItemArmor && equipmentInSlot.getItem() instanceof ItemArmor) {
                        final ItemArmor itemArmor = (ItemArmor)entityItem2.getItem();
                        final ItemArmor itemArmor2 = (ItemArmor)equipmentInSlot.getItem();
                        if (itemArmor.damageReduceAmount == itemArmor2.damageReduceAmount) {
                            final boolean b4 = entityItem2.getMetadata() > equipmentInSlot.getMetadata() || (entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound());
                        }
                        else {
                            final boolean b5 = itemArmor.damageReduceAmount > itemArmor2.damageReduceAmount;
                        }
                    }
                }
            }
        }
    }
    
    protected boolean func_175448_a(final ItemStack itemStack) {
        return true;
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    protected void despawnEntity() {
        final Object fieldValue = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
        final Object fieldValue2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
        if (this.persistenceRequired) {
            this.entityAge = 0;
        }
        else {
            final Object call;
            if ((this.entityAge & 0x1F) == 0x1F && (call = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, this)) != fieldValue) {
                if (call == fieldValue2) {
                    this.entityAge = 0;
                }
                else {
                    this.setDead();
                }
            }
            else {
                final EntityPlayer closestPlayerToEntity = this.worldObj.getClosestPlayerToEntity(this, -1.0);
                if (closestPlayerToEntity != null) {
                    final double n = closestPlayerToEntity.posX - this.posX;
                    final double n2 = closestPlayerToEntity.posY - this.posY;
                    final double n3 = closestPlayerToEntity.posZ - this.posZ;
                    final double n4 = n * n + n2 * n2 + n3 * n3;
                    if (this.canDespawn() && n4 > 16384.0) {
                        this.setDead();
                    }
                    if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && n4 > 1024.0 && this.canDespawn()) {
                        this.setDead();
                    }
                    else if (n4 < 1024.0) {
                        this.entityAge = 0;
                    }
                }
            }
        }
    }
    
    @Override
    protected final void updateEntityActionState() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }
    
    protected void updateAITasks() {
    }
    
    public int getVerticalFaceSpeed() {
        return 40;
    }
    
    public void faceEntity(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - this.posX;
        final double n4 = entity.posZ - this.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (this.posY + this.getEyeHeight());
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.posY + this.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        this.rotationPitch = this.updateRotation(this.rotationPitch, (float)(-(Math.atan2(n5, n6) * 180.0 / 3.141592653589793)), n2);
        this.rotationYaw = this.updateRotation(this.rotationYaw, n7, n);
    }
    
    private float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public boolean getCanSpawnHere() {
        return true;
    }
    
    public boolean handleLavaMovement() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    public float getRenderSizeModifier() {
        return 1.0f;
    }
    
    public int getMaxSpawnedInChunk() {
        return 4;
    }
    
    @Override
    public int getMaxFallHeight() {
        if (this.getAttackTarget() == null) {
            return 3;
        }
        final int n = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        final int n2 = 0 - (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
        return 3;
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.equipment[0];
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return this.equipment[n];
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.equipment[n + 1];
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.equipment[n] = itemStack;
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.equipment;
    }
    
    @Override
    protected void dropEquipment(final boolean b, final int n) {
        while (0 < this.getInventory().length) {
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(0);
            final boolean b2 = this.equipmentDropChances[0] > 1.0f;
            if (equipmentInSlot != null && (b || b2) && this.rand.nextFloat() - n * 0.01f < this.equipmentDropChances[0]) {
                if (!b2 && equipmentInSlot.isItemStackDamageable()) {
                    final int max = Math.max(equipmentInSlot.getMaxDamage() - 25, 1);
                    final int n2 = equipmentInSlot.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(max) + 1);
                    if (1 > max) {}
                    equipmentInSlot.setItemDamage(1);
                }
                this.entityDropItem(equipmentInSlot, 0.0f);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    protected void func_180481_a(final DifficultyInstance difficultyInstance) {
        if (this.rand.nextFloat() < 0.15f * difficultyInstance.func_180170_c()) {
            int nextInt = this.rand.nextInt(2);
            final float n = (this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.1f : 0.25f;
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
        }
    }
    
    public static int getArmorPosition(final ItemStack itemStack) {
        if (itemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && itemStack.getItem() != Items.skull) {
            if (itemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)itemStack.getItem()).armorType) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public static Item getArmorItemForSlot(final int n, final int n2) {
        switch (n) {
            case 4: {
                if (n2 == 0) {
                    return Items.leather_helmet;
                }
                if (n2 == 1) {
                    return Items.golden_helmet;
                }
                if (n2 == 2) {
                    return Items.chainmail_helmet;
                }
                if (n2 == 3) {
                    return Items.iron_helmet;
                }
                if (n2 == 4) {
                    return Items.diamond_helmet;
                }
            }
            case 3: {
                if (n2 == 0) {
                    return Items.leather_chestplate;
                }
                if (n2 == 1) {
                    return Items.golden_chestplate;
                }
                if (n2 == 2) {
                    return Items.chainmail_chestplate;
                }
                if (n2 == 3) {
                    return Items.iron_chestplate;
                }
                if (n2 == 4) {
                    return Items.diamond_chestplate;
                }
            }
            case 2: {
                if (n2 == 0) {
                    return Items.leather_leggings;
                }
                if (n2 == 1) {
                    return Items.golden_leggings;
                }
                if (n2 == 2) {
                    return Items.chainmail_leggings;
                }
                if (n2 == 3) {
                    return Items.iron_leggings;
                }
                if (n2 == 4) {
                    return Items.diamond_leggings;
                }
            }
            case 1: {
                if (n2 == 0) {
                    return Items.leather_boots;
                }
                if (n2 == 1) {
                    return Items.golden_boots;
                }
                if (n2 == 2) {
                    return Items.chainmail_boots;
                }
                if (n2 == 3) {
                    return Items.iron_boots;
                }
                if (n2 == 4) {
                    return Items.diamond_boots;
                }
                break;
            }
        }
        return null;
    }
    
    protected void func_180483_b(final DifficultyInstance difficultyInstance) {
        final float func_180170_c = difficultyInstance.func_180170_c();
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25f * func_180170_c) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0f + func_180170_c * this.rand.nextInt(18)));
        }
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
        return entityLivingData;
    }
    
    public boolean canBeSteered() {
        return false;
    }
    
    public void enablePersistence() {
        this.persistenceRequired = true;
    }
    
    public void setEquipmentDropChance(final int n, final float n2) {
        this.equipmentDropChances[n] = n2;
    }
    
    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }
    
    public void setCanPickUpLoot(final boolean canPickUpLoot) {
        this.canPickUpLoot = canPickUpLoot;
    }
    
    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }
    
    @Override
    public final boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.getLeashed() && this.getLeashedToEntity() == entityPlayer) {
            this.clearLeashed(true, !entityPlayer.capabilities.isCreativeMode);
            return true;
        }
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.lead && this == 0) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
                this.setLeashedToEntity(entityPlayer, true);
                final ItemStack itemStack = currentItem;
                --itemStack.stackSize;
                return true;
            }
            if (((EntityTameable)this).func_152114_e(entityPlayer)) {
                this.setLeashedToEntity(entityPlayer, true);
                final ItemStack itemStack2 = currentItem;
                --itemStack2.stackSize;
                return true;
            }
        }
        return this.interact(entityPlayer) || super.interactFirst(entityPlayer);
    }
    
    protected boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    protected void updateLeashedState() {
        if (this.leashNBTTag != null) {
            this.recreateLeash();
        }
        if (this.isLeashed) {
            if (!this.isEntityAlive()) {
                this.clearLeashed(true, true);
            }
            if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
                this.clearLeashed(true, true);
            }
        }
    }
    
    public void clearLeashed(final boolean b, final boolean b2) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;
            if (!this.worldObj.isRemote && b2) {
                this.dropItem(Items.lead, 1);
            }
            if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, null));
            }
        }
    }
    
    public boolean getLeashed() {
        return this.isLeashed;
    }
    
    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }
    
    public void setLeashedToEntity(final Entity leashedToEntity, final boolean b) {
        this.isLeashed = true;
        this.leashedToEntity = leashedToEntity;
        if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }
    
    private void recreateLeash() {
        if (this.isLeashed && this.leashNBTTag != null) {
            if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
                final UUID uuid = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
                for (final EntityLivingBase leashedToEntity : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0, 10.0, 10.0))) {
                    if (leashedToEntity.getUniqueID().equals(uuid)) {
                        this.leashedToEntity = leashedToEntity;
                        break;
                    }
                }
            }
            else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
                final BlockPos blockPos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
                EntityLeashKnot leashedToEntity2 = EntityLeashKnot.func_174863_b(this.worldObj, blockPos);
                if (leashedToEntity2 == null) {
                    leashedToEntity2 = EntityLeashKnot.func_174862_a(this.worldObj, blockPos);
                }
                this.leashedToEntity = leashedToEntity2;
            }
            else {
                this.clearLeashed(false, true);
            }
        }
        this.leashNBTTag = null;
    }
    
    @Override
    public boolean func_174820_d(final int n, final ItemStack itemStack) {
        if (n != 99) {
            if (0 >= this.equipment.length) {
                return false;
            }
        }
        if (itemStack != null && getArmorPosition(itemStack) != 0) {
            return false;
        }
        this.setCurrentItemOrArmor(0, itemStack);
        return true;
    }
    
    @Override
    public boolean isServerWorld() {
        return super.isServerWorld() && this != 0;
    }
    
    protected void setNoAI(final boolean b) {
        this.dataWatcher.updateObject(15, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return false;
        }
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        return false;
    }
    
    private void onUpdateMinimal() {
        ++this.entityAge;
        if (this instanceof EntityMob && this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += 2;
        }
        this.despawnEntity();
    }
    
    static {
        __OBFID = "CL_00001550";
    }
    
    public enum SpawnPlacementType
    {
        ON_GROUND("ON_GROUND", 0, "ON_GROUND", 0, "ON_GROUND", 0), 
        IN_AIR("IN_AIR", 1, "IN_AIR", 1, "IN_AIR", 1), 
        IN_WATER("IN_WATER", 2, "IN_WATER", 2, "IN_WATER", 2);
        
        private static final SpawnPlacementType[] $VALUES;
        private static final String __OBFID;
        private static final SpawnPlacementType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002255";
            ENUM$VALUES = new SpawnPlacementType[] { SpawnPlacementType.ON_GROUND, SpawnPlacementType.IN_AIR, SpawnPlacementType.IN_WATER };
            $VALUES = new SpawnPlacementType[] { SpawnPlacementType.ON_GROUND, SpawnPlacementType.IN_AIR, SpawnPlacementType.IN_WATER };
        }
        
        private SpawnPlacementType(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
    }
}
