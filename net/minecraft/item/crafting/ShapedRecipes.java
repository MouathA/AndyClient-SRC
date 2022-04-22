package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class ShapedRecipes implements IRecipe
{
    private final int recipeWidth;
    private final int recipeHeight;
    private final ItemStack[] recipeItems;
    private final ItemStack recipeOutput;
    private boolean field_92101_f;
    private static final String __OBFID;
    
    public ShapedRecipes(final int recipeWidth, final int recipeHeight, final ItemStack[] recipeItems, final ItemStack recipeOutput) {
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public ItemStack[] func_179532_b(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        while (0 < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        while (0 <= 3 - this.recipeWidth) {
            while (0 <= 3 - this.recipeHeight) {
                if (this.checkMatch(inventoryCrafting, 0, 0, true)) {
                    return true;
                }
                if (this.checkMatch(inventoryCrafting, 0, 0, false)) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    private boolean checkMatch(final InventoryCrafting inventoryCrafting, final int n, final int n2, final boolean b) {
        while (0 < 3) {
            while (0 < 3) {
                final int n3 = 0 - n;
                final int n4 = 0 - n2;
                ItemStack itemStack = null;
                if (n3 >= 0 && n4 >= 0 && n3 < this.recipeWidth && n4 < this.recipeHeight) {
                    if (b) {
                        itemStack = this.recipeItems[this.recipeWidth - n3 - 1 + n4 * this.recipeWidth];
                    }
                    else {
                        itemStack = this.recipeItems[n3 + n4 * this.recipeWidth];
                    }
                }
                final ItemStack stackInRowAndColumn = inventoryCrafting.getStackInRowAndColumn(0, 0);
                if (stackInRowAndColumn != null || itemStack != null) {
                    if ((stackInRowAndColumn == null && itemStack != null) || (stackInRowAndColumn != null && itemStack == null)) {
                        return false;
                    }
                    if (itemStack.getItem() != stackInRowAndColumn.getItem()) {
                        return false;
                    }
                    if (itemStack.getMetadata() != 32767 && itemStack.getMetadata() != stackInRowAndColumn.getMetadata()) {
                        return false;
                    }
                }
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
        return true;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        final ItemStack copy = this.getRecipeOutput().copy();
        if (this.field_92101_f) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.hasTagCompound()) {
                    copy.setTagCompound((NBTTagCompound)stackInSlot.getTagCompound().copy());
                }
                int n = 0;
                ++n;
            }
        }
        return copy;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
    
    public ShapedRecipes func_92100_c() {
        this.field_92101_f = true;
        return this;
    }
    
    static {
        __OBFID = "CL_00000093";
    }
}
