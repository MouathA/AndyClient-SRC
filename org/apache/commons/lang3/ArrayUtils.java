package org.apache.commons.lang3;

import org.apache.commons.lang3.builder.*;
import java.lang.reflect.*;
import org.apache.commons.lang3.mutable.*;
import java.util.*;

public class ArrayUtils
{
    public static final Object[] EMPTY_OBJECT_ARRAY;
    public static final Class[] EMPTY_CLASS_ARRAY;
    public static final String[] EMPTY_STRING_ARRAY;
    public static final long[] EMPTY_LONG_ARRAY;
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY;
    public static final int[] EMPTY_INT_ARRAY;
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY;
    public static final short[] EMPTY_SHORT_ARRAY;
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY;
    public static final double[] EMPTY_DOUBLE_ARRAY;
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY;
    public static final float[] EMPTY_FLOAT_ARRAY;
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY;
    public static final boolean[] EMPTY_BOOLEAN_ARRAY;
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY;
    public static final char[] EMPTY_CHAR_ARRAY;
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY;
    public static final int INDEX_NOT_FOUND = -1;
    
    public static String toString(final Object o) {
        return toString(o, "{}");
    }
    
    public static String toString(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return new ToStringBuilder(o, ToStringStyle.SIMPLE_STYLE).append(o).toString();
    }
    
    public static int hashCode(final Object o) {
        return new HashCodeBuilder().append(o).toHashCode();
    }
    
    @Deprecated
    public static boolean isEquals(final Object o, final Object o2) {
        return new EqualsBuilder().append(o, o2).isEquals();
    }
    
    public static Map toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>((int)(array.length * 1.5));
        for (int i = 0; i < array.length; ++i) {
            final Object o = array[i];
            if (o instanceof Map.Entry) {
                final Map.Entry<Object, V> entry = (Map.Entry<Object, V>)o;
                hashMap.put(entry.getKey(), entry.getValue());
            }
            else {
                if (!(o instanceof Object[])) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + o + "', is neither of type Map.Entry nor an Array");
                }
                final Object[] array2 = (Object[])o;
                if (array2.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + o + "', has a length less than 2");
                }
                hashMap.put(array2[0], array2[1]);
            }
        }
        return hashMap;
    }
    
    public static Object[] toArray(final Object... array) {
        return array;
    }
    
    public static Object[] clone(final Object[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static long[] clone(final long[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static int[] clone(final int[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static short[] clone(final short[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static char[] clone(final char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static byte[] clone(final byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static double[] clone(final double[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static float[] clone(final float[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static boolean[] clone(final boolean[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static Object[] nullToEmpty(final Object[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Class[] nullToEmpty(final Class[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        return array;
    }
    
    public static String[] nullToEmpty(final String[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return array;
    }
    
    public static long[] nullToEmpty(final long[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        return array;
    }
    
    public static int[] nullToEmpty(final int[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return array;
    }
    
    public static short[] nullToEmpty(final short[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        return array;
    }
    
    public static char[] nullToEmpty(final char[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        return array;
    }
    
    public static byte[] nullToEmpty(final byte[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return array;
    }
    
    public static double[] nullToEmpty(final double[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }
    
    public static float[] nullToEmpty(final float[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        return array;
    }
    
    public static boolean[] nullToEmpty(final boolean[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }
    
    public static Long[] nullToEmpty(final Long[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Integer[] nullToEmpty(final Integer[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Short[] nullToEmpty(final Short[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Character[] nullToEmpty(final Character[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Byte[] nullToEmpty(final Byte[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Double[] nullToEmpty(final Double[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Float[] nullToEmpty(final Float[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Boolean[] nullToEmpty(final Boolean[] array) {
        if (array == null || array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }
    
    public static Object[] subarray(final Object[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        final Class<?> componentType = array.getClass().getComponentType();
        if (n2 <= 0) {
            return (Object[])Array.newInstance(componentType, 0);
        }
        final Object[] array2 = (Object[])Array.newInstance(componentType, n2);
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static long[] subarray(final long[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] array2 = new long[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static int[] subarray(final int[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] array2 = new int[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static short[] subarray(final short[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] array2 = new short[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static char[] subarray(final char[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array2 = new char[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static byte[] subarray(final byte[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static double[] subarray(final double[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] array2 = new double[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static float[] subarray(final float[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] array2 = new float[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static boolean[] subarray(final boolean[] array, int n, int length) {
        if (array == null) {
            return null;
        }
        if (n < 0) {
            n = 0;
        }
        if (length > array.length) {
            length = array.length;
        }
        final int n2 = length - n;
        if (n2 <= 0) {
            return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] array2 = new boolean[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static boolean isSameLength(final Object[] array, final Object[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final long[] array, final long[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final int[] array, final int[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final short[] array, final short[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final char[] array, final char[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final byte[] array, final byte[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final double[] array, final double[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final float[] array, final float[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static boolean isSameLength(final boolean[] array, final boolean[] array2) {
        return (array != null || array2 == null || array2.length <= 0) && (array2 != null || array == null || array.length <= 0) && (array == null || array2 == null || array.length == array2.length);
    }
    
    public static int getLength(final Object o) {
        if (o == null) {
            return 0;
        }
        return Array.getLength(o);
    }
    
    public static boolean isSameType(final Object o, final Object o2) {
        if (o == null || o2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return o.getClass().getName().equals(o2.getClass().getName());
    }
    
    public static void reverse(final Object[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final long[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final int[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final short[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final char[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final byte[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final double[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final float[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final boolean[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    
    public static void reverse(final boolean[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final boolean b = array[i];
            array[i] = array[n3];
            array[n3] = b;
        }
    }
    
    public static void reverse(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final byte b = array[i];
            array[i] = array[n3];
            array[n3] = b;
        }
    }
    
    public static void reverse(final char[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final char c = array[i];
            array[i] = array[n3];
            array[n3] = c;
        }
    }
    
    public static void reverse(final double[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final double n4 = array[i];
            array[i] = array[n3];
            array[n3] = n4;
        }
    }
    
    public static void reverse(final float[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final float n4 = array[i];
            array[i] = array[n3];
            array[n3] = n4;
        }
    }
    
    public static void reverse(final int[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final int n4 = array[i];
            array[i] = array[n3];
            array[n3] = n4;
        }
    }
    
    public static void reverse(final long[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final long n4 = array[i];
            array[i] = array[n3];
            array[n3] = n4;
        }
    }
    
    public static void reverse(final Object[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final Object o = array[i];
            array[i] = array[n3];
            array[n3] = o;
        }
    }
    
    public static void reverse(final short[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        for (int n3 = (n < 0) ? 0 : n, i = Math.min(array.length, n2) - 1; i > n3; --i, ++n3) {
            final short n4 = array[i];
            array[i] = array[n3];
            array[n3] = n4;
        }
    }
    
    public static int indexOf(final Object[] array, final Object o) {
        return indexOf(array, o, 0);
    }
    
    public static int indexOf(final Object[] array, final Object o, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        if (o == null) {
            for (int i = n; i < array.length; ++i) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else if (array.getClass().getComponentType().isInstance(o)) {
            for (int j = n; j < array.length; ++j) {
                if (o.equals(array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final Object[] array, final Object o) {
        return lastIndexOf(array, o, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final Object[] array, final Object o, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= array.length) {
            n = array.length - 1;
        }
        if (o == null) {
            for (int i = n; i >= 0; --i) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else if (array.getClass().getComponentType().isInstance(o)) {
            for (int j = n; j >= 0; --j) {
                if (o.equals(array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public static boolean contains(final Object[] array, final Object o) {
        return indexOf(array, o) != -1;
    }
    
    public static int indexOf(final long[] array, final long n) {
        return indexOf(array, n, 0);
    }
    
    public static int indexOf(final long[] array, final long n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        for (int i = n2; i < array.length; ++i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final long[] array, final long n) {
        return lastIndexOf(array, n, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final long[] array, final long n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final long[] array, final long n) {
        return indexOf(array, n) != -1;
    }
    
    public static int indexOf(final int[] array, final int n) {
        return indexOf(array, n, 0);
    }
    
    public static int indexOf(final int[] array, final int n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        for (int i = n2; i < array.length; ++i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final int[] array, final int n) {
        return lastIndexOf(array, n, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final int[] array, final int n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final int[] array, final int n) {
        return indexOf(array, n) != -1;
    }
    
    public static int indexOf(final short[] array, final short n) {
        return indexOf(array, n, 0);
    }
    
    public static int indexOf(final short[] array, final short n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        for (int i = n2; i < array.length; ++i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final short[] array, final short n) {
        return lastIndexOf(array, n, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final short[] array, final short n, int n2) {
        if (array == null) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final short[] array, final short n) {
        return indexOf(array, n) != -1;
    }
    
    public static int indexOf(final char[] array, final char c) {
        return indexOf(array, c, 0);
    }
    
    public static int indexOf(final char[] array, final char c, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        for (int i = n; i < array.length; ++i) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final char[] array, final char c) {
        return lastIndexOf(array, c, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final char[] array, final char c, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= array.length) {
            n = array.length - 1;
        }
        for (int i = n; i >= 0; --i) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final char[] array, final char c) {
        return indexOf(array, c) != -1;
    }
    
    public static int indexOf(final byte[] array, final byte b) {
        return indexOf(array, b, 0);
    }
    
    public static int indexOf(final byte[] array, final byte b, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        for (int i = n; i < array.length; ++i) {
            if (b == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final byte[] array, final byte b) {
        return lastIndexOf(array, b, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final byte[] array, final byte b, int n) {
        if (array == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= array.length) {
            n = array.length - 1;
        }
        for (int i = n; i >= 0; --i) {
            if (b == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final byte[] array, final byte b) {
        return indexOf(array, b) != -1;
    }
    
    public static int indexOf(final double[] array, final double n) {
        return indexOf(array, n, 0);
    }
    
    public static int indexOf(final double[] array, final double n, final double n2) {
        return indexOf(array, n, 0, n2);
    }
    
    public static int indexOf(final double[] array, final double n, int n2) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        for (int i = n2; i < array.length; ++i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final double[] array, final double n, int n2, final double n3) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        final double n4 = n - n3;
        final double n5 = n + n3;
        for (int i = n2; i < array.length; ++i) {
            if (array[i] >= n4 && array[i] <= n5) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final double[] array, final double n) {
        return lastIndexOf(array, n, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final double[] array, final double n, final double n2) {
        return lastIndexOf(array, n, Integer.MAX_VALUE, n2);
    }
    
    public static int lastIndexOf(final double[] array, final double n, int n2) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final double[] array, final double n, int n2, final double n3) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        final double n4 = n - n3;
        final double n5 = n + n3;
        for (int i = n2; i >= 0; --i) {
            if (array[i] >= n4 && array[i] <= n5) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final double[] array, final double n) {
        return indexOf(array, n) != -1;
    }
    
    public static boolean contains(final double[] array, final double n, final double n2) {
        return indexOf(array, n, 0, n2) != -1;
    }
    
    public static int indexOf(final float[] array, final float n) {
        return indexOf(array, n, 0);
    }
    
    public static int indexOf(final float[] array, final float n, int n2) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        for (int i = n2; i < array.length; ++i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final float[] array, final float n) {
        return lastIndexOf(array, n, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final float[] array, final float n, int n2) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= array.length) {
            n2 = array.length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (n == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final float[] array, final float n) {
        return indexOf(array, n) != -1;
    }
    
    public static int indexOf(final boolean[] array, final boolean b) {
        return indexOf(array, b, 0);
    }
    
    public static int indexOf(final boolean[] array, final boolean b, int n) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        for (int i = n; i < array.length; ++i) {
            if (b == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final boolean[] array, final boolean b) {
        return lastIndexOf(array, b, Integer.MAX_VALUE);
    }
    
    public static int lastIndexOf(final boolean[] array, final boolean b, int n) {
        if (isEmpty(array)) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= array.length) {
            n = array.length - 1;
        }
        for (int i = n; i >= 0; --i) {
            if (b == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final boolean[] array, final boolean b) {
        return indexOf(array, b) != -1;
    }
    
    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array2 = new char[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static char[] toPrimitive(final Character[] array, final char c) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array2 = new char[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Character c2 = array[i];
            array2[i] = ((c2 == null) ? c : c2);
        }
        return array2;
    }
    
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        final Character[] array2 = new Character[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] array2 = new long[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static long[] toPrimitive(final Long[] array, final long n) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] array2 = new long[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Long n2 = array[i];
            array2[i] = ((n2 == null) ? n : n2);
        }
        return array2;
    }
    
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
        }
        final Long[] array2 = new Long[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static int[] toPrimitive(final Integer[] array, final int n) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Integer n2 = array[i];
            array2[i] = ((n2 == null) ? n : n2);
        }
        return array2;
    }
    
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
        }
        final Integer[] array2 = new Integer[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static short[] toPrimitive(final Short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] array2 = new short[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static short[] toPrimitive(final Short[] array, final short n) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] array2 = new short[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Short n2 = array[i];
            array2[i] = ((n2 == null) ? n : n2);
        }
        return array2;
    }
    
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
        }
        final Short[] array2 = new Short[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static byte[] toPrimitive(final Byte[] array, final byte b) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] array2 = new byte[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Byte b2 = array[i];
            array2[i] = ((b2 == null) ? b : b2);
        }
        return array2;
    }
    
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
        }
        final Byte[] array2 = new Byte[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] array2 = new double[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static double[] toPrimitive(final Double[] array, final double n) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] array2 = new double[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Double n2 = array[i];
            array2[i] = ((n2 == null) ? n : n2);
        }
        return array2;
    }
    
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        final Double[] array2 = new Double[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] array2 = new float[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static float[] toPrimitive(final Float[] array, final float n) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] array2 = new float[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Float n2 = array[i];
            array2[i] = ((n2 == null) ? n : n2);
        }
        return array2;
    }
    
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
        }
        final Float[] array2 = new Float[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] array2 = new boolean[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static boolean[] toPrimitive(final Boolean[] array, final boolean b) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] array2 = new boolean[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Boolean b2 = array[i];
            array2[i] = ((b2 == null) ? b : b2);
        }
        return array2;
    }
    
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        final Boolean[] array2 = new Boolean[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return array2;
    }
    
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final long[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final int[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final short[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final char[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final double[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final float[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(final boolean[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isNotEmpty(final Object[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final long[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final int[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final short[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final char[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final byte[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final double[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final float[] array) {
        return array != null && array.length != 0;
    }
    
    public static boolean isNotEmpty(final boolean[] array) {
        return array != null && array.length != 0;
    }
    
    public static Object[] addAll(final Object[] array, final Object... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final Class<?> componentType = array.getClass().getComponentType();
        final Object[] array3 = (Object[])Array.newInstance(componentType, array.length + array2.length);
        System.arraycopy(array, 0, array3, 0, array.length);
        try {
            System.arraycopy(array2, 0, array3, array.length, array2.length);
        }
        catch (ArrayStoreException ex) {
            final Class<?> componentType2 = array2.getClass().getComponentType();
            if (!componentType.isAssignableFrom(componentType2)) {
                throw new IllegalArgumentException("Cannot store " + componentType2.getName() + " in an array of " + componentType.getName(), ex);
            }
            throw ex;
        }
        return array3;
    }
    
    public static boolean[] addAll(final boolean[] array, final boolean... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final boolean[] array3 = new boolean[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static char[] addAll(final char[] array, final char... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final char[] array3 = new char[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static byte[] addAll(final byte[] array, final byte... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final byte[] array3 = new byte[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static short[] addAll(final short[] array, final short... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final short[] array3 = new short[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static int[] addAll(final int[] array, final int... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final int[] array3 = new int[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static long[] addAll(final long[] array, final long... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final long[] array3 = new long[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static float[] addAll(final float[] array, final float... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final float[] array3 = new float[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static double[] addAll(final double[] array, final double... array2) {
        if (array == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array);
        }
        final double[] array3 = new double[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static Object[] add(final Object[] array, final Object o) {
        Class<?> clazz;
        if (array != null) {
            clazz = array.getClass();
        }
        else {
            if (o == null) {
                throw new IllegalArgumentException("Arguments cannot both be null");
            }
            clazz = o.getClass();
        }
        final Object[] array2 = (Object[])copyArrayGrow1(array, clazz);
        array2[array2.length - 1] = o;
        return array2;
    }
    
    public static boolean[] add(final boolean[] array, final boolean b) {
        final boolean[] array2 = (boolean[])copyArrayGrow1(array, Boolean.TYPE);
        array2[array2.length - 1] = b;
        return array2;
    }
    
    public static byte[] add(final byte[] array, final byte b) {
        final byte[] array2 = (byte[])copyArrayGrow1(array, Byte.TYPE);
        array2[array2.length - 1] = b;
        return array2;
    }
    
    public static char[] add(final char[] array, final char c) {
        final char[] array2 = (char[])copyArrayGrow1(array, Character.TYPE);
        array2[array2.length - 1] = c;
        return array2;
    }
    
    public static double[] add(final double[] array, final double n) {
        final double[] array2 = (double[])copyArrayGrow1(array, Double.TYPE);
        array2[array2.length - 1] = n;
        return array2;
    }
    
    public static float[] add(final float[] array, final float n) {
        final float[] array2 = (float[])copyArrayGrow1(array, Float.TYPE);
        array2[array2.length - 1] = n;
        return array2;
    }
    
    public static int[] add(final int[] array, final int n) {
        final int[] array2 = (int[])copyArrayGrow1(array, Integer.TYPE);
        array2[array2.length - 1] = n;
        return array2;
    }
    
    public static long[] add(final long[] array, final long n) {
        final long[] array2 = (long[])copyArrayGrow1(array, Long.TYPE);
        array2[array2.length - 1] = n;
        return array2;
    }
    
    public static short[] add(final short[] array, final short n) {
        final short[] array2 = (short[])copyArrayGrow1(array, Short.TYPE);
        array2[array2.length - 1] = n;
        return array2;
    }
    
    private static Object copyArrayGrow1(final Object o, final Class clazz) {
        if (o != null) {
            final int length = Array.getLength(o);
            final Object instance = Array.newInstance(o.getClass().getComponentType(), length + 1);
            System.arraycopy(o, 0, instance, 0, length);
            return instance;
        }
        return Array.newInstance(clazz, 1);
    }
    
    public static Object[] add(final Object[] array, final int n, final Object o) {
        Class<?> clazz;
        if (array != null) {
            clazz = array.getClass().getComponentType();
        }
        else {
            if (o == null) {
                throw new IllegalArgumentException("Array and element cannot both be null");
            }
            clazz = o.getClass();
        }
        return (Object[])add(array, n, o, clazz);
    }
    
    public static boolean[] add(final boolean[] array, final int n, final boolean b) {
        return (boolean[])add(array, n, b, Boolean.TYPE);
    }
    
    public static char[] add(final char[] array, final int n, final char c) {
        return (char[])add(array, n, c, Character.TYPE);
    }
    
    public static byte[] add(final byte[] array, final int n, final byte b) {
        return (byte[])add(array, n, b, Byte.TYPE);
    }
    
    public static short[] add(final short[] array, final int n, final short n2) {
        return (short[])add(array, n, n2, Short.TYPE);
    }
    
    public static int[] add(final int[] array, final int n, final int n2) {
        return (int[])add(array, n, n2, Integer.TYPE);
    }
    
    public static long[] add(final long[] array, final int n, final long n2) {
        return (long[])add(array, n, n2, Long.TYPE);
    }
    
    public static float[] add(final float[] array, final int n, final float n2) {
        return (float[])add(array, n, n2, Float.TYPE);
    }
    
    public static double[] add(final double[] array, final int n, final double n2) {
        return (double[])add(array, n, n2, Double.TYPE);
    }
    
    private static Object add(final Object o, final int n, final Object o2, final Class clazz) {
        if (o == null) {
            if (n != 0) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Length: 0");
            }
            final Object instance = Array.newInstance(clazz, 1);
            Array.set(instance, 0, o2);
            return instance;
        }
        else {
            final int length = Array.getLength(o);
            if (n > length || n < 0) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Length: " + length);
            }
            final Object instance2 = Array.newInstance(clazz, length + 1);
            System.arraycopy(o, 0, instance2, 0, n);
            Array.set(instance2, n, o2);
            if (n < length) {
                System.arraycopy(o, n, instance2, n + 1, length - n);
            }
            return instance2;
        }
    }
    
    public static Object[] remove(final Object[] array, final int n) {
        return (Object[])remove((Object)array, n);
    }
    
    public static Object[] removeElement(final Object[] array, final Object o) {
        final int index = indexOf(array, o);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static boolean[] remove(final boolean[] array, final int n) {
        return (boolean[])remove((Object)array, n);
    }
    
    public static boolean[] removeElement(final boolean[] array, final boolean b) {
        final int index = indexOf(array, b);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static byte[] remove(final byte[] array, final int n) {
        return (byte[])remove((Object)array, n);
    }
    
    public static byte[] removeElement(final byte[] array, final byte b) {
        final int index = indexOf(array, b);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static char[] remove(final char[] array, final int n) {
        return (char[])remove((Object)array, n);
    }
    
    public static char[] removeElement(final char[] array, final char c) {
        final int index = indexOf(array, c);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static double[] remove(final double[] array, final int n) {
        return (double[])remove((Object)array, n);
    }
    
    public static double[] removeElement(final double[] array, final double n) {
        final int index = indexOf(array, n);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static float[] remove(final float[] array, final int n) {
        return (float[])remove((Object)array, n);
    }
    
    public static float[] removeElement(final float[] array, final float n) {
        final int index = indexOf(array, n);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static int[] remove(final int[] array, final int n) {
        return (int[])remove((Object)array, n);
    }
    
    public static int[] removeElement(final int[] array, final int n) {
        final int index = indexOf(array, n);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static long[] remove(final long[] array, final int n) {
        return (long[])remove((Object)array, n);
    }
    
    public static long[] removeElement(final long[] array, final long n) {
        final int index = indexOf(array, n);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    public static short[] remove(final short[] array, final int n) {
        return (short[])remove((Object)array, n);
    }
    
    public static short[] removeElement(final short[] array, final short n) {
        final int index = indexOf(array, n);
        if (index == -1) {
            return clone(array);
        }
        return remove(array, index);
    }
    
    private static Object remove(final Object o, final int n) {
        final int length = getLength(o);
        if (n < 0 || n >= length) {
            throw new IndexOutOfBoundsException("Index: " + n + ", Length: " + length);
        }
        final Object instance = Array.newInstance(o.getClass().getComponentType(), length - 1);
        System.arraycopy(o, 0, instance, 0, n);
        if (n < length - 1) {
            System.arraycopy(o, n + 1, instance, n, length - n - 1);
        }
        return instance;
    }
    
    public static Object[] removeAll(final Object[] array, final int... array2) {
        return (Object[])removeAll((Object)array, clone(array2));
    }
    
    public static Object[] removeElements(final Object[] array, final Object... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Object, MutableInt> hashMap = new HashMap<Object, MutableInt>(array2.length);
        for (final Object o : array2) {
            final MutableInt mutableInt = hashMap.get(o);
            if (mutableInt == null) {
                hashMap.put(o, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Object, MutableInt> entry : hashMap.entrySet()) {
            final Object key = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, key, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (Object[])removeAll((Object)array, set);
    }
    
    public static byte[] removeAll(final byte[] array, final int... array2) {
        return (byte[])removeAll((Object)array, clone(array2));
    }
    
    public static byte[] removeElements(final byte[] array, final byte... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Byte, MutableInt> hashMap = new HashMap<Byte, MutableInt>(array2.length);
        for (final Byte value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Byte, MutableInt> entry : hashMap.entrySet()) {
            final Byte b = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, b, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (byte[])removeAll((Object)array, set);
    }
    
    public static short[] removeAll(final short[] array, final int... array2) {
        return (short[])removeAll((Object)array, clone(array2));
    }
    
    public static short[] removeElements(final short[] array, final short... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Short, MutableInt> hashMap = new HashMap<Short, MutableInt>(array2.length);
        for (final Short value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Short, MutableInt> entry : hashMap.entrySet()) {
            final Short n = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, n, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (short[])removeAll((Object)array, set);
    }
    
    public static int[] removeAll(final int[] array, final int... array2) {
        return (int[])removeAll((Object)array, clone(array2));
    }
    
    public static int[] removeElements(final int[] array, final int... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Integer, MutableInt> hashMap = new HashMap<Integer, MutableInt>(array2.length);
        for (final Integer value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Integer, MutableInt> entry : hashMap.entrySet()) {
            final Integer n = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, n, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (int[])removeAll((Object)array, set);
    }
    
    public static char[] removeAll(final char[] array, final int... array2) {
        return (char[])removeAll((Object)array, clone(array2));
    }
    
    public static char[] removeElements(final char[] array, final char... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Character, MutableInt> hashMap = new HashMap<Character, MutableInt>(array2.length);
        for (final Character value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Character, MutableInt> entry : hashMap.entrySet()) {
            final Character c = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, c, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (char[])removeAll((Object)array, set);
    }
    
    public static long[] removeAll(final long[] array, final int... array2) {
        return (long[])removeAll((Object)array, clone(array2));
    }
    
    public static long[] removeElements(final long[] array, final long... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Long, MutableInt> hashMap = new HashMap<Long, MutableInt>(array2.length);
        for (final Long value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Long, MutableInt> entry : hashMap.entrySet()) {
            final Long n = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, n, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (long[])removeAll((Object)array, set);
    }
    
    public static float[] removeAll(final float[] array, final int... array2) {
        return (float[])removeAll((Object)array, clone(array2));
    }
    
    public static float[] removeElements(final float[] array, final float... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Float, MutableInt> hashMap = new HashMap<Float, MutableInt>(array2.length);
        for (final Float value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Float, MutableInt> entry : hashMap.entrySet()) {
            final Float n = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, n, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (float[])removeAll((Object)array, set);
    }
    
    public static double[] removeAll(final double[] array, final int... array2) {
        return (double[])removeAll((Object)array, clone(array2));
    }
    
    public static double[] removeElements(final double[] array, final double... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Double, MutableInt> hashMap = new HashMap<Double, MutableInt>(array2.length);
        for (final Double value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Double, MutableInt> entry : hashMap.entrySet()) {
            final Double n = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, n, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (double[])removeAll((Object)array, set);
    }
    
    public static boolean[] removeAll(final boolean[] array, final int... array2) {
        return (boolean[])removeAll((Object)array, clone(array2));
    }
    
    public static boolean[] removeElements(final boolean[] array, final boolean... array2) {
        if (isEmpty(array) || isEmpty(array2)) {
            return clone(array);
        }
        final HashMap<Boolean, MutableInt> hashMap = new HashMap<Boolean, MutableInt>(2);
        for (final Boolean value : array2) {
            final MutableInt mutableInt = hashMap.get(value);
            if (mutableInt == null) {
                hashMap.put(value, new MutableInt(1));
            }
            else {
                mutableInt.increment();
            }
        }
        final BitSet set = new BitSet();
        for (final Map.Entry<Boolean, MutableInt> entry : hashMap.entrySet()) {
            final Boolean b = entry.getKey();
            int index = 0;
            for (int j = 0; j < entry.getValue().intValue(); ++j) {
                index = indexOf(array, b, index);
                if (index < 0) {
                    break;
                }
                set.set(index++);
            }
        }
        return (boolean[])removeAll((Object)array, set);
    }
    
    static Object removeAll(final Object o, final int... array) {
        final int length = getLength(o);
        int n = 0;
        if (isNotEmpty(array)) {
            Arrays.sort(array);
            int length2 = array.length;
            int n2 = length;
            while (--length2 >= 0) {
                final int n3 = array[length2];
                if (n3 < 0 || n3 >= length) {
                    throw new IndexOutOfBoundsException("Index: " + n3 + ", Length: " + length);
                }
                if (n3 >= n2) {
                    continue;
                }
                ++n;
                n2 = n3;
            }
        }
        final Object instance = Array.newInstance(o.getClass().getComponentType(), length - n);
        if (n < length) {
            int n4 = length;
            int n5 = length - n;
            for (int i = array.length - 1; i >= 0; --i) {
                final int n6 = array[i];
                if (n4 - n6 > 1) {
                    final int n7 = n4 - n6 - 1;
                    n5 -= n7;
                    System.arraycopy(o, n6 + 1, instance, n5, n7);
                }
                n4 = n6;
            }
            if (n4 > 0) {
                System.arraycopy(o, 0, instance, 0, n4);
            }
        }
        return instance;
    }
    
    static Object removeAll(final Object o, final BitSet set) {
        final int length = getLength(o);
        final Object instance = Array.newInstance(o.getClass().getComponentType(), length - set.cardinality());
        int nextClearBit = 0;
        int n = 0;
        int nextSetBit;
        while ((nextSetBit = set.nextSetBit(nextClearBit)) != -1) {
            final int n2 = nextSetBit - nextClearBit;
            if (n2 > 0) {
                System.arraycopy(o, nextClearBit, instance, n, n2);
                n += n2;
            }
            nextClearBit = set.nextClearBit(nextSetBit);
        }
        final int n3 = length - nextClearBit;
        if (n3 > 0) {
            System.arraycopy(o, nextClearBit, instance, n, n3);
        }
        return instance;
    }
    
    static {
        EMPTY_OBJECT_ARRAY = new Object[0];
        EMPTY_CLASS_ARRAY = new Class[0];
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_LONG_OBJECT_ARRAY = new Long[0];
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
        EMPTY_SHORT_ARRAY = new short[0];
        EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
        EMPTY_DOUBLE_ARRAY = new double[0];
        EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
        EMPTY_FLOAT_ARRAY = new float[0];
        EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
        EMPTY_BOOLEAN_ARRAY = new boolean[0];
        EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
        EMPTY_CHAR_ARRAY = new char[0];
        EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
    }
}
