package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import org.apache.commons.lang3.*;

public abstract class TypeLiteral implements Typed
{
    private static final TypeVariable T;
    public final Type value;
    private final String toString;
    
    protected TypeLiteral() {
        this.value = (Type)Validate.notNull(TypeUtils.getTypeArguments(this.getClass(), TypeLiteral.class).get(TypeLiteral.T), "%s does not assign type parameter %s", this.getClass(), TypeUtils.toLongString(TypeLiteral.T));
        this.toString = String.format("%s<%s>", TypeLiteral.class.getSimpleName(), TypeUtils.toString(this.value));
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o == this || (o instanceof TypeLiteral && TypeUtils.equals(this.value, ((TypeLiteral)o).value));
    }
    
    @Override
    public int hashCode() {
        return 0x250 | this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    @Override
    public Type getType() {
        return this.value;
    }
    
    static {
        T = TypeLiteral.class.getTypeParameters()[0];
    }
}
