package com.viaversion.viaversion.api.data;

import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class IntArrayMappings implements Mappings
{
    private final int[] oldToNew;
    private final int mappedIds;
    
    protected IntArrayMappings(final int[] oldToNew, final int mappedIds) {
        this.oldToNew = oldToNew;
        this.mappedIds = mappedIds;
    }
    
    public static IntArrayMappings of(final int[] array, final int n) {
        return new IntArrayMappings(array, n);
    }
    
    public static Builder builder() {
        return Mappings.builder(IntArrayMappings::new);
    }
    
    @Deprecated
    public IntArrayMappings(final int[] array) {
        this(array, -1);
    }
    
    @Deprecated
    public IntArrayMappings(final int n, final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        Arrays.fill(this.oldToNew = new int[n], -1);
        this.mappedIds = jsonObject2.size();
        MappingDataLoader.mapIdentifiers(this.oldToNew, jsonObject, jsonObject2, jsonObject3);
    }
    
    @Deprecated
    public IntArrayMappings(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        this(jsonObject.entrySet().size(), jsonObject, jsonObject2, jsonObject3);
    }
    
    @Deprecated
    public IntArrayMappings(final int n, final JsonObject jsonObject, final JsonObject jsonObject2) {
        Arrays.fill(this.oldToNew = new int[n], -1);
        this.mappedIds = -1;
        MappingDataLoader.mapIdentifiers(this.oldToNew, jsonObject, jsonObject2);
    }
    
    @Deprecated
    public IntArrayMappings(final JsonObject jsonObject, final JsonObject jsonObject2) {
        this(jsonObject.entrySet().size(), jsonObject, jsonObject2);
    }
    
    @Deprecated
    public IntArrayMappings(final int n, final JsonArray jsonArray, final JsonArray jsonArray2, final JsonObject jsonObject, final boolean b) {
        Arrays.fill(this.oldToNew = new int[n], -1);
        this.mappedIds = -1;
        MappingDataLoader.mapIdentifiers(this.oldToNew, jsonArray, jsonArray2, jsonObject, b);
    }
    
    @Deprecated
    public IntArrayMappings(final int n, final JsonArray jsonArray, final JsonArray jsonArray2, final boolean b) {
        this(n, jsonArray, jsonArray2, null, b);
    }
    
    @Deprecated
    public IntArrayMappings(final JsonArray jsonArray, final JsonArray jsonArray2, final boolean b) {
        this(jsonArray.size(), jsonArray, jsonArray2, b);
    }
    
    @Deprecated
    public IntArrayMappings(final int n, final JsonArray jsonArray, final JsonArray jsonArray2) {
        this(n, jsonArray, jsonArray2, true);
    }
    
    @Deprecated
    public IntArrayMappings(final JsonArray jsonArray, final JsonArray jsonArray2, final JsonObject jsonObject) {
        this(jsonArray.size(), jsonArray, jsonArray2, jsonObject, true);
    }
    
    @Deprecated
    public IntArrayMappings(final JsonArray jsonArray, final JsonArray jsonArray2) {
        this(jsonArray.size(), jsonArray, jsonArray2, true);
    }
    
    @Override
    public int getNewId(final int n) {
        return (n >= 0 && n < this.oldToNew.length) ? this.oldToNew[n] : -1;
    }
    
    @Override
    public void setNewId(final int n, final int n2) {
        this.oldToNew[n] = n2;
    }
    
    @Override
    public int size() {
        return this.oldToNew.length;
    }
    
    @Override
    public int mappedSize() {
        return this.mappedIds;
    }
    
    public int[] getOldToNew() {
        return this.oldToNew;
    }
}
