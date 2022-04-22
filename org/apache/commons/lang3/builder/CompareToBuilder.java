package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.*;
import java.lang.reflect.*;
import java.util.*;

public class CompareToBuilder implements Builder
{
    private int comparison;
    
    public CompareToBuilder() {
        this.comparison = 0;
    }
    
    public static int reflectionCompare(final Object o, final Object o2) {
        return reflectionCompare(o, o2, false, null, new String[0]);
    }
    
    public static int reflectionCompare(final Object o, final Object o2, final boolean b) {
        return reflectionCompare(o, o2, b, null, new String[0]);
    }
    
    public static int reflectionCompare(final Object o, final Object o2, final Collection collection) {
        return reflectionCompare(o, o2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }
    
    public static int reflectionCompare(final Object o, final Object o2, final String... array) {
        return reflectionCompare(o, o2, false, null, array);
    }
    
    public static int reflectionCompare(final Object o, final Object o2, final boolean b, final Class clazz, final String... array) {
        if (o == o2) {
            return 0;
        }
        if (o == null || o2 == null) {
            throw new NullPointerException();
        }
        Class<?> clazz2 = o.getClass();
        if (!clazz2.isInstance(o2)) {
            throw new ClassCastException();
        }
        final CompareToBuilder compareToBuilder = new CompareToBuilder();
        reflectionAppend(o, o2, clazz2, compareToBuilder, b, array);
        while (clazz2.getSuperclass() != null && clazz2 != clazz) {
            clazz2 = clazz2.getSuperclass();
            reflectionAppend(o, o2, clazz2, compareToBuilder, b, array);
        }
        return compareToBuilder.toComparison();
    }
    
    private static void reflectionAppend(final Object o, final Object o2, final Class clazz, final CompareToBuilder compareToBuilder, final boolean b, final String[] array) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        while (0 < declaredFields.length && compareToBuilder.comparison == 0) {
            final Field field = declaredFields[0];
            if (!ArrayUtils.contains(array, field.getName()) && field.getName().indexOf(36) == -1 && (b || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers())) {
                compareToBuilder.append(field.get(o), field.get(o2));
            }
            int n = 0;
            ++n;
        }
    }
    
    public CompareToBuilder appendSuper(final int comparison) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = comparison;
        return this;
    }
    
    public CompareToBuilder append(final Object o, final Object o2) {
        return this.append(o, o2, null);
    }
    
    public CompareToBuilder append(final Object o, final Object o2, final Comparator comparator) {
        if (this.comparison != 0) {
            return this;
        }
        if (o == o2) {
            return this;
        }
        if (o == null) {
            this.comparison = -1;
            return this;
        }
        if (o2 == null) {
            this.comparison = 1;
            return this;
        }
        if (o.getClass().isArray()) {
            if (o instanceof long[]) {
                this.append((long[])o, (long[])o2);
            }
            else if (o instanceof int[]) {
                this.append((int[])o, (int[])o2);
            }
            else if (o instanceof short[]) {
                this.append((short[])o, (short[])o2);
            }
            else if (o instanceof char[]) {
                this.append((char[])o, (char[])o2);
            }
            else if (o instanceof byte[]) {
                this.append((byte[])o, (byte[])o2);
            }
            else if (o instanceof double[]) {
                this.append((double[])o, (double[])o2);
            }
            else if (o instanceof float[]) {
                this.append((float[])o, (float[])o2);
            }
            else if (o instanceof boolean[]) {
                this.append((boolean[])o, (boolean[])o2);
            }
            else {
                this.append((Object[])o, (Object[])o2, comparator);
            }
        }
        else if (comparator == null) {
            this.comparison = ((Comparable)o).compareTo(o2);
        }
        else {
            this.comparison = comparator.compare(o, o2);
        }
        return this;
    }
    
    public CompareToBuilder append(final long n, final long n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = ((n < n2) ? -1 : ((n > n2) ? 1 : 0));
        return this;
    }
    
    public CompareToBuilder append(final int n, final int n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = ((n < n2) ? -1 : ((n > n2) ? 1 : 0));
        return this;
    }
    
    public CompareToBuilder append(final short n, final short n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = ((n < n2) ? -1 : ((n > n2) ? 1 : 0));
        return this;
    }
    
    public CompareToBuilder append(final char c, final char c2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = ((c < c2) ? -1 : ((c > c2) ? 1 : 0));
        return this;
    }
    
    public CompareToBuilder append(final byte b, final byte b2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = ((b < b2) ? -1 : ((b > b2) ? 1 : 0));
        return this;
    }
    
    public CompareToBuilder append(final double n, final double n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = Double.compare(n, n2);
        return this;
    }
    
    public CompareToBuilder append(final float n, final float n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = Float.compare(n, n2);
        return this;
    }
    
    public CompareToBuilder append(final boolean b, final boolean b2) {
        if (this.comparison != 0) {
            return this;
        }
        if (b == b2) {
            return this;
        }
        if (!b) {
            this.comparison = -1;
        }
        else {
            this.comparison = 1;
        }
        return this;
    }
    
    public CompareToBuilder append(final Object[] array, final Object[] array2) {
        return this.append(array, array2, null);
    }
    
    public CompareToBuilder append(final Object[] array, final Object[] array2, final Comparator comparator) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0], comparator);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final long[] array, final long[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final int[] array, final int[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final short[] array, final short[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final char[] array, final char[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final byte[] array, final byte[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final double[] array, final double[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final float[] array, final float[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public CompareToBuilder append(final boolean[] array, final boolean[] array2) {
        if (this.comparison != 0) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null) {
            this.comparison = -1;
            return this;
        }
        if (array2 == null) {
            this.comparison = 1;
            return this;
        }
        if (array.length != array2.length) {
            this.comparison = ((array.length < array2.length) ? -1 : 1);
            return this;
        }
        while (0 < array.length && this.comparison == 0) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public int toComparison() {
        return this.comparison;
    }
    
    @Override
    public Integer build() {
        return this.toComparison();
    }
    
    @Override
    public Object build() {
        return this.build();
    }
}
