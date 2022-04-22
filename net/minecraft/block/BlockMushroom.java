package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.gen.feature.*;

public class BlockMushroom extends BlockBush implements IGrowable
{
    private static final String __OBFID;
    
    protected BlockMushroom() {
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World world, BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(25) == 0) {
            final Iterator<BlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos.add(-4, -1, -4), blockPos.add(4, 1, 4)).iterator();
            while (iterator.hasNext()) {
                if (world.getBlockState(iterator.next()).getBlock() == this) {
                    int n = 0;
                    --n;
                    if (5 <= 0) {
                        return;
                    }
                    continue;
                }
            }
            BlockPos blockPos2 = blockPos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            while (0 < 4) {
                if (world.isAirBlock(blockPos2) && this.canBlockStay(world, blockPos2, this.getDefaultState())) {
                    blockPos = blockPos2;
                }
                blockPos2 = blockPos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                int n2 = 0;
                ++n2;
            }
            if (world.isAirBlock(blockPos2) && this.canBlockStay(world, blockPos2, this.getDefaultState())) {
                world.setBlockState(blockPos2, this.getDefaultState(), 2);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos, this.getDefaultState());
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block.isFullBlock();
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            final IBlockState blockState2 = world.getBlockState(blockPos.offsetDown());
            return blockState2.getBlock() == Blocks.mycelium || (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) || (world.getLight(blockPos) < 13 && this.canPlaceBlockOn(blockState2.getBlock()));
        }
        return false;
    }
    
    public boolean func_176485_d(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        world.setBlockToAir(blockToAir);
        WorldGenBigMushroom worldGenBigMushroom = null;
        if (this == Blocks.brown_mushroom) {
            worldGenBigMushroom = new WorldGenBigMushroom(0);
        }
        else if (this == Blocks.red_mushroom) {
            worldGenBigMushroom = new WorldGenBigMushroom(1);
        }
        if (worldGenBigMushroom != null && worldGenBigMushroom.generate(world, random, blockToAir)) {
            return true;
        }
        world.setBlockState(blockToAir, blockState, 3);
        return false;
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return random.nextFloat() < 0.4;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176485_d(world, blockPos, blockState, random);
    }
    
    static {
        __OBFID = "CL_00000272";
    }
}
