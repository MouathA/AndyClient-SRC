package net.minecraft.inventory;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class InventoryLargeChest implements ILockableContainer
{
    private String name;
    private ILockableContainer upperChest;
    private ILockableContainer lowerChest;
    private static final String __OBFID;
    
    public InventoryLargeChest(final String name, ILockableContainer upperChest, ILockableContainer lowerChest) {
        this.name = name;
        if (upperChest == null) {
            upperChest = lowerChest;
        }
        if (lowerChest == null) {
            lowerChest = upperChest;
        }
        this.upperChest = upperChest;
        this.lowerChest = lowerChest;
        if (upperChest.isLocked()) {
            lowerChest.setLockCode(upperChest.getLockCode());
        }
        else if (lowerChest.isLocked()) {
            upperChest.setLockCode(lowerChest.getLockCode());
        }
    }
    
    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }
    
    public boolean isPartOfLargeChest(final IInventory inventory) {
        return this.upperChest == inventory || this.lowerChest == inventory;
    }
    
    @Override
    public String getName() {
        return this.upperChest.hasCustomName() ? this.upperChest.getName() : (this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name);
    }
    
    @Override
    public boolean hasCustomName() {
        return this.upperChest.hasCustomName() || this.lowerChest.hasCustomName();
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(n - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(n);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        return (n >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(n - this.upperChest.getSizeInventory(), n2) : this.upperChest.decrStackSize(n, n2);
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        return (n >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlotOnClosing(n - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(n);
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        if (n >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(n - this.upperChest.getSizeInventory(), itemStack);
        }
        else {
            this.upperChest.setInventorySlotContents(n, itemStack);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }
    
    @Override
    public void markDirty() {
        this.upperChest.markDirty();
        this.lowerChest.markDirty();
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.upperChest.isUseableByPlayer(entityPlayer) && this.lowerChest.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        this.upperChest.openInventory(entityPlayer);
        this.lowerChest.openInventory(entityPlayer);
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        this.upperChest.closeInventory(entityPlayer);
        this.lowerChest.closeInventory(entityPlayer);
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
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
        return this.upperChest.isLocked() || this.lowerChest.isLocked();
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
        this.upperChest.setLockCode(lockCode);
        this.lowerChest.setLockCode(lockCode);
    }
    
    @Override
    public LockCode getLockCode() {
        return this.upperChest.getLockCode();
    }
    
    @Override
    public String getGuiID() {
        return this.upperChest.getGuiID();
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public void clearInventory() {
        this.upperChest.clearInventory();
        this.lowerChest.clearInventory();
    }
    
    static {
        __OBFID = "CL_00001507";
    }
}
