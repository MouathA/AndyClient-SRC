package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class EntityChicken extends EntityAnimal
{
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i;
    public int timeUntilNextEgg;
    public boolean field_152118_bv;
    private static final String __OBFID;
    
    public EntityChicken(final World world) {
        super(world);
        this.field_70889_i = 1.0f;
        this.setSize(0.4f, 0.7f);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0, Items.wheat_seeds, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos += (float)((this.onGround ? -1 : 4) * 0.3);
        this.destPos = MathHelper.clamp_float(this.destPos, 0.0f, 1.0f);
        if (!this.onGround && this.field_70889_i < 1.0f) {
            this.field_70889_i = 1.0f;
        }
        this.field_70889_i *= (float)0.9;
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.field_70886_e += this.field_70889_i * 2.0f;
        if (!this.worldObj.isRemote && !this.isChild() && !this.func_152116_bZ() && --this.timeUntilNextEgg <= 0) {
            this.playSound("mob.chicken.plop", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.dropItem(Items.egg, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.chicken.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.chicken.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.feather;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.feather, 1);
            int n2 = 0;
            ++n2;
        }
        if (this.isBurning()) {
            this.dropItem(Items.cooked_chicken, 1);
        }
        else {
            this.dropItem(Items.chicken, 1);
        }
    }
    
    public EntityChicken createChild1(final EntityAgeable entityAgeable) {
        return new EntityChicken(this.worldObj);
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.wheat_seeds;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.field_152118_bv = nbtTagCompound.getBoolean("IsChickenJockey");
        if (nbtTagCompound.hasKey("EggLayTime")) {
            this.timeUntilNextEgg = nbtTagCompound.getInteger("EggLayTime");
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return this.func_152116_bZ() ? 10 : super.getExperiencePoints(entityPlayer);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("IsChickenJockey", this.field_152118_bv);
        nbtTagCompound.setInteger("EggLayTime", this.timeUntilNextEgg);
    }
    
    @Override
    protected boolean canDespawn() {
        return this.func_152116_bZ() && this.riddenByEntity == null;
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        final float sin = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
        final float cos = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
        final float n = 0.1f;
        this.riddenByEntity.setPosition(this.posX + n * sin, this.posY + this.height * 0.5f + this.riddenByEntity.getYOffset() + 0.0f, this.posZ - n * cos);
        if (this.riddenByEntity instanceof EntityLivingBase) {
            ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }
    
    public boolean func_152116_bZ() {
        return this.field_152118_bv;
    }
    
    public void func_152117_i(final boolean field_152118_bv) {
        this.field_152118_bv = field_152118_bv;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild1(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001639";
    }
}
