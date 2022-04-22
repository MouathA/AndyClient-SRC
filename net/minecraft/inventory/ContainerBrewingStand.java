package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class ContainerBrewingStand extends Container
{
    private IInventory tileBrewingStand;
    private final Slot theSlot;
    private int brewTime;
    private static final String __OBFID;
    
    public ContainerBrewingStand(final InventoryPlayer inventoryPlayer, final IInventory tileBrewingStand) {
        this.tileBrewingStand = tileBrewingStand;
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, 0, 56, 46));
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, 1, 79, 53));
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new Ingredient(tileBrewingStand, 3, 79, 17));
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileBrewingStand);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        while (0 < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(0);
            if (this.brewTime != this.tileBrewingStand.getField(0)) {
                crafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
            }
            int n = 0;
            ++n;
        }
        this.brewTime = this.tileBrewingStand.getField(0);
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileBrewingStand.setField(n, n2);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileBrewingStand.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if ((n < 0 || n > 2) && n != 3) {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(stack)) {
                    if (!this.mergeItemStack(stack, 3, 4, false)) {
                        return null;
                    }
                }
                else if (Potion.canHoldPotion(copy)) {
                    if (!this.mergeItemStack(stack, 0, 3, false)) {
                        return null;
                    }
                }
                else if (n >= 4 && n < 31) {
                    if (!this.mergeItemStack(stack, 31, 40, false)) {
                        return null;
                    }
                }
                else if (n >= 31 && n < 40) {
                    if (!this.mergeItemStack(stack, 4, 31, false)) {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(stack, 4, 40, false)) {
                    return null;
                }
            }
            else {
                if (!this.mergeItemStack(stack, 4, 40, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
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
        __OBFID = "CL_00001737";
    }
    
    class Ingredient extends Slot
    {
        private static final String __OBFID;
        final ContainerBrewingStand this$0;
        
        public Ingredient(final ContainerBrewingStand this$0, final IInventory inventory, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(inventory, n, n2, n3);
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return itemStack != null && itemStack.getItem().isPotionIngredient(itemStack);
        }
        
        @Override
        public int getSlotStackLimit() {
            return 64;
        }
        
        static {
            __OBFID = "CL_00001738";
        }
    }
    
    static class Potion extends Slot
    {
        private EntityPlayer player;
        private static final String __OBFID;
        
        public Potion(final EntityPlayer player, final IInventory inventory, final int n, final int n2, final int n3) {
            super(inventory, n, n2, n3);
            this.player = player;
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return canHoldPotion(itemStack);
        }
        
        @Override
        public int getSlotStackLimit() {
            return 1;
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
            if (itemStack.getItem() == Items.potionitem && itemStack.getMetadata() > 0) {
                this.player.triggerAchievement(AchievementList.potion);
            }
            super.onPickupFromSlot(entityPlayer, itemStack);
        }
        
        public static boolean canHoldPotion(final ItemStack itemStack) {
            return itemStack != null && (itemStack.getItem() == Items.potionitem || itemStack.getItem() == Items.glass_bottle);
        }
        
        static {
            __OBFID = "CL_00001740";
        }
    }
}
