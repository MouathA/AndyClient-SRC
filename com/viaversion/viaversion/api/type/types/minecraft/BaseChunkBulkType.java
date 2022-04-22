package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public abstract class BaseChunkBulkType extends Type
{
    protected BaseChunkBulkType() {
        super(Chunk[].class);
    }
    
    protected BaseChunkBulkType(final String s) {
        super(s, Chunk[].class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkBulkType.class;
    }
}
