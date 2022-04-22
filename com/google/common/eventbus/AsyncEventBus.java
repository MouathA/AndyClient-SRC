package com.google.common.eventbus;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public class AsyncEventBus extends EventBus
{
    private final Executor executor;
    private final ConcurrentLinkedQueue eventsToDispatch;
    
    public AsyncEventBus(final String s, final Executor executor) {
        super(s);
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }
    
    public AsyncEventBus(final Executor executor, final SubscriberExceptionHandler subscriberExceptionHandler) {
        super(subscriberExceptionHandler);
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }
    
    public AsyncEventBus(final Executor executor) {
        super("default");
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }
    
    @Override
    void enqueueEvent(final Object o, final EventSubscriber eventSubscriber) {
        this.eventsToDispatch.offer(new EventWithSubscriber(o, eventSubscriber));
    }
    
    protected void dispatchQueuedEvents() {
        while (true) {
            final EventWithSubscriber eventWithSubscriber = this.eventsToDispatch.poll();
            if (eventWithSubscriber == null) {
                break;
            }
            this.dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
        }
    }
    
    @Override
    void dispatch(final Object o, final EventSubscriber eventSubscriber) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(eventSubscriber);
        this.executor.execute(new Runnable(o, eventSubscriber) {
            final Object val$event;
            final EventSubscriber val$subscriber;
            final AsyncEventBus this$0;
            
            @Override
            public void run() {
                AsyncEventBus.access$001(this.this$0, this.val$event, this.val$subscriber);
            }
        });
    }
    
    static void access$001(final AsyncEventBus asyncEventBus, final Object o, final EventSubscriber eventSubscriber) {
        asyncEventBus.dispatch(o, eventSubscriber);
    }
}
