package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Types1_16
{
    public static final Type CHUNK_SECTION;
    public static final ParticleType PARTICLE;
    public static final MetaTypes1_14 META_TYPES;
    public static final Type METADATA;
    public static final Type METADATA_LIST;
    
    static {
        CHUNK_SECTION = new ChunkSectionType1_16();
        PARTICLE = new ParticleType();
        META_TYPES = new MetaTypes1_14(Types1_16.PARTICLE);
        METADATA = new MetadataType(Types1_16.META_TYPES);
        METADATA_LIST = new MetaListType(Types1_16.METADATA);
    }
}
