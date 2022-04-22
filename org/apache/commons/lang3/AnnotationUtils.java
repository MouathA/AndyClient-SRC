package org.apache.commons.lang3;

import java.lang.annotation.*;
import java.lang.reflect.*;
import org.apache.commons.lang3.builder.*;
import java.util.*;

public class AnnotationUtils
{
    private static final ToStringStyle TO_STRING_STYLE;
    
    public static boolean equals(final Annotation annotation, final Annotation annotation2) {
        if (annotation == annotation2) {
            return true;
        }
        if (annotation == null || annotation2 == null) {
            return false;
        }
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        final Class<? extends Annotation> annotationType2 = annotation2.annotationType();
        Validate.notNull(annotationType, "Annotation %s with null annotationType()", annotation);
        Validate.notNull(annotationType2, "Annotation %s with null annotationType()", annotation2);
        if (!annotationType.equals(annotationType2)) {
            return false;
        }
        final Method[] declaredMethods = annotationType.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method method = declaredMethods[0];
            if (method.getParameterTypes().length == 0 && isValidAnnotationMemberType(method.getReturnType()) && !memberEquals(method.getReturnType(), method.invoke(annotation, new Object[0]), method.invoke(annotation2, new Object[0]))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static int hashCode(final Annotation annotation) {
        final Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method method = declaredMethods[0];
            final Object invoke = method.invoke(annotation, new Object[0]);
            if (invoke == null) {
                throw new IllegalStateException(String.format("Annotation method %s returned null", method));
            }
            final int n = 0 + hashMember(method.getName(), invoke);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static String toString(final Annotation annotation) {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(annotation, AnnotationUtils.TO_STRING_STYLE);
        final Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method method = declaredMethods[0];
            if (method.getParameterTypes().length <= 0) {
                toStringBuilder.append(method.getName(), method.invoke(annotation, new Object[0]));
            }
            int n = 0;
            ++n;
        }
        return toStringBuilder.build();
    }
    
    public static boolean isValidAnnotationMemberType(Class componentType) {
        if (componentType == null) {
            return false;
        }
        if (componentType.isArray()) {
            componentType = componentType.getComponentType();
        }
        return componentType.isPrimitive() || componentType.isEnum() || componentType.isAnnotation() || String.class.equals(componentType) || Class.class.equals(componentType);
    }
    
    private static int hashMember(final String s, final Object o) {
        final int n = s.hashCode() * 127;
        if (o.getClass().isArray()) {
            return n ^ arrayMemberHash(o.getClass().getComponentType(), o);
        }
        if (o instanceof Annotation) {
            return n ^ hashCode((Annotation)o);
        }
        return n ^ o.hashCode();
    }
    
    private static boolean memberEquals(final Class clazz, final Object o, final Object o2) {
        if (o == o2) {
            return true;
        }
        if (o == null || o2 == null) {
            return false;
        }
        if (clazz.isArray()) {
            return arrayMemberEquals(clazz.getComponentType(), o, o2);
        }
        if (clazz.isAnnotation()) {
            return equals((Annotation)o, (Annotation)o2);
        }
        return o.equals(o2);
    }
    
    private static boolean arrayMemberEquals(final Class clazz, final Object o, final Object o2) {
        if (clazz.isAnnotation()) {
            return annotationArrayMemberEquals((Annotation[])o, (Annotation[])o2);
        }
        if (clazz.equals(Byte.TYPE)) {
            return Arrays.equals((byte[])o, (byte[])o2);
        }
        if (clazz.equals(Short.TYPE)) {
            return Arrays.equals((short[])o, (short[])o2);
        }
        if (clazz.equals(Integer.TYPE)) {
            return Arrays.equals((int[])o, (int[])o2);
        }
        if (clazz.equals(Character.TYPE)) {
            return Arrays.equals((char[])o, (char[])o2);
        }
        if (clazz.equals(Long.TYPE)) {
            return Arrays.equals((long[])o, (long[])o2);
        }
        if (clazz.equals(Float.TYPE)) {
            return Arrays.equals((float[])o, (float[])o2);
        }
        if (clazz.equals(Double.TYPE)) {
            return Arrays.equals((double[])o, (double[])o2);
        }
        if (clazz.equals(Boolean.TYPE)) {
            return Arrays.equals((boolean[])o, (boolean[])o2);
        }
        return Arrays.equals((Object[])o, (Object[])o2);
    }
    
    private static boolean annotationArrayMemberEquals(final Annotation[] array, final Annotation[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        while (0 < array.length) {
            if (!equals(array[0], array2[0])) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private static int arrayMemberHash(final Class clazz, final Object o) {
        if (clazz.equals(Byte.TYPE)) {
            return Arrays.hashCode((byte[])o);
        }
        if (clazz.equals(Short.TYPE)) {
            return Arrays.hashCode((short[])o);
        }
        if (clazz.equals(Integer.TYPE)) {
            return Arrays.hashCode((int[])o);
        }
        if (clazz.equals(Character.TYPE)) {
            return Arrays.hashCode((char[])o);
        }
        if (clazz.equals(Long.TYPE)) {
            return Arrays.hashCode((long[])o);
        }
        if (clazz.equals(Float.TYPE)) {
            return Arrays.hashCode((float[])o);
        }
        if (clazz.equals(Double.TYPE)) {
            return Arrays.hashCode((double[])o);
        }
        if (clazz.equals(Boolean.TYPE)) {
            return Arrays.hashCode((boolean[])o);
        }
        return Arrays.hashCode((Object[])o);
    }
    
    static {
        TO_STRING_STYLE = new ToStringStyle() {
            private static final long serialVersionUID = 1L;
            
            {
                this.setDefaultFullDetail(true);
                this.setArrayContentDetail(true);
                this.setUseClassName(true);
                this.setUseShortClassName(true);
                this.setUseIdentityHashCode(false);
                this.setContentStart("(");
                this.setContentEnd(")");
                this.setFieldSeparator(", ");
                this.setArrayStart("[");
                this.setArrayEnd("]");
            }
            
            @Override
            protected String getShortClassName(final Class clazz) {
                Class<?> clazz2 = null;
                for (final Class<?> clazz3 : ClassUtils.getAllInterfaces(clazz)) {
                    if (Annotation.class.isAssignableFrom(clazz3)) {
                        clazz2 = clazz3;
                        break;
                    }
                }
                return new StringBuilder((clazz2 == null) ? "" : clazz2.getName()).insert(0, '@').toString();
            }
            
            @Override
            protected void appendDetail(final StringBuffer sb, final String s, Object string) {
                if (string instanceof Annotation) {
                    string = AnnotationUtils.toString((Annotation)string);
                }
                super.appendDetail(sb, s, string);
            }
        };
    }
}
