package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.minecraft.nbt.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class MappingData extends MappingDataBase
{
    private final Map dimensionDataMap;
    private CompoundTag dimensionRegistry;
    
    public MappingData() {
        super("1.16", "1.16.2", true);
        this.dimensionDataMap = new HashMap();
    }
    
    public void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        this.dimensionRegistry = BinaryTagIO.readCompressedInputStream(MappingDataLoader.getResource("dimension-registry-1.16.2.nbt"));
        for (final CompoundTag compoundTag : (ListTag)((CompoundTag)this.dimensionRegistry.get("minecraft:dimension_type")).get("value")) {
            this.dimensionDataMap.put(((StringTag)compoundTag.get("name")).getValue(), new CompoundTag(((CompoundTag)compoundTag.get("element")).getValue()));
        }
    }
    
    public Map getDimensionDataMap() {
        return this.dimensionDataMap;
    }
    
    public CompoundTag getDimensionRegistry() {
        return this.dimensionRegistry;
    }
}
