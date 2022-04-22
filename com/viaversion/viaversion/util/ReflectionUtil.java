package com.viaversion.viaversion.util;

import java.util.concurrent.*;
import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtil
{
    public static Object invokeStatic(final Class clazz, final String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return clazz.getDeclaredMethod(s, (Class[])new Class[0]).invoke(null, new Object[0]);
    }
    
    public static Object invoke(final Object o, final String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return o.getClass().getDeclaredMethod(s, (Class<?>[])new Class[0]).invoke(o, new Object[0]);
    }
    
    public static Object getStatic(final Class clazz, final String s, final Class clazz2) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = clazz.getDeclaredField(s);
        declaredField.setAccessible(true);
        return clazz2.cast(declaredField.get(null));
    }
    
    public static void setStatic(final Class clazz, final String s, final Object o) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = clazz.getDeclaredField(s);
        declaredField.setAccessible(true);
        declaredField.set(null, o);
    }
    
    public static Object getSuper(final Object o, final String s, final Class clazz) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = o.getClass().getSuperclass().getDeclaredField(s);
        declaredField.setAccessible(true);
        return clazz.cast(declaredField.get(o));
    }
    
    public static Object get(final Object o, final Class clazz, final String s, final Class clazz2) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = clazz.getDeclaredField(s);
        declaredField.setAccessible(true);
        return clazz2.cast(declaredField.get(o));
    }
    
    public static Object get(final Object o, final String s, final Class clazz) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = o.getClass().getDeclaredField(s);
        declaredField.setAccessible(true);
        return clazz.cast(declaredField.get(o));
    }
    
    public static Object getPublic(final Object o, final String s, final Class clazz) throws NoSuchFieldException, IllegalAccessException {
        final Field field = o.getClass().getField(s);
        field.setAccessible(true);
        return clazz.cast(field.get(o));
    }
    
    public static void set(final Object o, final String s, final Object o2) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = o.getClass().getDeclaredField(s);
        declaredField.setAccessible(true);
        declaredField.set(o, o2);
    }
    
    public static final class ClassReflection
    {
        private final Class handle;
        private final Map fields;
        private final Map methods;
        
        public ClassReflection(final Class clazz) {
            this(clazz, true);
        }
        
        public ClassReflection(final Class handle, final boolean b) {
            this.fields = new ConcurrentHashMap();
            this.methods = new ConcurrentHashMap();
            this.scanFields(this.handle = handle, b);
            this.scanMethods(handle, b);
        }
        
        private void scanFields(final Class clazz, final boolean b) {
            if (b && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
                this.scanFields(clazz.getSuperclass(), true);
            }
            final Field[] declaredFields = clazz.getDeclaredFields();
            while (0 < declaredFields.length) {
                final Field field = declaredFields[0];
                field.setAccessible(true);
                this.fields.put(field.getName(), field);
                int n = 0;
                ++n;
            }
        }
        
        private void scanMethods(final Class clazz, final boolean b) {
            if (b && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
                this.scanMethods(clazz.getSuperclass(), true);
            }
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            while (0 < declaredMethods.length) {
                final Method method = declaredMethods[0];
                method.setAccessible(true);
                this.methods.put(method.getName(), method);
                int n = 0;
                ++n;
            }
        }
        
        public Object newInstance() throws ReflectiveOperationException {
            return this.handle.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        
        public Field getField(final String s) {
            return this.fields.get(s);
        }
        
        public void setFieldValue(final String s, final Object o, final Object o2) throws IllegalAccessException {
            this.getField(s).set(o, o2);
        }
        
        public Object getFieldValue(final String s, final Object o, final Class clazz) throws IllegalAccessException {
            return clazz.cast(this.getField(s).get(o));
        }
        
        public Object invokeMethod(final Class clazz, final String s, final Object o, final Object... array) throws InvocationTargetException, IllegalAccessException {
            return clazz.cast(this.getMethod(s).invoke(o, array));
        }
        
        public Method getMethod(final String s) {
            return this.methods.get(s);
        }
        
        public Collection getFields() {
            return Collections.unmodifiableCollection(this.fields.values());
        }
        
        public Collection getMethods() {
            return Collections.unmodifiableCollection(this.methods.values());
        }
    }
}
