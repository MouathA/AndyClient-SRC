package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.data;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.data.*;

public class MappingData extends MappingDataBase
{
    public MappingData() {
        super("1.14", "1.15", true);
    }
    
    @Override
    protected Mappings loadFromArray(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (!s.equals("sounds")) {
            return super.loadFromArray(jsonObject, jsonObject2, jsonObject3, s);
        }
        return IntArrayMappings.builder().warnOnMissing(false).unmapped(jsonObject.getAsJsonArray(s)).mapped(jsonObject2.getAsJsonArray(s)).build();
    }
}
