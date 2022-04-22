package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.lang.reflect.*;
import java.io.*;

public class RuntimeSupport
{
    public static MethodHandler default_interceptor;
    
    public static void find2Methods(final Class clazz, final String s, final String s2, final int n, final String s3, final Method[] array) {
        array[n + 1] = ((s2 == null) ? null : findMethod(clazz, s2, s3));
        array[n] = findSuperClassMethod(clazz, s, s3);
    }
    
    @Deprecated
    public static void find2Methods(final Object o, final String s, final String s2, final int n, final String s3, final Method[] array) {
        array[n + 1] = ((s2 == null) ? null : findMethod(o, s2, s3));
        array[n] = findSuperMethod(o, s, s3);
    }
    
    @Deprecated
    public static Method findMethod(final Object o, final String s, final String s2) {
        final Method method2 = findMethod2(o.getClass(), s, s2);
        if (method2 == null) {
            error(o.getClass(), s, s2);
        }
        return method2;
    }
    
    public static Method findMethod(final Class clazz, final String s, final String s2) {
        final Method method2 = findMethod2(clazz, s, s2);
        if (method2 == null) {
            error(clazz, s, s2);
        }
        return method2;
    }
    
    public static Method findSuperMethod(final Object o, final String s, final String s2) {
        return findSuperClassMethod(o.getClass(), s, s2);
    }
    
    public static Method findSuperClassMethod(final Class clazz, final String s, final String s2) {
        Method method = findSuperMethod2(clazz.getSuperclass(), s, s2);
        if (method == null) {
            method = searchInterfaces(clazz, s, s2);
        }
        if (method == null) {
            error(clazz, s, s2);
        }
        return method;
    }
    
    private static void error(final Class clazz, final String s, final String s2) {
        throw new RuntimeException("not found " + s + ":" + s2 + " in " + clazz.getName());
    }
    
    private static Method findSuperMethod2(final Class clazz, final String s, final String s2) {
        final Method method2 = findMethod2(clazz, s, s2);
        if (method2 != null) {
            return method2;
        }
        final Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            final Method superMethod2 = findSuperMethod2(superclass, s, s2);
            if (superMethod2 != null) {
                return superMethod2;
            }
        }
        return searchInterfaces(clazz, s, s2);
    }
    
    private static Method searchInterfaces(final Class clazz, final String s, final String s2) {
        Method superMethod2 = null;
        final Class[] interfaces = clazz.getInterfaces();
        while (0 < interfaces.length) {
            superMethod2 = findSuperMethod2(interfaces[0], s, s2);
            if (superMethod2 != null) {
                return superMethod2;
            }
            int n = 0;
            ++n;
        }
        return superMethod2;
    }
    
    private static Method findMethod2(final Class clazz, final String s, final String s2) {
        final Method[] declaredMethods = SecurityActions.getDeclaredMethods(clazz);
        while (0 < declaredMethods.length) {
            if (declaredMethods[0].getName().equals(s) && makeDescriptor(declaredMethods[0]).equals(s2)) {
                return declaredMethods[0];
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static String makeDescriptor(final Method method) {
        return makeDescriptor(method.getParameterTypes(), method.getReturnType());
    }
    
    public static String makeDescriptor(final Class[] array, final Class clazz) {
        final StringBuffer sb = new StringBuffer();
        sb.append('(');
        while (0 < array.length) {
            makeDesc(sb, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(')');
        if (clazz != null) {
            makeDesc(sb, clazz);
        }
        return sb.toString();
    }
    
    public static String makeDescriptor(final String s, final Class clazz) {
        final StringBuffer sb = new StringBuffer(s);
        makeDesc(sb, clazz);
        return sb.toString();
    }
    
    private static void makeDesc(final StringBuffer sb, final Class clazz) {
        if (clazz.isArray()) {
            sb.append('[');
            makeDesc(sb, clazz.getComponentType());
        }
        else if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                sb.append('V');
            }
            else if (clazz == Integer.TYPE) {
                sb.append('I');
            }
            else if (clazz == Byte.TYPE) {
                sb.append('B');
            }
            else if (clazz == Long.TYPE) {
                sb.append('J');
            }
            else if (clazz == Double.TYPE) {
                sb.append('D');
            }
            else if (clazz == Float.TYPE) {
                sb.append('F');
            }
            else if (clazz == Character.TYPE) {
                sb.append('C');
            }
            else if (clazz == Short.TYPE) {
                sb.append('S');
            }
            else {
                if (clazz != Boolean.TYPE) {
                    throw new RuntimeException("bad type: " + clazz.getName());
                }
                sb.append('Z');
            }
        }
        else {
            sb.append('L').append(clazz.getName().replace('.', '/')).append(';');
        }
    }
    
    public static SerializedProxy makeSerializedProxy(final Object o) throws InvalidClassException {
        final Class<?> class1 = o.getClass();
        MethodHandler methodHandler = null;
        if (o instanceof ProxyObject) {
            methodHandler = ((ProxyObject)o).getHandler();
        }
        else if (o instanceof Proxy) {
            methodHandler = ProxyFactory.getHandler((Proxy)o);
        }
        return new SerializedProxy(class1, ProxyFactory.getFilterSignature(class1), methodHandler);
    }
    
    static {
        RuntimeSupport.default_interceptor = new DefaultMethodHandler();
    }
    
    static class DefaultMethodHandler implements MethodHandler, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public Object invoke(final Object o, final Method method, final Method method2, final Object[] array) throws Exception {
            return method2.invoke(o, array);
        }
    }
}
