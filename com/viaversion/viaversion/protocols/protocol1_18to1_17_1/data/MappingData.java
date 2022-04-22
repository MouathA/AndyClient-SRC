package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data;

import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public final class MappingData extends MappingDataBase
{
    private final Object2IntMap blockEntityIds;
    
    public MappingData() {
        super("1.17", "1.18", true);
        (this.blockEntityIds = new Object2IntOpenHashMap()).defaultReturnValue(-1);
    }
    
    @Override
    protected void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        final Iterator iterator = jsonObject2.getAsJsonArray("blockentities").iterator();
        while (iterator.hasNext()) {
            final String asString = iterator.next().getAsString();
            final Object2IntMap blockEntityIds = this.blockEntityIds;
            final String s = asString;
            final int n = 0;
            int n2 = 0;
            ++n2;
            blockEntityIds.put(s, n);
        }
    }
    
    public Object2IntMap blockEntityIds() {
        return this.blockEntityIds;
    }
}
