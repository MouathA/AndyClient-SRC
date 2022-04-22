package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.*;
import java.lang.reflect.*;
import java.util.*;

public class FieldUtils
{
    public static Field getField(final Class clazz, final String s) {
        final Field field = getField(clazz, s, false);
        MemberUtils.setAccessibleWorkaround(field);
        return field;
    }
    
    public static Field getField(final Class clazz, final String s, final boolean b) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        Validate.isTrue(StringUtils.isNotBlank(s), "The field name must not be blank/empty", new Object[0]);
        Class superclass = clazz;
        while (superclass != null) {
            final Field declaredField = superclass.getDeclaredField(s);
            if (!Modifier.isPublic(declaredField.getModifiers())) {
                if (!b) {
                    superclass = superclass.getSuperclass();
                    continue;
                }
                declaredField.setAccessible(true);
            }
            return declaredField;
        }
        Field field = null;
        final Iterator<Class> iterator = (Iterator<Class>)ClassUtils.getAllInterfaces(clazz).iterator();
        while (iterator.hasNext()) {
            final Field field2 = iterator.next().getField(s);
            Validate.isTrue(field == null, "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", s, clazz);
            field = field2;
        }
        return field;
    }
    
    public static Field getDeclaredField(final Class clazz, final String s) {
        return getDeclaredField(clazz, s, false);
    }
    
    public static Field getDeclaredField(final Class clazz, final String s, final boolean b) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        Validate.isTrue(StringUtils.isNotBlank(s), "The field name must not be blank/empty", new Object[0]);
        final Field declaredField = clazz.getDeclaredField(s);
        if (!MemberUtils.isAccessible(declaredField)) {
            if (!b) {
                return null;
            }
            declaredField.setAccessible(true);
        }
        return declaredField;
    }
    
    public static Field[] getAllFields(final Class clazz) {
        final List allFieldsList = getAllFieldsList(clazz);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }
    
    public static List getAllFieldsList(final Class clazz) {
        Validate.isTrue(clazz != null, "The class must not be null", new Object[0]);
        final ArrayList<Field> list = new ArrayList<Field>();
        for (Class superclass = clazz; superclass != null; superclass = superclass.getSuperclass()) {
            final Field[] declaredFields = superclass.getDeclaredFields();
            while (0 < declaredFields.length) {
                list.add(declaredFields[0]);
                int n = 0;
                ++n;
            }
        }
        return list;
    }
    
    public static Object readStaticField(final Field field) throws IllegalAccessException {
        return readStaticField(field, false);
    }
    
    public static Object readStaticField(final Field field, final boolean b) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", field.getName());
        return readField(field, (Object)null, b);
    }
    
    public static Object readStaticField(final Class clazz, final String s) throws IllegalAccessException {
        return readStaticField(clazz, s, false);
    }
    
    public static Object readStaticField(final Class clazz, final String s, final boolean b) throws IllegalAccessException {
        final Field field = getField(clazz, s, b);
        Validate.isTrue(field != null, "Cannot locate field '%s' on %s", s, clazz);
        return readStaticField(field, false);
    }
    
    public static Object readDeclaredStaticField(final Class clazz, final String s) throws IllegalAccessException {
        return readDeclaredStaticField(clazz, s, false);
    }
    
    public static Object readDeclaredStaticField(final Class clazz, final String s, final boolean b) throws IllegalAccessException {
        final Field declaredField = getDeclaredField(clazz, s, b);
        Validate.isTrue(declaredField != null, "Cannot locate declared field %s.%s", clazz.getName(), s);
        return readStaticField(declaredField, false);
    }
    
    public static Object readField(final Field field, final Object o) throws IllegalAccessException {
        return readField(field, o, false);
    }
    
    public static Object readField(final Field accessibleWorkaround, final Object o, final boolean b) throws IllegalAccessException {
        Validate.isTrue(accessibleWorkaround != null, "The field must not be null", new Object[0]);
        if (b && !accessibleWorkaround.isAccessible()) {
            accessibleWorkaround.setAccessible(true);
        }
        else {
            MemberUtils.setAccessibleWorkaround(accessibleWorkaround);
        }
        return accessibleWorkaround.get(o);
    }
    
    public static Object readField(final Object o, final String s) throws IllegalAccessException {
        return readField(o, s, false);
    }
    
    public static Object readField(final Object o, final String s, final boolean b) throws IllegalAccessException {
        Validate.isTrue(o != null, "target object must not be null", new Object[0]);
        final Class<?> class1 = o.getClass();
        final Field field = getField(class1, s, b);
        Validate.isTrue(field != null, "Cannot locate field %s on %s", s, class1);
        return readField(field, o, false);
    }
    
    public static Object readDeclaredField(final Object o, final String s) throws IllegalAccessException {
        return readDeclaredField(o, s, false);
    }
    
    public static Object readDeclaredField(final Object o, final String s, final boolean b) throws IllegalAccessException {
        Validate.isTrue(o != null, "target object must not be null", new Object[0]);
        final Class<?> class1 = o.getClass();
        final Field declaredField = getDeclaredField(class1, s, b);
        Validate.isTrue(declaredField != null, "Cannot locate declared field %s.%s", class1, s);
        return readField(declaredField, o, false);
    }
    
    public static void writeStaticField(final Field field, final Object o) throws IllegalAccessException {
        writeStaticField(field, o, false);
    }
    
    public static void writeStaticField(final Field field, final Object o, final boolean b) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", field.getDeclaringClass().getName(), field.getName());
        writeField(field, (Object)null, o, b);
    }
    
    public static void writeStaticField(final Class clazz, final String s, final Object o) throws IllegalAccessException {
        writeStaticField(clazz, s, o, false);
    }
    
    public static void writeStaticField(final Class clazz, final String s, final Object o, final boolean b) throws IllegalAccessException {
        final Field field = getField(clazz, s, b);
        Validate.isTrue(field != null, "Cannot locate field %s on %s", s, clazz);
        writeStaticField(field, o, false);
    }
    
    public static void writeDeclaredStaticField(final Class clazz, final String s, final Object o) throws IllegalAccessException {
        writeDeclaredStaticField(clazz, s, o, false);
    }
    
    public static void writeDeclaredStaticField(final Class clazz, final String s, final Object o, final boolean b) throws IllegalAccessException {
        final Field declaredField = getDeclaredField(clazz, s, b);
        Validate.isTrue(declaredField != null, "Cannot locate declared field %s.%s", clazz.getName(), s);
        writeField(declaredField, (Object)null, o, false);
    }
    
    public static void writeField(final Field field, final Object o, final Object o2) throws IllegalAccessException {
        writeField(field, o, o2, false);
    }
    
    public static void writeField(final Field accessibleWorkaround, final Object o, final Object o2, final boolean b) throws IllegalAccessException {
        Validate.isTrue(accessibleWorkaround != null, "The field must not be null", new Object[0]);
        if (b && !accessibleWorkaround.isAccessible()) {
            accessibleWorkaround.setAccessible(true);
        }
        else {
            MemberUtils.setAccessibleWorkaround(accessibleWorkaround);
        }
        accessibleWorkaround.set(o, o2);
    }
    
    public static void removeFinalModifier(final Field field) {
        removeFinalModifier(field, true);
    }
    
    public static void removeFinalModifier(final Field field, final boolean b) {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        if (Modifier.isFinal(field.getModifiers())) {
            final Field declaredField = Field.class.getDeclaredField("modifiers");
            final boolean b2 = b && !declaredField.isAccessible();
            if (b2) {
                declaredField.setAccessible(true);
            }
            declaredField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            if (b2) {
                declaredField.setAccessible(false);
            }
        }
    }
    
    public static void writeField(final Object o, final String s, final Object o2) throws IllegalAccessException {
        writeField(o, s, o2, false);
    }
    
    public static void writeField(final Object o, final String s, final Object o2, final boolean b) throws IllegalAccessException {
        Validate.isTrue(o != null, "target object must not be null", new Object[0]);
        final Class<?> class1 = o.getClass();
        final Field field = getField(class1, s, b);
        Validate.isTrue(field != null, "Cannot locate declared field %s.%s", class1.getName(), s);
        writeField(field, o, o2, false);
    }
    
    public static void writeDeclaredField(final Object o, final String s, final Object o2) throws IllegalAccessException {
        writeDeclaredField(o, s, o2, false);
    }
    
    public static void writeDeclaredField(final Object o, final String s, final Object o2, final boolean b) throws IllegalAccessException {
        Validate.isTrue(o != null, "target object must not be null", new Object[0]);
        final Class<?> class1 = o.getClass();
        final Field declaredField = getDeclaredField(class1, s, b);
        Validate.isTrue(declaredField != null, "Cannot locate declared field %s.%s", class1.getName(), s);
        writeField(declaredField, o, o2, false);
    }
}
