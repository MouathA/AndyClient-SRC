package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesWeapons
{
    private String[][] recipePatterns;
    private Object[][] recipeItems;
    private static final String __OBFID;
    
    public RecipesWeapons() {
        this.recipePatterns = new String[][] { { "X", "X", "#" } };
        this.recipeItems = new Object[][] { { Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword, Items.golden_sword } };
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
        craftingManager.addRecipe(new ItemStack(Items.bow, 1), " #X", "# X", " #X", 'X', Items.string, '#', Items.stick);
        craftingManager.addRecipe(new ItemStack(Items.arrow, 4), "X", "#", "Y", 'Y', Items.feather, 'X', Items.flint, '#', Items.stick);
    }
    
    static {
        __OBFID = "CL_00000097";
    }
}
