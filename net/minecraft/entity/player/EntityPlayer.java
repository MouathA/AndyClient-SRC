package net.minecraft.entity.player;

import com.mojang.authlib.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.tileentity.*;
import net.minecraft.command.server.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.passive.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.stats.*;
import net.minecraft.entity.boss.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.*;
import java.nio.charset.*;

public abstract class EntityPlayer extends EntityLivingBase
{
    public InventoryPlayer inventory;
    private InventoryEnderChest theInventoryEnderChest;
    public Container inventoryContainer;
    public Container openContainer;
    protected FoodStats foodStats;
    protected int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;
    public int xpCooldown;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
    protected boolean sleeping;
    public BlockPos playerLocation;
    private int sleepTimer;
    public float field_71079_bU;
    public float field_71082_cx;
    public float field_71089_bV;
    private BlockPos spawnChunk;
    private boolean spawnForced;
    private BlockPos startMinecartRidingCoordinate;
    public PlayerCapabilities capabilities;
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    private int field_175152_f;
    private ItemStack itemInUse;
    private int itemInUseCount;
    protected float speedOnGround;
    protected float speedInAir;
    private int field_82249_h;
    private final GameProfile gameProfile;
    private boolean field_175153_bG;
    public EntityFishHook fishEntity;
    private static final String __OBFID;
    
    public EntityPlayer(final World world, final GameProfile gameProfile) {
        super(world);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.field_175153_bG = false;
        this.entityUniqueID = getUUID(gameProfile);
        this.gameProfile = gameProfile;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !world.isRemote, this);
        this.openContainer = this.inventoryContainer;
        final BlockPos spawnPoint = world.getSpawnPoint();
        this.setLocationAndAngles(spawnPoint.getX() + 0.5, spawnPoint.getY() + 1, spawnPoint.getZ() + 0.5, 0.0f, 0.0f);
        this.field_70741_aB = 180.0f;
        this.fireResistance = 20;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0.0f);
        this.dataWatcher.addObject(18, 0);
        this.dataWatcher.addObject(10, 0);
    }
    
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }
    
    public int getItemInUseCount() {
        return this.itemInUseCount;
    }
    
    public boolean isUsingItem() {
        return this.itemInUse != null;
    }
    
    public int getItemInUseDuration() {
        return this.isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
    }
    
    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }
    
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.worldObj.isRemote) {
            this.setEating(false);
        }
    }
    
    public boolean isBlocking() {
        return this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK;
    }
    
    @Override
    public void onUpdate() {
        this.noClip = this.func_175149_v();
        if (this.func_175149_v()) {
            this.onGround = false;
        }
        if (this.itemInUse != null) {
            final ItemStack currentItem = this.inventory.getCurrentItem();
            if (currentItem == this.itemInUse) {
                if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
                    this.updateItemUse(currentItem, 5);
                }
                if (--this.itemInUseCount == 0 && !this.worldObj.isRemote) {
                    this.onItemUseFinish();
                }
            }
            else {
                this.clearItemInUse();
            }
        }
        if (this.xpCooldown > 0) {
            --this.xpCooldown;
        }
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.worldObj.isRemote) {
                if (!this.func_175143_p()) {
                    this.wakeUpPlayer(true, true, false);
                }
                else if (this.worldObj.isDaytime()) {
                    this.wakeUpPlayer(false, true, true);
                }
            }
        }
        else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.onUpdate();
        if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        if (this.isBurning() && this.capabilities.disableDamage) {
            this.extinguish();
        }
        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        final double n = this.posX - this.field_71094_bP;
        final double n2 = this.posY - this.field_71095_bQ;
        final double n3 = this.posZ - this.field_71085_bR;
        final double n4 = 10.0;
        if (n > n4) {
            final double posX = this.posX;
            this.field_71094_bP = posX;
            this.field_71091_bM = posX;
        }
        if (n3 > n4) {
            final double posZ = this.posZ;
            this.field_71085_bR = posZ;
            this.field_71097_bO = posZ;
        }
        if (n2 > n4) {
            final double posY = this.posY;
            this.field_71095_bQ = posY;
            this.field_71096_bN = posY;
        }
        if (n < -n4) {
            final double posX2 = this.posX;
            this.field_71094_bP = posX2;
            this.field_71091_bM = posX2;
        }
        if (n3 < -n4) {
            final double posZ2 = this.posZ;
            this.field_71085_bR = posZ2;
            this.field_71097_bO = posZ2;
        }
        if (n2 < -n4) {
            final double posY2 = this.posY;
            this.field_71095_bQ = posY2;
            this.field_71096_bN = posY2;
        }
        this.field_71094_bP += n * 0.25;
        this.field_71085_bR += n3 * 0.25;
        this.field_71095_bQ += n2 * 0.25;
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.worldObj.isRemote) {
            this.foodStats.onUpdate(this);
            this.triggerAchievement(StatList.minutesPlayedStat);
            if (this.isEntityAlive()) {
                this.triggerAchievement(StatList.timeSinceDeathStat);
            }
        }
        final double clamp_double = MathHelper.clamp_double(this.posX, -2.9999999E7, 2.9999999E7);
        final double clamp_double2 = MathHelper.clamp_double(this.posZ, -2.9999999E7, 2.9999999E7);
        if (clamp_double != this.posX || clamp_double2 != this.posZ) {
            this.setPosition(clamp_double, this.posY, clamp_double2);
        }
    }
    
    @Override
    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }
    
    @Override
    protected String getSwimSound() {
        return "game.player.swim";
    }
    
    @Override
    protected String getSplashSound() {
        return "game.player.swim.splash";
    }
    
    @Override
    public int getPortalCooldown() {
        return 10;
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSoundToNearExcept(this, s, n, n2);
    }
    
    protected void updateItemUse(final ItemStack itemStack, final int n) {
        if (itemStack.getItemUseAction() == EnumAction.DRINK) {
            this.playSound("random.drink", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (itemStack.getItemUseAction() == EnumAction.EAT) {
            while (0 < n) {
                final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
                final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                if (itemStack.getHasSubtypes()) {
                    this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()), itemStack.getMetadata());
                }
                else {
                    this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()));
                }
                int n2 = 0;
                ++n2;
            }
            this.playSound("random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            final int stackSize = this.itemInUse.stackSize;
            final ItemStack onItemUseFinish = this.itemInUse.onItemUseFinish(this.worldObj, this);
            if (onItemUseFinish != this.itemInUse || (onItemUseFinish != null && onItemUseFinish.stackSize != stackSize)) {
                this.inventory.mainInventory[this.inventory.currentItem] = onItemUseFinish;
                if (onItemUseFinish.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 9) {
            this.onItemUseFinish();
        }
        else if (b == 23) {
            this.field_175153_bG = false;
        }
        else if (b == 22) {
            this.field_175153_bG = true;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0f || this.isPlayerSleeping();
    }
    
    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }
    
    @Override
    public void updateRidden() {
        if (!this.worldObj.isRemote && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking(false);
        }
        else {
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            final float rotationYaw = this.rotationYaw;
            final float rotationPitch = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
            if (this.ridingEntity instanceof EntityPig) {
                this.rotationPitch = rotationPitch;
                this.rotationYaw = rotationYaw;
                this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
            }
        }
    }
    
    public void preparePlayerToSpawn() {
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
        this.rotationYawHead = this.rotationYaw;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
            if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
            }
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.worldObj.isRemote) {
            entityAttribute.setBaseValue(this.capabilities.getWalkSpeed());
        }
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor += (float)(this.speedInAir * 0.3);
        }
        this.setAIMoveSpeed((float)entityAttribute.getAttributeValue());
        float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float n = (float)(Math.atan(-this.motionY * 0.20000000298023224) * 15.0);
        if (sqrt_double > 0.1f) {
            sqrt_double = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            sqrt_double = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            n = 0.0f;
        }
        this.cameraYaw += (sqrt_double - this.cameraYaw) * 0.4f;
        this.cameraPitch += (n - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0.0f && !this.func_175149_v()) {
            AxisAlignedBB axisAlignedBB;
            if (this.ridingEntity != null && !this.ridingEntity.isDead) {
                axisAlignedBB = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0, 0.0, 1.0);
            }
            else {
                axisAlignedBB = this.getEntityBoundingBox().expand(1.0, 0.5, 1.0);
            }
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
                if (!entity.isDead) {
                    this.collideWithPlayer(entity);
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    private void collideWithPlayer(final Entity entity) {
        entity.onCollideWithPlayer(this);
    }
    
    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setScore(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    public void addScore(final int n) {
        this.dataWatcher.updateObject(18, this.getScore() + n);
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.getName().equals("Notch")) {
            this.func_146097_a(new ItemStack(Items.apple, 1), true, false);
        }
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        if (damageSource != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
    }
    
    @Override
    protected String getHurtSound() {
        return "game.player.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "game.player.die";
    }
    
    @Override
    public void addToPlayerScore(final Entity entity, final int n) {
        this.addScore(n);
        final Collection func_96520_a = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);
        if (entity instanceof EntityPlayer) {
            this.triggerAchievement(StatList.playerKillsStat);
            func_96520_a.addAll(this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
            func_96520_a.addAll(this.func_175137_e(entity));
        }
        else {
            this.triggerAchievement(StatList.mobKillsStat);
        }
        final Iterator<ScoreObjective> iterator = func_96520_a.iterator();
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
    }
    
    private Collection func_175137_e(final Entity entity) {
        final ScorePlayerTeam playersTeam = this.getWorldScoreboard().getPlayersTeam(this.getName());
        if (playersTeam != null) {
            final int func_175746_b = playersTeam.func_178775_l().func_175746_b();
            if (func_175746_b >= 0 && func_175746_b < IScoreObjectiveCriteria.field_178793_i.length) {
                final Iterator iterator = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178793_i[func_175746_b]).iterator();
                while (iterator.hasNext()) {
                    this.getWorldScoreboard().getValueFromObjective(entity.getName(), iterator.next()).func_96648_a();
                }
            }
        }
        final ScorePlayerTeam playersTeam2 = this.getWorldScoreboard().getPlayersTeam(entity.getName());
        if (playersTeam2 != null) {
            final int func_175746_b2 = playersTeam2.func_178775_l().func_175746_b();
            if (func_175746_b2 >= 0 && func_175746_b2 < IScoreObjectiveCriteria.field_178792_h.length) {
                return this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178792_h[func_175746_b2]);
            }
        }
        return Lists.newArrayList();
    }
    
    public EntityItem dropOneItem(final boolean b) {
        return this.func_146097_a(this.inventory.decrStackSize(this.inventory.currentItem, (b && this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false, true);
    }
    
    public EntityItem dropPlayerItemWithRandomChoice(final ItemStack itemStack, final boolean b) {
        return this.func_146097_a(itemStack, false, false);
    }
    
    public EntityItem func_146097_a(final ItemStack itemStack, final boolean b, final boolean b2) {
        if (itemStack == null) {
            return null;
        }
        if (itemStack.stackSize == 0) {
            return null;
        }
        final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + this.getEyeHeight(), this.posZ, itemStack);
        entityItem.setPickupDelay(40);
        if (b2) {
            entityItem.setThrower(this.getName());
        }
        if (b) {
            final float n = this.rand.nextFloat() * 0.5f;
            final float n2 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            entityItem.motionX = -MathHelper.sin(n2) * n;
            entityItem.motionZ = MathHelper.cos(n2) * n;
            entityItem.motionY = 0.20000000298023224;
        }
        else {
            final float n3 = 0.3f;
            entityItem.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n3 + 0.1f;
            final float n4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            final float n5 = 0.02f * this.rand.nextFloat();
            final EntityItem entityItem2 = entityItem;
            entityItem2.motionX += Math.cos(n4) * n5;
            final EntityItem entityItem3 = entityItem;
            entityItem3.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem4 = entityItem;
            entityItem4.motionZ += Math.sin(n4) * n5;
        }
        this.joinEntityItemWithWorld(entityItem);
        if (b2) {
            this.triggerAchievement(StatList.dropStat);
        }
        return entityItem;
    }
    
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
        this.worldObj.spawnEntityInWorld(entityItem);
    }
    
    public float func_180471_a(final Block block) {
        float strVsBlock = this.inventory.getStrVsBlock(block);
        if (strVsBlock > 1.0f) {
            final int efficiencyModifier = EnchantmentHelper.getEfficiencyModifier(this);
            final ItemStack currentItem = this.inventory.getCurrentItem();
            if (efficiencyModifier > 0 && currentItem != null) {
                strVsBlock += efficiencyModifier * efficiencyModifier + 1;
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            strVsBlock *= 1.0f + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            float n = 0.0f;
            switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                case 0: {
                    n = 0.3f;
                    break;
                }
                case 1: {
                    n = 0.09f;
                    break;
                }
                case 2: {
                    n = 0.0027f;
                    break;
                }
                default: {
                    n = 8.1E-4f;
                    break;
                }
            }
            strVsBlock *= n;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            strVsBlock /= 5.0f;
        }
        if (!this.onGround) {
            strVsBlock /= 5.0f;
        }
        return strVsBlock;
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.inventory.func_146025_b(block);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.entityUniqueID = getUUID(this.gameProfile);
        this.inventory.readFromNBT(nbtTagCompound.getTagList("Inventory", 10));
        this.inventory.currentItem = nbtTagCompound.getInteger("SelectedItemSlot");
        this.sleeping = nbtTagCompound.getBoolean("Sleeping");
        this.sleepTimer = nbtTagCompound.getShort("SleepTimer");
        this.experience = nbtTagCompound.getFloat("XpP");
        this.experienceLevel = nbtTagCompound.getInteger("XpLevel");
        this.experienceTotal = nbtTagCompound.getInteger("XpTotal");
        this.field_175152_f = nbtTagCompound.getInteger("XpSeed");
        if (this.field_175152_f == 0) {
            this.field_175152_f = this.rand.nextInt();
        }
        this.setScore(nbtTagCompound.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new BlockPos(this);
            this.wakeUpPlayer(true, true, false);
        }
        if (nbtTagCompound.hasKey("SpawnX", 99) && nbtTagCompound.hasKey("SpawnY", 99) && nbtTagCompound.hasKey("SpawnZ", 99)) {
            this.spawnChunk = new BlockPos(nbtTagCompound.getInteger("SpawnX"), nbtTagCompound.getInteger("SpawnY"), nbtTagCompound.getInteger("SpawnZ"));
            this.spawnForced = nbtTagCompound.getBoolean("SpawnForced");
        }
        this.foodStats.readNBT(nbtTagCompound);
        this.capabilities.readCapabilitiesFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("EnderItems", 9)) {
            this.theInventoryEnderChest.loadInventoryFromNBT(nbtTagCompound.getTagList("EnderItems", 10));
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        nbtTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        nbtTagCompound.setBoolean("Sleeping", this.sleeping);
        nbtTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
        nbtTagCompound.setFloat("XpP", this.experience);
        nbtTagCompound.setInteger("XpLevel", this.experienceLevel);
        nbtTagCompound.setInteger("XpTotal", this.experienceTotal);
        nbtTagCompound.setInteger("XpSeed", this.field_175152_f);
        nbtTagCompound.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            nbtTagCompound.setInteger("SpawnX", this.spawnChunk.getX());
            nbtTagCompound.setInteger("SpawnY", this.spawnChunk.getY());
            nbtTagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
            nbtTagCompound.setBoolean("SpawnForced", this.spawnForced);
        }
        this.foodStats.writeNBT(nbtTagCompound);
        this.capabilities.writeCapabilitiesToNBT(nbtTagCompound);
        nbtTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
        final ItemStack currentItem = this.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() != null) {
            nbtTagCompound.setTag("SelectedItem", currentItem.writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (this.capabilities.disableDamage && !damageSource.canHarmInCreative()) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
            this.wakeUpPlayer(true, true, false);
        }
        if (damageSource.isDifficultyScaled()) {
            if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
                n = 0.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
                n = n / 2.0f + 1.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                n = n * 3.0f / 2.0f;
            }
        }
        if (n == 0.0f) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null) {
            final Entity shootingEntity = ((EntityArrow)entity).shootingEntity;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public boolean canAttackPlayer(final EntityPlayer entityPlayer) {
        final Team team = this.getTeam();
        final Team team2 = entityPlayer.getTeam();
        return team == null || !team.isSameTeam(team2) || team.getAllowFriendlyFire();
    }
    
    @Override
    protected void damageArmor(final float n) {
        this.inventory.damageArmor(n);
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }
    
    public float getArmorVisibility() {
        final ItemStack[] armorInventory = this.inventory.armorInventory;
        while (0 < armorInventory.length) {
            if (armorInventory[0] != null) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0 / (float)this.inventory.armorInventory.length;
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.func_180431_b(damageSource)) {
            if (!damageSource.isUnblockable() && this.isBlocking() && n > 0.0f) {
                n = (1.0f + n) * 0.5f;
            }
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                this.addExhaustion(damageSource.getHungerDamage());
                final float health = this.getHealth();
                this.setHealth(this.getHealth() - n);
                this.getCombatTracker().func_94547_a(damageSource, health, n);
                if (n < 3.4028235E37f) {
                    this.addStat(StatList.damageTakenStat, Math.round(n * 10.0f));
                }
            }
        }
    }
    
    public void func_175141_a(final TileEntitySign tileEntitySign) {
    }
    
    public void func_146095_a(final CommandBlockLogic commandBlockLogic) {
    }
    
    public void displayVillagerTradeGui(final IMerchant merchant) {
    }
    
    public void displayGUIChest(final IInventory inventory) {
    }
    
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
    }
    
    public void displayGui(final IInteractionObject interactionObject) {
    }
    
    public void displayGUIBook(final ItemStack itemStack) {
    }
    
    public boolean interactWith(final Entity entity) {
        if (this.func_175149_v()) {
            if (entity instanceof IInventory) {
                this.displayGUIChest((IInventory)entity);
            }
            return false;
        }
        ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        final ItemStack itemStack = (currentEquippedItem != null) ? currentEquippedItem.copy() : null;
        if (!entity.interactFirst(this)) {
            if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                if (this.capabilities.isCreativeMode) {
                    currentEquippedItem = itemStack;
                }
                if (currentEquippedItem.interactWithEntity(this, (EntityLivingBase)entity)) {
                    if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                    }
                    return true;
                }
            }
            return false;
        }
        if (currentEquippedItem != null && currentEquippedItem == this.getCurrentEquippedItem()) {
            if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                this.destroyCurrentEquippedItem();
            }
            else if (currentEquippedItem.stackSize < itemStack.stackSize && this.capabilities.isCreativeMode) {
                currentEquippedItem.stackSize = itemStack.stackSize;
            }
        }
        return true;
    }
    
    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }
    
    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }
    
    @Override
    public double getYOffset() {
        return -0.35;
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity lastAttacker) {
        if (lastAttacker.canAttackWithItem() && !lastAttacker.hitByEntity(this)) {
            float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float n2;
            if (lastAttacker instanceof EntityLivingBase) {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)lastAttacker).getCreatureAttribute());
            }
            else {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            }
            int n3 = 0 + EnchantmentHelper.getRespiration(this);
            if (this.isSprinting()) {
                ++n3;
            }
            if (n > 0.0f || n2 > 0.0f) {
                final boolean b = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && lastAttacker instanceof EntityLivingBase;
                if (b && n > 0.0f) {
                    n *= 1.5f;
                }
                final float n4 = n + n2;
                final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
                if (lastAttacker instanceof EntityLivingBase && fireAspectModifier > 0 && !lastAttacker.isBurning()) {
                    lastAttacker.setFire(1);
                }
                final double motionX = lastAttacker.motionX;
                final double motionY = lastAttacker.motionY;
                final double motionZ = lastAttacker.motionZ;
                if (lastAttacker.attackEntityFrom(DamageSource.causePlayerDamage(this), n4)) {
                    if (n3 > 0) {
                        lastAttacker.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * n3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * n3 * 0.5f);
                        this.motionX *= 0.6;
                        this.motionZ *= 0.6;
                        this.setSprinting(false);
                    }
                    if (lastAttacker instanceof EntityPlayerMP && lastAttacker.velocityChanged) {
                        ((EntityPlayerMP)lastAttacker).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(lastAttacker));
                        lastAttacker.velocityChanged = false;
                        lastAttacker.motionX = motionX;
                        lastAttacker.motionY = motionY;
                        lastAttacker.motionZ = motionZ;
                    }
                    if (b) {
                        this.onCriticalHit(lastAttacker);
                    }
                    if (n2 > 0.0f) {
                        this.onEnchantmentCritical(lastAttacker);
                    }
                    if (n4 >= 18.0f) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttacker(lastAttacker);
                    if (lastAttacker instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)lastAttacker, this);
                    }
                    EnchantmentHelper.func_151385_b(this, lastAttacker);
                    final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
                    Entity entity = lastAttacker;
                    if (lastAttacker instanceof EntityDragonPart) {
                        final IEntityMultiPart entityDragonObj = ((EntityDragonPart)lastAttacker).entityDragonObj;
                        if (entityDragonObj instanceof EntityLivingBase) {
                            entity = (EntityLivingBase)entityDragonObj;
                        }
                    }
                    if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                        currentEquippedItem.hitEntity((EntityLivingBase)entity, this);
                        if (currentEquippedItem.stackSize <= 0) {
                            this.destroyCurrentEquippedItem();
                        }
                    }
                    if (lastAttacker instanceof EntityLivingBase) {
                        this.addStat(StatList.damageDealtStat, Math.round(n4 * 10.0f));
                        if (fireAspectModifier > 0) {
                            lastAttacker.setFire(fireAspectModifier * 4);
                        }
                    }
                    this.addExhaustion(0.3f);
                }
                else if (true) {
                    lastAttacker.extinguish();
                }
            }
        }
    }
    
    public void onCriticalHit(final Entity entity) {
    }
    
    public void onEnchantmentCritical(final Entity entity) {
    }
    
    public void respawnPlayer() {
    }
    
    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }
    
    public boolean func_175144_cb() {
        return false;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public EnumStatus func_180469_a(final BlockPos playerLocation) {
        if (!this.worldObj.isRemote) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EnumStatus.OTHER_PROBLEM;
            }
            if (!this.worldObj.provider.isSurfaceWorld()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - playerLocation.getX()) > 3.0 || Math.abs(this.posY - playerLocation.getY()) > 2.0 || Math.abs(this.posZ - playerLocation.getZ()) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            final double n = 8.0;
            final double n2 = 5.0;
            if (!this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(playerLocation.getX() - n, playerLocation.getY() - n2, playerLocation.getZ() - n, playerLocation.getX() + n, playerLocation.getY() + n2, playerLocation.getZ() + n)).isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        if (this.isRiding()) {
            this.mountEntity(null);
        }
        this.setSize(0.2f, 0.2f);
        if (this.worldObj.isBlockLoaded(playerLocation)) {
            final EnumFacing enumFacing = (EnumFacing)this.worldObj.getBlockState(playerLocation).getValue(BlockDirectional.AGE);
            float n3 = 0.5f;
            float n4 = 0.5f;
            switch (SwitchEnumFacing.field_179420_a[enumFacing.ordinal()]) {
                case 1: {
                    n4 = 0.9f;
                    break;
                }
                case 2: {
                    n4 = 0.1f;
                    break;
                }
                case 3: {
                    n3 = 0.1f;
                    break;
                }
                case 4: {
                    n3 = 0.9f;
                    break;
                }
            }
            this.func_175139_a(enumFacing);
            this.setPosition(playerLocation.getX() + n3, playerLocation.getY() + 0.6875f, playerLocation.getZ() + n4);
        }
        else {
            this.setPosition(playerLocation.getX() + 0.5f, playerLocation.getY() + 0.6875f, playerLocation.getZ() + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = playerLocation;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        if (!this.worldObj.isRemote) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }
    
    private void func_175139_a(final EnumFacing enumFacing) {
        this.field_71079_bU = 0.0f;
        this.field_71089_bV = 0.0f;
        switch (SwitchEnumFacing.field_179420_a[enumFacing.ordinal()]) {
            case 1: {
                this.field_71089_bV = -1.8f;
                break;
            }
            case 2: {
                this.field_71089_bV = 1.8f;
                break;
            }
            case 3: {
                this.field_71079_bU = 1.8f;
                break;
            }
            case 4: {
                this.field_71079_bU = -1.8f;
                break;
            }
        }
    }
    
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        this.setSize(0.6f, 1.8f);
        final IBlockState blockState = this.worldObj.getBlockState(this.playerLocation);
        if (this.playerLocation != null && blockState.getBlock() == Blocks.bed) {
            this.worldObj.setBlockState(this.playerLocation, blockState.withProperty(BlockBed.OCCUPIED_PROP, false), 4);
            BlockPos blockPos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
            if (blockPos == null) {
                blockPos = this.playerLocation.offsetUp();
            }
            this.setPosition(blockPos.getX() + 0.5f, blockPos.getY() + 0.1f, blockPos.getZ() + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.isRemote && b2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = (b ? 0 : 100);
        if (b3) {
            this.func_180473_a(this.playerLocation, false);
        }
    }
    
    private boolean func_175143_p() {
        return this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed;
    }
    
    public static BlockPos func_180467_a(final World world, final BlockPos blockPos, final boolean b) {
        if (world.getBlockState(blockPos).getBlock() == Blocks.bed) {
            return BlockBed.getSafeExitLocation(world, blockPos, 0);
        }
        if (!b) {
            return null;
        }
        final Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        final Material material2 = world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial();
        final boolean b2 = !material.isSolid() && !material.isLiquid();
        final boolean b3 = !material2.isSolid() && !material2.isLiquid();
        return (b2 && b3) ? blockPos : null;
    }
    
    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            switch (SwitchEnumFacing.field_179420_a[((EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue(BlockDirectional.AGE)).ordinal()]) {
                case 1: {
                    return 90.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 0.0f;
                }
                case 4: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }
    
    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }
    
    public int getSleepTimer() {
        return this.sleepTimer;
    }
    
    public void addChatComponentMessage(final IChatComponent chatComponent) {
    }
    
    public BlockPos func_180470_cg() {
        return this.spawnChunk;
    }
    
    public boolean isSpawnForced() {
        return this.spawnForced;
    }
    
    public void func_180473_a(final BlockPos spawnChunk, final boolean spawnForced) {
        if (spawnChunk != null) {
            this.spawnChunk = spawnChunk;
            this.spawnForced = spawnForced;
        }
        else {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }
    
    public void triggerAchievement(final StatBase statBase) {
        this.addStat(statBase, 1);
    }
    
    public void addStat(final StatBase statBase, final int n) {
    }
    
    public void func_175145_a(final StatBase statBase) {
    }
    
    public void jump() {
        super.jump();
        this.triggerAchievement(StatList.jumpStat);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
        }
        else {
            this.addExhaustion(0.2f);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            final Client instance = Client.INSTANCE;
            if (Client.getModuleByName("NotchFlyEffect").toggled) {
                while (1 < 10) {
                    Minecraft.theWorld.spawnParticle(EnumParticleTypes.CLOUD, Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, 0.0, 0.0, 0.0, new int[0]);
                    int n3 = 0;
                    ++n3;
                }
            }
            final double motionY = this.motionY;
            final float jumpMovementFactor = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed() * (this.isSprinting() ? 2 : 1);
            super.moveEntityWithHeading(n, n2);
            this.motionY = motionY * 0.6;
            this.jumpMovementFactor = jumpMovementFactor;
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
        this.addMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
    }
    
    @Override
    public float getAIMoveSpeed() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }
    
    public void addMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
                if (round > 0) {
                    this.addStat(StatList.distanceDoveStat, round);
                    this.addExhaustion(0.015f * round * 0.01f);
                }
            }
            else if (this.isInWater()) {
                final int round2 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round2 > 0) {
                    this.addStat(StatList.distanceSwumStat, round2);
                    this.addExhaustion(0.015f * round2 * 0.01f);
                }
            }
            else if (this.isOnLadder()) {
                if (n2 > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(n2 * 100.0));
                }
            }
            else if (this.onGround) {
                final int round3 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round3 > 0) {
                    this.addStat(StatList.distanceWalkedStat, round3);
                    if (this.isSprinting()) {
                        this.addStat(StatList.distanceSprintedStat, round3);
                        this.addExhaustion(0.099999994f * round3 * 0.01f);
                    }
                    else {
                        if (this.isSneaking()) {
                            this.addStat(StatList.distanceCrouchedStat, round3);
                        }
                        this.addExhaustion(0.01f * round3 * 0.01f);
                    }
                }
            }
            else {
                final int round4 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round4 > 25) {
                    this.addStat(StatList.distanceFlownStat, round4);
                }
            }
        }
    }
    
    private void addMountedMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity != null) {
            final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
            if (round > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, round);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new BlockPos(this);
                    }
                    else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                        this.triggerAchievement(AchievementList.onARail);
                    }
                }
                else if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, round);
                }
                else if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, round);
                }
                else if (this.ridingEntity instanceof EntityHorse) {
                    this.addStat(StatList.distanceByHorseStat, round);
                }
            }
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (!this.capabilities.allowFlying) {
            if (n >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round(n * 100.0));
            }
            super.fall(n, n2);
        }
    }
    
    @Override
    protected void resetHeight() {
        if (!this.func_175149_v()) {
            super.resetHeight();
        }
    }
    
    @Override
    protected String func_146067_o(final int n) {
        return (n > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(entityLivingBase));
        if (entityEggInfo != null) {
            this.triggerAchievement(entityEggInfo.field_151512_d);
        }
    }
    
    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.inventory.armorItemInSlot(n);
    }
    
    public void addExperience(int n) {
        this.addScore(n);
        final int n2 = Integer.MAX_VALUE - this.experienceTotal;
        if (n > n2) {
            n = n2;
        }
        this.experience += n / (float)this.xpBarCap();
        this.experienceTotal += n;
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= this.xpBarCap();
        }
    }
    
    public int func_175138_ci() {
        return this.field_175152_f;
    }
    
    public void func_71013_b(final int n) {
        this.experienceLevel -= n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        this.field_175152_f = this.rand.nextInt();
    }
    
    public void addExperienceLevel(final int n) {
        this.experienceLevel += n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (n > 0 && this.experienceLevel % 5 == 0 && this.field_82249_h < this.ticksExisted - 100.0f) {
            this.worldObj.playSoundAtEntity(this, "random.levelup", ((this.experienceLevel > 30) ? 1.0f : (this.experienceLevel / 30.0f)) * 0.75f, 1.0f);
            this.field_82249_h = this.ticksExisted;
        }
    }
    
    public int xpBarCap() {
        return (this.experienceLevel >= 30) ? (112 + (this.experienceLevel - 30) * 9) : ((this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + this.experienceLevel * 2));
    }
    
    public void addExhaustion(final float n) {
        if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
            this.foodStats.addExhaustion(n);
        }
    }
    
    public FoodStats getFoodStats() {
        return this.foodStats;
    }
    
    public boolean canEat(final boolean b) {
        return (b || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }
    
    public boolean shouldHeal() {
        return this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth();
    }
    
    public void setItemInUse(final ItemStack itemInUse, final int itemInUseCount) {
        if (itemInUse != this.itemInUse) {
            this.itemInUse = itemInUse;
            this.itemInUseCount = itemInUseCount;
            if (!this.worldObj.isRemote) {
                this.setEating(true);
            }
        }
    }
    
    public boolean func_175142_cm() {
        return this.capabilities.allowEdit;
    }
    
    public boolean func_175151_a(final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        return this.capabilities.allowEdit || (itemStack != null && (itemStack.canPlaceOn(this.worldObj.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock()) || itemStack.canEditBlocks()));
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            return 0;
        }
        final int n = this.experienceLevel * 7;
        return (n > 100) ? 100 : n;
    }
    
    @Override
    protected boolean isPlayer() {
        return true;
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }
    
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.setHealth(entityPlayer.getHealth());
            this.foodStats = entityPlayer.foodStats;
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
            this.teleportDirection = entityPlayer.teleportDirection;
        }
        else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
        }
        this.theInventoryEnderChest = entityPlayer.theInventoryEnderChest;
        this.getDataWatcher().updateObject(10, entityPlayer.getDataWatcher().getWatchableObjectByte(10));
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }
    
    public void sendPlayerAbilities() {
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
    }
    
    @Override
    public String getName() {
        return this.gameProfile.getName();
    }
    
    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return (n == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[n - 1];
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.inventory.armorInventory[n] = itemStack;
    }
    
    @Override
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        if (!this.isInvisible()) {
            return false;
        }
        if (entityPlayer.func_175149_v()) {
            return false;
        }
        final Team team = this.getTeam();
        return team == null || entityPlayer == null || entityPlayer.getTeam() != team || !team.func_98297_h();
    }
    
    public abstract boolean func_175149_v();
    
    @Override
    public ItemStack[] getInventory() {
        return this.inventory.armorInventory;
    }
    
    @Override
    public boolean isPushedByWater() {
        return !this.capabilities.isFlying;
    }
    
    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }
    
    @Override
    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getName());
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        chatComponentText.getChatStyle().setChatHoverEvent(this.func_174823_aP());
        chatComponentText.getChatStyle().setInsertion(this.getName());
        return chatComponentText;
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isPlayerSleeping()) {
            n = 0.2f;
        }
        if (this.isSneaking()) {
            n -= 0.08f;
        }
        return n;
    }
    
    @Override
    public void setAbsorptionAmount(float n) {
        if (n < 0.0f) {
            n = 0.0f;
        }
        this.getDataWatcher().updateObject(17, n);
    }
    
    @Override
    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(17);
    }
    
    public static UUID getUUID(final GameProfile gameProfile) {
        UUID uuid = gameProfile.getId();
        if (uuid == null) {
            uuid = func_175147_b(gameProfile.getName());
        }
        return uuid;
    }
    
    public static UUID func_175147_b(final String s) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + s).getBytes(Charsets.UTF_8));
    }
    
    public boolean func_175146_a(final LockCode lockCode) {
        if (lockCode.isEmpty()) {
            return true;
        }
        final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        return currentEquippedItem != null && currentEquippedItem.hasDisplayName() && currentEquippedItem.getDisplayName().equals(lockCode.getLock());
    }
    
    public boolean func_175148_a(final EnumPlayerModelParts enumPlayerModelParts) {
        return (this.getDataWatcher().getWatchableObjectByte(10) & enumPlayerModelParts.func_179327_a()) == enumPlayerModelParts.func_179327_a();
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
    }
    
    @Override
    public boolean func_174820_d(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.inventory.mainInventory.length) {
            this.inventory.setInventorySlotContents(n, itemStack);
            return true;
        }
        final int n2 = n - 100;
        if (n2 >= 0 && n2 < this.inventory.armorInventory.length) {
            final int n3 = n2 + 1;
            if (itemStack != null && itemStack.getItem() != null) {
                if (itemStack.getItem() instanceof ItemArmor) {
                    if (EntityLiving.getArmorPosition(itemStack) != n3) {
                        return false;
                    }
                }
                else if (n3 != 4 || (itemStack.getItem() != Items.skull && !(itemStack.getItem() instanceof ItemBlock))) {
                    return false;
                }
            }
            this.inventory.setInventorySlotContents(n2 + this.inventory.mainInventory.length, itemStack);
            return true;
        }
        final int n4 = n - 200;
        if (n4 >= 0 && n4 < this.theInventoryEnderChest.getSizeInventory()) {
            this.theInventoryEnderChest.setInventorySlotContents(n4, itemStack);
            return true;
        }
        return false;
    }
    
    public boolean func_175140_cp() {
        return this.field_175153_bG;
    }
    
    public void func_175150_k(final boolean field_175153_bG) {
        this.field_175153_bG = field_175153_bG;
    }
    
    public boolean canPlayerEdit(final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        return this.capabilities.allowEdit || (itemStack != null && (itemStack.canPlaceOn(this.worldObj.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock()) || itemStack.canEditBlocks()));
    }
    
    static {
        __OBFID = "CL_00001711";
    }
    
    public enum EnumChatVisibility
    {
        FULL("FULL", 0, "FULL", 0, 0, "options.chat.visibility.full"), 
        SYSTEM("SYSTEM", 1, "SYSTEM", 1, 1, "options.chat.visibility.system"), 
        HIDDEN("HIDDEN", 2, "HIDDEN", 2, 2, "options.chat.visibility.hidden");
        
        private static final EnumChatVisibility[] field_151432_d;
        private final int chatVisibility;
        private final String resourceKey;
        private static final EnumChatVisibility[] $VALUES;
        private static final String __OBFID;
        private static final EnumChatVisibility[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001714";
            ENUM$VALUES = new EnumChatVisibility[] { EnumChatVisibility.FULL, EnumChatVisibility.SYSTEM, EnumChatVisibility.HIDDEN };
            field_151432_d = new EnumChatVisibility[values().length];
            $VALUES = new EnumChatVisibility[] { EnumChatVisibility.FULL, EnumChatVisibility.SYSTEM, EnumChatVisibility.HIDDEN };
            final EnumChatVisibility[] values = values();
            while (0 < values.length) {
                final EnumChatVisibility enumChatVisibility = values[0];
                EnumChatVisibility.field_151432_d[enumChatVisibility.chatVisibility] = enumChatVisibility;
                int n = 0;
                ++n;
            }
        }
        
        private EnumChatVisibility(final String s, final int n, final String s2, final int n2, final int chatVisibility, final String resourceKey) {
            this.chatVisibility = chatVisibility;
            this.resourceKey = resourceKey;
        }
        
        public int getChatVisibility() {
            return this.chatVisibility;
        }
        
        public static EnumChatVisibility getEnumChatVisibility(final int n) {
            return EnumChatVisibility.field_151432_d[n % EnumChatVisibility.field_151432_d.length];
        }
        
        public String getResourceKey() {
            return this.resourceKey;
        }
    }
    
    public enum EnumStatus
    {
        OK("OK", 0, "OK", 0), 
        NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1, "NOT_POSSIBLE_HERE", 1), 
        NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2, "NOT_POSSIBLE_NOW", 2), 
        TOO_FAR_AWAY("TOO_FAR_AWAY", 3, "TOO_FAR_AWAY", 3), 
        OTHER_PROBLEM("OTHER_PROBLEM", 4, "OTHER_PROBLEM", 4), 
        NOT_SAFE("NOT_SAFE", 5, "NOT_SAFE", 5);
        
        private static final EnumStatus[] $VALUES;
        private static final String __OBFID;
        private static final EnumStatus[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001712";
            ENUM$VALUES = new EnumStatus[] { EnumStatus.OK, EnumStatus.NOT_POSSIBLE_HERE, EnumStatus.NOT_POSSIBLE_NOW, EnumStatus.TOO_FAR_AWAY, EnumStatus.OTHER_PROBLEM, EnumStatus.NOT_SAFE };
            $VALUES = new EnumStatus[] { EnumStatus.OK, EnumStatus.NOT_POSSIBLE_HERE, EnumStatus.NOT_POSSIBLE_NOW, EnumStatus.TOO_FAR_AWAY, EnumStatus.OTHER_PROBLEM, EnumStatus.NOT_SAFE };
        }
        
        private EnumStatus(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_179420_a;
        private static final String __OBFID;
        private static final String[] lIllIlIlIllIIIII;
        private static String[] lIllIlIlIllIIIIl;
        
        static {
            lIIIIlIlIlllIIlII();
            lIIIIlIlIlllIIIll();
            __OBFID = SwitchEnumFacing.lIllIlIlIllIIIII[0];
            field_179420_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_179420_a[EnumFacing.SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_179420_a[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_179420_a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_179420_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIIIIlIlIlllIIIll() {
            (lIllIlIlIllIIIII = new String[1])[0] = lIIIIlIlIlllIIIIl(SwitchEnumFacing.lIllIlIlIllIIIIl[0], SwitchEnumFacing.lIllIlIlIllIIIIl[1]);
            SwitchEnumFacing.lIllIlIlIllIIIIl = null;
        }
        
        private static void lIIIIlIlIlllIIlII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIllIlIlIllIIIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIIlIlIlllIIIIl(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
