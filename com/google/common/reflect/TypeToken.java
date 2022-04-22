package com.google.common.reflect;

import java.io.*;
import javax.annotation.*;
import com.google.common.primitives.*;
import java.lang.reflect.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

@Beta
public abstract class TypeToken extends TypeCapture implements Serializable
{
    private final Type runtimeType;
    private transient TypeResolver typeResolver;
    
    protected TypeToken() {
        this.runtimeType = this.capture();
        Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", this.runtimeType);
    }
    
    protected TypeToken(final Class clazz) {
        final Type capture = super.capture();
        if (capture instanceof Class) {
            this.runtimeType = capture;
        }
        else {
            this.runtimeType = of(clazz).resolveType(capture).runtimeType;
        }
    }
    
    private TypeToken(final Type type) {
        this.runtimeType = (Type)Preconditions.checkNotNull(type);
    }
    
    public static TypeToken of(final Class clazz) {
        return new SimpleTypeToken((Type)clazz);
    }
    
    public static TypeToken of(final Type type) {
        return new SimpleTypeToken(type);
    }
    
    public final Class getRawType() {
        return getRawType(this.runtimeType);
    }
    
    private ImmutableSet getImmediateRawTypes() {
        return getRawTypes(this.runtimeType);
    }
    
    public final Type getType() {
        return this.runtimeType;
    }
    
    public final TypeToken where(final TypeParameter typeParameter, final TypeToken typeToken) {
        return new SimpleTypeToken(new TypeResolver().where(ImmutableMap.of(new TypeResolver.TypeVariableKey(typeParameter.typeVariable), typeToken.runtimeType)).resolveType(this.runtimeType));
    }
    
    public final TypeToken where(final TypeParameter typeParameter, final Class clazz) {
        return this.where(typeParameter, of(clazz));
    }
    
    public final TypeToken resolveType(final Type type) {
        Preconditions.checkNotNull(type);
        TypeResolver typeResolver = this.typeResolver;
        if (typeResolver == null) {
            final TypeResolver accordingTo = TypeResolver.accordingTo(this.runtimeType);
            this.typeResolver = accordingTo;
            typeResolver = accordingTo;
        }
        return of(typeResolver.resolveType(type));
    }
    
    private Type[] resolveInPlace(final Type[] array) {
        while (0 < array.length) {
            array[0] = this.resolveType(array[0]).getType();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private TypeToken resolveSupertype(final Type type) {
        final TypeToken resolveType = this.resolveType(type);
        resolveType.typeResolver = this.typeResolver;
        return resolveType;
    }
    
    @Nullable
    final TypeToken getGenericSuperclass() {
        if (this.runtimeType instanceof TypeVariable) {
            return this.boundAsSuperclass(((TypeVariable)this.runtimeType).getBounds()[0]);
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.boundAsSuperclass(((WildcardType)this.runtimeType).getUpperBounds()[0]);
        }
        final Type genericSuperclass = this.getRawType().getGenericSuperclass();
        if (genericSuperclass == null) {
            return null;
        }
        return this.resolveSupertype(genericSuperclass);
    }
    
    @Nullable
    private TypeToken boundAsSuperclass(final Type type) {
        final TypeToken of = of(type);
        if (of.getRawType().isInterface()) {
            return null;
        }
        return of;
    }
    
    final ImmutableList getGenericInterfaces() {
        if (this.runtimeType instanceof TypeVariable) {
            return this.boundsAsInterfaces(((TypeVariable)this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.boundsAsInterfaces(((WildcardType)this.runtimeType).getUpperBounds());
        }
        final ImmutableList.Builder builder = ImmutableList.builder();
        final Type[] genericInterfaces = this.getRawType().getGenericInterfaces();
        while (0 < genericInterfaces.length) {
            builder.add((Object)this.resolveSupertype(genericInterfaces[0]));
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    private ImmutableList boundsAsInterfaces(final Type[] array) {
        final ImmutableList.Builder builder = ImmutableList.builder();
        while (0 < array.length) {
            final TypeToken of = of(array[0]);
            if (of.getRawType().isInterface()) {
                builder.add((Object)of);
            }
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    public final TypeSet getTypes() {
        return new TypeSet();
    }
    
    public final TypeToken getSupertype(final Class clazz) {
        Preconditions.checkArgument(clazz.isAssignableFrom(this.getRawType()), "%s is not a super class of %s", clazz, this);
        if (this.runtimeType instanceof TypeVariable) {
            return this.getSupertypeFromUpperBounds(clazz, ((TypeVariable)this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.getSupertypeFromUpperBounds(clazz, ((WildcardType)this.runtimeType).getUpperBounds());
        }
        if (clazz.isArray()) {
            return this.getArraySupertype(clazz);
        }
        return this.resolveSupertype(toGenericType(clazz).runtimeType);
    }
    
    public final TypeToken getSubtype(final Class clazz) {
        Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", this);
        if (this.runtimeType instanceof WildcardType) {
            return this.getSubtypeFromLowerBounds(clazz, ((WildcardType)this.runtimeType).getLowerBounds());
        }
        Preconditions.checkArgument(this.getRawType().isAssignableFrom(clazz), "%s isn't a subclass of %s", clazz, this);
        if (this.isArray()) {
            return this.getArraySubtype(clazz);
        }
        return of(this.resolveTypeArgsForSubclass(clazz));
    }
    
    public final boolean isAssignableFrom(final TypeToken typeToken) {
        return this.isAssignableFrom(typeToken.runtimeType);
    }
    
    public final boolean isAssignableFrom(final Type type) {
        return isAssignable((Type)Preconditions.checkNotNull(type), this.runtimeType);
    }
    
    public final boolean isArray() {
        return this.getComponentType() != null;
    }
    
    public final boolean isPrimitive() {
        return this.runtimeType instanceof Class && ((Class)this.runtimeType).isPrimitive();
    }
    
    public final TypeToken wrap() {
        if (this.isPrimitive()) {
            return of(Primitives.wrap((Class)this.runtimeType));
        }
        return this;
    }
    
    private boolean isWrapper() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }
    
    public final TypeToken unwrap() {
        if (this.isWrapper()) {
            return of(Primitives.unwrap((Class)this.runtimeType));
        }
        return this;
    }
    
    @Nullable
    public final TypeToken getComponentType() {
        final Type componentType = Types.getComponentType(this.runtimeType);
        if (componentType == null) {
            return null;
        }
        return of(componentType);
    }
    
    public final Invokable method(final Method method) {
        Preconditions.checkArgument(of(method.getDeclaringClass()).isAssignableFrom(this), "%s not declared by %s", method, this);
        return new Invokable.MethodInvokable(method) {
            final TypeToken this$0;
            
            @Override
            Type getGenericReturnType() {
                return this.this$0.resolveType(super.getGenericReturnType()).getType();
            }
            
            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericParameterTypes());
            }
            
            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericExceptionTypes());
            }
            
            @Override
            public TypeToken getOwnerType() {
                return this.this$0;
            }
            
            @Override
            public String toString() {
                return this.getOwnerType() + "." + super.toString();
            }
        };
    }
    
    public final Invokable constructor(final Constructor constructor) {
        Preconditions.checkArgument(constructor.getDeclaringClass() == this.getRawType(), "%s not declared by %s", constructor, this.getRawType());
        return new Invokable.ConstructorInvokable(constructor) {
            final TypeToken this$0;
            
            @Override
            Type getGenericReturnType() {
                return this.this$0.resolveType(super.getGenericReturnType()).getType();
            }
            
            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericParameterTypes());
            }
            
            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericExceptionTypes());
            }
            
            @Override
            public TypeToken getOwnerType() {
                return this.this$0;
            }
            
            @Override
            public String toString() {
                return this.getOwnerType() + "(" + Joiner.on(", ").join(this.getGenericParameterTypes()) + ")";
            }
        };
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof TypeToken && this.runtimeType.equals(((TypeToken)o).runtimeType);
    }
    
    @Override
    public int hashCode() {
        return this.runtimeType.hashCode();
    }
    
    @Override
    public String toString() {
        return Types.toString(this.runtimeType);
    }
    
    protected Object writeReplace() {
        return of(new TypeResolver().resolveType(this.runtimeType));
    }
    
    final TypeToken rejectTypeVariables() {
        new TypeVisitor() {
            final TypeToken this$0;
            
            @Override
            void visitTypeVariable(final TypeVariable typeVariable) {
                throw new IllegalArgumentException(TypeToken.access$400(this.this$0) + "contains a type variable and is not safe for the operation");
            }
            
            @Override
            void visitWildcardType(final WildcardType wildcardType) {
                this.visit(wildcardType.getLowerBounds());
                this.visit(wildcardType.getUpperBounds());
            }
            
            @Override
            void visitParameterizedType(final ParameterizedType parameterizedType) {
                this.visit(parameterizedType.getActualTypeArguments());
                this.visit(parameterizedType.getOwnerType());
            }
            
            @Override
            void visitGenericArrayType(final GenericArrayType genericArrayType) {
                this.visit(genericArrayType.getGenericComponentType());
            }
        }.visit(this.runtimeType);
        return this;
    }
    
    private static boolean isAssignable(final Type type, final Type type2) {
        if (type2.equals(type)) {
            return true;
        }
        if (type2 instanceof WildcardType) {
            return isAssignableToWildcardType(type, (WildcardType)type2);
        }
        if (type instanceof TypeVariable) {
            return isAssignableFromAny(((TypeVariable)type).getBounds(), type2);
        }
        if (type instanceof WildcardType) {
            return isAssignableFromAny(((WildcardType)type).getUpperBounds(), type2);
        }
        if (type instanceof GenericArrayType) {
            return isAssignableFromGenericArrayType((GenericArrayType)type, type2);
        }
        if (type2 instanceof Class) {
            return isAssignableToClass(type, (Class)type2);
        }
        if (type2 instanceof ParameterizedType) {
            return isAssignableToParameterizedType(type, (ParameterizedType)type2);
        }
        return type2 instanceof GenericArrayType && isAssignableToGenericArrayType(type, (GenericArrayType)type2);
    }
    
    private static boolean isAssignableFromAny(final Type[] array, final Type type) {
        while (0 < array.length) {
            if (isAssignable(array[0], type)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private static boolean isAssignableToClass(final Type type, final Class clazz) {
        return clazz.isAssignableFrom(getRawType(type));
    }
    
    private static boolean isAssignableToWildcardType(final Type type, final WildcardType wildcardType) {
        return isAssignable(type, supertypeBound(wildcardType)) && isAssignableBySubtypeBound(type, wildcardType);
    }
    
    private static boolean isAssignableBySubtypeBound(final Type type, final WildcardType wildcardType) {
        final Type subtypeBound = subtypeBound(wildcardType);
        if (subtypeBound == null) {
            return true;
        }
        final Type subtypeBound2 = subtypeBound(type);
        return subtypeBound2 != null && isAssignable(subtypeBound, subtypeBound2);
    }
    
    private static boolean isAssignableToParameterizedType(final Type type, final ParameterizedType parameterizedType) {
        final Class rawType = getRawType(parameterizedType);
        if (!rawType.isAssignableFrom(getRawType(type))) {
            return false;
        }
        final TypeVariable[] typeParameters = rawType.getTypeParameters();
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        final TypeToken of = of(type);
        while (0 < typeParameters.length) {
            if (!matchTypeArgument(of.resolveType(typeParameters[0]).runtimeType, actualTypeArguments[0])) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private static boolean isAssignableToGenericArrayType(final Type type, final GenericArrayType genericArrayType) {
        if (type instanceof Class) {
            final Class clazz = (Class)type;
            return clazz.isArray() && isAssignable(clazz.getComponentType(), genericArrayType.getGenericComponentType());
        }
        return type instanceof GenericArrayType && isAssignable(((GenericArrayType)type).getGenericComponentType(), genericArrayType.getGenericComponentType());
    }
    
    private static boolean isAssignableFromGenericArrayType(final GenericArrayType genericArrayType, final Type type) {
        if (!(type instanceof Class)) {
            return type instanceof GenericArrayType && isAssignable(genericArrayType.getGenericComponentType(), ((GenericArrayType)type).getGenericComponentType());
        }
        final Class clazz = (Class)type;
        if (!clazz.isArray()) {
            return clazz == Object.class;
        }
        return isAssignable(genericArrayType.getGenericComponentType(), clazz.getComponentType());
    }
    
    private static boolean matchTypeArgument(final Type type, final Type type2) {
        return type.equals(type2) || (type2 instanceof WildcardType && isAssignableToWildcardType(type, (WildcardType)type2));
    }
    
    private static Type supertypeBound(final Type type) {
        if (type instanceof WildcardType) {
            return supertypeBound((WildcardType)type);
        }
        return type;
    }
    
    private static Type supertypeBound(final WildcardType wildcardType) {
        final Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length == 1) {
            return supertypeBound(upperBounds[0]);
        }
        if (upperBounds.length == 0) {
            return Object.class;
        }
        throw new AssertionError((Object)("There should be at most one upper bound for wildcard type: " + wildcardType));
    }
    
    @Nullable
    private static Type subtypeBound(final Type type) {
        if (type instanceof WildcardType) {
            return subtypeBound((WildcardType)type);
        }
        return type;
    }
    
    @Nullable
    private static Type subtypeBound(final WildcardType wildcardType) {
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length == 1) {
            return subtypeBound(lowerBounds[0]);
        }
        if (lowerBounds.length == 0) {
            return null;
        }
        throw new AssertionError((Object)("Wildcard should have at most one lower bound: " + wildcardType));
    }
    
    @VisibleForTesting
    static Class getRawType(final Type type) {
        return getRawTypes(type).iterator().next();
    }
    
    @VisibleForTesting
    static ImmutableSet getRawTypes(final Type type) {
        Preconditions.checkNotNull(type);
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        new TypeVisitor(builder) {
            final ImmutableSet.Builder val$builder;
            
            @Override
            void visitTypeVariable(final TypeVariable typeVariable) {
                this.visit(typeVariable.getBounds());
            }
            
            @Override
            void visitWildcardType(final WildcardType wildcardType) {
                this.visit(wildcardType.getUpperBounds());
            }
            
            @Override
            void visitParameterizedType(final ParameterizedType parameterizedType) {
                this.val$builder.add((Object)parameterizedType.getRawType());
            }
            
            @Override
            void visitClass(final Class clazz) {
                this.val$builder.add((Object)clazz);
            }
            
            @Override
            void visitGenericArrayType(final GenericArrayType genericArrayType) {
                this.val$builder.add((Object)Types.getArrayClass(TypeToken.getRawType(genericArrayType.getGenericComponentType())));
            }
        }.visit(type);
        return builder.build();
    }
    
    @VisibleForTesting
    static TypeToken toGenericType(final Class clazz) {
        if (clazz.isArray()) {
            return of(Types.newArrayType(toGenericType(clazz.getComponentType()).runtimeType));
        }
        final TypeVariable[] typeParameters = clazz.getTypeParameters();
        if (typeParameters.length > 0) {
            return of(Types.newParameterizedType(clazz, (Type[])typeParameters));
        }
        return of(clazz);
    }
    
    private TypeToken getSupertypeFromUpperBounds(final Class clazz, final Type[] array) {
        while (0 < array.length) {
            final TypeToken of = of(array[0]);
            if (of(clazz).isAssignableFrom(of)) {
                return of.getSupertype(clazz);
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException(clazz + " isn't a super type of " + this);
    }
    
    private TypeToken getSubtypeFromLowerBounds(final Class clazz, final Type[] array) {
        if (0 < array.length) {
            return of(array[0]).getSubtype(clazz);
        }
        throw new IllegalArgumentException(clazz + " isn't a subclass of " + this);
    }
    
    private TypeToken getArraySupertype(final Class clazz) {
        return of(newArrayClassOrGenericArrayType(((TypeToken)Preconditions.checkNotNull(this.getComponentType(), "%s isn't a super type of %s", clazz, this)).getSupertype(clazz.getComponentType()).runtimeType));
    }
    
    private TypeToken getArraySubtype(final Class clazz) {
        return of(newArrayClassOrGenericArrayType(this.getComponentType().getSubtype(clazz.getComponentType()).runtimeType));
    }
    
    private Type resolveTypeArgsForSubclass(final Class clazz) {
        if (this.runtimeType instanceof Class) {
            return clazz;
        }
        final TypeToken genericType = toGenericType(clazz);
        return new TypeResolver().where(genericType.getSupertype(this.getRawType()).runtimeType, this.runtimeType).resolveType(genericType.runtimeType);
    }
    
    private static Type newArrayClassOrGenericArrayType(final Type type) {
        return Types.JavaVersion.JAVA7.newArrayType(type);
    }
    
    static Type[] access$000(final TypeToken typeToken, final Type[] array) {
        return typeToken.resolveInPlace(array);
    }
    
    static ImmutableSet access$200(final TypeToken typeToken) {
        return typeToken.getImmediateRawTypes();
    }
    
    static Type access$400(final TypeToken typeToken) {
        return typeToken.runtimeType;
    }
    
    TypeToken(final Type type, final TypeToken$1 methodInvokable) {
        this(type);
    }
    
    private abstract static class TypeCollector
    {
        static final TypeCollector FOR_GENERIC_TYPE;
        static final TypeCollector FOR_RAW_TYPE;
        
        private TypeCollector() {
        }
        
        final TypeCollector classesOnly() {
            return new ForwardingTypeCollector(this) {
                final TypeCollector this$0;
                
                @Override
                Iterable getInterfaces(final Object o) {
                    return ImmutableSet.of();
                }
                
                @Override
                ImmutableList collectTypes(final Iterable iterable) {
                    final ImmutableList.Builder builder = ImmutableList.builder();
                    for (final Object next : iterable) {
                        if (!this.getRawType(next).isInterface()) {
                            builder.add(next);
                        }
                    }
                    return super.collectTypes(builder.build());
                }
            };
        }
        
        final ImmutableList collectTypes(final Object o) {
            return this.collectTypes(ImmutableList.of(o));
        }
        
        ImmutableList collectTypes(final Iterable iterable) {
            final HashMap hashMap = Maps.newHashMap();
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.collectTypes(iterator.next(), hashMap);
            }
            return sortKeysByValue(hashMap, Ordering.natural().reverse());
        }
        
        private int collectTypes(final Object o, final Map map) {
            final Integer n = map.get(this);
            if (n != null) {
                return n;
            }
            int n2 = this.getRawType(o).isInterface() ? 1 : 0;
            final Iterator<Object> iterator = (Iterator<Object>)this.getInterfaces(o).iterator();
            while (iterator.hasNext()) {
                n2 = Math.max(n2, this.collectTypes(iterator.next(), map));
            }
            final Object superclass = this.getSuperclass(o);
            if (superclass != null) {
                n2 = Math.max(n2, this.collectTypes(superclass, map));
            }
            map.put(o, n2 + 1);
            return n2 + 1;
        }
        
        private static ImmutableList sortKeysByValue(final Map map, final Comparator comparator) {
            return new Ordering(comparator, map) {
                final Comparator val$valueComparator;
                final Map val$map;
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.val$valueComparator.compare(this.val$map.get(o), this.val$map.get(o2));
                }
            }.immutableSortedCopy(map.keySet());
        }
        
        abstract Class getRawType(final Object p0);
        
        abstract Iterable getInterfaces(final Object p0);
        
        @Nullable
        abstract Object getSuperclass(final Object p0);
        
        TypeCollector(final TypeToken$1 methodInvokable) {
            this();
        }
        
        static {
            FOR_GENERIC_TYPE = new TypeCollector() {
                Class getRawType(final TypeToken typeToken) {
                    return typeToken.getRawType();
                }
                
                Iterable getInterfaces(final TypeToken typeToken) {
                    return typeToken.getGenericInterfaces();
                }
                
                @Nullable
                TypeToken getSuperclass(final TypeToken typeToken) {
                    return typeToken.getGenericSuperclass();
                }
                
                @Override
                Object getSuperclass(final Object o) {
                    return this.getSuperclass((TypeToken)o);
                }
                
                @Override
                Iterable getInterfaces(final Object o) {
                    return this.getInterfaces((TypeToken)o);
                }
                
                @Override
                Class getRawType(final Object o) {
                    return this.getRawType((TypeToken)o);
                }
            };
            FOR_RAW_TYPE = new TypeCollector() {
                Class getRawType(final Class clazz) {
                    return clazz;
                }
                
                Iterable getInterfaces(final Class clazz) {
                    return Arrays.asList(clazz.getInterfaces());
                }
                
                @Nullable
                Class getSuperclass(final Class clazz) {
                    return clazz.getSuperclass();
                }
                
                @Override
                Object getSuperclass(final Object o) {
                    return this.getSuperclass((Class)o);
                }
                
                @Override
                Iterable getInterfaces(final Object o) {
                    return this.getInterfaces((Class)o);
                }
                
                @Override
                Class getRawType(final Object o) {
                    return this.getRawType((Class)o);
                }
            };
        }
        
        private static class ForwardingTypeCollector extends TypeCollector
        {
            private final TypeCollector delegate;
            
            ForwardingTypeCollector(final TypeCollector delegate) {
                super(null);
                this.delegate = delegate;
            }
            
            @Override
            Class getRawType(final Object o) {
                return this.delegate.getRawType(o);
            }
            
            @Override
            Iterable getInterfaces(final Object o) {
                return this.delegate.getInterfaces(o);
            }
            
            @Override
            Object getSuperclass(final Object o) {
                return this.delegate.getSuperclass(o);
            }
        }
    }
    
    private static final class SimpleTypeToken extends TypeToken
    {
        private static final long serialVersionUID = 0L;
        
        SimpleTypeToken(final Type type) {
            super(type, null);
        }
    }
    
    private enum TypeFilter implements Predicate
    {
        IGNORE_TYPE_VARIABLE_OR_WILDCARD {
            public boolean apply(final TypeToken typeToken) {
                return !(TypeToken.access$400(typeToken) instanceof TypeVariable) && !(TypeToken.access$400(typeToken) instanceof WildcardType);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((TypeToken)o);
            }
        }, 
        INTERFACE_ONLY {
            public boolean apply(final TypeToken typeToken) {
                return typeToken.getRawType().isInterface();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((TypeToken)o);
            }
        };
        
        private static final TypeFilter[] $VALUES;
        
        private TypeFilter(final String s, final int n) {
        }
        
        TypeFilter(final String s, final int n, final TypeToken$1 methodInvokable) {
            this(s, n);
        }
        
        static {
            $VALUES = new TypeFilter[] { TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD, TypeFilter.INTERFACE_ONLY };
        }
    }
    
    private final class ClassSet extends TypeSet
    {
        private transient ImmutableSet classes;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;
        
        private ClassSet(final TypeToken this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        protected Set delegate() {
            final ImmutableSet classes = this.classes;
            if (classes == null) {
                return this.classes = FluentIterable.from(TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes(this.this$0)).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            }
            return classes;
        }
        
        @Override
        public TypeSet classes() {
            return this;
        }
        
        @Override
        public Set rawTypes() {
            return ImmutableSet.copyOf(TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes(TypeToken.access$200(this.this$0)));
        }
        
        @Override
        public TypeSet interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }
        
        private Object readResolve() {
            return this.this$0.getTypes().classes();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        ClassSet(final TypeToken typeToken, final TypeToken$1 methodInvokable) {
            this(typeToken);
        }
    }
    
    public class TypeSet extends ForwardingSet implements Serializable
    {
        private transient ImmutableSet types;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;
        
        TypeSet(final TypeToken this$0) {
            this.this$0 = this$0;
        }
        
        public TypeSet interfaces() {
            return this.this$0.new InterfaceSet(this);
        }
        
        public TypeSet classes() {
            return this.this$0.new ClassSet(null);
        }
        
        @Override
        protected Set delegate() {
            final ImmutableSet types = this.types;
            if (types == null) {
                return this.types = FluentIterable.from(TypeCollector.FOR_GENERIC_TYPE.collectTypes(this.this$0)).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            }
            return types;
        }
        
        public Set rawTypes() {
            return ImmutableSet.copyOf(TypeCollector.FOR_RAW_TYPE.collectTypes(TypeToken.access$200(this.this$0)));
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private final class InterfaceSet extends TypeSet
    {
        private final transient TypeSet allTypes;
        private transient ImmutableSet interfaces;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;
        
        InterfaceSet(final TypeToken this$0, final TypeSet allTypes) {
            this.this$0 = this$0.super();
            this.allTypes = allTypes;
        }
        
        @Override
        protected Set delegate() {
            final ImmutableSet interfaces = this.interfaces;
            if (interfaces == null) {
                return this.interfaces = FluentIterable.from(this.allTypes).filter(TypeFilter.INTERFACE_ONLY).toSet();
            }
            return interfaces;
        }
        
        @Override
        public TypeSet interfaces() {
            return this;
        }
        
        @Override
        public Set rawTypes() {
            return FluentIterable.from(TypeCollector.FOR_RAW_TYPE.collectTypes(TypeToken.access$200(this.this$0))).filter(new Predicate() {
                final InterfaceSet this$1;
                
                public boolean apply(final Class clazz) {
                    return clazz.isInterface();
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.apply((Class)o);
                }
            }).toSet();
        }
        
        @Override
        public TypeSet classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }
        
        private Object readResolve() {
            return this.this$0.getTypes().interfaces();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
