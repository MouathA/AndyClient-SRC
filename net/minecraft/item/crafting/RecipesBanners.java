package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;

public class RecipesBanners
{
    private static final String __OBFID;
    
    void func_179534_a(final CraftingManager craftingManager) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            final EnumDyeColor enumDyeColor = values[0];
            craftingManager.addRecipe(new ItemStack(Items.banner, 1, enumDyeColor.getDyeColorDamage()), "###", "###", " | ", '#', new ItemStack(Blocks.wool, 1, enumDyeColor.func_176765_a()), '|', Items.stick);
            int n = 0;
            ++n;
        }
        craftingManager.func_180302_a(new RecipeDuplicatePattern(null));
        craftingManager.func_180302_a(new RecipeAddPattern(null));
    }
    
    static {
        __OBFID = "CL_00002160";
    }
    
    static class RecipeAddPattern implements IRecipe
    {
        private static final String __OBFID;
        
        private RecipeAddPattern() {
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    if (true) {
                        return false;
                    }
                    if (TileEntityBanner.func_175113_c(stackInSlot) >= 6) {
                        return false;
                    }
                }
                int n = 0;
                ++n;
            }
            return true && this.func_179533_c(inventoryCrafting) != null;
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            ItemStack copy = null;
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    copy = stackInSlot.copy();
                    copy.stackSize = 1;
                    break;
                }
                int n = 0;
                ++n;
            }
            final TileEntityBanner.EnumBannerPattern func_179533_c = this.func_179533_c(inventoryCrafting);
            if (func_179533_c != null) {
                while (0 < inventoryCrafting.getSizeInventory()) {
                    final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(0);
                    if (stackInSlot2 != null && stackInSlot2.getItem() == Items.dye) {
                        stackInSlot2.getMetadata();
                        break;
                    }
                    int n2 = 0;
                    ++n2;
                }
                final NBTTagCompound subCompound = copy.getSubCompound("BlockEntityTag", true);
                NBTTagList tagList;
                if (subCompound.hasKey("Patterns", 9)) {
                    tagList = subCompound.getTagList("Patterns", 10);
                }
                else {
                    tagList = new NBTTagList();
                    subCompound.setTag("Patterns", tagList);
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Pattern", func_179533_c.func_177273_b());
                nbtTagCompound.setInteger("Color", 0);
                tagList.appendTag(nbtTagCompound);
            }
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
        
        private TileEntityBanner.EnumBannerPattern func_179533_c(final InventoryCrafting inventoryCrafting) {
            final TileEntityBanner.EnumBannerPattern[] values = TileEntityBanner.EnumBannerPattern.values();
            while (0 < values.length) {
                final TileEntityBanner.EnumBannerPattern enumBannerPattern = values[0];
                if (enumBannerPattern.func_177270_d()) {
                    if (enumBannerPattern.func_177269_e()) {
                        while (0 < inventoryCrafting.getSizeInventory() && false) {
                            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                            if (stackInSlot != null && stackInSlot.getItem() != Items.banner) {
                                if (stackInSlot.getItem() == Items.dye) {
                                    if (false) {
                                        break;
                                    }
                                }
                                else if (-1 != 0 || !stackInSlot.isItemEqual(enumBannerPattern.func_177272_f())) {
                                    break;
                                }
                            }
                            int n = 0;
                            ++n;
                        }
                        if (-1 == 0) {}
                    }
                    else if (inventoryCrafting.getSizeInventory() == enumBannerPattern.func_177267_c().length * enumBannerPattern.func_177267_c()[0].length()) {
                        while (0 < inventoryCrafting.getSizeInventory() && false) {
                            final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(0);
                            if (stackInSlot2 != null && stackInSlot2.getItem() != Items.banner) {
                                if (stackInSlot2.getItem() != Items.dye) {
                                    break;
                                }
                                if (-1 != -1 && -1 != stackInSlot2.getMetadata()) {
                                    break;
                                }
                                if (enumBannerPattern.func_177267_c()[0].charAt(0) == ' ') {
                                    break;
                                }
                                stackInSlot2.getMetadata();
                            }
                            else if (enumBannerPattern.func_177267_c()[0].charAt(0) != ' ') {
                                break;
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    if (false) {
                        return enumBannerPattern;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            return null;
        }
        
        RecipeAddPattern(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00002158";
        }
    }
    
    static class RecipeDuplicatePattern implements IRecipe
    {
        private static final String __OBFID;
        
        private RecipeDuplicatePattern() {
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            ItemStack itemStack = null;
            ItemStack itemStack2 = null;
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem() != Items.banner) {
                        return false;
                    }
                    if (itemStack != null && itemStack2 != null) {
                        return false;
                    }
                    final int baseColor = TileEntityBanner.getBaseColor(stackInSlot);
                    final boolean b = TileEntityBanner.func_175113_c(stackInSlot) > 0;
                    if (itemStack != null) {
                        if (b) {
                            return false;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack)) {
                            return false;
                        }
                        itemStack2 = stackInSlot;
                    }
                    else if (itemStack2 != null) {
                        if (!b) {
                            return false;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack2)) {
                            return false;
                        }
                        itemStack = stackInSlot;
                    }
                    else if (b) {
                        itemStack = stackInSlot;
                    }
                    else {
                        itemStack2 = stackInSlot;
                    }
                }
                int n = 0;
                ++n;
            }
            return itemStack != null && itemStack2 != null;
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && TileEntityBanner.func_175113_c(stackInSlot) > 0) {
                    final ItemStack copy = stackInSlot.copy();
                    copy.stackSize = 1;
                    return copy;
                }
                int n = 0;
                ++n;
            }
            return null;
        }
        
        @Override
        public int getRecipeSize() {
            return 2;
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
                if (stackInSlot != null) {
                    if (stackInSlot.getItem().hasContainerItem()) {
                        array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
                    }
                    else if (stackInSlot.hasTagCompound() && TileEntityBanner.func_175113_c(stackInSlot) > 0) {
                        array[0] = stackInSlot.copy();
                        array[0].stackSize = 1;
                    }
                }
                int n = 0;
                ++n;
            }
            return array;
        }
        
        RecipeDuplicatePattern(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00002157";
        }
    }
}
