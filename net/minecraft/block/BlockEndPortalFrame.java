package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class BlockEndPortalFrame extends Block
{
    public static final PropertyDirection field_176508_a;
    public static final PropertyBool field_176507_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000237";
        field_176508_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        field_176507_b = PropertyBool.create("eye");
    }
    
    public BlockEndPortalFrame() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockEndPortalFrame.field_176508_a, EnumFacing.NORTH).withProperty(BlockEndPortalFrame.field_176507_b, false));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        if (world.getBlockState(blockPos).getValue(BlockEndPortalFrame.field_176507_b)) {
            this.setBlockBounds(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockEndPortalFrame.field_176508_a, entityLivingBase.func_174811_aO().getOpposite()).withProperty(BlockEndPortalFrame.field_176507_b, false);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getValue(BlockEndPortalFrame.field_176507_b) ? 15 : 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockEndPortalFrame.field_176507_b, (n & 0x4) != 0x0).withProperty(BlockEndPortalFrame.field_176508_a, EnumFacing.getHorizontal(n & 0x3));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockEndPortalFrame.field_176508_a)).getHorizontalIndex();
        if (blockState.getValue(BlockEndPortalFrame.field_176507_b)) {
            n |= 0x4;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockEndPortalFrame.field_176508_a, BlockEndPortalFrame.field_176507_b });
    }
}
