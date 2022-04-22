package com.darkmagician6.eventapi;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import com.darkmagician6.eventapi.types.*;
import java.lang.annotation.*;
import com.darkmagician6.eventapi.events.*;

public final class EventManager
{
    private static final Map REGISTRY_MAP;
    
    static {
        REGISTRY_MAP = new HashMap();
    }
    
    public static void register(final Object o) {
        Method[] declaredMethods;
        while (0 < (declaredMethods = o.getClass().getDeclaredMethods()).length) {
            final Method method = declaredMethods[0];
            if (!isMethodBad(method)) {
                register(method, o);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void register(final Object o, final Class clazz) {
        Method[] declaredMethods;
        while (0 < (declaredMethods = o.getClass().getDeclaredMethods()).length) {
            final Method method = declaredMethods[0];
            if (!isMethodBad(method, clazz)) {
                register(method, o);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void unregister(final Object o) {
        for (final List<MethodData> list : EventManager.REGISTRY_MAP.values()) {
            for (final MethodData methodData : list) {
                if (methodData.getSource().equals(o)) {
                    list.remove(methodData);
                }
            }
        }
        cleanMap(true);
    }
    
    public static void unregister(final Object o, final Class clazz) {
        if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
            for (final MethodData methodData : EventManager.REGISTRY_MAP.get(clazz)) {
                if (methodData.getSource().equals(o)) {
                    EventManager.REGISTRY_MAP.get(clazz).remove(methodData);
                }
            }
            cleanMap(true);
        }
    }
    
    private static void register(final Method method, final Object o) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final MethodData methodData = new MethodData(o, method, method.getAnnotation(EventTarget.class).value());
        if (!methodData.getTarget().isAccessible()) {
            methodData.getTarget().setAccessible(true);
        }
        if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
            if (!EventManager.REGISTRY_MAP.get(clazz).contains(methodData)) {
                EventManager.REGISTRY_MAP.get(clazz).add(methodData);
                sortListValue(clazz);
            }
        }
        else {
            EventManager.REGISTRY_MAP.put(clazz, new CopyOnWriteArrayList() {
                private static final long serialVersionUID = 666L;
                
                {
                    this.add(methodData);
                }
            });
        }
    }
    
    public static void removeEntry(final Class clazz) {
        final Iterator<Map.Entry<Class<?>, V>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(clazz)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public static void cleanMap(final boolean b) {
        final Iterator<Map.Entry<K, List>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!b || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }
    
    private static void sortListValue(final Class clazz) {
        final CopyOnWriteArrayList<MethodData> list = new CopyOnWriteArrayList<MethodData>();
        byte[] value_ARRAY;
        while (0 < (value_ARRAY = Priority.VALUE_ARRAY).length) {
            final byte b = value_ARRAY[0];
            for (final MethodData methodData : EventManager.REGISTRY_MAP.get(clazz)) {
                if (methodData.getPriority() == b) {
                    list.add(methodData);
                }
            }
            int n = 0;
            ++n;
        }
        EventManager.REGISTRY_MAP.put(clazz, list);
    }
    
    private static boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }
    
    private static boolean isMethodBad(final Method method, final Class clazz) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(clazz);
    }
    
    public static final Event call(final Event event) {
        final List<MethodData> list = EventManager.REGISTRY_MAP.get(event.getClass());
        if (list != null) {
            if (event instanceof EventStoppable) {
                final EventStoppable eventStoppable = (EventStoppable)event;
                final Iterator<MethodData> iterator = list.iterator();
                while (iterator.hasNext()) {
                    invoke(iterator.next(), event);
                    if (eventStoppable.isStopped()) {
                        break;
                    }
                }
            }
            else {
                final Iterator<MethodData> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    invoke(iterator2.next(), event);
                }
            }
        }
        return event;
    }
    
    private static void invoke(final MethodData methodData, final Event event) {
        methodData.getTarget().invoke(methodData.getSource(), event);
    }
    
    private static final class MethodData
    {
        private final Object source;
        private final Method target;
        private final byte priority;
        
        public MethodData(final Object source, final Method target, final byte priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }
        
        public Object getSource() {
            return this.source;
        }
        
        public Method getTarget() {
            return this.target;
        }
        
        public byte getPriority() {
            return this.priority;
        }
    }
}
