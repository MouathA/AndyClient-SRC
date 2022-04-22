package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Types1_9
{
    public static final Type METADATA;
    public static final Type METADATA_LIST;
    public static final Type CHUNK_SECTION;
    
    static {
        METADATA = new Metadata1_9Type();
        METADATA_LIST = new MetaListType(Types1_9.METADATA);
        CHUNK_SECTION = new ChunkSectionType1_9();
    }
}
