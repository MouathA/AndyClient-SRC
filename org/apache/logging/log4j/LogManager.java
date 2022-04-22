package org.apache.logging.log4j;

import java.net.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.simple.*;
import java.util.*;

public class LogManager
{
    private static LoggerContextFactory factory;
    private static final String FACTORY_PROPERTY_NAME = "log4j2.loggerContextFactory";
    private static final Logger LOGGER;
    public static final String ROOT_LOGGER_NAME = "";
    
    private static String getClassName(final int n) {
        return new Throwable().getStackTrace()[n].getClassName();
    }
    
    public static LoggerContext getContext() {
        return LogManager.factory.getContext(LogManager.class.getName(), null, true);
    }
    
    public static LoggerContext getContext(final boolean b) {
        return LogManager.factory.getContext(LogManager.class.getName(), null, b);
    }
    
    public static LoggerContext getContext(final ClassLoader classLoader, final boolean b) {
        return LogManager.factory.getContext(LogManager.class.getName(), classLoader, b);
    }
    
    public static LoggerContext getContext(final ClassLoader classLoader, final boolean b, final URI uri) {
        return LogManager.factory.getContext(LogManager.class.getName(), classLoader, b, uri);
    }
    
    protected static LoggerContext getContext(final String s, final boolean b) {
        return LogManager.factory.getContext(s, null, b);
    }
    
    protected static LoggerContext getContext(final String s, final ClassLoader classLoader, final boolean b) {
        return LogManager.factory.getContext(s, classLoader, b);
    }
    
    public static LoggerContextFactory getFactory() {
        return LogManager.factory;
    }
    
    public static Logger getFormatterLogger(final Class clazz) {
        return getLogger((clazz != null) ? clazz.getName() : getClassName(2), StringFormatterMessageFactory.INSTANCE);
    }
    
    public static Logger getFormatterLogger(final Object o) {
        return getLogger((o != null) ? o.getClass().getName() : getClassName(2), StringFormatterMessageFactory.INSTANCE);
    }
    
    public static Logger getFormatterLogger(final String s) {
        return getLogger((s != null) ? s : getClassName(2), StringFormatterMessageFactory.INSTANCE);
    }
    
    public static Logger getLogger() {
        return getLogger(getClassName(2));
    }
    
    public static Logger getLogger(final Class clazz) {
        return getLogger((clazz != null) ? clazz.getName() : getClassName(2));
    }
    
    public static Logger getLogger(final Class clazz, final MessageFactory messageFactory) {
        return getLogger((clazz != null) ? clazz.getName() : getClassName(2), messageFactory);
    }
    
    public static Logger getLogger(final MessageFactory messageFactory) {
        return getLogger(getClassName(2), messageFactory);
    }
    
    public static Logger getLogger(final Object o) {
        return getLogger((o != null) ? o.getClass().getName() : getClassName(2));
    }
    
    public static Logger getLogger(final Object o, final MessageFactory messageFactory) {
        return getLogger((o != null) ? o.getClass().getName() : getClassName(2), messageFactory);
    }
    
    public static Logger getLogger(final String s) {
        return LogManager.factory.getContext(LogManager.class.getName(), null, false).getLogger((s != null) ? s : getClassName(2));
    }
    
    public static Logger getLogger(final String s, final MessageFactory messageFactory) {
        return LogManager.factory.getContext(LogManager.class.getName(), null, false).getLogger((s != null) ? s : getClassName(2), messageFactory);
    }
    
    protected static Logger getLogger(final String s, final String s2) {
        return LogManager.factory.getContext(s, null, false).getLogger(s2);
    }
    
    public static Logger getRootLogger() {
        return getLogger("");
    }
    
    protected LogManager() {
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("log4j2.loggerContextFactory");
        final ClassLoader classLoader = ProviderUtil.findClassLoader();
        if (stringProperty != null) {
            final Class<?> loadClass = classLoader.loadClass(stringProperty);
            if (LoggerContextFactory.class.isAssignableFrom(loadClass)) {
                LogManager.factory = (LoggerContextFactory)loadClass.newInstance();
            }
        }
        if (LogManager.factory == null) {
            final TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
            if (ProviderUtil.hasProviders()) {
                final Iterator providers = ProviderUtil.getProviders();
                while (providers.hasNext()) {
                    final Provider provider = providers.next();
                    final String className = provider.getClassName();
                    if (className != null) {
                        final Class<?> loadClass2 = classLoader.loadClass(className);
                        if (LoggerContextFactory.class.isAssignableFrom(loadClass2)) {
                            treeMap.put(provider.getPriority(), loadClass2.newInstance());
                        }
                        else {
                            LogManager.LOGGER.error(className + " does not implement " + LoggerContextFactory.class.getName());
                        }
                    }
                }
                if (treeMap.size() == 0) {
                    LogManager.LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
                    LogManager.factory = new SimpleLoggerContextFactory();
                }
                else {
                    final StringBuilder sb = new StringBuilder("Multiple logging implementations found: \n");
                    for (final Map.Entry<Object, LoggerContextFactory> entry : treeMap.entrySet()) {
                        sb.append("Factory: ").append(entry.getValue().getClass().getName());
                        sb.append(", Weighting: ").append(entry.getKey()).append("\n");
                    }
                    LogManager.factory = (LoggerContextFactory)treeMap.get(treeMap.lastKey());
                    sb.append("Using factory: ").append(LogManager.factory.getClass().getName());
                    LogManager.LOGGER.warn(sb.toString());
                }
            }
            else {
                LogManager.LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
                LogManager.factory = new SimpleLoggerContextFactory();
            }
        }
    }
}
