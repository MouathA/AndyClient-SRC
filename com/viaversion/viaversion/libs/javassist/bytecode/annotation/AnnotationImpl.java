package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class AnnotationImpl implements InvocationHandler
{
    private static final String JDK_ANNOTATION_CLASS_NAME = "java.lang.annotation.Annotation";
    private static Method JDK_ANNOTATION_TYPE_METHOD;
    private Annotation annotation;
    private ClassPool pool;
    private ClassLoader classLoader;
    private transient Class annotationType;
    private transient int cachedHashCode;
    
    public static Object make(final ClassLoader classLoader, final Class clazz, final ClassPool classPool, final Annotation annotation) throws IllegalArgumentException {
        return Proxy.newProxyInstance(classLoader, new Class[] { clazz }, new AnnotationImpl(annotation, classPool, classLoader));
    }
    
    private AnnotationImpl(final Annotation annotation, final ClassPool pool, final ClassLoader classLoader) {
        this.cachedHashCode = Integer.MIN_VALUE;
        this.annotation = annotation;
        this.pool = pool;
        this.classLoader = classLoader;
    }
    
    public String getTypeName() {
        return this.annotation.getTypeName();
    }
    
    private Class getAnnotationType() {
        if (this.annotationType == null) {
            this.annotationType = this.classLoader.loadClass(this.annotation.getTypeName());
        }
        return this.annotationType;
    }
    
    public Annotation getAnnotation() {
        return this.annotation;
    }
    
    @Override
    public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
        final String name = method.getName();
        if (Object.class == method.getDeclaringClass()) {
            if ("equals".equals(name)) {
                return this.checkEquals(array[0]);
            }
            if ("toString".equals(name)) {
                return this.annotation.toString();
            }
            if ("hashCode".equals(name)) {
                return this.hashCode();
            }
        }
        else if ("annotationType".equals(name) && method.getParameterTypes().length == 0) {
            return this.getAnnotationType();
        }
        final MemberValue memberValue = this.annotation.getMemberValue(name);
        if (memberValue == null) {
            return this.getDefault(name, method);
        }
        return memberValue.getValue(this.classLoader, this.pool, method);
    }
    
    private Object getDefault(final String s, final Method method) throws ClassNotFoundException, RuntimeException {
        final String typeName = this.annotation.getTypeName();
        if (this.pool != null) {
            final MethodInfo method2 = this.pool.get(typeName).getClassFile2().getMethod(s);
            if (method2 != null) {
                final AnnotationDefaultAttribute annotationDefaultAttribute = (AnnotationDefaultAttribute)method2.getAttribute("AnnotationDefault");
                if (annotationDefaultAttribute != null) {
                    return annotationDefaultAttribute.getDefaultValue().getValue(this.classLoader, this.pool, method);
                }
            }
        }
        throw new RuntimeException("no default value: " + typeName + "." + s + "()");
    }
    
    @Override
    public int hashCode() {
        if (this.cachedHashCode == Integer.MIN_VALUE) {
            this.getAnnotationType();
            final Method[] declaredMethods = this.annotationType.getDeclaredMethods();
            while (0 < declaredMethods.length) {
                final String name = declaredMethods[0].getName();
                final MemberValue memberValue = this.annotation.getMemberValue(name);
                Object o = null;
                if (memberValue != null) {
                    o = memberValue.getValue(this.classLoader, this.pool, declaredMethods[0]);
                }
                if (o == null) {
                    o = this.getDefault(name, declaredMethods[0]);
                }
                if (o != null) {
                    if (o.getClass().isArray()) {
                        arrayHashCode(o);
                    }
                    else {
                        o.hashCode();
                    }
                }
                final int n = 0 + (127 * name.hashCode() ^ 0x0);
                int n2 = 0;
                ++n2;
            }
            this.cachedHashCode = 0;
        }
        return this.cachedHashCode;
    }
    
    private boolean checkEquals(final Object o) throws Exception {
        if (o == null) {
            return false;
        }
        if (o instanceof Proxy) {
            final InvocationHandler invocationHandler = Proxy.getInvocationHandler(o);
            if (invocationHandler instanceof AnnotationImpl) {
                return this.annotation.equals(((AnnotationImpl)invocationHandler).annotation);
            }
        }
        if (!this.getAnnotationType().equals(AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD.invoke(o, new Object[0]))) {
            return false;
        }
        final Method[] declaredMethods = this.annotationType.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final String name = declaredMethods[0].getName();
            final MemberValue memberValue = this.annotation.getMemberValue(name);
            Object o2 = null;
            if (memberValue != null) {
                o2 = memberValue.getValue(this.classLoader, this.pool, declaredMethods[0]);
            }
            if (o2 == null) {
                o2 = this.getDefault(name, declaredMethods[0]);
            }
            final Object invoke = declaredMethods[0].invoke(o, new Object[0]);
            if (o2 == null && invoke != null) {
                return false;
            }
            if (o2 != null && !o2.equals(invoke)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private static int arrayHashCode(final Object o) {
        if (o == null) {
            return 0;
        }
        final Object[] array = (Object[])o;
        while (0 < array.length) {
            if (array[0] != null) {
                array[0].hashCode();
            }
            int n = 0;
            ++n;
        }
        return 1;
    }
    
    static {
        AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD = null;
        AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD = Class.forName("java.lang.annotation.Annotation").getMethod("annotationType", (Class<?>[])null);
    }
}
