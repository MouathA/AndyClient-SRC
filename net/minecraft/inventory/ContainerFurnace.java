package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.*;

public class ContainerFurnace extends Container
{
    private final IInventory tileFurnace;
    private int field_178152_f;
    private int field_178153_g;
    private int field_178154_h;
    private int field_178155_i;
    private static final String __OBFID;
    
    public ContainerFurnace(final InventoryPlayer inventoryPlayer, final IInventory tileFurnace) {
        this.tileFurnace = tileFurnace;
        this.addSlotToContainer(new Slot(tileFurnace, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceFuel(tileFurnace, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, tileFurnace, 2, 116, 35));
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileFurnace);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        while (0 < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(0);
            if (this.field_178152_f != this.tileFurnace.getField(2)) {
                crafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
            }
            if (this.field_178154_h != this.tileFurnace.getField(0)) {
                crafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
            }
            if (this.field_178155_i != this.tileFurnace.getField(1)) {
                crafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
            }
            if (this.field_178153_g != this.tileFurnace.getField(3)) {
                crafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
            }
            int n = 0;
            ++n;
        }
        this.field_178152_f = this.tileFurnace.getField(2);
        this.field_178154_h = this.tileFurnace.getField(0);
        this.field_178155_i = this.tileFurnace.getField(1);
        this.field_178153_g = this.tileFurnace.getField(3);
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileFurnace.setField(n, n2);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileFurnace.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 2) {
                if (!this.mergeItemStack(stack, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n != 1 && n != 0) {
                if (FurnaceRecipes.instance().getSmeltingResult(stack) != null) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(stack)) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return null;
                    }
                }
                else if (n >= 3 && n < 30) {
                    if (!this.mergeItemStack(stack, 30, 39, false)) {
                        return null;
                    }
                }
                else if (n >= 30 && n < 39 && !this.mergeItemStack(stack, 3, 30, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 3, 39, false)) {
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
        __OBFID = "CL_00001748";
    }
}
