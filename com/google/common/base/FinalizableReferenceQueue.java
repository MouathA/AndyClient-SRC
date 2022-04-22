package com.google.common.base;

import java.util.logging.*;
import java.lang.reflect.*;
import java.lang.ref.*;
import java.io.*;
import java.net.*;

public class FinalizableReferenceQueue implements Closeable
{
    private static final Logger logger;
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    private static final Method startFinalizer;
    final ReferenceQueue queue;
    final PhantomReference frqRef;
    final boolean threadStarted;
    
    public FinalizableReferenceQueue() {
        this.queue = new ReferenceQueue();
        this.frqRef = new PhantomReference((T)this, this.queue);
        FinalizableReferenceQueue.startFinalizer.invoke(null, FinalizableReference.class, this.queue, this.frqRef);
        this.threadStarted = true;
    }
    
    @Override
    public void close() {
        this.frqRef.enqueue();
        this.cleanUp();
    }
    
    void cleanUp() {
        if (this.threadStarted) {
            return;
        }
        Reference poll;
        while ((poll = this.queue.poll()) != null) {
            poll.clear();
            ((FinalizableReference)poll).finalizeReferent();
        }
    }
    
    private static Class loadFinalizer(final FinalizerLoader... array) {
        while (0 < array.length) {
            final Class loadFinalizer = array[0].loadFinalizer();
            if (loadFinalizer != null) {
                return loadFinalizer;
            }
            int n = 0;
            ++n;
        }
        throw new AssertionError();
    }
    
    static Method getStartFinalizer(final Class clazz) {
        return clazz.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
    }
    
    static Logger access$000() {
        return FinalizableReferenceQueue.logger;
    }
    
    static {
        logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
        startFinalizer = getStartFinalizer(loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));
    }
    
    static class DirectLoader implements FinalizerLoader
    {
        @Override
        public Class loadFinalizer() {
            return Class.forName("com.google.common.base.internal.Finalizer");
        }
    }
    
    interface FinalizerLoader
    {
        Class loadFinalizer();
    }
    
    static class DecoupledLoader implements FinalizerLoader
    {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader.Loading Finalizer in the current class loader instead. As a result, you will not be ableto garbage collect this class loader. To support reclaiming this class loader, eitherresolve the underlying issue, or move Google Collections to your system class path.";
        
        @Override
        public Class loadFinalizer() {
            return this.newLoader(this.getBaseUrl()).loadClass("com.google.common.base.internal.Finalizer");
        }
        
        URL getBaseUrl() throws IOException {
            final String string = "com.google.common.base.internal.Finalizer".replace('.', '/') + ".class";
            final URL resource = this.getClass().getClassLoader().getResource(string);
            if (resource == null) {
                throw new FileNotFoundException(string);
            }
            final String string2 = resource.toString();
            if (!string2.endsWith(string)) {
                throw new IOException("Unsupported path style: " + string2);
            }
            return new URL(resource, string2.substring(0, string2.length() - string.length()));
        }
        
        URLClassLoader newLoader(final URL url) {
            return new URLClassLoader(new URL[] { url }, (ClassLoader)null);
        }
    }
    
    static class SystemLoader implements FinalizerLoader
    {
        @Override
        public Class loadFinalizer() {
            final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            if (systemClassLoader != null) {
                return systemClassLoader.loadClass("com.google.common.base.internal.Finalizer");
            }
            return null;
        }
    }
}
