package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockGlass extends BlockBreakable
{
    private static final String __OBFID;
    
    public BlockGlass(final Material material, final boolean b) {
        super(material, b);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    static {
        __OBFID = "CL_00000249";
    }
}
