package com.viaversion.viaversion.libs.javassist.tools.reflect;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;

public class ClassMetaobject implements Serializable
{
    private static final long serialVersionUID = 1L;
    static final String methodPrefix = "_m_";
    static final int methodPrefixLen = 3;
    private Class javaClass;
    private Constructor[] constructors;
    private Method[] methods;
    public static boolean useContextClassLoader;
    
    public ClassMetaobject(final String[] array) {
        this.javaClass = this.getClassObject(array[0]);
        this.constructors = this.javaClass.getConstructors();
        this.methods = null;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.javaClass.getName());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.javaClass = this.getClassObject(objectInputStream.readUTF());
        this.constructors = this.javaClass.getConstructors();
        this.methods = null;
    }
    
    private Class getClassObject(final String s) throws ClassNotFoundException {
        if (ClassMetaobject.useContextClassLoader) {
            return Thread.currentThread().getContextClassLoader().loadClass(s);
        }
        return Class.forName(s);
    }
    
    public final Class getJavaClass() {
        return this.javaClass;
    }
    
    public final String getName() {
        return this.javaClass.getName();
    }
    
    public final boolean isInstance(final Object o) {
        return this.javaClass.isInstance(o);
    }
    
    public final Object newInstance(final Object[] array) throws CannotCreateException {
        if (0 < this.constructors.length) {
            return this.constructors[0].newInstance(array);
        }
        throw new CannotCreateException("no constructor matches");
    }
    
    public Object trapFieldRead(final String s) {
        return this.getJavaClass().getField(s).get(null);
    }
    
    public void trapFieldWrite(final String s, final Object o) {
        this.getJavaClass().getField(s).set(null, o);
    }
    
    public static Object invoke(final Object o, final int n, final Object[] array) throws Throwable {
        final Method[] methods = o.getClass().getMethods();
        final int length = methods.length;
        final String string = "_m_" + n;
        while (0 < length) {
            if (methods[0].getName().startsWith(string)) {
                return methods[0].invoke(o, array);
            }
            int n2 = 0;
            ++n2;
        }
        throw new CannotInvokeException("cannot find a method");
    }
    
    public Object trapMethodcall(final int n, final Object[] array) throws Throwable {
        return this.getReflectiveMethods()[n].invoke(null, array);
    }
    
    public final Method[] getReflectiveMethods() {
        if (this.methods != null) {
            return this.methods;
        }
        final Method[] declaredMethods = this.getJavaClass().getDeclaredMethods();
        final int length = declaredMethods.length;
        final int[] array = new int[length];
        int n4 = 0;
        while (0 < length) {
            final String name = declaredMethods[0].getName();
            if (name.startsWith("_m_")) {
                int n = 0;
                while (true) {
                    final char char1 = name.charAt(3);
                    if ('0' > char1 || char1 > '9') {
                        break;
                    }
                    n = '\0' + char1 - 48;
                    int n2 = 0;
                    ++n2;
                }
                final int[] array2 = array;
                final int n3 = 0;
                ++n;
                array2[n3] = 0;
                if (0 > 0) {}
            }
            ++n4;
        }
        this.methods = new Method[0];
        while (0 < length) {
            if (array[0] > 0) {
                this.methods[array[0] - 1] = declaredMethods[0];
            }
            ++n4;
        }
        return this.methods;
    }
    
    public final Method getMethod(final int n) {
        return this.getReflectiveMethods()[n];
    }
    
    public final String getMethodName(final int n) {
        final String name = this.getReflectiveMethods()[n].getName();
        char char1;
        do {
            final String s = name;
            final int n2 = 3;
            int n3 = 0;
            ++n3;
            char1 = s.charAt(n2);
        } while (char1 >= '0' && '9' >= char1);
        return name.substring(3);
    }
    
    public final Class[] getParameterTypes(final int n) {
        return this.getReflectiveMethods()[n].getParameterTypes();
    }
    
    public final Class getReturnType(final int n) {
        return this.getReflectiveMethods()[n].getReturnType();
    }
    
    public final int getMethodIndex(final String s, final Class[] array) throws NoSuchMethodException {
        final Method[] reflectiveMethods = this.getReflectiveMethods();
        while (0 < reflectiveMethods.length) {
            if (reflectiveMethods[0] != null) {
                if (this.getMethodName(0).equals(s) && Arrays.equals(array, reflectiveMethods[0].getParameterTypes())) {
                    return 0;
                }
            }
            int n = 0;
            ++n;
        }
        throw new NoSuchMethodException("Method " + s + " not found");
    }
    
    static {
        ClassMetaobject.useContextClassLoader = false;
    }
}
