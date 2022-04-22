package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ContainerBeacon extends Container
{
    private IInventory tileBeacon;
    private final BeaconSlot beaconSlot;
    private static final String __OBFID;
    
    public ContainerBeacon(final IInventory inventory, final IInventory tileBeacon) {
        this.tileBeacon = tileBeacon;
        this.addSlotToContainer(this.beaconSlot = new BeaconSlot(tileBeacon, 0, 136, 110));
        int n2 = 0;
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventory, 9, 36, 137));
                int n = 0;
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventory, 0, 36, 195));
            ++n2;
        }
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileBeacon);
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileBeacon.setField(n, n2);
    }
    
    public IInventory func_180611_e() {
        return this.tileBeacon;
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileBeacon.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(stack) && stack.stackSize == 1) {
                if (!this.mergeItemStack(stack, 0, 1, false)) {
                    return null;
                }
            }
            else if (n >= 1 && n < 28) {
                if (!this.mergeItemStack(stack, 28, 37, false)) {
                    return null;
                }
            }
            else if (n >= 28 && n < 37) {
                if (!this.mergeItemStack(stack, 1, 28, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 1, 37, false)) {
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
        __OBFID = "CL_00001735";
    }
    
    class BeaconSlot extends Slot
    {
        private static final String __OBFID;
        final ContainerBeacon this$0;
        
        public BeaconSlot(final ContainerBeacon this$0, final IInventory inventory, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(inventory, n, n2, n3);
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return itemStack != null && (itemStack.getItem() == Items.emerald || itemStack.getItem() == Items.diamond || itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot);
        }
        
        @Override
        public int getSlotStackLimit() {
            return 1;
        }
        
        static {
            __OBFID = "CL_00001736";
        }
    }
}
