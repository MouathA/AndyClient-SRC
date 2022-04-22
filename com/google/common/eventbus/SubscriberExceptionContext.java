package com.google.common.eventbus;

import java.lang.reflect.*;
import com.google.common.base.*;

public class SubscriberExceptionContext
{
    private final EventBus eventBus;
    private final Object event;
    private final Object subscriber;
    private final Method subscriberMethod;
    
    SubscriberExceptionContext(final EventBus eventBus, final Object o, final Object o2, final Method method) {
        this.eventBus = (EventBus)Preconditions.checkNotNull(eventBus);
        this.event = Preconditions.checkNotNull(o);
        this.subscriber = Preconditions.checkNotNull(o2);
        this.subscriberMethod = (Method)Preconditions.checkNotNull(method);
    }
    
    public EventBus getEventBus() {
        return this.eventBus;
    }
    
    public Object getEvent() {
        return this.event;
    }
    
    public Object getSubscriber() {
        return this.subscriber;
    }
    
    public Method getSubscriberMethod() {
        return this.subscriberMethod;
    }
}
