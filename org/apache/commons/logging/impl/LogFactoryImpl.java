package org.apache.commons.logging.impl;

import java.util.*;
import org.apache.commons.logging.*;
import java.security.*;
import java.net.*;
import java.lang.reflect.*;
import java.io.*;

public class LogFactoryImpl extends LogFactory
{
    private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
    private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
    private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
    private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
    private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
    public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
    protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
    public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
    public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
    public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
    private boolean useTCCL;
    private String diagnosticPrefix;
    protected Hashtable attributes;
    protected Hashtable instances;
    private String logClassName;
    protected Constructor logConstructor;
    protected Class[] logConstructorSignature;
    protected Method logMethod;
    protected Class[] logMethodSignature;
    private boolean allowFlawedContext;
    private boolean allowFlawedDiscovery;
    private boolean allowFlawedHierarchy;
    static Class class$java$lang$String;
    static Class class$org$apache$commons$logging$LogFactory;
    static Class class$org$apache$commons$logging$impl$LogFactoryImpl;
    static Class class$org$apache$commons$logging$Log;
    
    public LogFactoryImpl() {
        this.useTCCL = true;
        this.attributes = new Hashtable();
        this.instances = new Hashtable();
        this.logConstructor = null;
        this.logConstructorSignature = new Class[] { (LogFactoryImpl.class$java$lang$String == null) ? (LogFactoryImpl.class$java$lang$String = class$("java.lang.String")) : LogFactoryImpl.class$java$lang$String };
        this.logMethod = null;
        this.logMethodSignature = new Class[] { (LogFactoryImpl.class$org$apache$commons$logging$LogFactory == null) ? (LogFactoryImpl.class$org$apache$commons$logging$LogFactory = class$("org.apache.commons.logging.LogFactory")) : LogFactoryImpl.class$org$apache$commons$logging$LogFactory };
        this.initDiagnostics();
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Instance created.");
        }
    }
    
    public Object getAttribute(final String s) {
        return this.attributes.get(s);
    }
    
    public String[] getAttributeNames() {
        return (String[])this.attributes.keySet().toArray(new String[this.attributes.size()]);
    }
    
    public Log getInstance(final Class clazz) throws LogConfigurationException {
        return this.getInstance(clazz.getName());
    }
    
    public Log getInstance(final String s) throws LogConfigurationException {
        Log instance = this.instances.get(s);
        if (instance == null) {
            instance = this.newInstance(s);
            this.instances.put(s, instance);
        }
        return instance;
    }
    
    public void release() {
        this.logDiagnostic("Releasing all known loggers");
        this.instances.clear();
    }
    
    public void removeAttribute(final String s) {
        this.attributes.remove(s);
    }
    
    public void setAttribute(final String s, final Object o) {
        if (this.logConstructor != null) {
            this.logDiagnostic("setAttribute: call too late; configuration already performed.");
        }
        if (o == null) {
            this.attributes.remove(s);
        }
        else {
            this.attributes.put(s, o);
        }
        if (s.equals("use_tccl")) {
            this.useTCCL = (o != null && Boolean.valueOf(o.toString()));
        }
    }
    
    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.getContextClassLoader();
    }
    
    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.isDiagnosticsEnabled();
    }
    
    protected static ClassLoader getClassLoader(final Class clazz) {
        return LogFactory.getClassLoader(clazz);
    }
    
    private void initDiagnostics() {
        final ClassLoader classLoader = getClassLoader(this.getClass());
        String objectId;
        if (classLoader == null) {
            objectId = "BOOTLOADER";
        }
        else {
            objectId = LogFactory.objectId(classLoader);
        }
        this.diagnosticPrefix = "[LogFactoryImpl@" + System.identityHashCode(this) + " from " + objectId + "] ";
    }
    
    protected void logDiagnostic(final String s) {
        if (isDiagnosticsEnabled()) {
            LogFactory.logRawDiagnostic(this.diagnosticPrefix + s);
        }
    }
    
    protected String getLogClassName() {
        if (this.logClassName == null) {
            this.discoverLogImplementation(this.getClass().getName());
        }
        return this.logClassName;
    }
    
    protected Constructor getLogConstructor() throws LogConfigurationException {
        if (this.logConstructor == null) {
            this.discoverLogImplementation(this.getClass().getName());
        }
        return this.logConstructor;
    }
    
    protected boolean isJdk13LumberjackAvailable() {
        return this.isLogLibraryAvailable("Jdk13Lumberjack", "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
    }
    
    protected boolean isJdk14Available() {
        return this.isLogLibraryAvailable("Jdk14", "org.apache.commons.logging.impl.Jdk14Logger");
    }
    
    protected boolean isLog4JAvailable() {
        return this.isLogLibraryAvailable("Log4J", "org.apache.commons.logging.impl.Log4JLogger");
    }
    
    protected Log newInstance(final String s) throws LogConfigurationException {
        Log discoverLogImplementation;
        if (this.logConstructor == null) {
            discoverLogImplementation = this.discoverLogImplementation(s);
        }
        else {
            discoverLogImplementation = this.logConstructor.newInstance(s);
        }
        if (this.logMethod != null) {
            this.logMethod.invoke(discoverLogImplementation, this);
        }
        return discoverLogImplementation;
    }
    
    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
            public Object run() {
                return LogFactoryImpl.access$000();
            }
        });
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
    
    private ClassLoader getParentClassLoader(final ClassLoader classLoader) {
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction(classLoader) {
            private final ClassLoader val$cl = val$cl;
            private final LogFactoryImpl this$0 = this$0;
            
            public Object run() {
                return this.val$cl.getParent();
            }
        });
    }
    
    private boolean isLogLibraryAvailable(final String s, final String s2) {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Checking for '" + s + "'.");
        }
        if (this.createLogFromClass(s2, this.getClass().getName(), false) == null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Did not find '" + s + "'.");
            }
            return false;
        }
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Found '" + s + "'.");
        }
        return true;
    }
    
    private String getConfigurationValue(final String s) {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("[ENV] Trying to get configuration for item " + s);
        }
        final Object attribute = this.getAttribute(s);
        if (attribute != null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("[ENV] Found LogFactory attribute [" + attribute + "] for " + s);
            }
            return attribute.toString();
        }
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("[ENV] No LogFactory attribute found for " + s);
        }
        final String systemProperty = getSystemProperty(s, null);
        if (systemProperty != null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("[ENV] Found system property [" + systemProperty + "] for " + s);
            }
            return systemProperty;
        }
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("[ENV] No system property found for property " + s);
        }
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("[ENV] No configuration defined for item " + s);
        }
        return null;
    }
    
    private boolean getBooleanConfiguration(final String s, final boolean b) {
        final String configurationValue = this.getConfigurationValue(s);
        if (configurationValue == null) {
            return b;
        }
        return Boolean.valueOf(configurationValue);
    }
    
    private void initConfiguration() {
        this.allowFlawedContext = this.getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedContext", true);
        this.allowFlawedDiscovery = this.getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedDiscovery", true);
        this.allowFlawedHierarchy = this.getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedHierarchy", true);
    }
    
    private Log discoverLogImplementation(final String s) throws LogConfigurationException {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Discovering a Log implementation...");
        }
        this.initConfiguration();
        Log logFromClass = null;
        final String userSpecifiedLogClassName = this.findUserSpecifiedLogClassName();
        if (userSpecifiedLogClassName != null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Attempting to load user-specified log class '" + userSpecifiedLogClassName + "'...");
            }
            final Log logFromClass2 = this.createLogFromClass(userSpecifiedLogClassName, s, true);
            if (logFromClass2 == null) {
                final StringBuffer sb = new StringBuffer("User-specified log class '");
                sb.append(userSpecifiedLogClassName);
                sb.append("' cannot be found or is not useable.");
                this.informUponSimilarName(sb, userSpecifiedLogClassName, "org.apache.commons.logging.impl.Log4JLogger");
                this.informUponSimilarName(sb, userSpecifiedLogClassName, "org.apache.commons.logging.impl.Jdk14Logger");
                this.informUponSimilarName(sb, userSpecifiedLogClassName, "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
                this.informUponSimilarName(sb, userSpecifiedLogClassName, "org.apache.commons.logging.impl.SimpleLog");
                throw new LogConfigurationException(sb.toString());
            }
            return logFromClass2;
        }
        else {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
            }
            while (0 < LogFactoryImpl.classesToDiscover.length && logFromClass == null) {
                logFromClass = this.createLogFromClass(LogFactoryImpl.classesToDiscover[0], s, true);
                int n = 0;
                ++n;
            }
            if (logFromClass == null) {
                throw new LogConfigurationException("No suitable Log implementation");
            }
            return logFromClass;
        }
    }
    
    private void informUponSimilarName(final StringBuffer sb, final String s, final String s2) {
        if (s.equals(s2)) {
            return;
        }
        if (s.regionMatches(true, 0, s2, 0, 37)) {
            sb.append(" Did you mean '");
            sb.append(s2);
            sb.append("'?");
        }
    }
    
    private String findUserSpecifiedLogClassName() {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
        }
        String s = (String)this.getAttribute("org.apache.commons.logging.Log");
        if (s == null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
            }
            s = (String)this.getAttribute("org.apache.commons.logging.log");
        }
        if (s == null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
            }
            s = getSystemProperty("org.apache.commons.logging.Log", null);
        }
        if (s == null) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
            }
            s = getSystemProperty("org.apache.commons.logging.log", null);
        }
        if (s != null) {
            s = s.trim();
        }
        return s;
    }
    
    private Log createLogFromClass(final String logClassName, final String s, final boolean b) throws LogConfigurationException {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Attempting to instantiate '" + logClassName + "'");
        }
        final Object[] array = { s };
        Log log = null;
        Class<?> clazz = null;
        ClassLoader classLoader = this.getBaseClassLoader();
        Constructor<?> constructor;
        while (true) {
            this.logDiagnostic("Trying to load '" + logClassName + "' from classloader " + LogFactory.objectId(classLoader));
            if (isDiagnosticsEnabled()) {
                final String string = logClassName.replace('.', '/') + ".class";
                URL url;
                if (classLoader != null) {
                    url = classLoader.getResource(string);
                }
                else {
                    url = ClassLoader.getSystemResource(string + ".class");
                }
                if (url == null) {
                    this.logDiagnostic("Class '" + logClassName + "' [" + string + "] cannot be found.");
                }
                else {
                    this.logDiagnostic("Class '" + logClassName + "' was found at '" + url + "'");
                }
            }
            final Class<?> forName = Class.forName(logClassName, true, classLoader);
            constructor = forName.getConstructor((Class<?>[])this.logConstructorSignature);
            final Object instance = constructor.newInstance(array);
            if (instance instanceof Log) {
                clazz = forName;
                log = (Log)instance;
                break;
            }
            this.handleFlawedHierarchy(classLoader, forName);
            if (classLoader == null) {
                break;
            }
            classLoader = this.getParentClassLoader(classLoader);
        }
        if (clazz != null && b) {
            this.logClassName = logClassName;
            this.logConstructor = constructor;
            this.logMethod = clazz.getMethod("setLogFactory", (Class[])this.logMethodSignature);
            this.logDiagnostic("Found method setLogFactory(LogFactory) in '" + logClassName + "'");
            this.logDiagnostic("Log adapter '" + logClassName + "' from classloader " + LogFactory.objectId(clazz.getClassLoader()) + " has been selected for use.");
        }
        return log;
    }
    
    private ClassLoader getBaseClassLoader() throws LogConfigurationException {
        final ClassLoader classLoader = getClassLoader((LogFactoryImpl.class$org$apache$commons$logging$impl$LogFactoryImpl == null) ? (LogFactoryImpl.class$org$apache$commons$logging$impl$LogFactoryImpl = class$("org.apache.commons.logging.impl.LogFactoryImpl")) : LogFactoryImpl.class$org$apache$commons$logging$impl$LogFactoryImpl);
        if (!this.useTCCL) {
            return classLoader;
        }
        final ClassLoader contextClassLoaderInternal = getContextClassLoaderInternal();
        final ClassLoader lowestClassLoader = this.getLowestClassLoader(contextClassLoaderInternal, classLoader);
        if (lowestClassLoader != null) {
            if (lowestClassLoader != contextClassLoaderInternal) {
                if (!this.allowFlawedContext) {
                    throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
                }
                if (isDiagnosticsEnabled()) {
                    this.logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
                }
            }
            return lowestClassLoader;
        }
        if (this.allowFlawedContext) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
            }
            return contextClassLoaderInternal;
        }
        throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
    }
    
    private ClassLoader getLowestClassLoader(final ClassLoader classLoader, final ClassLoader classLoader2) {
        if (classLoader == null) {
            return classLoader2;
        }
        if (classLoader2 == null) {
            return classLoader;
        }
        for (ClassLoader parentClassLoader = classLoader; parentClassLoader != null; parentClassLoader = this.getParentClassLoader(parentClassLoader)) {
            if (parentClassLoader == classLoader2) {
                return classLoader;
            }
        }
        for (ClassLoader parentClassLoader2 = classLoader2; parentClassLoader2 != null; parentClassLoader2 = this.getParentClassLoader(parentClassLoader2)) {
            if (parentClassLoader2 == classLoader) {
                return classLoader2;
            }
        }
        return null;
    }
    
    private void handleFlawedDiscovery(final String s, final ClassLoader classLoader, final Throwable t) {
        if (isDiagnosticsEnabled()) {
            this.logDiagnostic("Could not instantiate Log '" + s + "' -- " + t.getClass().getName() + ": " + t.getLocalizedMessage());
            if (t instanceof InvocationTargetException) {
                final Throwable targetException = ((InvocationTargetException)t).getTargetException();
                if (targetException != null) {
                    this.logDiagnostic("... InvocationTargetException: " + ((ExceptionInInitializerError)targetException).getClass().getName() + ": " + targetException.getLocalizedMessage());
                    if (targetException instanceof ExceptionInInitializerError) {
                        final Throwable exception = ((ExceptionInInitializerError)targetException).getException();
                        if (exception != null) {
                            final StringWriter stringWriter = new StringWriter();
                            exception.printStackTrace(new PrintWriter(stringWriter, true));
                            this.logDiagnostic("... ExceptionInInitializerError: " + stringWriter.toString());
                        }
                    }
                }
            }
        }
        if (!this.allowFlawedDiscovery) {
            throw new LogConfigurationException(t);
        }
    }
    
    private void handleFlawedHierarchy(final ClassLoader classLoader, final Class clazz) throws LogConfigurationException {
        final String name = ((LogFactoryImpl.class$org$apache$commons$logging$Log == null) ? (LogFactoryImpl.class$org$apache$commons$logging$Log = class$("org.apache.commons.logging.Log")) : LogFactoryImpl.class$org$apache$commons$logging$Log).getName();
        final Class[] interfaces = clazz.getInterfaces();
        while (0 < interfaces.length && !name.equals(interfaces[0].getName())) {
            int n = 0;
            ++n;
        }
        if (true) {
            if (isDiagnosticsEnabled()) {
                this.logDiagnostic("Class '" + clazz.getName() + "' was found in classloader " + LogFactory.objectId(classLoader) + ". It is bound to a Log interface which is not" + " the one loaded from classloader " + LogFactory.objectId(getClassLoader((LogFactoryImpl.class$org$apache$commons$logging$Log == null) ? (LogFactoryImpl.class$org$apache$commons$logging$Log = class$("org.apache.commons.logging.Log")) : LogFactoryImpl.class$org$apache$commons$logging$Log)));
            }
            if (!this.allowFlawedHierarchy) {
                final StringBuffer sb = new StringBuffer();
                sb.append("Terminating logging for this context ");
                sb.append("due to bad log hierarchy. ");
                sb.append("You have more than one version of '");
                sb.append(((LogFactoryImpl.class$org$apache$commons$logging$Log == null) ? (LogFactoryImpl.class$org$apache$commons$logging$Log = class$("org.apache.commons.logging.Log")) : LogFactoryImpl.class$org$apache$commons$logging$Log).getName());
                sb.append("' visible.");
                if (isDiagnosticsEnabled()) {
                    this.logDiagnostic(sb.toString());
                }
                throw new LogConfigurationException(sb.toString());
            }
            if (isDiagnosticsEnabled()) {
                final StringBuffer sb2 = new StringBuffer();
                sb2.append("Warning: bad log hierarchy. ");
                sb2.append("You have more than one version of '");
                sb2.append(((LogFactoryImpl.class$org$apache$commons$logging$Log == null) ? (LogFactoryImpl.class$org$apache$commons$logging$Log = class$("org.apache.commons.logging.Log")) : LogFactoryImpl.class$org$apache$commons$logging$Log).getName());
                sb2.append("' visible.");
                this.logDiagnostic(sb2.toString());
            }
        }
        else {
            if (!this.allowFlawedDiscovery) {
                final StringBuffer sb3 = new StringBuffer();
                sb3.append("Terminating logging for this context. ");
                sb3.append("Log class '");
                sb3.append(clazz.getName());
                sb3.append("' does not implement the Log interface.");
                if (isDiagnosticsEnabled()) {
                    this.logDiagnostic(sb3.toString());
                }
                throw new LogConfigurationException(sb3.toString());
            }
            if (isDiagnosticsEnabled()) {
                final StringBuffer sb4 = new StringBuffer();
                sb4.append("[WARNING] Log class '");
                sb4.append(clazz.getName());
                sb4.append("' does not implement the Log interface.");
                this.logDiagnostic(sb4.toString());
            }
        }
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static ClassLoader access$000() throws LogConfigurationException {
        return LogFactory.directGetContextClassLoader();
    }
    
    static {
        LogFactoryImpl.classesToDiscover = new String[] { "org.apache.commons.logging.impl.Log4JLogger", "org.apache.commons.logging.impl.Jdk14Logger", "org.apache.commons.logging.impl.Jdk13LumberjackLogger", "org.apache.commons.logging.impl.SimpleLog" };
    }
}
