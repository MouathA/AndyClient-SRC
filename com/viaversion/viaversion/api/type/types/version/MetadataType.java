package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public final class MetadataType extends ModernMetaType
{
    private final MetaTypes metaTypes;
    
    public MetadataType(final MetaTypes metaTypes) {
        this.metaTypes = metaTypes;
    }
    
    @Override
    protected MetaType getType(final int n) {
        return this.metaTypes.byId(n);
    }
}
