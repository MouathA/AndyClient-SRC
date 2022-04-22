package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;

public class BlockLilyPad extends BlockBush
{
    private static final String __OBFID;
    
    protected BlockLilyPad() {
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.015625f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        if (entity == null || !(entity instanceof EntityBoat)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    @Override
    public int getBlockColor() {
        return 7455580;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return 7455580;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return 2129968;
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.water;
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            final IBlockState blockState2 = world.getBlockState(blockPos.offsetDown());
            return blockState2.getBlock().getMaterial() == Material.water && (int)blockState2.getValue(BlockLiquid.LEVEL) == 0;
        }
        return false;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    static {
        __OBFID = "CL_00000332";
    }
}
