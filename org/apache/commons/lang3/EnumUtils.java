package org.apache.commons.lang3;

import java.util.*;

public class EnumUtils
{
    private static final String NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted";
    private static final String CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits";
    private static final String S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type";
    private static final String ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined.";
    
    public static Map getEnumMap(final Class clazz) {
        final LinkedHashMap<String, Enum> linkedHashMap = new LinkedHashMap<String, Enum>();
        final Enum[] array = clazz.getEnumConstants();
        while (0 < array.length) {
            final Enum enum1 = array[0];
            linkedHashMap.put(enum1.name(), enum1);
            int n = 0;
            ++n;
        }
        return linkedHashMap;
    }
    
    public static List getEnumList(final Class clazz) {
        return new ArrayList(Arrays.asList((Object[])clazz.getEnumConstants()));
    }
    
    public static boolean isValidEnum(final Class clazz, final String s) {
        if (s == null) {
            return false;
        }
        Enum.valueOf((Class<Enum>)clazz, s);
        return true;
    }
    
    public static Enum getEnum(final Class clazz, final String s) {
        if (s == null) {
            return null;
        }
        return Enum.valueOf((Class<Enum>)clazz, s);
    }
    
    public static long generateBitVector(final Class clazz, final Iterable iterable) {
        checkBitVectorable(clazz);
        Validate.notNull(iterable);
        long n = 0L;
        for (final Enum enum1 : iterable) {
            Validate.isTrue(enum1 != null, "null elements not permitted", new Object[0]);
            n |= 1 << enum1.ordinal();
        }
        return n;
    }
    
    public static long[] generateBitVectors(final Class clazz, final Iterable iterable) {
        asEnum(clazz);
        Validate.notNull(iterable);
        final EnumSet<Enum> none = EnumSet.noneOf((Class<Enum>)clazz);
        for (final Enum enum1 : iterable) {
            Validate.isTrue(enum1 != null, "null elements not permitted", new Object[0]);
            none.add(enum1);
        }
        final long[] array = new long[(clazz.getEnumConstants().length - 1) / 64 + 1];
        for (final Enum enum2 : none) {
            final long[] array2 = array;
            final int n = enum2.ordinal() / 64;
            array2[n] |= 1 << enum2.ordinal() % 64;
        }
        ArrayUtils.reverse(array);
        return array;
    }
    
    public static long generateBitVector(final Class clazz, final Enum... array) {
        Validate.noNullElements(array);
        return generateBitVector(clazz, Arrays.asList((Enum[])array));
    }
    
    public static long[] generateBitVectors(final Class clazz, final Enum... array) {
        asEnum(clazz);
        Validate.noNullElements(array);
        final EnumSet<Enum> none = EnumSet.noneOf((Class<Enum>)clazz);
        Collections.addAll(none, (Enum[])array);
        final long[] array2 = new long[(clazz.getEnumConstants().length - 1) / 64 + 1];
        for (final Enum enum1 : none) {
            final long[] array3 = array2;
            final int n = enum1.ordinal() / 64;
            array3[n] |= 1 << enum1.ordinal() % 64;
        }
        ArrayUtils.reverse(array2);
        return array2;
    }
    
    public static EnumSet processBitVector(final Class clazz, final long n) {
        checkBitVectorable(clazz).getEnumConstants();
        return processBitVectors(clazz, n);
    }
    
    public static EnumSet processBitVectors(final Class clazz, final long... array) {
        final EnumSet<Enum> none = EnumSet.noneOf((Class<Enum>)asEnum(clazz));
        final long[] clone = ArrayUtils.clone((long[])Validate.notNull(array));
        ArrayUtils.reverse(clone);
        final Enum[] array2 = clazz.getEnumConstants();
        while (0 < array2.length) {
            final Enum enum1 = array2[0];
            final int n = enum1.ordinal() / 64;
            if (n < clone.length && (clone[n] & (long)(1 << enum1.ordinal() % 64)) != 0x0L) {
                none.add(enum1);
            }
            int n2 = 0;
            ++n2;
        }
        return none;
    }
    
    private static Class checkBitVectorable(final Class clazz) {
        final Enum[] array = asEnum(clazz).getEnumConstants();
        Validate.isTrue(array.length <= 64, "Cannot store %s %s values in %s bits", array.length, clazz.getSimpleName(), 64);
        return clazz;
    }
    
    private static Class asEnum(final Class clazz) {
        Validate.notNull(clazz, "EnumClass must be defined.", new Object[0]);
        Validate.isTrue(clazz.isEnum(), "%s does not seem to be an Enum type", clazz);
        return clazz;
    }
}
