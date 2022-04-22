package com.google.common.reflect;

import com.google.common.annotations.*;
import java.util.concurrent.atomic.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;

@Beta
public final class TypeResolver
{
    private final TypeTable typeTable;
    
    public TypeResolver() {
        this.typeTable = new TypeTable();
    }
    
    private TypeResolver(final TypeTable typeTable) {
        this.typeTable = typeTable;
    }
    
    static TypeResolver accordingTo(final Type type) {
        return new TypeResolver().where(TypeMappingIntrospector.getTypeMappings(type));
    }
    
    public TypeResolver where(final Type type, final Type type2) {
        final HashMap hashMap = Maps.newHashMap();
        populateTypeMappings(hashMap, (Type)Preconditions.checkNotNull(type), (Type)Preconditions.checkNotNull(type2));
        return this.where(hashMap);
    }
    
    TypeResolver where(final Map map) {
        return new TypeResolver(this.typeTable.where(map));
    }
    
    private static void populateTypeMappings(final Map map, final Type type, final Type type2) {
        if (type.equals(type2)) {
            return;
        }
        new TypeVisitor(map, type2) {
            final Map val$mappings;
            final Type val$to;
            
            @Override
            void visitTypeVariable(final TypeVariable typeVariable) {
                this.val$mappings.put(new TypeVariableKey(typeVariable), this.val$to);
            }
            
            @Override
            void visitWildcardType(final WildcardType wildcardType) {
                final WildcardType wildcardType2 = (WildcardType)TypeResolver.access$000(WildcardType.class, this.val$to);
                final Type[] upperBounds = wildcardType.getUpperBounds();
                final Type[] upperBounds2 = wildcardType2.getUpperBounds();
                final Type[] lowerBounds = wildcardType.getLowerBounds();
                final Type[] lowerBounds2 = wildcardType2.getLowerBounds();
                Preconditions.checkArgument(upperBounds.length == upperBounds2.length && lowerBounds.length == lowerBounds2.length, "Incompatible type: %s vs. %s", wildcardType, this.val$to);
                int n = 0;
                while (0 < upperBounds.length) {
                    TypeResolver.access$100(this.val$mappings, upperBounds[0], upperBounds2[0]);
                    ++n;
                }
                while (0 < lowerBounds.length) {
                    TypeResolver.access$100(this.val$mappings, lowerBounds[0], lowerBounds2[0]);
                    ++n;
                }
            }
            
            @Override
            void visitParameterizedType(final ParameterizedType parameterizedType) {
                final ParameterizedType parameterizedType2 = (ParameterizedType)TypeResolver.access$000(ParameterizedType.class, this.val$to);
                Preconditions.checkArgument(parameterizedType.getRawType().equals(parameterizedType2.getRawType()), "Inconsistent raw type: %s vs. %s", parameterizedType, this.val$to);
                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                final Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                Preconditions.checkArgument(actualTypeArguments.length == actualTypeArguments2.length, "%s not compatible with %s", parameterizedType, parameterizedType2);
                while (0 < actualTypeArguments.length) {
                    TypeResolver.access$100(this.val$mappings, actualTypeArguments[0], actualTypeArguments2[0]);
                    int n = 0;
                    ++n;
                }
            }
            
            @Override
            void visitGenericArrayType(final GenericArrayType genericArrayType) {
                final Type componentType = Types.getComponentType(this.val$to);
                Preconditions.checkArgument(componentType != null, "%s is not an array type.", this.val$to);
                TypeResolver.access$100(this.val$mappings, genericArrayType.getGenericComponentType(), componentType);
            }
            
            @Override
            void visitClass(final Class clazz) {
                throw new IllegalArgumentException("No type mapping from " + clazz);
            }
        }.visit(type);
    }
    
    public Type resolveType(final Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof TypeVariable) {
            return this.typeTable.resolve((TypeVariable)type);
        }
        if (type instanceof ParameterizedType) {
            return this.resolveParameterizedType((ParameterizedType)type);
        }
        if (type instanceof GenericArrayType) {
            return this.resolveGenericArrayType((GenericArrayType)type);
        }
        if (type instanceof WildcardType) {
            return this.resolveWildcardType((WildcardType)type);
        }
        return type;
    }
    
    private Type[] resolveTypes(final Type[] array) {
        final Type[] array2 = new Type[array.length];
        while (0 < array.length) {
            array2[0] = this.resolveType(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    private WildcardType resolveWildcardType(final WildcardType wildcardType) {
        return new Types.WildcardTypeImpl(this.resolveTypes(wildcardType.getLowerBounds()), this.resolveTypes(wildcardType.getUpperBounds()));
    }
    
    private Type resolveGenericArrayType(final GenericArrayType genericArrayType) {
        return Types.newArrayType(this.resolveType(genericArrayType.getGenericComponentType()));
    }
    
    private ParameterizedType resolveParameterizedType(final ParameterizedType parameterizedType) {
        final Type ownerType = parameterizedType.getOwnerType();
        return Types.newParameterizedTypeWithOwner((ownerType == null) ? null : this.resolveType(ownerType), (Class)this.resolveType(parameterizedType.getRawType()), this.resolveTypes(parameterizedType.getActualTypeArguments()));
    }
    
    private static Object expectArgument(final Class clazz, final Object o) {
        return clazz.cast(o);
    }
    
    static Object access$000(final Class clazz, final Object o) {
        return expectArgument(clazz, o);
    }
    
    static void access$100(final Map map, final Type type, final Type type2) {
        populateTypeMappings(map, type, type2);
    }
    
    TypeResolver(final TypeTable typeTable, final TypeResolver$1 typeVisitor) {
        this(typeTable);
    }
    
    static Type[] access$300(final TypeResolver typeResolver, final Type[] array) {
        return typeResolver.resolveTypes(array);
    }
    
    static final class TypeVariableKey
    {
        private final TypeVariable var;
        
        TypeVariableKey(final TypeVariable typeVariable) {
            this.var = (TypeVariable)Preconditions.checkNotNull(typeVariable);
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof TypeVariableKey && this.equalsTypeVariable(((TypeVariableKey)o).var);
        }
        
        @Override
        public String toString() {
            return this.var.toString();
        }
        
        static Object forLookup(final Type type) {
            if (type instanceof TypeVariable) {
                return new TypeVariableKey((TypeVariable)type);
            }
            return null;
        }
        
        boolean equalsType(final Type type) {
            return type instanceof TypeVariable && this.equalsTypeVariable((TypeVariable)type);
        }
        
        private boolean equalsTypeVariable(final TypeVariable typeVariable) {
            return this.var.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) && this.var.getName().equals(typeVariable.getName());
        }
    }
    
    private static final class WildcardCapturer
    {
        private final AtomicInteger id;
        
        private WildcardCapturer() {
            this.id = new AtomicInteger();
        }
        
        Type capture(final Type type) {
            Preconditions.checkNotNull(type);
            if (type instanceof Class) {
                return type;
            }
            if (type instanceof TypeVariable) {
                return type;
            }
            if (type instanceof GenericArrayType) {
                return Types.newArrayType(this.capture(((GenericArrayType)type).getGenericComponentType()));
            }
            if (type instanceof ParameterizedType) {
                final ParameterizedType parameterizedType = (ParameterizedType)type;
                return Types.newParameterizedTypeWithOwner(this.captureNullable(parameterizedType.getOwnerType()), (Class)parameterizedType.getRawType(), this.capture(parameterizedType.getActualTypeArguments()));
            }
            if (!(type instanceof WildcardType)) {
                throw new AssertionError((Object)"must have been one of the known types");
            }
            final WildcardType wildcardType = (WildcardType)type;
            if (wildcardType.getLowerBounds().length == 0) {
                return Types.newArtificialTypeVariable(WildcardCapturer.class, "capture#" + this.id.incrementAndGet() + "-of ? extends " + Joiner.on('&').join(wildcardType.getUpperBounds()), wildcardType.getUpperBounds());
            }
            return type;
        }
        
        private Type captureNullable(@Nullable final Type type) {
            if (type == null) {
                return null;
            }
            return this.capture(type);
        }
        
        private Type[] capture(final Type[] array) {
            final Type[] array2 = new Type[array.length];
            while (0 < array.length) {
                array2[0] = this.capture(array[0]);
                int n = 0;
                ++n;
            }
            return array2;
        }
        
        WildcardCapturer(final TypeResolver$1 typeVisitor) {
            this();
        }
    }
    
    private static final class TypeMappingIntrospector extends TypeVisitor
    {
        private static final WildcardCapturer wildcardCapturer;
        private final Map mappings;
        
        private TypeMappingIntrospector() {
            this.mappings = Maps.newHashMap();
        }
        
        static ImmutableMap getTypeMappings(final Type type) {
            final TypeMappingIntrospector typeMappingIntrospector = new TypeMappingIntrospector();
            typeMappingIntrospector.visit(TypeMappingIntrospector.wildcardCapturer.capture(type));
            return ImmutableMap.copyOf(typeMappingIntrospector.mappings);
        }
        
        @Override
        void visitClass(final Class clazz) {
            this.visit(clazz.getGenericSuperclass());
            this.visit(clazz.getGenericInterfaces());
        }
        
        @Override
        void visitParameterizedType(final ParameterizedType parameterizedType) {
            final Class clazz = (Class)parameterizedType.getRawType();
            final TypeVariable[] typeParameters = clazz.getTypeParameters();
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Preconditions.checkState(typeParameters.length == actualTypeArguments.length);
            while (0 < typeParameters.length) {
                this.map(new TypeVariableKey(typeParameters[0]), actualTypeArguments[0]);
                int n = 0;
                ++n;
            }
            this.visit(clazz);
            this.visit(parameterizedType.getOwnerType());
        }
        
        @Override
        void visitTypeVariable(final TypeVariable typeVariable) {
            this.visit(typeVariable.getBounds());
        }
        
        @Override
        void visitWildcardType(final WildcardType wildcardType) {
            this.visit(wildcardType.getUpperBounds());
        }
        
        private void map(final TypeVariableKey typeVariableKey, final Type type) {
            if (this.mappings.containsKey(typeVariableKey)) {
                return;
            }
            for (Type type2 = type; type2 != null; type2 = (Type)this.mappings.get(TypeVariableKey.forLookup(type2))) {
                if (typeVariableKey.equalsType(type2)) {
                    for (Type type3 = type; type3 != null; type3 = (Type)this.mappings.remove(TypeVariableKey.forLookup(type3))) {}
                    return;
                }
            }
            this.mappings.put(typeVariableKey, type);
        }
        
        static {
            wildcardCapturer = new WildcardCapturer(null);
        }
    }
    
    private static class TypeTable
    {
        private final ImmutableMap map;
        
        TypeTable() {
            this.map = ImmutableMap.of();
        }
        
        private TypeTable(final ImmutableMap map) {
            this.map = map;
        }
        
        final TypeTable where(final Map map) {
            final ImmutableMap.Builder builder = ImmutableMap.builder();
            builder.putAll(this.map);
            for (final Map.Entry<TypeVariableKey, V> entry : map.entrySet()) {
                final TypeVariableKey typeVariableKey = entry.getKey();
                final Type type = (Type)entry.getValue();
                Preconditions.checkArgument(!typeVariableKey.equalsType(type), "Type variable %s bound to itself", typeVariableKey);
                builder.put(typeVariableKey, type);
            }
            return new TypeTable(builder.build());
        }
        
        final Type resolve(final TypeVariable typeVariable) {
            return this.resolveInternal(typeVariable, new TypeTable(typeVariable, this) {
                final TypeVariable val$var;
                final TypeTable val$unguarded;
                final TypeTable this$0;
                
                public Type resolveInternal(final TypeVariable typeVariable, final TypeTable typeTable) {
                    if (typeVariable.getGenericDeclaration().equals(this.val$var.getGenericDeclaration())) {
                        return typeVariable;
                    }
                    return this.val$unguarded.resolveInternal(typeVariable, typeTable);
                }
            });
        }
        
        Type resolveInternal(final TypeVariable typeVariable, final TypeTable typeTable) {
            final Type type = (Type)this.map.get(new TypeVariableKey(typeVariable));
            if (type != null) {
                return new TypeResolver(typeTable, null).resolveType(type);
            }
            final Type[] bounds = typeVariable.getBounds();
            if (bounds.length == 0) {
                return typeVariable;
            }
            final Type[] access$300 = TypeResolver.access$300(new TypeResolver(typeTable, null), bounds);
            if (Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY && Arrays.equals(bounds, access$300)) {
                return typeVariable;
            }
            return Types.newArtificialTypeVariable(typeVariable.getGenericDeclaration(), typeVariable.getName(), access$300);
        }
    }
}
