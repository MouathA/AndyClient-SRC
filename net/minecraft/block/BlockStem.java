package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockStem extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE_PROP;
    public static final PropertyDirection FACING_PROP;
    private final Block cropBlock;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000316";
        AGE_PROP = PropertyInteger.create("age", 0, 7);
        FACING_PROP = PropertyDirection.create("facing", new Predicate() {
            private static final String __OBFID;
            
            public boolean apply(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.DOWN;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
            
            static {
                __OBFID = "CL_00002059";
            }
        });
    }
    
    protected BlockStem(final Block cropBlock) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStem.AGE_PROP, 0).withProperty(BlockStem.FACING_PROP, EnumFacing.UP));
        this.cropBlock = cropBlock;
        this.setTickRandomly(true);
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty(BlockStem.FACING_PROP, EnumFacing.UP);
        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            if (blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock() == this.cropBlock) {
                blockState = blockState.withProperty(BlockStem.FACING_PROP, enumFacing);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.farmland;
    }
    
    @Override
    public void updateTick(final World world, BlockPos offset, IBlockState withProperty, final Random random) {
        super.updateTick(world, offset, withProperty, random);
        if (world.getLightFromNeighbors(offset.offsetUp()) >= 9 && random.nextInt((int)(25.0f / BlockCrops.getGrowthChance(this, world, offset)) + 1) == 0) {
            final int intValue = (int)withProperty.getValue(BlockStem.AGE_PROP);
            if (intValue < 7) {
                withProperty = withProperty.withProperty(BlockStem.AGE_PROP, intValue + 1);
                world.setBlockState(offset, withProperty, 2);
            }
            else {
                final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                while (iterator.hasNext()) {
                    if (world.getBlockState(offset.offset(iterator.next())).getBlock() == this.cropBlock) {
                        return;
                    }
                }
                offset = offset.offset(EnumFacing.Plane.HORIZONTAL.random(random));
                final Block block = world.getBlockState(offset.offsetDown()).getBlock();
                if (world.getBlockState(offset).getBlock().blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass)) {
                    world.setBlockState(offset, this.cropBlock.getDefaultState());
                }
            }
        }
    }
    
    public void growStem(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty(BlockStem.AGE_PROP, Math.min(7, (int)blockState.getValue(BlockStem.AGE_PROP) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5))), 2);
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final int intValue = (int)blockState.getValue(BlockStem.AGE_PROP);
        return intValue * 32 << 16 | 255 - intValue * 8 << 8 | intValue * 4;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return this.getRenderColor(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.maxY = ((int)blockAccess.getBlockState(blockPos).getValue(BlockStem.AGE_PROP) * 2 + 2) / 16.0f;
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, (float)this.maxY, 0.5f + n);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (!world.isRemote) {
            final Item seedItem = this.getSeedItem();
            if (seedItem != null) {
                final int intValue = (int)blockState.getValue(BlockStem.AGE_PROP);
                while (0 < 3) {
                    if (world.rand.nextInt(15) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(seedItem));
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
        }
    }
    
    protected Item getSeedItem() {
        return (this.cropBlock == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.cropBlock == Blocks.melon_block) ? Items.melon_seeds : null);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final Item seedItem = this.getSeedItem();
        return (seedItem != null) ? seedItem : null;
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockStem.AGE_PROP) != 7;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.growStem(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStem.AGE_PROP, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockStem.AGE_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStem.AGE_PROP, BlockStem.FACING_PROP });
    }
}
