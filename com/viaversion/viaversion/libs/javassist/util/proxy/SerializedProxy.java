package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.security.*;
import java.io.*;

class SerializedProxy implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String superClass;
    private String[] interfaces;
    private byte[] filterSignature;
    private MethodHandler handler;
    
    SerializedProxy(final Class clazz, final byte[] filterSignature, final MethodHandler handler) {
        this.filterSignature = filterSignature;
        this.handler = handler;
        this.superClass = clazz.getSuperclass().getName();
        final Class[] interfaces = clazz.getInterfaces();
        final int length = interfaces.length;
        this.interfaces = new String[length - 1];
        final String name = ProxyObject.class.getName();
        final String name2 = Proxy.class.getName();
        while (0 < length) {
            final String name3 = interfaces[0].getName();
            if (!name3.equals(name) && !name3.equals(name2)) {
                this.interfaces[0] = name3;
            }
            int n = 0;
            ++n;
        }
    }
    
    protected Class loadClass(final String s) throws ClassNotFoundException {
        return AccessController.doPrivileged((PrivilegedExceptionAction<Class>)new PrivilegedExceptionAction(s) {
            final String val$className;
            final SerializedProxy this$0;
            
            @Override
            public Class run() throws Exception {
                return Class.forName(this.val$className, true, Thread.currentThread().getContextClassLoader());
            }
            
            @Override
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    Object readResolve() throws ObjectStreamException {
        final int length = this.interfaces.length;
        final Class[] interfaces = new Class[length];
        while (0 < length) {
            interfaces[0] = this.loadClass(this.interfaces[0]);
            int n = 0;
            ++n;
        }
        final ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(this.loadClass(this.superClass));
        proxyFactory.setInterfaces(interfaces);
        final Proxy proxy = proxyFactory.createClass(this.filterSignature).getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        proxy.setHandler(this.handler);
        return proxy;
    }
}
