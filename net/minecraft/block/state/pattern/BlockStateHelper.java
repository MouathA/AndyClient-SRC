package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;

public class BlockStateHelper implements Predicate
{
    private final BlockState field_177641_a;
    private final Map field_177640_b;
    private static final String __OBFID;
    
    private BlockStateHelper(final BlockState field_177641_a) {
        this.field_177640_b = Maps.newHashMap();
        this.field_177641_a = field_177641_a;
    }
    
    public static BlockStateHelper forBlock(final Block block) {
        return new BlockStateHelper(block.getBlockState());
    }
    
    public boolean func_177639_a(final IBlockState blockState) {
        if (blockState != null && blockState.getBlock().equals(this.field_177641_a.getBlock())) {
            for (final Map.Entry<IProperty, V> entry : this.field_177640_b.entrySet()) {
                if (!((Predicate)entry.getValue()).apply(blockState.getValue(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public BlockStateHelper func_177637_a(final IProperty property, final Predicate predicate) {
        if (!this.field_177641_a.getProperties().contains(property)) {
            throw new IllegalArgumentException(this.field_177641_a + " cannot support property " + property);
        }
        this.field_177640_b.put(property, predicate);
        return this;
    }
    
    @Override
    public boolean apply(final Object o) {
        return this.func_177639_a((IBlockState)o);
    }
    
    static {
        __OBFID = "CL_00002019";
    }
}
