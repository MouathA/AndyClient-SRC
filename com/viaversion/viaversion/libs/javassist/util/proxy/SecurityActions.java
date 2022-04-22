package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.security.*;
import java.lang.invoke.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

class SecurityActions extends SecurityManager
{
    public static final SecurityActions stack;
    
    public Class getCallerClass() {
        return this.getClassContext()[2];
    }
    
    static Method[] getDeclaredMethods(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethods();
        }
        return AccessController.doPrivileged((PrivilegedAction<Method[]>)new PrivilegedAction() {
            final Class val$clazz;
            
            @Override
            public Method[] run() {
                return this.val$clazz.getDeclaredMethods();
            }
            
            @Override
            public Object run() {
                return this.run();
            }
        });
    }
    
    static Constructor[] getDeclaredConstructors(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructors();
        }
        return AccessController.doPrivileged((PrivilegedAction<Constructor[]>)new PrivilegedAction() {
            final Class val$clazz;
            
            @Override
            public Constructor[] run() {
                return this.val$clazz.getDeclaredConstructors();
            }
            
            @Override
            public Object run() {
                return this.run();
            }
        });
    }
    
    static MethodHandle getMethodHandle(final Class clazz, final String s, final Class[] array) throws NoSuchMethodException {
        return AccessController.doPrivileged((PrivilegedExceptionAction<MethodHandle>)new PrivilegedExceptionAction(s, array) {
            final Class val$clazz;
            final String val$name;
            final Class[] val$params;
            
            @Override
            public MethodHandle run() throws IllegalAccessException, NoSuchMethodException, SecurityException {
                final Method declaredMethod = this.val$clazz.getDeclaredMethod(this.val$name, (Class[])this.val$params);
                declaredMethod.setAccessible(true);
                final MethodHandle unreflect = MethodHandles.lookup().unreflect(declaredMethod);
                declaredMethod.setAccessible(false);
                return unreflect;
            }
            
            @Override
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    static Method getDeclaredMethod(final Class clazz, final String s, final Class[] array) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethod(s, (Class[])array);
        }
        return AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction(s, array) {
            final Class val$clazz;
            final String val$name;
            final Class[] val$types;
            
            @Override
            public Method run() throws Exception {
                return this.val$clazz.getDeclaredMethod(this.val$name, (Class[])this.val$types);
            }
            
            @Override
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    static Constructor getDeclaredConstructor(final Class clazz, final Class[] array) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructor((Class[])array);
        }
        return AccessController.doPrivileged((PrivilegedExceptionAction<Constructor>)new PrivilegedExceptionAction(array) {
            final Class val$clazz;
            final Class[] val$types;
            
            @Override
            public Constructor run() throws Exception {
                return this.val$clazz.getDeclaredConstructor((Class[])this.val$types);
            }
            
            @Override
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    static void setAccessible(final AccessibleObject accessibleObject, final boolean accessible) {
        if (System.getSecurityManager() == null) {
            accessibleObject.setAccessible(accessible);
        }
        else {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(accessible) {
                final AccessibleObject val$ao;
                final boolean val$accessible;
                
                @Override
                public Void run() {
                    this.val$ao.setAccessible(this.val$accessible);
                    return null;
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            });
        }
    }
    
    static void set(final Field field, final Object o, final Object o2) throws IllegalAccessException {
        if (System.getSecurityManager() == null) {
            field.set(o, o2);
        }
        else {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction(o, o2) {
                final Field val$fld;
                final Object val$target;
                final Object val$value;
                
                @Override
                public Void run() throws Exception {
                    this.val$fld.set(this.val$target, this.val$value);
                    return null;
                }
                
                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        }
    }
    
    static TheUnsafe getSunMiscUnsafeAnonymously() throws ClassNotFoundException {
        return AccessController.doPrivileged((PrivilegedExceptionAction<TheUnsafe>)new PrivilegedExceptionAction() {
            @Override
            public TheUnsafe run() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
                final Class<?> forName = Class.forName("sun.misc.Unsafe");
                final Field declaredField = forName.getDeclaredField("theUnsafe");
                declaredField.setAccessible(true);
                final SecurityActions stack = SecurityActions.stack;
                Objects.requireNonNull(stack);
                final TheUnsafe theUnsafe = stack.new TheUnsafe(forName, declaredField.get(null));
                declaredField.setAccessible(false);
                SecurityActions.disableWarning(theUnsafe);
                return theUnsafe;
            }
            
            @Override
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    static void disableWarning(final TheUnsafe theUnsafe) {
        if (ClassFile.MAJOR_VERSION < 53) {
            return;
        }
        final Class<?> forName = Class.forName("jdk.internal.module.IllegalAccessLogger");
        theUnsafe.call("putObjectVolatile", forName, theUnsafe.call("staticFieldOffset", forName.getDeclaredField("logger")), null);
    }
    
    static {
        stack = new SecurityActions();
    }
    
    class TheUnsafe
    {
        final Class unsafe;
        final Object theUnsafe;
        final Map methods;
        final SecurityActions this$0;
        
        TheUnsafe(final SecurityActions this$0, final Class unsafe, final Object theUnsafe) {
            this.this$0 = this$0;
            this.methods = new HashMap();
            this.unsafe = unsafe;
            this.theUnsafe = theUnsafe;
            final Method[] declaredMethods = this.unsafe.getDeclaredMethods();
            while (0 < declaredMethods.length) {
                final Method method = declaredMethods[0];
                if (!this.methods.containsKey(method.getName())) {
                    this.methods.put(method.getName(), Collections.singletonList(method));
                }
                else {
                    if (((List)this.methods.get(method.getName())).size() == 1) {
                        this.methods.put(method.getName(), new ArrayList((Collection<?>)this.methods.get(method.getName())));
                    }
                    ((List<Method>)this.methods.get(method.getName())).add(method);
                }
                int n = 0;
                ++n;
            }
        }
        
        private Method getM(final String s, final Object[] array) {
            return this.methods.get(s).get(0);
        }
        
        public Object call(final String s, final Object... array) {
            return this.getM(s, array).invoke(this.theUnsafe, array);
        }
    }
}
