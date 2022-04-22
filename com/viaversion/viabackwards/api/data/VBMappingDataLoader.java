package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.util.*;
import java.io.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public final class VBMappingDataLoader
{
    public static JsonObject loadFromDataDir(final String s) {
        final File file = new File(ViaBackwards.getPlatform().getDataFolder(), s);
        if (!file.exists()) {
            return loadData(s);
        }
        final FileReader fileReader = new FileReader(file);
        final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(fileReader, JsonObject.class);
        fileReader.close();
        return jsonObject;
    }
    
    public static JsonObject loadData(final String s) {
        final InputStreamReader inputStreamReader = new InputStreamReader(VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + s));
        final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(inputStreamReader, JsonObject.class);
        inputStreamReader.close();
        return jsonObject;
    }
    
    public static void mapIdentifiers(final int[] array, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final boolean b) {
        final Object2IntMap indexedObjectToMap = MappingDataLoader.indexedObjectToMap(jsonObject2);
        for (final Map.Entry<K, JsonElement> entry : jsonObject.entrySet()) {
            final String asString = entry.getValue().getAsString();
            int n = indexedObjectToMap.getInt(asString);
            if (n == -1) {
                if (jsonObject3 != null) {
                    final JsonPrimitive asJsonPrimitive = jsonObject3.getAsJsonPrimitive(asString);
                    String s = (asJsonPrimitive != null) ? asJsonPrimitive.getAsString() : null;
                    final int index;
                    final JsonPrimitive asJsonPrimitive2;
                    if (s == null && (index = asString.indexOf(91)) != -1 && (asJsonPrimitive2 = jsonObject3.getAsJsonPrimitive(asString.substring(0, index))) != null) {
                        s = asJsonPrimitive2.getAsString();
                        if (s.endsWith("[")) {
                            s += asString.substring(index + 1);
                        }
                    }
                    if (s != null) {
                        n = indexedObjectToMap.getInt(s);
                    }
                }
                if (n == -1) {
                    if ((b && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                        continue;
                    }
                    continue;
                }
            }
            array[Integer.parseInt((String)entry.getKey())] = n;
        }
    }
    
    public static Map objectToNamespacedMap(final JsonObject jsonObject) {
        final HashMap<String, String> hashMap = new HashMap<String, String>(jsonObject.size());
        for (final Map.Entry<String, V> entry : jsonObject.entrySet()) {
            String string = entry.getKey();
            if (string.indexOf(58) == -1) {
                string = "minecraft:" + string;
            }
            String s = ((JsonElement)entry.getValue()).getAsString();
            if (s.indexOf(58) == -1) {
                s = "minecraft:" + s;
            }
            hashMap.put(string, s);
        }
        return hashMap;
    }
    
    public static Map objectToMap(final JsonObject jsonObject) {
        final HashMap<String, String> hashMap = new HashMap<String, String>(jsonObject.size());
        for (final Map.Entry<String, V> entry : jsonObject.entrySet()) {
            hashMap.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
        }
        return hashMap;
    }
    
    public static Int2ObjectMap loadItemMappings(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final boolean b) {
        final Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(jsonObject3.size(), 0.99f);
        final Object2IntMap indexedObjectToMap = MappingDataLoader.indexedObjectToMap(jsonObject2);
        final Object2IntMap indexedObjectToMap2 = MappingDataLoader.indexedObjectToMap(jsonObject);
        for (final Map.Entry<K, JsonElement> entry : jsonObject3.entrySet()) {
            final JsonObject asJsonObject = entry.getValue().getAsJsonObject();
            final String asString = asJsonObject.getAsJsonPrimitive("id").getAsString();
            final int int1 = indexedObjectToMap.getInt(asString);
            if (int1 == -1) {
                if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) {
                    continue;
                }
                ViaBackwards.getPlatform().getLogger().warning("No key for " + asString + " :( ");
            }
            else {
                final int int2 = indexedObjectToMap2.getInt(entry.getKey());
                if (int2 == -1) {
                    if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) {
                        continue;
                    }
                    ViaBackwards.getPlatform().getLogger().warning("No old entry for " + asString + " :( ");
                }
                else {
                    int2ObjectOpenHashMap.put(int2, new MappedItem(int1, asJsonObject.getAsJsonPrimitive("name").getAsString()));
                }
            }
        }
        if (b && !Via.getConfig().isSuppressConversionWarnings()) {
            for (final Object2IntMap.Entry entry2 : indexedObjectToMap2.object2IntEntrySet()) {
                if (!indexedObjectToMap.containsKey(entry2.getKey()) && !int2ObjectOpenHashMap.containsKey(entry2.getIntValue())) {
                    ViaBackwards.getPlatform().getLogger().warning("No item mapping for " + entry2.getKey() + " :( ");
                }
            }
        }
        return int2ObjectOpenHashMap;
    }
}
