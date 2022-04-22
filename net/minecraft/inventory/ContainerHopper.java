package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerHopper extends Container
{
    private final IInventory field_94538_a;
    private static final String __OBFID;
    
    public ContainerHopper(final InventoryPlayer inventoryPlayer, final IInventory field_94538_a, final EntityPlayer entityPlayer) {
        (this.field_94538_a = field_94538_a).openInventory(entityPlayer);
        while (0 < field_94538_a.getSizeInventory()) {
            this.addSlotToContainer(new Slot(field_94538_a, 0, 44, 20));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.field_94538_a.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.field_94538_a.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0, this.field_94538_a.getSizeInventory(), false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.field_94538_a.closeInventory(entityPlayer);
    }
    
    static {
        __OBFID = "CL_00001750";
    }
}
