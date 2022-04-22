package io.netty.util;

import io.netty.util.internal.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

public final class Version
{
    private static final String PROP_VERSION = ".version";
    private static final String PROP_BUILD_DATE = ".buildDate";
    private static final String PROP_COMMIT_DATE = ".commitDate";
    private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
    private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
    private static final String PROP_REPO_STATUS = ".repoStatus";
    private final String artifactId;
    private final String artifactVersion;
    private final long buildTimeMillis;
    private final long commitTimeMillis;
    private final String shortCommitHash;
    private final String longCommitHash;
    private final String repositoryStatus;
    
    public static Map identify() {
        return identify(null);
    }
    
    public static Map identify(ClassLoader contextClassLoader) {
        if (contextClassLoader == null) {
            contextClassLoader = PlatformDependent.getContextClassLoader();
        }
        final Properties properties = new Properties();
        final Enumeration<URL> resources = contextClassLoader.getResources("META-INF/io.netty.versions.properties");
        while (resources.hasMoreElements()) {
            final InputStream openStream = resources.nextElement().openStream();
            properties.load(openStream);
            openStream.close();
        }
        final HashSet<String> set = new HashSet<String>();
        for (final String s : ((Hashtable<String, V>)properties).keySet()) {
            final int index = s.indexOf(46);
            if (index <= 0) {
                continue;
            }
            final String substring = s.substring(0, index);
            if (!properties.containsKey(substring + ".version") || !properties.containsKey(substring + ".buildDate") || !properties.containsKey(substring + ".commitDate") || !properties.containsKey(substring + ".shortCommitHash") || !properties.containsKey(substring + ".longCommitHash")) {
                continue;
            }
            if (!properties.containsKey(substring + ".repoStatus")) {
                continue;
            }
            set.add(substring);
        }
        final TreeMap<String, Version> treeMap = new TreeMap<String, Version>();
        for (final String s2 : set) {
            treeMap.put(s2, new Version(s2, properties.getProperty(s2 + ".version"), parseIso8601(properties.getProperty(s2 + ".buildDate")), parseIso8601(properties.getProperty(s2 + ".commitDate")), properties.getProperty(s2 + ".shortCommitHash"), properties.getProperty(s2 + ".longCommitHash"), properties.getProperty(s2 + ".repoStatus")));
        }
        return treeMap;
    }
    
    private static long parseIso8601(final String s) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(s).getTime();
    }
    
    public static void main(final String[] array) {
        final Iterator<Version> iterator = identify().values().iterator();
        while (iterator.hasNext()) {
            System.err.println(iterator.next());
        }
    }
    
    private Version(final String artifactId, final String artifactVersion, final long buildTimeMillis, final long commitTimeMillis, final String shortCommitHash, final String longCommitHash, final String repositoryStatus) {
        this.artifactId = artifactId;
        this.artifactVersion = artifactVersion;
        this.buildTimeMillis = buildTimeMillis;
        this.commitTimeMillis = commitTimeMillis;
        this.shortCommitHash = shortCommitHash;
        this.longCommitHash = longCommitHash;
        this.repositoryStatus = repositoryStatus;
    }
    
    public String artifactId() {
        return this.artifactId;
    }
    
    public String artifactVersion() {
        return this.artifactVersion;
    }
    
    public long buildTimeMillis() {
        return this.buildTimeMillis;
    }
    
    public long commitTimeMillis() {
        return this.commitTimeMillis;
    }
    
    public String shortCommitHash() {
        return this.shortCommitHash;
    }
    
    public String longCommitHash() {
        return this.longCommitHash;
    }
    
    public String repositoryStatus() {
        return this.repositoryStatus;
    }
    
    @Override
    public String toString() {
        return this.artifactId + '-' + this.artifactVersion + '.' + this.shortCommitHash + ("clean".equals(this.repositoryStatus) ? "" : (" (repository: " + this.repositoryStatus + ')'));
    }
}
