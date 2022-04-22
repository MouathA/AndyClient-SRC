package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;

public class BlockPotato extends BlockCrops
{
    private static final String __OBFID;
    
    @Override
    protected Item getSeed() {
        return Items.potato;
    }
    
    @Override
    protected Item getCrop() {
        return Items.potato;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (!world.isRemote && (int)blockState.getValue(BlockPotato.AGE) >= 7 && world.rand.nextInt(50) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.poisonous_potato));
        }
    }
    
    static {
        __OBFID = "CL_00000286";
    }
}
