package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public class EntityTypeMapping
{
    private static final Int2IntMap TYPES;
    
    public static int getOldId(final int n) {
        return EntityTypeMapping.TYPES.get(n);
    }
    
    static {
        (TYPES = new Int2IntOpenHashMap()).defaultReturnValue(-1);
        final Field declaredField = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
        declaredField.setAccessible(true);
        for (final Int2IntMap.Entry entry : ((Int2IntMap)declaredField.get(null)).int2IntEntrySet()) {
            EntityTypeMapping.TYPES.put(entry.getIntValue(), entry.getIntKey());
        }
    }
}
