package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import com.viaversion.viaversion.api.data.*;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings
{
    private final Int2ObjectMap statisticMappings;
    private final Map translateMappings;
    private Mappings enchantmentMappings;
    
    public BackwardsMappings() {
        super("1.13", "1.12", Protocol1_13To1_12_2.class, true);
        this.statisticMappings = new Int2ObjectOpenHashMap();
        this.translateMappings = new HashMap();
    }
    
    public void loadVBExtras(final JsonObject jsonObject, final JsonObject jsonObject2) {
        this.enchantmentMappings = VBMappings.vbBuilder().warnOnMissing(false).unmapped(jsonObject.getAsJsonObject("enchantments")).mapped(jsonObject2.getAsJsonObject("enchantments")).build();
        for (final Map.Entry<K, Integer> entry : StatisticMappings.CUSTOM_STATS.entrySet()) {
            this.statisticMappings.put((int)entry.getValue(), entry.getKey());
        }
        for (final Map.Entry<K, String> entry2 : Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet()) {
            this.translateMappings.put(entry2.getValue(), entry2.getKey());
        }
    }
    
    private static void mapIdentifiers(final int[] array, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        final Object2IntMap indexedObjectToMap = MappingDataLoader.indexedObjectToMap(jsonObject2);
        for (final Map.Entry<K, JsonElement> entry : jsonObject.entrySet()) {
            final String asString = entry.getValue().getAsString();
            int n = indexedObjectToMap.getInt(asString);
            if (n == -1) {
                JsonPrimitive jsonPrimitive = jsonObject3.getAsJsonPrimitive(asString);
                final int index;
                if (jsonPrimitive == null && (index = asString.indexOf(91)) != -1) {
                    jsonPrimitive = jsonObject3.getAsJsonPrimitive(asString.substring(0, index));
                }
                if (jsonPrimitive != null) {
                    if (jsonPrimitive.getAsString().startsWith("id:")) {
                        final String replace = jsonPrimitive.getAsString().replace("id:", "");
                        Short.parseShort(replace);
                        n = indexedObjectToMap.getInt(jsonObject2.getAsJsonPrimitive(replace).getAsString());
                    }
                    else {
                        n = indexedObjectToMap.getInt(jsonPrimitive.getAsString());
                    }
                }
                if (n == -1) {
                    if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) {
                        continue;
                    }
                    if (jsonPrimitive != null) {
                        ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + "/" + jsonPrimitive.getAsString() + " :( ");
                        continue;
                    }
                    ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                    continue;
                }
            }
            array[Integer.parseInt((String)entry.getKey())] = ((-1 != -1) ? -1 : ((short)n));
        }
    }
    
    @Override
    protected Mappings loadFromObject(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (s.equals("blockstates")) {
            final int[] array = new int[8582];
            Arrays.fill(array, -1);
            mapIdentifiers(array, jsonObject.getAsJsonObject("blockstates"), jsonObject2.getAsJsonObject("blocks"), jsonObject3.getAsJsonObject("blockstates"));
            return IntArrayMappings.of(array, -1);
        }
        return super.loadFromObject(jsonObject, jsonObject2, jsonObject3, s);
    }
    
    @Override
    public int getNewBlockStateId(final int n) {
        final int newBlockStateId = super.getNewBlockStateId(n);
        switch (newBlockStateId) {
            case 1595:
            case 1596:
            case 1597: {
                return 1584;
            }
            case 1611:
            case 1612:
            case 1613: {
                return 1600;
            }
            default: {
                return newBlockStateId;
            }
        }
    }
    
    @Override
    protected int checkValidity(final int n, final int n2, final String s) {
        return n2;
    }
    
    @Override
    protected boolean shouldWarnOnMissing(final String s) {
        return super.shouldWarnOnMissing(s) && !s.equals("items");
    }
    
    public Int2ObjectMap getStatisticMappings() {
        return this.statisticMappings;
    }
    
    public Map getTranslateMappings() {
        return this.translateMappings;
    }
    
    public Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }
}
