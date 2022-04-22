package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private World worldObj;
    private BlockPos field_178145_h;
    private static final String __OBFID;
    
    public ContainerWorkbench(final InventoryPlayer inventoryPlayer, final World worldObj, final BlockPos field_178145_h) {
        this.craftMatrix = new InventoryCrafting(this, 3, 3);
        this.craftResult = new InventoryCraftResult();
        this.worldObj = worldObj;
        this.field_178145_h = field_178145_h;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int n = 0;
        int n2 = 0;
        while (0 < 3) {
            while (0 < 3) {
                this.addSlotToContainer(new Slot(this.craftMatrix, 0, 30, 17));
                ++n;
            }
            ++n2;
        }
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 0, 8, 142));
            ++n2;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.worldObj.isRemote) {
            while (0 < 9) {
                final ItemStack stackInSlotOnClosing = this.craftMatrix.getStackInSlotOnClosing(0);
                if (stackInSlotOnClosing != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing, false);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.worldObj.getBlockState(this.field_178145_h).getBlock() == Blocks.crafting_table && entityPlayer.getDistanceSq(this.field_178145_h.getX() + 0.5, this.field_178145_h.getY() + 0.5, this.field_178145_h.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 10, 46, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n >= 10 && n < 37) {
                if (!this.mergeItemStack(stack, 37, 46, false)) {
                    return null;
                }
            }
            else if (n >= 37 && n < 46) {
                if (!this.mergeItemStack(stack, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 10, 46, false)) {
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
    
    @Override
    public boolean func_94530_a(final ItemStack itemStack, final Slot slot) {
        return slot.inventory != this.craftResult && super.func_94530_a(itemStack, slot);
    }
    
    static {
        __OBFID = "CL_00001744";
    }
}
