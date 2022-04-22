package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class RecipesDyes
{
    private static final String __OBFID;
    
    public void addRecipes(final CraftingManager craftingManager) {
        int n = 0;
        while (0 < 16) {
            craftingManager.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0), new ItemStack(Items.dye, 1, 15), new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, 15), "###", "#X#", "###", '#', new ItemStack(Blocks.hardened_clay), 'X', new ItemStack(Items.dye, 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_glass, 8, 15), "###", "#X#", "###", '#', new ItemStack(Blocks.glass), 'X', new ItemStack(Items.dye, 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_glass_pane, 16, 0), "###", "###", '#', new ItemStack(Blocks.stained_glass, 1, 0));
            ++n;
        }
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeColorDamage()), new ItemStack(Blocks.yellow_flower, 1, BlockFlower.EnumFlowerType.DANDELION.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.POPPY.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.WHITE.getDyeColorDamage()), Items.bone);
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.ORANGE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIME.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.GRAY.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.SILVER.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GRAY.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.SILVER.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.CYAN.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PURPLE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PURPLE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.MAGENTA.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 4, EnumDyeColor.MAGENTA.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.BLUE_ORCHID.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.MAGENTA.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.ALLIUM.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.HOUSTONIA.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.RED_TULIP.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.ORANGE.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.ORANGE_TULIP.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.WHITE_TULIP.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.PINK_TULIP.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()), new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.YELLOW.getDyeColorDamage()), new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.SUNFLOWER.func_176936_a()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeColorDamage()), new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.SYRINGA.func_176936_a()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.RED.getDyeColorDamage()), new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.ROSE.func_176936_a()));
        craftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeColorDamage()), new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a()));
        while (0 < 16) {
            craftingManager.addRecipe(new ItemStack(Blocks.carpet, 3, 0), "##", '#', new ItemStack(Blocks.wool, 1, 0));
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00000082";
    }
}
