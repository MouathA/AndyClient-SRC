package net.minecraft.entity.item;

import net.minecraft.entity.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;

public class EntityItem extends Entity
{
    private static final Logger logger;
    private int age;
    private int delayBeforeCanPickup;
    private int health;
    private String thrower;
    private String owner;
    public float hoverStart;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001669";
        logger = LogManager.getLogger();
    }
    
    public EntityItem(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntityItem(final World world, final double n, final double n2, final double n3, final ItemStack entityItemStack) {
        this(world, n, n2, n3);
        this.setEntityItemStack(entityItemStack);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityItem(final World world) {
        super(world);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.setEntityItemStack(new ItemStack(Blocks.air, 0));
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }
    
    @Override
    public void onUpdate() {
        if (this.getEntityItem() == null) {
            this.setDead();
        }
        else {
            super.onUpdate();
            if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767) {
                --this.delayBeforeCanPickup;
            }
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if ((int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ || this.ticksExisted % 25 == 0) {
                if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
                    this.motionY = 0.20000000298023224;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
                }
                if (!this.worldObj.isRemote) {
                    this.searchForOtherItemsNearby();
                }
            }
            float n = 0.98f;
            if (this.onGround) {
                n = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98f;
            }
            this.motionX *= n;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= n;
            if (this.onGround) {
                this.motionY *= -0.5;
            }
            if (this.age != -32768) {
                ++this.age;
            }
            this.handleWaterMovement();
            if (!this.worldObj.isRemote && this.age >= 6000) {
                this.setDead();
            }
        }
    }
    
    private void searchForOtherItemsNearby() {
        final Iterator<EntityItem> iterator = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.5, 0.0, 0.5)).iterator();
        while (iterator.hasNext()) {
            this.combineItems(iterator.next());
        }
    }
    
    private boolean combineItems(final EntityItem entityItem) {
        if (entityItem == this) {
            return false;
        }
        if (!entityItem.isEntityAlive() || !this.isEntityAlive()) {
            return false;
        }
        final ItemStack entityItem2 = this.getEntityItem();
        final ItemStack entityItem3 = entityItem.getEntityItem();
        if (this.delayBeforeCanPickup == 32767 || entityItem.delayBeforeCanPickup == 32767) {
            return false;
        }
        if (this.age == -32768 || entityItem.age == -32768) {
            return false;
        }
        if (entityItem3.getItem() != entityItem2.getItem()) {
            return false;
        }
        if (entityItem3.hasTagCompound() ^ entityItem2.hasTagCompound()) {
            return false;
        }
        if (entityItem3.hasTagCompound() && !entityItem3.getTagCompound().equals(entityItem2.getTagCompound())) {
            return false;
        }
        if (entityItem3.getItem() == null) {
            return false;
        }
        if (entityItem3.getItem().getHasSubtypes() && entityItem3.getMetadata() != entityItem2.getMetadata()) {
            return false;
        }
        if (entityItem3.stackSize < entityItem2.stackSize) {
            return entityItem.combineItems(this);
        }
        if (entityItem3.stackSize + entityItem2.stackSize > entityItem3.getMaxStackSize()) {
            return false;
        }
        final ItemStack itemStack = entityItem3;
        itemStack.stackSize += entityItem2.stackSize;
        entityItem.delayBeforeCanPickup = Math.max(entityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
        entityItem.age = Math.min(entityItem.age, this.age);
        entityItem.setEntityItemStack(entityItem3);
        this.setDead();
        return true;
    }
    
    public void setAgeToCreativeDespawnTime() {
        this.age = 4800;
    }
    
    @Override
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.inWater = true;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    @Override
    protected void dealFireDamage(final int n) {
        this.attackEntityFrom(DamageSource.inFire, (float)n);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && damageSource.isExplosion()) {
            return false;
        }
        this.setBeenAttacked();
        this.health -= (int)n;
        if (this.health <= 0) {
            this.setDead();
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("Health", (byte)this.health);
        nbtTagCompound.setShort("Age", (short)this.age);
        nbtTagCompound.setShort("PickupDelay", (short)this.delayBeforeCanPickup);
        if (this.getThrower() != null) {
            nbtTagCompound.setString("Thrower", this.thrower);
        }
        if (this.getOwner() != null) {
            nbtTagCompound.setString("Owner", this.owner);
        }
        if (this.getEntityItem() != null) {
            nbtTagCompound.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.health = (nbtTagCompound.getShort("Health") & 0xFF);
        this.age = nbtTagCompound.getShort("Age");
        if (nbtTagCompound.hasKey("PickupDelay")) {
            this.delayBeforeCanPickup = nbtTagCompound.getShort("PickupDelay");
        }
        if (nbtTagCompound.hasKey("Owner")) {
            this.owner = nbtTagCompound.getString("Owner");
        }
        if (nbtTagCompound.hasKey("Thrower")) {
            this.thrower = nbtTagCompound.getString("Thrower");
        }
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("Item")));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            final ItemStack entityItem = this.getEntityItem();
            final int stackSize = entityItem.stackSize;
            if (this.delayBeforeCanPickup == 0 && (this.owner == null || 6000 - this.age <= 200 || this.owner.equals(entityPlayer.getName())) && entityPlayer.inventory.addItemStackToInventory(entityItem)) {
                if (entityItem.getItem() == Item.getItemFromBlock(Blocks.log)) {
                    entityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (entityItem.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                    entityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (entityItem.getItem() == Items.leather) {
                    entityPlayer.triggerAchievement(AchievementList.killCow);
                }
                if (entityItem.getItem() == Items.diamond) {
                    entityPlayer.triggerAchievement(AchievementList.diamonds);
                }
                if (entityItem.getItem() == Items.blaze_rod) {
                    entityPlayer.triggerAchievement(AchievementList.blazeRod);
                }
                if (entityItem.getItem() == Items.diamond && this.getThrower() != null) {
                    final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(this.getThrower());
                    if (playerEntityByName != null && playerEntityByName != entityPlayer) {
                        playerEntityByName.triggerAchievement(AchievementList.diamondsToYou);
                    }
                }
                if (!this.isSlient()) {
                    this.worldObj.playSoundAtEntity(entityPlayer, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                }
                entityPlayer.onItemPickup(this, stackSize);
                if (entityItem.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    @Override
    public void travelToDimension(final int n) {
        super.travelToDimension(n);
        if (!this.worldObj.isRemote) {
            this.searchForOtherItemsNearby();
        }
    }
    
    public ItemStack getEntityItem() {
        final ItemStack watchableObjectItemStack = this.getDataWatcher().getWatchableObjectItemStack(10);
        if (watchableObjectItemStack == null) {
            if (this.worldObj != null) {
                EntityItem.logger.error("Item entity " + this.getEntityId() + " has no item?!");
            }
            return new ItemStack(Blocks.stone);
        }
        return watchableObjectItemStack;
    }
    
    public void setEntityItemStack(final ItemStack itemStack) {
        this.getDataWatcher().updateObject(10, itemStack);
        this.getDataWatcher().setObjectWatched(10);
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(final String owner) {
        this.owner = owner;
    }
    
    public String getThrower() {
        return this.thrower;
    }
    
    public void setThrower(final String thrower) {
        this.thrower = thrower;
    }
    
    public int func_174872_o() {
        return this.age;
    }
    
    public void setDefaultPickupDelay() {
        this.delayBeforeCanPickup = 10;
    }
    
    public void setNoPickupDelay() {
        this.delayBeforeCanPickup = 0;
    }
    
    public void setInfinitePickupDelay() {
        this.delayBeforeCanPickup = 32767;
    }
    
    public void setPickupDelay(final int delayBeforeCanPickup) {
        this.delayBeforeCanPickup = delayBeforeCanPickup;
    }
    
    public boolean func_174874_s() {
        return this.delayBeforeCanPickup > 0;
    }
    
    public void func_174873_u() {
        this.age = -6000;
    }
    
    public void func_174870_v() {
        this.setInfinitePickupDelay();
        this.age = 5999;
    }
}
