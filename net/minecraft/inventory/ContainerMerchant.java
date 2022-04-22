package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerMerchant extends Container
{
    private IMerchant theMerchant;
    private InventoryMerchant merchantInventory;
    private final World theWorld;
    private static final String __OBFID;
    
    public ContainerMerchant(final InventoryPlayer inventoryPlayer, final IMerchant theMerchant, final World theWorld) {
        this.theMerchant = theMerchant;
        this.theWorld = theWorld;
        this.merchantInventory = new InventoryMerchant(inventoryPlayer.player, theMerchant);
        this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
        this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
        this.addSlotToContainer(new SlotMerchantResult(inventoryPlayer.player, theMerchant, this.merchantInventory, 2, 120, 53));
        int n2 = 0;
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
                int n = 0;
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 0, 8, 142));
            ++n2;
        }
    }
    
    public InventoryMerchant getMerchantInventory() {
        return this.merchantInventory;
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(inventory);
    }
    
    public void setCurrentRecipeIndex(final int currentRecipeIndex) {
        this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.theMerchant.getCustomer() == entityPlayer;
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
            else if (n != 0 && n != 1) {
                if (n >= 3 && n < 30) {
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
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.theMerchant.setCustomer(null);
        super.onContainerClosed(entityPlayer);
        if (!this.theWorld.isRemote) {
            final ItemStack stackInSlotOnClosing = this.merchantInventory.getStackInSlotOnClosing(0);
            if (stackInSlotOnClosing != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing, false);
            }
            final ItemStack stackInSlotOnClosing2 = this.merchantInventory.getStackInSlotOnClosing(1);
            if (stackInSlotOnClosing2 != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing2, false);
            }
        }
    }
    
    static {
        __OBFID = "CL_00001757";
    }
}
