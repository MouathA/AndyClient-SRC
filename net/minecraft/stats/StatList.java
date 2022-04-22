package net.minecraft.stats;

import com.google.common.collect.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class StatList
{
    protected static Map oneShotStats;
    public static List allStats;
    public static List generalStats;
    public static List itemStats;
    public static List objectMineStats;
    public static StatBase leaveGameStat;
    public static StatBase minutesPlayedStat;
    public static StatBase timeSinceDeathStat;
    public static StatBase distanceWalkedStat;
    public static StatBase distanceCrouchedStat;
    public static StatBase distanceSprintedStat;
    public static StatBase distanceSwumStat;
    public static StatBase distanceFallenStat;
    public static StatBase distanceClimbedStat;
    public static StatBase distanceFlownStat;
    public static StatBase distanceDoveStat;
    public static StatBase distanceByMinecartStat;
    public static StatBase distanceByBoatStat;
    public static StatBase distanceByPigStat;
    public static StatBase distanceByHorseStat;
    public static StatBase jumpStat;
    public static StatBase dropStat;
    public static StatBase damageDealtStat;
    public static StatBase damageTakenStat;
    public static StatBase deathsStat;
    public static StatBase mobKillsStat;
    public static StatBase animalsBredStat;
    public static StatBase playerKillsStat;
    public static StatBase fishCaughtStat;
    public static StatBase junkFishedStat;
    public static StatBase treasureFishedStat;
    public static StatBase timesTalkedToVillagerStat;
    public static StatBase timesTradedWithVillagerStat;
    public static final StatBase[] mineBlockStatArray;
    public static final StatBase[] objectCraftStats;
    public static final StatBase[] objectUseStats;
    public static final StatBase[] objectBreakStats;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001480";
        StatList.oneShotStats = Maps.newHashMap();
        StatList.allStats = Lists.newArrayList();
        StatList.generalStats = Lists.newArrayList();
        StatList.itemStats = Lists.newArrayList();
        StatList.objectMineStats = Lists.newArrayList();
        StatList.leaveGameStat = new StatBasic("stat.leaveGame", new ChatComponentTranslation("stat.leaveGame", new Object[0])).initIndependentStat().registerStat();
        StatList.minutesPlayedStat = new StatBasic("stat.playOneMinute", new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.timeSinceDeathStat = new StatBasic("stat.timeSinceDeath", new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.distanceWalkedStat = new StatBasic("stat.walkOneCm", new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceCrouchedStat = new StatBasic("stat.crouchOneCm", new ChatComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSprintedStat = new StatBasic("stat.sprintOneCm", new ChatComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSwumStat = new StatBasic("stat.swimOneCm", new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFallenStat = new StatBasic("stat.fallOneCm", new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceClimbedStat = new StatBasic("stat.climbOneCm", new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFlownStat = new StatBasic("stat.flyOneCm", new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceDoveStat = new StatBasic("stat.diveOneCm", new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByMinecartStat = new StatBasic("stat.minecartOneCm", new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByBoatStat = new StatBasic("stat.boatOneCm", new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByPigStat = new StatBasic("stat.pigOneCm", new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByHorseStat = new StatBasic("stat.horseOneCm", new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.jumpStat = new StatBasic("stat.jump", new ChatComponentTranslation("stat.jump", new Object[0])).initIndependentStat().registerStat();
        StatList.dropStat = new StatBasic("stat.drop", new ChatComponentTranslation("stat.drop", new Object[0])).initIndependentStat().registerStat();
        StatList.damageDealtStat = new StatBasic("stat.damageDealt", new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.field_111202_k).registerStat();
        StatList.damageTakenStat = new StatBasic("stat.damageTaken", new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.field_111202_k).registerStat();
        StatList.deathsStat = new StatBasic("stat.deaths", new ChatComponentTranslation("stat.deaths", new Object[0])).registerStat();
        StatList.mobKillsStat = new StatBasic("stat.mobKills", new ChatComponentTranslation("stat.mobKills", new Object[0])).registerStat();
        StatList.animalsBredStat = new StatBasic("stat.animalsBred", new ChatComponentTranslation("stat.animalsBred", new Object[0])).registerStat();
        StatList.playerKillsStat = new StatBasic("stat.playerKills", new ChatComponentTranslation("stat.playerKills", new Object[0])).registerStat();
        StatList.fishCaughtStat = new StatBasic("stat.fishCaught", new ChatComponentTranslation("stat.fishCaught", new Object[0])).registerStat();
        StatList.junkFishedStat = new StatBasic("stat.junkFished", new ChatComponentTranslation("stat.junkFished", new Object[0])).registerStat();
        StatList.treasureFishedStat = new StatBasic("stat.treasureFished", new ChatComponentTranslation("stat.treasureFished", new Object[0])).registerStat();
        StatList.timesTalkedToVillagerStat = new StatBasic("stat.talkedToVillager", new ChatComponentTranslation("stat.talkedToVillager", new Object[0])).registerStat();
        StatList.timesTradedWithVillagerStat = new StatBasic("stat.tradedWithVillager", new ChatComponentTranslation("stat.tradedWithVillager", new Object[0])).registerStat();
        mineBlockStatArray = new StatBase[4096];
        objectCraftStats = new StatBase[32000];
        objectUseStats = new StatBase[32000];
        objectBreakStats = new StatBase[32000];
    }
    
    public static void func_151178_a() {
    }
    
    private static void initCraftableStats() {
        final HashSet hashSet = Sets.newHashSet();
        for (final IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe.getRecipeOutput() != null) {
                hashSet.add(recipe.getRecipeOutput().getItem());
            }
        }
        final Iterator<ItemStack> iterator2 = FurnaceRecipes.instance().getSmeltingList().values().iterator();
        while (iterator2.hasNext()) {
            hashSet.add(iterator2.next().getItem());
        }
        for (final Item item : hashSet) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                StatList.objectCraftStats[idFromItem] = new StatCrafting("stat.craftItem.", func_180204_a, new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectCraftStats);
    }
    
    private static void func_151181_c() {
        for (final Block block : Block.blockRegistry) {
            final Item itemFromBlock = Item.getItemFromBlock(block);
            if (itemFromBlock != null) {
                final int idFromBlock = Block.getIdFromBlock(block);
                final String func_180204_a = func_180204_a(itemFromBlock);
                if (func_180204_a == null || !block.getEnableStats()) {
                    continue;
                }
                StatList.mineBlockStatArray[idFromBlock] = new StatCrafting("stat.mineBlock.", func_180204_a, new ChatComponentTranslation("stat.mineBlock", new Object[] { new ItemStack(block).getChatComponent() }), itemFromBlock).registerStat();
                StatList.objectMineStats.add(StatList.mineBlockStatArray[idFromBlock]);
            }
        }
        replaceAllSimilarBlocks(StatList.mineBlockStatArray);
    }
    
    private static void initStats() {
        for (final Item item : Item.itemRegistry) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                StatList.objectUseStats[idFromItem] = new StatCrafting("stat.useItem.", func_180204_a, new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
                if (item instanceof ItemBlock) {
                    continue;
                }
                StatList.itemStats.add(StatList.objectUseStats[idFromItem]);
            }
        }
        replaceAllSimilarBlocks(StatList.objectUseStats);
    }
    
    private static void func_151179_e() {
        for (final Item item : Item.itemRegistry) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null || !item.isDamageable()) {
                    continue;
                }
                StatList.objectBreakStats[idFromItem] = new StatCrafting("stat.breakItem.", func_180204_a, new ChatComponentTranslation("stat.breakItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectBreakStats);
    }
    
    private static String func_180204_a(final Item item) {
        final ResourceLocation resourceLocation = (ResourceLocation)Item.itemRegistry.getNameForObject(item);
        return (resourceLocation != null) ? resourceLocation.toString().replace(':', '.') : null;
    }
    
    private static void replaceAllSimilarBlocks(final StatBase[] array) {
        func_151180_a(array, Blocks.water, Blocks.flowing_water);
        func_151180_a(array, Blocks.lava, Blocks.flowing_lava);
        func_151180_a(array, Blocks.lit_pumpkin, Blocks.pumpkin);
        func_151180_a(array, Blocks.lit_furnace, Blocks.furnace);
        func_151180_a(array, Blocks.lit_redstone_ore, Blocks.redstone_ore);
        func_151180_a(array, Blocks.powered_repeater, Blocks.unpowered_repeater);
        func_151180_a(array, Blocks.powered_comparator, Blocks.unpowered_comparator);
        func_151180_a(array, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
        func_151180_a(array, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
        func_151180_a(array, Blocks.double_stone_slab, Blocks.stone_slab);
        func_151180_a(array, Blocks.double_wooden_slab, Blocks.wooden_slab);
        func_151180_a(array, Blocks.double_stone_slab2, Blocks.stone_slab2);
        func_151180_a(array, Blocks.grass, Blocks.dirt);
        func_151180_a(array, Blocks.farmland, Blocks.dirt);
    }
    
    private static void func_151180_a(final StatBase[] array, final Block block, final Block block2) {
        final int idFromBlock = Block.getIdFromBlock(block);
        final int idFromBlock2 = Block.getIdFromBlock(block2);
        if (array[idFromBlock] != null && array[idFromBlock2] == null) {
            array[idFromBlock2] = array[idFromBlock];
        }
        else {
            StatList.allStats.remove(array[idFromBlock]);
            StatList.objectMineStats.remove(array[idFromBlock]);
            StatList.generalStats.remove(array[idFromBlock]);
            array[idFromBlock] = array[idFromBlock2];
        }
    }
    
    public static StatBase func_151182_a(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        return (stringFromID == null) ? null : new StatBase("stat.killEntity." + stringFromID, new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + stringFromID + ".name", new Object[0]) })).registerStat();
    }
    
    public static StatBase func_151176_b(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        return (stringFromID == null) ? null : new StatBase("stat.entityKilledBy." + stringFromID, new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + stringFromID + ".name", new Object[0]) })).registerStat();
    }
    
    public static StatBase getOneShotStat(final String s) {
        return StatList.oneShotStats.get(s);
    }
}
