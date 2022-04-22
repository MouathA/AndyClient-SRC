package com.google.common.reflect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import java.util.*;
import java.lang.reflect.*;

@Beta
public abstract class Invokable extends Element implements GenericDeclaration
{
    Invokable(final AccessibleObject accessibleObject) {
        super(accessibleObject);
    }
    
    public static Invokable from(final Method method) {
        return new MethodInvokable(method);
    }
    
    public static Invokable from(final Constructor constructor) {
        return new ConstructorInvokable(constructor);
    }
    
    public abstract boolean isOverridable();
    
    public abstract boolean isVarArgs();
    
    public final Object invoke(@Nullable final Object o, final Object... array) throws InvocationTargetException, IllegalAccessException {
        return this.invokeInternal(o, (Object[])Preconditions.checkNotNull(array));
    }
    
    public final TypeToken getReturnType() {
        return TypeToken.of(this.getGenericReturnType());
    }
    
    public final ImmutableList getParameters() {
        final Type[] genericParameterTypes = this.getGenericParameterTypes();
        final Annotation[][] parameterAnnotations = this.getParameterAnnotations();
        final ImmutableList.Builder builder = ImmutableList.builder();
        while (0 < genericParameterTypes.length) {
            builder.add((Object)new Parameter(this, 0, TypeToken.of(genericParameterTypes[0]), parameterAnnotations[0]));
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    public final ImmutableList getExceptionTypes() {
        final ImmutableList.Builder builder = ImmutableList.builder();
        final Type[] genericExceptionTypes = this.getGenericExceptionTypes();
        while (0 < genericExceptionTypes.length) {
            builder.add((Object)TypeToken.of(genericExceptionTypes[0]));
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    public final Invokable returning(final Class clazz) {
        return this.returning(TypeToken.of(clazz));
    }
    
    public final Invokable returning(final TypeToken typeToken) {
        if (!typeToken.isAssignableFrom(this.getReturnType())) {
            throw new IllegalArgumentException("Invokable is known to return " + this.getReturnType() + ", not " + typeToken);
        }
        return this;
    }
    
    @Override
    public final Class getDeclaringClass() {
        return super.getDeclaringClass();
    }
    
    @Override
    public TypeToken getOwnerType() {
        return TypeToken.of(this.getDeclaringClass());
    }
    
    abstract Object invokeInternal(@Nullable final Object p0, final Object[] p1) throws InvocationTargetException, IllegalAccessException;
    
    abstract Type[] getGenericParameterTypes();
    
    abstract Type[] getGenericExceptionTypes();
    
    abstract Annotation[][] getParameterAnnotations();
    
    abstract Type getGenericReturnType();
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    static class ConstructorInvokable extends Invokable
    {
        final Constructor constructor;
        
        ConstructorInvokable(final Constructor constructor) {
            super(constructor);
            this.constructor = constructor;
        }
        
        @Override
        final Object invokeInternal(@Nullable final Object o, final Object[] array) throws InvocationTargetException, IllegalAccessException {
            return this.constructor.newInstance(array);
        }
        
        @Override
        Type getGenericReturnType() {
            final Class declaringClass = this.getDeclaringClass();
            final TypeVariable[] typeParameters = declaringClass.getTypeParameters();
            if (typeParameters.length > 0) {
                return Types.newParameterizedType(declaringClass, (Type[])typeParameters);
            }
            return declaringClass;
        }
        
        @Override
        Type[] getGenericParameterTypes() {
            final Type[] genericParameterTypes = this.constructor.getGenericParameterTypes();
            if (genericParameterTypes.length > 0 && this != null) {
                final Class[] parameterTypes = this.constructor.getParameterTypes();
                if (genericParameterTypes.length == parameterTypes.length && parameterTypes[0] == this.getDeclaringClass().getEnclosingClass()) {
                    return Arrays.copyOfRange(genericParameterTypes, 1, genericParameterTypes.length);
                }
            }
            return genericParameterTypes;
        }
        
        @Override
        Type[] getGenericExceptionTypes() {
            return this.constructor.getGenericExceptionTypes();
        }
        
        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.constructor.getParameterAnnotations();
        }
        
        @Override
        public final TypeVariable[] getTypeParameters() {
            final TypeVariable[] typeParameters = this.getDeclaringClass().getTypeParameters();
            final TypeVariable[] typeParameters2 = this.constructor.getTypeParameters();
            final TypeVariable[] array = new TypeVariable[typeParameters.length + typeParameters2.length];
            System.arraycopy(typeParameters, 0, array, 0, typeParameters.length);
            System.arraycopy(typeParameters2, 0, array, typeParameters.length, typeParameters2.length);
            return array;
        }
        
        @Override
        public final boolean isOverridable() {
            return false;
        }
        
        @Override
        public final boolean isVarArgs() {
            return this.constructor.isVarArgs();
        }
    }
    
    static class MethodInvokable extends Invokable
    {
        final Method method;
        
        MethodInvokable(final Method method) {
            super(method);
            this.method = method;
        }
        
        @Override
        final Object invokeInternal(@Nullable final Object o, final Object[] array) throws InvocationTargetException, IllegalAccessException {
            return this.method.invoke(o, array);
        }
        
        @Override
        Type getGenericReturnType() {
            return this.method.getGenericReturnType();
        }
        
        @Override
        Type[] getGenericParameterTypes() {
            return this.method.getGenericParameterTypes();
        }
        
        @Override
        Type[] getGenericExceptionTypes() {
            return this.method.getGenericExceptionTypes();
        }
        
        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.method.getParameterAnnotations();
        }
        
        @Override
        public final TypeVariable[] getTypeParameters() {
            return this.method.getTypeParameters();
        }
        
        @Override
        public final boolean isOverridable() {
            return !this.isFinal() && !this.isPrivate() && !this.isStatic() && !Modifier.isFinal(this.getDeclaringClass().getModifiers());
        }
        
        @Override
        public final boolean isVarArgs() {
            return this.method.isVarArgs();
        }
    }
}
