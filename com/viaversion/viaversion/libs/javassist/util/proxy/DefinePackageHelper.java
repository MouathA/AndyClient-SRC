package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.net.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.lang.reflect.*;
import java.lang.invoke.*;

public class DefinePackageHelper
{
    private static final Helper privileged;
    
    public static void definePackage(final String s, final ClassLoader classLoader) throws CannotCompileException {
        DefinePackageHelper.privileged.definePackage(classLoader, s, null, null, null, null, null, null, null);
    }
    
    private DefinePackageHelper() {
    }
    
    static {
        privileged = ((ClassFile.MAJOR_VERSION >= 53) ? new Java9(null) : ((ClassFile.MAJOR_VERSION >= 51) ? new Java7(null) : new JavaOther(null)));
    }
    
    private static class JavaOther extends Helper
    {
        private final SecurityActions stack;
        private final Method definePackage;
        
        private JavaOther() {
            super(null);
            this.stack = SecurityActions.stack;
            this.definePackage = this.getDefinePackageMethod();
        }
        
        private Method getDefinePackageMethod() {
            if (this.stack.getCallerClass() != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return SecurityActions.getDeclaredMethod(ClassLoader.class, "definePackage", new Class[] { String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class });
        }
        
        @Override
        Package definePackage(final ClassLoader classLoader, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final URL url) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            this.definePackage.setAccessible(true);
            return (Package)this.definePackage.invoke(classLoader, s, s2, s3, s4, s5, s6, s7, url);
        }
        
        JavaOther(final DefinePackageHelper$1 object) {
            this();
        }
    }
    
    private abstract static class Helper
    {
        private Helper() {
        }
        
        abstract Package definePackage(final ClassLoader p0, final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7, final URL p8) throws IllegalArgumentException;
        
        Helper(final DefinePackageHelper$1 object) {
            this();
        }
    }
    
    private static class Java7 extends Helper
    {
        private final SecurityActions stack;
        private final MethodHandle definePackage;
        
        private Java7() {
            super(null);
            this.stack = SecurityActions.stack;
            this.definePackage = this.getDefinePackageMethodHandle();
        }
        
        private MethodHandle getDefinePackageMethodHandle() {
            if (this.stack.getCallerClass() != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return SecurityActions.getMethodHandle(ClassLoader.class, "definePackage", new Class[] { String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class });
        }
        
        @Override
        Package definePackage(final ClassLoader classLoader, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final URL url) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return (Package)this.definePackage.invokeWithArguments(classLoader, s, s2, s3, s4, s5, s6, s7, url);
        }
        
        Java7(final DefinePackageHelper$1 object) {
            this();
        }
    }
    
    private static class Java9 extends Helper
    {
        private Java9() {
            super(null);
        }
        
        @Override
        Package definePackage(final ClassLoader classLoader, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final URL url) throws IllegalArgumentException {
            throw new RuntimeException("define package has been disabled for jigsaw");
        }
        
        Java9(final DefinePackageHelper$1 object) {
            this();
        }
    }
}
