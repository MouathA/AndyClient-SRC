package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;

public class EntityPig extends EntityAnimal
{
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    private static final String __OBFID;
    
    public EntityPig(final World world) {
        super(world);
        this.setSize(0.9f, 0.9f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3f));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, false));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public boolean canBeSteered() {
        final ItemStack heldItem = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return heldItem != null && heldItem.getItem() == Items.carrot_on_a_stick;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Saddle", this.getSaddled());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSaddled(nbtTagCompound.getBoolean("Saddle"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        if (super.interact(entityPlayer)) {
            return true;
        }
        if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer)) {
            entityPlayer.mountEntity(this);
            return true;
        }
        return false;
    }
    
    @Override
    protected Item getDropItem() {
        return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + n)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_porkchop, 1);
            }
            else {
                this.dropItem(Items.porkchop, 1);
            }
            int n2 = 0;
            ++n2;
        }
        if (this.getSaddled()) {
            this.dropItem(Items.saddle, 1);
        }
    }
    
    public boolean getSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setSaddled(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(16, 1);
        }
        else {
            this.dataWatcher.updateObject(16, 0);
        }
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote) {
            final EntityPigZombie entityPigZombie = new EntityPigZombie(this.worldObj);
            entityPigZombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            entityPigZombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(entityPigZombie);
            this.setDead();
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        if (n > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }
    
    @Override
    public EntityPig createChild(final EntityAgeable entityAgeable) {
        return new EntityPig(this.worldObj);
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.carrot;
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001647";
    }
}
