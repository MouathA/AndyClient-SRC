package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.*;

public final class FieldAttributes
{
    private final Field field;
    
    public FieldAttributes(final Field field) {
        $Gson$Preconditions.checkNotNull(field);
        this.field = field;
    }
    
    public Class getDeclaringClass() {
        return this.field.getDeclaringClass();
    }
    
    public String getName() {
        return this.field.getName();
    }
    
    public Type getDeclaredType() {
        return this.field.getGenericType();
    }
    
    public Class getDeclaredClass() {
        return this.field.getType();
    }
    
    public Annotation getAnnotation(final Class clazz) {
        return this.field.getAnnotation((Class<Annotation>)clazz);
    }
    
    public Collection getAnnotations() {
        return Arrays.asList(this.field.getAnnotations());
    }
    
    public boolean hasModifier(final int n) {
        return (this.field.getModifiers() & n) != 0x0;
    }
    
    Object get(final Object o) throws IllegalAccessException {
        return this.field.get(o);
    }
    
    boolean isSynthetic() {
        return this.field.isSynthetic();
    }
}
