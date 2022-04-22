package org.apache.commons.lang3;

import java.util.*;
import java.util.regex.*;

public class Validate
{
    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
    
    public static void isTrue(final boolean b, final String s, final long n) {
        if (!b) {
            throw new IllegalArgumentException(String.format(s, n));
        }
    }
    
    public static void isTrue(final boolean b, final String s, final double n) {
        if (!b) {
            throw new IllegalArgumentException(String.format(s, n));
        }
    }
    
    public static void isTrue(final boolean b, final String s, final Object... array) {
        if (!b) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
    
    public static void isTrue(final boolean b) {
        if (!b) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }
    
    public static Object notNull(final Object o) {
        return notNull(o, "The validated object is null", new Object[0]);
    }
    
    public static Object notNull(final Object o, final String s, final Object... array) {
        if (o == null) {
            throw new NullPointerException(String.format(s, array));
        }
        return o;
    }
    
    public static Object[] notEmpty(final Object[] array, final String s, final Object... array2) {
        if (array == null) {
            throw new NullPointerException(String.format(s, array2));
        }
        if (array.length == 0) {
            throw new IllegalArgumentException(String.format(s, array2));
        }
        return array;
    }
    
    public static Object[] notEmpty(final Object[] array) {
        return notEmpty(array, "The validated array is empty", new Object[0]);
    }
    
    public static Collection notEmpty(final Collection collection, final String s, final Object... array) {
        if (collection == null) {
            throw new NullPointerException(String.format(s, array));
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(s, array));
        }
        return collection;
    }
    
    public static Collection notEmpty(final Collection collection) {
        return notEmpty(collection, "The validated collection is empty", new Object[0]);
    }
    
    public static Map notEmpty(final Map map, final String s, final Object... array) {
        if (map == null) {
            throw new NullPointerException(String.format(s, array));
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(String.format(s, array));
        }
        return map;
    }
    
    public static Map notEmpty(final Map map) {
        return notEmpty(map, "The validated map is empty", new Object[0]);
    }
    
    public static CharSequence notEmpty(final CharSequence charSequence, final String s, final Object... array) {
        if (charSequence == null) {
            throw new NullPointerException(String.format(s, array));
        }
        if (charSequence.length() == 0) {
            throw new IllegalArgumentException(String.format(s, array));
        }
        return charSequence;
    }
    
    public static CharSequence notEmpty(final CharSequence charSequence) {
        return notEmpty(charSequence, "The validated character sequence is empty", new Object[0]);
    }
    
    public static CharSequence notBlank(final CharSequence charSequence, final String s, final Object... array) {
        if (charSequence == null) {
            throw new NullPointerException(String.format(s, array));
        }
        if (StringUtils.isBlank(charSequence)) {
            throw new IllegalArgumentException(String.format(s, array));
        }
        return charSequence;
    }
    
    public static CharSequence notBlank(final CharSequence charSequence) {
        return notBlank(charSequence, "The validated character sequence is blank", new Object[0]);
    }
    
    public static Object[] noNullElements(final Object[] array, final String s, final Object... array2) {
        notNull(array);
        while (0 < array.length) {
            if (array[0] == null) {
                throw new IllegalArgumentException(String.format(s, ArrayUtils.add(array2, 0)));
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public static Object[] noNullElements(final Object[] array) {
        return noNullElements(array, "The validated array contains null element at index: %d", new Object[0]);
    }
    
    public static Iterable noNullElements(final Iterable iterable, final String s, final Object... array) {
        notNull(iterable);
        final Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                throw new IllegalArgumentException(String.format(s, ArrayUtils.addAll(array, 0)));
            }
            int n = 0;
            ++n;
        }
        return iterable;
    }
    
    public static Iterable noNullElements(final Iterable iterable) {
        return noNullElements(iterable, "The validated collection contains null element at index: %d", new Object[0]);
    }
    
    public static Object[] validIndex(final Object[] array, final int n, final String s, final Object... array2) {
        notNull(array);
        if (n < 0 || n >= array.length) {
            throw new IndexOutOfBoundsException(String.format(s, array2));
        }
        return array;
    }
    
    public static Object[] validIndex(final Object[] array, final int n) {
        return validIndex(array, n, "The validated array index is invalid: %d", n);
    }
    
    public static Collection validIndex(final Collection collection, final int n, final String s, final Object... array) {
        notNull(collection);
        if (n < 0 || n >= collection.size()) {
            throw new IndexOutOfBoundsException(String.format(s, array));
        }
        return collection;
    }
    
    public static Collection validIndex(final Collection collection, final int n) {
        return validIndex(collection, n, "The validated collection index is invalid: %d", n);
    }
    
    public static CharSequence validIndex(final CharSequence charSequence, final int n, final String s, final Object... array) {
        notNull(charSequence);
        if (n < 0 || n >= charSequence.length()) {
            throw new IndexOutOfBoundsException(String.format(s, array));
        }
        return charSequence;
    }
    
    public static CharSequence validIndex(final CharSequence charSequence, final int n) {
        return validIndex(charSequence, n, "The validated character sequence index is invalid: %d", n);
    }
    
    public static void validState(final boolean b) {
        if (!b) {
            throw new IllegalStateException("The validated state is false");
        }
    }
    
    public static void validState(final boolean b, final String s, final Object... array) {
        if (!b) {
            throw new IllegalStateException(String.format(s, array));
        }
    }
    
    public static void matchesPattern(final CharSequence charSequence, final String s) {
        if (!Pattern.matches(s, charSequence)) {
            throw new IllegalArgumentException(String.format("The string %s does not match the pattern %s", charSequence, s));
        }
    }
    
    public static void matchesPattern(final CharSequence charSequence, final String s, final String s2, final Object... array) {
        if (!Pattern.matches(s, charSequence)) {
            throw new IllegalArgumentException(String.format(s2, array));
        }
    }
    
    public static void inclusiveBetween(final Object o, final Object o2, final Comparable comparable) {
        if (comparable.compareTo(o) < 0 || comparable.compareTo(o2) > 0) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", comparable, o, o2));
        }
    }
    
    public static void inclusiveBetween(final Object o, final Object o2, final Comparable comparable, final String s, final Object... array) {
        if (comparable.compareTo(o) < 0 || comparable.compareTo(o2) > 0) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
    
    public static void inclusiveBetween(final long n, final long n2, final long n3) {
        if (n3 < n || n3 > n2) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", n3, n, n2));
        }
    }
    
    public static void inclusiveBetween(final long n, final long n2, final long n3, final String s) {
        if (n3 < n || n3 > n2) {
            throw new IllegalArgumentException(String.format(s, new Object[0]));
        }
    }
    
    public static void inclusiveBetween(final double n, final double n2, final double n3) {
        if (n3 < n || n3 > n2) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", n3, n, n2));
        }
    }
    
    public static void inclusiveBetween(final double n, final double n2, final double n3, final String s) {
        if (n3 < n || n3 > n2) {
            throw new IllegalArgumentException(String.format(s, new Object[0]));
        }
    }
    
    public static void exclusiveBetween(final Object o, final Object o2, final Comparable comparable) {
        if (comparable.compareTo(o) <= 0 || comparable.compareTo(o2) >= 0) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", comparable, o, o2));
        }
    }
    
    public static void exclusiveBetween(final Object o, final Object o2, final Comparable comparable, final String s, final Object... array) {
        if (comparable.compareTo(o) <= 0 || comparable.compareTo(o2) >= 0) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
    
    public static void exclusiveBetween(final long n, final long n2, final long n3) {
        if (n3 <= n || n3 >= n2) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", n3, n, n2));
        }
    }
    
    public static void exclusiveBetween(final long n, final long n2, final long n3, final String s) {
        if (n3 <= n || n3 >= n2) {
            throw new IllegalArgumentException(String.format(s, new Object[0]));
        }
    }
    
    public static void exclusiveBetween(final double n, final double n2, final double n3) {
        if (n3 <= n || n3 >= n2) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", n3, n, n2));
        }
    }
    
    public static void exclusiveBetween(final double n, final double n2, final double n3, final String s) {
        if (n3 <= n || n3 >= n2) {
            throw new IllegalArgumentException(String.format(s, new Object[0]));
        }
    }
    
    public static void isInstanceOf(final Class clazz, final Object o) {
        if (!clazz.isInstance(o)) {
            throw new IllegalArgumentException(String.format("Expected type: %s, actual: %s", clazz.getName(), (o == null) ? "null" : o.getClass().getName()));
        }
    }
    
    public static void isInstanceOf(final Class clazz, final Object o, final String s, final Object... array) {
        if (!clazz.isInstance(o)) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
    
    public static void isAssignableFrom(final Class clazz, final Class clazz2) {
        if (!clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException(String.format("Cannot assign a %s to a %s", (clazz2 == null) ? "null" : clazz2.getName(), clazz.getName()));
        }
    }
    
    public static void isAssignableFrom(final Class clazz, final Class clazz2, final String s, final Object... array) {
        if (!clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
}
