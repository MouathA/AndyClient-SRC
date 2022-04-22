package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockCompressedPowered extends BlockCompressed
{
    private static final String __OBFID;
    
    public BlockCompressedPowered(final MapColor mapColor) {
        super(mapColor);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return 15;
    }
    
    static {
        __OBFID = "CL_00000287";
    }
}
