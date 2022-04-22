package org.lwjgl.opengl;

import java.security.*;
import org.lwjgl.*;
import java.util.*;

public final class GLContext
{
    private static final ThreadLocal current_capabilities;
    private static CapabilitiesCacheEntry fast_path_cache;
    private static final ThreadLocal thread_cache_entries;
    private static final Map capability_cache;
    private static int gl_ref_count;
    private static boolean did_auto_load;
    
    public static ContextCapabilities getCapabilities() {
        final ContextCapabilities capabilitiesImpl = getCapabilitiesImpl();
        if (capabilitiesImpl == null) {
            throw new RuntimeException("No OpenGL context found in the current thread.");
        }
        return capabilitiesImpl;
    }
    
    private static ContextCapabilities getCapabilitiesImpl() {
        final CapabilitiesCacheEntry fast_path_cache = GLContext.fast_path_cache;
        if (fast_path_cache.owner == Thread.currentThread()) {
            return fast_path_cache.capabilities;
        }
        return getThreadLocalCapabilities();
    }
    
    static ContextCapabilities getCapabilities(final Object o) {
        return GLContext.capability_cache.get(o);
    }
    
    private static ContextCapabilities getThreadLocalCapabilities() {
        return GLContext.current_capabilities.get();
    }
    
    static void setCapabilities(final ContextCapabilities capabilities) {
        GLContext.current_capabilities.set(capabilities);
        CapabilitiesCacheEntry fast_path_cache = GLContext.thread_cache_entries.get();
        if (fast_path_cache == null) {
            fast_path_cache = new CapabilitiesCacheEntry(null);
            GLContext.thread_cache_entries.set(fast_path_cache);
        }
        fast_path_cache.owner = Thread.currentThread();
        fast_path_cache.capabilities = capabilities;
        GLContext.fast_path_cache = fast_path_cache;
    }
    
    static long getPlatformSpecificFunctionAddress(final String s, final String[] array, final String[] array2, final String s2) {
        final String s3 = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction() {
            public String run() {
                return System.getProperty("os.name");
            }
            
            public Object run() {
                return this.run();
            }
        });
        while (0 < array.length) {
            if (s3.startsWith(array[0])) {
                return getFunctionAddress(s2.replaceFirst(s, array2[0]));
            }
            int n = 0;
            ++n;
        }
        return 0L;
    }
    
    static long getFunctionAddress(final String[] array) {
        while (0 < array.length) {
            final long functionAddress = getFunctionAddress(array[0]);
            if (functionAddress != 0L) {
                return functionAddress;
            }
            int n = 0;
            ++n;
        }
        return 0L;
    }
    
    static long getFunctionAddress(final String s) {
        return ngetFunctionAddress(MemoryUtil.getAddress(MemoryUtil.encodeASCII(s)));
    }
    
    private static native long ngetFunctionAddress(final long p0);
    
    static int getSupportedExtensions(final Set set) {
        final String glGetString = GL11.glGetString(7938);
        if (glGetString == null) {
            throw new IllegalStateException("glGetString(GL_VERSION) returned null - possibly caused by missing current context.");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(glGetString, ". ");
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        Integer.parseInt(nextToken);
        Integer.parseInt(nextToken2);
        final int[][] array = { { 1, 2, 3, 4, 5 }, { 0, 1 }, { 0, 1, 2, 3 }, { 0, 1, 2, 3, 4, 5 } };
        while (0 <= array.length) {
            final int[] array2 = array[-1];
            while (0 < array2.length) {
                final int n = array2[0];
                if (n <= 0) {
                    set.add("OpenGL" + Integer.toString(0) + Integer.toString(n));
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        final String glGetString2 = GL11.glGetString(7939);
        if (glGetString2 == null) {
            throw new IllegalStateException("glGetString(GL_EXTENSIONS) returned null - is there a context current?");
        }
        final StringTokenizer stringTokenizer2 = new StringTokenizer(glGetString2);
        while (stringTokenizer2.hasMoreTokens()) {
            set.add(stringTokenizer2.nextToken());
        }
        return 0;
    }
    
    static void initNativeStubs(final Class clazz, final Set set, final String s) {
        resetNativeStubs(clazz);
        if (set.contains(s)) {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction(clazz) {
                final Class val$extension_class;
                
                public Object run() throws Exception {
                    this.val$extension_class.getDeclaredMethod("initNativeStubs", (Class[])new Class[0]).invoke(null, new Object[0]);
                    return null;
                }
            });
        }
    }
    
    public static synchronized void useContext(final Object o) throws LWJGLException {
        useContext(o, false);
    }
    
    public static synchronized void useContext(final Object o, final boolean b) throws LWJGLException {
        if (o == null) {
            setCapabilities(null);
            GLContext.did_auto_load;
            return;
        }
        if (GLContext.gl_ref_count == 0) {
            GLContext.did_auto_load = true;
        }
        final ContextCapabilities capabilities = GLContext.capability_cache.get(o);
        if (capabilities == null) {
            new ContextCapabilities(b);
            GLContext.capability_cache.put(o, getCapabilities());
        }
        else {
            setCapabilities(capabilities);
        }
    }
    
    public static synchronized void loadOpenGLLibrary() throws LWJGLException {
        GLContext.gl_ref_count;
        ++GLContext.gl_ref_count;
    }
    
    private static native void nLoadOpenGLLibrary() throws LWJGLException;
    
    public static synchronized void unloadOpenGLLibrary() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_1       
        //     4: isub           
        //     5: putstatic       org/lwjgl/opengl/GLContext.gl_ref_count:I
        //     8: getstatic       org/lwjgl/opengl/GLContext.gl_ref_count:I
        //    11: ifne            18
        //    14: invokestatic    org/lwjgl/LWJGLUtil.getPlatform:()I
        //    17: iconst_1       
        //    18: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0018 (coming from #0017).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static native void nUnloadOpenGLLibrary();
    
    static native void resetNativeStubs(final Class p0);
    
    static {
        current_capabilities = new ThreadLocal();
        GLContext.fast_path_cache = new CapabilitiesCacheEntry(null);
        thread_cache_entries = new ThreadLocal();
        capability_cache = new WeakHashMap();
    }
    
    private static final class CapabilitiesCacheEntry
    {
        Thread owner;
        ContextCapabilities capabilities;
        
        private CapabilitiesCacheEntry() {
        }
        
        CapabilitiesCacheEntry(final GLContext$1 privilegedAction) {
            this();
        }
    }
}
