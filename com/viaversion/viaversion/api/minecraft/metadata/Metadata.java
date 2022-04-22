package com.viaversion.viaversion.api.minecraft.metadata;

import com.google.common.base.*;
import java.util.*;

public final class Metadata
{
    private int id;
    private MetaType metaType;
    private Object value;
    
    public Metadata(final int id, final MetaType metaType, final Object o) {
        this.id = id;
        this.metaType = metaType;
        this.value = this.checkValue(metaType, o);
    }
    
    public int id() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public MetaType metaType() {
        return this.metaType;
    }
    
    public void setMetaType(final MetaType metaType) {
        this.checkValue(metaType, this.value);
        this.metaType = metaType;
    }
    
    public Object value() {
        return this.value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public void setValue(final Object o) {
        this.value = this.checkValue(this.metaType, o);
    }
    
    public void setTypeAndValue(final MetaType metaType, final Object o) {
        this.value = this.checkValue(metaType, o);
        this.metaType = metaType;
    }
    
    private Object checkValue(final MetaType metaType, final Object o) {
        Preconditions.checkNotNull(metaType);
        if (o != null && !metaType.type().getOutputClass().isAssignableFrom(o.getClass())) {
            throw new IllegalArgumentException("Metadata value and metaType are incompatible. Type=" + metaType + ", value=" + o + " (" + o.getClass().getSimpleName() + ")");
        }
        return o;
    }
    
    @Deprecated
    public void setMetaTypeUnsafe(final MetaType metaType) {
        this.metaType = metaType;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Metadata metadata = (Metadata)o;
        return this.id == metadata.id && this.metaType == metadata.metaType && Objects.equals(this.value, metadata.value);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.id + this.metaType.hashCode()) + ((this.value != null) ? this.value.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
    }
}
