package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityCow extends EntityAnimal
{
    private static final String __OBFID;
    
    public EntityCow(final World world) {
        super(world);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.cow.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.cow.step", 0.15f, 1.0f);
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
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(3) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.leather, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + n)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_beef, 1);
            }
            else {
                this.dropItem(Items.beef, 1);
            }
            ++n2;
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.bucket && !entityPlayer.capabilities.isCreativeMode) {
            if (currentItem.stackSize-- == 1) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
            }
            else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
                entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
            }
            return true;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public EntityCow createChild(final EntityAgeable entityAgeable) {
        return new EntityCow(this.worldObj);
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001640";
    }
}
