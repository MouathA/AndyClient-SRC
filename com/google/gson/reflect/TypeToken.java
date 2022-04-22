package com.google.gson.reflect;

import com.google.gson.internal.*;
import java.util.*;
import java.lang.reflect.*;

public class TypeToken
{
    final Class rawType;
    final Type type;
    final int hashCode;
    
    protected TypeToken() {
        this.type = getSuperclassTypeParameter(this.getClass());
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }
    
    TypeToken(final Type type) {
        this.type = $Gson$Types.canonicalize((Type)$Gson$Preconditions.checkNotNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }
    
    static Type getSuperclassTypeParameter(final Class clazz) {
        final Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return $Gson$Types.canonicalize(((ParameterizedType)genericSuperclass).getActualTypeArguments()[0]);
    }
    
    public final Class getRawType() {
        return this.rawType;
    }
    
    public final Type getType() {
        return this.type;
    }
    
    @Deprecated
    public boolean isAssignableFrom(final Class clazz) {
        return this.isAssignableFrom((Type)clazz);
    }
    
    @Deprecated
    public boolean isAssignableFrom(final Type type) {
        if (type == null) {
            return false;
        }
        if (this.type.equals(type)) {
            return true;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type));
        }
        if (this.type instanceof ParameterizedType) {
            return isAssignableFrom(type, (ParameterizedType)this.type, new HashMap());
        }
        if (this.type instanceof GenericArrayType) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type)) && isAssignableFrom(type, (GenericArrayType)this.type);
        }
        throw buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
    }
    
    @Deprecated
    public boolean isAssignableFrom(final TypeToken typeToken) {
        return this.isAssignableFrom(typeToken.getType());
    }
    
    private static boolean isAssignableFrom(final Type type, final GenericArrayType genericArrayType) {
        final Type genericComponentType = genericArrayType.getGenericComponentType();
        if (genericComponentType instanceof ParameterizedType) {
            Type genericComponentType2 = type;
            if (type instanceof GenericArrayType) {
                genericComponentType2 = ((GenericArrayType)type).getGenericComponentType();
            }
            else if (type instanceof Class) {
                Class componentType;
                for (componentType = (Class)type; componentType.isArray(); componentType = componentType.getComponentType()) {}
                genericComponentType2 = componentType;
            }
            return isAssignableFrom(genericComponentType2, (ParameterizedType)genericComponentType, new HashMap());
        }
        return true;
    }
    
    private static boolean isAssignableFrom(final Type type, final ParameterizedType parameterizedType, final Map map) {
        if (type == null) {
            return false;
        }
        if (parameterizedType.equals(type)) {
            return true;
        }
        final Class rawType = $Gson$Types.getRawType(type);
        ParameterizedType parameterizedType2 = null;
        if (type instanceof ParameterizedType) {
            parameterizedType2 = (ParameterizedType)type;
        }
        int n = 0;
        if (parameterizedType2 != null) {
            final Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
            final TypeVariable[] typeParameters = rawType.getTypeParameters();
            while (0 < actualTypeArguments.length) {
                Type type2 = actualTypeArguments[0];
                final TypeVariable typeVariable = typeParameters[0];
                while (type2 instanceof TypeVariable) {
                    type2 = map.get(((TypeVariable)type2).getName());
                }
                map.put(typeVariable.getName(), type2);
                ++n;
            }
            if (typeEquals(parameterizedType2, parameterizedType, map)) {
                return true;
            }
        }
        final Type[] genericInterfaces = rawType.getGenericInterfaces();
        while (0 < genericInterfaces.length) {
            if (isAssignableFrom(genericInterfaces[0], parameterizedType, new HashMap(map))) {
                return true;
            }
            ++n;
        }
        return isAssignableFrom(rawType.getGenericSuperclass(), parameterizedType, new HashMap(map));
    }
    
    private static boolean typeEquals(final ParameterizedType parameterizedType, final ParameterizedType parameterizedType2, final Map map) {
        if (parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            final Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
            while (0 < actualTypeArguments.length) {
                if (!matches(actualTypeArguments[0], actualTypeArguments2[0], map)) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        return false;
    }
    
    private static AssertionError buildUnexpectedTypeError(final Type type, final Class... array) {
        final StringBuilder sb = new StringBuilder("Unexpected type. Expected one of: ");
        while (0 < array.length) {
            sb.append(array[0].getName()).append(", ");
            int n = 0;
            ++n;
        }
        sb.append("but got: ").append(type.getClass().getName()).append(", for type token: ").append(type.toString()).append('.');
        return new AssertionError((Object)sb.toString());
    }
    
    private static boolean matches(final Type type, final Type type2, final Map map) {
        return type2.equals(type) || (type instanceof TypeVariable && type2.equals(map.get(((TypeVariable)type).getName())));
    }
    
    @Override
    public final int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof TypeToken && $Gson$Types.equals(this.type, ((TypeToken)o).type);
    }
    
    @Override
    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }
    
    public static TypeToken get(final Type type) {
        return new TypeToken(type);
    }
    
    public static TypeToken get(final Class clazz) {
        return new TypeToken(clazz);
    }
}
