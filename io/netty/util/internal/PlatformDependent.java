package io.netty.util.internal;

import io.netty.util.internal.chmv8.*;
import java.util.concurrent.*;
import java.nio.*;
import java.lang.reflect.*;
import java.util.concurrent.atomic.*;
import io.netty.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import io.netty.util.internal.logging.*;

public final class PlatformDependent
{
    private static final InternalLogger logger;
    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN;
    private static final boolean IS_ANDROID;
    private static final boolean IS_WINDOWS;
    private static final boolean IS_ROOT;
    private static final int JAVA_VERSION;
    private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    private static final boolean HAS_UNSAFE;
    private static final boolean CAN_USE_CHM_V8;
    private static final boolean DIRECT_BUFFER_PREFERRED;
    private static final long MAX_DIRECT_MEMORY;
    private static final long ARRAY_BASE_OFFSET;
    private static final boolean HAS_JAVASSIST;
    private static final File TMPDIR;
    private static final int BIT_MODE;
    private static final int ADDRESS_SIZE;
    
    public static boolean isAndroid() {
        return PlatformDependent.IS_ANDROID;
    }
    
    public static boolean isWindows() {
        return PlatformDependent.IS_WINDOWS;
    }
    
    public static boolean isRoot() {
        return PlatformDependent.IS_ROOT;
    }
    
    public static int javaVersion() {
        return PlatformDependent.JAVA_VERSION;
    }
    
    public static boolean canEnableTcpNoDelayByDefault() {
        return PlatformDependent.CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    }
    
    public static boolean hasUnsafe() {
        return PlatformDependent.HAS_UNSAFE;
    }
    
    public static boolean directBufferPreferred() {
        return PlatformDependent.DIRECT_BUFFER_PREFERRED;
    }
    
    public static long maxDirectMemory() {
        return PlatformDependent.MAX_DIRECT_MEMORY;
    }
    
    public static boolean hasJavassist() {
        return PlatformDependent.HAS_JAVASSIST;
    }
    
    public static File tmpdir() {
        return PlatformDependent.TMPDIR;
    }
    
    public static int bitMode() {
        return PlatformDependent.BIT_MODE;
    }
    
    public static int addressSize() {
        return PlatformDependent.ADDRESS_SIZE;
    }
    
    public static long allocateMemory(final long n) {
        return PlatformDependent0.allocateMemory(n);
    }
    
    public static void freeMemory(final long n) {
        PlatformDependent0.freeMemory(n);
    }
    
    public static void throwException(final Throwable t) {
        if (hasUnsafe()) {
            PlatformDependent0.throwException(t);
        }
        else {
            throwException0(t);
        }
    }
    
    private static void throwException0(final Throwable t) throws Throwable {
        throw t;
    }
    
    public static ConcurrentMap newConcurrentHashMap() {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8();
        }
        return new ConcurrentHashMap();
    }
    
    public static ConcurrentMap newConcurrentHashMap(final int n) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8(n);
        }
        return new ConcurrentHashMap(n);
    }
    
    public static ConcurrentMap newConcurrentHashMap(final int n, final float n2) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8(n, n2);
        }
        return new ConcurrentHashMap(n, n2);
    }
    
    public static ConcurrentMap newConcurrentHashMap(final int n, final float n2, final int n3) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8(n, n2, n3);
        }
        return new ConcurrentHashMap(n, n2, n3);
    }
    
    public static ConcurrentMap newConcurrentHashMap(final Map map) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8(map);
        }
        return new ConcurrentHashMap(map);
    }
    
    public static void freeDirectBuffer(final ByteBuffer byteBuffer) {
        if (hasUnsafe() && !isAndroid()) {
            PlatformDependent0.freeDirectBuffer(byteBuffer);
        }
    }
    
    public static long directBufferAddress(final ByteBuffer byteBuffer) {
        return PlatformDependent0.directBufferAddress(byteBuffer);
    }
    
    public static Object getObject(final Object o, final long n) {
        return PlatformDependent0.getObject(o, n);
    }
    
    public static Object getObjectVolatile(final Object o, final long n) {
        return PlatformDependent0.getObjectVolatile(o, n);
    }
    
    public static int getInt(final Object o, final long n) {
        return PlatformDependent0.getInt(o, n);
    }
    
    public static long objectFieldOffset(final Field field) {
        return PlatformDependent0.objectFieldOffset(field);
    }
    
    public static byte getByte(final long n) {
        return PlatformDependent0.getByte(n);
    }
    
    public static short getShort(final long n) {
        return PlatformDependent0.getShort(n);
    }
    
    public static int getInt(final long n) {
        return PlatformDependent0.getInt(n);
    }
    
    public static long getLong(final long n) {
        return PlatformDependent0.getLong(n);
    }
    
    public static void putOrderedObject(final Object o, final long n, final Object o2) {
        PlatformDependent0.putOrderedObject(o, n, o2);
    }
    
    public static void putByte(final long n, final byte b) {
        PlatformDependent0.putByte(n, b);
    }
    
    public static void putShort(final long n, final short n2) {
        PlatformDependent0.putShort(n, n2);
    }
    
    public static void putInt(final long n, final int n2) {
        PlatformDependent0.putInt(n, n2);
    }
    
    public static void putLong(final long n, final long n2) {
        PlatformDependent0.putLong(n, n2);
    }
    
    public static void copyMemory(final long n, final long n2, final long n3) {
        PlatformDependent0.copyMemory(n, n2, n3);
    }
    
    public static void copyMemory(final byte[] array, final int n, final long n2, final long n3) {
        PlatformDependent0.copyMemory(array, PlatformDependent.ARRAY_BASE_OFFSET + n, null, n2, n3);
    }
    
    public static void copyMemory(final long n, final byte[] array, final int n2, final long n3) {
        PlatformDependent0.copyMemory(null, n, array, PlatformDependent.ARRAY_BASE_OFFSET + n2, n3);
    }
    
    public static AtomicReferenceFieldUpdater newAtomicReferenceFieldUpdater(final Class clazz, final String s) {
        if (hasUnsafe()) {
            return PlatformDependent0.newAtomicReferenceFieldUpdater(clazz, s);
        }
        return null;
    }
    
    public static AtomicIntegerFieldUpdater newAtomicIntegerFieldUpdater(final Class clazz, final String s) {
        if (hasUnsafe()) {
            return PlatformDependent0.newAtomicIntegerFieldUpdater(clazz, s);
        }
        return null;
    }
    
    public static AtomicLongFieldUpdater newAtomicLongFieldUpdater(final Class clazz, final String s) {
        if (hasUnsafe()) {
            return PlatformDependent0.newAtomicLongFieldUpdater(clazz, s);
        }
        return null;
    }
    
    public static Queue newMpscQueue() {
        return new MpscLinkedQueue();
    }
    
    public static ClassLoader getClassLoader(final Class clazz) {
        return PlatformDependent0.getClassLoader(clazz);
    }
    
    public static ClassLoader getContextClassLoader() {
        return PlatformDependent0.getContextClassLoader();
    }
    
    public static ClassLoader getSystemClassLoader() {
        return PlatformDependent0.getSystemClassLoader();
    }
    
    private static boolean isAndroid0() {
        Class.forName("android.app.Application", false, getSystemClassLoader());
        if (false) {
            PlatformDependent.logger.debug("Platform: Android");
        }
        return false;
    }
    
    private static boolean isWindows0() {
        final boolean contains = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
        if (contains) {
            PlatformDependent.logger.debug("Platform: Windows");
        }
        return contains;
    }
    
    private static boolean isRoot0() {
        if (isWindows()) {
            return false;
        }
        final String[] array = { "/usr/bin/id", "/bin/id", "id", "/usr/xpg4/bin/id" };
        final Pattern compile = Pattern.compile("^(?:0|[1-9][0-9]*)$");
        final String[] array2 = array;
        final int length = array2.length;
        while (0 < 1023) {
            final Process exec = Runtime.getRuntime().exec(new String[] { array2[0], "-u" });
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), CharsetUtil.US_ASCII));
            String line = bufferedReader.readLine();
            bufferedReader.close();
            if (exec.waitFor() != 0) {
                line = null;
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (exec != null) {
                exec.destroy();
            }
            if (line != null && compile.matcher(line).matches()) {
                PlatformDependent.logger.debug("UID: {}", line);
                return "0".equals(line);
            }
            int n = 0;
            ++n;
        }
        PlatformDependent.logger.debug("Could not determine the current UID using /usr/bin/id; attempting to bind at privileged ports.");
        Pattern.compile(".*(?:denied|not.*permitted).*");
        if (1023 > 0) {
            final ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(1023));
            if (PlatformDependent.logger.isDebugEnabled()) {
                PlatformDependent.logger.debug("UID: 0 (succeded to bind at port {})", (Object)1023);
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            return true;
        }
        PlatformDependent.logger.debug("UID: non-root (failed to bind at any privileged ports)");
        return false;
    }
    
    private static int javaVersion0() {
        if (!isAndroid()) {
            Class.forName("java.time.Clock", false, getClassLoader(Object.class));
        }
        if (PlatformDependent.logger.isDebugEnabled()) {
            PlatformDependent.logger.debug("Java version: {}", (Object)6);
        }
        return 6;
    }
    
    private static boolean hasUnsafe0() {
        final boolean boolean1 = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        PlatformDependent.logger.debug("-Dio.netty.noUnsafe: {}", (Object)boolean1);
        if (isAndroid()) {
            PlatformDependent.logger.debug("sun.misc.Unsafe: unavailable (Android)");
            return false;
        }
        if (boolean1) {
            PlatformDependent.logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            return false;
        }
        boolean b;
        if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
            b = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
        }
        else {
            b = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
        }
        if (!b) {
            PlatformDependent.logger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
            return false;
        }
        final boolean hasUnsafe = PlatformDependent0.hasUnsafe();
        PlatformDependent.logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
        return hasUnsafe;
    }
    
    private static long arrayBaseOffset0() {
        if (!hasUnsafe()) {
            return -1L;
        }
        return PlatformDependent0.arrayBaseOffset();
    }
    
    private static long maxDirectMemory0() {
        long n = ((Number)Class.forName("sun.misc.VM", true, getSystemClassLoader()).getDeclaredMethod("maxDirectMemory", (Class<?>[])new Class[0]).invoke(null, new Object[0])).longValue();
        if (n > 0L) {
            return n;
        }
        final List<CharSequence> list = (List<CharSequence>)Class.forName("java.lang.management.RuntimeMXBean", true, getSystemClassLoader()).getDeclaredMethod("getInputArguments", (Class<?>[])new Class[0]).invoke(Class.forName("java.lang.management.ManagementFactory", true, getSystemClassLoader()).getDeclaredMethod("getRuntimeMXBean", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new Object[0]);
        for (int i = list.size() - 1; i >= 0; --i) {
            final Matcher matcher = PlatformDependent.MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher(list.get(i));
            if (matcher.matches()) {
                n = Long.parseLong(matcher.group(1));
                switch (matcher.group(2).charAt(0)) {
                    case 'K':
                    case 'k': {
                        n *= 1024L;
                        break;
                    }
                    case 'M':
                    case 'm': {
                        n *= 1048576L;
                        break;
                    }
                    case 'G':
                    case 'g': {
                        n *= 1073741824L;
                        break;
                    }
                }
                break;
            }
        }
        if (n <= 0L) {
            n = Runtime.getRuntime().maxMemory();
            PlatformDependent.logger.debug("maxDirectMemory: {} bytes (maybe)", (Object)n);
        }
        else {
            PlatformDependent.logger.debug("maxDirectMemory: {} bytes", (Object)n);
        }
        return n;
    }
    
    private static boolean hasJavassist0() {
        if (isAndroid()) {
            return false;
        }
        final boolean boolean1 = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
        PlatformDependent.logger.debug("-Dio.netty.noJavassist: {}", (Object)boolean1);
        if (boolean1) {
            PlatformDependent.logger.debug("Javassist: unavailable (io.netty.noJavassist)");
            return false;
        }
        JavassistTypeParameterMatcherGenerator.generate(Object.class, getClassLoader(PlatformDependent.class));
        PlatformDependent.logger.debug("Javassist: available");
        return true;
    }
    
    private static File tmpdir0() {
        final File directory = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
        if (directory != null) {
            PlatformDependent.logger.debug("-Dio.netty.tmpdir: {}", directory);
            return directory;
        }
        final File directory2 = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
        if (directory2 != null) {
            PlatformDependent.logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", directory2);
            return directory2;
        }
        if (isWindows()) {
            final File directory3 = toDirectory(System.getenv("TEMP"));
            if (directory3 != null) {
                PlatformDependent.logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", directory3);
                return directory3;
            }
            final String getenv = System.getenv("USERPROFILE");
            if (getenv != null) {
                final File directory4 = toDirectory(getenv + "\\AppData\\Local\\Temp");
                if (directory4 != null) {
                    PlatformDependent.logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", directory4);
                    return directory4;
                }
                final File directory5 = toDirectory(getenv + "\\Local Settings\\Temp");
                if (directory5 != null) {
                    PlatformDependent.logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", directory5);
                    return directory5;
                }
            }
        }
        else {
            final File directory6 = toDirectory(System.getenv("TMPDIR"));
            if (directory6 != null) {
                PlatformDependent.logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", directory6);
                return directory6;
            }
        }
        File file;
        if (isWindows()) {
            file = new File("C:\\Windows\\Temp");
        }
        else {
            file = new File("/tmp");
        }
        PlatformDependent.logger.warn("Failed to get the temporary directory; falling back to: {}", file);
        return file;
    }
    
    private static File toDirectory(final String s) {
        if (s == null) {
            return null;
        }
        final File file = new File(s);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        return file.getAbsoluteFile();
    }
    
    private static int bitMode0() {
        SystemPropertyUtil.getInt("io.netty.bitMode", 0);
        if (32 > 0) {
            PlatformDependent.logger.debug("-Dio.netty.bitMode: {}", (Object)32);
            return 32;
        }
        SystemPropertyUtil.getInt("sun.arch.data.model", 0);
        if (32 > 0) {
            PlatformDependent.logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", (Object)32);
            return 32;
        }
        SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
        if (32 > 0) {
            PlatformDependent.logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", (Object)32);
            return 32;
        }
        final String trim = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
        if (!"amd64".equals(trim) && !"x86_64".equals(trim)) {
            if ("i386".equals(trim) || "i486".equals(trim) || "i586".equals(trim) || "i686".equals(trim)) {}
        }
        if (32 > 0) {
            PlatformDependent.logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", (Object)32, trim);
        }
        final Matcher matcher = Pattern.compile("([1-9][0-9]+)-?bit").matcher(SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 64;
    }
    
    private static int addressSize0() {
        if (!hasUnsafe()) {
            return -1;
        }
        return PlatformDependent0.addressSize();
    }
    
    private PlatformDependent() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
        MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
        IS_ANDROID = isAndroid0();
        IS_WINDOWS = isWindows0();
        IS_ROOT = isRoot0();
        JAVA_VERSION = javaVersion0();
        CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
        HAS_UNSAFE = hasUnsafe0();
        CAN_USE_CHM_V8 = (PlatformDependent.HAS_UNSAFE && PlatformDependent.JAVA_VERSION < 8);
        DIRECT_BUFFER_PREFERRED = (PlatformDependent.HAS_UNSAFE && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false));
        MAX_DIRECT_MEMORY = maxDirectMemory0();
        ARRAY_BASE_OFFSET = arrayBaseOffset0();
        HAS_JAVASSIST = hasJavassist0();
        TMPDIR = tmpdir0();
        BIT_MODE = bitMode0();
        ADDRESS_SIZE = addressSize0();
        if (PlatformDependent.logger.isDebugEnabled()) {
            PlatformDependent.logger.debug("-Dio.netty.noPreferDirect: {}", (Object)!PlatformDependent.DIRECT_BUFFER_PREFERRED);
        }
        if (!hasUnsafe() && !isAndroid()) {
            PlatformDependent.logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system unstability.");
        }
    }
}
