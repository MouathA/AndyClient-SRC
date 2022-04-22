package org.apache.logging.log4j.core.impl;

import java.io.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.util.*;
import java.security.*;
import java.net.*;
import org.apache.logging.log4j.status.*;

public class ThrowableProxy implements Serializable
{
    private static final long serialVersionUID = -2752771578252251910L;
    private static final Logger LOGGER;
    private static final PrivateSecurityManager SECURITY_MANAGER;
    private static final Method GET_SUPPRESSED;
    private static final Method ADD_SUPPRESSED;
    private final ThrowableProxy proxyCause;
    private final Throwable throwable;
    private final String name;
    private final StackTracePackageElement[] callerPackageData;
    private int commonElementCount;
    
    public ThrowableProxy(final Throwable t) {
        this.throwable = t;
        this.name = t.getClass().getName();
        final HashMap hashMap = new HashMap();
        final Stack currentStack = this.getCurrentStack();
        this.callerPackageData = this.resolvePackageData(currentStack, hashMap, null, t.getStackTrace());
        this.proxyCause = ((t.getCause() == null) ? null : new ThrowableProxy(t, currentStack, hashMap, t.getCause()));
        this.setSuppressed(t);
    }
    
    private ThrowableProxy(final Throwable t, final Stack stack, final Map map, final Throwable t2) {
        this.throwable = t2;
        this.name = t2.getClass().getName();
        this.callerPackageData = this.resolvePackageData(stack, map, t.getStackTrace(), t2.getStackTrace());
        this.proxyCause = ((t2.getCause() == null) ? null : new ThrowableProxy(t, stack, map, t2.getCause()));
        this.setSuppressed(t2);
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    public ThrowableProxy getCause() {
        return this.proxyCause;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCommonElementCount() {
        return this.commonElementCount;
    }
    
    public StackTracePackageElement[] getPackageData() {
        return this.callerPackageData;
    }
    
    @Override
    public String toString() {
        final String message = this.throwable.getMessage();
        return (message != null) ? (this.name + ": " + message) : this.name;
    }
    
    public String getRootCauseStackTrace() {
        return this.getRootCauseStackTrace(null);
    }
    
    public String getRootCauseStackTrace(final List list) {
        final StringBuilder sb = new StringBuilder();
        if (this.proxyCause != null) {
            this.formatWrapper(sb, this.proxyCause);
            sb.append("Wrapped by: ");
        }
        sb.append(this.toString());
        sb.append("\n");
        this.formatElements(sb, 0, this.throwable.getStackTrace(), this.callerPackageData, list);
        return sb.toString();
    }
    
    public void formatWrapper(final StringBuilder sb, final ThrowableProxy throwableProxy) {
        this.formatWrapper(sb, throwableProxy, null);
    }
    
    public void formatWrapper(final StringBuilder sb, final ThrowableProxy throwableProxy, final List list) {
        if (((throwableProxy.getCause() != null) ? throwableProxy.getCause().getThrowable() : null) != null) {
            this.formatWrapper(sb, throwableProxy.proxyCause);
            sb.append("Wrapped by: ");
        }
        sb.append(throwableProxy).append("\n");
        this.formatElements(sb, throwableProxy.commonElementCount, throwableProxy.getThrowable().getStackTrace(), throwableProxy.callerPackageData, list);
    }
    
    public String getExtendedStackTrace() {
        return this.getExtendedStackTrace(null);
    }
    
    public String getExtendedStackTrace(final List list) {
        final StringBuilder sb = new StringBuilder(this.name);
        if (this.throwable.getMessage() != null) {
            sb.append(": ").append(this.throwable.getMessage());
        }
        sb.append("\n");
        this.formatElements(sb, 0, this.throwable.getStackTrace(), this.callerPackageData, list);
        if (this.proxyCause != null) {
            this.formatCause(sb, this.proxyCause, list);
        }
        return sb.toString();
    }
    
    public String getSuppressedStackTrace() {
        final ThrowableProxy[] suppressed = this.getSuppressed();
        if (suppressed == null || suppressed.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder("Suppressed Stack Trace Elements:\n");
        final ThrowableProxy[] array = suppressed;
        while (0 < array.length) {
            sb.append(array[0].getExtendedStackTrace());
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private void formatCause(final StringBuilder sb, final ThrowableProxy throwableProxy, final List list) {
        sb.append("Caused by: ").append(throwableProxy).append("\n");
        this.formatElements(sb, throwableProxy.commonElementCount, throwableProxy.getThrowable().getStackTrace(), throwableProxy.callerPackageData, list);
        if (throwableProxy.getCause() != null) {
            this.formatCause(sb, throwableProxy.proxyCause, list);
        }
    }
    
    private void formatElements(final StringBuilder sb, final int n, final StackTraceElement[] array, final StackTracePackageElement[] array2, final List list) {
        if (list == null || list.size() == 0) {
            while (0 < array2.length) {
                this.formatEntry(array[0], array2[0], sb);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            while (0 < array2.length) {
                if (!this.isSuppressed(array[0], list)) {
                    if (0 > 0) {
                        if (false == true) {
                            sb.append("\t....\n");
                        }
                        else {
                            sb.append("\t... suppressed ").append(0).append(" lines\n");
                        }
                    }
                    this.formatEntry(array[0], array2[0], sb);
                }
                else {
                    int n2 = 0;
                    ++n2;
                }
                int n3 = 0;
                ++n3;
            }
            if (0 > 0) {
                if (false == true) {
                    sb.append("\t...\n");
                }
                else {
                    sb.append("\t... suppressed ").append(0).append(" lines\n");
                }
            }
        }
        if (n != 0) {
            sb.append("\t... ").append(n).append(" more").append("\n");
        }
    }
    
    private void formatEntry(final StackTraceElement stackTraceElement, final StackTracePackageElement stackTracePackageElement, final StringBuilder sb) {
        sb.append("\tat ");
        sb.append(stackTraceElement);
        sb.append(" ");
        sb.append(stackTracePackageElement);
        sb.append("\n");
    }
    
    private boolean isSuppressed(final StackTraceElement stackTraceElement, final List list) {
        final String className = stackTraceElement.getClassName();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (className.startsWith(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    private Stack getCurrentStack() {
        if (ReflectiveCallerClassUtility.isSupported()) {
            final Stack<Class> stack = new Stack<Class>();
            for (Class clazz = ReflectiveCallerClassUtility.getCaller(1); clazz != null; clazz = ReflectiveCallerClassUtility.getCaller(1)) {
                stack.push(clazz);
                int n = 0;
                ++n;
            }
            return stack;
        }
        if (ThrowableProxy.SECURITY_MANAGER != null) {
            final Class[] classes = ThrowableProxy.SECURITY_MANAGER.getClasses();
            final Stack<Class> stack2 = new Stack<Class>();
            final Class[] array = classes;
            while (0 < array.length) {
                stack2.push(array[0]);
                int n2 = 0;
                ++n2;
            }
            return stack2;
        }
        return new Stack();
    }
    
    StackTracePackageElement[] resolvePackageData(final Stack stack, final Map map, final StackTraceElement[] array, final StackTraceElement[] array2) {
        int length;
        if (array != null) {
            int n;
            int n2;
            for (n = array.length - 1, n2 = array2.length - 1; n >= 0 && n2 >= 0 && array[n].equals(array2[n2]); --n, --n2) {}
            this.commonElementCount = array2.length - 1 - n2;
            length = n2 + 1;
        }
        else {
            this.commonElementCount = 0;
            length = array2.length;
        }
        final StackTracePackageElement[] array3 = new StackTracePackageElement[length];
        Class clazz = stack.isEmpty() ? null : stack.peek();
        ClassLoader classLoader = null;
        for (int i = length - 1; i >= 0; --i) {
            final String className = array2[i].getClassName();
            if (clazz != null && className.equals(clazz.getName())) {
                final CacheEntry resolvePackageElement = this.resolvePackageElement(clazz, true);
                array3[i] = CacheEntry.access$100(resolvePackageElement);
                classLoader = CacheEntry.access$200(resolvePackageElement);
                stack.pop();
                clazz = (stack.isEmpty() ? null : stack.peek());
            }
            else if (map.containsKey(className)) {
                final CacheEntry cacheEntry = map.get(className);
                array3[i] = CacheEntry.access$100(cacheEntry);
                if (CacheEntry.access$200(cacheEntry) != null) {
                    classLoader = CacheEntry.access$200(cacheEntry);
                }
            }
            else {
                final CacheEntry resolvePackageElement2 = this.resolvePackageElement(this.loadClass(classLoader, className), false);
                array3[i] = CacheEntry.access$100(resolvePackageElement2);
                map.put(className, resolvePackageElement2);
                if (CacheEntry.access$200(resolvePackageElement2) != null) {
                    classLoader = CacheEntry.access$200(resolvePackageElement2);
                }
            }
        }
        return array3;
    }
    
    private CacheEntry resolvePackageElement(final Class clazz, final boolean b) {
        String s = "?";
        String s2 = "?";
        ClassLoader classLoader = null;
        if (clazz != null) {
            final CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                final URL location = codeSource.getLocation();
                if (location != null) {
                    final String replace = location.toString().replace('\\', '/');
                    final int lastIndex = replace.lastIndexOf("/");
                    if (lastIndex >= 0 && lastIndex == replace.length() - 1) {
                        s = replace.substring(replace.lastIndexOf("/", lastIndex - 1) + 1);
                    }
                    else {
                        s = replace.substring(lastIndex + 1);
                    }
                }
            }
            final Package package1 = clazz.getPackage();
            if (package1 != null) {
                final String implementationVersion = package1.getImplementationVersion();
                if (implementationVersion != null) {
                    s2 = implementationVersion;
                }
            }
            classLoader = clazz.getClassLoader();
        }
        return new CacheEntry(new StackTracePackageElement(s, s2, b), classLoader);
    }
    
    private Class loadClass(final ClassLoader classLoader, final String s) {
        if (classLoader != null) {
            final Class<?> loadClass = classLoader.loadClass(s);
            if (loadClass != null) {
                return loadClass;
            }
        }
        return Thread.currentThread().getContextClassLoader().loadClass(s);
    }
    
    public ThrowableProxy[] getSuppressed() {
        if (ThrowableProxy.GET_SUPPRESSED != null) {
            return (ThrowableProxy[])ThrowableProxy.GET_SUPPRESSED.invoke(this.throwable, new Object[0]);
        }
        return null;
    }
    
    private void setSuppressed(final Throwable t) {
        if (ThrowableProxy.GET_SUPPRESSED != null && ThrowableProxy.ADD_SUPPRESSED != null) {
            final Throwable[] array = (Throwable[])ThrowableProxy.GET_SUPPRESSED.invoke(t, new Object[0]);
            while (0 < array.length) {
                ThrowableProxy.ADD_SUPPRESSED.invoke(this, new ThrowableProxy(array[0]));
                int n = 0;
                ++n;
            }
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        if (ReflectiveCallerClassUtility.isSupported()) {
            SECURITY_MANAGER = null;
        }
        else {
            PrivateSecurityManager security_MANAGER = new PrivateSecurityManager(null);
            if (security_MANAGER.getClasses() == null) {
                security_MANAGER = null;
                ThrowableProxy.LOGGER.error("Unable to obtain call stack from security manager.");
            }
            SECURITY_MANAGER = security_MANAGER;
        }
        Method get_SUPPRESSED = null;
        Method add_SUPPRESSED = null;
        final Method[] methods = Throwable.class.getMethods();
        while (0 < methods.length) {
            final Method method = methods[0];
            if (method.getName().equals("getSuppressed")) {
                get_SUPPRESSED = method;
            }
            else if (method.getName().equals("addSuppressed")) {
                add_SUPPRESSED = method;
            }
            int n = 0;
            ++n;
        }
        GET_SUPPRESSED = get_SUPPRESSED;
        ADD_SUPPRESSED = add_SUPPRESSED;
    }
    
    private static class PrivateSecurityManager extends SecurityManager
    {
        private PrivateSecurityManager() {
        }
        
        public Class[] getClasses() {
            return this.getClassContext();
        }
        
        PrivateSecurityManager(final ThrowableProxy$1 object) {
            this();
        }
    }
    
    class CacheEntry
    {
        private final StackTracePackageElement element;
        private final ClassLoader loader;
        final ThrowableProxy this$0;
        
        public CacheEntry(final ThrowableProxy this$0, final StackTracePackageElement element, final ClassLoader loader) {
            this.this$0 = this$0;
            this.element = element;
            this.loader = loader;
        }
        
        static StackTracePackageElement access$100(final CacheEntry cacheEntry) {
            return cacheEntry.element;
        }
        
        static ClassLoader access$200(final CacheEntry cacheEntry) {
            return cacheEntry.loader;
        }
    }
}
