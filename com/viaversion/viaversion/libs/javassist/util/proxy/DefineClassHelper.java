package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.security.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.lang.reflect.*;
import java.lang.invoke.*;
import java.util.*;

public class DefineClassHelper
{
    private static final Helper privileged;
    
    public static Class toClass(final String s, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain, final byte[] array) throws CannotCompileException {
        return DefineClassHelper.privileged.defineClass(s, array, 0, array.length, clazz, classLoader, protectionDomain);
    }
    
    public static Class toClass(final Class clazz, final byte[] array) throws CannotCompileException {
        DefineClassHelper.class.getModule().addReads(clazz.getModule());
        return MethodHandles.privateLookupIn(clazz, MethodHandles.lookup()).defineClass(array);
    }
    
    public static Class toClass(final MethodHandles.Lookup lookup, final byte[] array) throws CannotCompileException {
        return lookup.defineClass(array);
    }
    
    static Class toPublicClass(final String s, final byte[] array) throws CannotCompileException {
        return MethodHandles.lookup().dropLookupMode(2).defineClass(array);
    }
    
    private DefineClassHelper() {
    }
    
    static Helper access$400() {
        return DefineClassHelper.privileged;
    }
    
    static {
        privileged = ((ClassFile.MAJOR_VERSION > 54) ? new Java11(null) : ((ClassFile.MAJOR_VERSION >= 53) ? new Java9() : ((ClassFile.MAJOR_VERSION >= 51) ? new Java7(null) : new JavaOther(null))));
    }
    
    private static class JavaOther extends Helper
    {
        private final Method defineClass;
        private final SecurityActions stack;
        
        private JavaOther() {
            super(null);
            this.defineClass = this.getDefineClassMethod();
            this.stack = SecurityActions.stack;
        }
        
        private final Method getDefineClassMethod() {
            if (DefineClassHelper.access$400() != null && this.stack.getCallerClass() != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return SecurityActions.getDeclaredMethod(ClassLoader.class, "defineClass", new Class[] { String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class });
        }
        
        @Override
        Class defineClass(final String s, final byte[] array, final int n, final int n2, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            final Class callerClass = this.stack.getCallerClass();
            if (callerClass != DefineClassHelper.class && callerClass != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            SecurityActions.setAccessible(this.defineClass, true);
            return (Class)this.defineClass.invoke(classLoader, s, array, n, n2, protectionDomain);
        }
        
        JavaOther(final DefineClassHelper$1 object) {
            this();
        }
    }
    
    private abstract static class Helper
    {
        private Helper() {
        }
        
        abstract Class defineClass(final String p0, final byte[] p1, final int p2, final int p3, final Class p4, final ClassLoader p5, final ProtectionDomain p6) throws ClassFormatError, CannotCompileException;
        
        Helper(final DefineClassHelper$1 object) {
            this();
        }
    }
    
    private static class Java7 extends Helper
    {
        private final SecurityActions stack;
        private final MethodHandle defineClass;
        
        private Java7() {
            super(null);
            this.stack = SecurityActions.stack;
            this.defineClass = this.getDefineClassMethodHandle();
        }
        
        private final MethodHandle getDefineClassMethodHandle() {
            if (DefineClassHelper.access$400() != null && this.stack.getCallerClass() != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return SecurityActions.getMethodHandle(ClassLoader.class, "defineClass", new Class[] { String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class });
        }
        
        @Override
        Class defineClass(final String s, final byte[] array, final int n, final int n2, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws ClassFormatError {
            if (this.stack.getCallerClass() != DefineClassHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return (Class)this.defineClass.invokeWithArguments(classLoader, s, array, n, n2, protectionDomain);
        }
        
        Java7(final DefineClassHelper$1 object) {
            this();
        }
    }
    
    private static class Java9 extends Helper
    {
        private final Object stack;
        private final Method getCallerClass;
        private final ReferencedUnsafe sunMiscUnsafe;
        
        Java9() {
            super(null);
            this.sunMiscUnsafe = this.getReferencedUnsafe();
            final Class<?> forName = Class.forName("java.lang.StackWalker");
            if (forName != null) {
                final Class<?> forName2 = Class.forName("java.lang.StackWalker$Option");
                this.stack = forName.getMethod("getInstance", forName2).invoke(null, forName2.getEnumConstants()[0]);
                this.getCallerClass = forName.getMethod("getCallerClass", (Class<?>[])new Class[0]);
            }
            else {
                this.stack = null;
                this.getCallerClass = null;
            }
        }
        
        private final ReferencedUnsafe getReferencedUnsafe() {
            if (DefineClassHelper.access$400() != null && this.getCallerClass.invoke(this.stack, new Object[0]) != this.getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            final SecurityActions.TheUnsafe sunMiscUnsafeAnonymously = SecurityActions.getSunMiscUnsafeAnonymously();
            final List<Method> list = sunMiscUnsafeAnonymously.methods.get("defineClass");
            if (null == list) {
                return null;
            }
            return new ReferencedUnsafe(sunMiscUnsafeAnonymously, MethodHandles.lookup().unreflect(list.get(0)));
        }
        
        @Override
        Class defineClass(final String s, final byte[] array, final int n, final int n2, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws ClassFormatError {
            if (this.getCallerClass.invoke(this.stack, new Object[0]) != DefineClassHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            return this.sunMiscUnsafe.defineClass(s, array, n, n2, classLoader, protectionDomain);
        }
        
        static Object access$100(final Java9 java9) {
            return java9.stack;
        }
        
        static Method access$200(final Java9 java9) {
            return java9.getCallerClass;
        }
        
        final class ReferencedUnsafe
        {
            private final SecurityActions.TheUnsafe sunMiscUnsafeTheUnsafe;
            private final MethodHandle defineClass;
            final Java9 this$0;
            
            ReferencedUnsafe(final Java9 this$0, final SecurityActions.TheUnsafe sunMiscUnsafeTheUnsafe, final MethodHandle defineClass) {
                this.this$0 = this$0;
                this.sunMiscUnsafeTheUnsafe = sunMiscUnsafeTheUnsafe;
                this.defineClass = defineClass;
            }
            
            Class defineClass(final String s, final byte[] array, final int n, final int n2, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws ClassFormatError {
                if (Java9.access$200(this.this$0).invoke(Java9.access$100(this.this$0), new Object[0]) != Java9.class) {
                    throw new IllegalAccessError("Access denied for caller.");
                }
                return (Class)this.defineClass.invokeWithArguments(this.sunMiscUnsafeTheUnsafe.theUnsafe, s, array, n, n2, classLoader, protectionDomain);
            }
        }
    }
    
    private static class Java11 extends JavaOther
    {
        private Java11() {
            super(null);
        }
        
        @Override
        Class defineClass(final String s, final byte[] array, final int n, final int n2, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            if (clazz != null) {
                return DefineClassHelper.toClass(clazz, array);
            }
            return super.defineClass(s, array, n, n2, clazz, classLoader, protectionDomain);
        }
        
        Java11(final DefineClassHelper$1 object) {
            this();
        }
    }
}
