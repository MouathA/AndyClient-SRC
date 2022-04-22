package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class EntityEndermite extends EntityMob
{
    private int lifetime;
    private boolean playerSpawned;
    private static final String __OBFID;
    
    public EntityEndermite(final World world) {
        super(world);
        this.lifetime = 0;
        this.playerSpawned = false;
        this.experienceValue = 3;
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    public float getEyeHeight() {
        return 0.1f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.lifetime = nbtTagCompound.getInteger("Lifetime");
        this.playerSpawned = nbtTagCompound.getBoolean("PlayerSpawned");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Lifetime", this.lifetime);
        nbtTagCompound.setBoolean("PlayerSpawned", this.playerSpawned);
    }
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    public boolean isSpawnedByPlayer() {
        return this.playerSpawned;
    }
    
    public void setSpawnedByPlayer(final boolean playerSpawned) {
        this.playerSpawned = playerSpawned;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (!this.isNoDespawnRequired()) {
                ++this.lifetime;
            }
            if (this.lifetime >= 2400) {
                this.setDead();
            }
        }
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.getClosestPlayerToEntity(this, 5.0) == null;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    static {
        __OBFID = "CL_00002219";
    }
}
