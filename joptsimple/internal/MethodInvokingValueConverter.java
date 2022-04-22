package joptsimple.internal;

import joptsimple.*;
import java.lang.reflect.*;

class MethodInvokingValueConverter implements ValueConverter
{
    private final Method method;
    private final Class clazz;
    
    MethodInvokingValueConverter(final Method method, final Class clazz) {
        this.method = method;
        this.clazz = clazz;
    }
    
    public Object convert(final String s) {
        return this.clazz.cast(Reflection.invoke(this.method, s));
    }
    
    public Class valueType() {
        return this.clazz;
    }
    
    public String valuePattern() {
        return null;
    }
}
