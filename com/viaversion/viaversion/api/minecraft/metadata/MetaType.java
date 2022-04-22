package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.type.*;

public interface MetaType
{
    Type type();
    
    int typeId();
    
    default MetaType create(final int n, final Type type) {
        return new MetaTypeImpl(n, type);
    }
    
    public static final class MetaTypeImpl implements MetaType
    {
        private final int typeId;
        private final Type type;
        
        MetaTypeImpl(final int typeId, final Type type) {
            this.typeId = typeId;
            this.type = type;
        }
        
        @Override
        public int typeId() {
            return this.typeId;
        }
        
        @Override
        public Type type() {
            return this.type;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final MetaTypeImpl metaTypeImpl = (MetaTypeImpl)o;
            return this.typeId == metaTypeImpl.typeId && this.type.equals(metaTypeImpl.type);
        }
        
        @Override
        public int hashCode() {
            return 31 * this.typeId + this.type.hashCode();
        }
    }
}
