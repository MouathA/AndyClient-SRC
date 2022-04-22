package org.apache.logging.log4j;

import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.spi.*;
import java.util.*;
import java.io.*;

public final class ThreadContext
{
    public static final Map EMPTY_MAP;
    public static final ThreadContextStack EMPTY_STACK;
    private static final String DISABLE_MAP = "disableThreadContextMap";
    private static final String DISABLE_STACK = "disableThreadContextStack";
    private static final String DISABLE_ALL = "disableThreadContext";
    private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
    private static boolean all;
    private static boolean useMap;
    private static boolean useStack;
    private static ThreadContextMap contextMap;
    private static ThreadContextStack contextStack;
    private static final Logger LOGGER;
    
    private ThreadContext() {
    }
    
    public static void put(final String s, final String s2) {
        ThreadContext.contextMap.put(s, s2);
    }
    
    public static String get(final String s) {
        return ThreadContext.contextMap.get(s);
    }
    
    public static void remove(final String s) {
        ThreadContext.contextMap.remove(s);
    }
    
    public static void clear() {
        ThreadContext.contextMap.clear();
    }
    
    public static boolean containsKey(final String s) {
        return ThreadContext.contextMap.containsKey(s);
    }
    
    public static Map getContext() {
        return ThreadContext.contextMap.getCopy();
    }
    
    public static Map getImmutableContext() {
        final Map immutableMapOrNull = ThreadContext.contextMap.getImmutableMapOrNull();
        return (immutableMapOrNull == null) ? ThreadContext.EMPTY_MAP : immutableMapOrNull;
    }
    
    public static boolean isEmpty() {
        return ThreadContext.contextMap.isEmpty();
    }
    
    public static void clearStack() {
        ThreadContext.contextStack.clear();
    }
    
    public static ContextStack cloneStack() {
        return ThreadContext.contextStack.copy();
    }
    
    public static ContextStack getImmutableStack() {
        return ThreadContext.contextStack;
    }
    
    public static void setStack(final Collection collection) {
        if (collection.size() == 0 || !ThreadContext.useStack) {
            return;
        }
        ThreadContext.contextStack.clear();
        ThreadContext.contextStack.addAll(collection);
    }
    
    public static int getDepth() {
        return ThreadContext.contextStack.getDepth();
    }
    
    public static String pop() {
        return ThreadContext.contextStack.pop();
    }
    
    public static String peek() {
        return ThreadContext.contextStack.peek();
    }
    
    public static void push(final String s) {
        ThreadContext.contextStack.push(s);
    }
    
    public static void push(final String s, final Object... array) {
        ThreadContext.contextStack.push(ParameterizedMessage.format(s, array));
    }
    
    public static void removeStack() {
        ThreadContext.contextStack.clear();
    }
    
    public static void trim(final int n) {
        ThreadContext.contextStack.trim(n);
    }
    
    static {
        EMPTY_MAP = Collections.emptyMap();
        EMPTY_STACK = new MutableThreadContextStack(new ArrayList());
        LOGGER = StatusLogger.getLogger();
        final PropertiesUtil properties = PropertiesUtil.getProperties();
        ThreadContext.all = properties.getBooleanProperty("disableThreadContext");
        ThreadContext.useStack = (!properties.getBooleanProperty("disableThreadContextStack") && !ThreadContext.all);
        ThreadContext.contextStack = new DefaultThreadContextStack(ThreadContext.useStack);
        ThreadContext.useMap = (!properties.getBooleanProperty("disableThreadContextMap") && !ThreadContext.all);
        final String stringProperty = properties.getStringProperty("log4j2.threadContextMap");
        final ClassLoader classLoader = ProviderUtil.findClassLoader();
        if (stringProperty != null) {
            final Class<?> loadClass = classLoader.loadClass(stringProperty);
            if (ThreadContextMap.class.isAssignableFrom(loadClass)) {
                ThreadContext.contextMap = (ThreadContextMap)loadClass.newInstance();
            }
        }
        if (ThreadContext.contextMap == null && ProviderUtil.hasProviders()) {
            final LoggerContextFactory factory = LogManager.getFactory();
            final Iterator providers = ProviderUtil.getProviders();
            while (providers.hasNext()) {
                final Provider provider = providers.next();
                final String threadContextMap = provider.getThreadContextMap();
                final String className = provider.getClassName();
                if (threadContextMap != null && factory.getClass().getName().equals(className)) {
                    final Class<?> loadClass2 = classLoader.loadClass(threadContextMap);
                    if (ThreadContextMap.class.isAssignableFrom(loadClass2)) {
                        ThreadContext.contextMap = (ThreadContextMap)loadClass2.newInstance();
                        break;
                    }
                    continue;
                }
            }
        }
        if (ThreadContext.contextMap == null) {
            ThreadContext.contextMap = new DefaultThreadContextMap(ThreadContext.useMap);
        }
    }
    
    public interface ContextStack extends Serializable
    {
        void clear();
        
        String pop();
        
        String peek();
        
        void push(final String p0);
        
        int getDepth();
        
        List asList();
        
        void trim(final int p0);
        
        ContextStack copy();
    }
}
