package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.model.*;

public abstract class StateMapperBase implements IStateMapper
{
    protected Map field_178133_b;
    private static final String __OBFID;
    
    public StateMapperBase() {
        this.field_178133_b = Maps.newLinkedHashMap();
    }
    
    public String func_178131_a(final Map map) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<IProperty, V> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            final IProperty property = entry.getKey();
            final Comparable comparable = (Comparable)entry.getValue();
            sb.append(property.getName());
            sb.append("=");
            sb.append(property.getName(comparable));
        }
        if (sb.length() == 0) {
            sb.append("normal");
        }
        return sb.toString();
    }
    
    @Override
    public Map func_178130_a(final Block block) {
        for (final IBlockState blockState : block.getBlockState().getValidStates()) {
            this.field_178133_b.put(blockState, this.func_178132_a(blockState));
        }
        return this.field_178133_b;
    }
    
    protected abstract ModelResourceLocation func_178132_a(final IBlockState p0);
    
    static {
        __OBFID = "CL_00002479";
    }
}
