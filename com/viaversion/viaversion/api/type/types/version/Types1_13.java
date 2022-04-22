package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Types1_13
{
    public static final Type CHUNK_SECTION;
    public static final ParticleType PARTICLE;
    public static final MetaTypes1_13 META_TYPES;
    public static final Type METADATA;
    public static final Type METADATA_LIST;
    
    static {
        CHUNK_SECTION = new ChunkSectionType1_13();
        PARTICLE = new ParticleType();
        META_TYPES = new MetaTypes1_13(Types1_13.PARTICLE);
        METADATA = new MetadataType(Types1_13.META_TYPES);
        METADATA_LIST = new MetaListType(Types1_13.METADATA);
    }
}
