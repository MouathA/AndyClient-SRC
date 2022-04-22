package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class MappingData extends MappingDataBase
{
    private final Map blockTags;
    private final Map itemTags;
    private final Map fluidTags;
    private final BiMap oldEnchantmentsIds;
    private final Map translateMapping;
    private final Map mojangTranslation;
    private final BiMap channelMappings;
    private Mappings enchantmentMappings;
    
    public MappingData() {
        super("1.12", "1.13");
        this.blockTags = new HashMap();
        this.itemTags = new HashMap();
        this.fluidTags = new HashMap();
        this.oldEnchantmentsIds = HashBiMap.create();
        this.translateMapping = new HashMap();
        this.mojangTranslation = new HashMap();
        this.channelMappings = HashBiMap.create();
    }
    
    public void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        this.loadTags(this.blockTags, jsonObject2.getAsJsonObject("block_tags"));
        this.loadTags(this.itemTags, jsonObject2.getAsJsonObject("item_tags"));
        this.loadTags(this.fluidTags, jsonObject2.getAsJsonObject("fluid_tags"));
        this.loadEnchantments(this.oldEnchantmentsIds, jsonObject.getAsJsonObject("enchantments"));
        this.enchantmentMappings = IntArrayMappings.builder().customEntrySize(72).unmapped(jsonObject.getAsJsonObject("enchantments")).mapped(jsonObject2.getAsJsonObject("enchantments")).build();
        if (Via.getConfig().isSnowCollisionFix()) {
            this.blockMappings.setNewId(1248, 3416);
        }
        if (Via.getConfig().isInfestedBlocksFix()) {
            this.blockMappings.setNewId(1552, 1);
            this.blockMappings.setNewId(1553, 14);
            this.blockMappings.setNewId(1554, 3983);
            this.blockMappings.setNewId(1555, 3984);
            this.blockMappings.setNewId(1556, 3985);
            this.blockMappings.setNewId(1557, 3986);
        }
        final JsonObject loadFromDataDir = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json");
        if (loadFromDataDir != null) {
            for (final Map.Entry<String, V> entry : loadFromDataDir.entrySet()) {
                final String s = entry.getKey();
                final String asString = ((JsonElement)entry.getValue()).getAsString();
                if (!isValid1_13Channel(asString)) {
                    Via.getPlatform().getLogger().warning("Channel '" + asString + "' is not a valid 1.13 plugin channel, please check your configuration!");
                }
                else {
                    this.channelMappings.put(s, asString);
                }
            }
        }
        final Map map = (Map)GsonUtil.getGson().fromJson(new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")), new TypeToken() {
            final MappingData this$0;
        }.getType());
        final InputStreamReader inputStreamReader = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8);
        final Object o = null;
        final String[] split = CharStreams.toString(inputStreamReader).split("\n");
        if (inputStreamReader != null) {
            if (o != null) {
                inputStreamReader.close();
            }
            else {
                inputStreamReader.close();
            }
        }
        final String[] array = split;
        while (0 < array.length) {
            final String s2 = array[0];
            if (!s2.isEmpty()) {
                final String[] split2 = s2.split("=", 2);
                if (split2.length == 2) {
                    final String s3 = split2[0];
                    if (!map.containsKey(s3)) {
                        this.mojangTranslation.put(s3, split2[1].replaceAll("%(\\d\\$)?d", "%$1s"));
                    }
                    else {
                        final String s4 = map.get(s3);
                        if (s4 != null) {
                            this.translateMapping.put(s3, s4);
                        }
                    }
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected Mappings loadFromObject(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (s.equals("blocks")) {
            return IntArrayMappings.builder().customEntrySize(4084).unmapped(jsonObject.getAsJsonObject("blocks")).mapped(jsonObject2.getAsJsonObject("blockstates")).build();
        }
        return super.loadFromObject(jsonObject, jsonObject2, jsonObject3, s);
    }
    
    public static String validateNewChannel(final String s) {
        if (!isValid1_13Channel(s)) {
            return null;
        }
        final int index = s.indexOf(58);
        if (index == -1) {
            return "minecraft:" + s;
        }
        if (index == 0) {
            return "minecraft" + s;
        }
        return s;
    }
    
    public static boolean isValid1_13Channel(final String s) {
        return s.matches("([0-9a-z_.-]+:)?[0-9a-z_/.-]+");
    }
    
    private void loadTags(final Map map, final JsonObject jsonObject) {
        for (final Map.Entry<K, JsonElement> entry : jsonObject.entrySet()) {
            final JsonArray asJsonArray = entry.getValue().getAsJsonArray();
            final Integer[] array = new Integer[asJsonArray.size()];
            while (0 < asJsonArray.size()) {
                array[0] = asJsonArray.get(0).getAsInt();
                int n = 0;
                ++n;
            }
            map.put(entry.getKey(), array);
        }
    }
    
    private void loadEnchantments(final Map map, final JsonObject jsonObject) {
        for (final Map.Entry<String, V> entry : jsonObject.entrySet()) {
            map.put(Short.parseShort(entry.getKey()), ((JsonElement)entry.getValue()).getAsString());
        }
    }
    
    public Map getBlockTags() {
        return this.blockTags;
    }
    
    public Map getItemTags() {
        return this.itemTags;
    }
    
    public Map getFluidTags() {
        return this.fluidTags;
    }
    
    public BiMap getOldEnchantmentsIds() {
        return this.oldEnchantmentsIds;
    }
    
    public Map getTranslateMapping() {
        return this.translateMapping;
    }
    
    public Map getMojangTranslation() {
        return this.mojangTranslation;
    }
    
    public BiMap getChannelMappings() {
        return this.channelMappings;
    }
    
    public Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }
}
