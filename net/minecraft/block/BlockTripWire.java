package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockTripWire extends Block
{
    public static final PropertyBool field_176293_a;
    public static final PropertyBool field_176290_b;
    public static final PropertyBool field_176294_M;
    public static final PropertyBool field_176295_N;
    public static final PropertyBool field_176296_O;
    public static final PropertyBool field_176291_P;
    public static final PropertyBool field_176289_Q;
    public static final PropertyBool field_176292_R;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000328";
        field_176293_a = PropertyBool.create("powered");
        field_176290_b = PropertyBool.create("suspended");
        field_176294_M = PropertyBool.create("attached");
        field_176295_N = PropertyBool.create("disarmed");
        field_176296_O = PropertyBool.create("north");
        field_176291_P = PropertyBool.create("east");
        field_176289_Q = PropertyBool.create("south");
        field_176292_R = PropertyBool.create("west");
    }
    
    public BlockTripWire() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTripWire.field_176293_a, false).withProperty(BlockTripWire.field_176290_b, false).withProperty(BlockTripWire.field_176294_M, false).withProperty(BlockTripWire.field_176295_N, false).withProperty(BlockTripWire.field_176296_O, false).withProperty(BlockTripWire.field_176291_P, false).withProperty(BlockTripWire.field_176289_Q, false).withProperty(BlockTripWire.field_176292_R, false));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.setTickRandomly(true);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockTripWire.field_176296_O, func_176287_c(blockAccess, blockPos, blockState, EnumFacing.NORTH)).withProperty(BlockTripWire.field_176291_P, func_176287_c(blockAccess, blockPos, blockState, EnumFacing.EAST)).withProperty(BlockTripWire.field_176289_Q, func_176287_c(blockAccess, blockPos, blockState, EnumFacing.SOUTH)).withProperty(BlockTripWire.field_176292_R, func_176287_c(blockAccess, blockPos, blockState, EnumFacing.WEST));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.string;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.string;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if ((boolean)blockState.getValue(BlockTripWire.field_176290_b) != !World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown())) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWire.field_176294_M);
        if (!(boolean)blockState.getValue(BlockTripWire.field_176290_b)) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!booleanValue) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState withProperty) {
        withProperty = withProperty.withProperty(BlockTripWire.field_176290_b, !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()));
        world.setBlockState(blockPos, withProperty, 3);
        this.func_176286_e(world, blockPos, withProperty);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176286_e(world, blockPos, blockState.withProperty(BlockTripWire.field_176293_a, true));
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            world.setBlockState(blockPos, blockState.withProperty(BlockTripWire.field_176295_N, true), 4);
        }
    }
    
    private void func_176286_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing[] array = { EnumFacing.SOUTH, EnumFacing.WEST };
        while (0 < array.length) {
            final EnumFacing enumFacing = array[0];
            while (1 < 42) {
                final BlockPos offset = blockPos.offset(enumFacing, 1);
                final IBlockState blockState2 = world.getBlockState(offset);
                if (blockState2.getBlock() == Blocks.tripwire_hook) {
                    if (blockState2.getValue(BlockTripWireHook.field_176264_a) == enumFacing.getOpposite()) {
                        Blocks.tripwire_hook.func_176260_a(world, offset, blockState2, false, true, 1, blockState);
                        break;
                    }
                    break;
                }
                else {
                    if (blockState2.getBlock() != Blocks.tripwire) {
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !(boolean)blockState.getValue(BlockTripWire.field_176293_a)) {
            this.func_176288_d(world, blockPos);
        }
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)world.getBlockState(blockPos).getValue(BlockTripWire.field_176293_a)) {
            this.func_176288_d(world, blockPos);
        }
    }
    
    private void func_176288_d(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWire.field_176293_a);
        final List entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ));
        if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
            final Iterator<Entity> iterator = entitiesWithinAABBExcludingEntity.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    break;
                }
            }
        }
        if (!booleanValue) {
            final IBlockState withProperty = blockState.withProperty(BlockTripWire.field_176293_a, true);
            world.setBlockState(blockPos, withProperty, 3);
            this.func_176286_e(world, blockPos, withProperty);
        }
        if (true) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    public static boolean func_176287_c(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offset(enumFacing));
        final Block block = blockState2.getBlock();
        if (block == Blocks.tripwire_hook) {
            return blockState2.getValue(BlockTripWireHook.field_176264_a) == enumFacing.getOpposite();
        }
        return block == Blocks.tripwire && (boolean)blockState.getValue(BlockTripWire.field_176290_b) == (boolean)blockState2.getValue(BlockTripWire.field_176290_b);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTripWire.field_176293_a, (n & 0x1) > 0).withProperty(BlockTripWire.field_176290_b, (n & 0x2) > 0).withProperty(BlockTripWire.field_176294_M, (n & 0x4) > 0).withProperty(BlockTripWire.field_176295_N, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockTripWire.field_176293_a)) {}
        if (blockState.getValue(BlockTripWire.field_176290_b)) {}
        if (blockState.getValue(BlockTripWire.field_176294_M)) {}
        if (blockState.getValue(BlockTripWire.field_176295_N)) {}
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTripWire.field_176293_a, BlockTripWire.field_176290_b, BlockTripWire.field_176294_M, BlockTripWire.field_176295_N, BlockTripWire.field_176296_O, BlockTripWire.field_176291_P, BlockTripWire.field_176292_R, BlockTripWire.field_176289_Q });
    }
}
