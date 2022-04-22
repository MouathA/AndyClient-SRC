package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockRailDetector extends BlockRailBase
{
    public static final PropertyEnum field_176573_b;
    public static final PropertyBool field_176574_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000225";
        field_176573_b = PropertyEnum.create("shape", EnumRailDirection.class, new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180344_a(final EnumRailDirection enumRailDirection) {
                return enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180344_a((EnumRailDirection)o);
            }
            
            static {
                __OBFID = "CL_00002126";
            }
        });
        field_176574_M = PropertyBool.create("powered");
    }
    
    public BlockRailDetector() {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRailDetector.field_176574_M, false).withProperty(BlockRailDetector.field_176573_b, EnumRailDirection.NORTH_SOUTH));
        this.setTickRandomly(true);
    }
    
    @Override
    public int tickRate(final World world) {
        return 20;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !(boolean)blockState.getValue(BlockRailDetector.field_176574_M)) {
            this.func_176570_e(world, blockPos, blockState);
        }
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockRailDetector.field_176574_M)) {
            this.func_176570_e(world, blockPos, blockState);
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockRailDetector.field_176574_M) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockRailDetector.field_176574_M) ? ((enumFacing == EnumFacing.UP) ? 15 : 0) : 0;
    }
    
    private void func_176570_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = (boolean)blockState.getValue(BlockRailDetector.field_176574_M);
        if (!this.func_176571_a(world, blockPos, EntityMinecart.class, new Predicate[0]).isEmpty()) {}
        if (true && !booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockRailDetector.field_176574_M, true), 3);
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (!true && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockRailDetector.field_176574_M, false), 3);
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (true) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
        world.updateComparatorOutputLevel(blockPos, this);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.func_176570_e(world, blockPos, blockState);
    }
    
    @Override
    public IProperty func_176560_l() {
        return BlockRailDetector.field_176573_b;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getValue(BlockRailDetector.field_176574_M)) {
            final List func_176571_a = this.func_176571_a(world, blockPos, EntityMinecartCommandBlock.class, new Predicate[0]);
            if (!func_176571_a.isEmpty()) {
                return func_176571_a.get(0).func_145822_e().getSuccessCount();
            }
            final List func_176571_a2 = this.func_176571_a(world, blockPos, EntityMinecart.class, IEntitySelector.selectInventories);
            if (!func_176571_a2.isEmpty()) {
                return Container.calcRedstoneFromInventory(func_176571_a2.get(0));
            }
        }
        return 0;
    }
    
    protected List func_176571_a(final World world, final BlockPos blockPos, final Class clazz, final Predicate... array) {
        final AxisAlignedBB func_176572_a = this.func_176572_a(blockPos);
        return (array.length != 1) ? world.getEntitiesWithinAABB(clazz, func_176572_a) : world.func_175647_a(clazz, func_176572_a, array[0]);
    }
    
    private AxisAlignedBB func_176572_a(final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + 0.2f, blockPos.getY(), blockPos.getZ() + 0.2f, blockPos.getX() + 1 - 0.2f, blockPos.getY() + 1 - 0.2f, blockPos.getZ() + 1 - 0.2f);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRailDetector.field_176573_b, EnumRailDirection.func_177016_a(n & 0x7)).withProperty(BlockRailDetector.field_176574_M, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumRailDirection)blockState.getValue(BlockRailDetector.field_176573_b)).func_177015_a();
        if (blockState.getValue(BlockRailDetector.field_176574_M)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRailDetector.field_176573_b, BlockRailDetector.field_176574_M });
    }
}
