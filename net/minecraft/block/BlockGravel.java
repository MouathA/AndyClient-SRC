package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockGravel extends BlockFalling
{
    private static final String __OBFID;
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        if (3 > 3) {}
        return (random.nextInt(1) == 0) ? Items.flint : Item.getItemFromBlock(this);
    }
    
    static {
        __OBFID = "CL_00000252";
    }
}
