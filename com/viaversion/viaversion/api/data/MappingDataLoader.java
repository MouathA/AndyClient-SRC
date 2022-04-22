package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.*;
import java.io.*;
import com.viaversion.viaversion.util.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.concurrent.*;

public class MappingDataLoader
{
    private static final Map MAPPINGS_CACHE;
    private static boolean cacheJsonMappings;
    
    public static boolean isCacheJsonMappings() {
        return MappingDataLoader.cacheJsonMappings;
    }
    
    public static void enableMappingsCache() {
        MappingDataLoader.cacheJsonMappings = true;
    }
    
    public static Map getMappingsCache() {
        return MappingDataLoader.MAPPINGS_CACHE;
    }
    
    public static JsonObject loadFromDataDir(final String s) {
        final File file = new File(Via.getPlatform().getDataFolder(), s);
        if (!file.exists()) {
            return loadData(s);
        }
        final FileReader fileReader = new FileReader(file);
        final Object o = null;
        final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(fileReader, JsonObject.class);
        if (fileReader != null) {
            if (o != null) {
                fileReader.close();
            }
            else {
                fileReader.close();
            }
        }
        return jsonObject;
    }
    
    public static JsonObject loadData(final String s) {
        return loadData(s, false);
    }
    
    public static JsonObject loadData(final String s, final boolean b) {
        if (MappingDataLoader.cacheJsonMappings) {
            final JsonObject jsonObject = MappingDataLoader.MAPPINGS_CACHE.get(s);
            if (jsonObject != null) {
                return jsonObject;
            }
        }
        final InputStream resource = getResource(s);
        if (resource == null) {
            return null;
        }
        final InputStreamReader inputStreamReader = new InputStreamReader(resource);
        final JsonObject jsonObject2 = (JsonObject)GsonUtil.getGson().fromJson(inputStreamReader, JsonObject.class);
        if (b && MappingDataLoader.cacheJsonMappings) {
            MappingDataLoader.MAPPINGS_CACHE.put(s, jsonObject2);
        }
        final JsonObject jsonObject3 = jsonObject2;
        inputStreamReader.close();
        return jsonObject3;
    }
    
    public static void mapIdentifiers(final Int2IntBiMap int2IntBiMap, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final boolean b) {
        final Object2IntMap indexedObjectToMap = indexedObjectToMap(jsonObject2);
        for (final Map.Entry<String, V> entry : jsonObject.entrySet()) {
            final int mapIdentifierEntry = mapIdentifierEntry(entry, indexedObjectToMap, jsonObject3, b);
            if (mapIdentifierEntry != -1) {
                int2IntBiMap.put(Integer.parseInt(entry.getKey()), mapIdentifierEntry);
            }
        }
    }
    
    @Deprecated
    public static void mapIdentifiers(final int[] array, final JsonObject jsonObject, final JsonObject jsonObject2) {
        mapIdentifiers(array, jsonObject, jsonObject2, null);
    }
    
    public static void mapIdentifiers(final int[] array, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final boolean b) {
        final Object2IntMap indexedObjectToMap = indexedObjectToMap(jsonObject2);
        for (final Map.Entry<String, V> entry : jsonObject.entrySet()) {
            final int mapIdentifierEntry = mapIdentifierEntry(entry, indexedObjectToMap, jsonObject3, b);
            if (mapIdentifierEntry != -1) {
                array[Integer.parseInt(entry.getKey())] = mapIdentifierEntry;
            }
        }
    }
    
    public static void mapIdentifiers(final int[] array, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        mapIdentifiers(array, jsonObject, jsonObject2, jsonObject3, true);
    }
    
    private static int mapIdentifierEntry(final Map.Entry entry, final Object2IntMap object2IntMap, final JsonObject jsonObject, final boolean b) {
        int n = object2IntMap.getInt(entry.getValue().getAsString());
        if (n == -1) {
            if (jsonObject != null) {
                final JsonElement value = jsonObject.get((String)entry.getKey());
                if (value != null) {
                    n = object2IntMap.getInt(value.getAsString());
                }
            }
            if (n == -1) {
                if ((b && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                }
                return -1;
            }
        }
        return n;
    }
    
    @Deprecated
    public static void mapIdentifiers(final int[] array, final JsonArray jsonArray, final JsonArray jsonArray2, final boolean b) {
        mapIdentifiers(array, jsonArray, jsonArray2, null, b);
    }
    
    public static void mapIdentifiers(final int[] array, final JsonArray jsonArray, final JsonArray jsonArray2, final JsonObject jsonObject, final boolean b) {
        final Object2IntMap arrayToMap = arrayToMap(jsonArray2);
        while (0 < jsonArray.size()) {
            final JsonElement value = jsonArray.get(0);
            int n = arrayToMap.getInt(value.getAsString());
            Label_0167: {
                if (n == -1) {
                    if (jsonObject != null) {
                        final JsonElement value2 = jsonObject.get(value.getAsString());
                        if (value2 != null) {
                            final String asString = value2.getAsString();
                            if (asString.isEmpty()) {
                                break Label_0167;
                            }
                            n = arrayToMap.getInt(asString);
                        }
                    }
                    if (n == -1) {
                        if ((b && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                            Via.getPlatform().getLogger().warning("No key for " + value + " :( ");
                        }
                        break Label_0167;
                    }
                }
                array[0] = n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public static Object2IntMap indexedObjectToMap(final JsonObject jsonObject) {
        final Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(jsonObject.size(), 0.99f);
        object2IntOpenHashMap.defaultReturnValue(-1);
        for (final Map.Entry<K, JsonElement> entry : jsonObject.entrySet()) {
            object2IntOpenHashMap.put(entry.getValue().getAsString(), Integer.parseInt((String)entry.getKey()));
        }
        return object2IntOpenHashMap;
    }
    
    public static Object2IntMap arrayToMap(final JsonArray jsonArray) {
        final Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(jsonArray.size(), 0.99f);
        object2IntOpenHashMap.defaultReturnValue(-1);
        while (0 < jsonArray.size()) {
            object2IntOpenHashMap.put(jsonArray.get(0).getAsString(), 0);
            int n = 0;
            ++n;
        }
        return object2IntOpenHashMap;
    }
    
    public static InputStream getResource(final String s) {
        return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + s);
    }
    
    static {
        MAPPINGS_CACHE = new ConcurrentHashMap();
    }
}
