package com.google.common.eventbus;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

class EventSubscriber
{
    private final Object target;
    private final Method method;
    
    EventSubscriber(final Object target, final Method method) {
        Preconditions.checkNotNull(target, (Object)"EventSubscriber target cannot be null.");
        Preconditions.checkNotNull(method, (Object)"EventSubscriber method cannot be null.");
        this.target = target;
        (this.method = method).setAccessible(true);
    }
    
    public void handleEvent(final Object o) throws InvocationTargetException {
        Preconditions.checkNotNull(o);
        this.method.invoke(this.target, o);
    }
    
    @Override
    public String toString() {
        return "[wrapper " + this.method + "]";
    }
    
    @Override
    public int hashCode() {
        return (31 + this.method.hashCode()) * 31 + System.identityHashCode(this.target);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof EventSubscriber) {
            final EventSubscriber eventSubscriber = (EventSubscriber)o;
            return this.target == eventSubscriber.target && this.method.equals(eventSubscriber.method);
        }
        return false;
    }
    
    public Object getSubscriber() {
        return this.target;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
