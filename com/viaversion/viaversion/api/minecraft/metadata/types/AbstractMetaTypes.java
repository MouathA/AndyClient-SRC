package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;

public abstract class AbstractMetaTypes implements MetaTypes
{
    private final MetaType[] values;
    
    protected AbstractMetaTypes(final int n) {
        this.values = new MetaType[n];
    }
    
    @Override
    public MetaType byId(final int n) {
        return this.values[n];
    }
    
    @Override
    public MetaType[] values() {
        return this.values;
    }
    
    protected MetaType add(final int n, final Type type) {
        return this.values[n] = MetaType.create(n, type);
    }
}
