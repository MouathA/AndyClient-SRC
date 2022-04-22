package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;

public class RecipesMapExtending extends ShapedRecipes
{
    private static final String __OBFID;
    
    public RecipesMapExtending() {
        super(3, 3, new ItemStack[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper) }, new ItemStack(Items.map, 0, 0));
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        if (!super.matches(inventoryCrafting, world)) {
            return false;
        }
        ItemStack itemStack = null;
        while (0 < inventoryCrafting.getSizeInventory() && itemStack == null) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem() == Items.filled_map) {
                itemStack = stackInSlot;
            }
            int n = 0;
            ++n;
        }
        if (itemStack == null) {
            return false;
        }
        final MapData mapData = Items.filled_map.getMapData(itemStack, world);
        return mapData != null && mapData.scale < 4;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack itemStack = null;
        while (0 < inventoryCrafting.getSizeInventory() && itemStack == null) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem() == Items.filled_map) {
                itemStack = stackInSlot;
            }
            int n = 0;
            ++n;
        }
        final ItemStack copy = itemStack.copy();
        copy.stackSize = 1;
        if (copy.getTagCompound() == null) {
            copy.setTagCompound(new NBTTagCompound());
        }
        copy.getTagCompound().setBoolean("map_is_scaling", true);
        return copy;
    }
    
    static {
        __OBFID = "CL_00000088";
    }
}
