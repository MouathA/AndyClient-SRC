package org.apache.commons.lang3;

import java.io.*;
import java.util.*;

public class SerializationUtils
{
    public static Serializable clone(final Serializable s) {
        if (s == null) {
            return null;
        }
        final ClassLoaderAwareObjectInputStream classLoaderAwareObjectInputStream = new ClassLoaderAwareObjectInputStream(new ByteArrayInputStream(serialize(s)), s.getClass().getClassLoader());
        final Serializable s2 = (Serializable)classLoaderAwareObjectInputStream.readObject();
        if (classLoaderAwareObjectInputStream != null) {
            classLoaderAwareObjectInputStream.close();
        }
        return s2;
    }
    
    public static Serializable roundtrip(final Serializable s) {
        return (Serializable)deserialize(serialize(s));
    }
    
    public static void serialize(final Serializable s, final OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(s);
        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
    }
    
    public static byte[] serialize(final Serializable s) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(s, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    public static Object deserialize(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        final Object object = objectInputStream.readObject();
        if (objectInputStream != null) {
            objectInputStream.close();
        }
        return object;
    }
    
    public static Object deserialize(final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        return deserialize(new ByteArrayInputStream(array));
    }
    
    static class ClassLoaderAwareObjectInputStream extends ObjectInputStream
    {
        private static final Map primitiveTypes;
        private final ClassLoader classLoader;
        
        public ClassLoaderAwareObjectInputStream(final InputStream inputStream, final ClassLoader classLoader) throws IOException {
            super(inputStream);
            this.classLoader = classLoader;
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("byte", Byte.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("short", Short.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("int", Integer.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("long", Long.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("float", Float.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("double", Double.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("boolean", Boolean.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("char", Character.TYPE);
            ClassLoaderAwareObjectInputStream.primitiveTypes.put("void", Void.TYPE);
        }
        
        @Override
        protected Class resolveClass(final ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            return Class.forName(objectStreamClass.getName(), false, this.classLoader);
        }
        
        static {
            primitiveTypes = new HashMap();
        }
    }
}
