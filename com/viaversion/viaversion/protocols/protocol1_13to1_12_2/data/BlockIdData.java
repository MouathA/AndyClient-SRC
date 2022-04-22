package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public class BlockIdData
{
    public static Map blockIdMapping;
    public static Map fallbackReverseMapping;
    public static Int2ObjectMap numberIdToString;
    
    public static void init() {
        final InputStreamReader inputStreamReader = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json"));
        final Object o = null;
        BlockIdData.blockIdMapping = new HashMap((Map<?, ?>)GsonUtil.getGson().fromJson(inputStreamReader, new TypeToken() {}.getType()));
        BlockIdData.fallbackReverseMapping = new HashMap();
        for (final Map.Entry<K, String[]> entry : BlockIdData.blockIdMapping.entrySet()) {
            final String[] array = entry.getValue();
            while (0 < array.length) {
                final String s = array[0];
                String[] previous = BlockIdData.fallbackReverseMapping.get(s);
                if (previous == null) {
                    previous = BlockIdData.PREVIOUS;
                }
                BlockIdData.fallbackReverseMapping.put(s, ObjectArrays.concat(previous, entry.getKey()));
                int n = 0;
                ++n;
            }
        }
        if (inputStreamReader != null) {
            if (o != null) {
                inputStreamReader.close();
            }
            else {
                inputStreamReader.close();
            }
        }
        final InputStreamReader inputStreamReader2 = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json"));
        final Object o2 = null;
        BlockIdData.numberIdToString = new Int2ObjectOpenHashMap((Map)GsonUtil.getGson().fromJson(inputStreamReader2, new TypeToken() {}.getType()));
        if (inputStreamReader2 != null) {
            if (o2 != null) {
                inputStreamReader2.close();
            }
            else {
                inputStreamReader2.close();
            }
        }
    }
    
    static {
        BlockIdData.PREVIOUS = new String[0];
    }
}
