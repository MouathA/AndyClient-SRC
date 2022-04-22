package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesArmor
{
    private String[][] recipePatterns;
    private Item[][] recipeItems;
    private static final String __OBFID;
    
    public RecipesArmor() {
        this.recipePatterns = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
        this.recipeItems = new Item[][] { { Items.leather, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.leather_helmet, Items.iron_helmet, Items.diamond_helmet, Items.golden_helmet }, { Items.leather_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate }, { Items.leather_leggings, Items.iron_leggings, Items.diamond_leggings, Items.golden_leggings }, { Items.leather_boots, Items.iron_boots, Items.diamond_boots, Items.golden_boots } };
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        while (0 < this.recipeItems[0].length) {
            final Item item = this.recipeItems[0][0];
            while (0 < this.recipeItems.length - 1) {
                craftingManager.addRecipe(new ItemStack(this.recipeItems[1][0]), this.recipePatterns[0], 'X', item);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    static {
        __OBFID = "CL_00000080";
    }
}
