package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.*;
import java.lang.reflect.*;
import java.util.*;

public class MethodUtils
{
    public static Object invokeMethod(final Object o, final String s, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeMethod(o, s, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Object invokeMethod(final Object o, final String s, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        final Method matchingAccessibleMethod = getMatchingAccessibleMethod(o.getClass(), s, nullToEmpty2);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + s + "() on object: " + o.getClass().getName());
        }
        return matchingAccessibleMethod.invoke(o, nullToEmpty);
    }
    
    public static Object invokeExactMethod(final Object o, final String s, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeExactMethod(o, s, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Object invokeExactMethod(final Object o, final String s, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        final Method accessibleMethod = getAccessibleMethod(o.getClass(), s, nullToEmpty2);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + s + "() on object: " + o.getClass().getName());
        }
        return accessibleMethod.invoke(o, nullToEmpty);
    }
    
    public static Object invokeExactStaticMethod(final Class clazz, final String s, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        final Method accessibleMethod = getAccessibleMethod(clazz, s, nullToEmpty2);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + s + "() on class: " + clazz.getName());
        }
        return accessibleMethod.invoke(null, nullToEmpty);
    }
    
    public static Object invokeStaticMethod(final Class clazz, final String s, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeStaticMethod(clazz, s, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Object invokeStaticMethod(final Class clazz, final String s, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        final Method matchingAccessibleMethod = getMatchingAccessibleMethod(clazz, s, nullToEmpty2);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + s + "() on class: " + clazz.getName());
        }
        return matchingAccessibleMethod.invoke(null, nullToEmpty);
    }
    
    public static Object invokeExactStaticMethod(final Class clazz, final String s, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeExactStaticMethod(clazz, s, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Method getAccessibleMethod(final Class clazz, final String s, final Class... array) {
        return getAccessibleMethod(clazz.getMethod(s, (Class[])array));
    }
    
    public static Method getAccessibleMethod(Method method) {
        if (!MemberUtils.isAccessible(method)) {
            return null;
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        if (Modifier.isPublic(declaringClass.getModifiers())) {
            return method;
        }
        final String name = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        method = getAccessibleMethodFromInterfaceNest(declaringClass, name, (Class[])parameterTypes);
        if (method == null) {
            method = getAccessibleMethodFromSuperclass(declaringClass, name, (Class[])parameterTypes);
        }
        return method;
    }
    
    private static Method getAccessibleMethodFromSuperclass(final Class clazz, final String s, final Class... array) {
        for (Class clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            if (Modifier.isPublic(clazz2.getModifiers())) {
                return clazz2.getMethod(s, (Class[])array);
            }
        }
        return null;
    }
    
    private static Method getAccessibleMethodFromInterfaceNest(Class superclass, final String s, final Class... array) {
        while (superclass != null) {
            final Class[] interfaces = superclass.getInterfaces();
            while (0 < interfaces.length) {
                if (Modifier.isPublic(interfaces[0].getModifiers())) {
                    return interfaces[0].getDeclaredMethod(s, (Class[])array);
                }
                int n = 0;
                ++n;
            }
            superclass = superclass.getSuperclass();
        }
        return null;
    }
    
    public static Method getMatchingAccessibleMethod(final Class clazz, final String s, final Class... array) {
        final Method method = clazz.getMethod(s, (Class[])array);
        MemberUtils.setAccessibleWorkaround(method);
        return method;
    }
    
    public static Set getOverrideHierarchy(final Method method, final ClassUtils.Interfaces interfaces) {
        Validate.notNull(method);
        final LinkedHashSet<Method> set = new LinkedHashSet<Method>();
        set.add(method);
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Class<?> declaringClass = method.getDeclaringClass();
        final Iterator iterator = ClassUtils.hierarchy(declaringClass, interfaces).iterator();
        iterator.next();
    Label_0053:
        while (iterator.hasNext()) {
            final Method matchingAccessibleMethod = getMatchingAccessibleMethod(iterator.next(), method.getName(), (Class[])parameterTypes);
            if (matchingAccessibleMethod == null) {
                continue;
            }
            if (Arrays.equals(matchingAccessibleMethod.getParameterTypes(), parameterTypes)) {
                set.add(matchingAccessibleMethod);
            }
            else {
                final Map typeArguments = TypeUtils.getTypeArguments(declaringClass, matchingAccessibleMethod.getDeclaringClass());
                while (0 < parameterTypes.length) {
                    if (!TypeUtils.equals(TypeUtils.unrollVariables(typeArguments, method.getGenericParameterTypes()[0]), TypeUtils.unrollVariables(typeArguments, matchingAccessibleMethod.getGenericParameterTypes()[0]))) {
                        continue Label_0053;
                    }
                    int n = 0;
                    ++n;
                }
                set.add(matchingAccessibleMethod);
            }
        }
        return set;
    }
}
