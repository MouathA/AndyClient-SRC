package net.minecraft.inventory;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class ContainerHorseInventory extends Container
{
    private IInventory field_111243_a;
    private EntityHorse theHorse;
    private static final String __OBFID;
    
    public ContainerHorseInventory(final IInventory inventory, final IInventory field_111243_a, final EntityHorse theHorse, final EntityPlayer entityPlayer) {
        this.field_111243_a = field_111243_a;
        this.theHorse = theHorse;
        field_111243_a.openInventory(entityPlayer);
        this.addSlotToContainer(new Slot(field_111243_a, 0, 8, 18) {
            private static final String __OBFID;
            final ContainerHorseInventory this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return super.isItemValid(itemStack) && itemStack.getItem() == Items.saddle && !this.getHasStack();
            }
            
            static {
                __OBFID = "CL_00001752";
            }
        });
        this.addSlotToContainer(new Slot(field_111243_a, 1, 8, 36, theHorse) {
            private static final String __OBFID;
            final ContainerHorseInventory this$0;
            private final EntityHorse val$p_i45791_3_;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return super.isItemValid(itemStack) && this.val$p_i45791_3_.canWearArmor() && EntityHorse.func_146085_a(itemStack.getItem());
            }
            
            @Override
            public boolean canBeHovered() {
                return this.val$p_i45791_3_.canWearArmor();
            }
            
            static {
                __OBFID = "CL_00001753";
            }
        });
        int n = 0;
        int n2 = 0;
        if (theHorse.isChested()) {
            while (0 < 3) {
                while (0 < 5) {
                    this.addSlotToContainer(new Slot(field_111243_a, 2, 80, 18));
                    ++n;
                }
                ++n2;
            }
        }
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventory, 9, 8, 84));
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventory, 0, 8, 142));
            ++n2;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.field_111243_a.isUseableByPlayer(entityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(entityPlayer) < 8.0f;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.field_111243_a.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (this.getSlot(1).isItemValid(stack) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(stack, 1, 2, false)) {
                    return null;
                }
            }
            else if (this.getSlot(0).isItemValid(stack)) {
                if (!this.mergeItemStack(stack, 0, 1, false)) {
                    return null;
                }
            }
            else if (this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(stack, 2, this.field_111243_a.getSizeInventory(), false)) {
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
        this.field_111243_a.closeInventory(entityPlayer);
    }
    
    static {
        __OBFID = "CL_00001751";
    }
}
