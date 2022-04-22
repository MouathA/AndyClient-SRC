package org.apache.commons.logging;

import java.util.*;
import java.security.*;
import java.net.*;
import java.io.*;

public abstract class LogFactory
{
    public static final String PRIORITY_KEY = "priority";
    public static final String TCCL_KEY = "use_tccl";
    public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
    public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
    public static final String FACTORY_PROPERTIES = "commons-logging.properties";
    protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
    public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
    private static PrintStream diagnosticsStream;
    private static final String diagnosticPrefix;
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
    private static final String WEAK_HASHTABLE_CLASSNAME = "org.apache.commons.logging.impl.WeakHashtable";
    private static final ClassLoader thisClassLoader;
    protected static Hashtable factories;
    protected static LogFactory nullClassLoaderFactory;
    static Class class$java$lang$Thread;
    static Class class$org$apache$commons$logging$LogFactory;
    
    protected LogFactory() {
    }
    
    public abstract Object getAttribute(final String p0);
    
    public abstract String[] getAttributeNames();
    
    public abstract Log getInstance(final Class p0) throws LogConfigurationException;
    
    public abstract Log getInstance(final String p0) throws LogConfigurationException;
    
    public abstract void release();
    
    public abstract void removeAttribute(final String p0);
    
    public abstract void setAttribute(final String p0, final Object p1);
    
    private static final Hashtable createFactoryStore() {
        String systemProperty = getSystemProperty("org.apache.commons.logging.LogFactory.HashtableImpl", null);
        if (systemProperty == null) {
            systemProperty = "org.apache.commons.logging.impl.WeakHashtable";
        }
        Hashtable<?, ?> hashtable = (Hashtable<?, ?>)Class.forName(systemProperty).newInstance();
        if (hashtable == null) {
            hashtable = new Hashtable<Object, Object>();
        }
        return hashtable;
    }
    
    private static String trim(final String s) {
        if (s == null) {
            return null;
        }
        return s.trim();
    }
    
    protected static void handleThrowable(final Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath)t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError)t;
        }
    }
    
    public static LogFactory getFactory() throws LogConfigurationException {
        final ClassLoader contextClassLoaderInternal = getContextClassLoaderInternal();
        if (contextClassLoaderInternal == null && isDiagnosticsEnabled()) {
            logDiagnostic("Context classloader is null.");
        }
        LogFactory logFactory = getCachedFactory(contextClassLoaderInternal);
        if (logFactory != null) {
            return logFactory;
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("[LOOKUP] LogFactory implementation requested for the first time for context classloader " + objectId(contextClassLoaderInternal));
            logHierarchy("[LOOKUP] ", contextClassLoaderInternal);
        }
        final Properties configurationFile = getConfigurationFile(contextClassLoaderInternal, "commons-logging.properties");
        ClassLoader thisClassLoader = contextClassLoaderInternal;
        if (configurationFile != null) {
            final String property = configurationFile.getProperty("use_tccl");
            if (property != null && !Boolean.valueOf(property)) {
                thisClassLoader = LogFactory.thisClassLoader;
            }
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
        }
        final String systemProperty = getSystemProperty("org.apache.commons.logging.LogFactory", null);
        if (systemProperty != null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[LOOKUP] Creating an instance of LogFactory class '" + systemProperty + "' as specified by system property " + "org.apache.commons.logging.LogFactory");
            }
            logFactory = newFactory(systemProperty, thisClassLoader, contextClassLoaderInternal);
        }
        else if (isDiagnosticsEnabled()) {
            logDiagnostic("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
        }
        if (logFactory == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
            }
            final InputStream resourceAsStream = getResourceAsStream(contextClassLoaderInternal, "META-INF/services/org.apache.commons.logging.LogFactory");
            if (resourceAsStream != null) {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
                final String line = bufferedReader.readLine();
                bufferedReader.close();
                if (line != null && !"".equals(line)) {
                    if (isDiagnosticsEnabled()) {
                        logDiagnostic("[LOOKUP]  Creating an instance of LogFactory class " + line + " as specified by file '" + "META-INF/services/org.apache.commons.logging.LogFactory" + "' which was present in the path of the context classloader.");
                    }
                    logFactory = newFactory(line, thisClassLoader, contextClassLoaderInternal);
                }
            }
            else if (isDiagnosticsEnabled()) {
                logDiagnostic("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
            }
        }
        if (logFactory == null) {
            if (configurationFile != null) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
                }
                final String property2 = configurationFile.getProperty("org.apache.commons.logging.LogFactory");
                if (property2 != null) {
                    if (isDiagnosticsEnabled()) {
                        logDiagnostic("[LOOKUP] Properties file specifies LogFactory subclass '" + property2 + "'");
                    }
                    logFactory = newFactory(property2, thisClassLoader, contextClassLoaderInternal);
                }
                else if (isDiagnosticsEnabled()) {
                    logDiagnostic("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
                }
            }
            else if (isDiagnosticsEnabled()) {
                logDiagnostic("[LOOKUP] No properties file available to determine LogFactory subclass from..");
            }
        }
        if (logFactory == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
            }
            logFactory = newFactory("org.apache.commons.logging.impl.LogFactoryImpl", LogFactory.thisClassLoader, contextClassLoaderInternal);
        }
        if (logFactory != null) {
            cacheFactory(contextClassLoaderInternal, logFactory);
            if (configurationFile != null) {
                final Enumeration<?> propertyNames = configurationFile.propertyNames();
                while (propertyNames.hasMoreElements()) {
                    final String s = (String)propertyNames.nextElement();
                    logFactory.setAttribute(s, configurationFile.getProperty(s));
                }
            }
        }
        return logFactory;
    }
    
    public static Log getLog(final Class clazz) throws LogConfigurationException {
        return getFactory().getInstance(clazz);
    }
    
    public static Log getLog(final String s) throws LogConfigurationException {
        return getFactory().getInstance(s);
    }
    
    public static void release(final ClassLoader classLoader) {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Releasing factory for classloader " + objectId(classLoader));
        }
        final Hashtable factories = LogFactory.factories;
        // monitorenter(hashtable = factories)
        if (classLoader == null) {
            if (LogFactory.nullClassLoaderFactory != null) {
                LogFactory.nullClassLoaderFactory.release();
                LogFactory.nullClassLoaderFactory = null;
            }
        }
        else {
            final LogFactory logFactory = factories.get(classLoader);
            if (logFactory != null) {
                logFactory.release();
                factories.remove(classLoader);
            }
        }
    }
    // monitorexit(hashtable)
    
    public static void releaseAll() {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Releasing factory for all classloaders.");
        }
        final Hashtable factories = LogFactory.factories;
        // monitorenter(hashtable = factories)
        final Enumeration<LogFactory> elements = factories.elements();
        while (elements.hasMoreElements()) {
            elements.nextElement().release();
        }
        factories.clear();
        if (LogFactory.nullClassLoaderFactory != null) {
            LogFactory.nullClassLoaderFactory.release();
            LogFactory.nullClassLoaderFactory = null;
        }
    }
    // monitorexit(hashtable)
    
    protected static ClassLoader getClassLoader(final Class clazz) {
        return clazz.getClassLoader();
    }
    
    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return directGetContextClassLoader();
    }
    
    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
            public Object run() {
                return LogFactory.directGetContextClassLoader();
            }
        });
    }
    
    protected static ClassLoader directGetContextClassLoader() throws LogConfigurationException {
        return (ClassLoader)((LogFactory.class$java$lang$Thread == null) ? (LogFactory.class$java$lang$Thread = class$("java.lang.Thread")) : LogFactory.class$java$lang$Thread).getMethod("getContextClassLoader", (Class[])null).invoke(Thread.currentThread(), (Object[])null);
    }
    
    private static LogFactory getCachedFactory(final ClassLoader classLoader) {
        if (classLoader == null) {
            return LogFactory.nullClassLoaderFactory;
        }
        return LogFactory.factories.get(classLoader);
    }
    
    private static void cacheFactory(final ClassLoader classLoader, final LogFactory nullClassLoaderFactory) {
        if (nullClassLoaderFactory != null) {
            if (classLoader == null) {
                LogFactory.nullClassLoaderFactory = nullClassLoaderFactory;
            }
            else {
                LogFactory.factories.put(classLoader, nullClassLoaderFactory);
            }
        }
    }
    
    protected static LogFactory newFactory(final String s, final ClassLoader classLoader, final ClassLoader classLoader2) throws LogConfigurationException {
        final LogConfigurationException doPrivileged = AccessController.doPrivileged((PrivilegedAction<LogConfigurationException>)new PrivilegedAction(s, classLoader) {
            private final String val$factoryClass = val$factoryClass;
            private final ClassLoader val$classLoader = val$classLoader;
            
            public Object run() {
                return LogFactory.createFactory(this.val$factoryClass, this.val$classLoader);
            }
        });
        if (doPrivileged instanceof LogConfigurationException) {
            final LogConfigurationException ex = doPrivileged;
            if (isDiagnosticsEnabled()) {
                logDiagnostic("An error occurred while loading the factory class:" + ex.getMessage());
            }
            throw ex;
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Created object " + objectId(doPrivileged) + " to manage classloader " + objectId(classLoader2));
        }
        return (LogFactory)doPrivileged;
    }
    
    protected static LogFactory newFactory(final String s, final ClassLoader classLoader) {
        return newFactory(s, classLoader, null);
    }
    
    protected static Object createFactory(final String s, final ClassLoader classLoader) {
        if (classLoader != null) {
            final Class<?> loadClass = classLoader.loadClass(s);
            if (((LogFactory.class$org$apache$commons$logging$LogFactory == null) ? (LogFactory.class$org$apache$commons$logging$LogFactory = class$("org.apache.commons.logging.LogFactory")) : LogFactory.class$org$apache$commons$logging$LogFactory).isAssignableFrom(loadClass)) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("Loaded class " + loadClass.getName() + " from classloader " + objectId(classLoader));
                }
            }
            else if (isDiagnosticsEnabled()) {
                logDiagnostic("Factory class " + loadClass.getName() + " loaded from classloader " + objectId(loadClass.getClassLoader()) + " does not extend '" + ((LogFactory.class$org$apache$commons$logging$LogFactory == null) ? (LogFactory.class$org$apache$commons$logging$LogFactory = class$("org.apache.commons.logging.LogFactory")) : LogFactory.class$org$apache$commons$logging$LogFactory).getName() + "' as loaded by this classloader.");
                logHierarchy("[BAD CL TREE] ", classLoader);
            }
            return (LogFactory)loadClass.newInstance();
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Unable to load factory class via classloader " + objectId(classLoader) + " - trying the classloader associated with this LogFactory.");
        }
        return (LogFactory)Class.forName(s).newInstance();
    }
    
    private static boolean implementsLogFactory(final Class clazz) {
        if (clazz != null) {
            final ClassLoader classLoader = clazz.getClassLoader();
            if (classLoader == null) {
                logDiagnostic("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
            }
            else {
                logHierarchy("[CUSTOM LOG FACTORY] ", classLoader);
                Class.forName("org.apache.commons.logging.LogFactory", false, classLoader).isAssignableFrom(clazz);
                if (false) {
                    logDiagnostic("[CUSTOM LOG FACTORY] " + clazz.getName() + " implements LogFactory but was loaded by an incompatible classloader.");
                }
                else {
                    logDiagnostic("[CUSTOM LOG FACTORY] " + clazz.getName() + " does not implement LogFactory.");
                }
            }
        }
        return false;
    }
    
    private static InputStream getResourceAsStream(final ClassLoader classLoader, final String s) {
        return AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction(classLoader, s) {
            private final ClassLoader val$loader = val$loader;
            private final String val$name = val$name;
            
            public Object run() {
                if (this.val$loader != null) {
                    return this.val$loader.getResourceAsStream(this.val$name);
                }
                return ClassLoader.getSystemResourceAsStream(this.val$name);
            }
        });
    }
    
    private static Enumeration getResources(final ClassLoader classLoader, final String s) {
        return AccessController.doPrivileged((PrivilegedAction<Enumeration>)new PrivilegedAction(classLoader, s) {
            private final ClassLoader val$loader = val$loader;
            private final String val$name = val$name;
            
            public Object run() {
                if (this.val$loader != null) {
                    return this.val$loader.getResources(this.val$name);
                }
                return ClassLoader.getSystemResources(this.val$name);
            }
        });
    }
    
    private static Properties getProperties(final URL url) {
        return AccessController.doPrivileged((PrivilegedAction<Properties>)new PrivilegedAction(url) {
            private final URL val$url = val$url;
            
            public Object run() {
                final URLConnection openConnection = this.val$url.openConnection();
                openConnection.setUseCaches(false);
                final InputStream inputStream = openConnection.getInputStream();
                if (inputStream != null) {
                    final Properties properties = new Properties();
                    properties.load(inputStream);
                    inputStream.close();
                    final InputStream inputStream2 = null;
                    final Properties properties2 = properties;
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    return properties2;
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return null;
            }
        });
    }
    
    private static final Properties getConfigurationFile(final ClassLoader classLoader, final String s) {
        Properties properties = null;
        double double1 = 0.0;
        Object o = null;
        final Enumeration resources = getResources(classLoader, s);
        if (resources == null) {
            return null;
        }
        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            final Properties properties2 = getProperties(url);
            if (properties2 != null) {
                if (properties == null) {
                    o = url;
                    properties = properties2;
                    final String property = properties.getProperty("priority");
                    double1 = 0.0;
                    if (property != null) {
                        double1 = Double.parseDouble(property);
                    }
                    if (!isDiagnosticsEnabled()) {
                        continue;
                    }
                    logDiagnostic("[LOOKUP] Properties file found at '" + url + "'" + " with priority " + double1);
                }
                else {
                    final String property2 = properties2.getProperty("priority");
                    double double2 = 0.0;
                    if (property2 != null) {
                        double2 = Double.parseDouble(property2);
                    }
                    if (double2 > double1) {
                        if (isDiagnosticsEnabled()) {
                            logDiagnostic("[LOOKUP] Properties file at '" + url + "'" + " with priority " + double2 + " overrides file at '" + o + "'" + " with priority " + double1);
                        }
                        o = url;
                        properties = properties2;
                        double1 = double2;
                    }
                    else {
                        if (!isDiagnosticsEnabled()) {
                            continue;
                        }
                        logDiagnostic("[LOOKUP] Properties file at '" + url + "'" + " with priority " + double2 + " does not override file at '" + o + "'" + " with priority " + double1);
                    }
                }
            }
        }
        if (isDiagnosticsEnabled()) {
            if (properties == null) {
                logDiagnostic("[LOOKUP] No properties file of name '" + s + "' found.");
            }
            else {
                logDiagnostic("[LOOKUP] Properties file of name '" + s + "' found at '" + o + '\"');
            }
        }
        return properties;
    }
    
    private static String getSystemProperty(final String s, final String s2) throws SecurityException {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s, s2) {
            private final String val$key = val$key;
            private final String val$def = val$def;
            
            public Object run() {
                return System.getProperty(this.val$key, this.val$def);
            }
        });
    }
    
    private static PrintStream initDiagnostics() {
        final String systemProperty = getSystemProperty("org.apache.commons.logging.diagnostics.dest", null);
        if (systemProperty == null) {
            return null;
        }
        if (systemProperty.equals("STDOUT")) {
            return System.out;
        }
        if (systemProperty.equals("STDERR")) {
            return System.err;
        }
        return new PrintStream(new FileOutputStream(systemProperty, true));
    }
    
    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.diagnosticsStream != null;
    }
    
    private static final void logDiagnostic(final String s) {
        if (LogFactory.diagnosticsStream != null) {
            LogFactory.diagnosticsStream.print(LogFactory.diagnosticPrefix);
            LogFactory.diagnosticsStream.println(s);
            LogFactory.diagnosticsStream.flush();
        }
    }
    
    protected static final void logRawDiagnostic(final String s) {
        if (LogFactory.diagnosticsStream != null) {
            LogFactory.diagnosticsStream.println(s);
            LogFactory.diagnosticsStream.flush();
        }
    }
    
    private static void logClassLoaderEnvironment(final Class clazz) {
        if (!isDiagnosticsEnabled()) {
            return;
        }
        logDiagnostic("[ENV] Extension directories (java.ext.dir): " + System.getProperty("java.ext.dir"));
        logDiagnostic("[ENV] Application classpath (java.class.path): " + System.getProperty("java.class.path"));
        final String name = clazz.getName();
        final ClassLoader classLoader = getClassLoader(clazz);
        logDiagnostic("[ENV] Class " + name + " was loaded via classloader " + objectId(classLoader));
        logHierarchy("[ENV] Ancestry of classloader which loaded " + name + " is ", classLoader);
    }
    
    private static void logHierarchy(final String s, ClassLoader parent) {
        if (!isDiagnosticsEnabled()) {
            return;
        }
        if (parent != null) {
            logDiagnostic(s + objectId(parent) + " == '" + parent.toString() + "'");
        }
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (parent != null) {
            final StringBuffer sb = new StringBuffer(s + "ClassLoader tree:");
            do {
                sb.append(objectId(parent));
                if (parent == systemClassLoader) {
                    sb.append(" (SYSTEM) ");
                }
                parent = parent.getParent();
                sb.append(" --> ");
            } while (parent != null);
            sb.append("BOOT");
            logDiagnostic(sb.toString());
        }
    }
    
    public static String objectId(final Object o) {
        if (o == null) {
            return "null";
        }
        return o.getClass().getName() + "@" + System.identityHashCode(o);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static void access$000(final String s) {
        logDiagnostic(s);
    }
    
    static {
        LogFactory.diagnosticsStream = null;
        LogFactory.factories = null;
        LogFactory.nullClassLoaderFactory = null;
        thisClassLoader = getClassLoader((LogFactory.class$org$apache$commons$logging$LogFactory == null) ? (LogFactory.class$org$apache$commons$logging$LogFactory = class$("org.apache.commons.logging.LogFactory")) : LogFactory.class$org$apache$commons$logging$LogFactory);
        final ClassLoader thisClassLoader2 = LogFactory.thisClassLoader;
        String objectId;
        if (LogFactory.thisClassLoader == null) {
            objectId = "BOOTLOADER";
        }
        else {
            objectId = objectId(thisClassLoader2);
        }
        diagnosticPrefix = "[LogFactory from " + objectId + "] ";
        LogFactory.diagnosticsStream = initDiagnostics();
        logClassLoaderEnvironment((LogFactory.class$org$apache$commons$logging$LogFactory == null) ? (LogFactory.class$org$apache$commons$logging$LogFactory = class$("org.apache.commons.logging.LogFactory")) : LogFactory.class$org$apache$commons$logging$LogFactory);
        LogFactory.factories = createFactoryStore();
        if (isDiagnosticsEnabled()) {
            logDiagnostic("BOOTSTRAP COMPLETED");
        }
    }
}
