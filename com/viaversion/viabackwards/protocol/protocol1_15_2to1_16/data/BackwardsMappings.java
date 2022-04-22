package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings
{
    private final Map attributeMappings;
    
    public BackwardsMappings() {
        super("1.16", "1.15", Protocol1_16To1_15_2.class, true);
        this.attributeMappings = new HashMap();
    }
    
    @Override
    protected void loadVBExtras(final JsonObject jsonObject, final JsonObject jsonObject2) {
        for (final Map.Entry<Object, Object> entry : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().entrySet()) {
            this.attributeMappings.put(entry.getValue(), entry.getKey());
        }
    }
    
    public Map getAttributeMappings() {
        return this.attributeMappings;
    }
}
