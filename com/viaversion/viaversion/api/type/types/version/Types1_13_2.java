package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Types1_13_2
{
    public static final ParticleType PARTICLE;
    public static final MetaTypes1_13_2 META_TYPES;
    public static final Type METADATA;
    public static final Type METADATA_LIST;
    
    static {
        PARTICLE = new ParticleType();
        META_TYPES = new MetaTypes1_13_2(Types1_13_2.PARTICLE);
        METADATA = new MetadataType(Types1_13_2.META_TYPES);
        METADATA_LIST = new MetaListType(Types1_13_2.METADATA);
    }
}
