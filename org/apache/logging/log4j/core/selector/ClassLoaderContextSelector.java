package org.apache.logging.log4j.core.selector;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.core.*;
import java.net.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.helpers.*;
import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.*;

public class ClassLoaderContextSelector implements ContextSelector
{
    private static final AtomicReference CONTEXT;
    private static final PrivateSecurityManager SECURITY_MANAGER;
    private static final StatusLogger LOGGER;
    private static final ConcurrentMap CONTEXT_MAP;
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return this.getContext(s, classLoader, b, null);
    }
    
    @Override
    public LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b, final URI uri) {
        if (b) {
            final LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
            if (loggerContext != null) {
                return loggerContext;
            }
            return this.getDefault();
        }
        else {
            if (classLoader != null) {
                return this.locateContext(classLoader, uri);
            }
            if (ReflectiveCallerClassUtility.isSupported()) {
                Class<Class> caller = Class.class;
                while (caller != null) {
                    caller = (Class<Class>)ReflectiveCallerClassUtility.getCaller(2);
                    if (caller == null) {
                        break;
                    }
                    if (!caller.getName().equals(s)) {
                        if (true) {
                            break;
                        }
                    }
                    int n = 0;
                    ++n;
                }
                if (caller != null) {
                    return this.locateContext(caller.getClassLoader(), uri);
                }
            }
            if (ClassLoaderContextSelector.SECURITY_MANAGER != null) {
                final Class caller2 = ClassLoaderContextSelector.SECURITY_MANAGER.getCaller(s);
                if (caller2 != null) {
                    return this.locateContext((caller2.getClassLoader() != null) ? caller2.getClassLoader() : ClassLoader.getSystemClassLoader(), uri);
                }
            }
            final Throwable t = new Throwable();
            String className = null;
            final StackTraceElement[] stackTrace = t.getStackTrace();
            while (0 < stackTrace.length) {
                final StackTraceElement stackTraceElement = stackTrace[0];
                if (!stackTraceElement.getClassName().equals(s)) {
                    if (true) {
                        className = stackTraceElement.getClassName();
                        break;
                    }
                }
                int n2 = 0;
                ++n2;
            }
            if (className != null) {
                return this.locateContext(Loader.loadClass(className).getClassLoader(), uri);
            }
            final LoggerContext loggerContext2 = ContextAnchor.THREAD_CONTEXT.get();
            if (loggerContext2 != null) {
                return loggerContext2;
            }
            return this.getDefault();
        }
    }
    
    @Override
    public void removeContext(final LoggerContext loggerContext) {
        for (final Map.Entry<K, AtomicReference> entry : ClassLoaderContextSelector.CONTEXT_MAP.entrySet()) {
            if (((WeakReference<LoggerContext>)entry.getValue().get()).get() == loggerContext) {
                ClassLoaderContextSelector.CONTEXT_MAP.remove(entry.getKey());
            }
        }
    }
    
    @Override
    public List getLoggerContexts() {
        final ArrayList<LoggerContext> list = new ArrayList<LoggerContext>();
        final Iterator iterator = ClassLoaderContextSelector.CONTEXT_MAP.values().iterator();
        while (iterator.hasNext()) {
            final LoggerContext loggerContext = iterator.next().get().get();
            if (loggerContext != null) {
                list.add(loggerContext);
            }
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    private LoggerContext locateContext(final ClassLoader classLoader, final URI configLocation) {
        final String string = classLoader.toString();
        final AtomicReference atomicReference = (AtomicReference)ClassLoaderContextSelector.CONTEXT_MAP.get(string);
        if (atomicReference == null) {
            if (configLocation == null) {
                for (ClassLoader classLoader2 = classLoader.getParent(); classLoader2 != null; classLoader2 = classLoader2.getParent()) {
                    final AtomicReference atomicReference2 = (AtomicReference)ClassLoaderContextSelector.CONTEXT_MAP.get(classLoader2.toString());
                    if (atomicReference2 != null) {
                        final LoggerContext loggerContext = atomicReference2.get().get();
                        if (loggerContext != null) {
                            return loggerContext;
                        }
                    }
                }
            }
            final LoggerContext loggerContext2 = new LoggerContext(string, null, configLocation);
            final AtomicReference<WeakReference<Object>> atomicReference3 = new AtomicReference<WeakReference<Object>>();
            atomicReference3.set(new WeakReference<Object>(loggerContext2));
            ClassLoaderContextSelector.CONTEXT_MAP.putIfAbsent(classLoader.toString(), atomicReference3);
            return (LoggerContext)((WeakReference<LoggerContext>)((AtomicReference)ClassLoaderContextSelector.CONTEXT_MAP.get(string)).get()).get();
        }
        final WeakReference<LoggerContext> weakReference = atomicReference.get();
        final LoggerContext loggerContext3 = weakReference.get();
        if (loggerContext3 != null) {
            if (loggerContext3.getConfigLocation() == null && configLocation != null) {
                ClassLoaderContextSelector.LOGGER.debug("Setting configuration to {}", configLocation);
                loggerContext3.setConfigLocation(configLocation);
            }
            else if (loggerContext3.getConfigLocation() != null && configLocation != null && !loggerContext3.getConfigLocation().equals(configLocation)) {
                ClassLoaderContextSelector.LOGGER.warn("locateContext called with URI {}. Existing LoggerContext has URI {}", configLocation, loggerContext3.getConfigLocation());
            }
            return loggerContext3;
        }
        final LoggerContext loggerContext4 = new LoggerContext(string, null, configLocation);
        atomicReference.compareAndSet(weakReference, new WeakReference<LoggerContext>(loggerContext4));
        return loggerContext4;
    }
    
    private LoggerContext getDefault() {
        final LoggerContext loggerContext = ClassLoaderContextSelector.CONTEXT.get();
        if (loggerContext != null) {
            return loggerContext;
        }
        ClassLoaderContextSelector.CONTEXT.compareAndSet(null, new LoggerContext("Default"));
        return ClassLoaderContextSelector.CONTEXT.get();
    }
    
    static {
        CONTEXT = new AtomicReference();
        LOGGER = StatusLogger.getLogger();
        CONTEXT_MAP = new ConcurrentHashMap();
        if (ReflectiveCallerClassUtility.isSupported()) {
            SECURITY_MANAGER = null;
        }
        else {
            PrivateSecurityManager security_MANAGER = new PrivateSecurityManager(null);
            if (security_MANAGER.getCaller(ClassLoaderContextSelector.class.getName()) == null) {
                security_MANAGER = null;
                ClassLoaderContextSelector.LOGGER.error("Unable to obtain call stack from security manager.");
            }
            SECURITY_MANAGER = security_MANAGER;
        }
    }
    
    private static class PrivateSecurityManager extends SecurityManager
    {
        private PrivateSecurityManager() {
        }
        
        public Class getCaller(final String s) {
            final Class[] classContext = this.getClassContext();
            while (0 < classContext.length) {
                final Class clazz = classContext[0];
                if (!clazz.getName().equals(s)) {
                    if (true) {
                        return clazz;
                    }
                }
                int n = 0;
                ++n;
            }
            return null;
        }
        
        PrivateSecurityManager(final ClassLoaderContextSelector$1 object) {
            this();
        }
    }
}
