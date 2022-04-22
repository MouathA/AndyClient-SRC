package net.minecraft.entity.passive;

import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import com.google.common.base.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityOcelot extends EntityTameable
{
    private EntityAIAvoidEntity field_175545_bm;
    private EntityAITempt aiTempt;
    private static final String __OBFID;
    
    public EntityOcelot(final World world) {
        super(world);
        this.setSize(0.6f, 0.7f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 5.0f));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3f));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8));
        this.tasks.addTask(10, new EntityAIWander(this, 0.8));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, 0);
    }
    
    public void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            final double speed = this.getMoveHelper().getSpeed();
            if (speed == 0.6) {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (speed == 1.33) {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("CatType", this.getTameSkin());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setTameSkin(nbtTagCompound.getInteger("CatType"));
    }
    
    @Override
    protected String getLivingSound() {
        return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : ((this.rand.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.leather;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.aiSit.setSitting(false);
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (this.func_152114_e(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(currentItem)) {
                this.aiSit.setSitting(!this.isSitting());
            }
        }
        else if (this.aiTempt.isRunning() && currentItem != null && currentItem.getItem() == Items.fish && entityPlayer.getDistanceSqToEntity(this) < 9.0) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentItem;
                --itemStack.stackSize;
            }
            if (currentItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
                    this.func_152115_b(entityPlayer.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
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
    
    public EntityOcelot func_180493_b(final EntityAgeable entityAgeable) {
        final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
        if (this.isTamed()) {
            entityOcelot.func_152115_b(this.func_152113_b());
            entityOcelot.setTamed(true);
            entityOcelot.setTameSkin(this.getTameSkin());
        }
        return entityOcelot;
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.fish;
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityAnimal instanceof EntityOcelot)) {
            return false;
        }
        final EntityOcelot entityOcelot = (EntityOcelot)entityAnimal;
        return entityOcelot.isTamed() && (this.isInLove() && entityOcelot.isInLove());
    }
    
    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    public void setTameSkin(final int n) {
        this.dataWatcher.updateObject(18, (byte)n);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.rand.nextInt(3) != 0;
    }
    
    @Override
    public boolean handleLavaMovement() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
            if (blockPos.getY() < 63) {
                return false;
            }
            final Block block = this.worldObj.getBlockState(blockPos.offsetDown()).getBlock();
            if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : (this.isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName());
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
    }
    
    @Override
    protected void func_175544_ck() {
        if (this.field_175545_bm == null) {
            this.field_175545_bm = new EntityAIAvoidEntity(this, new Predicate() {
                private static final String __OBFID;
                final EntityOcelot this$0;
                
                public boolean func_179874_a(final Entity entity) {
                    return entity instanceof EntityPlayer;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179874_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002243";
                }
            }, 16.0f, 0.8, 1.33);
        }
        this.tasks.removeTask(this.field_175545_bm);
        if (!this.isTamed()) {
            this.tasks.addTask(4, this.field_175545_bm);
        }
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, IEntityLivingData func_180482_a) {
        func_180482_a = super.func_180482_a(difficultyInstance, func_180482_a);
        if (this.worldObj.rand.nextInt(7) == 0) {
            while (0 < 2) {
                final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
                entityOcelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                entityOcelot.setGrowingAge(-24000);
                this.worldObj.spawnEntityInWorld(entityOcelot);
                int n = 0;
                ++n;
            }
        }
        return func_180482_a;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.func_180493_b(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001646";
    }
}
