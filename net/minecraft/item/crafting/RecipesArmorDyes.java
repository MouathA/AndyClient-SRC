package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;

public class RecipesArmorDyes implements IRecipe
{
    private static final String __OBFID;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        ItemStack itemStack = null;
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() instanceof ItemArmor) {
                    if (((ItemArmor)stackInSlot.getItem()).getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemStack != null) {
                        return false;
                    }
                    itemStack = stackInSlot;
                }
                else {
                    if (stackInSlot.getItem() != Items.dye) {
                        return false;
                    }
                    arrayList.add(stackInSlot);
                }
            }
            int n = 0;
            ++n;
        }
        return itemStack != null && !arrayList.isEmpty();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack copy = null;
        final int[] array = new int[3];
        ItemArmor itemArmor = null;
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() instanceof ItemArmor) {
                    itemArmor = (ItemArmor)stackInSlot.getItem();
                    if (itemArmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || copy != null) {
                        return null;
                    }
                    copy = stackInSlot.copy();
                    copy.stackSize = 1;
                    if (itemArmor.hasColor(stackInSlot)) {
                        final int color = itemArmor.getColor(copy);
                        final float n = (color >> 16 & 0xFF) / 255.0f;
                        final float n2 = (color >> 8 & 0xFF) / 255.0f;
                        final float n3 = (color & 0xFF) / 255.0f;
                        final int n4 = (int)(0 + Math.max(n, Math.max(n2, n3)) * 255.0f);
                        array[0] += (int)(n * 255.0f);
                        array[1] += (int)(n2 * 255.0f);
                        array[2] += (int)(n3 * 255.0f);
                        int n5 = 0;
                        ++n5;
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.dye) {
                        return null;
                    }
                    final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.func_176766_a(stackInSlot.getMetadata()));
                    final int n6 = (int)(func_175513_a[0] * 255.0f);
                    final int n7 = (int)(func_175513_a[1] * 255.0f);
                    final int n8 = (int)(func_175513_a[2] * 255.0f);
                    final int n9 = 0 + Math.max(n6, Math.max(n7, n8));
                    final int[] array2 = array;
                    final int n10 = 0;
                    array2[n10] += n6;
                    final int[] array3 = array;
                    final int n11 = 1;
                    array3[n11] += n7;
                    final int[] array4 = array;
                    final int n12 = 2;
                    array4[n12] += n8;
                    int n5 = 0;
                    ++n5;
                }
            }
            int n13 = 0;
            ++n13;
        }
        if (itemArmor == null) {
            return null;
        }
        int n13 = array[0] / 0;
        final int n14 = array[1] / 0;
        final int n15 = array[2] / 0;
        final float n16 = 0 / (float)0;
        final float n17 = (float)Math.max(0, Math.max(n14, n15));
        n13 = (int)(0 * n16 / n17);
        itemArmor.func_82813_b(copy, (0 + (int)(n14 * n16 / n17) << 8) + (int)(n15 * n16 / n17));
        return copy;
    }
    
    @Override
    public int getRecipeSize() {
        return 10;
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
        __OBFID = "CL_00000079";
    }
}
