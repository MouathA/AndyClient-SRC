package com.google.common.reflect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.concurrent.atomic.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;

final class Types
{
    private static final Function TYPE_TO_STRING;
    private static final Joiner COMMA_JOINER;
    
    static Type newArrayType(final Type type) {
        if (!(type instanceof WildcardType)) {
            return JavaVersion.CURRENT.newArrayType(type);
        }
        final WildcardType wildcardType = (WildcardType)type;
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        Preconditions.checkArgument(lowerBounds.length <= 1, (Object)"Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return supertypeOf(newArrayType(lowerBounds[0]));
        }
        final Type[] upperBounds = wildcardType.getUpperBounds();
        Preconditions.checkArgument(upperBounds.length == 1, (Object)"Wildcard should have only one upper bound.");
        return subtypeOf(newArrayType(upperBounds[0]));
    }
    
    static ParameterizedType newParameterizedTypeWithOwner(@Nullable final Type type, final Class clazz, final Type... array) {
        if (type == null) {
            return newParameterizedType(clazz, array);
        }
        Preconditions.checkNotNull(array);
        Preconditions.checkArgument(clazz.getEnclosingClass() != null, "Owner type for unenclosed %s", clazz);
        return new ParameterizedTypeImpl(type, clazz, array);
    }
    
    static ParameterizedType newParameterizedType(final Class clazz, final Type... array) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(clazz), clazz, array);
    }
    
    static TypeVariable newArtificialTypeVariable(final GenericDeclaration genericDeclaration, final String s, final Type... array) {
        return new TypeVariableImpl(genericDeclaration, s, (array.length == 0) ? new Type[] { Object.class } : array);
    }
    
    @VisibleForTesting
    static WildcardType subtypeOf(final Type type) {
        return new WildcardTypeImpl(new Type[0], new Type[] { type });
    }
    
    @VisibleForTesting
    static WildcardType supertypeOf(final Type type) {
        return new WildcardTypeImpl(new Type[] { type }, new Type[] { Object.class });
    }
    
    static String toString(final Type type) {
        return (type instanceof Class) ? ((Class)type).getName() : type.toString();
    }
    
    @Nullable
    static Type getComponentType(final Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference<Type> atomicReference = new AtomicReference<Type>();
        new TypeVisitor(atomicReference) {
            final AtomicReference val$result;
            
            @Override
            void visitTypeVariable(final TypeVariable typeVariable) {
                this.val$result.set(Types.access$100(typeVariable.getBounds()));
            }
            
            @Override
            void visitWildcardType(final WildcardType wildcardType) {
                this.val$result.set(Types.access$100(wildcardType.getUpperBounds()));
            }
            
            @Override
            void visitGenericArrayType(final GenericArrayType genericArrayType) {
                this.val$result.set(genericArrayType.getGenericComponentType());
            }
            
            @Override
            void visitClass(final Class clazz) {
                this.val$result.set(clazz.getComponentType());
            }
        }.visit(type);
        return atomicReference.get();
    }
    
    @Nullable
    private static Type subtypeOfComponentType(final Type[] array) {
        while (0 < array.length) {
            final Type componentType = getComponentType(array[0]);
            if (componentType != null) {
                if (componentType instanceof Class) {
                    final Class clazz = (Class)componentType;
                    if (clazz.isPrimitive()) {
                        return clazz;
                    }
                }
                return subtypeOf(componentType);
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private static Type[] toArray(final Collection collection) {
        return collection.toArray(new Type[collection.size()]);
    }
    
    private static Iterable filterUpperBounds(final Iterable iterable) {
        return Iterables.filter(iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }
    
    private static void disallowPrimitiveType(final Type[] array, final String s) {
        while (0 < array.length) {
            final Type type = array[0];
            if (type instanceof Class) {
                final Class clazz = (Class)type;
                Preconditions.checkArgument(!clazz.isPrimitive(), "Primitive type '%s' used as %s", clazz, s);
            }
            int n = 0;
            ++n;
        }
    }
    
    static Class getArrayClass(final Class clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }
    
    private Types() {
    }
    
    static Type access$100(final Type[] array) {
        return subtypeOfComponentType(array);
    }
    
    static void access$200(final Type[] array, final String s) {
        disallowPrimitiveType(array, s);
    }
    
    static Type[] access$300(final Collection collection) {
        return toArray(collection);
    }
    
    static Function access$400() {
        return Types.TYPE_TO_STRING;
    }
    
    static Joiner access$500() {
        return Types.COMMA_JOINER;
    }
    
    static Iterable access$600(final Iterable iterable) {
        return filterUpperBounds(iterable);
    }
    
    static {
        TYPE_TO_STRING = new Function() {
            public String apply(final Type type) {
                return Types.toString(type);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Type)o);
            }
        };
        COMMA_JOINER = Joiner.on(", ").useForNull("null");
    }
    
    static final class NativeTypeVariableEquals
    {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY;
        
        static {
            NATIVE_TYPE_VARIABLE_ONLY = !NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0]));
        }
    }
    
    enum JavaVersion
    {
        JAVA6 {
            @Override
            GenericArrayType newArrayType(final Type type) {
                return new GenericArrayTypeImpl(type);
            }
            
            @Override
            Type usedInGenericType(final Type type) {
                Preconditions.checkNotNull(type);
                if (type instanceof Class) {
                    final Class clazz = (Class)type;
                    if (clazz.isArray()) {
                        return new GenericArrayTypeImpl(clazz.getComponentType());
                    }
                }
                return type;
            }
            
            @Override
            Type newArrayType(final Type type) {
                return this.newArrayType(type);
            }
        }, 
        JAVA7 {
            @Override
            Type newArrayType(final Type type) {
                if (type instanceof Class) {
                    return Types.getArrayClass((Class)type);
                }
                return new GenericArrayTypeImpl(type);
            }
            
            @Override
            Type usedInGenericType(final Type type) {
                return (Type)Preconditions.checkNotNull(type);
            }
        };
        
        static final JavaVersion CURRENT;
        private static final JavaVersion[] $VALUES;
        
        private JavaVersion(final String s, final int n) {
        }
        
        abstract Type newArrayType(final Type p0);
        
        abstract Type usedInGenericType(final Type p0);
        
        final ImmutableList usedInGenericType(final Type[] array) {
            final ImmutableList.Builder builder = ImmutableList.builder();
            while (0 < array.length) {
                builder.add((Object)this.usedInGenericType(array[0]));
                int n = 0;
                ++n;
            }
            return builder.build();
        }
        
        JavaVersion(final String s, final int n, final Types$1 function) {
            this(s, n);
        }
        
        static {
            $VALUES = new JavaVersion[] { JavaVersion.JAVA6, JavaVersion.JAVA7 };
            CURRENT = ((new TypeCapture() {}.capture() instanceof Class) ? JavaVersion.JAVA7 : JavaVersion.JAVA6);
        }
    }
    
    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable
    {
        private final Type componentType;
        private static final long serialVersionUID = 0L;
        
        GenericArrayTypeImpl(final Type type) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(type);
        }
        
        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }
        
        @Override
        public String toString() {
            return Types.toString(this.componentType) + "[]";
        }
        
        @Override
        public int hashCode() {
            return this.componentType.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof GenericArrayType && Objects.equal(this.getGenericComponentType(), ((GenericArrayType)o).getGenericComponentType());
        }
    }
    
    static final class WildcardTypeImpl implements WildcardType, Serializable
    {
        private final ImmutableList lowerBounds;
        private final ImmutableList upperBounds;
        private static final long serialVersionUID = 0L;
        
        WildcardTypeImpl(final Type[] array, final Type[] array2) {
            Types.access$200(array, "lower bound for wildcard");
            Types.access$200(array2, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(array);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(array2);
        }
        
        @Override
        public Type[] getLowerBounds() {
            return Types.access$300(this.lowerBounds);
        }
        
        @Override
        public Type[] getUpperBounds() {
            return Types.access$300(this.upperBounds);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof WildcardType) {
                final WildcardType wildcardType = (WildcardType)o;
                return this.lowerBounds.equals(Arrays.asList(wildcardType.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(wildcardType.getUpperBounds()));
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("?");
            final Iterator iterator = this.lowerBounds.iterator();
            while (iterator.hasNext()) {
                sb.append(" super ").append(Types.toString(iterator.next()));
            }
            final Iterator<Type> iterator2 = Types.access$600(this.upperBounds).iterator();
            while (iterator2.hasNext()) {
                sb.append(" extends ").append(Types.toString(iterator2.next()));
            }
            return sb.toString();
        }
    }
    
    private static final class TypeVariableImpl implements TypeVariable
    {
        private final GenericDeclaration genericDeclaration;
        private final String name;
        private final ImmutableList bounds;
        
        TypeVariableImpl(final GenericDeclaration genericDeclaration, final String s, final Type[] array) {
            Types.access$200(array, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration)Preconditions.checkNotNull(genericDeclaration);
            this.name = (String)Preconditions.checkNotNull(s);
            this.bounds = ImmutableList.copyOf(array);
        }
        
        @Override
        public Type[] getBounds() {
            return Types.access$300(this.bounds);
        }
        
        @Override
        public GenericDeclaration getGenericDeclaration() {
            return this.genericDeclaration;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public int hashCode() {
            return this.genericDeclaration.hashCode() ^ this.name.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
                if (o instanceof TypeVariableImpl) {
                    final TypeVariableImpl typeVariableImpl = (TypeVariableImpl)o;
                    return this.name.equals(typeVariableImpl.getName()) && this.genericDeclaration.equals(typeVariableImpl.getGenericDeclaration()) && this.bounds.equals(typeVariableImpl.bounds);
                }
                return false;
            }
            else {
                if (o instanceof TypeVariable) {
                    final TypeVariable typeVariable = (TypeVariable)o;
                    return this.name.equals(typeVariable.getName()) && this.genericDeclaration.equals(typeVariable.getGenericDeclaration());
                }
                return false;
            }
        }
    }
    
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable
    {
        private final Type ownerType;
        private final ImmutableList argumentsList;
        private final Class rawType;
        private static final long serialVersionUID = 0L;
        
        ParameterizedTypeImpl(@Nullable final Type ownerType, final Class rawType, final Type[] array) {
            Preconditions.checkNotNull(rawType);
            Preconditions.checkArgument(array.length == rawType.getTypeParameters().length);
            Types.access$200(array, "type parameter");
            this.ownerType = ownerType;
            this.rawType = rawType;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(array);
        }
        
        @Override
        public Type[] getActualTypeArguments() {
            return Types.access$300(this.argumentsList);
        }
        
        @Override
        public Type getRawType() {
            return this.rawType;
        }
        
        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (this.ownerType != null) {
                sb.append(Types.toString(this.ownerType)).append('.');
            }
            sb.append(this.rawType.getName()).append('<').append(Types.access$500().join(Iterables.transform(this.argumentsList, Types.access$400()))).append('>');
            return sb.toString();
        }
        
        @Override
        public int hashCode() {
            return ((this.ownerType == null) ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof ParameterizedType)) {
                return false;
            }
            final ParameterizedType parameterizedType = (ParameterizedType)o;
            return this.getRawType().equals(parameterizedType.getRawType()) && Objects.equal(this.getOwnerType(), parameterizedType.getOwnerType()) && Arrays.equals(this.getActualTypeArguments(), parameterizedType.getActualTypeArguments());
        }
    }
    
    private enum ClassOwnership
    {
        OWNED_BY_ENCLOSING_CLASS {
            @Nullable
            @Override
            Class getOwnerType(final Class clazz) {
                return clazz.getEnclosingClass();
            }
        }, 
        LOCAL_CLASS_HAS_NO_OWNER {
            @Nullable
            @Override
            Class getOwnerType(final Class clazz) {
                if (clazz.isLocalClass()) {
                    return null;
                }
                return clazz.getEnclosingClass();
            }
        };
        
        static final ClassOwnership JVM_BEHAVIOR;
        private static final ClassOwnership[] $VALUES;
        
        private ClassOwnership(final String s, final int n) {
        }
        
        @Nullable
        abstract Class getOwnerType(final Class p0);
        
        private static ClassOwnership detectJvmBehavior() {
            final ParameterizedType parameterizedType = (ParameterizedType)new LocalClass() {}.getClass().getGenericSuperclass();
            final ClassOwnership[] values = values();
            while (0 < values.length) {
                final ClassOwnership classOwnership = values[0];
                if (classOwnership.getOwnerType(LocalClass.class) == parameterizedType.getOwnerType()) {
                    return classOwnership;
                }
                int n = 0;
                ++n;
            }
            throw new AssertionError();
        }
        
        ClassOwnership(final String s, final int n, final Types$1 function) {
            this(s, n);
        }
        
        static {
            $VALUES = new ClassOwnership[] { ClassOwnership.OWNED_BY_ENCLOSING_CLASS, ClassOwnership.LOCAL_CLASS_HAS_NO_OWNER };
            JVM_BEHAVIOR = detectJvmBehavior();
        }
        
        class LocalClass
        {
        }
    }
}
