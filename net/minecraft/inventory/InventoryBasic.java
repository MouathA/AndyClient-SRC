package net.minecraft.inventory;

import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class InventoryBasic implements IInventory
{
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_70480_d;
    private boolean hasCustomName;
    private static final String __OBFID;
    
    public InventoryBasic(final String inventoryTitle, final boolean hasCustomName, final int slotsCount) {
        this.inventoryTitle = inventoryTitle;
        this.hasCustomName = hasCustomName;
        this.slotsCount = slotsCount;
        this.inventoryContents = new ItemStack[slotsCount];
    }
    
    public InventoryBasic(final IChatComponent chatComponent, final int n) {
        this(chatComponent.getUnformattedText(), true, n);
    }
    
    public void func_110134_a(final IInvBasic invBasic) {
        if (this.field_70480_d == null) {
            this.field_70480_d = Lists.newArrayList();
        }
        this.field_70480_d.add(invBasic);
    }
    
    public void func_110132_b(final IInvBasic invBasic) {
        this.field_70480_d.remove(invBasic);
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= 0 && n < this.inventoryContents.length) ? this.inventoryContents[n] : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.inventoryContents[n] == null) {
            return null;
        }
        if (this.inventoryContents[n].stackSize <= n2) {
            final ItemStack itemStack = this.inventoryContents[n];
            this.inventoryContents[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.inventoryContents[n].splitStack(n2);
        if (this.inventoryContents[n].stackSize == 0) {
            this.inventoryContents[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    public ItemStack func_174894_a(final ItemStack itemStack) {
        final ItemStack copy = itemStack.copy();
        while (0 < this.slotsCount) {
            final ItemStack stackInSlot = this.getStackInSlot(0);
            if (stackInSlot == null) {
                this.setInventorySlotContents(0, copy);
                this.markDirty();
                return null;
            }
            if (ItemStack.areItemsEqual(stackInSlot, copy)) {
                final int min = Math.min(copy.stackSize, Math.min(this.getInventoryStackLimit(), stackInSlot.getMaxStackSize()) - stackInSlot.stackSize);
                if (min > 0) {
                    final ItemStack itemStack2 = stackInSlot;
                    itemStack2.stackSize += min;
                    final ItemStack itemStack3 = copy;
                    itemStack3.stackSize -= min;
                    if (copy.stackSize <= 0) {
                        this.markDirty();
                        return null;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        if (copy.stackSize != itemStack.stackSize) {
            this.markDirty();
        }
        return copy;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.inventoryContents[n] != null) {
            final ItemStack itemStack = this.inventoryContents[n];
            this.inventoryContents[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.inventoryContents[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }
    
    @Override
    public String getName() {
        return this.inventoryTitle;
    }
    
    @Override
    public boolean hasCustomName() {
        return this.hasCustomName;
    }
    
    public void func_110133_a(final String inventoryTitle) {
        this.hasCustomName = true;
        this.inventoryTitle = inventoryTitle;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void markDirty() {
        if (this.field_70480_d != null) {
            while (0 < this.field_70480_d.size()) {
                this.field_70480_d.get(0).onInventoryChanged(this);
                int n = 0;
                ++n;
            }
        }
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
        while (0 < this.inventoryContents.length) {
            this.inventoryContents[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00001514";
    }
}
