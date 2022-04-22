package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.*;

public class InheritanceUtils
{
    public static int distance(final Class clazz, final Class clazz2) {
        if (clazz == null || clazz2 == null) {
            return -1;
        }
        if (clazz.equals(clazz2)) {
            return 0;
        }
        final Class superclass = clazz.getSuperclass();
        final int integer = BooleanUtils.toInteger(clazz2.equals(superclass));
        if (integer == 1) {
            return integer;
        }
        final int n = integer + distance(superclass, clazz2);
        return (n > 0) ? (n + 1) : -1;
    }
}
