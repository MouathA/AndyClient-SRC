package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;

public class RecipeRepairItem implements IRecipe
{
    private static final String __OBFID;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                arrayList.add(stackInSlot);
                if (arrayList.size() > 1) {
                    final ItemStack itemStack = arrayList.get(0);
                    if (stackInSlot.getItem() != itemStack.getItem() || itemStack.stackSize != 1 || stackInSlot.stackSize != 1 || !itemStack.getItem().isDamageable()) {
                        return false;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        return arrayList.size() == 2;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                arrayList.add(stackInSlot);
                if (arrayList.size() > 1) {
                    final ItemStack itemStack = arrayList.get(0);
                    if (stackInSlot.getItem() != itemStack.getItem() || itemStack.stackSize != 1 || stackInSlot.stackSize != 1 || !itemStack.getItem().isDamageable()) {
                        return null;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        if (arrayList.size() == 2) {
            final ItemStack itemStack2 = arrayList.get(0);
            final ItemStack itemStack3 = arrayList.get(1);
            if (itemStack2.getItem() == itemStack3.getItem() && itemStack2.stackSize == 1 && itemStack3.stackSize == 1 && itemStack2.getItem().isDamageable()) {
                final Item item = itemStack2.getItem();
                final int n2 = item.getMaxDamage() - (item.getMaxDamage() - itemStack2.getItemDamage() + (item.getMaxDamage() - itemStack3.getItemDamage()) + item.getMaxDamage() * 5 / 100);
                if (0 < 0) {}
                return new ItemStack(itemStack2.getItem(), 1, 0);
            }
        }
        return null;
    }
    
    @Override
    public int getRecipeSize() {
        return 4;
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
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    static {
        __OBFID = "CL_00002156";
    }
}
