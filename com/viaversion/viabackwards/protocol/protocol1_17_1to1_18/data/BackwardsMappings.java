package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;

import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public final class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings
{
    private final Int2ObjectMap blockEntities;
    
    public BackwardsMappings() {
        super("1.18", "1.17", Protocol1_18To1_17_1.class, true);
        this.blockEntities = new Int2ObjectOpenHashMap();
    }
    
    @Override
    protected void loadVBExtras(final JsonObject jsonObject, final JsonObject jsonObject2) {
        for (final Object2IntMap.Entry entry : Protocol1_18To1_17_1.MAPPINGS.blockEntityIds().object2IntEntrySet()) {
            this.blockEntities.put(entry.getIntValue(), entry.getKey());
        }
    }
    
    public Int2ObjectMap blockEntities() {
        return this.blockEntities;
    }
}
