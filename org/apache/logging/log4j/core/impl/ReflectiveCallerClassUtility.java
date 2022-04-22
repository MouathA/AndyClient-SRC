package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.core.helpers.*;
import java.lang.reflect.*;

public final class ReflectiveCallerClassUtility
{
    private static final Logger LOGGER;
    private static final Method GET_CALLER_CLASS_METHOD;
    
    private ReflectiveCallerClassUtility() {
    }
    
    public static boolean isSupported() {
        return ReflectiveCallerClassUtility.GET_CALLER_CLASS_SUPPORTED;
    }
    
    public static Class getCaller(final int n) {
        return (Class)ReflectiveCallerClassUtility.GET_CALLER_CLASS_METHOD.invoke(null, n + 1 - 1);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        Method get_CALLER_CLASS_METHOD = null;
        final Class<?> loadClass = Loader.getClassLoader().loadClass("sun.reflect.Reflection");
        final Method[] methods = loadClass.getMethods();
        while (0 < methods.length) {
            final Method method = methods[0];
            final int modifiers = method.getModifiers();
            final Class<?>[] parameterTypes = method.getParameterTypes();
            if (method.getName().equals("getCallerClass") && Modifier.isStatic(modifiers) && parameterTypes.length == 1 && parameterTypes[0] == Integer.TYPE) {
                get_CALLER_CLASS_METHOD = method;
                break;
            }
            int n = 0;
            ++n;
        }
        if (get_CALLER_CLASS_METHOD == null) {
            ReflectiveCallerClassUtility.LOGGER.info("sun.reflect.Reflection#getCallerClass does not exist.");
        }
        else {
            final Object invoke = get_CALLER_CLASS_METHOD.invoke(null, 0);
            if (invoke == null || invoke != loadClass) {
                get_CALLER_CLASS_METHOD = null;
                ReflectiveCallerClassUtility.LOGGER.warn("sun.reflect.Reflection#getCallerClass returned unexpected value of [{}] and is unusable. Will fall back to another option.", invoke);
            }
            else if (get_CALLER_CLASS_METHOD.invoke(null, 1) == loadClass) {
                ReflectiveCallerClassUtility.LOGGER.warn("sun.reflect.Reflection#getCallerClass is broken in Java 7u25. You should upgrade to 7u40. Using alternate stack offset to compensate.");
            }
        }
        if (get_CALLER_CLASS_METHOD == null) {
            ReflectiveCallerClassUtility.GET_CALLER_CLASS_SUPPORTED = false;
            GET_CALLER_CLASS_METHOD = null;
        }
        else {
            ReflectiveCallerClassUtility.GET_CALLER_CLASS_SUPPORTED = true;
            GET_CALLER_CLASS_METHOD = get_CALLER_CLASS_METHOD;
            ReflectiveCallerClassUtility.JAVA_7U25_COMPENSATION_OFFSET = 1;
        }
    }
}
