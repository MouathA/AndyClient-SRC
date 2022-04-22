package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockCrops extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000222";
        AGE = PropertyInteger.create("age", 0, 7);
    }
    
    protected BlockCrops() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCrops.AGE, 0));
        this.setTickRandomly(true);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(BlockCrops.soundTypeGrass);
        this.disableStats();
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.farmland;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9) {
            final int intValue = (int)blockState.getValue(BlockCrops.AGE);
            if (intValue < 7 && random.nextInt((int)(25.0f / getGrowthChance(this, world, blockPos)) + 1) == 0) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCrops.AGE, intValue + 1), 2);
            }
        }
    }
    
    public void growCrops(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int n = (int)blockState.getValue(BlockCrops.AGE) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
        if (7 > 7) {}
        world.setBlockState(blockPos, blockState.withProperty(BlockCrops.AGE, 7), 2);
    }
    
    protected static float getGrowthChance(final Block block, final World world, final BlockPos blockPos) {
        float n = 1.0f;
        final BlockPos offsetDown = blockPos.offsetDown();
        while (-1 <= 1) {
            while (-1 <= 1) {
                float n2 = 0.0f;
                final IBlockState blockState = world.getBlockState(offsetDown.add(-1, 0, -1));
                if (blockState.getBlock() == Blocks.farmland) {
                    n2 = 1.0f;
                    if ((int)blockState.getValue(BlockFarmland.field_176531_a) > 0) {
                        n2 = 3.0f;
                    }
                }
                if (-1 != 0 || -1 != 0) {
                    n2 /= 4.0f;
                }
                n += n2;
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        final BlockPos offsetNorth = blockPos.offsetNorth();
        final BlockPos offsetSouth = blockPos.offsetSouth();
        final BlockPos offsetWest = blockPos.offsetWest();
        final BlockPos offsetEast = blockPos.offsetEast();
        final boolean b = block == world.getBlockState(offsetWest).getBlock() || block == world.getBlockState(offsetEast).getBlock();
        final boolean b2 = block == world.getBlockState(offsetNorth).getBlock() || block == world.getBlockState(offsetSouth).getBlock();
        if (b && b2) {
            n /= 2.0f;
        }
        else if (block == world.getBlockState(offsetWest.offsetNorth()).getBlock() || block == world.getBlockState(offsetEast.offsetNorth()).getBlock() || block == world.getBlockState(offsetEast.offsetSouth()).getBlock() || block == world.getBlockState(offsetWest.offsetSouth()).getBlock()) {
            n /= 2.0f;
        }
        return n;
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return (world.getLight(blockPos) >= 8 || world.isAgainstSky(blockPos)) && this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }
    
    protected Item getSeed() {
        return Items.wheat_seeds;
    }
    
    protected Item getCrop() {
        return Items.wheat;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, 0);
        if (!world.isRemote) {
            final int intValue = (int)blockState.getValue(BlockCrops.AGE);
            if (intValue >= 7) {
                while (0 < 3 + n2) {
                    if (world.rand.nextInt(15) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getSeed(), 1, 0));
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return ((int)blockState.getValue(BlockCrops.AGE) == 7) ? this.getCrop() : this.getSeed();
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.getSeed();
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockCrops.AGE) < 7;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.growCrops(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCrops.AGE, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCrops.AGE);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCrops.AGE });
    }
}
