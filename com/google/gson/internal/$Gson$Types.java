package com.google.gson.internal;

import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public final class $Gson$Types
{
    static final Type[] EMPTY_TYPE_ARRAY;
    
    private $Gson$Types() {
    }
    
    public static ParameterizedType newParameterizedTypeWithOwner(final Type type, final Type type2, final Type... array) {
        return new ParameterizedTypeImpl(type, type2, array);
    }
    
    public static GenericArrayType arrayOf(final Type type) {
        return new GenericArrayTypeImpl(type);
    }
    
    public static WildcardType subtypeOf(final Type type) {
        return new WildcardTypeImpl(new Type[] { type }, $Gson$Types.EMPTY_TYPE_ARRAY);
    }
    
    public static WildcardType supertypeOf(final Type type) {
        return new WildcardTypeImpl(new Type[] { Object.class }, new Type[] { type });
    }
    
    public static Type canonicalize(final Type type) {
        if (type instanceof Class) {
            final Class clazz = (Class)type;
            return (GenericArrayTypeImpl)(clazz.isArray() ? new GenericArrayTypeImpl(canonicalize(clazz.getComponentType())) : clazz);
        }
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType)type;
            return new ParameterizedTypeImpl(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(((GenericArrayType)type).getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            final WildcardType wildcardType = (WildcardType)type;
            return new WildcardTypeImpl(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
        return type;
    }
    
    public static Class getRawType(final Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            final Type rawType = ((ParameterizedType)type).getRawType();
            $Gson$Preconditions.checkArgument(rawType instanceof Class);
            return (Class)rawType;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + ((type == null) ? "null" : type.getClass().getName()));
    }
    
    static boolean equal(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    public static boolean equals(final Type type, final Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            final ParameterizedType parameterizedType = (ParameterizedType)type;
            final ParameterizedType parameterizedType2 = (ParameterizedType)type2;
            return equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        }
        else {
            if (type instanceof GenericArrayType) {
                return type2 instanceof GenericArrayType && equals(((GenericArrayType)type).getGenericComponentType(), ((GenericArrayType)type2).getGenericComponentType());
            }
            if (type instanceof WildcardType) {
                if (!(type2 instanceof WildcardType)) {
                    return false;
                }
                final WildcardType wildcardType = (WildcardType)type;
                final WildcardType wildcardType2 = (WildcardType)type2;
                return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
            }
            else {
                if (!(type instanceof TypeVariable)) {
                    return false;
                }
                if (!(type2 instanceof TypeVariable)) {
                    return false;
                }
                final TypeVariable typeVariable = (TypeVariable)type;
                final TypeVariable typeVariable2 = (TypeVariable)type2;
                return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
            }
        }
    }
    
    private static int hashCodeOrZero(final Object o) {
        return (o != null) ? o.hashCode() : 0;
    }
    
    public static String typeToString(final Type type) {
        return (type instanceof Class) ? ((Class)type).getName() : type.toString();
    }
    
    static Type getGenericSupertype(final Type type, Class clazz, final Class clazz2) {
        if (clazz2 == clazz) {
            return type;
        }
        if (clazz2.isInterface()) {
            final Class<?>[] interfaces = clazz.getInterfaces();
            while (0 < interfaces.length) {
                if (interfaces[0] == clazz2) {
                    return clazz.getGenericInterfaces()[0];
                }
                if (clazz2.isAssignableFrom(interfaces[0])) {
                    return getGenericSupertype(clazz.getGenericInterfaces()[0], interfaces[0], clazz2);
                }
                int n = 0;
                ++n;
            }
        }
        if (!clazz.isInterface()) {
            while (clazz != Object.class) {
                final Class<? super Object> superclass = clazz.getSuperclass();
                if (superclass == clazz2) {
                    return clazz.getGenericSuperclass();
                }
                if (clazz2.isAssignableFrom(superclass)) {
                    return getGenericSupertype(clazz.getGenericSuperclass(), superclass, clazz2);
                }
                clazz = (Class<Object>)superclass;
            }
        }
        return clazz2;
    }
    
    static Type getSupertype(final Type type, final Class clazz, final Class clazz2) {
        $Gson$Preconditions.checkArgument(clazz2.isAssignableFrom(clazz));
        return resolve(type, clazz, getGenericSupertype(type, clazz, clazz2));
    }
    
    public static Type getArrayComponentType(final Type type) {
        return (type instanceof GenericArrayType) ? ((GenericArrayType)type).getGenericComponentType() : ((Class)type).getComponentType();
    }
    
    public static Type getCollectionElementType(final Type type, final Class clazz) {
        Type supertype = getSupertype(type, clazz, Collection.class);
        if (supertype instanceof WildcardType) {
            supertype = ((WildcardType)supertype).getUpperBounds()[0];
        }
        if (supertype instanceof ParameterizedType) {
            return ((ParameterizedType)supertype).getActualTypeArguments()[0];
        }
        return Object.class;
    }
    
    public static Type[] getMapKeyAndValueTypes(final Type type, final Class clazz) {
        if (type == Properties.class) {
            return new Type[] { String.class, String.class };
        }
        final Type supertype = getSupertype(type, clazz, Map.class);
        if (supertype instanceof ParameterizedType) {
            return ((ParameterizedType)supertype).getActualTypeArguments();
        }
        return new Type[] { Object.class, Object.class };
    }
    
    public static Type resolve(final Type type, final Class clazz, Type resolveTypeVariable) {
        while (resolveTypeVariable instanceof TypeVariable) {
            final TypeVariable typeVariable = (TypeVariable)resolveTypeVariable;
            resolveTypeVariable = resolveTypeVariable(type, clazz, typeVariable);
            if (resolveTypeVariable == typeVariable) {
                return resolveTypeVariable;
            }
        }
        if (resolveTypeVariable instanceof Class && ((Class)resolveTypeVariable).isArray()) {
            final Class clazz2 = (Class)resolveTypeVariable;
            final Class componentType = clazz2.getComponentType();
            final Type resolve = resolve(type, clazz, componentType);
            return (Type)((componentType == resolve) ? clazz2 : arrayOf(resolve));
        }
        if (resolveTypeVariable instanceof GenericArrayType) {
            final GenericArrayType genericArrayType = (GenericArrayType)resolveTypeVariable;
            final Type genericComponentType = genericArrayType.getGenericComponentType();
            final Type resolve2 = resolve(type, clazz, genericComponentType);
            return (genericComponentType == resolve2) ? genericArrayType : arrayOf(resolve2);
        }
        if (resolveTypeVariable instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType)resolveTypeVariable;
            final Type ownerType = parameterizedType.getOwnerType();
            final Type resolve3 = resolve(type, clazz, ownerType);
            final boolean b = resolve3 != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            while (0 < actualTypeArguments.length) {
                final Type resolve4 = resolve(type, clazz, actualTypeArguments[0]);
                if (resolve4 != actualTypeArguments[0]) {
                    if (!true) {
                        actualTypeArguments = actualTypeArguments.clone();
                    }
                    actualTypeArguments[0] = resolve4;
                }
                int n = 0;
                ++n;
            }
            return true ? newParameterizedTypeWithOwner(resolve3, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
        }
        if (resolveTypeVariable instanceof WildcardType) {
            final WildcardType wildcardType = (WildcardType)resolveTypeVariable;
            final Type[] lowerBounds = wildcardType.getLowerBounds();
            final Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                final Type resolve5 = resolve(type, clazz, lowerBounds[0]);
                if (resolve5 != lowerBounds[0]) {
                    return supertypeOf(resolve5);
                }
            }
            else if (upperBounds.length == 1) {
                final Type resolve6 = resolve(type, clazz, upperBounds[0]);
                if (resolve6 != upperBounds[0]) {
                    return subtypeOf(resolve6);
                }
            }
            return wildcardType;
        }
        return resolveTypeVariable;
    }
    
    static Type resolveTypeVariable(final Type type, final Class clazz, final TypeVariable typeVariable) {
        final Class declaringClass = declaringClassOf(typeVariable);
        if (declaringClass == null) {
            return typeVariable;
        }
        final Type genericSupertype = getGenericSupertype(type, clazz, declaringClass);
        if (genericSupertype instanceof ParameterizedType) {
            return ((ParameterizedType)genericSupertype).getActualTypeArguments()[indexOf(declaringClass.getTypeParameters(), typeVariable)];
        }
        return typeVariable;
    }
    
    private static int indexOf(final Object[] array, final Object o) {
        while (0 < array.length) {
            if (o.equals(array[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        throw new NoSuchElementException();
    }
    
    private static Class declaringClassOf(final TypeVariable typeVariable) {
        final Class genericDeclaration = typeVariable.getGenericDeclaration();
        return (genericDeclaration instanceof Class) ? genericDeclaration : null;
    }
    
    private static void checkNotPrimitive(final Type type) {
        $Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
    }
    
    static void access$000(final Type type) {
        checkNotPrimitive(type);
    }
    
    static int access$100(final Object o) {
        return hashCodeOrZero(o);
    }
    
    static {
        EMPTY_TYPE_ARRAY = new Type[0];
    }
    
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
                $Gson$Types.access$000(array2[0]);
                $Gson$Preconditions.checkArgument(array[0] == Object.class);
                this.lowerBound = $Gson$Types.canonicalize(array2[0]);
                this.upperBound = Object.class;
            }
            else {
                $Gson$Preconditions.checkNotNull(array[0]);
                $Gson$Types.access$000(array[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(array[0]);
            }
        }
        
        public Type[] getUpperBounds() {
            return new Type[] { this.upperBound };
        }
        
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
    
    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable
    {
        private final Type componentType;
        private static final long serialVersionUID = 0L;
        
        public GenericArrayTypeImpl(final Type type) {
            this.componentType = $Gson$Types.canonicalize(type);
        }
        
        public Type getGenericComponentType() {
            return this.componentType;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof GenericArrayType && $Gson$Types.equals(this, (Type)o);
        }
        
        @Override
        public int hashCode() {
            return this.componentType.hashCode();
        }
        
        @Override
        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }
    
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable
    {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;
        private static final long serialVersionUID = 0L;
        
        public ParameterizedTypeImpl(final Type p0, final Type p1, final Type... p2) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokespecial   java/lang/Object.<init>:()V
            //     4: aload_2        
            //     5: instanceof      Ljava/lang/Class;
            //     8: ifeq            57
            //    11: aload_2        
            //    12: checkcast       Ljava/lang/Class;
            //    15: astore          4
            //    17: aload_1        
            //    18: ifnonnull       29
            //    21: aload           4
            //    23: invokevirtual   java/lang/Class.getEnclosingClass:()Ljava/lang/Class;
            //    26: ifnonnull       33
            //    29: iconst_1       
            //    30: goto            34
            //    33: iconst_0       
            //    34: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkArgument:(Z)V
            //    37: aload_1        
            //    38: ifnull          49
            //    41: aload           4
            //    43: invokevirtual   java/lang/Class.getEnclosingClass:()Ljava/lang/Class;
            //    46: ifnull          53
            //    49: iconst_1       
            //    50: goto            54
            //    53: iconst_0       
            //    54: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkArgument:(Z)V
            //    57: aload_0        
            //    58: aload_1        
            //    59: ifnonnull       66
            //    62: aconst_null    
            //    63: goto            70
            //    66: aload_1        
            //    67: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
            //    70: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.ownerType:Ljava/lang/reflect/Type;
            //    73: aload_0        
            //    74: aload_2        
            //    75: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
            //    78: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.rawType:Ljava/lang/reflect/Type;
            //    81: aload_0        
            //    82: aload_3        
            //    83: invokevirtual   [java/lang/reflect/Type.clone:()Ljava/lang/Object;
            //    86: checkcast       [Ljava/lang/reflect/Type;
            //    89: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //    92: iconst_0       
            //    93: aload_0        
            //    94: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //    97: arraylength    
            //    98: if_icmpge       141
            //   101: aload_0        
            //   102: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //   105: iconst_0       
            //   106: aaload         
            //   107: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
            //   110: pop            
            //   111: aload_0        
            //   112: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //   115: iconst_0       
            //   116: aaload         
            //   117: invokestatic    com/google/gson/internal/$Gson$Types.access$000:(Ljava/lang/reflect/Type;)V
            //   120: aload_0        
            //   121: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //   124: iconst_0       
            //   125: aload_0        
            //   126: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
            //   129: iconst_0       
            //   130: aaload         
            //   131: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
            //   134: aastore        
            //   135: iinc            4, 1
            //   138: goto            92
            //   141: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public Type[] getActualTypeArguments() {
            return this.typeArguments.clone();
        }
        
        public Type getRawType() {
            return this.rawType;
        }
        
        public Type getOwnerType() {
            return this.ownerType;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof ParameterizedType && $Gson$Types.equals(this, (Type)o);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.access$100(this.ownerType);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(30 * (this.typeArguments.length + 1));
            sb.append($Gson$Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return sb.toString();
            }
            sb.append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            while (1 < this.typeArguments.length) {
                sb.append(", ").append($Gson$Types.typeToString(this.typeArguments[1]));
                int n = 0;
                ++n;
            }
            return sb.append(">").toString();
        }
    }
}
