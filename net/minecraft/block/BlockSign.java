package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockSign extends BlockContainer
{
    private static final String __OBFID;
    
    protected BlockSign() {
        super(Material.wood);
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntitySign();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.sign;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.sign;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntitySign && ((TileEntitySign)tileEntity).func_174882_b(entityPlayer);
    }
    
    static {
        __OBFID = "CL_00000306";
    }
}
