package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

@Deprecated
public class Metadata1_13_2Type extends ModernMetaType
{
    @Override
    protected MetaType getType(final int n) {
        return Types1_13_2.META_TYPES.byId(n);
    }
}
