package com.sun.jna;

import java.lang.ref.*;
import java.net.*;
import java.security.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import java.util.*;
import java.awt.*;

public final class Native
{
    private static final String VERSION = "3.4.0";
    private static final String VERSION_NATIVE = "3.4.0";
    private static String nativeLibraryPath;
    private static Map typeMappers;
    private static Map alignments;
    private static Map options;
    private static Map libraries;
    private static final Callback.UncaughtExceptionHandler DEFAULT_HANDLER;
    private static Callback.UncaughtExceptionHandler callbackExceptionHandler;
    public static final int POINTER_SIZE;
    public static final int LONG_SIZE;
    public static final int WCHAR_SIZE;
    public static final int SIZE_T_SIZE;
    private static final int TYPE_VOIDP = 0;
    private static final int TYPE_LONG = 1;
    private static final int TYPE_WCHAR_T = 2;
    private static final int TYPE_SIZE_T = 3;
    private static final int THREAD_NOCHANGE = 0;
    private static final int THREAD_DETACH = -1;
    private static final int THREAD_LEAVE_ATTACHED = -2;
    private static final Object finalizer;
    private static final ThreadLocal lastError;
    private static Map registeredClasses;
    private static Map registeredLibraries;
    private static Object unloader;
    static final int CB_HAS_INITIALIZER = 1;
    private static final int CVT_UNSUPPORTED = -1;
    private static final int CVT_DEFAULT = 0;
    private static final int CVT_POINTER = 1;
    private static final int CVT_STRING = 2;
    private static final int CVT_STRUCTURE = 3;
    private static final int CVT_STRUCTURE_BYVAL = 4;
    private static final int CVT_BUFFER = 5;
    private static final int CVT_ARRAY_BYTE = 6;
    private static final int CVT_ARRAY_SHORT = 7;
    private static final int CVT_ARRAY_CHAR = 8;
    private static final int CVT_ARRAY_INT = 9;
    private static final int CVT_ARRAY_LONG = 10;
    private static final int CVT_ARRAY_FLOAT = 11;
    private static final int CVT_ARRAY_DOUBLE = 12;
    private static final int CVT_ARRAY_BOOLEAN = 13;
    private static final int CVT_BOOLEAN = 14;
    private static final int CVT_CALLBACK = 15;
    private static final int CVT_FLOAT = 16;
    private static final int CVT_NATIVE_MAPPED = 17;
    private static final int CVT_WSTRING = 18;
    private static final int CVT_INTEGER_TYPE = 19;
    private static final int CVT_POINTER_TYPE = 20;
    private static final int CVT_TYPE_MAPPER = 21;
    static Class class$com$sun$jna$Library;
    static Class class$com$sun$jna$Callback;
    static Class class$com$sun$jna$TypeMapper;
    static Class class$com$sun$jna$Native;
    static Class class$java$lang$String;
    static Class class$java$lang$ClassLoader;
    static Class class$com$sun$jna$Structure;
    static Class class$com$sun$jna$Structure$ByReference;
    static Class class$com$sun$jna$NativeMapped;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Short;
    static Class class$java$lang$Character;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    static Class class$com$sun$jna$Structure$ByValue;
    static Class class$com$sun$jna$Pointer;
    static Class class$com$sun$jna$WString;
    static Class class$java$lang$Void;
    static Class class$com$sun$jna$IntegerType;
    static Class class$com$sun$jna$PointerType;
    static Class class$com$sun$jna$LastErrorException;
    static Class class$java$nio$Buffer;
    
    private static void dispose() {
        Native.nativeLibraryPath = null;
    }
    
    private static boolean deleteNativeLibrary(final String s) {
        final File file = new File(s);
        if (file.delete()) {
            return true;
        }
        markTemporaryFile(file);
        return false;
    }
    
    private Native() {
    }
    
    private static native void initIDs();
    
    public static synchronized native void setProtected(final boolean p0);
    
    public static synchronized native boolean isProtected();
    
    public static synchronized native void setPreserveLastError(final boolean p0);
    
    public static synchronized native boolean getPreserveLastError();
    
    public static long getWindowID(final Window window) throws HeadlessException {
        return AWT.getWindowID(window);
    }
    
    public static long getComponentID(final Component component) throws HeadlessException {
        return AWT.getComponentID(component);
    }
    
    public static Pointer getWindowPointer(final Window window) throws HeadlessException {
        return new Pointer(AWT.getWindowID(window));
    }
    
    public static Pointer getComponentPointer(final Component component) throws HeadlessException {
        return new Pointer(AWT.getComponentID(component));
    }
    
    static native long getWindowHandle0(final Component p0);
    
    public static Pointer getDirectBufferPointer(final Buffer buffer) {
        final long getDirectBufferPointer = _getDirectBufferPointer(buffer);
        return (getDirectBufferPointer == 0L) ? null : new Pointer(getDirectBufferPointer);
    }
    
    private static native long _getDirectBufferPointer(final Buffer p0);
    
    public static String toString(final byte[] array) {
        return toString(array, System.getProperty("jna.encoding"));
    }
    
    public static String toString(final byte[] array, final String s) {
        String substring = null;
        if (s != null) {
            substring = new String(array, s);
        }
        if (substring == null) {
            substring = new String(array);
        }
        final int index = substring.indexOf(0);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        return substring;
    }
    
    public static String toString(final char[] array) {
        String substring = new String(array);
        final int index = substring.indexOf(0);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        return substring;
    }
    
    public static Object loadLibrary(final Class clazz) {
        return loadLibrary(null, clazz);
    }
    
    public static Object loadLibrary(final Class clazz, final Map map) {
        return loadLibrary(null, clazz, map);
    }
    
    public static Object loadLibrary(final String s, final Class clazz) {
        return loadLibrary(s, clazz, Collections.EMPTY_MAP);
    }
    
    public static Object loadLibrary(final String s, final Class clazz, final Map map) {
        final Library library = (Library)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new Library.Handler(s, clazz, map));
        cacheOptions(clazz, map, library);
        return library;
    }
    
    private static void loadLibraryInstance(final Class clazz) {
        if (clazz != null && !Native.libraries.containsKey(clazz)) {
            final Field[] fields = clazz.getFields();
            while (0 < fields.length) {
                final Field field = fields[0];
                if (field.getType() == clazz && Modifier.isStatic(field.getModifiers())) {
                    Native.libraries.put(clazz, new WeakReference<Object>(field.get(null)));
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    static Class findEnclosingLibraryClass(Class callbackClass) {
        if (callbackClass == null) {
            return null;
        }
        // monitorenter(libraries = Native.libraries)
        if (Native.options.containsKey(callbackClass)) {
            // monitorexit(libraries)
            return callbackClass;
        }
        // monitorexit(libraries)
        if (((Native.class$com$sun$jna$Library == null) ? (Native.class$com$sun$jna$Library = class$("com.sun.jna.Library")) : Native.class$com$sun$jna$Library).isAssignableFrom(callbackClass)) {
            return callbackClass;
        }
        if (((Native.class$com$sun$jna$Callback == null) ? (Native.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Native.class$com$sun$jna$Callback).isAssignableFrom(callbackClass)) {
            callbackClass = CallbackReference.findCallbackClass(callbackClass);
        }
        final Class enclosingLibraryClass = findEnclosingLibraryClass(callbackClass.getDeclaringClass());
        if (enclosingLibraryClass != null) {
            return enclosingLibraryClass;
        }
        return findEnclosingLibraryClass(callbackClass.getSuperclass());
    }
    
    public static Map getLibraryOptions(final Class clazz) {
        // monitorenter(libraries = Native.libraries)
        Class enclosingLibraryClass = findEnclosingLibraryClass(clazz);
        if (enclosingLibraryClass != null) {
            loadLibraryInstance(enclosingLibraryClass);
        }
        else {
            enclosingLibraryClass = clazz;
        }
        if (!Native.options.containsKey(enclosingLibraryClass)) {
            final Field field = enclosingLibraryClass.getField("OPTIONS");
            field.setAccessible(true);
            Native.options.put(enclosingLibraryClass, field.get(null));
        }
        // monitorexit(libraries)
        return Native.options.get(enclosingLibraryClass);
    }
    
    public static TypeMapper getTypeMapper(final Class clazz) {
        // monitorenter(libraries = Native.libraries)
        Class enclosingLibraryClass = findEnclosingLibraryClass(clazz);
        if (enclosingLibraryClass != null) {
            loadLibraryInstance(enclosingLibraryClass);
        }
        else {
            enclosingLibraryClass = clazz;
        }
        if (!Native.typeMappers.containsKey(enclosingLibraryClass)) {
            final Field field = enclosingLibraryClass.getField("TYPE_MAPPER");
            field.setAccessible(true);
            Native.typeMappers.put(enclosingLibraryClass, field.get(null));
        }
        // monitorexit(libraries)
        return Native.typeMappers.get(enclosingLibraryClass);
    }
    
    public static int getStructureAlignment(final Class clazz) {
        // monitorenter(libraries = Native.libraries)
        Class enclosingLibraryClass = findEnclosingLibraryClass(clazz);
        if (enclosingLibraryClass != null) {
            loadLibraryInstance(enclosingLibraryClass);
        }
        else {
            enclosingLibraryClass = clazz;
        }
        if (!Native.alignments.containsKey(enclosingLibraryClass)) {
            final Field field = enclosingLibraryClass.getField("STRUCTURE_ALIGNMENT");
            field.setAccessible(true);
            Native.alignments.put(enclosingLibraryClass, field.get(null));
        }
        final Integer n = Native.alignments.get(enclosingLibraryClass);
        // monitorexit(libraries)
        return (n != null) ? n : 0;
    }
    
    static byte[] getBytes(final String s) {
        return getBytes(s, System.getProperty("jna.encoding"));
    }
    
    static byte[] getBytes(final String s, final String s2) throws UnsupportedEncodingException {
        if (s2 != null) {
            return s.getBytes(s2);
        }
        return s.getBytes();
    }
    
    public static byte[] toByteArray(final String s) {
        final byte[] bytes = getBytes(s);
        final byte[] array = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, array, 0, bytes.length);
        return array;
    }
    
    public static byte[] toByteArray(final String s, final String s2) throws UnsupportedEncodingException {
        final byte[] bytes = getBytes(s, s2);
        final byte[] array = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, array, 0, bytes.length);
        return array;
    }
    
    public static char[] toCharArray(final String s) {
        final char[] charArray = s.toCharArray();
        final char[] array = new char[charArray.length + 1];
        System.arraycopy(charArray, 0, array, 0, charArray.length);
        return array;
    }
    
    static String getNativeLibraryResourcePath(final int n, String lowerCase, final String s) {
        lowerCase = lowerCase.toLowerCase();
        if ("powerpc".equals(lowerCase)) {
            lowerCase = "ppc";
        }
        else if ("powerpc64".equals(lowerCase)) {
            lowerCase = "ppc64";
        }
        String s2 = null;
        switch (n) {
            case 2: {
                if ("i386".equals(lowerCase)) {
                    lowerCase = "x86";
                }
                s2 = "win32-" + lowerCase;
                break;
            }
            case 6: {
                s2 = "w32ce-" + lowerCase;
                break;
            }
            case 0: {
                s2 = "darwin";
                break;
            }
            case 1: {
                if ("x86".equals(lowerCase)) {
                    lowerCase = "i386";
                }
                else if ("x86_64".equals(lowerCase)) {
                    lowerCase = "amd64";
                }
                s2 = "linux-" + lowerCase;
                break;
            }
            case 3: {
                s2 = "sunos-" + lowerCase;
                break;
            }
            default: {
                String s3 = s.toLowerCase();
                if ("x86".equals(lowerCase)) {
                    lowerCase = "i386";
                }
                if ("x86_64".equals(lowerCase)) {
                    lowerCase = "amd64";
                }
                final int index = s3.indexOf(" ");
                if (index != -1) {
                    s3 = s3.substring(0, index);
                }
                s2 = s3 + "-" + lowerCase;
                break;
            }
        }
        return "/com/sun/jna/" + s2;
    }
    
    private static void loadNativeLibrary() {
        final String property = System.getProperty("jna.boot.library.name", "jnidispatch");
        final String property2 = System.getProperty("jna.boot.library.path");
        if (property2 != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property2, File.pathSeparator);
            while (stringTokenizer.hasMoreTokens()) {
                final File file = new File(new File(stringTokenizer.nextToken()), System.mapLibraryName(property));
                final String absolutePath = file.getAbsolutePath();
                if (file.exists()) {
                    System.load(absolutePath);
                    Native.nativeLibraryPath = absolutePath;
                    return;
                }
                if (!Platform.isMac()) {
                    continue;
                }
                String s;
                String s2;
                if (absolutePath.endsWith("dylib")) {
                    s = "dylib";
                    s2 = "jnilib";
                }
                else {
                    s = "jnilib";
                    s2 = "dylib";
                }
                final String string = absolutePath.substring(0, absolutePath.lastIndexOf(s)) + s2;
                if (new File(string).exists()) {
                    System.load(string);
                    Native.nativeLibraryPath = string;
                    return;
                }
            }
        }
        if (!Boolean.getBoolean("jna.nosys")) {
            System.loadLibrary(property);
            return;
        }
        if (!Boolean.getBoolean("jna.nounpack")) {
            return;
        }
        throw new UnsatisfiedLinkError("Native jnidispatch library not found");
    }
    
    private static void loadNativeLibraryFromJar() {
        String s = getNativeLibraryResourcePath(Platform.getOSType(), System.getProperty("os.arch"), System.getProperty("os.name")) + "/" + System.mapLibraryName("jnidispatch");
        URL url = ((Native.class$com$sun$jna$Native == null) ? (Native.class$com$sun$jna$Native = class$("com.sun.jna.Native")) : Native.class$com$sun$jna$Native).getResource(s);
        if (url == null && Platform.isMac() && s.endsWith(".dylib")) {
            s = s.substring(0, s.lastIndexOf(".dylib")) + ".jnilib";
            url = ((Native.class$com$sun$jna$Native == null) ? (Native.class$com$sun$jna$Native = class$("com.sun.jna.Native")) : Native.class$com$sun$jna$Native).getResource(s);
        }
        if (url == null) {
            throw new UnsatisfiedLinkError("jnidispatch (" + s + ") not found in resource path");
        }
        File tempFile;
        if (url.getProtocol().toLowerCase().equals("file")) {
            tempFile = new File(new URI(url.toString()));
            if (!tempFile.exists()) {
                throw new Error("File URL " + url + " could not be properly decoded");
            }
        }
        else {
            final InputStream resourceAsStream = ((Native.class$com$sun$jna$Native == null) ? (Native.class$com$sun$jna$Native = class$("com.sun.jna.Native")) : Native.class$com$sun$jna$Native).getResourceAsStream(s);
            if (resourceAsStream == null) {
                throw new Error("Can't obtain jnidispatch InputStream");
            }
            tempFile = File.createTempFile("jna", Platform.isWindows() ? ".dll" : null, getTempDir());
            tempFile.deleteOnExit();
            final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            final byte[] array = new byte[1024];
            int read;
            while ((read = resourceAsStream.read(array, 0, array.length)) > 0) {
                fileOutputStream.write(array, 0, read);
            }
            resourceAsStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        System.load(tempFile.getAbsolutePath());
        Native.nativeLibraryPath = tempFile.getAbsolutePath();
        if (true) {
            deleteNativeLibrary(tempFile.getAbsolutePath());
        }
    }
    
    private static native int sizeof(final int p0);
    
    private static native String getNativeVersion();
    
    private static native String getAPIChecksum();
    
    public static int getLastError() {
        return Native.lastError.get();
    }
    
    public static native void setLastError(final int p0);
    
    static void updateLastError(final int n) {
        Native.lastError.set(new Integer(n));
    }
    
    public static Library synchronizedLibrary(final Library library) {
        final Class<? extends Library> class1 = library.getClass();
        if (!Proxy.isProxyClass(class1)) {
            throw new IllegalArgumentException("Library must be a proxy class");
        }
        final InvocationHandler invocationHandler = Proxy.getInvocationHandler(library);
        if (!(invocationHandler instanceof Library.Handler)) {
            throw new IllegalArgumentException("Unrecognized proxy handler: " + invocationHandler);
        }
        return (Library)Proxy.newProxyInstance(class1.getClassLoader(), class1.getInterfaces(), new InvocationHandler((Library.Handler)invocationHandler, library) {
            private final Library.Handler val$handler;
            private final Library val$library;
            
            public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
                // monitorenter(nativeLibrary = this.val$handler.getNativeLibrary())
                // monitorexit(nativeLibrary)
                return this.val$handler.invoke(this.val$library, method, array);
            }
        });
    }
    
    public static String getWebStartLibraryPath(final String s) {
        if (System.getProperty("javawebstart.version") == null) {
            return null;
        }
        final String s2 = (String)AccessController.doPrivileged((PrivilegedAction<Method>)new PrivilegedAction() {
            public Object run() {
                final Method declaredMethod = ((Native.class$java$lang$ClassLoader == null) ? (Native.class$java$lang$ClassLoader = Native.class$("java.lang.ClassLoader")) : Native.class$java$lang$ClassLoader).getDeclaredMethod("findLibrary", (Native.class$java$lang$String == null) ? (Native.class$java$lang$String = Native.class$("java.lang.String")) : Native.class$java$lang$String);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            }
        }).invoke(((Native.class$com$sun$jna$Native == null) ? (Native.class$com$sun$jna$Native = class$("com.sun.jna.Native")) : Native.class$com$sun$jna$Native).getClassLoader(), s);
        if (s2 != null) {
            return new File(s2).getParent();
        }
        return null;
    }
    
    static void markTemporaryFile(final File file) {
        new File(file.getParentFile(), file.getName() + ".x").createNewFile();
    }
    
    static File getTempDir() {
        final File file = new File(System.getProperty("java.io.tmpdir"));
        final File file2 = new File(file, "jna");
        file2.mkdirs();
        return file2.exists() ? file2 : file;
    }
    
    static void removeTemporaryFiles() {
        final File[] listFiles = getTempDir().listFiles(new FilenameFilter() {
            public boolean accept(final File file, final String s) {
                return s.endsWith(".x") && s.indexOf("jna") != -1;
            }
        });
        while (listFiles != null && 0 < listFiles.length) {
            final File file = listFiles[0];
            final String name = file.getName();
            final File file2 = new File(file.getParentFile(), name.substring(0, name.length() - 2));
            if (!file2.exists() || file2.delete()) {
                file.delete();
            }
            int n = 0;
            ++n;
        }
    }
    
    public static int getNativeSize(final Class clazz, Object instance) {
        if (clazz.isArray()) {
            final int length = Array.getLength(instance);
            if (length > 0) {
                return length * getNativeSize(clazz.getComponentType(), Array.get(instance, 0));
            }
            throw new IllegalArgumentException("Arrays of length zero not allowed: " + clazz);
        }
        else {
            if (((Native.class$com$sun$jna$Structure == null) ? (Native.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Native.class$com$sun$jna$Structure).isAssignableFrom(clazz) && !((Native.class$com$sun$jna$Structure$ByReference == null) ? (Native.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Native.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
                if (instance == null) {
                    instance = Structure.newInstance(clazz);
                }
                return ((Structure)instance).size();
            }
            return getNativeSize(clazz);
        }
    }
    
    public static int getNativeSize(Class nativeType) {
        if (((Native.class$com$sun$jna$NativeMapped == null) ? (Native.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Native.class$com$sun$jna$NativeMapped).isAssignableFrom(nativeType)) {
            nativeType = NativeMappedConverter.getInstance(nativeType).nativeType();
        }
        if (nativeType == Boolean.TYPE || nativeType == ((Native.class$java$lang$Boolean == null) ? (Native.class$java$lang$Boolean = class$("java.lang.Boolean")) : Native.class$java$lang$Boolean)) {
            return 4;
        }
        if (nativeType == Byte.TYPE || nativeType == ((Native.class$java$lang$Byte == null) ? (Native.class$java$lang$Byte = class$("java.lang.Byte")) : Native.class$java$lang$Byte)) {
            return 1;
        }
        if (nativeType == Short.TYPE || nativeType == ((Native.class$java$lang$Short == null) ? (Native.class$java$lang$Short = class$("java.lang.Short")) : Native.class$java$lang$Short)) {
            return 2;
        }
        if (nativeType == Character.TYPE || nativeType == ((Native.class$java$lang$Character == null) ? (Native.class$java$lang$Character = class$("java.lang.Character")) : Native.class$java$lang$Character)) {
            return Native.WCHAR_SIZE;
        }
        if (nativeType == Integer.TYPE || nativeType == ((Native.class$java$lang$Integer == null) ? (Native.class$java$lang$Integer = class$("java.lang.Integer")) : Native.class$java$lang$Integer)) {
            return 4;
        }
        if (nativeType == Long.TYPE || nativeType == ((Native.class$java$lang$Long == null) ? (Native.class$java$lang$Long = class$("java.lang.Long")) : Native.class$java$lang$Long)) {
            return 8;
        }
        if (nativeType == Float.TYPE || nativeType == ((Native.class$java$lang$Float == null) ? (Native.class$java$lang$Float = class$("java.lang.Float")) : Native.class$java$lang$Float)) {
            return 4;
        }
        if (nativeType == Double.TYPE || nativeType == ((Native.class$java$lang$Double == null) ? (Native.class$java$lang$Double = class$("java.lang.Double")) : Native.class$java$lang$Double)) {
            return 8;
        }
        if (((Native.class$com$sun$jna$Structure == null) ? (Native.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Native.class$com$sun$jna$Structure).isAssignableFrom(nativeType)) {
            if (((Native.class$com$sun$jna$Structure$ByValue == null) ? (Native.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : Native.class$com$sun$jna$Structure$ByValue).isAssignableFrom(nativeType)) {
                return Structure.newInstance(nativeType).size();
            }
            return Native.POINTER_SIZE;
        }
        else {
            if (((Native.class$com$sun$jna$Pointer == null) ? (Native.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Native.class$com$sun$jna$Pointer).isAssignableFrom(nativeType) || (Platform.HAS_BUFFERS && Buffers.isBuffer(nativeType)) || ((Native.class$com$sun$jna$Callback == null) ? (Native.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Native.class$com$sun$jna$Callback).isAssignableFrom(nativeType) || ((Native.class$java$lang$String == null) ? (Native.class$java$lang$String = class$("java.lang.String")) : Native.class$java$lang$String) == nativeType || ((Native.class$com$sun$jna$WString == null) ? (Native.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Native.class$com$sun$jna$WString) == nativeType) {
                return Native.POINTER_SIZE;
            }
            throw new IllegalArgumentException("Native size for type \"" + nativeType.getName() + "\" is unknown");
        }
    }
    
    public static boolean isSupportedNativeType(final Class clazz) {
        return ((Native.class$com$sun$jna$Structure == null) ? (Native.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Native.class$com$sun$jna$Structure).isAssignableFrom(clazz) || getNativeSize(clazz) != 0;
    }
    
    public static void setCallbackExceptionHandler(final Callback.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Native.callbackExceptionHandler = ((uncaughtExceptionHandler == null) ? Native.DEFAULT_HANDLER : uncaughtExceptionHandler);
    }
    
    public static Callback.UncaughtExceptionHandler getCallbackExceptionHandler() {
        return Native.callbackExceptionHandler;
    }
    
    public static void register(final String s) {
        register(getNativeClass(getCallingClass()), NativeLibrary.getInstance(s));
    }
    
    public static void register(final NativeLibrary nativeLibrary) {
        register(getNativeClass(getCallingClass()), nativeLibrary);
    }
    
    static Class getNativeClass(final Class clazz) {
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            if ((declaredMethods[0].getModifiers() & 0x100) != 0x0) {
                return clazz;
            }
            int lastIndex = 0;
            ++lastIndex;
        }
        int lastIndex = clazz.getName().lastIndexOf("$");
        if (0 != -1) {
            return getNativeClass(Class.forName(clazz.getName().substring(0, 0), true, clazz.getClassLoader()));
        }
        throw new IllegalArgumentException("Can't determine class with native methods from the current context (" + clazz + ")");
    }
    
    static Class getCallingClass() {
        final Class[] classContext = new SecurityManager() {
            public Class[] getClassContext() {
                return super.getClassContext();
            }
        }.getClassContext();
        if (classContext.length < 4) {
            throw new IllegalStateException("This method must be called from the static initializer of a class");
        }
        return classContext[3];
    }
    
    public static void setCallbackThreadInitializer(final Callback callback, final CallbackThreadInitializer callbackThreadInitializer) {
        CallbackReference.setCallbackThreadInitializer(callback, callbackThreadInitializer);
    }
    
    public static void unregister() {
        unregister(getNativeClass(getCallingClass()));
    }
    
    public static void unregister(final Class clazz) {
        // monitorenter(registeredClasses = Native.registeredClasses)
        if (Native.registeredClasses.containsKey(clazz)) {
            unregister(clazz, Native.registeredClasses.get(clazz));
            Native.registeredClasses.remove(clazz);
            Native.registeredLibraries.remove(clazz);
        }
    }
    // monitorexit(registeredClasses)
    
    private static native void unregister(final Class p0, final long[] p1);
    
    private static String getSignature(final Class clazz) {
        if (clazz.isArray()) {
            return "[" + getSignature(clazz.getComponentType());
        }
        if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                return "V";
            }
            if (clazz == Boolean.TYPE) {
                return "Z";
            }
            if (clazz == Byte.TYPE) {
                return "B";
            }
            if (clazz == Short.TYPE) {
                return "S";
            }
            if (clazz == Character.TYPE) {
                return "C";
            }
            if (clazz == Integer.TYPE) {
                return "I";
            }
            if (clazz == Long.TYPE) {
                return "J";
            }
            if (clazz == Float.TYPE) {
                return "F";
            }
            if (clazz == Double.TYPE) {
                return "D";
            }
        }
        return "L" + replace(".", "/", clazz.getName()) + ";";
    }
    
    static String replace(final String s, final String s2, String substring) {
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final int index = substring.indexOf(s);
            if (index == -1) {
                break;
            }
            sb.append(substring.substring(0, index));
            sb.append(s2);
            substring = substring.substring(index + s.length());
        }
        sb.append(substring);
        return sb.toString();
    }
    
    private static int getConversion(Class s, final TypeMapper typeMapper) {
        if (s == ((Native.class$java$lang$Boolean == null) ? (Native.class$java$lang$Boolean = class$("java.lang.Boolean")) : Native.class$java$lang$Boolean)) {
            s = Boolean.TYPE;
        }
        else if (s == ((Native.class$java$lang$Byte == null) ? (Native.class$java$lang$Byte = class$("java.lang.Byte")) : Native.class$java$lang$Byte)) {
            s = Byte.TYPE;
        }
        else if (s == ((Native.class$java$lang$Short == null) ? (Native.class$java$lang$Short = class$("java.lang.Short")) : Native.class$java$lang$Short)) {
            s = Short.TYPE;
        }
        else if (s == ((Native.class$java$lang$Character == null) ? (Native.class$java$lang$Character = class$("java.lang.Character")) : Native.class$java$lang$Character)) {
            s = Character.TYPE;
        }
        else if (s == ((Native.class$java$lang$Integer == null) ? (Native.class$java$lang$Integer = class$("java.lang.Integer")) : Native.class$java$lang$Integer)) {
            s = Integer.TYPE;
        }
        else if (s == ((Native.class$java$lang$Long == null) ? (Native.class$java$lang$Long = class$("java.lang.Long")) : Native.class$java$lang$Long)) {
            s = Long.TYPE;
        }
        else if (s == ((Native.class$java$lang$Float == null) ? (Native.class$java$lang$Float = class$("java.lang.Float")) : Native.class$java$lang$Float)) {
            s = Float.TYPE;
        }
        else if (s == ((Native.class$java$lang$Double == null) ? (Native.class$java$lang$Double = class$("java.lang.Double")) : Native.class$java$lang$Double)) {
            s = Double.TYPE;
        }
        else if (s == ((Native.class$java$lang$Void == null) ? (Native.class$java$lang$Void = class$("java.lang.Void")) : Native.class$java$lang$Void)) {
            s = Void.TYPE;
        }
        if (typeMapper != null && (typeMapper.getFromNativeConverter((Class)s) != null || typeMapper.getToNativeConverter((Class)s) != null)) {
            return 21;
        }
        if (((Native.class$com$sun$jna$Pointer == null) ? (Native.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Native.class$com$sun$jna$Pointer).isAssignableFrom((Class)s)) {
            return 1;
        }
        if (((Native.class$java$lang$String == null) ? (Native.class$java$lang$String = class$("java.lang.String")) : Native.class$java$lang$String) == s) {
            return 2;
        }
        if (((Native.class$com$sun$jna$WString == null) ? (Native.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Native.class$com$sun$jna$WString).isAssignableFrom((Class)s)) {
            return 18;
        }
        if (Platform.HAS_BUFFERS && Buffers.isBuffer((Class)s)) {
            return 5;
        }
        if (((Native.class$com$sun$jna$Structure == null) ? (Native.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Native.class$com$sun$jna$Structure).isAssignableFrom((Class)s)) {
            if (((Native.class$com$sun$jna$Structure$ByValue == null) ? (Native.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : Native.class$com$sun$jna$Structure$ByValue).isAssignableFrom((Class)s)) {
                return 4;
            }
            return 3;
        }
        else {
            if (((Class)s).isArray()) {
                switch (((Class)s).getName().charAt(1)) {
                    case 'Z': {
                        return 13;
                    }
                    case 'B': {
                        return 6;
                    }
                    case 'S': {
                        return 7;
                    }
                    case 'C': {
                        return 8;
                    }
                    case 'I': {
                        return 9;
                    }
                    case 'J': {
                        return 10;
                    }
                    case 'F': {
                        return 11;
                    }
                    case 'D': {
                        return 12;
                    }
                }
            }
            if (((Class)s).isPrimitive()) {
                return (s == Boolean.TYPE) ? 14 : 0;
            }
            if (((Native.class$com$sun$jna$Callback == null) ? (Native.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Native.class$com$sun$jna$Callback).isAssignableFrom((Class)s)) {
                return 15;
            }
            if (((Native.class$com$sun$jna$IntegerType == null) ? (Native.class$com$sun$jna$IntegerType = class$("com.sun.jna.IntegerType")) : Native.class$com$sun$jna$IntegerType).isAssignableFrom((Class)s)) {
                return 19;
            }
            if (((Native.class$com$sun$jna$PointerType == null) ? (Native.class$com$sun$jna$PointerType = class$("com.sun.jna.PointerType")) : Native.class$com$sun$jna$PointerType).isAssignableFrom((Class)s)) {
                return 20;
            }
            if (((Native.class$com$sun$jna$NativeMapped == null) ? (Native.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Native.class$com$sun$jna$NativeMapped).isAssignableFrom((Class)s)) {
                return 17;
            }
            return -1;
        }
    }
    
    public static void register(final Class p0, final NativeLibrary p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/Class.getDeclaredMethods:()[Ljava/lang/reflect/Method;
        //     4: astore_2       
        //     5: new             Ljava/util/ArrayList;
        //     8: dup            
        //     9: invokespecial   java/util/ArrayList.<init>:()V
        //    12: astore_3       
        //    13: aload_1        
        //    14: invokevirtual   com/sun/jna/NativeLibrary.getOptions:()Ljava/util/Map;
        //    17: ldc_w           "type-mapper"
        //    20: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    25: checkcast       Lcom/sun/jna/TypeMapper;
        //    28: astore          4
        //    30: iconst_0       
        //    31: aload_2        
        //    32: arraylength    
        //    33: if_icmpge       65
        //    36: aload_2        
        //    37: iconst_0       
        //    38: aaload         
        //    39: invokevirtual   java/lang/reflect/Method.getModifiers:()I
        //    42: sipush          256
        //    45: iand           
        //    46: ifeq            59
        //    49: aload_3        
        //    50: aload_2        
        //    51: iconst_0       
        //    52: aaload         
        //    53: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    58: pop            
        //    59: iinc            5, 1
        //    62: goto            30
        //    65: aload_3        
        //    66: invokeinterface java/util/List.size:()I
        //    71: newarray        J
        //    73: astore          5
        //    75: iconst_0       
        //    76: aload           5
        //    78: arraylength    
        //    79: if_icmpge       1173
        //    82: aload_3        
        //    83: iconst_0       
        //    84: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    89: checkcast       Ljava/lang/reflect/Method;
        //    92: astore          7
        //    94: ldc_w           "("
        //    97: astore          8
        //    99: aload           7
        //   101: invokevirtual   java/lang/reflect/Method.getReturnType:()Ljava/lang/Class;
        //   104: astore          9
        //   106: aload           7
        //   108: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
        //   111: astore          14
        //   113: aload           14
        //   115: arraylength    
        //   116: newarray        J
        //   118: astore          15
        //   120: aload           14
        //   122: arraylength    
        //   123: newarray        J
        //   125: astore          16
        //   127: aload           14
        //   129: arraylength    
        //   130: newarray        I
        //   132: astore          17
        //   134: aload           14
        //   136: arraylength    
        //   137: anewarray       Lcom/sun/jna/ToNativeConverter;
        //   140: astore          18
        //   142: aconst_null    
        //   143: astore          19
        //   145: aload           9
        //   147: aload           4
        //   149: invokestatic    com/sun/jna/Native.getConversion:(Ljava/lang/Class;Lcom/sun/jna/TypeMapper;)I
        //   152: istore          20
        //   154: iload           20
        //   156: tableswitch {
        //               -2: 264
        //               -1: 484
        //                0: 484
        //                1: 484
        //                2: 405
        //                3: 441
        //                4: 484
        //                5: 484
        //                6: 484
        //                7: 484
        //                8: 484
        //                9: 484
        //               10: 484
        //               11: 484
        //               12: 484
        //               13: 484
        //               14: 484
        //               15: 484
        //               16: 356
        //               17: 484
        //               18: 356
        //               19: 356
        //               20: 317
        //          default: 484
        //        }
        //   264: new             Ljava/lang/IllegalArgumentException;
        //   267: dup            
        //   268: new             Ljava/lang/StringBuffer;
        //   271: dup            
        //   272: invokespecial   java/lang/StringBuffer.<init>:()V
        //   275: aload           9
        //   277: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   280: ldc_w           " is not a supported return type (in method "
        //   283: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   286: aload           7
        //   288: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //   291: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   294: ldc_w           " in "
        //   297: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   300: aload_0        
        //   301: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   304: ldc_w           ")"
        //   307: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   310: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   313: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   316: athrow         
        //   317: aload           4
        //   319: aload           9
        //   321: invokeinterface com/sun/jna/TypeMapper.getFromNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/FromNativeConverter;
        //   326: astore          19
        //   328: aload           9
        //   330: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   333: getfield        com/sun/jna/Pointer.peer:J
        //   336: lstore          12
        //   338: aload           19
        //   340: invokeinterface com/sun/jna/FromNativeConverter.nativeType:()Ljava/lang/Class;
        //   345: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   348: getfield        com/sun/jna/Pointer.peer:J
        //   351: lstore          10
        //   353: goto            497
        //   356: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   359: ifnonnull       375
        //   362: ldc_w           "com.sun.jna.Pointer"
        //   365: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   368: dup            
        //   369: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   372: goto            378
        //   375: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   378: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   381: getfield        com/sun/jna/Pointer.peer:J
        //   384: lstore          12
        //   386: aload           9
        //   388: invokestatic    com/sun/jna/NativeMappedConverter.getInstance:(Ljava/lang/Class;)Lcom/sun/jna/NativeMappedConverter;
        //   391: invokevirtual   com/sun/jna/NativeMappedConverter.nativeType:()Ljava/lang/Class;
        //   394: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   397: getfield        com/sun/jna/Pointer.peer:J
        //   400: lstore          10
        //   402: goto            497
        //   405: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   408: ifnonnull       424
        //   411: ldc_w           "com.sun.jna.Pointer"
        //   414: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   417: dup            
        //   418: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   421: goto            427
        //   424: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   427: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   430: getfield        com/sun/jna/Pointer.peer:J
        //   433: dup2           
        //   434: lstore          10
        //   436: lstore          12
        //   438: goto            497
        //   441: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   444: ifnonnull       460
        //   447: ldc_w           "com.sun.jna.Pointer"
        //   450: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   453: dup            
        //   454: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   457: goto            463
        //   460: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   463: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   466: getfield        com/sun/jna/Pointer.peer:J
        //   469: lstore          12
        //   471: aload           9
        //   473: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   476: getfield        com/sun/jna/Pointer.peer:J
        //   479: lstore          10
        //   481: goto            497
        //   484: aload           9
        //   486: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   489: getfield        com/sun/jna/Pointer.peer:J
        //   492: dup2           
        //   493: lstore          10
        //   495: lstore          12
        //   497: iconst_0       
        //   498: aload           14
        //   500: arraylength    
        //   501: if_icmpge       911
        //   504: aload           14
        //   506: iconst_0       
        //   507: aaload         
        //   508: astore          23
        //   510: new             Ljava/lang/StringBuffer;
        //   513: dup            
        //   514: invokespecial   java/lang/StringBuffer.<init>:()V
        //   517: aload           8
        //   519: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   522: aload           23
        //   524: invokestatic    com/sun/jna/Native.getSignature:(Ljava/lang/Class;)Ljava/lang/String;
        //   527: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   530: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   533: astore          8
        //   535: aload           17
        //   537: iconst_0       
        //   538: aload           23
        //   540: aload           4
        //   542: invokestatic    com/sun/jna/Native.getConversion:(Ljava/lang/Class;Lcom/sun/jna/TypeMapper;)I
        //   545: iastore        
        //   546: aload           17
        //   548: iconst_0       
        //   549: iaload         
        //   550: iconst_m1      
        //   551: if_icmpne       607
        //   554: new             Ljava/lang/IllegalArgumentException;
        //   557: dup            
        //   558: new             Ljava/lang/StringBuffer;
        //   561: dup            
        //   562: invokespecial   java/lang/StringBuffer.<init>:()V
        //   565: aload           23
        //   567: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   570: ldc_w           " is not a supported argument type (in method "
        //   573: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   576: aload           7
        //   578: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //   581: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   584: ldc_w           " in "
        //   587: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   590: aload_0        
        //   591: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   594: ldc_w           ")"
        //   597: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   600: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   603: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   606: athrow         
        //   607: aload           17
        //   609: iconst_0       
        //   610: iaload         
        //   611: bipush          17
        //   613: if_icmpeq       625
        //   616: aload           17
        //   618: iconst_0       
        //   619: iaload         
        //   620: bipush          19
        //   622: if_icmpne       638
        //   625: aload           23
        //   627: invokestatic    com/sun/jna/NativeMappedConverter.getInstance:(Ljava/lang/Class;)Lcom/sun/jna/NativeMappedConverter;
        //   630: invokevirtual   com/sun/jna/NativeMappedConverter.nativeType:()Ljava/lang/Class;
        //   633: astore          23
        //   635: goto            660
        //   638: aload           17
        //   640: iconst_0       
        //   641: iaload         
        //   642: bipush          21
        //   644: if_icmpne       660
        //   647: aload           18
        //   649: iconst_0       
        //   650: aload           4
        //   652: aload           23
        //   654: invokeinterface com/sun/jna/TypeMapper.getToNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/ToNativeConverter;
        //   659: aastore        
        //   660: aload           17
        //   662: iconst_0       
        //   663: iaload         
        //   664: lookupswitch {
        //                0: 848
        //                4: 724
        //               17: 724
        //               19: 724
        //               20: 724
        //               21: 771
        //          default: 868
        //        }
        //   724: aload           15
        //   726: iconst_0       
        //   727: aload           23
        //   729: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   732: getfield        com/sun/jna/Pointer.peer:J
        //   735: lastore        
        //   736: aload           16
        //   738: iconst_0       
        //   739: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   742: ifnonnull       758
        //   745: ldc_w           "com.sun.jna.Pointer"
        //   748: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   751: dup            
        //   752: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   755: goto            761
        //   758: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   761: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   764: getfield        com/sun/jna/Pointer.peer:J
        //   767: lastore        
        //   768: goto            905
        //   771: aload           23
        //   773: invokevirtual   java/lang/Class.isPrimitive:()Z
        //   776: ifeq            794
        //   779: aload           16
        //   781: iconst_0       
        //   782: aload           23
        //   784: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   787: getfield        com/sun/jna/Pointer.peer:J
        //   790: lastore        
        //   791: goto            826
        //   794: aload           16
        //   796: iconst_0       
        //   797: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   800: ifnonnull       816
        //   803: ldc_w           "com.sun.jna.Pointer"
        //   806: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   809: dup            
        //   810: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   813: goto            819
        //   816: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   819: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   822: getfield        com/sun/jna/Pointer.peer:J
        //   825: lastore        
        //   826: aload           15
        //   828: iconst_0       
        //   829: aload           18
        //   831: iconst_0       
        //   832: aaload         
        //   833: invokeinterface com/sun/jna/ToNativeConverter.nativeType:()Ljava/lang/Class;
        //   838: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   841: getfield        com/sun/jna/Pointer.peer:J
        //   844: lastore        
        //   845: goto            905
        //   848: aload           16
        //   850: iconst_0       
        //   851: aload           15
        //   853: iconst_0       
        //   854: aload           23
        //   856: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   859: getfield        com/sun/jna/Pointer.peer:J
        //   862: dup2_x2        
        //   863: lastore        
        //   864: lastore        
        //   865: goto            905
        //   868: aload           16
        //   870: iconst_0       
        //   871: aload           15
        //   873: iconst_0       
        //   874: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   877: ifnonnull       893
        //   880: ldc_w           "com.sun.jna.Pointer"
        //   883: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   886: dup            
        //   887: putstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   890: goto            896
        //   893: getstatic       com/sun/jna/Native.class$com$sun$jna$Pointer:Ljava/lang/Class;
        //   896: invokestatic    com/sun/jna/Structure$FFIType.get:(Ljava/lang/Object;)Lcom/sun/jna/Pointer;
        //   899: getfield        com/sun/jna/Pointer.peer:J
        //   902: dup2_x2        
        //   903: lastore        
        //   904: lastore        
        //   905: iinc            22, 1
        //   908: goto            497
        //   911: new             Ljava/lang/StringBuffer;
        //   914: dup            
        //   915: invokespecial   java/lang/StringBuffer.<init>:()V
        //   918: aload           8
        //   920: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   923: ldc_w           ")"
        //   926: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   929: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   932: astore          8
        //   934: new             Ljava/lang/StringBuffer;
        //   937: dup            
        //   938: invokespecial   java/lang/StringBuffer.<init>:()V
        //   941: aload           8
        //   943: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   946: aload           9
        //   948: invokestatic    com/sun/jna/Native.getSignature:(Ljava/lang/Class;)Ljava/lang/String;
        //   951: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   954: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   957: astore          8
        //   959: aload           7
        //   961: invokevirtual   java/lang/reflect/Method.getExceptionTypes:()[Ljava/lang/Class;
        //   964: astore          22
        //   966: iconst_0       
        //   967: aload           22
        //   969: arraylength    
        //   970: if_icmpge       1014
        //   973: getstatic       com/sun/jna/Native.class$com$sun$jna$LastErrorException:Ljava/lang/Class;
        //   976: ifnonnull       992
        //   979: ldc_w           "com.sun.jna.LastErrorException"
        //   982: invokestatic    com/sun/jna/Native.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //   985: dup            
        //   986: putstatic       com/sun/jna/Native.class$com$sun$jna$LastErrorException:Ljava/lang/Class;
        //   989: goto            995
        //   992: getstatic       com/sun/jna/Native.class$com$sun$jna$LastErrorException:Ljava/lang/Class;
        //   995: aload           22
        //   997: iconst_0       
        //   998: aaload         
        //   999: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //  1002: ifeq            1008
        //  1005: goto            1014
        //  1008: iinc            23, 1
        //  1011: goto            966
        //  1014: aload           7
        //  1016: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1019: astore          23
        //  1021: aload_1        
        //  1022: invokevirtual   com/sun/jna/NativeLibrary.getOptions:()Ljava/util/Map;
        //  1025: ldc_w           "function-mapper"
        //  1028: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1033: checkcast       Lcom/sun/jna/FunctionMapper;
        //  1036: astore          24
        //  1038: aload           24
        //  1040: ifnull          1055
        //  1043: aload           24
        //  1045: aload_1        
        //  1046: aload           7
        //  1048: invokeinterface com/sun/jna/FunctionMapper.getFunctionName:(Lcom/sun/jna/NativeLibrary;Ljava/lang/reflect/Method;)Ljava/lang/String;
        //  1053: astore          23
        //  1055: aload_1        
        //  1056: aload           23
        //  1058: aload           7
        //  1060: invokevirtual   com/sun/jna/NativeLibrary.getFunction:(Ljava/lang/String;Ljava/lang/reflect/Method;)Lcom/sun/jna/Function;
        //  1063: astore          25
        //  1065: aload           5
        //  1067: iconst_0       
        //  1068: aload_0        
        //  1069: aload           7
        //  1071: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1074: aload           8
        //  1076: aload           17
        //  1078: aload           16
        //  1080: aload           15
        //  1082: iload           20
        //  1084: lload           12
        //  1086: lload           10
        //  1088: aload           9
        //  1090: aload           25
        //  1092: getfield        com/sun/jna/Function.peer:J
        //  1095: aload           25
        //  1097: invokevirtual   com/sun/jna/Function.getCallingConvention:()I
        //  1100: iconst_1       
        //  1101: aload           18
        //  1103: aload           19
        //  1105: invokestatic    com/sun/jna/Native.registerMethod:(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;[I[J[JIJJLjava/lang/Class;JIZ[Lcom/sun/jna/ToNativeConverter;Lcom/sun/jna/FromNativeConverter;)J
        //  1108: lastore        
        //  1109: goto            1167
        //  1112: astore          26
        //  1114: new             Ljava/lang/UnsatisfiedLinkError;
        //  1117: dup            
        //  1118: new             Ljava/lang/StringBuffer;
        //  1121: dup            
        //  1122: invokespecial   java/lang/StringBuffer.<init>:()V
        //  1125: ldc_w           "No method "
        //  1128: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //  1131: aload           7
        //  1133: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1136: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //  1139: ldc_w           " with signature "
        //  1142: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //  1145: aload           8
        //  1147: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //  1150: ldc_w           " in "
        //  1153: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //  1156: aload_0        
        //  1157: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //  1160: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //  1163: invokespecial   java/lang/UnsatisfiedLinkError.<init>:(Ljava/lang/String;)V
        //  1166: athrow         
        //  1167: iinc            6, 1
        //  1170: goto            75
        //  1173: getstatic       com/sun/jna/Native.registeredClasses:Ljava/util/Map;
        //  1176: dup            
        //  1177: astore          6
        //  1179: monitorenter   
        //  1180: getstatic       com/sun/jna/Native.registeredClasses:Ljava/util/Map;
        //  1183: aload_0        
        //  1184: aload           5
        //  1186: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //  1191: pop            
        //  1192: getstatic       com/sun/jna/Native.registeredLibraries:Ljava/util/Map;
        //  1195: aload_0        
        //  1196: aload_1        
        //  1197: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //  1202: pop            
        //  1203: aload           6
        //  1205: monitorexit    
        //  1206: goto            1217
        //  1209: astore          27
        //  1211: aload           6
        //  1213: monitorexit    
        //  1214: aload           27
        //  1216: athrow         
        //  1217: aload_0        
        //  1218: aload_1        
        //  1219: invokevirtual   com/sun/jna/NativeLibrary.getOptions:()Ljava/util/Map;
        //  1222: aconst_null    
        //  1223: invokestatic    com/sun/jna/Native.cacheOptions:(Ljava/lang/Class;Ljava/util/Map;Ljava/lang/Object;)V
        //  1226: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private static void cacheOptions(final Class clazz, final Map map, final Object o) {
        // monitorenter(libraries = Native.libraries)
        if (!map.isEmpty()) {
            Native.options.put(clazz, map);
        }
        if (map.containsKey("type-mapper")) {
            Native.typeMappers.put(clazz, map.get("type-mapper"));
        }
        if (map.containsKey("structure-alignment")) {
            Native.alignments.put(clazz, map.get("structure-alignment"));
        }
        if (o != null) {
            Native.libraries.put(clazz, new WeakReference<Object>(o));
        }
        if (!clazz.isInterface() && ((Native.class$com$sun$jna$Library == null) ? (Native.class$com$sun$jna$Library = class$("com.sun.jna.Library")) : Native.class$com$sun$jna$Library).isAssignableFrom(clazz)) {
            final Class[] interfaces = clazz.getInterfaces();
            while (0 < interfaces.length) {
                if (((Native.class$com$sun$jna$Library == null) ? (Native.class$com$sun$jna$Library = class$("com.sun.jna.Library")) : Native.class$com$sun$jna$Library).isAssignableFrom(interfaces[0])) {
                    cacheOptions(interfaces[0], map, o);
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
    // monitorexit(libraries)
    
    private static native long registerMethod(final Class p0, final String p1, final String p2, final int[] p3, final long[] p4, final long[] p5, final int p6, final long p7, final long p8, final Class p9, final long p10, final int p11, final boolean p12, final ToNativeConverter[] p13, final FromNativeConverter p14);
    
    private static NativeMapped fromNative(final Class clazz, final Object o) {
        return (NativeMapped)NativeMappedConverter.getInstance(clazz).fromNative(o, new FromNativeContext(clazz));
    }
    
    private static Class nativeType(final Class clazz) {
        return NativeMappedConverter.getInstance(clazz).nativeType();
    }
    
    private static Object toNative(final ToNativeConverter toNativeConverter, final Object o) {
        return toNativeConverter.toNative(o, new ToNativeContext());
    }
    
    private static Object fromNative(final FromNativeConverter fromNativeConverter, final Object o, final Class clazz) {
        return fromNativeConverter.fromNative(o, new FromNativeContext(clazz));
    }
    
    public static native long ffi_prep_cif(final int p0, final int p1, final long p2, final long p3);
    
    public static native void ffi_call(final long p0, final long p1, final long p2, final long p3);
    
    public static native long ffi_prep_closure(final long p0, final ffi_callback p1);
    
    public static native void ffi_free_closure(final long p0);
    
    static native int initialize_ffi_type(final long p0);
    
    public static void main(final String[] array) {
        final Package package1 = ((Native.class$com$sun$jna$Native == null) ? (Native.class$com$sun$jna$Native = class$("com.sun.jna.Native")) : Native.class$com$sun$jna$Native).getPackage();
        String s = (package1 != null) ? package1.getSpecificationTitle() : "Java Native Access (JNA)";
        if (s == null) {
            s = "Java Native Access (JNA)";
        }
        String s2 = (package1 != null) ? package1.getSpecificationVersion() : "3.4.0";
        if (s2 == null) {
            s2 = "3.4.0";
        }
        System.out.println(s + " API Version " + s2);
        String s3 = (package1 != null) ? package1.getImplementationVersion() : "3.4.0 (package information missing)";
        if (s3 == null) {
            s3 = "3.4.0 (package information missing)";
        }
        System.out.println("Version: " + s3);
        System.out.println(" Native: " + getNativeVersion() + " (" + getAPIChecksum() + ")");
        System.exit(0);
    }
    
    static synchronized native void freeNativeCallback(final long p0);
    
    static synchronized native long createNativeCallback(final Callback p0, final Method p1, final Class[] p2, final Class p3, final int p4, final boolean p5);
    
    static native int invokeInt(final long p0, final int p1, final Object[] p2);
    
    static native long invokeLong(final long p0, final int p1, final Object[] p2);
    
    static native void invokeVoid(final long p0, final int p1, final Object[] p2);
    
    static native float invokeFloat(final long p0, final int p1, final Object[] p2);
    
    static native double invokeDouble(final long p0, final int p1, final Object[] p2);
    
    static native long invokePointer(final long p0, final int p1, final Object[] p2);
    
    private static native void invokeStructure(final long p0, final int p1, final Object[] p2, final long p3, final long p4);
    
    static Structure invokeStructure(final long n, final int n2, final Object[] array, final Structure structure) {
        invokeStructure(n, n2, array, structure.getPointer().peer, structure.getTypeInfo().peer);
        return structure;
    }
    
    static native Object invokeObject(final long p0, final int p1, final Object[] p2);
    
    static native long open(final String p0);
    
    static native void close(final long p0);
    
    static native long findSymbol(final long p0, final String p1);
    
    static native long indexOf(final long p0, final byte p1);
    
    static native void read(final long p0, final byte[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final short[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final char[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final int[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final long[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final float[] p1, final int p2, final int p3);
    
    static native void read(final long p0, final double[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final byte[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final short[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final char[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final int[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final long[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final float[] p1, final int p2, final int p3);
    
    static native void write(final long p0, final double[] p1, final int p2, final int p3);
    
    static native byte getByte(final long p0);
    
    static native char getChar(final long p0);
    
    static native short getShort(final long p0);
    
    static native int getInt(final long p0);
    
    static native long getLong(final long p0);
    
    static native float getFloat(final long p0);
    
    static native double getDouble(final long p0);
    
    static Pointer getPointer(final long n) {
        final long getPointer = _getPointer(n);
        return (getPointer == 0L) ? null : new Pointer(getPointer);
    }
    
    private static native long _getPointer(final long p0);
    
    static native String getString(final long p0, final boolean p1);
    
    static native void setMemory(final long p0, final long p1, final byte p2);
    
    static native void setByte(final long p0, final byte p1);
    
    static native void setShort(final long p0, final short p1);
    
    static native void setChar(final long p0, final char p1);
    
    static native void setInt(final long p0, final int p1);
    
    static native void setLong(final long p0, final long p1);
    
    static native void setFloat(final long p0, final float p1);
    
    static native void setDouble(final long p0, final double p1);
    
    static native void setPointer(final long p0, final long p1);
    
    static native void setString(final long p0, final String p1, final boolean p2);
    
    public static native long malloc(final long p0);
    
    public static native void free(final long p0);
    
    public static native ByteBuffer getDirectByteBuffer(final long p0, final long p1);
    
    public static void detach(final boolean b) {
        setLastError(b ? -1 : -2);
    }
    
    static void access$000() {
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static Map access$100() {
        return Native.registeredClasses;
    }
    
    static void access$200(final Class clazz, final long[] array) {
        unregister(clazz, array);
    }
    
    static {
        Native.nativeLibraryPath = null;
        Native.typeMappers = new WeakHashMap();
        Native.alignments = new WeakHashMap();
        Native.options = new WeakHashMap();
        Native.libraries = new WeakHashMap();
        DEFAULT_HANDLER = new Callback.UncaughtExceptionHandler() {
            public void uncaughtException(final Callback callback, final Throwable t) {
                System.err.println("JNA: Callback " + callback + " threw the following exception:");
                t.printStackTrace();
            }
        };
        Native.callbackExceptionHandler = Native.DEFAULT_HANDLER;
        POINTER_SIZE = sizeof(0);
        LONG_SIZE = sizeof(1);
        WCHAR_SIZE = sizeof(2);
        SIZE_T_SIZE = sizeof(3);
        if (Boolean.getBoolean("jna.protected")) {
            setProtected(true);
        }
        if (!"3.4.0".equals(getNativeVersion())) {
            final String property = System.getProperty("line.separator");
            throw new Error(property + property + "There is an incompatible JNA native library installed on this system." + property + "To resolve this issue you may do one of the following:" + property + " - remove or uninstall the offending library" + property + " - set the system property jna.nosys=true" + property + " - set jna.boot.library.path to include the path to the version of the " + property + "   jnidispatch library included with the JNA jar file you are using" + property);
        }
        setPreserveLastError("true".equalsIgnoreCase(System.getProperty("jna.preserve_last_error", "true")));
        finalizer = new Object() {
            protected void finalize() {
            }
        };
        lastError = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new Integer(0);
            }
        };
        Native.registeredClasses = new HashMap();
        Native.registeredLibraries = new HashMap();
        Native.unloader = new Object() {
            protected void finalize() {
                // monitorenter(access$100 = Native.access$100())
                final Iterator<Map.Entry<Class, V>> iterator = Native.access$100().entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<Class, V> entry = iterator.next();
                    Native.access$200(entry.getKey(), entry.getValue());
                    iterator.remove();
                }
            }
            // monitorexit(access$100)
        };
    }
    
    private static class AWT
    {
        static long getWindowID(final Window window) throws HeadlessException {
            return getComponentID(window);
        }
        
        static long getComponentID(final Object o) throws HeadlessException {
            if (GraphicsEnvironment.isHeadless()) {
                throw new HeadlessException("No native windows when headless");
            }
            final Component component = (Component)o;
            if (component.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight");
            }
            if (!component.isDisplayable()) {
                throw new IllegalStateException("Component must be displayable");
            }
            if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !component.isVisible()) {
                throw new IllegalStateException("Component must be visible");
            }
            return Native.getWindowHandle0(component);
        }
    }
    
    private static class Buffers
    {
        static boolean isBuffer(final Class clazz) {
            return ((Native.class$java$nio$Buffer == null) ? (Native.class$java$nio$Buffer = Native.class$("java.nio.Buffer")) : Native.class$java$nio$Buffer).isAssignableFrom(clazz);
        }
    }
    
    public interface ffi_callback
    {
        void invoke(final long p0, final long p1, final long p2);
    }
}
