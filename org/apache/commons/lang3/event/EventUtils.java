package org.apache.commons.lang3.event;

import org.apache.commons.lang3.reflect.*;
import java.util.*;
import java.lang.reflect.*;

public class EventUtils
{
    public static void addEventListener(final Object o, final Class clazz, final Object o2) {
        MethodUtils.invokeMethod(o, "add" + clazz.getSimpleName(), o2);
    }
    
    public static void bindEventsToMethod(final Object o, final String s, final Object o2, final Class clazz, final String... array) {
        addEventListener(o2, clazz, clazz.cast(Proxy.newProxyInstance(o.getClass().getClassLoader(), new Class[] { clazz }, new EventBindingInvocationHandler(o, s, array))));
    }
    
    private static class EventBindingInvocationHandler implements InvocationHandler
    {
        private final Object target;
        private final String methodName;
        private final Set eventTypes;
        
        EventBindingInvocationHandler(final Object target, final String methodName, final String[] array) {
            this.target = target;
            this.methodName = methodName;
            this.eventTypes = new HashSet(Arrays.asList(array));
        }
        
        @Override
        public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
            if (!this.eventTypes.isEmpty() && !this.eventTypes.contains(method.getName())) {
                return null;
            }
            if (this.hasMatchingParametersMethod(method)) {
                return MethodUtils.invokeMethod(this.target, this.methodName, array);
            }
            return MethodUtils.invokeMethod(this.target, this.methodName, new Object[0]);
        }
        
        private boolean hasMatchingParametersMethod(final Method method) {
            return MethodUtils.getAccessibleMethod(this.target.getClass(), this.methodName, (Class[])method.getParameterTypes()) != null;
        }
    }
}
