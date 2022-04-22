package com.google.common.eventbus;

import java.lang.reflect.*;
import com.google.common.reflect.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import com.google.common.cache.*;
import java.util.*;
import com.google.common.base.*;
import javax.annotation.*;

class AnnotatedSubscriberFinder implements SubscriberFindingStrategy
{
    private static final LoadingCache subscriberMethodsCache;
    
    @Override
    public Multimap findAllSubscribers(final Object o) {
        final HashMultimap create = HashMultimap.create();
        for (final Method method : getAnnotatedMethods(o.getClass())) {
            create.put(method.getParameterTypes()[0], makeSubscriber(o, method));
        }
        return create;
    }
    
    private static ImmutableList getAnnotatedMethods(final Class clazz) {
        return (ImmutableList)AnnotatedSubscriberFinder.subscriberMethodsCache.getUnchecked(clazz);
    }
    
    private static ImmutableList getAnnotatedMethodsInternal(final Class clazz) {
        final Set rawTypes = TypeToken.of(clazz).getTypes().rawTypes();
        final HashMap hashMap = Maps.newHashMap();
        final Iterator<Class> iterator = rawTypes.iterator();
        while (iterator.hasNext()) {
            final Method[] methods = iterator.next().getMethods();
            while (0 < methods.length) {
                final Method method = methods[0];
                if (method.isAnnotationPresent(Subscribe.class)) {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation, but requires " + parameterTypes.length + " arguments.  Event subscriber methods must require a single argument.");
                    }
                    final MethodIdentifier methodIdentifier = new MethodIdentifier(method);
                    if (!hashMap.containsKey(methodIdentifier)) {
                        hashMap.put(methodIdentifier, method);
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return ImmutableList.copyOf(hashMap.values());
    }
    
    private static EventSubscriber makeSubscriber(final Object o, final Method method) {
        EventSubscriber eventSubscriber;
        if (method != null) {
            eventSubscriber = new EventSubscriber(o, method);
        }
        else {
            eventSubscriber = new SynchronizedEventSubscriber(o, method);
        }
        return eventSubscriber;
    }
    
    static ImmutableList access$000(final Class clazz) {
        return getAnnotatedMethodsInternal(clazz);
    }
    
    static {
        subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
            public ImmutableList load(final Class clazz) throws Exception {
                return AnnotatedSubscriberFinder.access$000(clazz);
            }
            
            @Override
            public Object load(final Object o) throws Exception {
                return this.load((Class)o);
            }
        });
    }
    
    private static final class MethodIdentifier
    {
        private final String name;
        private final List parameterTypes;
        
        MethodIdentifier(final Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.name, this.parameterTypes);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof MethodIdentifier) {
                final MethodIdentifier methodIdentifier = (MethodIdentifier)o;
                return this.name.equals(methodIdentifier.name) && this.parameterTypes.equals(methodIdentifier.parameterTypes);
            }
            return false;
        }
    }
}
