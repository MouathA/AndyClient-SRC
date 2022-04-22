package wdl;

import java.lang.reflect.*;

public class ReflectionUtils
{
    public static Field stealField(final Class clazz, final Class clazz2) {
        Field[] declaredFields;
        while (0 < (declaredFields = clazz.getDeclaredFields()).length) {
            final Field field = declaredFields[0];
            if (field.getType().equals(clazz2)) {
                field.setAccessible(true);
                return field;
            }
            int n = 0;
            ++n;
        }
        throw new RuntimeException("WorldDownloader: Couldn't steal Field of type \"" + clazz2 + "\" from class \"" + clazz + "\" !");
    }
    
    public static Object stealAndGetField(Object o, final Class clazz) {
        Class<?> class1;
        if (o instanceof Class) {
            class1 = (Class<?>)o;
            o = null;
        }
        else {
            class1 = o.getClass();
        }
        return clazz.cast(stealField(class1, clazz).get(o));
    }
    
    public static void stealAndSetField(Object o, final Class clazz, final Object o2) {
        Class<?> class1;
        if (o instanceof Class) {
            class1 = (Class<?>)o;
            o = null;
        }
        else {
            class1 = o.getClass();
        }
        stealField(class1, clazz).set(o, o2);
    }
    
    public static Object stealAndGetField(final Object o, final Class clazz, final Class clazz2) {
        return clazz2.cast(stealField(clazz, clazz2).get(o));
    }
    
    public static void stealAndSetField(final Object o, final Class clazz, final Class clazz2, final Object o2) {
        stealField(clazz, clazz2).set(o, o2);
    }
}
