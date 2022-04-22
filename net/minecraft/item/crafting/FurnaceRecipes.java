package net.minecraft.item.crafting;

import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.*;

public class FurnaceRecipes
{
    private static final FurnaceRecipes smeltingBase;
    private Map smeltingList;
    private Map experienceList;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000085";
        smeltingBase = new FurnaceRecipes();
    }
    
    public static FurnaceRecipes instance() {
        return FurnaceRecipes.smeltingBase;
    }
    
    private FurnaceRecipes() {
        this.smeltingList = Maps.newHashMap();
        this.experienceList = Maps.newHashMap();
        this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1f);
        this.addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35f);
        this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35f);
        this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35f);
        this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35f);
        this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1f);
        this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
        this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0f);
        this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15f);
        final ItemFishFood.FishType[] values = ItemFishFood.FishType.values();
        while (0 < values.length) {
            final ItemFishFood.FishType fishType = values[0];
            if (fishType.getCookable()) {
                this.addSmeltingRecipe(new ItemStack(Items.fish, 1, fishType.getItemDamage()), new ItemStack(Items.cooked_fish, 1, fishType.getItemDamage()), 0.35f);
            }
            int n = 0;
            ++n;
        }
        this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1f);
        this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2f);
    }
    
    public void addSmeltingRecipeForBlock(final Block block, final ItemStack itemStack, final float n) {
        this.addSmelting(Item.getItemFromBlock(block), itemStack, n);
    }
    
    public void addSmelting(final Item item, final ItemStack itemStack, final float n) {
        this.addSmeltingRecipe(new ItemStack(item, 1, 32767), itemStack, n);
    }
    
    public void addSmeltingRecipe(final ItemStack itemStack, final ItemStack itemStack2, final float n) {
        this.smeltingList.put(itemStack, itemStack2);
        this.experienceList.put(itemStack2, n);
    }
    
    public ItemStack getSmeltingResult(final ItemStack itemStack) {
        for (final Map.Entry<ItemStack, V> entry : this.smeltingList.entrySet()) {
            if (this.func_151397_a(itemStack, entry.getKey())) {
                return (ItemStack)entry.getValue();
            }
        }
        return null;
    }
    
    private boolean func_151397_a(final ItemStack itemStack, final ItemStack itemStack2) {
        return itemStack2.getItem() == itemStack.getItem() && (itemStack2.getMetadata() == 32767 || itemStack2.getMetadata() == itemStack.getMetadata());
    }
    
    public Map getSmeltingList() {
        return this.smeltingList;
    }
    
    public float getSmeltingExperience(final ItemStack itemStack) {
        for (final Map.Entry<ItemStack, V> entry : this.experienceList.entrySet()) {
            if (this.func_151397_a(itemStack, entry.getKey())) {
                return (float)entry.getValue();
            }
        }
        return 0.0f;
    }
}
