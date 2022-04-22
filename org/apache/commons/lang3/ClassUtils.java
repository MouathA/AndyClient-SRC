package org.apache.commons.lang3;

import java.lang.reflect.*;
import org.apache.commons.lang3.mutable.*;
import java.util.*;

public class ClassUtils
{
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final String PACKAGE_SEPARATOR;
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final String INNER_CLASS_SEPARATOR;
    private static final Map primitiveWrapperMap;
    private static final Map wrapperPrimitiveMap;
    private static final Map abbreviationMap;
    private static final Map reverseAbbreviationMap;
    
    public static String getShortClassName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getShortClassName(o.getClass());
    }
    
    public static String getShortClassName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getShortClassName(clazz.getName());
    }
    
    public static String getShortClassName(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        if (s.startsWith("[")) {
            while (s.charAt(0) == '[') {
                s = s.substring(1);
                sb.append("[]");
            }
            if (s.charAt(0) == 'L' && s.charAt(s.length() - 1) == ';') {
                s = s.substring(1, s.length() - 1);
            }
            if (ClassUtils.reverseAbbreviationMap.containsKey(s)) {
                s = ClassUtils.reverseAbbreviationMap.get(s);
            }
        }
        final int lastIndex = s.lastIndexOf(46);
        final int index = s.indexOf(36, (lastIndex == -1) ? 0 : (lastIndex + 1));
        String s2 = s.substring(lastIndex + 1);
        if (index != -1) {
            s2 = s2.replace('$', '.');
        }
        return s2 + (Object)sb;
    }
    
    public static String getSimpleName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return clazz.getSimpleName();
    }
    
    public static String getSimpleName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getSimpleName(o.getClass());
    }
    
    public static String getPackageName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getPackageName(o.getClass());
    }
    
    public static String getPackageName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getPackageName(clazz.getName());
    }
    
    public static String getPackageName(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        while (s.charAt(0) == '[') {
            s = s.substring(1);
        }
        if (s.charAt(0) == 'L' && s.charAt(s.length() - 1) == ';') {
            s = s.substring(1);
        }
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex == -1) {
            return "";
        }
        return s.substring(0, lastIndex);
    }
    
    public static List getAllSuperclasses(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        final ArrayList<Class> list = new ArrayList<Class>();
        for (Class clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            list.add(clazz2);
        }
        return list;
    }
    
    public static List getAllInterfaces(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        final LinkedHashSet set = new LinkedHashSet<Object>();
        getAllInterfaces(clazz, set);
        return new ArrayList(set);
    }
    
    private static void getAllInterfaces(Class superclass, final HashSet set) {
        while (superclass != null) {
            final Class[] interfaces = superclass.getInterfaces();
            while (0 < interfaces.length) {
                final Class clazz = interfaces[0];
                if (set.add(clazz)) {
                    getAllInterfaces(clazz, set);
                }
                int n = 0;
                ++n;
            }
            superclass = superclass.getSuperclass();
        }
    }
    
    public static List convertClassNamesToClasses(final List list) {
        if (list == null) {
            return null;
        }
        final ArrayList<Class<?>> list2 = new ArrayList<Class<?>>(list.size());
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(Class.forName(iterator.next()));
        }
        return list2;
    }
    
    public static List convertClassesToClassNames(final List list) {
        if (list == null) {
            return null;
        }
        final ArrayList<String> list2 = new ArrayList<String>(list.size());
        for (final Class clazz : list) {
            if (clazz == null) {
                list2.add(null);
            }
            else {
                list2.add(clazz.getName());
            }
        }
        return list2;
    }
    
    public static boolean isAssignable(final Class[] array, final Class... array2) {
        return isAssignable(array, array2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }
    
    public static boolean isAssignable(Class[] empty_CLASS_ARRAY, Class[] empty_CLASS_ARRAY2, final boolean b) {
        if (!ArrayUtils.isSameLength(empty_CLASS_ARRAY, empty_CLASS_ARRAY2)) {
            return false;
        }
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (empty_CLASS_ARRAY2 == null) {
            empty_CLASS_ARRAY2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        while (0 < empty_CLASS_ARRAY.length) {
            if (!isAssignable(empty_CLASS_ARRAY[0], empty_CLASS_ARRAY2[0], b)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isPrimitiveOrWrapper(final Class clazz) {
        return clazz != null && (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }
    
    public static boolean isPrimitiveWrapper(final Class clazz) {
        return ClassUtils.wrapperPrimitiveMap.containsKey(clazz);
    }
    
    public static boolean isAssignable(final Class clazz, final Class clazz2) {
        return isAssignable(clazz, clazz2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }
    
    public static boolean isAssignable(Class clazz, final Class clazz2, final boolean b) {
        if (clazz2 == null) {
            return false;
        }
        if (clazz == null) {
            return !clazz2.isPrimitive();
        }
        if (b) {
            if (clazz.isPrimitive() && !clazz2.isPrimitive()) {
                clazz = primitiveToWrapper(clazz);
                if (clazz == null) {
                    return false;
                }
            }
            if (clazz2.isPrimitive() && !clazz.isPrimitive()) {
                clazz = wrapperToPrimitive(clazz);
                if (clazz == null) {
                    return false;
                }
            }
        }
        if (clazz.equals(clazz2)) {
            return true;
        }
        if (!clazz.isPrimitive()) {
            return clazz2.isAssignableFrom(clazz);
        }
        if (!clazz2.isPrimitive()) {
            return false;
        }
        if (Integer.TYPE.equals(clazz)) {
            return Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Long.TYPE.equals(clazz)) {
            return Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Boolean.TYPE.equals(clazz)) {
            return false;
        }
        if (Double.TYPE.equals(clazz)) {
            return false;
        }
        if (Float.TYPE.equals(clazz)) {
            return Double.TYPE.equals(clazz2);
        }
        if (Character.TYPE.equals(clazz)) {
            return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Short.TYPE.equals(clazz)) {
            return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        return Byte.TYPE.equals(clazz) && (Short.TYPE.equals(clazz2) || Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2));
    }
    
    public static Class primitiveToWrapper(final Class clazz) {
        Class clazz2 = clazz;
        if (clazz != null && clazz.isPrimitive()) {
            clazz2 = ClassUtils.primitiveWrapperMap.get(clazz);
        }
        return clazz2;
    }
    
    public static Class[] primitivesToWrappers(final Class... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return array;
        }
        final Class[] array2 = new Class[array.length];
        while (0 < array.length) {
            array2[0] = primitiveToWrapper(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static Class wrapperToPrimitive(final Class clazz) {
        return ClassUtils.wrapperPrimitiveMap.get(clazz);
    }
    
    public static Class[] wrappersToPrimitives(final Class... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return array;
        }
        final Class[] array2 = new Class[array.length];
        while (0 < array.length) {
            array2[0] = wrapperToPrimitive(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static boolean isInnerClass(final Class clazz) {
        return clazz != null && clazz.getEnclosingClass() != null;
    }
    
    public static Class getClass(final ClassLoader classLoader, final String s, final boolean b) throws ClassNotFoundException {
        Class<?> clazz;
        if (ClassUtils.abbreviationMap.containsKey(s)) {
            clazz = Class.forName("[" + ClassUtils.abbreviationMap.get(s), b, classLoader).getComponentType();
        }
        else {
            clazz = Class.forName(toCanonicalName(s), b, classLoader);
        }
        return clazz;
    }
    
    public static Class getClass(final ClassLoader classLoader, final String s) throws ClassNotFoundException {
        return getClass(classLoader, s, true);
    }
    
    public static Class getClass(final String s) throws ClassNotFoundException {
        return getClass(s, true);
    }
    
    public static Class getClass(final String s, final boolean b) throws ClassNotFoundException {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return getClass((contextClassLoader == null) ? ClassUtils.class.getClassLoader() : contextClassLoader, s, b);
    }
    
    public static Method getPublicMethod(final Class clazz, final String s, final Class... array) throws SecurityException, NoSuchMethodException {
        final Method method = clazz.getMethod(s, (Class[])array);
        if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        final ArrayList<Class> list = new ArrayList<Class>();
        list.addAll((Collection<?>)getAllInterfaces(clazz));
        list.addAll((Collection<?>)getAllSuperclasses(clazz));
        for (final Class clazz2 : list) {
            if (!Modifier.isPublic(clazz2.getModifiers())) {
                continue;
            }
            final Method method2 = clazz2.getMethod(s, (Class[])array);
            if (Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
                return method2;
            }
        }
        throw new NoSuchMethodException("Can't find a public method for " + s + " " + ArrayUtils.toString(array));
    }
    
    private static String toCanonicalName(String s) {
        s = StringUtils.deleteWhitespace(s);
        if (s == null) {
            throw new NullPointerException("className must not be null.");
        }
        if (s.endsWith("[]")) {
            final StringBuilder sb = new StringBuilder();
            while (s.endsWith("[]")) {
                s = s.substring(0, s.length() - 2);
                sb.append("[");
            }
            final String s2 = ClassUtils.abbreviationMap.get(s);
            if (s2 != null) {
                sb.append(s2);
            }
            else {
                sb.append("L").append(s).append(";");
            }
            s = sb.toString();
        }
        return s;
    }
    
    public static Class[] toClass(final Object... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class[] array2 = new Class[array.length];
        while (0 < array.length) {
            array2[0] = ((array[0] == null) ? null : array[0].getClass());
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static String getShortCanonicalName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getShortCanonicalName(o.getClass().getName());
    }
    
    public static String getShortCanonicalName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getShortCanonicalName(clazz.getName());
    }
    
    public static String getShortCanonicalName(final String s) {
        return getShortClassName(getCanonicalName(s));
    }
    
    public static String getPackageCanonicalName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getPackageCanonicalName(o.getClass().getName());
    }
    
    public static String getPackageCanonicalName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getPackageCanonicalName(clazz.getName());
    }
    
    public static String getPackageCanonicalName(final String s) {
        return getPackageName(getCanonicalName(s));
    }
    
    private static String getCanonicalName(String s) {
        s = StringUtils.deleteWhitespace(s);
        if (s == null) {
            return null;
        }
        while (s.startsWith("[")) {
            int n = 0;
            ++n;
            s = s.substring(1);
        }
        if (0 < 1) {
            return s;
        }
        if (s.startsWith("L")) {
            s = s.substring(1, s.endsWith(";") ? (s.length() - 1) : s.length());
        }
        else if (s.length() > 0) {
            s = ClassUtils.reverseAbbreviationMap.get(s.substring(0, 1));
        }
        final StringBuilder sb = new StringBuilder(s);
        while (0 < 0) {
            sb.append("[]");
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    public static Iterable hierarchy(final Class clazz) {
        return hierarchy(clazz, Interfaces.EXCLUDE);
    }
    
    public static Iterable hierarchy(final Class clazz, final Interfaces interfaces) {
        final Iterable iterable = new Iterable(clazz) {
            final Class val$type;
            
            @Override
            public Iterator iterator() {
                return new Iterator(new MutableObject(this.val$type)) {
                    final MutableObject val$next;
                    final ClassUtils$1 this$0;
                    
                    @Override
                    public boolean hasNext() {
                        return this.val$next.getValue() != null;
                    }
                    
                    @Override
                    public Class next() {
                        final Class clazz = (Class)this.val$next.getValue();
                        this.val$next.setValue(clazz.getSuperclass());
                        return clazz;
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
        };
        if (interfaces != Interfaces.INCLUDE) {
            return iterable;
        }
        return new Iterable(iterable) {
            final Iterable val$classes;
            
            @Override
            public Iterator iterator() {
                return new Iterator((Iterator)this.val$classes.iterator(), (Set)new HashSet()) {
                    Iterator interfaces = Collections.emptySet().iterator();
                    final Iterator val$wrapped;
                    final Set val$seenInterfaces;
                    final ClassUtils$2 this$0;
                    
                    @Override
                    public boolean hasNext() {
                        return this.interfaces.hasNext() || this.val$wrapped.hasNext();
                    }
                    
                    @Override
                    public Class next() {
                        if (this.interfaces.hasNext()) {
                            final Class clazz = this.interfaces.next();
                            this.val$seenInterfaces.add(clazz);
                            return clazz;
                        }
                        final Class clazz2 = this.val$wrapped.next();
                        final LinkedHashSet set = new LinkedHashSet();
                        this.walkInterfaces(set, clazz2);
                        this.interfaces = set.iterator();
                        return clazz2;
                    }
                    
                    private void walkInterfaces(final Set set, final Class clazz) {
                        final Class[] interfaces = clazz.getInterfaces();
                        while (0 < interfaces.length) {
                            final Class clazz2 = interfaces[0];
                            if (!this.val$seenInterfaces.contains(clazz2)) {
                                set.add(clazz2);
                            }
                            this.walkInterfaces(set, clazz2);
                            int n = 0;
                            ++n;
                        }
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
        };
    }
    
    static {
        PACKAGE_SEPARATOR = String.valueOf('.');
        INNER_CLASS_SEPARATOR = String.valueOf('$');
        (primitiveWrapperMap = new HashMap()).put(Boolean.TYPE, Boolean.class);
        ClassUtils.primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        ClassUtils.primitiveWrapperMap.put(Character.TYPE, Character.class);
        ClassUtils.primitiveWrapperMap.put(Short.TYPE, Short.class);
        ClassUtils.primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        ClassUtils.primitiveWrapperMap.put(Long.TYPE, Long.class);
        ClassUtils.primitiveWrapperMap.put(Double.TYPE, Double.class);
        ClassUtils.primitiveWrapperMap.put(Float.TYPE, Float.class);
        ClassUtils.primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap();
        for (final Class clazz : ClassUtils.primitiveWrapperMap.keySet()) {
            final Class clazz2 = ClassUtils.primitiveWrapperMap.get(clazz);
            if (!clazz.equals(clazz2)) {
                ClassUtils.wrapperPrimitiveMap.put(clazz2, clazz);
            }
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("int", "I");
        hashMap.put("boolean", "Z");
        hashMap.put("float", "F");
        hashMap.put("long", "J");
        hashMap.put("short", "S");
        hashMap.put("byte", "B");
        hashMap.put("double", "D");
        hashMap.put("char", "C");
        hashMap.put("void", "V");
        final HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>();
        for (final Map.Entry<? extends K, ? extends V> entry : hashMap.entrySet()) {
            hashMap2.put(entry.getValue(), entry.getKey());
        }
        abbreviationMap = Collections.unmodifiableMap((Map<?, ?>)hashMap);
        reverseAbbreviationMap = Collections.unmodifiableMap((Map<?, ?>)hashMap2);
    }
    
    public enum Interfaces
    {
        INCLUDE("INCLUDE", 0), 
        EXCLUDE("EXCLUDE", 1);
        
        private static final Interfaces[] $VALUES;
        
        private Interfaces(final String s, final int n) {
        }
        
        static {
            $VALUES = new Interfaces[] { Interfaces.INCLUDE, Interfaces.EXCLUDE };
        }
    }
}
