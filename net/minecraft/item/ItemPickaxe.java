package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;

public class ItemPickaxe extends ItemTool
{
    private static final Set effectiveBlocks;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000053";
        effectiveBlocks = Sets.newHashSet(Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab);
    }
    
    protected ItemPickaxe(final ToolMaterial toolMaterial) {
        super(2.0f, toolMaterial, ItemPickaxe.effectiveBlocks);
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return (block == Blocks.obsidian) ? (this.toolMaterial.getHarvestLevel() == 3) : ((block != Blocks.diamond_block && block != Blocks.diamond_ore) ? ((block != Blocks.emerald_ore && block != Blocks.emerald_block) ? ((block != Blocks.gold_block && block != Blocks.gold_ore) ? ((block != Blocks.iron_block && block != Blocks.iron_ore) ? ((block != Blocks.lapis_block && block != Blocks.lapis_ore) ? ((block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore) ? (block.getMaterial() == Material.rock || block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2));
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return (block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock) ? super.getStrVsBlock(itemStack, block) : this.efficiencyOnProperMaterial;
    }
}
