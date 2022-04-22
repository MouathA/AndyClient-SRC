package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class InventoryCrafting implements IInventory
{
    private final ItemStack[] stackList;
    private final int inventoryWidth;
    private final int field_174924_c;
    private final Container eventHandler;
    private static final String __OBFID;
    
    public InventoryCrafting(final Container eventHandler, final int inventoryWidth, final int field_174924_c) {
        this.stackList = new ItemStack[inventoryWidth * field_174924_c];
        this.eventHandler = eventHandler;
        this.inventoryWidth = inventoryWidth;
        this.field_174924_c = field_174924_c;
    }
    
    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= this.getSizeInventory()) ? null : this.stackList[n];
    }
    
    public ItemStack getStackInRowAndColumn(final int n, final int n2) {
        return (n >= 0 && n < this.inventoryWidth && n2 >= 0 && n2 <= this.field_174924_c) ? this.getStackInSlot(n + n2 * this.inventoryWidth) : null;
    }
    
    @Override
    public String getName() {
        return "container.crafting";
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
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.stackList[n] != null) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackList[n] == null) {
            return null;
        }
        if (this.stackList[n].stackSize <= n2) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return itemStack;
        }
        final ItemStack splitStack = this.stackList[n].splitStack(n2);
        if (this.stackList[n].stackSize == 0) {
            this.stackList[n] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return splitStack;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackList[n] = itemStack;
        this.eventHandler.onCraftMatrixChanged(this);
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
        while (0 < this.stackList.length) {
            this.stackList[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    public int func_174923_h() {
        return this.field_174924_c;
    }
    
    public int func_174922_i() {
        return this.inventoryWidth;
    }
    
    static {
        __OBFID = "CL_00001743";
    }
}
