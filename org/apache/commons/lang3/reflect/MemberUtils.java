package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import org.apache.commons.lang3.*;

abstract class MemberUtils
{
    private static final int ACCESS_TEST = 7;
    private static final Class[] ORDERED_PRIMITIVE_TYPES;
    
    static boolean setAccessibleWorkaround(final AccessibleObject accessibleObject) {
        if (accessibleObject == null || accessibleObject.isAccessible()) {
            return false;
        }
        final Member member = (Member)accessibleObject;
        if (!accessibleObject.isAccessible() && Modifier.isPublic(member.getModifiers()) && isPackageAccess(member.getDeclaringClass().getModifiers())) {
            accessibleObject.setAccessible(true);
            return true;
        }
        return false;
    }
    
    static boolean isPackageAccess(final int n) {
        return (n & 0x7) == 0x0;
    }
    
    static boolean isAccessible(final Member member) {
        return member != null && Modifier.isPublic(member.getModifiers()) && !member.isSynthetic();
    }
    
    static int compareParameterTypes(final Class[] array, final Class[] array2, final Class[] array3) {
        final float totalTransformationCost = getTotalTransformationCost(array3, array);
        final float totalTransformationCost2 = getTotalTransformationCost(array3, array2);
        return (totalTransformationCost < totalTransformationCost2) ? -1 : (totalTransformationCost2 < totalTransformationCost);
    }
    
    private static float getTotalTransformationCost(final Class[] array, final Class[] array2) {
        float n = 0.0f;
        while (0 < array.length) {
            n += getObjectTransformationCost(array[0], array2[0]);
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    private static float getObjectTransformationCost(Class superclass, final Class clazz) {
        if (clazz.isPrimitive()) {
            return getPrimitivePromotionCost(superclass, clazz);
        }
        float n = 0.0f;
        while (superclass != null && !clazz.equals(superclass)) {
            if (clazz.isInterface() && ClassUtils.isAssignable(superclass, clazz)) {
                n += 0.25f;
                break;
            }
            ++n;
            superclass = superclass.getSuperclass();
        }
        if (superclass == null) {
            n += 1.5f;
        }
        return n;
    }
    
    private static float getPrimitivePromotionCost(final Class clazz, final Class clazz2) {
        float n = 0.0f;
        Class wrapperToPrimitive = clazz;
        if (!wrapperToPrimitive.isPrimitive()) {
            n += 0.1f;
            wrapperToPrimitive = ClassUtils.wrapperToPrimitive(wrapperToPrimitive);
        }
        while (wrapperToPrimitive != clazz2 && 0 < MemberUtils.ORDERED_PRIMITIVE_TYPES.length) {
            if (wrapperToPrimitive == MemberUtils.ORDERED_PRIMITIVE_TYPES[0]) {
                n += 0.1f;
                if (0 < MemberUtils.ORDERED_PRIMITIVE_TYPES.length - 1) {
                    wrapperToPrimitive = MemberUtils.ORDERED_PRIMITIVE_TYPES[1];
                }
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    static {
        ORDERED_PRIMITIVE_TYPES = new Class[] { Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };
    }
}
