package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockBreakable extends Block
{
    private boolean ignoreSimilarity;
    private static final String __OBFID;
    
    protected BlockBreakable(final Material material, final boolean ignoreSimilarity) {
        super(material);
        this.ignoreSimilarity = ignoreSimilarity;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (blockAccess.getBlockState(blockPos.offset(enumFacing.getOpposite())) != blockState) {
                return true;
            }
            if (block == this) {
                return false;
            }
        }
        return (this.ignoreSimilarity || block != this) && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    static {
        __OBFID = "CL_00000254";
    }
}
