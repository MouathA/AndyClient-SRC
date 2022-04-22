package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import java.util.logging.*;
import com.viaversion.viaversion.api.*;

public class MappingDataBase implements MappingData
{
    protected final String oldVersion;
    protected final String newVersion;
    protected final boolean hasDiffFile;
    protected Int2IntBiMap itemMappings;
    protected ParticleMappings particleMappings;
    protected Mappings blockMappings;
    protected Mappings blockStateMappings;
    protected Mappings blockEntityMappings;
    protected Mappings soundMappings;
    protected Mappings statisticsMappings;
    protected Map tags;
    protected boolean loadItems;
    
    public MappingDataBase(final String s, final String s2) {
        this(s, s2, false);
    }
    
    public MappingDataBase(final String oldVersion, final String newVersion, final boolean hasDiffFile) {
        this.loadItems = true;
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.hasDiffFile = hasDiffFile;
    }
    
    @Override
    public void load() {
        this.getLogger().info("Loading " + this.oldVersion + " -> " + this.newVersion + " mappings...");
        final JsonObject jsonObject = this.hasDiffFile ? this.loadDiffFile() : null;
        final JsonObject loadData = MappingDataLoader.loadData("mapping-" + this.oldVersion + ".json", true);
        final JsonObject loadData2 = MappingDataLoader.loadData("mapping-" + this.newVersion + ".json", true);
        this.blockMappings = this.loadFromObject(loadData, loadData2, jsonObject, "blocks");
        this.blockStateMappings = this.loadFromObject(loadData, loadData2, jsonObject, "blockstates");
        this.blockEntityMappings = this.loadFromArray(loadData, loadData2, jsonObject, "blockentities");
        this.soundMappings = this.loadFromArray(loadData, loadData2, jsonObject, "sounds");
        this.statisticsMappings = this.loadFromArray(loadData, loadData2, jsonObject, "statistics");
        final Mappings loadFromArray = this.loadFromArray(loadData, loadData2, jsonObject, "particles");
        if (loadFromArray != null) {
            this.particleMappings = new ParticleMappings(loadData.getAsJsonArray("particles"), loadData2.getAsJsonArray("particles"), loadFromArray);
        }
        if (this.loadItems && loadData2.has("items")) {
            (this.itemMappings = new Int2IntBiHashMap()).defaultReturnValue(-1);
            MappingDataLoader.mapIdentifiers(this.itemMappings, loadData.getAsJsonObject("items"), loadData2.getAsJsonObject("items"), (jsonObject != null) ? jsonObject.getAsJsonObject("items") : null, true);
        }
        if (jsonObject != null && jsonObject.has("tags")) {
            this.tags = new EnumMap(RegistryType.class);
            final JsonObject asJsonObject = jsonObject.getAsJsonObject("tags");
            if (asJsonObject.has(RegistryType.ITEM.resourceLocation())) {
                this.loadTags(RegistryType.ITEM, asJsonObject, MappingDataLoader.indexedObjectToMap(loadData2.getAsJsonObject("items")));
            }
            if (asJsonObject.has(RegistryType.BLOCK.resourceLocation())) {
                this.loadTags(RegistryType.BLOCK, asJsonObject, MappingDataLoader.indexedObjectToMap(loadData2.getAsJsonObject("blocks")));
            }
        }
        this.loadExtras(loadData, loadData2, jsonObject);
    }
    
    private void loadTags(final RegistryType registryType, final JsonObject jsonObject, final Object2IntMap object2IntMap) {
        final JsonObject asJsonObject = jsonObject.getAsJsonObject(registryType.resourceLocation());
        final ArrayList list = new ArrayList<TagData>(asJsonObject.size());
        for (final Map.Entry<K, JsonElement> entry : asJsonObject.entrySet()) {
            final JsonArray asJsonArray = entry.getValue().getAsJsonArray();
            final int[] array = new int[asJsonArray.size()];
            final Iterator iterator2 = asJsonArray.iterator();
            while (iterator2.hasNext()) {
                String asString = iterator2.next().getAsString();
                if (!object2IntMap.containsKey(asString)) {
                    final String s = asString;
                    asString = "minecraft:";
                    if (!object2IntMap.containsKey(s)) {
                        this.getLogger().warning(registryType + " Tags contains invalid type identifier " + asString + " in tag " + (String)entry.getKey());
                        continue;
                    }
                }
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = object2IntMap.getInt(asString);
            }
            list.add(new TagData((String)entry.getKey(), array));
        }
        this.tags.put(registryType, list);
    }
    
    @Override
    public int getNewBlockStateId(final int n) {
        return this.checkValidity(n, this.blockStateMappings.getNewId(n), "blockstate");
    }
    
    @Override
    public int getNewBlockId(final int n) {
        return this.checkValidity(n, this.blockMappings.getNewId(n), "block");
    }
    
    @Override
    public int getNewItemId(final int n) {
        return this.checkValidity(n, this.itemMappings.get(n), "item");
    }
    
    @Override
    public int getOldItemId(final int n) {
        final boolean value = this.itemMappings.inverse().get(n) != 0;
        return (((value ? 1 : 0) != -1) ? value : true) ? 1 : 0;
    }
    
    @Override
    public int getNewParticleId(final int n) {
        return this.checkValidity(n, this.particleMappings.getMappings().getNewId(n), "particles");
    }
    
    @Override
    public List getTags(final RegistryType registryType) {
        return (this.tags != null) ? this.tags.get(registryType) : null;
    }
    
    @Override
    public Int2IntBiMap getItemMappings() {
        return this.itemMappings;
    }
    
    @Override
    public ParticleMappings getParticleMappings() {
        return this.particleMappings;
    }
    
    @Override
    public Mappings getBlockMappings() {
        return this.blockMappings;
    }
    
    @Override
    public Mappings getBlockEntityMappings() {
        return this.blockEntityMappings;
    }
    
    @Override
    public Mappings getBlockStateMappings() {
        return this.blockStateMappings;
    }
    
    @Override
    public Mappings getSoundMappings() {
        return this.soundMappings;
    }
    
    @Override
    public Mappings getStatisticsMappings() {
        return this.statisticsMappings;
    }
    
    protected Mappings loadFromArray(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (!jsonObject.has(s) || !jsonObject2.has(s)) {
            return null;
        }
        return IntArrayMappings.builder().unmapped(jsonObject.getAsJsonArray(s)).mapped(jsonObject2.getAsJsonArray(s)).diffMappings((jsonObject3 != null) ? jsonObject3.getAsJsonObject(s) : null).build();
    }
    
    protected Mappings loadFromObject(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (!jsonObject.has(s) || !jsonObject2.has(s)) {
            return null;
        }
        return IntArrayMappings.builder().unmapped(jsonObject.getAsJsonObject(s)).mapped(jsonObject2.getAsJsonObject(s)).diffMappings((jsonObject3 != null) ? jsonObject3.getAsJsonObject(s) : null).build();
    }
    
    protected JsonObject loadDiffFile() {
        return MappingDataLoader.loadData("mappingdiff-" + this.oldVersion + "to" + this.newVersion + ".json");
    }
    
    protected Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
    
    protected int checkValidity(final int n, final int n2, final String s) {
        if (n2 == -1) {
            this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.newVersion, s, this.oldVersion, s, n));
            return 0;
        }
        return n2;
    }
    
    protected void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
    }
}
