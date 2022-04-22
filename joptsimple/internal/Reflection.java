package joptsimple.internal;

import joptsimple.*;
import java.lang.reflect.*;

public final class Reflection
{
    private Reflection() {
        throw new UnsupportedOperationException();
    }
    
    public static ValueConverter findConverter(final Class clazz) {
        final Class wrapper = Classes.wrapperOf(clazz);
        final ValueConverter valueOfConverter = valueOfConverter(wrapper);
        if (valueOfConverter != null) {
            return valueOfConverter;
        }
        final ValueConverter constructorConverter = constructorConverter(wrapper);
        if (constructorConverter != null) {
            return constructorConverter;
        }
        throw new IllegalArgumentException(clazz + " is not a value type");
    }
    
    private static ValueConverter valueOfConverter(final Class clazz) {
        final Method declaredMethod = clazz.getDeclaredMethod("valueOf", String.class);
        if (meetsConverterRequirements(declaredMethod, clazz)) {
            return new MethodInvokingValueConverter(declaredMethod, clazz);
        }
        return null;
    }
    
    private static ValueConverter constructorConverter(final Class clazz) {
        return new ConstructorInvokingValueConverter(clazz.getConstructor(String.class));
    }
    
    public static Object instantiate(final Constructor constructor, final Object... array) {
        return constructor.newInstance(array);
    }
    
    public static Object invoke(final Method method, final Object... array) {
        return method.invoke(null, array);
    }
    
    public static Object convertWith(final ValueConverter valueConverter, final String s) {
        return (valueConverter == null) ? s : valueConverter.convert(s);
    }
    
    private static boolean meetsConverterRequirements(final Method method, final Class clazz) {
        final int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && clazz.equals(method.getReturnType());
    }
    
    private static RuntimeException reflectionException(final Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return new ReflectionException(ex);
        }
        if (ex instanceof InvocationTargetException) {
            return new ReflectionException(ex.getCause());
        }
        if (ex instanceof RuntimeException) {
            return (RuntimeException)ex;
        }
        return new ReflectionException(ex);
    }
}
