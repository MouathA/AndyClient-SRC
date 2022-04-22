package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] stackResult;
    private static final String __OBFID;
    
    public InventoryCraftResult() {
        this.stackResult = new ItemStack[1];
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.stackResult[0];
    }
    
    @Override
    public String getName() {
        return "Result";
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackResult[0] != null) {
            final ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.stackResult[0] != null) {
            final ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackResult[0] = itemStack;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return true;
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
    public void clearInventory() {
        while (0 < this.stackResult.length) {
            this.stackResult[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00001760";
    }
}
