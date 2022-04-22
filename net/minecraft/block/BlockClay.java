package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockClay extends Block
{
    private static final String __OBFID;
    
    public BlockClay() {
        super(Material.clay);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.clay_ball;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 4;
    }
    
    static {
        __OBFID = "CL_00000215";
    }
}
