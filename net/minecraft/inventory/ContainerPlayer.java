package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import Mood.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.item.crafting.*;

public class ContainerPlayer extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final String __OBFID;
    
    public ContainerPlayer(final InventoryPlayer inventoryPlayer, final boolean isLocalWorld, final EntityPlayer thePlayer) {
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = isLocalWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        int n = 0;
        int n2 = 0;
        while (0 < 2) {
            while (0 < 2) {
                this.addSlotToContainer(new Slot(this.craftMatrix, 0, 88, 26));
                ++n;
            }
            ++n2;
        }
        while (0 < 4) {
            this.addSlotToContainer(new Slot((IInventory)inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - 0, 8, 8, 0) {
                private static final String __OBFID;
                final ContainerPlayer this$0;
                private final int val$var44;
                
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }
                
                @Override
                public boolean isItemValid(final ItemStack itemStack) {
                    final Client instance = Client.INSTANCE;
                    if (Client.getModuleByName("EveryItemOnArmor").toggled) {
                        return itemStack != null;
                    }
                    return itemStack != null && ((itemStack.getItem() instanceof ItemArmor) ? (((ItemArmor)itemStack.getItem()).armorType == this.val$var44) : ((itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) || itemStack.getItem() == Items.skull) && this.val$var44 == 0));
                }
                
                @Override
                public String func_178171_c() {
                    return ItemArmor.EMPTY_SLOT_NAMES[this.val$var44];
                }
                
                static {
                    __OBFID = "CL_00001755";
                }
            });
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
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        while (0 < 4) {
            final ItemStack stackInSlotOnClosing = this.craftMatrix.getStackInSlotOnClosing(0);
            if (stackInSlotOnClosing != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing, false);
            }
            int n = 0;
            ++n;
        }
        this.craftResult.setInventorySlotContents(0, null);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n >= 1 && n < 5) {
                if (!this.mergeItemStack(stack, 9, 45, false)) {
                    return null;
                }
            }
            else if (n >= 5 && n < 9) {
                if (!this.mergeItemStack(stack, 9, 45, false)) {
                    return null;
                }
            }
            else if (copy.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)copy.getItem()).armorType)).getHasStack()) {
                final int n2 = 5 + ((ItemArmor)copy.getItem()).armorType;
                if (!this.mergeItemStack(stack, n2, n2 + 1, false)) {
                    return null;
                }
            }
            else if (n >= 9 && n < 36) {
                if (!this.mergeItemStack(stack, 36, 45, false)) {
                    return null;
                }
            }
            else if (n >= 36 && n < 45) {
                if (!this.mergeItemStack(stack, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 9, 45, false)) {
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
        __OBFID = "CL_00001754";
    }
}
