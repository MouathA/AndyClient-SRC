package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockStairs extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyEnum HALF;
    public static final PropertyEnum SHAPE;
    private static final int[][] field_150150_a;
    private final Block modelBlock;
    private final IBlockState modelState;
    private boolean field_150152_N;
    private int field_150153_O;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000314";
        FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        HALF = PropertyEnum.create("half", EnumHalf.class);
        SHAPE = PropertyEnum.create("shape", EnumShape.class);
        field_150150_a = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
    }
    
    protected BlockStairs(final IBlockState modelState) {
        super(modelState.getBlock().blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
        this.modelBlock = modelState.getBlock();
        this.modelState = modelState;
        this.setHardness(this.modelBlock.blockHardness);
        this.setResistance(this.modelBlock.blockResistance / 3.0f);
        this.setStepSound(this.modelBlock.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.field_150152_N) {
            this.setBlockBounds(0.5f * (this.field_150153_O % 2), 0.5f * (this.field_150153_O / 4 % 2), 0.5f * (this.field_150153_O / 2 % 2), 0.5f + 0.5f * (this.field_150153_O % 2), 0.5f + 0.5f * (this.field_150153_O / 4 % 2), 0.5f + 0.5f * (this.field_150153_O / 2 % 2));
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public void setBaseCollisionBounds(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue(BlockStairs.HALF) == EnumHalf.TOP) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public static boolean isBlockStairs(final Block block) {
        return block instanceof BlockStairs;
    }
    
    public static boolean isSameStair(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos);
        return isBlockStairs(blockState2.getBlock()) && blockState2.getValue(BlockStairs.HALF) == blockState.getValue(BlockStairs.HALF) && blockState2.getValue(BlockStairs.FACING) == blockState.getValue(BlockStairs.FACING);
    }
    
    public int func_176307_f(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockStairs.FACING);
        final EnumHalf enumHalf = (EnumHalf)blockState.getValue(BlockStairs.HALF);
        final boolean b = enumHalf == EnumHalf.TOP;
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetEast());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = (EnumFacing)blockState2.getValue(BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    return b ? 1 : 2;
                }
                if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    return b ? 2 : 1;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetWest());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = (EnumFacing)blockState3.getValue(BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    return b ? 2 : 1;
                }
                if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    return b ? 1 : 2;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.offsetSouth());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = (EnumFacing)blockState4.getValue(BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    return b ? 2 : 1;
                }
                if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    return b ? 1 : 2;
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.offsetNorth());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = (EnumFacing)blockState5.getValue(BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    return b ? 1 : 2;
                }
                if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    return b ? 2 : 1;
                }
            }
        }
        return 0;
    }
    
    public int func_176305_g(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockStairs.FACING);
        final EnumHalf enumHalf = (EnumHalf)blockState.getValue(BlockStairs.HALF);
        final boolean b = enumHalf == EnumHalf.TOP;
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetWest());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = (EnumFacing)blockState2.getValue(BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    return b ? 1 : 2;
                }
                if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    return b ? 2 : 1;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetEast());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = (EnumFacing)blockState3.getValue(BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    return b ? 2 : 1;
                }
                if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    return b ? 1 : 2;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.offsetNorth());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = (EnumFacing)blockState4.getValue(BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    return b ? 2 : 1;
                }
                if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    return b ? 1 : 2;
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.offsetSouth());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = (EnumFacing)blockState5.getValue(BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    return b ? 1 : 2;
                }
                if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    return b ? 2 : 1;
                }
            }
        }
        return 0;
    }
    
    public boolean func_176306_h(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockStairs.FACING);
        final EnumHalf enumHalf = (EnumHalf)blockState.getValue(BlockStairs.HALF);
        final boolean b = enumHalf == EnumHalf.TOP;
        float n = 0.5f;
        float n2 = 1.0f;
        if (b) {
            n = 0.0f;
            n2 = 0.5f;
        }
        float n3 = 0.0f;
        float n4 = 1.0f;
        float n5 = 0.0f;
        float n6 = 0.5f;
        if (enumFacing == EnumFacing.EAST) {
            n3 = 0.5f;
            n6 = 1.0f;
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetEast());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = (EnumFacing)blockState2.getValue(BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    n6 = 0.5f;
                }
                else if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    n5 = 0.5f;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            n4 = 0.5f;
            n6 = 1.0f;
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetWest());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = (EnumFacing)blockState3.getValue(BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    n6 = 0.5f;
                }
                else if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    n5 = 0.5f;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            n5 = 0.5f;
            n6 = 1.0f;
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.offsetSouth());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = (EnumFacing)blockState4.getValue(BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    n4 = 0.5f;
                }
                else if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    n3 = 0.5f;
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.offsetNorth());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = (EnumFacing)blockState5.getValue(BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                    n4 = 0.5f;
                }
                else if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    n3 = 0.5f;
                }
            }
        }
        this.setBlockBounds(n3, n, n5, n4, n2, n6);
        return false;
    }
    
    public boolean func_176304_i(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockStairs.FACING);
        final EnumHalf enumHalf = (EnumHalf)blockState.getValue(BlockStairs.HALF);
        final boolean b = enumHalf == EnumHalf.TOP;
        float n = 0.5f;
        float n2 = 1.0f;
        if (b) {
            n = 0.0f;
            n2 = 0.5f;
        }
        float n3 = 0.0f;
        float n4 = 0.5f;
        float n5 = 0.5f;
        float n6 = 1.0f;
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetWest());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = (EnumFacing)blockState2.getValue(BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    n5 = 0.0f;
                    n6 = 0.5f;
                }
                else if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    n5 = 0.5f;
                    n6 = 1.0f;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetEast());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                n3 = 0.5f;
                n4 = 1.0f;
                final EnumFacing enumFacing3 = (EnumFacing)blockState3.getValue(BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.offsetNorth(), blockState)) {
                    n5 = 0.0f;
                    n6 = 0.5f;
                }
                else if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.offsetSouth(), blockState)) {
                    n5 = 0.5f;
                    n6 = 1.0f;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.offsetNorth());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                n5 = 0.0f;
                n6 = 0.5f;
                final EnumFacing enumFacing4 = (EnumFacing)blockState4.getValue(BlockStairs.FACING);
                if (enumFacing4 != EnumFacing.WEST || isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                        n3 = 0.5f;
                        n4 = 1.0f;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.offsetSouth());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = (EnumFacing)blockState5.getValue(BlockStairs.FACING);
                if (enumFacing5 != EnumFacing.WEST || isSameStair(blockAccess, blockPos.offsetWest(), blockState)) {
                    if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.offsetEast(), blockState)) {
                        n3 = 0.5f;
                        n4 = 1.0f;
                    }
                }
            }
        }
        if (true) {
            this.setBlockBounds(n3, n, n5, n4, n2, n6);
        }
        return true;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBaseCollisionBounds(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final boolean func_176306_h = this.func_176306_h(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        if (func_176306_h && this.func_176304_i(world, blockPos)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.randomDisplayTick(world, blockPos, blockState, random);
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.modelBlock.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.modelBlock.getMixedBrightnessForBlock(blockAccess, blockPos);
    }
    
    @Override
    public float getExplosionResistance(final Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return this.modelBlock.getBlockLayer();
    }
    
    @Override
    public int tickRate(final World world) {
        return this.modelBlock.tickRate(world);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return this.modelBlock.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return this.modelBlock.modifyAcceleration(world, blockPos, entity, vec3);
    }
    
    @Override
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return this.modelBlock.canCollideCheck(blockState, b);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.modelBlock.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.onNeighborBlockChange(world, blockPos, this.modelState, Blocks.air);
        this.modelBlock.onBlockAdded(world, blockPos, this.modelState);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.breakBlock(world, blockPos, this.modelState);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.modelBlock.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.updateTick(world, blockPos, blockState, random);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return this.modelBlock.onBlockActivated(world, blockPos, this.modelState, entityPlayer, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        this.modelBlock.onBlockDestroyedByExplosion(world, blockPos, explosion);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return this.modelBlock.getMapColor(this.modelState);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockStairs.FACING, entityLivingBase.func_174811_aO()).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
        return (enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) ? withProperty.withProperty(BlockStairs.HALF, EnumHalf.BOTTOM) : withProperty.withProperty(BlockStairs.HALF, EnumHalf.TOP);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final MovingObjectPosition[] array = new MovingObjectPosition[8];
        final IBlockState blockState = world.getBlockState(blockPos);
        final int[] array2 = BlockStairs.field_150150_a[((EnumFacing)blockState.getValue(BlockStairs.FACING)).getHorizontalIndex() + ((blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) ? 4 : 0)];
        this.field_150152_N = true;
        while (0 < 8) {
            this.field_150153_O = 0;
            if (Arrays.binarySearch(array2, 0) < 0) {
                array[0] = super.collisionRayTrace(world, blockPos, vec3, vec4);
            }
            int n = 0;
            ++n;
        }
        final int[] array3 = array2;
        while (0 < array2.length) {
            array[array3[0]] = null;
            int n2 = 0;
            ++n2;
        }
        MovingObjectPosition movingObjectPosition = null;
        double n3 = 0.0;
        final MovingObjectPosition[] array4 = array;
        while (0 < array.length) {
            final MovingObjectPosition movingObjectPosition2 = array4[0];
            if (movingObjectPosition2 != null) {
                final double squareDistanceTo = movingObjectPosition2.hitVec.squareDistanceTo(vec4);
                if (squareDistanceTo > n3) {
                    movingObjectPosition = movingObjectPosition2;
                    n3 = squareDistanceTo;
                }
            }
            int n4 = 0;
            ++n4;
        }
        return movingObjectPosition;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStairs.HALF, ((n & 0x4) > 0) ? EnumHalf.TOP : EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.getFront(5 - (n & 0x3)));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) {}
        final int n = 0x0 | 5 - ((EnumFacing)blockState.getValue(BlockStairs.FACING)).getIndex();
        return 0;
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.func_176306_h(blockAccess, blockPos)) {
            switch (this.func_176305_g(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    break;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_RIGHT);
                    break;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_LEFT);
                    break;
                }
            }
        }
        else {
            switch (this.func_176307_f(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    break;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
                    break;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
                    break;
                }
            }
        }
        return blockState;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStairs.FACING, BlockStairs.HALF, BlockStairs.SHAPE });
    }
    
    public enum EnumHalf implements IStringSerializable
    {
        TOP("TOP", 0, "TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String field_176709_c;
        private static final EnumHalf[] $VALUES;
        private static final String __OBFID;
        private static final EnumHalf[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002062";
            ENUM$VALUES = new EnumHalf[] { EnumHalf.TOP, EnumHalf.BOTTOM };
            $VALUES = new EnumHalf[] { EnumHalf.TOP, EnumHalf.BOTTOM };
        }
        
        private EnumHalf(final String s, final int n, final String s2, final int n2, final String field_176709_c) {
            this.field_176709_c = field_176709_c;
        }
        
        @Override
        public String toString() {
            return this.field_176709_c;
        }
        
        @Override
        public String getName() {
            return this.field_176709_c;
        }
    }
    
    public enum EnumShape implements IStringSerializable
    {
        STRAIGHT("STRAIGHT", 0, "STRAIGHT", 0, "straight"), 
        INNER_LEFT("INNER_LEFT", 1, "INNER_LEFT", 1, "inner_left"), 
        INNER_RIGHT("INNER_RIGHT", 2, "INNER_RIGHT", 2, "inner_right"), 
        OUTER_LEFT("OUTER_LEFT", 3, "OUTER_LEFT", 3, "outer_left"), 
        OUTER_RIGHT("OUTER_RIGHT", 4, "OUTER_RIGHT", 4, "outer_right");
        
        private final String field_176699_f;
        private static final EnumShape[] $VALUES;
        private static final String __OBFID;
        private static final EnumShape[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002061";
            ENUM$VALUES = new EnumShape[] { EnumShape.STRAIGHT, EnumShape.INNER_LEFT, EnumShape.INNER_RIGHT, EnumShape.OUTER_LEFT, EnumShape.OUTER_RIGHT };
            $VALUES = new EnumShape[] { EnumShape.STRAIGHT, EnumShape.INNER_LEFT, EnumShape.INNER_RIGHT, EnumShape.OUTER_LEFT, EnumShape.OUTER_RIGHT };
        }
        
        private EnumShape(final String s, final int n, final String s2, final int n2, final String field_176699_f) {
            this.field_176699_f = field_176699_f;
        }
        
        @Override
        public String toString() {
            return this.field_176699_f;
        }
        
        @Override
        public String getName() {
            return this.field_176699_f;
        }
    }
}
