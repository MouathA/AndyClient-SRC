package com.google.common.reflect;

import java.util.logging.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import javax.annotation.*;
import java.util.jar.*;
import java.net.*;
import com.google.common.base.*;

@Beta
public final class ClassPath
{
    private static final Logger logger;
    private static final Predicate IS_TOP_LEVEL;
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR;
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private final ImmutableSet resources;
    
    private ClassPath(final ImmutableSet resources) {
        this.resources = resources;
    }
    
    public static ClassPath from(final ClassLoader classLoader) throws IOException {
        final Scanner scanner = new Scanner();
        for (final Map.Entry<URI, V> entry : getClassPathEntries(classLoader).entrySet()) {
            scanner.scan(entry.getKey(), (ClassLoader)entry.getValue());
        }
        return new ClassPath(scanner.getResources());
    }
    
    public ImmutableSet getResources() {
        return this.resources;
    }
    
    public ImmutableSet getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }
    
    public ImmutableSet getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(ClassPath.IS_TOP_LEVEL).toSet();
    }
    
    public ImmutableSet getTopLevelClasses(final String s) {
        Preconditions.checkNotNull(s);
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        for (final ClassInfo classInfo : this.getTopLevelClasses()) {
            if (classInfo.getPackageName().equals(s)) {
                builder.add((Object)classInfo);
            }
        }
        return builder.build();
    }
    
    public ImmutableSet getTopLevelClassesRecursive(final String s) {
        Preconditions.checkNotNull(s);
        final String string = s + '.';
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        for (final ClassInfo classInfo : this.getTopLevelClasses()) {
            if (classInfo.getName().startsWith(string)) {
                builder.add((Object)classInfo);
            }
        }
        return builder.build();
    }
    
    @VisibleForTesting
    static ImmutableMap getClassPathEntries(final ClassLoader classLoader) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final ClassLoader parent = classLoader.getParent();
        if (parent != null) {
            linkedHashMap.putAll(getClassPathEntries(parent));
        }
        if (classLoader instanceof URLClassLoader) {
            final URL[] urLs = ((URLClassLoader)classLoader).getURLs();
            while (0 < urLs.length) {
                final URI uri = urLs[0].toURI();
                if (!linkedHashMap.containsKey(uri)) {
                    linkedHashMap.put(uri, classLoader);
                }
                int n = 0;
                ++n;
            }
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }
    
    @VisibleForTesting
    static String getClassName(final String s) {
        return s.substring(0, s.length() - 6).replace('/', '.');
    }
    
    static Logger access$100() {
        return ClassPath.logger;
    }
    
    static Splitter access$200() {
        return ClassPath.CLASS_PATH_ATTRIBUTE_SEPARATOR;
    }
    
    static {
        logger = Logger.getLogger(ClassPath.class.getName());
        IS_TOP_LEVEL = new Predicate() {
            public boolean apply(final ClassInfo classInfo) {
                return ClassInfo.access$000(classInfo).indexOf(36) == -1;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((ClassInfo)o);
            }
        };
        CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
    }
    
    @VisibleForTesting
    static final class Scanner
    {
        private final ImmutableSortedSet.Builder resources;
        private final Set scannedUris;
        
        Scanner() {
            this.resources = new ImmutableSortedSet.Builder(Ordering.usingToString());
            this.scannedUris = Sets.newHashSet();
        }
        
        ImmutableSortedSet getResources() {
            return this.resources.build();
        }
        
        void scan(final URI uri, final ClassLoader classLoader) throws IOException {
            if (uri.getScheme().equals("file") && this.scannedUris.add(uri)) {
                this.scanFrom(new File(uri), classLoader);
            }
        }
        
        @VisibleForTesting
        void scanFrom(final File file, final ClassLoader classLoader) throws IOException {
            if (!file.exists()) {
                return;
            }
            if (file.isDirectory()) {
                this.scanDirectory(file, classLoader);
            }
            else {
                this.scanJar(file, classLoader);
            }
        }
        
        private void scanDirectory(final File file, final ClassLoader classLoader) throws IOException {
            this.scanDirectory(file, classLoader, "", ImmutableSet.of());
        }
        
        private void scanDirectory(final File file, final ClassLoader classLoader, final String s, final ImmutableSet set) throws IOException {
            final File canonicalFile = file.getCanonicalFile();
            if (set.contains(canonicalFile)) {
                return;
            }
            final File[] listFiles = file.listFiles();
            if (listFiles == null) {
                ClassPath.access$100().warning("Cannot read directory " + file);
                return;
            }
            final ImmutableSet build = ImmutableSet.builder().addAll((Iterable)set).add((Object)canonicalFile).build();
            final File[] array = listFiles;
            while (0 < array.length) {
                final File file2 = array[0];
                final String name = file2.getName();
                if (file2.isDirectory()) {
                    this.scanDirectory(file2, classLoader, s + name + "/", build);
                }
                else {
                    final String string = s + name;
                    if (!string.equals("META-INF/MANIFEST.MF")) {
                        this.resources.add((Object)ResourceInfo.of(string, classLoader));
                    }
                }
                int n = 0;
                ++n;
            }
        }
        
        private void scanJar(final File file, final ClassLoader classLoader) throws IOException {
            final JarFile jarFile = new JarFile(file);
            final Iterator iterator = getClassPathFromManifest(file, jarFile.getManifest()).iterator();
            while (iterator.hasNext()) {
                this.scan(iterator.next(), classLoader);
            }
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.isDirectory()) {
                    if (jarEntry.getName().equals("META-INF/MANIFEST.MF")) {
                        continue;
                    }
                    this.resources.add((Object)ResourceInfo.of(jarEntry.getName(), classLoader));
                }
            }
            jarFile.close();
        }
        
        @VisibleForTesting
        static ImmutableSet getClassPathFromManifest(final File file, @Nullable final Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            final ImmutableSet.Builder builder = ImmutableSet.builder();
            final String value = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
            if (value != null) {
                final Iterator iterator = ClassPath.access$200().split(value).iterator();
                while (iterator.hasNext()) {
                    builder.add((Object)getClassPathEntry(file, iterator.next()));
                }
            }
            return builder.build();
        }
        
        @VisibleForTesting
        static URI getClassPathEntry(final File file, final String s) throws URISyntaxException {
            final URI uri = new URI(s);
            if (uri.isAbsolute()) {
                return uri;
            }
            return new File(file.getParentFile(), s.replace('/', File.separatorChar)).toURI();
        }
    }
    
    @Beta
    public static class ResourceInfo
    {
        private final String resourceName;
        final ClassLoader loader;
        
        static ResourceInfo of(final String s, final ClassLoader classLoader) {
            if (s.endsWith(".class")) {
                return new ClassInfo(s, classLoader);
            }
            return new ResourceInfo(s, classLoader);
        }
        
        ResourceInfo(final String s, final ClassLoader classLoader) {
            this.resourceName = (String)Preconditions.checkNotNull(s);
            this.loader = (ClassLoader)Preconditions.checkNotNull(classLoader);
        }
        
        public final URL url() {
            return (URL)Preconditions.checkNotNull(this.loader.getResource(this.resourceName), "Failed to load resource: %s", this.resourceName);
        }
        
        public final String getResourceName() {
            return this.resourceName;
        }
        
        @Override
        public int hashCode() {
            return this.resourceName.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof ResourceInfo) {
                final ResourceInfo resourceInfo = (ResourceInfo)o;
                return this.resourceName.equals(resourceInfo.resourceName) && this.loader == resourceInfo.loader;
            }
            return false;
        }
        
        @Override
        public String toString() {
            return this.resourceName;
        }
    }
    
    @Beta
    public static final class ClassInfo extends ResourceInfo
    {
        private final String className;
        
        ClassInfo(final String s, final ClassLoader classLoader) {
            super(s, classLoader);
            this.className = ClassPath.getClassName(s);
        }
        
        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }
        
        public String getSimpleName() {
            final int lastIndex = this.className.lastIndexOf(36);
            if (lastIndex != -1) {
                return CharMatcher.DIGIT.trimLeadingFrom(this.className.substring(lastIndex + 1));
            }
            final String packageName = this.getPackageName();
            if (packageName.isEmpty()) {
                return this.className;
            }
            return this.className.substring(packageName.length() + 1);
        }
        
        public String getName() {
            return this.className;
        }
        
        public Class load() {
            return this.loader.loadClass(this.className);
        }
        
        @Override
        public String toString() {
            return this.className;
        }
        
        static String access$000(final ClassInfo classInfo) {
            return classInfo.className;
        }
    }
}
