package com.google.common.eventbus;

import java.util.concurrent.locks.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.annotations.*;
import com.google.common.cache.*;
import com.google.common.reflect.*;
import java.util.logging.*;

@Beta
public class EventBus
{
    private static final LoadingCache flattenHierarchyCache;
    private final SetMultimap subscribersByType;
    private final ReadWriteLock subscribersByTypeLock;
    private final SubscriberFindingStrategy finder;
    private final ThreadLocal eventsToDispatch;
    private final ThreadLocal isDispatching;
    private SubscriberExceptionHandler subscriberExceptionHandler;
    
    public EventBus() {
        this("default");
    }
    
    public EventBus(final String s) {
        this(new LoggingSubscriberExceptionHandler(s));
    }
    
    public EventBus(final SubscriberExceptionHandler subscriberExceptionHandler) {
        this.subscribersByType = HashMultimap.create();
        this.subscribersByTypeLock = new ReentrantReadWriteLock();
        this.finder = new AnnotatedSubscriberFinder();
        this.eventsToDispatch = new ThreadLocal() {
            final EventBus this$0;
            
            @Override
            protected Queue initialValue() {
                return new LinkedList();
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        this.isDispatching = new ThreadLocal() {
            final EventBus this$0;
            
            @Override
            protected Boolean initialValue() {
                return false;
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        this.subscriberExceptionHandler = (SubscriberExceptionHandler)Preconditions.checkNotNull(subscriberExceptionHandler);
    }
    
    public void register(final Object o) {
        final Multimap allSubscribers = this.finder.findAllSubscribers(o);
        this.subscribersByTypeLock.writeLock().lock();
        this.subscribersByType.putAll(allSubscribers);
        this.subscribersByTypeLock.writeLock().unlock();
    }
    
    public void unregister(final Object o) {
        for (final Map.Entry<Class, V> entry : this.finder.findAllSubscribers(o).asMap().entrySet()) {
            final Class clazz = entry.getKey();
            final Collection collection = (Collection)entry.getValue();
            this.subscribersByTypeLock.writeLock().lock();
            final Set value = this.subscribersByType.get((Object)clazz);
            if (!value.containsAll(collection)) {
                throw new IllegalArgumentException("missing event subscriber for an annotated method. Is " + o + " registered?");
            }
            value.removeAll(collection);
            this.subscribersByTypeLock.writeLock().unlock();
        }
    }
    
    public void post(final Object o) {
        for (final Class clazz : this.flattenHierarchy(o.getClass())) {
            this.subscribersByTypeLock.readLock().lock();
            final Set value = this.subscribersByType.get((Object)clazz);
            if (!value.isEmpty()) {
                final Iterator<EventSubscriber> iterator2 = value.iterator();
                while (iterator2.hasNext()) {
                    this.enqueueEvent(o, iterator2.next());
                }
            }
            this.subscribersByTypeLock.readLock().unlock();
        }
        if (!true && !(o instanceof DeadEvent)) {
            this.post(new DeadEvent(this, o));
        }
        this.dispatchQueuedEvents();
    }
    
    void enqueueEvent(final Object o, final EventSubscriber eventSubscriber) {
        this.eventsToDispatch.get().offer(new EventWithSubscriber(o, eventSubscriber));
    }
    
    void dispatchQueuedEvents() {
        if (this.isDispatching.get()) {
            return;
        }
        this.isDispatching.set(true);
        EventWithSubscriber eventWithSubscriber;
        while ((eventWithSubscriber = this.eventsToDispatch.get().poll()) != null) {
            this.dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
        }
        this.isDispatching.remove();
        this.eventsToDispatch.remove();
    }
    
    void dispatch(final Object o, final EventSubscriber eventSubscriber) {
        eventSubscriber.handleEvent(o);
    }
    
    @VisibleForTesting
    Set flattenHierarchy(final Class clazz) {
        return (Set)EventBus.flattenHierarchyCache.getUnchecked(clazz);
    }
    
    static {
        flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
            public Set load(final Class clazz) {
                return TypeToken.of(clazz).getTypes().rawTypes();
            }
            
            @Override
            public Object load(final Object o) throws Exception {
                return this.load((Class)o);
            }
        });
    }
    
    static class EventWithSubscriber
    {
        final Object event;
        final EventSubscriber subscriber;
        
        public EventWithSubscriber(final Object o, final EventSubscriber eventSubscriber) {
            this.event = Preconditions.checkNotNull(o);
            this.subscriber = (EventSubscriber)Preconditions.checkNotNull(eventSubscriber);
        }
    }
    
    private static final class LoggingSubscriberExceptionHandler implements SubscriberExceptionHandler
    {
        private final Logger logger;
        
        public LoggingSubscriberExceptionHandler(final String s) {
            this.logger = Logger.getLogger(EventBus.class.getName() + "." + (String)Preconditions.checkNotNull(s));
        }
        
        @Override
        public void handleException(final Throwable t, final SubscriberExceptionContext subscriberExceptionContext) {
            this.logger.log(Level.SEVERE, "Could not dispatch event: " + subscriberExceptionContext.getSubscriber() + " to " + subscriberExceptionContext.getSubscriberMethod(), t.getCause());
        }
    }
}
