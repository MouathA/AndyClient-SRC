package org.apache.commons.io.input;

import java.io.*;
import java.lang.reflect.*;

public class ClassLoaderObjectInputStream extends ObjectInputStream
{
    private final ClassLoader classLoader;
    
    public ClassLoaderObjectInputStream(final ClassLoader classLoader, final InputStream inputStream) throws IOException, StreamCorruptedException {
        super(inputStream);
        this.classLoader = classLoader;
    }
    
    @Override
    protected Class resolveClass(final ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        final Class<?> forName = Class.forName(objectStreamClass.getName(), false, this.classLoader);
        if (forName != null) {
            return forName;
        }
        return super.resolveClass(objectStreamClass);
    }
    
    @Override
    protected Class resolveProxyClass(final String[] array) throws IOException, ClassNotFoundException {
        final Class[] array2 = new Class[array.length];
        while (0 < array.length) {
            array2[0] = Class.forName(array[0], false, this.classLoader);
            int n = 0;
            ++n;
        }
        return Proxy.getProxyClass(this.classLoader, (Class<?>[])array2);
    }
}
