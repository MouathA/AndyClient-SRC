package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockSnowBlock extends Block
{
    private static final String __OBFID;
    
    protected BlockSnowBlock() {
        super(Material.craftedSnow);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.snowball;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 4;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > 11) {
            this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    static {
        __OBFID = "CL_00000308";
    }
}
