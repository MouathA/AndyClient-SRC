package com.google.common.reflect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

@Beta
public abstract class TypeParameter extends TypeCapture
{
    final TypeVariable typeVariable;
    
    protected TypeParameter() {
        final Type capture = this.capture();
        Preconditions.checkArgument(capture instanceof TypeVariable, "%s should be a type variable.", capture);
        this.typeVariable = (TypeVariable)capture;
    }
    
    @Override
    public final int hashCode() {
        return this.typeVariable.hashCode();
    }
    
    @Override
    public final boolean equals(@Nullable final Object o) {
        return o instanceof TypeParameter && this.typeVariable.equals(((TypeParameter)o).typeVariable);
    }
    
    @Override
    public String toString() {
        return this.typeVariable.toString();
    }
}
