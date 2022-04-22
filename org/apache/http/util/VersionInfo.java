package org.apache.http.util;

import java.util.*;
import java.io.*;

public class VersionInfo
{
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    private final String infoPackage;
    private final String infoModule;
    private final String infoRelease;
    private final String infoTimestamp;
    private final String infoClassloader;
    
    protected VersionInfo(final String infoPackage, final String s, final String s2, final String s3, final String s4) {
        Args.notNull(infoPackage, "Package identifier");
        this.infoPackage = infoPackage;
        this.infoModule = ((s != null) ? s : "UNAVAILABLE");
        this.infoRelease = ((s2 != null) ? s2 : "UNAVAILABLE");
        this.infoTimestamp = ((s3 != null) ? s3 : "UNAVAILABLE");
        this.infoClassloader = ((s4 != null) ? s4 : "UNAVAILABLE");
    }
    
    public final String getPackage() {
        return this.infoPackage;
    }
    
    public final String getModule() {
        return this.infoModule;
    }
    
    public final String getRelease() {
        return this.infoRelease;
    }
    
    public final String getTimestamp() {
        return this.infoTimestamp;
    }
    
    public final String getClassloader() {
        return this.infoClassloader;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(20 + this.infoPackage.length() + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        sb.append("VersionInfo(").append(this.infoPackage).append(':').append(this.infoModule);
        if (!"UNAVAILABLE".equals(this.infoRelease)) {
            sb.append(':').append(this.infoRelease);
        }
        if (!"UNAVAILABLE".equals(this.infoTimestamp)) {
            sb.append(':').append(this.infoTimestamp);
        }
        sb.append(')');
        if (!"UNAVAILABLE".equals(this.infoClassloader)) {
            sb.append('@').append(this.infoClassloader);
        }
        return sb.toString();
    }
    
    public static VersionInfo[] loadVersionInfo(final String[] array, final ClassLoader classLoader) {
        Args.notNull(array, "Package identifier array");
        final ArrayList<VersionInfo> list = new ArrayList<VersionInfo>(array.length);
        while (0 < array.length) {
            final VersionInfo loadVersionInfo = loadVersionInfo(array[0], classLoader);
            if (loadVersionInfo != null) {
                list.add(loadVersionInfo);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new VersionInfo[list.size()]);
    }
    
    public static VersionInfo loadVersionInfo(final String s, final ClassLoader classLoader) {
        Args.notNull(s, "Package identifier");
        final ClassLoader classLoader2 = (classLoader != null) ? classLoader : Thread.currentThread().getContextClassLoader();
        Map map = null;
        final InputStream resourceAsStream = classLoader2.getResourceAsStream(s.replace('.', '/') + "/" + "version.properties");
        if (resourceAsStream != null) {
            final Properties properties = new Properties();
            properties.load(resourceAsStream);
            map = properties;
            resourceAsStream.close();
        }
        VersionInfo fromMap = null;
        if (map != null) {
            fromMap = fromMap(s, map, classLoader2);
        }
        return fromMap;
    }
    
    protected static VersionInfo fromMap(final String s, final Map map, final ClassLoader classLoader) {
        Args.notNull(s, "Package identifier");
        String s2 = null;
        String s3 = null;
        String s4 = null;
        if (map != null) {
            s2 = map.get("info.module");
            if (s2 != null && s2.length() < 1) {
                s2 = null;
            }
            s3 = map.get("info.release");
            if (s3 != null && (s3.length() < 1 || s3.equals("${pom.version}"))) {
                s3 = null;
            }
            s4 = map.get("info.timestamp");
            if (s4 != null && (s4.length() < 1 || s4.equals("${mvn.timestamp}"))) {
                s4 = null;
            }
        }
        String string = null;
        if (classLoader != null) {
            string = classLoader.toString();
        }
        return new VersionInfo(s, s2, s3, s4, string);
    }
    
    public static String getUserAgent(final String s, final String s2, final Class clazz) {
        final VersionInfo loadVersionInfo = loadVersionInfo(s2, clazz.getClassLoader());
        return s + "/" + ((loadVersionInfo != null) ? loadVersionInfo.getRelease() : "UNAVAILABLE") + " (Java 1.5 minimum; Java/" + System.getProperty("java.version") + ")";
    }
}
