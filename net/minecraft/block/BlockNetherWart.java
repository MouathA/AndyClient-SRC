package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockNetherWart extends BlockBush
{
    public static final PropertyInteger AGE_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000274";
        AGE_PROP = PropertyInteger.create("age", 0, 3);
    }
    
    protected BlockNetherWart() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNetherWart.AGE_PROP, 0));
        this.setTickRandomly(true);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.soul_sand;
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, IBlockState withProperty, final Random random) {
        final int intValue = (int)withProperty.getValue(BlockNetherWart.AGE_PROP);
        if (intValue < 3 && random.nextInt(10) == 0) {
            withProperty = withProperty.withProperty(BlockNetherWart.AGE_PROP, intValue + 1);
            world.setBlockState(blockPos, withProperty, 2);
        }
        super.updateTick(world, blockPos, withProperty, random);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (world.isRemote || (int)blockState.getValue(BlockNetherWart.AGE_PROP) < 3) {
            return;
        }
        final int n3 = 2 + world.rand.nextInt(3);
        if (n2 > 0) {
            final int n4 = 1 + world.rand.nextInt(n2 + 1);
            goto Label_0058;
        }
        goto Label_0058;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.nether_wart;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockNetherWart.AGE_PROP, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockNetherWart.AGE_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNetherWart.AGE_PROP });
    }
}
