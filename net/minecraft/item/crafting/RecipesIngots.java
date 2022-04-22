package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class RecipesIngots
{
    private Object[][] recipeItems;
    private static final String __OBFID;
    
    public RecipesIngots() {
        this.recipeItems = new Object[][] { { Blocks.gold_block, new ItemStack(Items.gold_ingot, 9) }, { Blocks.iron_block, new ItemStack(Items.iron_ingot, 9) }, { Blocks.diamond_block, new ItemStack(Items.diamond, 9) }, { Blocks.emerald_block, new ItemStack(Items.emerald, 9) }, { Blocks.lapis_block, new ItemStack(Items.dye, 9, EnumDyeColor.BLUE.getDyeColorDamage()) }, { Blocks.redstone_block, new ItemStack(Items.redstone, 9) }, { Blocks.coal_block, new ItemStack(Items.coal, 9, 0) }, { Blocks.hay_block, new ItemStack(Items.wheat, 9) }, { Blocks.slime_block, new ItemStack(Items.slime_ball, 9) } };
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        while (0 < this.recipeItems.length) {
            final Block block = (Block)this.recipeItems[0][0];
            final ItemStack itemStack = (ItemStack)this.recipeItems[0][1];
            craftingManager.addRecipe(new ItemStack(block), "###", "###", "###", '#', itemStack);
            craftingManager.addRecipe(itemStack, "#", '#', block);
            int n = 0;
            ++n;
        }
        craftingManager.addRecipe(new ItemStack(Items.gold_ingot), "###", "###", "###", '#', Items.gold_nugget);
        craftingManager.addRecipe(new ItemStack(Items.gold_nugget, 9), "#", '#', Items.gold_ingot);
    }
    
    static {
        __OBFID = "CL_00000089";
    }
}
