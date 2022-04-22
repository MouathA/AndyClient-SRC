package com.google.common.collect;

import java.lang.reflect.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class ObjectArrays
{
    static final Object[] EMPTY_ARRAY;
    
    private ObjectArrays() {
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static Object[] newArray(final Class clazz, final int n) {
        return (Object[])Array.newInstance(clazz, n);
    }
    
    public static Object[] newArray(final Object[] array, final int n) {
        return Platform.newArray(array, n);
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static Object[] concat(final Object[] array, final Object[] array2, final Class clazz) {
        final Object[] array3 = newArray(clazz, array.length + array2.length);
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static Object[] concat(@Nullable final Object o, final Object[] array) {
        final Object[] array2 = newArray(array, array.length + 1);
        array2[0] = o;
        System.arraycopy(array, 0, array2, 1, array.length);
        return array2;
    }
    
    public static Object[] concat(final Object[] array, @Nullable final Object o) {
        final Object[] arraysCopy = arraysCopyOf(array, array.length + 1);
        arraysCopy[array.length] = o;
        return arraysCopy;
    }
    
    static Object[] arraysCopyOf(final Object[] array, final int n) {
        final Object[] array2 = newArray(array, n);
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    static Object[] toArrayImpl(final Collection collection, Object[] array) {
        final int size = collection.size();
        if (array.length < size) {
            array = newArray(array, size);
        }
        fillArray(collection, array);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }
    
    static Object[] toArrayImpl(final Object[] array, final int n, final int n2, Object[] array2) {
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        if (array2.length < n2) {
            array2 = newArray(array2, n2);
        }
        else if (array2.length > n2) {
            array2[n2] = null;
        }
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    static Object[] toArrayImpl(final Collection collection) {
        return fillArray(collection, new Object[collection.size()]);
    }
    
    static Object[] copyAsObjectArray(final Object[] array, final int n, final int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        if (n2 == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        final Object[] array2 = new Object[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    private static Object[] fillArray(final Iterable iterable, final Object[] array) {
        for (final Object next : iterable) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            array[n] = next;
        }
        return array;
    }
    
    static void swap(final Object[] array, final int n, final int n2) {
        final Object o = array[n];
        array[n] = array[n2];
        array[n2] = o;
    }
    
    static Object[] checkElementsNotNull(final Object... array) {
        return checkElementsNotNull(array, array.length);
    }
    
    static Object[] checkElementsNotNull(final Object[] array, final int n) {
        while (0 < n) {
            checkElementNotNull(array[0], 0);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    static Object checkElementNotNull(final Object o, final int n) {
        if (o == null) {
            throw new NullPointerException("at index " + n);
        }
        return o;
    }
    
    static {
        EMPTY_ARRAY = new Object[0];
    }
}
