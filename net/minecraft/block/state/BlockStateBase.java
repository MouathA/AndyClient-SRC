package net.minecraft.block.state;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class BlockStateBase implements IBlockState
{
    private static final Joiner COMMA_JOINER;
    private static final Function field_177233_b;
    private static final String __OBFID;
    private int blockId;
    private int blockStateId;
    private int metadata;
    private ResourceLocation blockLocation;
    
    static {
        __OBFID = "CL_00002032";
        COMMA_JOINER = Joiner.on(',');
        field_177233_b = new Function() {
            private static final String __OBFID;
            
            public String func_177225_a(final Map.Entry entry) {
                if (entry == null) {
                    return "<NULL>";
                }
                final IProperty property = entry.getKey();
                return String.valueOf(property.getName()) + "=" + property.getName((Comparable)entry.getValue());
            }
            
            @Override
            public Object apply(final Object o) {
                return this.func_177225_a((Map.Entry)o);
            }
            
            static {
                __OBFID = "CL_00002031";
            }
        };
    }
    
    public BlockStateBase() {
        this.blockId = -1;
        this.blockStateId = -1;
        this.metadata = -1;
        this.blockLocation = null;
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.getBlock());
        }
        return this.blockId;
    }
    
    public int getBlockStateId() {
        if (this.blockStateId < 0) {
            this.blockStateId = Block.getStateId(this);
        }
        return this.blockStateId;
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.getBlock().getMetaFromState(this);
        }
        return this.metadata;
    }
    
    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.getBlock());
        }
        return this.blockLocation;
    }
    
    @Override
    public IBlockState cycleProperty(final IProperty property) {
        return this.withProperty(property, (Comparable)cyclePropertyValue(property.getAllowedValues(), this.getValue(property)));
    }
    
    protected static Object cyclePropertyValue(final Collection collection, final Object o) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                return collection.iterator().next();
            }
        }
        return iterator.next();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Block.blockRegistry.getNameForObject(this.getBlock()));
        if (!this.getProperties().isEmpty()) {
            sb.append("[");
            BlockStateBase.COMMA_JOINER.appendTo(sb, Iterables.transform(this.getProperties().entrySet(), BlockStateBase.field_177233_b));
            sb.append("]");
        }
        return sb.toString();
    }
    
    public ImmutableTable getPropertyValueTable() {
        return null;
    }
}
