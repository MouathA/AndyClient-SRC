package io.netty.util.internal;

import java.net.*;
import java.io.*;
import io.netty.util.internal.logging.*;
import java.util.*;

public final class NativeLibraryLoader
{
    private static final InternalLogger logger;
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final String OSNAME;
    private static final File WORKDIR;
    
    private static File tmpdir() {
        final File directory = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
        if (directory != null) {
            NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory);
            return directory;
        }
        final File directory2 = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
        if (directory2 != null) {
            NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory2 + " (java.io.tmpdir)");
            return directory2;
        }
        if (isWindows()) {
            final File directory3 = toDirectory(System.getenv("TEMP"));
            if (directory3 != null) {
                NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory3 + " (%TEMP%)");
                return directory3;
            }
            final String getenv = System.getenv("USERPROFILE");
            if (getenv != null) {
                final File directory4 = toDirectory(getenv + "\\AppData\\Local\\Temp");
                if (directory4 != null) {
                    NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory4 + " (%USERPROFILE%\\AppData\\Local\\Temp)");
                    return directory4;
                }
                final File directory5 = toDirectory(getenv + "\\Local Settings\\Temp");
                if (directory5 != null) {
                    NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory5 + " (%USERPROFILE%\\Local Settings\\Temp)");
                    return directory5;
                }
            }
        }
        else {
            final File directory6 = toDirectory(System.getenv("TMPDIR"));
            if (directory6 != null) {
                NativeLibraryLoader.logger.debug("-Dio.netty.tmpdir: " + directory6 + " ($TMPDIR)");
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
        NativeLibraryLoader.logger.warn("Failed to get the temporary directory; falling back to: " + file);
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
    
    private static boolean isWindows() {
        return NativeLibraryLoader.OSNAME.startsWith("windows");
    }
    
    private static boolean isOSX() {
        return NativeLibraryLoader.OSNAME.startsWith("macosx") || NativeLibraryLoader.OSNAME.startsWith("osx");
    }
    
    public static void load(final String s, final ClassLoader classLoader) {
        final String mapLibraryName = System.mapLibraryName(s);
        final String string = "META-INF/native/" + mapLibraryName;
        URL url = classLoader.getResource(string);
        if (url == null && isOSX()) {
            if (string.endsWith(".jnilib")) {
                url = classLoader.getResource("META-INF/native/lib" + s + ".dynlib");
            }
            else {
                url = classLoader.getResource("META-INF/native/lib" + s + ".jnilib");
            }
        }
        if (url == null) {
            System.loadLibrary(s);
            return;
        }
        final int lastIndex = mapLibraryName.lastIndexOf(46);
        final File tempFile = File.createTempFile(mapLibraryName.substring(0, lastIndex), mapLibraryName.substring(lastIndex, mapLibraryName.length()), NativeLibraryLoader.WORKDIR);
        final InputStream openStream = url.openStream();
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        final byte[] array = new byte[8192];
        int read;
        while ((read = openStream.read(array)) > 0) {
            fileOutputStream.write(array, 0, read);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        final OutputStream outputStream = null;
        System.load(tempFile.getPath());
        if (openStream != null) {
            openStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (tempFile != null) {
            if (true) {
                tempFile.deleteOnExit();
            }
            else if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }
    
    private NativeLibraryLoader() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
        OSNAME = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
        final String value = SystemPropertyUtil.get("io.netty.native.workdir");
        if (value != null) {
            final File file = new File(value);
            file.mkdirs();
            WORKDIR = file.getAbsoluteFile();
            NativeLibraryLoader.logger.debug("-Dio.netty.netty.workdir: " + NativeLibraryLoader.WORKDIR);
        }
        else {
            WORKDIR = tmpdir();
            NativeLibraryLoader.logger.debug("-Dio.netty.netty.workdir: " + NativeLibraryLoader.WORKDIR + " (io.netty.tmpdir)");
        }
    }
}
