package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.io.*;

public class ProxyObjectOutputStream extends ObjectOutputStream
{
    public ProxyObjectOutputStream(final OutputStream outputStream) throws IOException {
        super(outputStream);
    }
    
    @Override
    protected void writeClassDescriptor(final ObjectStreamClass objectStreamClass) throws IOException {
        final Class<?> forClass = objectStreamClass.forClass();
        if (ProxyFactory.isProxyClass(forClass)) {
            this.writeBoolean(true);
            final Class<?> superclass = forClass.getSuperclass();
            final Class[] interfaces = forClass.getInterfaces();
            final byte[] filterSignature = ProxyFactory.getFilterSignature(forClass);
            this.writeObject(superclass.getName());
            this.writeInt(interfaces.length - 1);
            while (0 < interfaces.length) {
                final Class clazz = interfaces[0];
                if (clazz != ProxyObject.class && clazz != Proxy.class) {
                    this.writeObject(interfaces[0].getName());
                }
                int n = 0;
                ++n;
            }
            this.writeInt(filterSignature.length);
            this.write(filterSignature);
        }
        else {
            this.writeBoolean(false);
            super.writeClassDescriptor(objectStreamClass);
        }
    }
}
