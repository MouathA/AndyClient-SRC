package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class RecipesCrafting
{
    private static final String __OBFID;
    
    public void addRecipes(final CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Blocks.chest), "###", "# #", "###", '#', Blocks.planks);
        craftingManager.addRecipe(new ItemStack(Blocks.trapped_chest), "#-", '#', Blocks.chest, '-', Blocks.tripwire_hook);
        craftingManager.addRecipe(new ItemStack(Blocks.ender_chest), "###", "#E#", "###", '#', Blocks.obsidian, 'E', Items.ender_eye);
        craftingManager.addRecipe(new ItemStack(Blocks.furnace), "###", "# #", "###", '#', Blocks.cobblestone);
        craftingManager.addRecipe(new ItemStack(Blocks.crafting_table), "##", "##", '#', Blocks.planks);
        craftingManager.addRecipe(new ItemStack(Blocks.sandstone), "##", "##", '#', new ItemStack(Blocks.sand, 1, BlockSand.EnumType.SAND.func_176688_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.red_sandstone), "##", "##", '#', new ItemStack(Blocks.sand, 1, BlockSand.EnumType.RED_SAND.func_176688_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.sandstone, 4, BlockSandStone.EnumType.SMOOTH.func_176675_a()), "##", "##", '#', new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.DEFAULT.func_176675_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.red_sandstone, 4, BlockRedSandstone.EnumType.SMOOTH.getMetaFromState()), "##", "##", '#', new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.DEFAULT.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.CHISELED.func_176675_a()), "#", "#", '#', new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SAND.func_176624_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.CHISELED.getMetaFromState()), "#", "#", '#', new ItemStack(Blocks.stone_slab2, 1, BlockStoneSlabNew.EnumType.RED_SANDSTONE.func_176915_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.CHISELED.getMetaFromState()), "#", "#", '#', new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.QUARTZ.func_176624_a()));
        craftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 2, BlockQuartz.EnumType.LINES_Y.getMetaFromState()), "#", "#", '#', new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.DEFAULT.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 4), "##", "##", '#', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CHISELED_META), "#", "#", '#', new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()));
        craftingManager.addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.MOSSY_META), Blocks.stonebrick, Blocks.vine);
        craftingManager.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone, 1), Blocks.cobblestone, Blocks.vine);
        craftingManager.addRecipe(new ItemStack(Blocks.iron_bars, 16), "###", "###", '#', Items.iron_ingot);
        craftingManager.addRecipe(new ItemStack(Blocks.glass_pane, 16), "###", "###", '#', Blocks.glass);
        craftingManager.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), " R ", "RGR", " R ", 'R', Items.redstone, 'G', Blocks.glowstone);
        craftingManager.addRecipe(new ItemStack(Blocks.beacon, 1), "GGG", "GSG", "OOO", 'G', Blocks.glass, 'S', Items.nether_star, 'O', Blocks.obsidian);
        craftingManager.addRecipe(new ItemStack(Blocks.nether_brick, 1), "NN", "NN", 'N', Items.netherbrick);
        craftingManager.addRecipe(new ItemStack(Blocks.stone, 2, BlockStone.EnumType.DIORITE.getMetaFromState()), "CQ", "QC", 'C', Blocks.cobblestone, 'Q', Items.quartz);
        craftingManager.addShapelessRecipe(new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetaFromState()), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetaFromState()), Items.quartz);
        craftingManager.addShapelessRecipe(new ItemStack(Blocks.stone, 2, BlockStone.EnumType.ANDESITE.getMetaFromState()), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetaFromState()), Blocks.cobblestone);
        craftingManager.addRecipe(new ItemStack(Blocks.dirt, 4, BlockDirt.DirtType.COARSE_DIRT.getMetadata()), "DG", "GD", 'D', new ItemStack(Blocks.dirt, 1, BlockDirt.DirtType.DIRT.getMetadata()), 'G', Blocks.gravel);
        craftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.DIORITE_SMOOTH.getMetaFromState()), "SS", "SS", 'S', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.GRANITE_SMOOTH.getMetaFromState()), "SS", "SS", 'S', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.ANDESITE_SMOOTH.getMetaFromState()), "SS", "SS", 'S', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.ANDESITE.getMetaFromState()));
        craftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.ROUGHMETA), "SS", "SS", 'S', Items.prismarine_shard);
        craftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.BRICKSMETA), "SSS", "SSS", "SSS", 'S', Items.prismarine_shard);
        craftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.DARKMETA), "SSS", "SIS", "SSS", 'S', Items.prismarine_shard, 'I', new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()));
        craftingManager.addRecipe(new ItemStack(Blocks.sea_lantern, 1, 0), "SCS", "CCC", "SCS", 'S', Items.prismarine_shard, 'C', Items.prismarine_crystals);
    }
    
    static {
        __OBFID = "CL_00000095";
    }
}
