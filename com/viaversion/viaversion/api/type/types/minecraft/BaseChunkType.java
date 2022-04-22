package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public abstract class BaseChunkType extends Type
{
    protected BaseChunkType() {
        super(Chunk.class);
    }
    
    protected BaseChunkType(final String s) {
        super(s, Chunk.class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkType.class;
    }
}
