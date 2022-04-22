package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.util.*;

public class StateMap extends StateMapperBase
{
    private final IProperty field_178142_a;
    private final String field_178141_c;
    private final List field_178140_d;
    private static final String __OBFID;
    
    private StateMap(final IProperty field_178142_a, final String field_178141_c, final List field_178140_d) {
        this.field_178142_a = field_178142_a;
        this.field_178141_c = field_178141_c;
        this.field_178140_d = field_178140_d;
    }
    
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
        String s;
        if (this.field_178142_a == null) {
            s = ((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock())).toString();
        }
        else {
            s = this.field_178142_a.getName(linkedHashMap.remove(this.field_178142_a));
        }
        if (this.field_178141_c != null) {
            s = String.valueOf(s) + this.field_178141_c;
        }
        final Iterator<IProperty> iterator = (Iterator<IProperty>)this.field_178140_d.iterator();
        while (iterator.hasNext()) {
            linkedHashMap.remove(iterator.next());
        }
        return new ModelResourceLocation(s, this.func_178131_a(linkedHashMap));
    }
    
    StateMap(final IProperty property, final String s, final List list, final Object o) {
        this(property, s, list);
    }
    
    static {
        __OBFID = "CL_00002476";
    }
    
    public static class Builder
    {
        private IProperty field_178445_a;
        private String field_178443_b;
        private final List field_178444_c;
        private static final String __OBFID;
        
        public Builder() {
            this.field_178444_c = Lists.newArrayList();
        }
        
        public Builder func_178440_a(final IProperty field_178445_a) {
            this.field_178445_a = field_178445_a;
            return this;
        }
        
        public Builder func_178439_a(final String field_178443_b) {
            this.field_178443_b = field_178443_b;
            return this;
        }
        
        public Builder func_178442_a(final IProperty... array) {
            Collections.addAll(this.field_178444_c, array);
            return this;
        }
        
        public StateMap build() {
            return new StateMap(this.field_178445_a, this.field_178443_b, this.field_178444_c, null);
        }
        
        static {
            __OBFID = "CL_00002474";
        }
    }
}
