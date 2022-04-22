package org.lwjgl.util.mapped;

import java.net.*;
import org.lwjgl.*;
import java.io.*;

public class MappedObjectClassLoader extends URLClassLoader
{
    static final String MAPPEDOBJECT_PACKAGE_PREFIX;
    static boolean FORKED;
    private static long total_time_transforming;
    
    public static boolean fork(final Class clazz, final String[] array) {
        if (MappedObjectClassLoader.FORKED) {
            return false;
        }
        MappedObjectClassLoader.FORKED = true;
        final MappedObjectClassLoader mappedObjectClassLoader = new MappedObjectClassLoader(clazz);
        mappedObjectClassLoader.loadMappedObject();
        mappedObjectClassLoader.loadClass(clazz.getName()).getMethod("main", String[].class).invoke(null, array);
        return true;
    }
    
    private MappedObjectClassLoader(final Class clazz) {
        super(((URLClassLoader)clazz.getClassLoader()).getURLs());
    }
    
    protected synchronized Class loadMappedObject() throws ClassNotFoundException {
        final String name = MappedObject.class.getName();
        final String replace = name.replace('.', '/');
        final byte[] stream = readStream(this.getResourceAsStream(replace.concat(".class")));
        final long nanoTime = System.nanoTime();
        final byte[] transformMappedObject = MappedObjectTransformer.transformMappedObject(stream);
        final long nanoTime2 = System.nanoTime();
        MappedObjectClassLoader.total_time_transforming += nanoTime2 - nanoTime;
        if (MappedObjectTransformer.PRINT_ACTIVITY) {
            printActivity(replace, nanoTime, nanoTime2);
        }
        final Class<?> defineClass = super.defineClass(name, transformMappedObject, 0, transformMappedObject.length);
        this.resolveClass(defineClass);
        return defineClass;
    }
    
    @Override
    protected synchronized Class loadClass(final String s, final boolean b) throws ClassNotFoundException {
        if (s.startsWith("java.") || s.startsWith("javax.") || s.startsWith("sun.") || s.startsWith("sunw.") || s.startsWith("org.objectweb.asm.")) {
            return super.loadClass(s, b);
        }
        final String replace = s.replace('.', '/');
        final boolean startsWith = s.startsWith(MappedObjectClassLoader.MAPPEDOBJECT_PACKAGE_PREFIX);
        if (startsWith && (s.equals(MappedObjectClassLoader.class.getName()) || s.equals(MappedObjectTransformer.class.getName()) || s.equals(CacheUtil.class.getName()))) {
            return super.loadClass(s, b);
        }
        byte[] stream = readStream(this.getResourceAsStream(replace.concat(".class")));
        if (!startsWith || s.substring(MappedObjectClassLoader.MAPPEDOBJECT_PACKAGE_PREFIX.length()).indexOf(46) != -1) {
            final long nanoTime = System.nanoTime();
            final byte[] transformMappedAPI = MappedObjectTransformer.transformMappedAPI(replace, stream);
            final long nanoTime2 = System.nanoTime();
            MappedObjectClassLoader.total_time_transforming += nanoTime2 - nanoTime;
            if (stream != transformMappedAPI) {
                stream = transformMappedAPI;
                if (MappedObjectTransformer.PRINT_ACTIVITY) {
                    printActivity(replace, nanoTime, nanoTime2);
                }
            }
        }
        final Class<?> defineClass = super.defineClass(s, stream, 0, stream.length);
        if (b) {
            this.resolveClass(defineClass);
        }
        return defineClass;
    }
    
    private static void printActivity(final String s, final long n, final long n2) {
        final StringBuilder sb = new StringBuilder(MappedObjectClassLoader.class.getSimpleName() + ": " + s);
        if (MappedObjectTransformer.PRINT_TIMING) {
            sb.append("\n\ttransforming took " + (n2 - n) / 1000L + " micros (total: " + MappedObjectClassLoader.total_time_transforming / 1000L / 1000L + "ms)");
        }
        LWJGLUtil.log(sb);
    }
    
    private static byte[] readStream(final InputStream inputStream) {
        byte[] copy = new byte[256];
        do {
            if (copy.length == 0) {
                copy = copyOf(copy, 0);
            }
        } while (inputStream.read(copy, 0, copy.length - 0) != -1);
        inputStream.close();
        return copyOf(copy, 0);
    }
    
    private static byte[] copyOf(final byte[] array, final int n) {
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    static {
        MAPPEDOBJECT_PACKAGE_PREFIX = MappedObjectClassLoader.class.getPackage().getName() + ".";
    }
}
