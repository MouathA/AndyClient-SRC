package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.tuple.*;
import java.util.*;
import org.apache.commons.lang3.*;
import java.lang.reflect.*;

public class EqualsBuilder implements Builder
{
    private static final ThreadLocal REGISTRY;
    private boolean isEquals;
    
    static Set getRegistry() {
        return EqualsBuilder.REGISTRY.get();
    }
    
    static Pair getRegisterPair(final Object o, final Object o2) {
        return Pair.of(new IDKey(o), new IDKey(o2));
    }
    
    static void register(final Object o, final Object o2) {
        final Class<EqualsBuilder> clazz = EqualsBuilder.class;
        final Class<EqualsBuilder> clazz2 = EqualsBuilder.class;
        // monitorenter(clazz)
        if (getRegistry() == null) {
            EqualsBuilder.REGISTRY.set(new HashSet());
        }
        // monitorexit(clazz2)
        getRegistry().add(getRegisterPair(o, o2));
    }
    
    static void unregister(final Object o, final Object o2) {
        final Set registry = getRegistry();
        if (registry != null) {
            registry.remove(getRegisterPair(o, o2));
            final Class<EqualsBuilder> clazz = EqualsBuilder.class;
            final Class<EqualsBuilder> clazz2 = EqualsBuilder.class;
            // monitorenter(clazz)
            final Set registry2 = getRegistry();
            if (registry2 != null && registry2.isEmpty()) {
                EqualsBuilder.REGISTRY.remove();
            }
        }
        // monitorexit(clazz2)
    }
    
    public EqualsBuilder() {
        this.isEquals = true;
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final Collection collection) {
        return reflectionEquals(o, o2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final String... array) {
        return reflectionEquals(o, o2, false, null, array);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final boolean b) {
        return reflectionEquals(o, o2, b, null, new String[0]);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final boolean b, final Class clazz, final String... array) {
        if (o == o2) {
            return true;
        }
        if (o == null || o2 == null) {
            return false;
        }
        final Class<?> class1 = o.getClass();
        final Class<?> class2 = o2.getClass();
        Class<?> superclass;
        if (class1.isInstance(o2)) {
            superclass = class1;
            if (!class2.isInstance(o)) {
                superclass = class2;
            }
        }
        else {
            if (!class2.isInstance(o)) {
                return false;
            }
            superclass = class2;
            if (!class1.isInstance(o2)) {
                superclass = class1;
            }
        }
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        if (superclass.isArray()) {
            equalsBuilder.append(o, o2);
        }
        else {
            reflectionAppend(o, o2, superclass, equalsBuilder, b, array);
            while (superclass.getSuperclass() != null && superclass != clazz) {
                superclass = superclass.getSuperclass();
                reflectionAppend(o, o2, superclass, equalsBuilder, b, array);
            }
        }
        return equalsBuilder.isEquals();
    }
    
    private static void reflectionAppend(final Object o, final Object o2, final Class clazz, final EqualsBuilder equalsBuilder, final boolean b, final String[] array) {
        if (o2 != null) {
            return;
        }
        register(o, o2);
        final Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        while (0 < declaredFields.length && equalsBuilder.isEquals) {
            final Field field = declaredFields[0];
            if (!ArrayUtils.contains(array, field.getName()) && field.getName().indexOf(36) == -1 && (b || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers())) {
                equalsBuilder.append(field.get(o), field.get(o2));
            }
            int n = 0;
            ++n;
        }
        unregister(o, o2);
    }
    
    public EqualsBuilder appendSuper(final boolean isEquals) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = isEquals;
        return this;
    }
    
    public EqualsBuilder append(final Object o, final Object o2) {
        if (!this.isEquals) {
            return this;
        }
        if (o == o2) {
            return this;
        }
        if (o == null || o2 == null) {
            this.setEquals(false);
            return this;
        }
        if (!o.getClass().isArray()) {
            this.isEquals = o.equals(o2);
        }
        else if (o.getClass() != o2.getClass()) {
            this.setEquals(false);
        }
        else if (o instanceof long[]) {
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
            this.append((Object[])o, (Object[])o2);
        }
        return this;
    }
    
    public EqualsBuilder append(final long n, final long n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final int n, final int n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final short n, final short n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final char c, final char c2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (c == c2);
        return this;
    }
    
    public EqualsBuilder append(final byte b, final byte b2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (b == b2);
        return this;
    }
    
    public EqualsBuilder append(final double n, final double n2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Double.doubleToLongBits(n), Double.doubleToLongBits(n2));
    }
    
    public EqualsBuilder append(final float n, final float n2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Float.floatToIntBits(n), Float.floatToIntBits(n2));
    }
    
    public EqualsBuilder append(final boolean b, final boolean b2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (b == b2);
        return this;
    }
    
    public EqualsBuilder append(final Object[] array, final Object[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final long[] array, final long[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final int[] array, final int[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final short[] array, final short[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final char[] array, final char[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final byte[] array, final byte[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final double[] array, final double[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final float[] array, final float[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public EqualsBuilder append(final boolean[] array, final boolean[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        while (0 < array.length && this.isEquals) {
            this.append(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public boolean isEquals() {
        return this.isEquals;
    }
    
    @Override
    public Boolean build() {
        return this.isEquals();
    }
    
    protected void setEquals(final boolean isEquals) {
        this.isEquals = isEquals;
    }
    
    public void reset() {
        this.isEquals = true;
    }
    
    @Override
    public Object build() {
        return this.build();
    }
    
    static {
        REGISTRY = new ThreadLocal();
    }
}
