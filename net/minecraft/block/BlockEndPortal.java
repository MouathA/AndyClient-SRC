package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockEndPortal extends BlockContainer
{
    private static final String __OBFID;
    
    protected BlockEndPortal(final Material material) {
        super(material);
        this.setLightLevel(1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEndPortal();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing == EnumFacing.DOWN && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
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
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null && !world.isRemote) {
            entity.travelToDimension(1);
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + random.nextFloat(), blockPos.getY() + 0.8f, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.obsidianColor;
    }
    
    static {
        __OBFID = "CL_00000236";
    }
}
