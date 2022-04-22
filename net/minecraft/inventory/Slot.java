package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class Slot
{
    private final int slotIndex;
    public final IInventory inventory;
    public int slotNumber;
    public int xDisplayPosition;
    public int yDisplayPosition;
    private static final String __OBFID;
    
    public Slot(final IInventory inventory, final int slotIndex, final int xDisplayPosition, final int yDisplayPosition) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.xDisplayPosition = xDisplayPosition;
        this.yDisplayPosition = yDisplayPosition;
    }
    
    public void onSlotChange(final ItemStack itemStack, final ItemStack itemStack2) {
        if (itemStack != null && itemStack2 != null && itemStack.getItem() == itemStack2.getItem()) {
            final int n = itemStack2.stackSize - itemStack.stackSize;
            if (n > 0) {
                this.onCrafting(itemStack, n);
            }
        }
    }
    
    protected void onCrafting(final ItemStack itemStack, final int n) {
    }
    
    protected void onCrafting(final ItemStack itemStack) {
    }
    
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onSlotChanged();
    }
    
    public boolean isItemValid(final ItemStack itemStack) {
        return true;
    }
    
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public boolean getHasStack() {
        return this.getStack() != null;
    }
    
    public void putStack(final ItemStack itemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
        this.onSlotChanged();
    }
    
    public void onSlotChanged() {
        this.inventory.markDirty();
    }
    
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
    
    public int func_178170_b(final ItemStack itemStack) {
        return this.getSlotStackLimit();
    }
    
    public String func_178171_c() {
        return null;
    }
    
    public ItemStack decrStackSize(final int n) {
        return this.inventory.decrStackSize(this.slotIndex, n);
    }
    
    public boolean isHere(final IInventory inventory, final int n) {
        return inventory == this.inventory && n == this.slotIndex;
    }
    
    public boolean canTakeStack(final EntityPlayer entityPlayer) {
        return true;
    }
    
    public boolean canBeHovered() {
        return true;
    }
    
    static {
        __OBFID = "CL_00001762";
    }
}
