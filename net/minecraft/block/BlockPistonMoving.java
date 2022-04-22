package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockPistonMoving extends BlockContainer
{
    public static final PropertyDirection field_176426_a;
    public static final PropertyEnum field_176425_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000368";
        field_176426_a = BlockPistonExtension.field_176326_a;
        field_176425_b = BlockPistonExtension.field_176325_b;
    }
    
    public BlockPistonMoving() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonMoving.field_176426_a, EnumFacing.NORTH).withProperty(BlockPistonMoving.field_176425_b, BlockPistonExtension.EnumPistonType.DEFAULT));
        this.setHardness(-1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return null;
    }
    
    public static TileEntity func_176423_a(final IBlockState blockState, final EnumFacing enumFacing, final boolean b, final boolean b2) {
        return new TileEntityPiston(blockState, enumFacing, b, b2);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityPiston) {
            ((TileEntityPiston)tileEntity).clearPistonTileEntity();
        }
        else {
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return false;
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockPos offset = blockPos.offset(((EnumFacing)blockState.getValue(BlockPistonMoving.field_176426_a)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() instanceof BlockPistonBase && (boolean)blockState2.getValue(BlockPistonBase.EXTENDED)) {
            world.setBlockToAir(offset);
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
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote && world.getTileEntity(blockToAir) == null) {
            world.setBlockToAir(blockToAir);
            return true;
        }
        return false;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            final TileEntityPiston func_176422_e = this.func_176422_e(world, blockPos);
            if (func_176422_e != null) {
                final IBlockState func_174927_b = func_176422_e.func_174927_b();
                func_174927_b.getBlock().dropBlockAsItem(world, blockPos, func_174927_b, 0);
            }
        }
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            world.getTileEntity(blockPos);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityPiston func_176422_e = this.func_176422_e(world, blockPos);
        if (func_176422_e == null) {
            return null;
        }
        float func_145860_a = func_176422_e.func_145860_a(0.0f);
        if (func_176422_e.isExtending()) {
            func_145860_a = 1.0f - func_145860_a;
        }
        return this.func_176424_a(world, blockPos, func_176422_e.func_174927_b(), func_145860_a, func_176422_e.func_174930_e());
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntityPiston func_176422_e = this.func_176422_e(blockAccess, blockPos);
        if (func_176422_e != null) {
            final Block block = func_176422_e.func_174927_b().getBlock();
            if (block == this || block.getMaterial() == Material.air) {
                return;
            }
            float func_145860_a = func_176422_e.func_145860_a(0.0f);
            if (func_176422_e.isExtending()) {
                func_145860_a = 1.0f - func_145860_a;
            }
            block.setBlockBoundsBasedOnState(blockAccess, blockPos);
            if (block == Blocks.piston || block == Blocks.sticky_piston) {
                func_145860_a = 0.0f;
            }
            final EnumFacing func_174930_e = func_176422_e.func_174930_e();
            this.minX = block.getBlockBoundsMinX() - func_174930_e.getFrontOffsetX() * func_145860_a;
            this.minY = block.getBlockBoundsMinY() - func_174930_e.getFrontOffsetY() * func_145860_a;
            this.minZ = block.getBlockBoundsMinZ() - func_174930_e.getFrontOffsetZ() * func_145860_a;
            this.maxX = block.getBlockBoundsMaxX() - func_174930_e.getFrontOffsetX() * func_145860_a;
            this.maxY = block.getBlockBoundsMaxY() - func_174930_e.getFrontOffsetY() * func_145860_a;
            this.maxZ = block.getBlockBoundsMaxZ() - func_174930_e.getFrontOffsetZ() * func_145860_a;
        }
    }
    
    public AxisAlignedBB func_176424_a(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final EnumFacing enumFacing) {
        if (blockState.getBlock() == this || blockState.getBlock().getMaterial() == Material.air) {
            return null;
        }
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(world, blockPos, blockState);
        if (collisionBoundingBox == null) {
            return null;
        }
        double minX = collisionBoundingBox.minX;
        double minY = collisionBoundingBox.minY;
        double minZ = collisionBoundingBox.minZ;
        double maxX = collisionBoundingBox.maxX;
        double maxY = collisionBoundingBox.maxY;
        double maxZ = collisionBoundingBox.maxZ;
        if (enumFacing.getFrontOffsetX() < 0) {
            minX -= enumFacing.getFrontOffsetX() * n;
        }
        else {
            maxX -= enumFacing.getFrontOffsetX() * n;
        }
        if (enumFacing.getFrontOffsetY() < 0) {
            minY -= enumFacing.getFrontOffsetY() * n;
        }
        else {
            maxY -= enumFacing.getFrontOffsetY() * n;
        }
        if (enumFacing.getFrontOffsetZ() < 0) {
            minZ -= enumFacing.getFrontOffsetZ() * n;
        }
        else {
            maxZ -= enumFacing.getFrontOffsetZ() * n;
        }
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    private TileEntityPiston func_176422_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityPiston) ? ((TileEntityPiston)tileEntity) : null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonMoving.field_176426_a, BlockPistonExtension.func_176322_b(n)).withProperty(BlockPistonMoving.field_176425_b, ((n & 0x8) > 0) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonMoving.field_176426_a)).getIndex();
        if (blockState.getValue(BlockPistonMoving.field_176425_b) == BlockPistonExtension.EnumPistonType.STICKY) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonMoving.field_176426_a, BlockPistonMoving.field_176425_b });
    }
}
