package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;

public class BlockNetherBrick extends Block
{
    private static final String __OBFID;
    
    public BlockNetherBrick() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.netherrackColor;
    }
    
    static {
        __OBFID = "CL_00002091";
    }
}
