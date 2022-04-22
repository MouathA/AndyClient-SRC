package com.sun.jna;

import java.lang.ref.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class NativeLibrary
{
    private long handle;
    private final String libraryName;
    private final String libraryPath;
    private final Map functions;
    final int callFlags;
    final Map options;
    private static final Map libraries;
    private static final Map searchPaths;
    private static final List librarySearchPath;
    static Class class$com$sun$jna$LastErrorException;
    
    private static String functionKey(final String s, final int n) {
        return s + "|" + n;
    }
    
    private NativeLibrary(final String s, final String libraryPath, final long handle, final Map options) {
        this.functions = new HashMap();
        this.libraryName = this.getLibraryName(s);
        this.libraryPath = libraryPath;
        this.handle = handle;
        final Integer value = options.get("calling-convention");
        this.callFlags = ((value instanceof Integer) ? value : 0);
        this.options = options;
        if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
            // monitorenter(functions = this.functions)
            this.functions.put(functionKey("GetLastError", this.callFlags), new Function(this, "GetLastError", 1) {
                private final NativeLibrary this$0;
                
                Object invoke(final Object[] array, final Class clazz, final boolean b) {
                    return new Integer(Native.getLastError());
                }
            });
        }
        // monitorexit(functions)
    }
    
    private static NativeLibrary loadLibrary(final String s, final Map map) {
        final LinkedList<String> list = new LinkedList<String>();
        final String webStartLibraryPath = Native.getWebStartLibraryPath(s);
        if (webStartLibraryPath != null) {
            list.add(webStartLibraryPath);
        }
        final List<? extends String> list2 = NativeLibrary.searchPaths.get(s);
        if (list2 != null) {
            // monitorenter(list3 = list2)
            list.addAll(0, (Collection<?>)list2);
        }
        // monitorexit(list3)
        list.addAll((Collection<?>)initPaths("jna.library.path"));
        String s2 = findLibraryPath(s, list);
        long n = Native.open(s2);
        if (n == 0L) {
            s2 = findLibraryPath(s, list);
            n = Native.open(s2);
            if (n == 0L) {
                throw new UnsatisfiedLinkError("Failed to load library '" + s + "'");
            }
        }
        return new NativeLibrary(s, s2, n, map);
    }
    
    private String getLibraryName(final String s) {
        String s2 = s;
        final String mapLibraryName = mapLibraryName("---");
        final int index = mapLibraryName.indexOf("---");
        if (index > 0 && s2.startsWith(mapLibraryName.substring(0, index))) {
            s2 = s2.substring(index);
        }
        final int index2 = s2.indexOf(mapLibraryName.substring(index + 3));
        if (index2 != -1) {
            s2 = s2.substring(0, index2);
        }
        return s2;
    }
    
    public static final NativeLibrary getInstance(final String s) {
        return getInstance(s, Collections.EMPTY_MAP);
    }
    
    public static final NativeLibrary getInstance(String s, final Map map) {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>(map);
        if (hashMap.get("calling-convention") == null) {
            hashMap.put("calling-convention", new Integer(0));
        }
        if (Platform.isLinux() && "c".equals(s)) {
            s = null;
        }
        // monitorenter(libraries = NativeLibrary.libraries)
        final WeakReference<NativeLibrary> weakReference = NativeLibrary.libraries.get(s + hashMap);
        NativeLibrary loadLibrary = (weakReference != null) ? weakReference.get() : null;
        if (loadLibrary == null) {
            if (s == null) {
                loadLibrary = new NativeLibrary("<process>", null, Native.open(null), hashMap);
            }
            else {
                loadLibrary = loadLibrary(s, hashMap);
            }
            final WeakReference weakReference2 = new WeakReference<NativeLibrary>(loadLibrary);
            NativeLibrary.libraries.put(loadLibrary.getName() + hashMap, weakReference2);
            final File file = loadLibrary.getFile();
            if (file != null) {
                NativeLibrary.libraries.put(file.getAbsolutePath() + hashMap, weakReference2);
                NativeLibrary.libraries.put(file.getName() + hashMap, weakReference2);
            }
        }
        // monitorexit(libraries)
        return loadLibrary;
    }
    
    public static final synchronized NativeLibrary getProcess() {
        return getInstance(null);
    }
    
    public static final synchronized NativeLibrary getProcess(final Map map) {
        return getInstance(null, map);
    }
    
    public static final void addSearchPath(final String s, final String s2) {
        // monitorenter(searchPaths = NativeLibrary.searchPaths)
        List<String> synchronizedList = NativeLibrary.searchPaths.get(s);
        if (synchronizedList == null) {
            synchronizedList = Collections.synchronizedList(new LinkedList<String>());
            NativeLibrary.searchPaths.put(s, synchronizedList);
        }
        synchronizedList.add(s2);
    }
    // monitorexit(searchPaths)
    
    public Function getFunction(final String s) {
        return this.getFunction(s, this.callFlags);
    }
    
    Function getFunction(final String s, final Method method) {
        int callFlags = this.callFlags;
        final Class<?>[] exceptionTypes = method.getExceptionTypes();
        while (0 < exceptionTypes.length) {
            if (((NativeLibrary.class$com$sun$jna$LastErrorException == null) ? (NativeLibrary.class$com$sun$jna$LastErrorException = class$("com.sun.jna.LastErrorException")) : NativeLibrary.class$com$sun$jna$LastErrorException).isAssignableFrom(exceptionTypes[0])) {
                callFlags |= 0x4;
            }
            int n = 0;
            ++n;
        }
        return this.getFunction(s, callFlags);
    }
    
    public Function getFunction(final String s, final int n) {
        if (s == null) {
            throw new NullPointerException("Function name may not be null");
        }
        // monitorenter(functions = this.functions)
        final String functionKey = functionKey(s, n);
        Function function = this.functions.get(functionKey);
        if (function == null) {
            function = new Function(this, s, n);
            this.functions.put(functionKey, function);
        }
        // monitorexit(functions)
        return function;
    }
    
    public Map getOptions() {
        return this.options;
    }
    
    public Pointer getGlobalVariableAddress(final String s) {
        return new Pointer(this.getSymbolAddress(s));
    }
    
    long getSymbolAddress(final String s) {
        if (this.handle == 0L) {
            throw new UnsatisfiedLinkError("Library has been unloaded");
        }
        return Native.findSymbol(this.handle, s);
    }
    
    public String toString() {
        return "Native Library <" + this.libraryPath + "@" + this.handle + ">";
    }
    
    public String getName() {
        return this.libraryName;
    }
    
    public File getFile() {
        if (this.libraryPath == null) {
            return null;
        }
        return new File(this.libraryPath);
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    static void disposeAll() {
        // monitorenter(libraries = NativeLibrary.libraries)
        final HashSet<WeakReference<NativeLibrary>> set = (HashSet<WeakReference<NativeLibrary>>)new HashSet<Object>(NativeLibrary.libraries.values());
        // monitorexit(libraries)
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final NativeLibrary nativeLibrary = iterator.next().get();
            if (nativeLibrary != null) {
                nativeLibrary.dispose();
            }
        }
    }
    
    public void dispose() {
        // monitorenter(libraries = NativeLibrary.libraries)
        final Iterator<WeakReference> iterator = NativeLibrary.libraries.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().get() == this) {
                iterator.remove();
            }
        }
        // monitorexit(libraries)
        // monitorenter(this)
        if (this.handle != 0L) {
            Native.close(this.handle);
            this.handle = 0L;
        }
    }
    // monitorexit(this)
    
    private static List initPaths(final String s) {
        final String property = System.getProperty(s, "");
        if ("".equals(property)) {
            return Collections.EMPTY_LIST;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (!"".equals(nextToken)) {
                list.add(nextToken);
            }
        }
        return list;
    }
    
    private static String findLibraryPath(final String s, final List list) {
        if (new File(s).isAbsolute()) {
            return s;
        }
        final String mapLibraryName = mapLibraryName(s);
        for (final String s2 : list) {
            final File file = new File(s2, mapLibraryName);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            if (!Platform.isMac() || !mapLibraryName.endsWith(".dylib")) {
                continue;
            }
            final File file2 = new File(s2, mapLibraryName.substring(0, mapLibraryName.lastIndexOf(".dylib")) + ".jnilib");
            if (file2.exists()) {
                return file2.getAbsolutePath();
            }
        }
        return mapLibraryName;
    }
    
    private static String mapLibraryName(final String s) {
        if (!Platform.isMac()) {
            if (Platform.isLinux()) {
                if (isVersionedName(s) || s.endsWith(".so")) {
                    return s;
                }
            }
            else if (Platform.isWindows() && (s.endsWith(".drv") || s.endsWith(".dll"))) {
                return s;
            }
            return System.mapLibraryName(s);
        }
        if (s.startsWith("lib") && (s.endsWith(".dylib") || s.endsWith(".jnilib"))) {
            return s;
        }
        final String mapLibraryName = System.mapLibraryName(s);
        if (mapLibraryName.endsWith(".jnilib")) {
            return mapLibraryName.substring(0, mapLibraryName.lastIndexOf(".jnilib")) + ".dylib";
        }
        return mapLibraryName;
    }
    
    private static boolean isVersionedName(final String s) {
        if (s.startsWith("lib")) {
            final int lastIndex = s.lastIndexOf(".so.");
            if (lastIndex != -1 && lastIndex + 4 < s.length()) {
                for (int i = lastIndex + 4; i < s.length(); ++i) {
                    final char char1 = s.charAt(i);
                    if (!Character.isDigit(char1) && char1 != '.') {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    static String matchLibrary(final String s, List list) {
        final File file = new File(s);
        if (file.isAbsolute()) {
            list = Arrays.asList(file.getParent());
        }
        final FilenameFilter filenameFilter = new FilenameFilter(s) {
            private final String val$libName;
            
            public boolean accept(final File file, final String s) {
                return (s.startsWith("lib" + this.val$libName + ".so") || (s.startsWith(this.val$libName + ".so") && this.val$libName.startsWith("lib"))) && NativeLibrary.access$000(s);
            }
        };
        final LinkedList<File> list2 = new LinkedList<File>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            final File[] listFiles = new File(iterator.next()).listFiles(filenameFilter);
            if (listFiles != null && listFiles.length > 0) {
                list2.addAll((Collection<?>)Arrays.asList(listFiles));
            }
        }
        double n = -1.0;
        String s2 = null;
        final Iterator<Object> iterator2 = list2.iterator();
        while (iterator2.hasNext()) {
            final String absolutePath = iterator2.next().getAbsolutePath();
            final double version = parseVersion(absolutePath.substring(absolutePath.lastIndexOf(".so.") + 4));
            if (version > n) {
                n = version;
                s2 = absolutePath;
            }
        }
        return s2;
    }
    
    static double parseVersion(String substring) {
        double n = 0.0;
        double n2 = 1.0;
        int n3 = substring.indexOf(".");
        while (substring != null) {
            String substring2;
            if (n3 != -1) {
                substring2 = substring.substring(0, n3);
                substring = substring.substring(n3 + 1);
                n3 = substring.indexOf(".");
            }
            else {
                substring2 = substring;
                substring = null;
            }
            n += Integer.parseInt(substring2) / n2;
            n2 *= 100.0;
        }
        return n;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static boolean access$000(final String s) {
        return isVersionedName(s);
    }
    
    static {
        libraries = new HashMap();
        searchPaths = Collections.synchronizedMap(new HashMap<Object, Object>());
        librarySearchPath = new LinkedList();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        final String webStartLibraryPath = Native.getWebStartLibraryPath("jnidispatch");
        if (webStartLibraryPath != null) {
            NativeLibrary.librarySearchPath.add(webStartLibraryPath);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            String string = "";
            String pathSeparator = "";
            String string2 = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD()) {
                string2 = (Platform.isSolaris() ? "/" : "") + Pointer.SIZE * 8;
            }
            String[] array = { "/usr/lib" + string2, "/lib" + string2, "/usr/lib", "/lib" };
            if (Platform.isLinux()) {
                String s = "";
                final String s2 = "linux";
                String s3 = "gnu";
                if (Platform.isIntel()) {
                    s = (Platform.is64Bit() ? "x86_64" : "i386");
                }
                else if (Platform.isPPC()) {
                    s = (Platform.is64Bit() ? "powerpc64" : "powerpc");
                }
                else if (Platform.isARM()) {
                    s = "arm";
                    s3 = "gnueabi";
                }
                final String string3 = s + "-" + s2 + "-" + s3;
                array = new String[] { "/usr/lib/" + string3, "/lib/" + string3, "/usr/lib" + string2, "/lib" + string2, "/usr/lib", "/lib" };
            }
            while (0 < array.length) {
                final File file = new File(array[0]);
                if (file.exists() && file.isDirectory()) {
                    string = string + pathSeparator + array[0];
                    pathSeparator = File.pathSeparator;
                }
                int n = 0;
                ++n;
            }
            if (!"".equals(string)) {
                System.setProperty("jna.platform.library.path", string);
            }
        }
        NativeLibrary.librarySearchPath.addAll(initPaths("jna.platform.library.path"));
    }
}
