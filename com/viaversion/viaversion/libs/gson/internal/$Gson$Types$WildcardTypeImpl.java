package com.viaversion.viaversion.libs.gson.internal;

import java.io.*;
import java.lang.reflect.*;

private static final class WildcardTypeImpl implements WildcardType, Serializable
{
    private final Type upperBound;
    private final Type lowerBound;
    private static final long serialVersionUID = 0L;
    
    public WildcardTypeImpl(final Type[] array, final Type[] array2) {
        $Gson$Preconditions.checkArgument(array2.length <= 1);
        $Gson$Preconditions.checkArgument(array.length == 1);
        if (array2.length == 1) {
            $Gson$Preconditions.checkNotNull(array2[0]);
            $Gson$Types.checkNotPrimitive(array2[0]);
            $Gson$Preconditions.checkArgument(array[0] == Object.class);
            this.lowerBound = $Gson$Types.canonicalize(array2[0]);
            this.upperBound = Object.class;
        }
        else {
            $Gson$Preconditions.checkNotNull(array[0]);
            $Gson$Types.checkNotPrimitive(array[0]);
            this.lowerBound = null;
            this.upperBound = $Gson$Types.canonicalize(array[0]);
        }
    }
    
    @Override
    public Type[] getUpperBounds() {
        return new Type[] { this.upperBound };
    }
    
    @Override
    public Type[] getLowerBounds() {
        return (this.lowerBound != null) ? new Type[] { this.lowerBound } : $Gson$Types.EMPTY_TYPE_ARRAY;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof WildcardType && $Gson$Types.equals(this, (Type)o);
    }
    
    @Override
    public int hashCode() {
        return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound.hashCode();
    }
    
    @Override
    public String toString() {
        if (this.lowerBound != null) {
            return "? super " + $Gson$Types.typeToString(this.lowerBound);
        }
        if (this.upperBound == Object.class) {
            return "?";
        }
        return "? extends " + $Gson$Types.typeToString(this.upperBound);
    }
}
