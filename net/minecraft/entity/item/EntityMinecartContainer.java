package net.minecraft.entity.item;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer
{
    private ItemStack[] minecartContainerItems;
    private boolean dropContentsWhenDead;
    private static final String __OBFID;
    
    public EntityMinecartContainer(final World world) {
        super(world);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    public EntityMinecartContainer(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        InventoryHelper.func_180176_a(this.worldObj, this, this);
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.minecartContainerItems[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.minecartContainerItems[n] == null) {
            return null;
        }
        if (this.minecartContainerItems[n].stackSize <= n2) {
            final ItemStack itemStack = this.minecartContainerItems[n];
            this.minecartContainerItems[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.minecartContainerItems[n].splitStack(n2);
        if (this.minecartContainerItems[n].stackSize == 0) {
            this.minecartContainerItems[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.minecartContainerItems[n] != null) {
            final ItemStack itemStack = this.minecartContainerItems[n];
            this.minecartContainerItems[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.minecartContainerItems[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return !this.isDead && entityPlayer.getDistanceSqToEntity(this) <= 64.0;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : "container.minecart";
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void travelToDimension(final int n) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(n);
    }
    
    @Override
    public void setDead() {
        if (this.dropContentsWhenDead) {
            InventoryHelper.func_180176_a(this.worldObj, this, this);
        }
        super.setDead();
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.minecartContainerItems.length) {
            if (this.minecartContainerItems[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.minecartContainerItems[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.minecartContainerItems.length) {
                this.minecartContainerItems[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            entityPlayer.displayGUIChest(this);
        }
        return true;
    }
    
    @Override
    protected void applyDrag() {
        final float n = 0.98f + (15 - Container.calcRedstoneFromInventory(this)) * 0.001f;
        this.motionX *= n;
        this.motionY *= 0.0;
        this.motionZ *= n;
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public boolean isLocked() {
        return false;
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
    }
    
    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }
    
    @Override
    public void clearInventory() {
        while (0 < this.minecartContainerItems.length) {
            this.minecartContainerItems[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00001674";
    }
}
