package org.apache.commons.lang3;

import org.apache.commons.lang3.text.*;
import org.apache.commons.lang3.mutable.*;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;

public class ObjectUtils
{
    public static final Null NULL;
    
    public static Object defaultIfNull(final Object o, final Object o2) {
        return (o != null) ? o : o2;
    }
    
    public static Object firstNonNull(final Object... array) {
        if (array != null) {
            while (0 < array.length) {
                final Object o = array[0];
                if (o != null) {
                    return o;
                }
                int n = 0;
                ++n;
            }
        }
        return null;
    }
    
    @Deprecated
    public static boolean equals(final Object o, final Object o2) {
        return o == o2 || (o != null && o2 != null && o.equals(o2));
    }
    
    public static boolean notEqual(final Object o, final Object o2) {
        return !equals(o, o2);
    }
    
    @Deprecated
    public static int hashCode(final Object o) {
        return (o == null) ? 0 : o.hashCode();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Deprecated
    public static int hashCodeMulti(final Object... array) {
        if (array != null) {
            while (0 < array.length) {
                final int n = 31 + hashCode(array[0]);
                int n2 = 0;
                ++n2;
            }
        }
        return 1;
    }
    
    public static String identityToString(final Object o) {
        if (o == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        identityToString(sb, o);
        return sb.toString();
    }
    
    public static void identityToString(final Appendable appendable, final Object o) throws IOException {
        if (o == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        appendable.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    public static void identityToString(final StrBuilder strBuilder, final Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        strBuilder.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    public static void identityToString(final StringBuffer sb, final Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    public static void identityToString(final StringBuilder sb, final Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    @Deprecated
    public static String toString(final Object o) {
        return (o == null) ? "" : o.toString();
    }
    
    @Deprecated
    public static String toString(final Object o, final String s) {
        return (o == null) ? s : o.toString();
    }
    
    public static Comparable min(final Comparable... array) {
        Comparable comparable = null;
        if (array != null) {
            while (0 < array.length) {
                final Comparable comparable2 = array[0];
                if (compare(comparable2, comparable, true) < 0) {
                    comparable = comparable2;
                }
                int n = 0;
                ++n;
            }
        }
        return comparable;
    }
    
    public static Comparable max(final Comparable... array) {
        Comparable comparable = null;
        if (array != null) {
            while (0 < array.length) {
                final Comparable comparable2 = array[0];
                if (compare(comparable2, comparable, false) > 0) {
                    comparable = comparable2;
                }
                int n = 0;
                ++n;
            }
        }
        return comparable;
    }
    
    public static int compare(final Comparable comparable, final Comparable comparable2) {
        return compare(comparable, comparable2, false);
    }
    
    public static int compare(final Comparable comparable, final Comparable comparable2, final boolean b) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return b ? 1 : -1;
        }
        if (comparable2 == null) {
            return b ? -1 : 1;
        }
        return comparable.compareTo(comparable2);
    }
    
    public static Comparable median(final Comparable... array) {
        Validate.notEmpty(array);
        Validate.noNullElements(array);
        final TreeSet<Object> set = new TreeSet<Object>();
        Collections.addAll(set, (Comparable[])array);
        return (Comparable)set.toArray()[(set.size() - 1) / 2];
    }
    
    public static Object median(final Comparator comparator, final Object... array) {
        Validate.notEmpty(array, "null/empty items", new Object[0]);
        Validate.noNullElements(array);
        Validate.notNull(comparator, "null comparator", new Object[0]);
        final TreeSet<Object> set = new TreeSet<Object>(comparator);
        Collections.addAll(set, array);
        return set.toArray()[(set.size() - 1) / 2];
    }
    
    public static Object mode(final Object... array) {
        if (ArrayUtils.isNotEmpty(array)) {
            final HashMap<Object, MutableInt> hashMap = new HashMap<Object, MutableInt>(array.length);
            final int length = array.length;
            while (0 < 0) {
                final Object o = array[0];
                final MutableInt mutableInt = hashMap.get(o);
                if (mutableInt == null) {
                    hashMap.put(o, new MutableInt(1));
                }
                else {
                    mutableInt.increment();
                }
                int n = 0;
                ++n;
            }
            Object key = null;
            for (final Map.Entry<Object, MutableInt> entry : hashMap.entrySet()) {
                final int intValue = entry.getValue().intValue();
                if (intValue == 0) {
                    key = null;
                }
                else {
                    if (intValue <= 0) {
                        continue;
                    }
                    key = entry.getKey();
                }
            }
            return key;
        }
        return null;
    }
    
    public static Object clone(final Object o) {
        if (o instanceof Cloneable) {
            Object o2;
            if (o.getClass().isArray()) {
                final Class<?> componentType = o.getClass().getComponentType();
                if (!componentType.isPrimitive()) {
                    o2 = ((Object[])o).clone();
                }
                else {
                    int length = Array.getLength(o);
                    o2 = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(o2, length, Array.get(o, length));
                    }
                }
            }
            else {
                o2 = o.getClass().getMethod("clone", (Class<?>[])new Class[0]).invoke(o, new Object[0]);
            }
            return o2;
        }
        return null;
    }
    
    public static Object cloneIfPossible(final Object o) {
        final Object clone = clone(o);
        return (clone == null) ? o : clone;
    }
    
    public static boolean CONST(final boolean b) {
        return b;
    }
    
    public static byte CONST(final byte b) {
        return b;
    }
    
    public static byte CONST_BYTE(final int n) throws IllegalArgumentException {
        if (n < -128 || n > 127) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + n + "]");
        }
        return (byte)n;
    }
    
    public static char CONST(final char c) {
        return c;
    }
    
    public static short CONST(final short n) {
        return n;
    }
    
    public static short CONST_SHORT(final int n) throws IllegalArgumentException {
        if (n < -32768 || n > 32767) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + n + "]");
        }
        return (short)n;
    }
    
    public static int CONST(final int n) {
        return n;
    }
    
    public static long CONST(final long n) {
        return n;
    }
    
    public static float CONST(final float n) {
        return n;
    }
    
    public static double CONST(final double n) {
        return n;
    }
    
    public static Object CONST(final Object o) {
        return o;
    }
    
    static {
        NULL = new Null();
    }
    
    public static class Null implements Serializable
    {
        private static final long serialVersionUID = 7092611880189329093L;
        
        Null() {
        }
        
        private Object readResolve() {
            return ObjectUtils.NULL;
        }
    }
}
