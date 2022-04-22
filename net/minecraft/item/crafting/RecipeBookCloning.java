package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipeBookCloning implements IRecipe
{
    private static final String __OBFID;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        ItemStack itemStack = null;
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.written_book) {
                    if (itemStack != null) {
                        return false;
                    }
                    itemStack = stackInSlot;
                }
                else {
                    if (stackInSlot.getItem() != Items.writable_book) {
                        return false;
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (itemStack != null) {}
        return false;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack itemStack = null;
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.written_book) {
                    if (itemStack != null) {
                        return null;
                    }
                    itemStack = stackInSlot;
                }
                else {
                    if (stackInSlot.getItem() != Items.writable_book) {
                        return null;
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (itemStack != null) {}
        return null;
    }
    
    @Override
    public int getRecipeSize() {
        return 9;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public ItemStack[] func_179532_b(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        while (0 < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem() instanceof ItemEditableBook) {
                array[0] = stackInSlot;
                break;
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    static {
        __OBFID = "CL_00000081";
    }
}
