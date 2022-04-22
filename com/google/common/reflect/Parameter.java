package com.google.common.reflect;

import java.lang.reflect.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@Beta
public final class Parameter implements AnnotatedElement
{
    private final Invokable declaration;
    private final int position;
    private final TypeToken type;
    private final ImmutableList annotations;
    
    Parameter(final Invokable declaration, final int position, final TypeToken type, final Annotation[] array) {
        this.declaration = declaration;
        this.position = position;
        this.type = type;
        this.annotations = ImmutableList.copyOf(array);
    }
    
    public TypeToken getType() {
        return this.type;
    }
    
    public Invokable getDeclaringInvokable() {
        return this.declaration;
    }
    
    @Override
    public boolean isAnnotationPresent(final Class clazz) {
        return this.getAnnotation(clazz) != null;
    }
    
    @Nullable
    @Override
    public Annotation getAnnotation(final Class clazz) {
        Preconditions.checkNotNull(clazz);
        for (final Annotation annotation : this.annotations) {
            if (clazz.isInstance(annotation)) {
                return clazz.cast(annotation);
            }
        }
        return null;
    }
    
    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return (Annotation[])this.annotations.toArray(new Annotation[this.annotations.size()]);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof Parameter) {
            final Parameter parameter = (Parameter)o;
            return this.position == parameter.position && this.declaration.equals(parameter.declaration);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.position;
    }
    
    @Override
    public String toString() {
        return this.type + " arg" + this.position;
    }
}
