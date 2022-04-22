package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockObsidian extends Block
{
    private static final String __OBFID;
    
    public BlockObsidian() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.obsidianColor;
    }
    
    static {
        __OBFID = "CL_00000279";
    }
}
