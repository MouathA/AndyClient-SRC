package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockRail extends BlockRailBase
{
    public static final PropertyEnum field_176565_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000293";
        field_176565_b = PropertyEnum.create("shape", EnumRailDirection.class);
    }
    
    protected BlockRail() {
        super(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRail.field_176565_b, EnumRailDirection.NORTH_SOUTH));
    }
    
    @Override
    protected void func_176561_b(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (block.canProvidePower() && new Rail(world, blockPos, blockState).countAdjacentRails() == 3) {
            this.func_176564_a(world, blockPos, blockState, false);
        }
    }
    
    @Override
    public IProperty func_176560_l() {
        return BlockRail.field_176565_b;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRail.field_176565_b, EnumRailDirection.func_177016_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumRailDirection)blockState.getValue(BlockRail.field_176565_b)).func_177015_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRail.field_176565_b });
    }
}
