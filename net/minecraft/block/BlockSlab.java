package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockSlab extends Block
{
    public static final PropertyEnum HALF_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000253";
        HALF_PROP = PropertyEnum.create("half", EnumBlockHalf.class);
    }
    
    public BlockSlab(final Material material) {
        super(material);
        if (this.isDouble()) {
            this.fullBlock = true;
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }
    
    protected boolean canSilkHarvest() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final IBlockState blockState = blockAccess.getBlockState(blockPos);
            if (blockState.getBlock() == this) {
                if (blockState.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP) {
                    this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.isDouble();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockSlab.HALF_PROP, EnumBlockHalf.BOTTOM);
        return this.isDouble() ? withProperty : ((enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) ? withProperty : withProperty.withProperty(BlockSlab.HALF_PROP, EnumBlockHalf.TOP));
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return this.isDouble() ? 2 : 1;
    }
    
    @Override
    public boolean isFullCube() {
        return this.isDouble();
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (this.isDouble()) {
            return super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
        }
        if (enumFacing != EnumFacing.UP && enumFacing != EnumFacing.DOWN && !super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) {
            return false;
        }
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final IBlockState blockState2 = blockAccess.getBlockState(offset);
        final boolean b = func_150003_a(blockState.getBlock()) && blockState.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP;
        return (func_150003_a(blockState2.getBlock()) && blockState2.getValue(BlockSlab.HALF_PROP) == EnumBlockHalf.TOP) ? (enumFacing == EnumFacing.DOWN || (enumFacing == EnumFacing.UP && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) || !func_150003_a(blockState.getBlock()) || !b) : (enumFacing == EnumFacing.UP || (enumFacing == EnumFacing.DOWN && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) || !func_150003_a(blockState.getBlock()) || b);
    }
    
    protected static boolean func_150003_a(final Block block) {
        return block == Blocks.stone_slab || block == Blocks.wooden_slab || block == Blocks.stone_slab2;
    }
    
    public abstract String getFullSlabName(final int p0);
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return super.getDamageValue(world, blockPos) & 0x7;
    }
    
    public abstract boolean isDouble();
    
    public abstract IProperty func_176551_l();
    
    public abstract Object func_176553_a(final ItemStack p0);
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        TOP("TOP", 0, "TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String halfName;
        private static final EnumBlockHalf[] $VALUES;
        private static final String __OBFID;
        private static final EnumBlockHalf[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002109";
            ENUM$VALUES = new EnumBlockHalf[] { EnumBlockHalf.TOP, EnumBlockHalf.BOTTOM };
            $VALUES = new EnumBlockHalf[] { EnumBlockHalf.TOP, EnumBlockHalf.BOTTOM };
        }
        
        private EnumBlockHalf(final String s, final int n, final String s2, final int n2, final String halfName) {
            this.halfName = halfName;
        }
        
        @Override
        public String toString() {
            return this.halfName;
        }
        
        @Override
        public String getName() {
            return this.halfName;
        }
    }
}
