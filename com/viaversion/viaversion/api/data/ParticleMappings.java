package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class ParticleMappings
{
    private final Object2IntMap stringToId;
    private final Object2IntMap mappedStringToId;
    private final Mappings mappings;
    private final IntList itemParticleIds;
    private final IntList blockParticleIds;
    
    public ParticleMappings(final JsonArray jsonArray, final JsonArray jsonArray2, final Mappings mappings) {
        this.itemParticleIds = new IntArrayList(2);
        this.blockParticleIds = new IntArrayList(4);
        this.mappings = mappings;
        this.stringToId = MappingDataLoader.arrayToMap(jsonArray);
        this.mappedStringToId = MappingDataLoader.arrayToMap(jsonArray2);
        this.stringToId.defaultReturnValue(-1);
        this.mappedStringToId.defaultReturnValue(-1);
        this.addBlockParticle("block");
        this.addBlockParticle("falling_dust");
        this.addBlockParticle("block_marker");
        this.addItemParticle("item");
    }
    
    public int id(final String s) {
        return this.stringToId.getInt(s);
    }
    
    public int mappedId(final String s) {
        return this.mappedStringToId.getInt(s);
    }
    
    public Mappings getMappings() {
        return this.mappings;
    }
    
    public boolean addItemParticle(final String s) {
        final int id = this.id(s);
        return id != -1 && this.itemParticleIds.add(id);
    }
    
    public boolean addBlockParticle(final String s) {
        final int id = this.id(s);
        return id != -1 && this.blockParticleIds.add(id);
    }
    
    public boolean isBlockParticle(final int n) {
        return this.blockParticleIds.contains(n);
    }
    
    public boolean isItemParticle(final int n) {
        return this.itemParticleIds.contains(n);
    }
    
    @Deprecated
    public int getBlockId() {
        return this.id("block");
    }
    
    @Deprecated
    public int getFallingDustId() {
        return this.id("falling_dust");
    }
    
    @Deprecated
    public int getItemId() {
        return this.id("item");
    }
}
