package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;

public class Metadata1_8Type extends OldMetaType
{
    @Override
    protected MetaType getType(final int n) {
        return MetaType1_8.byId(n);
    }
}
