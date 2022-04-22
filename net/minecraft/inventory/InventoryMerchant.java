package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.village.*;

public class InventoryMerchant implements IInventory
{
    private final IMerchant theMerchant;
    private ItemStack[] theInventory;
    private final EntityPlayer thePlayer;
    private MerchantRecipe currentRecipe;
    private int currentRecipeIndex;
    private static final String __OBFID;
    
    public InventoryMerchant(final EntityPlayer thePlayer, final IMerchant theMerchant) {
        this.theInventory = new ItemStack[3];
        this.thePlayer = thePlayer;
        this.theMerchant = theMerchant;
    }
    
    @Override
    public int getSizeInventory() {
        return this.theInventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.theInventory[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.theInventory[n] == null) {
            return null;
        }
        if (n == 2) {
            final ItemStack itemStack = this.theInventory[n];
            this.theInventory[n] = null;
            return itemStack;
        }
        if (this.theInventory[n].stackSize <= n2) {
            final ItemStack itemStack2 = this.theInventory[n];
            this.theInventory[n] = null;
            if (this.inventoryResetNeededOnSlotChange(n)) {
                this.resetRecipeAndSlots();
            }
            return itemStack2;
        }
        final ItemStack splitStack = this.theInventory[n].splitStack(n2);
        if (this.theInventory[n].stackSize == 0) {
            this.theInventory[n] = null;
        }
        if (this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
        return splitStack;
    }
    
    private boolean inventoryResetNeededOnSlotChange(final int n) {
        return n == 0 || n == 1;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.theInventory[n] != null) {
            final ItemStack itemStack = this.theInventory[n];
            this.theInventory[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.theInventory[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
    }
    
    @Override
    public String getName() {
        return "mob.villager";
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.theMerchant.getCustomer() == entityPlayer;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public void markDirty() {
        this.resetRecipeAndSlots();
    }
    
    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack itemStack = this.theInventory[0];
        ItemStack itemStack2 = this.theInventory[1];
        if (itemStack == null) {
            itemStack = itemStack2;
            itemStack2 = null;
        }
        if (itemStack == null) {
            this.setInventorySlotContents(2, null);
        }
        else {
            final MerchantRecipeList recipes = this.theMerchant.getRecipes(this.thePlayer);
            if (recipes != null) {
                final MerchantRecipe canRecipeBeUsed = recipes.canRecipeBeUsed(itemStack, itemStack2, this.currentRecipeIndex);
                if (canRecipeBeUsed != null && !canRecipeBeUsed.isRecipeDisabled()) {
                    this.currentRecipe = canRecipeBeUsed;
                    this.setInventorySlotContents(2, canRecipeBeUsed.getItemToSell().copy());
                }
                else if (itemStack2 != null) {
                    final MerchantRecipe canRecipeBeUsed2 = recipes.canRecipeBeUsed(itemStack2, itemStack, this.currentRecipeIndex);
                    if (canRecipeBeUsed2 != null && !canRecipeBeUsed2.isRecipeDisabled()) {
                        this.currentRecipe = canRecipeBeUsed2;
                        this.setInventorySlotContents(2, canRecipeBeUsed2.getItemToSell().copy());
                    }
                    else {
                        this.setInventorySlotContents(2, null);
                    }
                }
                else {
                    this.setInventorySlotContents(2, null);
                }
            }
        }
        this.theMerchant.verifySellingItem(this.getStackInSlot(2));
    }
    
    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }
    
    public void setCurrentRecipeIndex(final int currentRecipeIndex) {
        this.currentRecipeIndex = currentRecipeIndex;
        this.resetRecipeAndSlots();
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public void clearInventory() {
        while (0 < this.theInventory.length) {
            this.theInventory[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00001756";
    }
}
