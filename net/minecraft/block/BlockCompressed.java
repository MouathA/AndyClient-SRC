package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;

public class BlockCompressed extends Block
{
    private final MapColor mapColor;
    private static final String __OBFID;
    
    public BlockCompressed(final MapColor mapColor) {
        super(Material.iron);
        this.mapColor = mapColor;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return this.mapColor;
    }
    
    static {
        __OBFID = "CL_00000268";
    }
}
