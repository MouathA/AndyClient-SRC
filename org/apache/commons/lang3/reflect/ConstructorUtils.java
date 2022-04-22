package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.*;
import java.lang.reflect.*;

public class ConstructorUtils
{
    public static Object invokeConstructor(final Class clazz, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeConstructor(clazz, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Object invokeConstructor(final Class clazz, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        final Constructor matchingAccessibleConstructor = getMatchingAccessibleConstructor(clazz, nullToEmpty2);
        if (matchingAccessibleConstructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        return matchingAccessibleConstructor.newInstance(nullToEmpty);
    }
    
    public static Object invokeExactConstructor(final Class clazz, Object... nullToEmpty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        return invokeExactConstructor(clazz, nullToEmpty, ClassUtils.toClass(nullToEmpty));
    }
    
    public static Object invokeExactConstructor(final Class clazz, Object[] nullToEmpty, Class[] nullToEmpty2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        nullToEmpty = ArrayUtils.nullToEmpty(nullToEmpty);
        nullToEmpty2 = ArrayUtils.nullToEmpty(nullToEmpty2);
        final Constructor accessibleConstructor = getAccessibleConstructor(clazz, nullToEmpty2);
        if (accessibleConstructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        return accessibleConstructor.newInstance(nullToEmpty);
    }
    
    public static Constructor getAccessibleConstructor(final Class clazz, final Class... array) {
        Validate.notNull(clazz, "class cannot be null", new Object[0]);
        return getAccessibleConstructor(clazz.getConstructor((Class[])array));
    }
    
    public static Constructor getAccessibleConstructor(final Constructor constructor) {
        Validate.notNull(constructor, "constructor cannot be null", new Object[0]);
        return (MemberUtils.isAccessible(constructor) && isAccessible(constructor.getDeclaringClass())) ? constructor : null;
    }
    
    public static Constructor getMatchingAccessibleConstructor(final Class clazz, final Class... array) {
        Validate.notNull(clazz, "class cannot be null", new Object[0]);
        final Constructor constructor = clazz.getConstructor((Class[])array);
        MemberUtils.setAccessibleWorkaround(constructor);
        return constructor;
    }
    
    private static boolean isAccessible(final Class clazz) {
        for (Class enclosingClass = clazz; enclosingClass != null; enclosingClass = enclosingClass.getEnclosingClass()) {
            if (!Modifier.isPublic(enclosingClass.getModifiers())) {
                return false;
            }
        }
        return true;
    }
}
