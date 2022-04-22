package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerDispenser extends Container
{
    private IInventory field_178146_a;
    private static final String __OBFID;
    
    public ContainerDispenser(final IInventory inventory, final IInventory field_178146_a) {
        this.field_178146_a = field_178146_a;
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.field_178146_a.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < 9) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0, 9, false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    static {
        __OBFID = "CL_00001763";
    }
}
