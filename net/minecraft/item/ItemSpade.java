package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemSpade extends ItemTool
{
    private static final Set field_150916_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000063";
        field_150916_c = Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand);
    }
    
    public ItemSpade(final ToolMaterial toolMaterial) {
        super(1.0f, toolMaterial, ItemSpade.field_150916_c);
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.snow_layer || block == Blocks.snow;
    }
}
