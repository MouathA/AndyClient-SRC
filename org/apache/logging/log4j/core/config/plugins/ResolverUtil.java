package org.apache.logging.log4j.core.config.plugins;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.helpers.*;
import org.osgi.framework.wiring.*;
import org.osgi.framework.*;
import java.util.*;
import java.io.*;
import java.util.jar.*;
import org.apache.logging.log4j.status.*;
import java.net.*;

public class ResolverUtil
{
    private static final Logger LOGGER;
    private static final String VFSZIP = "vfszip";
    private static final String BUNDLE_RESOURCE = "bundleresource";
    private final Set classMatches;
    private final Set resourceMatches;
    private ClassLoader classloader;
    
    public ResolverUtil() {
        this.classMatches = new HashSet();
        this.resourceMatches = new HashSet();
    }
    
    public Set getClasses() {
        return this.classMatches;
    }
    
    public Set getResources() {
        return this.resourceMatches;
    }
    
    public ClassLoader getClassLoader() {
        return (this.classloader != null) ? this.classloader : (this.classloader = Loader.getClassLoader(ResolverUtil.class, null));
    }
    
    public void setClassLoader(final ClassLoader classloader) {
        this.classloader = classloader;
    }
    
    public void findImplementations(final Class clazz, final String... array) {
        if (array == null) {
            return;
        }
        final IsA isA = new IsA(clazz);
        while (0 < array.length) {
            this.findInPackage(isA, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void findSuffix(final String s, final String... array) {
        if (array == null) {
            return;
        }
        final NameEndsWith nameEndsWith = new NameEndsWith(s);
        while (0 < array.length) {
            this.findInPackage(nameEndsWith, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void findAnnotated(final Class clazz, final String... array) {
        if (array == null) {
            return;
        }
        final AnnotatedWith annotatedWith = new AnnotatedWith(clazz);
        while (0 < array.length) {
            this.findInPackage(annotatedWith, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void findNamedResource(final String s, final String... array) {
        if (array == null) {
            return;
        }
        final NameIs nameIs = new NameIs(s);
        while (0 < array.length) {
            this.findInPackage(nameIs, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void find(final Test test, final String... array) {
        if (array == null) {
            return;
        }
        while (0 < array.length) {
            this.findInPackage(test, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void findInPackage(final Test test, String replace) {
        replace = replace.replace('.', '/');
        final Enumeration<URL> resources = this.getClassLoader().getResources(replace);
        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            String s = URLDecoder.decode(url.getFile(), Charsets.UTF_8.name());
            if (s.startsWith("file:")) {
                s = s.substring(5);
            }
            if (s.indexOf(33) > 0) {
                s = s.substring(0, s.indexOf(33));
            }
            ResolverUtil.LOGGER.info("Scanning for classes in [" + s + "] matching criteria: " + test);
            if ("vfszip".equals(url.getProtocol())) {
                final String substring = s.substring(0, s.length() - replace.length() - 2);
                final URL url2 = new URL(url.getProtocol(), url.getHost(), substring);
                final JarInputStream jarInputStream = new JarInputStream(url2.openStream());
                this.loadImplementationsInJar(test, replace, substring, jarInputStream);
                this.close(jarInputStream, url2);
            }
            else if ("bundleresource".equals(url.getProtocol())) {
                this.loadImplementationsInBundle(test, replace);
            }
            else {
                final File file = new File(s);
                if (file.isDirectory()) {
                    this.loadImplementationsInDirectory(test, replace, file);
                }
                else {
                    this.loadImplementationsInJar(test, replace, file);
                }
            }
        }
    }
    
    private void loadImplementationsInBundle(final Test test, final String s) {
        final Iterator<String> iterator = ((BundleWiring)FrameworkUtil.getBundle((Class)ResolverUtil.class).adapt((Class)BundleWiring.class)).listResources(s, "*.class", 1).iterator();
        while (iterator.hasNext()) {
            this.addIfMatching(test, iterator.next());
        }
    }
    
    private void loadImplementationsInDirectory(final Test test, final String s, final File file) {
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        final File[] array = listFiles;
        while (0 < array.length) {
            final File file2 = array[0];
            final StringBuilder sb = new StringBuilder();
            sb.append(s).append("/").append(file2.getName());
            final String s2 = (s == null) ? file2.getName() : sb.toString();
            if (file2.isDirectory()) {
                this.loadImplementationsInDirectory(test, s2, file2);
            }
            else if (this.isTestApplicable(test, file2.getName())) {
                this.addIfMatching(test, s2);
            }
            int n = 0;
            ++n;
        }
    }
    
    private boolean isTestApplicable(final Test test, final String s) {
        return test.doesMatchResource() || (s.endsWith(".class") && test.doesMatchClass());
    }
    
    private void loadImplementationsInJar(final Test test, final String s, final File file) {
        final JarInputStream jarInputStream = new JarInputStream(new FileInputStream(file));
        this.loadImplementationsInJar(test, s, file.getPath(), jarInputStream);
        this.close(jarInputStream, file);
    }
    
    private void close(final JarInputStream jarInputStream, final Object o) {
        if (jarInputStream != null) {
            jarInputStream.close();
        }
    }
    
    private void loadImplementationsInJar(final Test test, final String s, final String s2, final JarInputStream jarInputStream) {
        JarEntry nextJarEntry;
        while ((nextJarEntry = jarInputStream.getNextJarEntry()) != null) {
            final String name = nextJarEntry.getName();
            if (!nextJarEntry.isDirectory() && name.startsWith(s) && this.isTestApplicable(test, name)) {
                this.addIfMatching(test, name);
            }
        }
    }
    
    protected void addIfMatching(final Test test, final String s) {
        final ClassLoader classLoader = this.getClassLoader();
        if (test.doesMatchClass()) {
            final String replace = s.substring(0, s.indexOf(46)).replace('/', '.');
            if (ResolverUtil.LOGGER.isDebugEnabled()) {
                ResolverUtil.LOGGER.debug("Checking to see if class " + replace + " matches criteria [" + test + "]");
            }
            final Class<?> loadClass = classLoader.loadClass(replace);
            if (test.matches(loadClass)) {
                this.classMatches.add(loadClass);
            }
        }
        if (test.doesMatchResource()) {
            URL url = classLoader.getResource(s);
            if (url == null) {
                url = classLoader.getResource(s.substring(1));
            }
            if (url != null && test.matches(url.toURI())) {
                this.resourceMatches.add(url.toURI());
            }
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
    
    public static class NameIs extends ResourceTest
    {
        private final String name;
        
        public NameIs(final String s) {
            this.name = "/" + s;
        }
        
        @Override
        public boolean matches(final URI uri) {
            return uri.getPath().endsWith(this.name);
        }
        
        @Override
        public String toString() {
            return "named " + this.name;
        }
    }
    
    public abstract static class ResourceTest implements Test
    {
        @Override
        public boolean matches(final Class clazz) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean doesMatchClass() {
            return false;
        }
        
        @Override
        public boolean doesMatchResource() {
            return true;
        }
    }
    
    public interface Test
    {
        boolean matches(final Class p0);
        
        boolean matches(final URI p0);
        
        boolean doesMatchClass();
        
        boolean doesMatchResource();
    }
    
    public static class AnnotatedWith extends ClassTest
    {
        private final Class annotation;
        
        public AnnotatedWith(final Class annotation) {
            this.annotation = annotation;
        }
        
        @Override
        public boolean matches(final Class clazz) {
            return clazz != null && clazz.isAnnotationPresent(this.annotation);
        }
        
        @Override
        public String toString() {
            return "annotated with @" + this.annotation.getSimpleName();
        }
    }
    
    public abstract static class ClassTest implements Test
    {
        @Override
        public boolean matches(final URI uri) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean doesMatchClass() {
            return true;
        }
        
        @Override
        public boolean doesMatchResource() {
            return false;
        }
    }
    
    public static class NameEndsWith extends ClassTest
    {
        private final String suffix;
        
        public NameEndsWith(final String suffix) {
            this.suffix = suffix;
        }
        
        @Override
        public boolean matches(final Class clazz) {
            return clazz != null && clazz.getName().endsWith(this.suffix);
        }
        
        @Override
        public String toString() {
            return "ends with the suffix " + this.suffix;
        }
    }
    
    public static class IsA extends ClassTest
    {
        private final Class parent;
        
        public IsA(final Class parent) {
            this.parent = parent;
        }
        
        @Override
        public boolean matches(final Class clazz) {
            return clazz != null && this.parent.isAssignableFrom(clazz);
        }
        
        @Override
        public String toString() {
            return "is assignable to " + this.parent.getSimpleName();
        }
    }
}
