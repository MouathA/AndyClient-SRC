package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesTools
{
    private String[][] recipePatterns;
    private Object[][] recipeItems;
    private static final String __OBFID;
    
    public RecipesTools() {
        this.recipePatterns = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
        this.recipeItems = new Object[][] { { Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.wooden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.diamond_pickaxe, Items.golden_pickaxe }, { Items.wooden_shovel, Items.stone_shovel, Items.iron_shovel, Items.diamond_shovel, Items.golden_shovel }, { Items.wooden_axe, Items.stone_axe, Items.iron_axe, Items.diamond_axe, Items.golden_axe }, { Items.wooden_hoe, Items.stone_hoe, Items.iron_hoe, Items.diamond_hoe, Items.golden_hoe } };
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        while (0 < this.recipeItems[0].length) {
            final Object o = this.recipeItems[0][0];
            while (0 < this.recipeItems.length - 1) {
                craftingManager.addRecipe(new ItemStack((Item)this.recipeItems[1][0]), this.recipePatterns[0], '#', Items.stick, 'X', o);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        craftingManager.addRecipe(new ItemStack(Items.shears), " #", "# ", '#', Items.iron_ingot);
    }
    
    static {
        __OBFID = "CL_00000096";
    }
}
