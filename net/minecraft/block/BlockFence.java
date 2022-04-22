package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockFence extends Block
{
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    public static final PropertyBool SOUTH;
    public static final PropertyBool WEST;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000242";
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
    }
    
    public BlockFence(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFence.NORTH, false).withProperty(BlockFence.EAST, false).withProperty(BlockFence.SOUTH, false).withProperty(BlockFence.WEST, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final boolean func_176524_e = this.func_176524_e(world, blockPos.offsetNorth());
        final boolean func_176524_e2 = this.func_176524_e(world, blockPos.offsetSouth());
        final boolean func_176524_e3 = this.func_176524_e(world, blockPos.offsetWest());
        final boolean func_176524_e4 = this.func_176524_e(world, blockPos.offsetEast());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (func_176524_e) {
            n3 = 0.0f;
        }
        if (func_176524_e2) {
            n4 = 1.0f;
        }
        if (func_176524_e || func_176524_e2) {
            this.setBlockBounds(n, 0.0f, n3, n2, 1.5f, n4);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        float n5 = 0.375f;
        float n6 = 0.625f;
        if (func_176524_e3) {
            n = 0.0f;
        }
        if (func_176524_e4) {
            n2 = 1.0f;
        }
        if (func_176524_e3 || func_176524_e4 || (!func_176524_e && !func_176524_e2)) {
            this.setBlockBounds(n, 0.0f, n5, n2, 1.5f, n6);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        if (func_176524_e) {
            n5 = 0.0f;
        }
        if (func_176524_e2) {
            n6 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n5, n2, 1.0f, n6);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean func_176524_e = this.func_176524_e(blockAccess, blockPos.offsetNorth());
        final boolean func_176524_e2 = this.func_176524_e(blockAccess, blockPos.offsetSouth());
        final boolean func_176524_e3 = this.func_176524_e(blockAccess, blockPos.offsetWest());
        final boolean func_176524_e4 = this.func_176524_e(blockAccess, blockPos.offsetEast());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (func_176524_e) {
            n3 = 0.0f;
        }
        if (func_176524_e2) {
            n4 = 1.0f;
        }
        if (func_176524_e3) {
            n = 0.0f;
        }
        if (func_176524_e4) {
            n2 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, 1.0f, n4);
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
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return false;
    }
    
    public boolean func_176524_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        return block != Blocks.barrier && ((block instanceof BlockFence && block.blockMaterial == this.blockMaterial) || block instanceof BlockFenceGate || (block.blockMaterial.isOpaque() && block.isFullCube() && block.blockMaterial != Material.gourd));
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return world.isRemote || ItemLead.func_180618_a(entityPlayer, world, blockPos);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockFence.NORTH, this.func_176524_e(blockAccess, blockPos.offsetNorth())).withProperty(BlockFence.EAST, this.func_176524_e(blockAccess, blockPos.offsetEast())).withProperty(BlockFence.SOUTH, this.func_176524_e(blockAccess, blockPos.offsetSouth())).withProperty(BlockFence.WEST, this.func_176524_e(blockAccess, blockPos.offsetWest()));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFence.NORTH, BlockFence.EAST, BlockFence.WEST, BlockFence.SOUTH });
    }
}
