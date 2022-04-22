package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class BlockHelper implements Predicate
{
    private final Block block;
    private static final String __OBFID;
    
    private BlockHelper(final Block block) {
        this.block = block;
    }
    
    public static BlockHelper forBlock(final Block block) {
        return new BlockHelper(block);
    }
    
    public boolean isBlockEqualTo(final IBlockState blockState) {
        return blockState != null && blockState.getBlock() == this.block;
    }
    
    @Override
    public boolean apply(final Object o) {
        return this.isBlockEqualTo((IBlockState)o);
    }
    
    static {
        __OBFID = "CL_00002020";
    }
}
