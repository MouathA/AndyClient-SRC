package joptsimple.internal;

import joptsimple.*;
import java.lang.reflect.*;

class ConstructorInvokingValueConverter implements ValueConverter
{
    private final Constructor ctor;
    
    ConstructorInvokingValueConverter(final Constructor ctor) {
        this.ctor = ctor;
    }
    
    public Object convert(final String s) {
        return Reflection.instantiate(this.ctor, s);
    }
    
    public Class valueType() {
        return this.ctor.getDeclaringClass();
    }
    
    public String valuePattern() {
        return null;
    }
}
