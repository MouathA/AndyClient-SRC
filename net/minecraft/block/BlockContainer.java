package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    private static final String __OBFID;
    
    protected BlockContainer(final Material material) {
        super(material);
        this.isBlockContainer = true;
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(n, n2);
    }
    
    static {
        __OBFID = "CL_00000193";
    }
}
