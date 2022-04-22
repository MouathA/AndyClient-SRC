package io.netty.util.internal;

import java.util.*;
import java.lang.reflect.*;

public abstract class TypeParameterMatcher
{
    private static final TypeParameterMatcher NOOP;
    private static final Object TEST_OBJECT;
    
    public static TypeParameterMatcher get(final Class clazz) {
        final Map typeParameterMatcherGetCache = InternalThreadLocalMap.get().typeParameterMatcherGetCache();
        TypeParameterMatcher typeParameterMatcher = typeParameterMatcherGetCache.get(clazz);
        if (typeParameterMatcher == null) {
            if (clazz == Object.class) {
                typeParameterMatcher = TypeParameterMatcher.NOOP;
            }
            else if (PlatformDependent.hasJavassist()) {
                typeParameterMatcher = JavassistTypeParameterMatcherGenerator.generate(clazz);
                typeParameterMatcher.match(TypeParameterMatcher.TEST_OBJECT);
            }
            if (typeParameterMatcher == null) {
                typeParameterMatcher = new ReflectiveMatcher(clazz);
            }
            typeParameterMatcherGetCache.put(clazz, typeParameterMatcher);
        }
        return typeParameterMatcher;
    }
    
    public static TypeParameterMatcher find(final Object o, final Class clazz, final String s) {
        final Map typeParameterMatcherFindCache = InternalThreadLocalMap.get().typeParameterMatcherFindCache();
        final Class<?> class1 = o.getClass();
        Object o2 = typeParameterMatcherFindCache.get(class1);
        if (o2 == null) {
            o2 = new HashMap<Object, TypeParameterMatcher>();
            typeParameterMatcherFindCache.put(class1, o2);
        }
        TypeParameterMatcher value = ((Map<K, TypeParameterMatcher>)o2).get(s);
        if (value == null) {
            value = get(find0(o, clazz, s));
            ((Map<K, TypeParameterMatcher>)o2).put((K)s, value);
        }
        return value;
    }
    
    private static Class find0(final Object o, final Class clazz, final String s) {
        Class<?> clazz3;
        final Class<?> clazz2 = clazz3 = o.getClass();
        while (clazz3.getSuperclass() != clazz) {
            clazz3 = clazz3.getSuperclass();
            if (clazz3 == null) {
                return fail(clazz2, s);
            }
        }
        final TypeVariable<Class<?>>[] typeParameters = clazz3.getSuperclass().getTypeParameters();
        while (0 < typeParameters.length && !s.equals(typeParameters[0].getName())) {
            int n = 0;
            ++n;
        }
        throw new IllegalStateException("unknown type parameter '" + s + "': " + clazz);
    }
    
    private static Class fail(final Class clazz, final String s) {
        throw new IllegalStateException("cannot determine the type of the type parameter '" + s + "': " + clazz);
    }
    
    public abstract boolean match(final Object p0);
    
    protected TypeParameterMatcher() {
    }
    
    static {
        NOOP = new NoOpTypeParameterMatcher();
        TEST_OBJECT = new Object();
    }
    
    private static final class ReflectiveMatcher extends TypeParameterMatcher
    {
        private final Class type;
        
        ReflectiveMatcher(final Class type) {
            this.type = type;
        }
        
        @Override
        public boolean match(final Object o) {
            return this.type.isInstance(o);
        }
    }
}
