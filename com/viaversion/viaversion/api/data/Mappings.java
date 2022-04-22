package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public interface Mappings
{
    int getNewId(final int p0);
    
    void setNewId(final int p0, final int p1);
    
    int size();
    
    int mappedSize();
    
    default Builder builder(final MappingsSupplier mappingsSupplier) {
        return new Builder(mappingsSupplier);
    }
    
    public static class Builder
    {
        protected final MappingsSupplier supplier;
        protected JsonElement unmapped;
        protected JsonElement mapped;
        protected JsonObject diffMappings;
        protected int mappedSize;
        protected int size;
        protected boolean warnOnMissing;
        
        protected Builder(final MappingsSupplier supplier) {
            this.mappedSize = -1;
            this.size = -1;
            this.warnOnMissing = true;
            this.supplier = supplier;
        }
        
        public Builder customEntrySize(final int size) {
            this.size = size;
            return this;
        }
        
        public Builder customMappedSize(final int mappedSize) {
            this.mappedSize = mappedSize;
            return this;
        }
        
        public Builder warnOnMissing(final boolean warnOnMissing) {
            this.warnOnMissing = warnOnMissing;
            return this;
        }
        
        public Builder unmapped(final JsonArray unmapped) {
            this.unmapped = unmapped;
            return this;
        }
        
        public Builder unmapped(final JsonObject unmapped) {
            this.unmapped = unmapped;
            return this;
        }
        
        public Builder mapped(final JsonArray mapped) {
            this.mapped = mapped;
            return this;
        }
        
        public Builder mapped(final JsonObject mapped) {
            this.mapped = mapped;
            return this;
        }
        
        public Builder diffMappings(final JsonObject diffMappings) {
            this.diffMappings = diffMappings;
            return this;
        }
        
        public Mappings build() {
            final int n = (this.size != -1) ? this.size : this.size(this.unmapped);
            final int n2 = (this.mappedSize != -1) ? this.mappedSize : this.size(this.mapped);
            final int[] array = new int[n];
            Arrays.fill(array, -1);
            if (this.unmapped.isJsonArray()) {
                if (this.mapped.isJsonObject()) {
                    MappingDataLoader.mapIdentifiers(array, this.toJsonObject(this.unmapped.getAsJsonArray()), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
                }
                else {
                    MappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonArray(), this.mapped.getAsJsonArray(), this.diffMappings, this.warnOnMissing);
                }
            }
            else if (this.mapped.isJsonArray()) {
                MappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonObject(), this.toJsonObject(this.mapped.getAsJsonArray()), this.diffMappings, this.warnOnMissing);
            }
            else {
                MappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonObject(), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
            }
            return this.supplier.supply(array, n2);
        }
        
        protected int size(final JsonElement jsonElement) {
            return jsonElement.isJsonObject() ? jsonElement.getAsJsonObject().size() : jsonElement.getAsJsonArray().size();
        }
        
        protected JsonObject toJsonObject(final JsonArray jsonArray) {
            final JsonObject jsonObject = new JsonObject();
            while (0 < jsonArray.size()) {
                jsonObject.add(Integer.toString(0), jsonArray.get(0));
                int n = 0;
                ++n;
            }
            return jsonObject;
        }
    }
    
    @FunctionalInterface
    public interface MappingsSupplier
    {
        Mappings supply(final int[] p0, final int p1);
    }
}
