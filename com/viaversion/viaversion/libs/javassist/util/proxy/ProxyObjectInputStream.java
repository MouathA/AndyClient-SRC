package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.io.*;

public class ProxyObjectInputStream extends ObjectInputStream
{
    private ClassLoader loader;
    
    public ProxyObjectInputStream(final InputStream inputStream) throws IOException {
        super(inputStream);
        this.loader = Thread.currentThread().getContextClassLoader();
        if (this.loader == null) {
            this.loader = ClassLoader.getSystemClassLoader();
        }
    }
    
    public void setClassLoader(ClassLoader systemClassLoader) {
        if (systemClassLoader != null) {
            this.loader = systemClassLoader;
        }
        else {
            systemClassLoader = ClassLoader.getSystemClassLoader();
        }
    }
    
    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        if (this.readBoolean()) {
            final Class<?> loadClass = this.loader.loadClass((String)this.readObject());
            final int int1 = this.readInt();
            final Class[] interfaces = new Class[int1];
            while (0 < int1) {
                interfaces[0] = this.loader.loadClass((String)this.readObject());
                int n = 0;
                ++n;
            }
            final byte[] array = new byte[this.readInt()];
            this.read(array);
            final ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setUseCache(true);
            proxyFactory.setUseWriteReplace(false);
            proxyFactory.setSuperclass(loadClass);
            proxyFactory.setInterfaces(interfaces);
            return ObjectStreamClass.lookup(proxyFactory.createClass(array));
        }
        return super.readClassDescriptor();
    }
}
