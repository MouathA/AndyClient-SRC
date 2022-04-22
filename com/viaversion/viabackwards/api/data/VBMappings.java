package com.viaversion.viabackwards.api.data;

import java.util.*;
import com.viaversion.viaversion.api.data.*;

public final class VBMappings extends IntArrayMappings
{
    private VBMappings(final int[] array, final int n) {
        super(array, n);
    }
    
    public static Mappings.Builder vbBuilder() {
        return new Builder(VBMappings::new, null);
    }
    
    public static final class Builder extends Mappings.Builder
    {
        private Builder(final Mappings.MappingsSupplier mappingsSupplier) {
            super(mappingsSupplier);
        }
        
        @Override
        public VBMappings build() {
            final int n = (this.size != -1) ? this.size : this.size(this.unmapped);
            final int n2 = (this.mappedSize != -1) ? this.mappedSize : this.size(this.mapped);
            final int[] array = new int[n];
            Arrays.fill(array, -1);
            if (this.unmapped.isJsonArray()) {
                if (this.mapped.isJsonObject()) {
                    VBMappingDataLoader.mapIdentifiers(array, this.toJsonObject(this.unmapped.getAsJsonArray()), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
                }
                else {
                    MappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonArray(), this.mapped.getAsJsonArray(), this.diffMappings, this.warnOnMissing);
                }
            }
            else if (this.mapped.isJsonArray()) {
                VBMappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonObject(), this.toJsonObject(this.mapped.getAsJsonArray()), this.diffMappings, this.warnOnMissing);
            }
            else {
                VBMappingDataLoader.mapIdentifiers(array, this.unmapped.getAsJsonObject(), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
            }
            return (VBMappings)this.supplier.supply(array, n2);
        }
        
        @Override
        public Mappings build() {
            return this.build();
        }
        
        Builder(final Mappings.MappingsSupplier mappingsSupplier, final VBMappings$1 object) {
            this(mappingsSupplier);
        }
    }
}
